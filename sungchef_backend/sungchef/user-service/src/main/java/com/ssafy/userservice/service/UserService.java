package com.ssafy.userservice.service;

import com.ssafy.userservice.config.jwt.JwtToken;
import com.ssafy.userservice.config.JwtTokenProvider;
import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.db.repository.UserRepository;
import com.ssafy.userservice.db.repository.mapping.BookmarkMapping;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.dto.request.UserInfoReq;
import com.ssafy.userservice.dto.response.UserMakeRecipe;
import com.ssafy.userservice.dto.response.UserMakeRecipeRes;
import com.ssafy.userservice.dto.response.UserSimpleInfoRes;
import com.ssafy.userservice.exception.exception.FeignException;
import com.ssafy.userservice.exception.exception.FileUploadException;
import com.ssafy.userservice.exception.exception.NicknameExistException;
import com.ssafy.userservice.exception.exception.NoContentException;
import com.ssafy.userservice.exception.exception.PageConvertException;
import com.ssafy.userservice.exception.exception.UserExistException;
import com.ssafy.userservice.exception.exception.UserNeedSurveyException;
import com.ssafy.userservice.exception.exception.UserNotCreatedException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.exception.exception.UserNotFoundException;
import com.ssafy.userservice.service.client.RecipeServiceClient;
import com.ssafy.userservice.util.result.SingleResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	// private final FridgeRepository fridgeRepository;
	private final UserRepository userRepository;
	private final FileUploadService fileUploadService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final BookmarkService bookmarkService;

	private final RecipeServiceClient recipeServiceClient;

	public void userExist(String userSnsId) {
		Optional<User> user = userRepository.findByUserSnsId(userSnsId);
		if (user.isPresent()) throw new UserExistException("이미 존재하는 유저");
	}

	public void nicknameExist(String userNickname) {
		Optional<User> user = userRepository.findByUserNickname(userNickname);
		if (user.isPresent()) throw new NicknameExistException("이미 존재하는 닉네임");
	}

	@Transactional
	public JwtToken createUser(SignUpReq req) {

		userExist(req.userSnsId());
		nicknameExist(req.userSnsId());

		User user = userRepository.save(User.builder()
						.userId(-1)
						.userPassword(bCryptPasswordEncoder.encode(req.userSnsId()))
						.userSnsId(req.userSnsId())
						.userGenderType(req.userGender())
						.userSnsType(req.userSnsType())
						.userNickname(req.userNickName())
						.userBirthDate(req.userBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.build()
		);

		if (user.getUserId() == -1) throw new UserNotCreatedException("유저 생성 실패");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.userSnsId(), req.userSnsId());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.generateToken(authentication, req.userSnsId());
	}

	@Transactional
	public JwtToken loginUser(LoginReq req) {

		Optional<User> selectUser = userRepository.findByUserSnsId(req.userSnsId());

		if (selectUser.isEmpty()) throw new UserNotFoundException("유저가 존재하지 않음");
		User user = selectUser.get();
		if (!user.isUserIsSurvey()) throw new UserNeedSurveyException("설문이 필요한 유저");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserSnsId(), user.getUserSnsId());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		return jwtTokenProvider.generateToken(authentication, req.userSnsId());
	}

	public JwtToken reissue(String refreshToken) {
		return jwtTokenProvider.generateTokenFromRefreshToken(refreshToken.substring(7));
	}

	public User getUserBySnsId(String userSnsId) {
		Optional<User> selectUser = userRepository.findByUserSnsId(userSnsId);
		if (selectUser.isEmpty()) throw new UserNotFoundException("존재하지 않는 유저");
		User user = selectUser.get();
		if (!user.isUserIsSurvey()) throw new UserNeedSurveyException("설문이 필요한 유저");
		return selectUser.get();
	}
	public User getUserBySnsIdSubmitSurvey(String userSnsId) {
		Optional<User> selectUser = userRepository.findByUserSnsId(userSnsId);
		if (selectUser.isEmpty()) throw new UserNotFoundException("존재하지 않는 유저");
		return selectUser.get();
	}
	@Transactional
	public void updateUser(String userSnsId, UserInfoReq req) {

		Optional<User> selectUser = userRepository.findByUserSnsId(userSnsId);
		if (selectUser.isEmpty()) throw new UserNotFoundException("존재하지 않는 유저");
		User user = selectUser.get();

		if (req.userNickName().equals(user.getUserNickname())) { // 닉네임 변경
			Optional<User> conflictUser = userRepository.findByUserNickname(req.userNickName());
			if (conflictUser.isEmpty()) throw new NicknameExistException("이미 존재하는 닉네임");
		}

		if (req.userImage() != null) {
			try {
				String url = fileUploadService.uploadFile(req.userImage());
				user.updateUserImage(url);
			} catch (Exception e) {
				throw new FileUploadException("파일 업로드 실패");
			}
		}

		user.updateUserInfo(
			req.userNickName()
			, req.userBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
			, req.userGender()
		);
	}

	@Transactional
	public UserSimpleInfoRes getUserSimpleInfo(String userSnsId, String token) {
		User user = getUserBySnsId(userSnsId);
		int bookmarkCount = bookmarkService.getUserBookmarkCount(userSnsId);
		try {
			return UserSimpleInfoRes.builder()
				.userNickname(user.getUserNickname())
				.userImage(user.getUserImage())
				.makeRecipeCount(recipeServiceClient.getUserMakeRecipeCount(token))
				.bookmarkRecipeCount(bookmarkCount)
				.build();
		} catch (Exception e) {
			throw new FeignException("recipeServiceClient.getUserMakeRecipeCount ERROR");
		}
	}


	@Transactional
	public ResponseEntity<?> getUserMakeRecipe(String token, String page) {

		try {
			int pageNumber = Integer.parseInt(page);
		} catch (NumberFormatException e) {
			throw new PageConvertException();
		}

		// int pageNumber = Integer.parseInt(page);
		// Pageable pageable = PageRequest.of(pageNumber, 20);

		try {
			ResponseEntity<SingleResult<UserMakeRecipeRes>> res = recipeServiceClient.getUserMakeRecipe(token, page);
			log.info("{}", res);
			if (res.getBody().getData().getMakeRecipeList().size() == 0)
				throw new NoContentException("");
			return res;
		} catch (NoContentException e) {
			throw new NoContentException("");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FeignException("recipeServiceClient.getUserMakeRecipe ERROR");
		}
		// Page<BookmarkMapping> bookmarkRecipeMapping = bookmarkRepository.findAllByUserSnsId(userSnsId, pageable);
		//
		// if (bookmarkRecipeMapping.isEmpty())
		// 	throw new NoContentException("유저 레시피의 마지막 페이지");
		//
		// List<Integer> bookMarkRecipeList = bookmarkRecipeMapping.getContent()
		// 	.stream()
		// 	.map(BookmarkMapping::getRecipeId)
		// 	.toList();
	}
}

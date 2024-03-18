package com.sungchef.sungchef.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.userservice.dto.request.BookmarkReq;
import com.sungchef.sungchef.userservice.dto.request.ContactReq;
import com.sungchef.sungchef.userservice.dto.request.LoginReq;
import com.sungchef.sungchef.userservice.dto.request.ReissueReq;
import com.sungchef.sungchef.userservice.dto.request.SignUpReq;
import com.sungchef.sungchef.userservice.dto.request.UserInfoReq;
import com.sungchef.sungchef.userservice.dto.response.UserBookmarkRecipe;
import com.sungchef.sungchef.userservice.dto.response.UserBookmarkRecipeRes;
import com.sungchef.sungchef.userservice.dto.response.UserInfoRes;
import com.sungchef.sungchef.userservice.dto.response.UserMakeRecipe;
import com.sungchef.sungchef.userservice.dto.response.UserMakeRecipeRes;
import com.sungchef.sungchef.userservice.dto.response.UserSimpleInfoRes;
import com.sungchef.sungchef.userservice.dto.response.UserTokenRes;
import com.sungchef.sungchef.util.exception.NicknameExistException;
import com.sungchef.sungchef.util.exception.UserRecipeNotExistException;
import com.sungchef.sungchef.util.exception.UserNeedSurveyException;
import com.sungchef.sungchef.util.exception.UserNotFoundException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final ResponseService responseService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid final SignUpReq req) {
		// TODO
		try {
			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					UserTokenRes.builder()
						.accessToken("adkssudgktpdy")
						.refreshToken("qksrkqtmqslek")
						.build()
					, "회원가입 성공")
			);
		} catch (NicknameExistException e) {
			return responseService.CONFLICT();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(LoginReq req) {
		// TODO
		try {
			return ResponseEntity.ok()
				.body(
					responseService.getSuccessSingleResult(UserTokenRes
							.builder()
							.build()
						, "로그인 성공"));
		} catch (UserNotFoundException e) {
			return responseService.NOT_FOUND();
		} catch (UserNeedSurveyException e) {
			return responseService.FORBIDDEN();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/autologin")
	public ResponseEntity<?> autologin() {
		// TODO
		try {
			return responseService.OK();
		} catch (UserNeedSurveyException e) {
			return responseService.FORBIDDEN();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(final ReissueReq req) {
		// TODO
		try {
			return ResponseEntity.ok()
				.body(
					responseService.getSuccessSingleResult(UserTokenRes
							.builder()
							.build()
						, "토큰 재발급 성공"));
		} catch (UserNotFoundException e) {
			return responseService.NOT_FOUND();
		} catch (UserNeedSurveyException e) {
			return responseService.FORBIDDEN();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/exist/{nickname}")
	public ResponseEntity<?> nicknameExist(@PathVariable final String nickname) {
		// TODO
		try {
			return responseService.OK();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (UserNeedSurveyException e) {
			return responseService.CONFLICT();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	/**
	 * 유저가 만든 레시피의 숫자, 북마크한 레시피의 숫자, 유저의 닉네임과 프로필 사진을 반환하는 함수
	 * @return
	 */
	@GetMapping("/simple")
	public ResponseEntity<?> getUserSimpleInfo() {

		//TODO
		try {
			return ResponseEntity.ok().body(
				UserSimpleInfoRes.builder()
					.userImage("성훈")
					.userImage("https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
					.makeRecipeCount(10)
					.bookmarkRecipeCount(20)
					.build()
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/recipe/{page}")
	public ResponseEntity<?> getUserRecipe(@PathVariable final String page) {
		// TODO

		List<UserMakeRecipe> makeRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			makeRecipeList.add(UserMakeRecipe.builder()
				.makeRecipeCreateDate("2024-03-1" + i)
				.makeRecipeImage("https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.makeRecipeReview("고양이가 귀여워요")
				.build());
		}
		try {
			return ResponseEntity.ok().body(
				UserMakeRecipeRes.builder()
					.makeRecipeCount(9)
					.makeRecipeList(makeRecipeList)
					.build()
			);
		}catch (UserRecipeNotExistException e) {
			return responseService.NO_CONTENT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/bookmark/{page}")
	public ResponseEntity<?> userRecipe(@PathVariable final String page) {

		List<UserBookmarkRecipe> bookmarkRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			bookmarkRecipeList.add(UserBookmarkRecipe.builder()
					.recipeId(i)
					.recipeImage("https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.build());
		}
		try {
			return ResponseEntity.ok().body(
				UserBookmarkRecipeRes.builder()
					.bookmarkRecipeCount(9)
					.bookmarkRecipeList(bookmarkRecipeList)
					.build()
			);
		}catch (UserRecipeNotExistException e) {
			return responseService.NO_CONTENT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
	@GetMapping("")
	public ResponseEntity<?> userInfo() {
		// TODO
		try {

			return ResponseEntity.ok().body(
				UserInfoRes.builder()
					.userBirthdate("1998-01-22")
					.userNickName("성식당")
					.userGender('F')
					.userImage("https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
					.build()
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("")
	public ResponseEntity<?> updateUser(final UserInfoReq req) {
		// TODO
		try {
			return responseService.OK();
		} catch (NicknameExistException e) {
			return responseService.CONFLICT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/contact")
	public ResponseEntity<?> contact(final ContactReq req) {
		// TODO
		try {
			return responseService.OK();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
	@PostMapping("/bookmark")
	public ResponseEntity<?> bookmark(BookmarkReq req) {
		// TODO
		try {
			return responseService.OK();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}

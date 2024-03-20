package com.sungchef.sungchef.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.sungchef.sungchef.util.exception.UserNeedSurveyException;
import com.sungchef.sungchef.util.exception.UserNotFoundException;
import com.sungchef.sungchef.util.exception.UserRecipeNotExistException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;
import com.sungchef.sungchef.util.sungchefEnum.UserGenderType;

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
	public ResponseEntity<?> signUp(@RequestBody @Valid final SignUpReq req) {
		// TODO
		try {
			log.debug("/login : {}", req);
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
	public ResponseEntity<?> login(@RequestBody LoginReq req) {
		// TODO
		try {
			log.debug("/login : {}", req);
			return ResponseEntity.ok()
				.body(
					responseService.getSuccessSingleResult(UserTokenRes
							.builder()
							.accessToken("adkssudgktpdy")
							.refreshToken("qksrkqtmqslek")
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
			log.debug("/autologin");
			return ResponseEntity.ok(responseService.getSuccessMessageResult("자동 로그인 성공"));
		} catch (UserNeedSurveyException e) {
			return responseService.FORBIDDEN();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@RequestBody final ReissueReq req) {
		// TODO
		try {
			log.debug("/reissue : {}", req);
			return ResponseEntity.ok()
				.body(
					responseService.getSuccessSingleResult(UserTokenRes
							.builder()
							.accessToken("adkssudgktpdy")
							.refreshToken("qksrkqtmqslek")
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
	public ResponseEntity<?> nicknameExist(@PathVariable(value = "nickname") final String nickname) {
		// TODO
		try {
			log.debug("/exist/{nickname} : {}", nickname);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("사용 가능한 닉네임")
			);
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
				responseService.getSuccessSingleResult(
					UserSimpleInfoRes.builder()
						.userNickname("성훈")
						.userImage(
							"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
						.makeRecipeCount(10)
						.bookmarkRecipeCount(20)
						.build()
					, "유저 요약 정보 호출 성공"
				)
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/recipe/{page}")
	public ResponseEntity<?> getUserRecipe(@PathVariable("page") final String page) {
		// TODO
		List<UserMakeRecipe> makeRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			makeRecipeList.add(UserMakeRecipe.builder()
				.makeRecipeCreateDate("2024-03-1" + i)
				.makeRecipeImage(
					"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.makeRecipeReview("고양이가 귀여워요")
				.build());
		}
		try {
			log.debug("/recipe/{page} : {}", page);

			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					UserMakeRecipeRes.builder()
						.makeRecipeCount(9)
						.makeRecipeList(makeRecipeList)
						.build()
					, "유저가 만든 음식 정보 호출 성공"
				)

			);
		} catch (UserRecipeNotExistException e) {
			return responseService.NO_CONTENT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/bookmark/{page}")
	public ResponseEntity<?> userRecipe(@PathVariable("page") final String page) {

		// TODO
		List<UserBookmarkRecipe> bookmarkRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			bookmarkRecipeList.add(UserBookmarkRecipe.builder()
				.recipeId(i)
				.recipeImage(
					"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.build());
		}
		try {
			log.debug("/bookmark/{page} : {}", page);

			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					UserBookmarkRecipeRes.builder()
						.bookmarkRecipeCount(9)
						.bookmarkRecipeList(bookmarkRecipeList)
						.build()
					, "유저가 즐겨찾기한 음식 정보 호출 성공"
				)

			);
		} catch (UserRecipeNotExistException e) {
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
				responseService.getSuccessSingleResult(
					UserInfoRes.builder()
						.userBirthdate("1998-01-22")
						.userNickName("성식당")
						.userGender(UserGenderType.F)
						.userImage(
							"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
						.build()
					, "유저 정보 호출 성공"
				)
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PutMapping("")
	public ResponseEntity<?> updateUser(@RequestBody final UserInfoReq req) {

		// TODO
		try {
			log.debug("{}", req);
			return ResponseEntity.ok().body(
				responseService.getSuccessMessageResult("유저 정보 변경 성공")
			);
		} catch (NicknameExistException e) {
			return responseService.CONFLICT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/contact")
	public ResponseEntity<?> contact(@RequestBody final ContactReq req) {
		// TODO
		try {
			log.debug("/contact : {}", req);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("문의 완료")
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/bookmark")
	public ResponseEntity<?> bookmark(@RequestBody BookmarkReq req) {
		// TODO
		try {
			log.debug("/bookmark : {}", req);
			return ResponseEntity.ok(
				responseService.getSuccessMessageResult("북마크 성공")
			);
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}

package com.ssafy.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.userservice.dto.request.BookmarkReq;
import com.ssafy.userservice.dto.request.ContactReq;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.dto.request.UserInfoReq;
import com.ssafy.userservice.dto.response.UserBookmarkRecipe;
import com.ssafy.userservice.dto.response.UserBookmarkRecipeRes;
import com.ssafy.userservice.dto.response.UserInfoRes;
import com.ssafy.userservice.dto.response.UserMakeRecipe;
import com.ssafy.userservice.dto.response.UserMakeRecipeRes;
import com.ssafy.userservice.dto.response.UserSimpleInfoRes;
import com.ssafy.userservice.service.JwtService;
import com.ssafy.userservice.service.ResponseService;
import com.ssafy.userservice.service.UserService;
import com.ssafy.userservice.util.exception.JwtExpiredException;
import com.ssafy.userservice.util.exception.NicknameExistException;
import com.ssafy.userservice.util.exception.UserNeedSurveyException;
import com.ssafy.userservice.util.exception.UserNotFoundException;
import com.ssafy.userservice.util.exception.UserRecipeNotExistException;
import com.ssafy.userservice.util.sungchefEnum.UserGenderType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final ResponseService responseService;
	private final UserService userService;

	private final JwtService jwtService;
	// private final FridgeServiceClient fridgeServiceClient;

	// @GetMapping("/getFridgeIngredient")
	// public ResponseEntity<?> getFridgeIngredient() {
	//
	// 	//
	// 	ResponseEntity<SingleResult<?>> res = fridgeServiceClient.getFridgeIngredient();
	// 	log.info("result : {}", res.getBody().getData());
	// 	return fridgeServiceClient.getFridgeIngredient();
	// }
	//
	// @GetMapping("/getIngredientIdToCook/{recipeId}")
	// public ResponseEntity<?> getIngredientIdToCook(@PathVariable("recipeId") final String recipeId) {
	// 	return fridgeServiceClient.getIngredientIdToCook(recipeId);
	// }

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpReq req) {
		// TODO
		try {
			// if (user.getUserId() == -1) throw new BaseException("USER NOT CREATED");
			log.debug("/signup : {}", req);
			return  ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					userService.createUser(req)
					, "회원가입 성공")
			);
		} catch (NicknameExistException e) {
			return responseService.CONFLICT();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("signup INTERNAL_SERVER_ERROR", e);
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
					responseService.getSuccessSingleResult(
						userService.loginUser(req)
						, "로그인 성공"));
		} catch (UserNotFoundException e) {
			return responseService.NOT_FOUND();
		} catch (UserNeedSurveyException e) {
			return responseService.FORBIDDEN();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/autologin")
	public ResponseEntity<?> autologin(@RequestHeader("Authorization") final String accessToken) {
		// TODO

		log.info("token : {}", jwtService.getUserSnsId(accessToken));
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
	public ResponseEntity<?> reissue(@RequestHeader("Refresh") final String refreshToken) {
		// try {
		return ResponseEntity.ok()
			.body(
				responseService.getSuccessSingleResult(
					userService.reissue(refreshToken)
					, "토큰 재발급 성공")
			);
		// } catch (UserNotFoundException e) {
		// 	return responseService.NOT_FOUND();
		// } catch (JwtExpiredException e) {
		// 	return responseService.FORBIDDEN();
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// 	return responseService.INTERNAL_SERVER_ERROR();
		// }
	}

	@GetMapping("/exist/{nickname}")
	public ResponseEntity<?> nicknameExist(@PathVariable("nickname") final String nickname) {
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

package com.ssafy.userservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ssafy.userservice.db.entity.Bookmark;
import org.checkerframework.common.value.qual.IntVal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.dto.request.BookmarkReq;
import com.ssafy.userservice.dto.request.ContactReq;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.dto.request.UserInfoReq;
import com.ssafy.userservice.dto.response.UserBookmarkRecipeRes;
import com.ssafy.userservice.dto.response.UserInfoRes;
import com.ssafy.userservice.service.BookmarkService;
import com.ssafy.userservice.service.JwtService;
import com.ssafy.userservice.service.ResponseService;
import com.ssafy.userservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
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
	private final BookmarkService bookmarkService;
	private final JwtService jwtService;

	@PostMapping("/feign/userbookmark")
	public List<Bookmark> getUserBookmark(HttpServletRequest request, @RequestBody final List<Integer> req) {
		String userSnsId = jwtService.getUserSnsId(request);
		return bookmarkService.getUserBookmark(userSnsId, req);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpReq req) {
		log.debug("/signup : {}", req);
		
		return  ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				userService.createUser(req)
				, "회원가입 성공")
		);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginReq req) {
		log.debug("/login : {}", req);
		return ResponseEntity.ok()
			.body(
				responseService.getSuccessSingleResult(
					userService.loginUser(req)
					, "로그인 성공")
			);
	}

	@PostMapping("/autologin")
	public ResponseEntity<?> autologin(HttpServletRequest request) {
		log.debug("/autologin called");
		String userSnsId = jwtService.getUserSnsId(request);
		userService.autoLoginUser(userSnsId);
		return ResponseEntity.ok(responseService.getSuccessMessageResult("자동 로그인 성공"));
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@RequestHeader("Refresh") final String refreshToken) {
		log.debug("/reissue : {}", refreshToken);
		return ResponseEntity.ok()
			.body(
				responseService.getSuccessSingleResult(
					userService.reissue(refreshToken)
					, "토큰 재발급 성공")
			);
	}

	@GetMapping("/exist/{nickname}")
	public ResponseEntity<?> nicknameExist(@PathVariable("nickname") final String nickname) {
		log.debug("/exist/{nickname} : {}", nickname);
		userService.nicknameExist(nickname);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("사용 가능한 닉네임")
		);
	}

	/**
	 * 유저가 만든 레시피의 숫자, 북마크한 레시피의 숫자, 유저의 닉네임과 프로필 사진을 반환하는 함수
	 * @return
	 */
	@GetMapping("/simple")
	public ResponseEntity<?> getUserSimpleInfo(HttpServletRequest request) {

		String userSnsId = jwtService.getUserSnsId(request);
		String token = request.getHeader("Authorization");
		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				userService.getUserSimpleInfo(userSnsId, token)
				, "유저 요약 정보 호출 성공"
			)
		);
	}

	@GetMapping("/recipe/{page}")
	public ResponseEntity<?> getUserRecipe(HttpServletRequest request, @PathVariable("page") final String page) {
		return userService.getUserMakeRecipe(request.getHeader("Authorization"), page);
	}

	@GetMapping("/bookmark/{page}")
	public ResponseEntity<?> userRecipe(HttpServletRequest request, @PathVariable("page") final String page) {
		String userSnsId = jwtService.getUserSnsId(request);
		UserBookmarkRecipeRes res = bookmarkService.getUserBoomMarkRecipe(userSnsId, request.getHeader("Authorization"), page);
		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				res, "유저가 즐겨찾기한 음식 정보 호출 성공"
			)

		);
	}

	@GetMapping("")
	public ResponseEntity<?> userInfo(HttpServletRequest request) {
		String userSnsId = jwtService.getUserSnsId(request);
		log.debug("/user userSnsId: {}", userSnsId);
		User user = userService.getUserBySnsId(userSnsId);
		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				UserInfoRes.builder()
					.userBirthdate(user.getUserBirthDate())
					.userNickName(user.getUserNickname())
					.userGender(user.getUserGenderType())
					.userImage(user.getUserImage())
					.build()
				, "유저 정보 호출 성공"
			)
		);
	}

	@PutMapping(value = "", consumes = {"multipart/form-data"})
	public ResponseEntity<?> updateUser(
		HttpServletRequest request
		, @ModelAttribute("userImage") @Valid final UserInfoReq req
	)
	{
		String userSnsId = jwtService.getUserSnsId(request);
		userService.updateUser(userSnsId, req);
		return ResponseEntity.ok().body(
			responseService.getSuccessMessageResult("유저 정보 변경 성공")
		);
	}

	@PostMapping("/contact")
	public ResponseEntity<?> contact(@RequestBody final ContactReq req) {
		// TODO
		log.debug("/contact : {}", req);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("문의 완료")
		);
	}

	@PostMapping("/bookmark")
	public ResponseEntity<?> bookmark(HttpServletRequest request, @RequestBody BookmarkReq req) {
		log.debug("/bookmark : {}", req);
		String userSnsId = jwtService.getUserSnsId(request);
		bookmarkService.bookmarkRecipe(userSnsId, request.getHeader("Authorization") , req);
		return ResponseEntity.ok(responseService.getSuccessMessageResult("북마크 성공"));
	}
}

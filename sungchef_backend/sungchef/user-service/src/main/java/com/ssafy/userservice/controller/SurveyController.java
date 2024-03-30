package com.ssafy.userservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.userservice.config.JwtTokenProvider;
import com.ssafy.userservice.dto.request.SubmitSurveyReq;
import com.ssafy.userservice.dto.response.FoodInfo;
import com.ssafy.userservice.dto.response.SurveyRes;
import com.ssafy.userservice.service.JwtService;
import com.ssafy.userservice.service.ResponseService;
import com.ssafy.userservice.exception.exception.SurveyCountException;
import com.ssafy.userservice.service.SurveyService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/survey")
public class SurveyController {
	private final ResponseService responseService;
	private final SurveyService surveyService;
	private final JwtService jwtService;
	@GetMapping("")
	public ResponseEntity<?> getSurvey() {
		// TODO
		List<FoodInfo> surveyList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			surveyList.add(FoodInfo.builder()
				.foodImage(
					"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.foodId(i)
				.foodName("고양이" + i)
				.build());
		}

		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				SurveyRes.builder()
					.foodInfoList(surveyList)
					.build()
				, "설문 목록 조회 성공"
			)
		);
	}

	// 설문 제출쪽 수정 오류 필요
	@PostMapping("/submit")
	public ResponseEntity<?> submitSurvey(HttpServletRequest request, @RequestBody @Valid final SubmitSurveyReq req) {
		log.debug("POST /submit -> foodIdList : {}", Arrays.toString(req.foodIdList().toArray()));
		String userSnsId = jwtService.getUserSnsId(request);
		return ResponseEntity.ok(
			responseService.getSuccessSingleResult(
				surveyService.submitSurvey(userSnsId, req)
				,"설문 제출 성공"
			)
		);
	}

	/**
	 * DB에서 유저 데이터 삭제하는 작업 필요
	 */
	@PutMapping("/submit")
	public ResponseEntity<?> updateSurvey(HttpServletRequest request, @RequestBody @Valid final SubmitSurveyReq req) {
		// TODO
		log.debug("PUT /submit -> foodIdList : {}", Arrays.toString(req.foodIdList().toArray()));
		String userSnsId = jwtService.getUserSnsId(request);
		surveyService.updateSurvey(userSnsId, req);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("설문 제출 성공")
		);
	}
}

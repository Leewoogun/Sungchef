package com.ssafy.userservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssafy.userservice.dto.request.FoodIdListReq;
import com.ssafy.userservice.dto.response.fridge.Food;
import com.ssafy.userservice.dto.response.fridge.FoodList;
import com.ssafy.userservice.service.client.RecipeServiceClient;
import com.ssafy.userservice.util.result.SingleResult;
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
	private final RecipeServiceClient recipeServiceClient;
	private final JwtService jwtService;
	@GetMapping("")
	public ResponseEntity<?> getSurvey(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		List<Integer> foodIdList =  Arrays.asList(93,160,102,74,214,82,75,94,238,184,168,90,190,157,88,205,266,19,49,173);
		// TODO
		FoodIdListReq req = FoodIdListReq.builder()
				.foodIdList(foodIdList)
				.build();
		ResponseEntity<SingleResult<FoodList>> res = recipeServiceClient.getFoodList(req, token);
		List<Food> FoodListRes = res.getBody().getData().getFoodList();

		List<FoodInfo> surveyList = new ArrayList<>();
		for (int i = 0; i < foodIdList.size(); i++) {
			surveyList.add(FoodInfo.builder()
				.foodImage(FoodListRes.get(i).getFoodImage())
				.foodId(foodIdList.get(i))
				.foodName(FoodListRes.get(i).getFoodName())
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
		surveyService.submitSurvey(userSnsId, req);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("설문 제출 성공")
		);
	}

	/**
	 * DB에서 유저 데이터 삭제하는 작업 필요
	 */
	@PutMapping("/submit")
	public ResponseEntity<?> updateSurvey(HttpServletRequest request, @RequestBody @Valid final SubmitSurveyReq req) {
		log.debug("PUT /submit -> foodIdList : {}", Arrays.toString(req.foodIdList().toArray()));
		String userSnsId = jwtService.getUserSnsId(request);
		surveyService.updateSurvey(userSnsId, req);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("설문 제출 성공")
		);
	}
}

package com.sungchef.sungchef.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.userservice.dto.request.FoodId;
import com.sungchef.sungchef.userservice.dto.response.FoodInfo;
import com.sungchef.sungchef.userservice.dto.response.SurveyRes;
import com.sungchef.sungchef.userservice.dto.response.UserBookmarkRecipeRes;
import com.sungchef.sungchef.userservice.dto.response.UserMakeRecipe;
import com.sungchef.sungchef.util.exception.SurveyCountException;
import com.sungchef.sungchef.util.exception.UserNotFoundException;
import com.sungchef.sungchef.util.exception.UserRecipeNotExistException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
	private final ResponseService responseService;

	@GetMapping("")
	public ResponseEntity<?> getSurvey() {
		// TODO

		List<FoodInfo> surveyList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			surveyList.add(FoodInfo.builder()
					.foodImage("https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
					.foodId(i)
					.foodName("고양이" + i)
					.build());
		}

		try {
			return ResponseEntity.ok().body(
				SurveyRes.builder()
					.foodInfoList(surveyList)
					.build()
			);
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@PostMapping("/submit")
	public ResponseEntity<?> submitSurvey() {
		// TODO
		List<FoodId> foodIdList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			foodIdList.add(FoodId.builder()
					.foodId(i)
					.build());
		}

		try {
			return responseService.OK();
		} catch (SurveyCountException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	/**
	 * DB에서 유저 데이터 삭제하는 작업 필요
	 * @return
	 */
	@PutMapping("/submit")
	public ResponseEntity<?> updateSurvey() {
		// TODO
		return getSurvey();
	}
}

package com.ssafy.fridgeservice.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.ssafy.fridgeservice.db.repository.FridgeRepository;
import com.ssafy.fridgeservice.dto.response.FridgeIngredientListRes;
import com.ssafy.fridgeservice.dto.response.Ingredient;
import com.ssafy.fridgeservice.dto.response.IngredientInfo;
import com.ssafy.fridgeservice.service.client.IngredientServiceClient;
import com.ssafy.fridgeservice.util.result.SingleResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class FridgeService {

	private final ResponseService responseService;
	private final FridgeRepository fridgeRepository;
	private final IngredientServiceClient ingredientServiceClient;


	// userSnsId 를 받은 다음에 ingredient info 반환해주는 함수 (controller 에서 호출할 용도)
	public ResponseEntity<?> getIngredientInFridge(String userSnsId, String token) {
		// fridge 의 ingredientId 받아오기
		List<Integer> ingredientIdList = getIngredientIdListFromFridge(userSnsId);
		// ingredientService 에서 getIngredientInfoList 의 .body.data 가져오기
		return getIngredientInfoList(ingredientIdList, token);
	}


	// fridgeDB 가서 suserId 로 ingredientId GET
	public List<Integer> getIngredientIdListFromFridge(String userSnsId) {
		log.debug("냉장고 속 재료 1개 ID 받아오기");
		return fridgeRepository.findIngredientIdListByUserSnsId(userSnsId);
	}


	// ingredientService 호출해서 ingredientIdList 넘겨주고 FridgeIngredientListRes 받아오기
	public ResponseEntity<?> getIngredientInfoList(List<Integer> ingredientIdList, String token) {
		log.debug("재료 리스트 정보 받아오기..");
		return ingredientServiceClient.getIngredientInfoList(ingredientIdList, token);
	}


}
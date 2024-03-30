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

	// sUserId 를 받은 다음에 ingredient info 반환해주는 함수 (controller 에서 호출할 용도)
	public List<Integer> getIngredientInFridge() {
		// header token 까서 snsId String 형태로 받아오기
		// DB에 유저 식별자 Integer 로 되어 있는데 varchar 로 바꾸기
		// dummy 값으로 일단 Integer 7073 으로 진행하기
		Integer sUserId = 7073;
		// fridge 의 ingredientId 받아오기
		return getIngredientIdListFromFridge(sUserId);
		// ingredientService 에서 getIngredientInfoList 의 .body.data 가져오기
		// ResponseEntity<SingleResult<?>> res = getIngredientInfoList(ingredientIdList);
		// return (FridgeIngredientListRes)Objects.requireNonNull(res.getBody()).getData();
	}



	// fridgeDB 가서 suserId 로 ingredientId 1개 GET
	public Integer getSingleIngredientIdFromFridge(Integer sUserId) {
		log.debug("냉장고 속 재료 1개 ID 받아오기");
		return fridgeRepository.findSingleIngredientIdBySuserId(sUserId);
	}


	// fridgeDB 가서 suserId 로 ingredientId 리스트 GET
	public List<Integer> getIngredientIdListFromFridge(Integer sUserId) {
		log.debug("냉장고 속 재료 List<Integer> 받아오기");
		return fridgeRepository.findIngredientIdListBySuserId(sUserId);
	}


	// fridgeDB 가서 suserId 로 ingredientId 1개 DELETE
	public Integer deleteSingleIngredientIdFromFridge(Integer sUserId) {
		log.debug("재료 한 개 제거하기");
		return fridgeRepository.findSingleIngredientIdBySuserId(sUserId);
	}


	// ingredientService 호출해서 ingredientId 넘겨주고 FridgeIngredientListRes 받아오기
	public ResponseEntity<?> getSingleIngredientInfo(Integer ingredientId) {
		log.debug("재료 1개 정보 받아오기");
		return ingredientServiceClient.getSingleIngredientInfo(ingredientId);
	}


	// ingredientService 호출해서 ingredientIdList 넘겨주고 FridgeIngredientListRes 받아오기
	public ResponseEntity<SingleResult<?>> getIngredientInfoList(List<Integer> ingredientIdList) {
		log.debug("재료 리스트 정보 받아오기..");
		return ingredientServiceClient.getIngredientInfoList(ingredientIdList);
	}


}
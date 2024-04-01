package com.ssafy.fridgeservice.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.fridgeservice.db.repository.FridgeRepository;
import com.ssafy.fridgeservice.dto.request.FridgeIngredientListReq;
import com.ssafy.fridgeservice.dto.response.FridgeIngredientListRes;
import com.ssafy.fridgeservice.dto.response.Ingredient;
import com.ssafy.fridgeservice.dto.response.IngredientId;
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
		log.debug("userSnsId:{}",userSnsId);
		log.debug("token:{}",token);
		// fridge 의 ingredientId 받아오기
		List<IngredientId> ingredientIdList = getIngredientIdListFromFridge(userSnsId);
		log.debug("ingredientIdList:{}",ingredientIdList);
		FridgeIngredientListReq req = FridgeIngredientListReq
										.builder()
										.ingredientIdList(ingredientIdList)
										.build();
		// ingredientService 에서 getIngredientInfoList 의 .body.data 가져오기
		return getIngredientInfoList(token, req);
	}


	// fridgeDB 가서 suserId 로 ingredientId GET
	public List<IngredientId> getIngredientIdListFromFridge(String userSnsId) {
		log.debug("냉장고 속 재료 1개 ID 받아오기");
		// return fridgeRepository.findIngredientIdListByUserSnsId(userSnsId);
		// return null;
		List<Integer> ingredientIdList = fridgeRepository.findAllByUserSnsId(userSnsId);
		return ingredientIdList.stream().map(
			integer -> IngredientId.builder()
				.ingredientId(integer)
				.build()
		).toList();
	}



	// ingredientService 호출해서 ingredientIdList 넘겨주고 FridgeIngredientListRes 받아오기
	public ResponseEntity<?> getIngredientInfoList(@RequestHeader("Authorization")String token, @RequestBody FridgeIngredientListReq ingredientIdList) {
		log.debug("재료 리스트 정보 받아오기..");
		return ingredientServiceClient.getIngredientInfoList(token, ingredientIdList);
	}


}
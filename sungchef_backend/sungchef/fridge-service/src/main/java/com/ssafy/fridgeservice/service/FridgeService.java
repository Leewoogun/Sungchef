package com.ssafy.fridgeservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.mapping.Array;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssafy.fridgeservice.db.entity.Fridge;
import com.ssafy.fridgeservice.db.repository.FridgeRepository;
import com.ssafy.fridgeservice.dto.request.FridgeIngredientListReq;
import com.ssafy.fridgeservice.dto.request.IngredientListReq;
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
		log.info("fridgeService");
		log.info("userSnsId:{}",userSnsId);
		log.info("token:{}",token);
		List<Fridge> fridgeList = fridgeRepository.findAllByUserSnsId(userSnsId);
		log.info("fridgeList:{}",fridgeList);
		List<Integer> ingredientIdList = new ArrayList<>();
			for (Fridge fridge : fridgeList) {
				int ingredientIdInt = fridge.getIngredientId();
				Integer ingredientIdInteger = (Integer)ingredientIdInt;
				ingredientIdList.add(ingredientIdInteger);
			}
		log.info("ingredientIdList:{}",ingredientIdList);
		IngredientListReq req = new IngredientListReq();
		req.setIngredientIdList(ingredientIdList);
		ResponseEntity<?> res = ingredientServiceClient.getIngredientInfoList(token, req);
		log.info("res:{}",res);
		return res;
	}

}
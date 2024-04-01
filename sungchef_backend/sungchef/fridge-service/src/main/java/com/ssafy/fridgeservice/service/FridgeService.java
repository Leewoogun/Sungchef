package com.ssafy.fridgeservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.fridgeservice.db.entity.Fridge;
import com.ssafy.fridgeservice.db.repository.FridgeRepository;
import com.ssafy.fridgeservice.dto.request.IngredientList;
import com.ssafy.fridgeservice.dto.request.IngredientListReq;
import com.ssafy.fridgeservice.dto.response.IngredientId;
import com.ssafy.fridgeservice.service.client.IngredientServiceClient;

import jakarta.transaction.Transactional;
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
	@Transactional
	public ResponseEntity<?> getIngredientInFridge(String userSnsId, String token) {
		Optional<List<Fridge>> fridgeList = fridgeRepository.findAllByUserSnsId(userSnsId);
		if (fridgeList.isPresent()) {
			List<Integer> ingredientIdList = new ArrayList<>();
			List<Fridge> fridgeListReal = fridgeList.get();
			for (Fridge fridge : fridgeListReal) {
				Integer ingredientIdInteger = fridge.getIngredientId();
				ingredientIdList.add(ingredientIdInteger);
			}
			IngredientListReq req = new IngredientListReq();
			req.setIngredientIdList(ingredientIdList);
			return ingredientServiceClient.getIngredientInfoList(token, req);
		} else {
			return responseService.NO_CONTENT();
		}
	}


	// 냉장고 속 재료 삭제
	@Transactional
	public boolean removeIngredients (String userSnsId, String token, IngredientList req) {
		List<IngredientId> removeIngredientIdList = req.getIngredientIdList();
		int intendedRemovalSize = removeIngredientIdList.size();
		for (IngredientId removeIngredientId : removeIngredientIdList) {
			int ingredientId = removeIngredientId.getIngredientId();
			int deletedIngredients = fridgeRepository.deleteByIngredientId(ingredientId);
			if (intendedRemovalSize == deletedIngredients) {
				return true;
			}
		}
		return false;
		// 냉장고에 없는 재료를 삭제하고자 할 경우 validation 필요 (추후 진행)
	}

}
package com.ssafy.fridgeservice.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.ssafy.fridgeservice.dto.response.IngredientIdListRes;
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

	
	/* getIngredientInFridge : 유저 냉장고 재료 정보 조회
	* @param : String userSnsId, String token
	* @return : ResponseEntity
	* */
	// 재사용 가능하도록 코드 분리하기
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
			// 냉장고에 아무것도 없는 유저라면 204 가 가도록 이렇게 return 문을 만들었는데
			// 왜 200 이 나온다고 할까 .. ㅠㅠ
			return responseService.NO_CONTENT();
		}
	}


	// 냉장고 속 재료 삭제
	// 리팩토링 - 재사용 가능하도록 함수 분리하기
	// 리팩토링 - 냉장고에 없는 재료를 삭제하고자 할 경우 명시적 Exception 반환 처리
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
	}


	// 냉장고 속 재료 등록
	// 리팩토링 - 재사용 가능하도록 함수 분리하기
	// 리팩토링 - 냉장고에 기존에 있던 재료일 경우 추가 로직 구현할 필요 없음
	@Transactional
	public boolean addIngredients (String userSnsId, String token, IngredientList req) {
		List<IngredientId> ingredientIdList = req.getIngredientIdList();
		Fridge newFridge = new Fridge();
		LocalDate today = LocalDate.now();
		DateTimeFormatter todayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String todayFridge = today.format(todayFormatter);
		for (IngredientId ingredientId : ingredientIdList) {
			int ingredientIdInt = ingredientId.getIngredientId();
			newFridge.setUserSnsId(userSnsId);
			newFridge.setIngredientId(ingredientIdInt);
			newFridge.setFridgeCreateDate(todayFridge);
			Fridge savedFridge = fridgeRepository.save(newFridge);
			return true;
		}
		return false;
	}


	// 유저 냉장고 속에 재료가 있는지 없는지 확인해보기
	@Transactional
	public IngredientIdListRes isExistIngredients(String userSnsId, IngredientListReq ingredientIdList) {
		List<Integer> beforeCheckIngredients = ingredientIdList.getIngredientIdList();
		log.info("beforeCheckIngredients:{}",beforeCheckIngredients);
		IngredientIdListRes ingredientIdListRes = new IngredientIdListRes();
		log.info("ingredientIdListRes:{}",ingredientIdListRes);
		List<Integer> afterCheckIngredients = new ArrayList<>();
		log.info("afterCheckIngredients:{}",afterCheckIngredients);
		Optional<List<Fridge>> optionalFridgeList = fridgeRepository.findAllByUserSnsId(userSnsId);
		log.info("optionalFridgeList:{}",optionalFridgeList.toString());
		if (optionalFridgeList.isEmpty()) {
			// 유저 냉장고에 아무것도 없음 -> 받아온 재료 전부를 부족한 재료로 반환
			ingredientIdListRes.setIngredientIdList(beforeCheckIngredients);
			log.info("ingredientIdListRes_emptyCase:{}",ingredientIdListRes);
			return ingredientIdListRes;
		} else {
			// 유저 냉장고에 들어있는 게 있음
			List<Fridge> fridgeList = optionalFridgeList.get();
			ArrayList<Integer> fridgeIngredientList = new ArrayList<>();
			// 냉장고 속 재료와 레시피 재료 id 비교하기
			for (Fridge fridge : fridgeList) {
				Integer ingredientId = (Integer) fridge.getIngredientId();
				fridgeIngredientList.add(ingredientId);
			}
			// 냉장고에 없는 경우 afterCheckIngredients 에 담기
			for (Integer beforeCheckIngredient : beforeCheckIngredients) {
				if (!fridgeIngredientList.contains(beforeCheckIngredient)) {
					afterCheckIngredients.add(beforeCheckIngredient);
				}
			}
			ingredientIdListRes.setIngredientIdList(afterCheckIngredients);
			return ingredientIdListRes;
		}
	}
}
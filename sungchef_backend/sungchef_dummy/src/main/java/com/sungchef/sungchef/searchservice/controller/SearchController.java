package com.sungchef.sungchef.searchservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.searchservice.dto.response.SearchFood;
import com.sungchef.sungchef.searchservice.dto.response.SearchFoodListRes;
import com.sungchef.sungchef.searchservice.dto.response.SearchIngredient;
import com.sungchef.sungchef.searchservice.dto.response.SearchIngredientListRes;
import com.sungchef.sungchef.util.exception.FoodNotFoundException;
import com.sungchef.sungchef.util.exception.IngredientNotFoundException;
import com.sungchef.sungchef.util.exception.RecipeNotFoundException;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
	private final ResponseService responseService;
	@GetMapping("/ingredient/{ingredientName}")
	public ResponseEntity<?> searchIngredient(@PathVariable("ingredientName") final String ingredientName) {

		List<SearchIngredient> searchIngredientList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			searchIngredientList.add(
				SearchIngredient.builder()
					.ingredientId(10 + i)
					.ingredientName(i + "김치가루")
					.build()
			);
		}

		SearchIngredientListRes res = SearchIngredientListRes.builder()
			.ingredientList(searchIngredientList)
			.build();

		try {
			log.debug("/search/ingredient/{ingredientName} : {}", ingredientName);
			return ResponseEntity.ok(
					responseService.getSuccessSingleResult(
							res
							, "재료 이름 조회 성공"
					)
			);
		} catch (IngredientNotFoundException e) {
			return responseService.NO_CONTENT();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/food/{foodName}")
	public ResponseEntity<?> searchFood(@PathVariable("foodName") final String foodName) {

		List<SearchFood> searchFoodList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			searchFoodList.add(
				SearchFood.builder()
					.foodId(10 + i)
					.foodName(i + "김치찌개")
					.build()
			);
		}

		SearchFoodListRes res = SearchFoodListRes.builder()
			.foodList(searchFoodList)
			.build();
		try {
			log.debug("/search/food/{foodName} : {}", foodName);
			return ResponseEntity.ok(
					responseService.getSuccessSingleResult(
							res
							, "음식 이름 조회 성공")
			);
		} catch (FoodNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}
}

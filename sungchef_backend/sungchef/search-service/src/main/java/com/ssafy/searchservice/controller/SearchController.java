package com.ssafy.searchservice.controller;

import com.ssafy.searchservice.db.entity.Food;
import com.ssafy.searchservice.db.entity.Ingredient;
import com.ssafy.searchservice.dto.response.SearchFood;
import com.ssafy.searchservice.dto.response.SearchFoodListRes;
import com.ssafy.searchservice.dto.response.SearchIngredient;
import com.ssafy.searchservice.dto.response.SearchIngredientListRes;
import com.ssafy.searchservice.exception.exception.NoContentException;
import com.ssafy.searchservice.service.ResponseService;
import com.ssafy.searchservice.service.SearchService;
import com.ssafy.searchservice.exception.exception.FoodNotFoundException;
import com.ssafy.searchservice.exception.exception.IngredientNotFoundException;
import com.ssafy.searchservice.util.sungchefEnum.IngredientType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
	private final ResponseService responseService;
	private final SearchService searchService;
	@GetMapping("/ingredient/{ingredientName}")
	public ResponseEntity<?> searchIngredient(@PathVariable("ingredientName") final String ingredientName) {

		log.debug("/search/ingredient/{ingredientName} : {}", ingredientName);

		List<Ingredient> ingredientList = searchService.getIngredient(ingredientName);
		if (ingredientList.isEmpty()) throw new IngredientNotFoundException("재료가 존재하지 않음");

		List<SearchIngredient> searchIngredientList = ingredientList.stream()
			.map(
				ingredient -> SearchIngredient.builder()
					.ingredientType(IngredientType.values()[ingredient.getIngredientTypeId()])
					.ingredientId(ingredient.getIngredientId())
					.ingredientName(ingredient.getIngredientName())
					.build()
			).toList();

		return ResponseEntity.ok(
			responseService.getSuccessSingleResult(
				searchIngredientList
				, "재료 이름 조회 성공"
			)
		);
	}

	@GetMapping("/food/{foodName}")
	public ResponseEntity<?> searchFood(@PathVariable("foodName") final String foodName) {

		List<Food> foodList = searchService.getFood(foodName);
		if (foodList.isEmpty()) throw new FoodNotFoundException("음식이 존재하지 않음");

		List<SearchFood> searchFoodList = foodList.stream()
			.map(food -> SearchFood.builder()
				.foodId(food.getFoodId())
				.foodName(food.getFoodName())
				.build()
			)
			.toList();
		return ResponseEntity.ok(
			responseService.getSuccessSingleResult(
				searchFoodList
				, "음식 이름 조회 성공"
			)
		);
	}
}

package com.ssafy.searchservice.controller;

import com.ssafy.searchservice.dto.response.SearchFood;
import com.ssafy.searchservice.dto.response.SearchFoodListRes;
import com.ssafy.searchservice.dto.response.SearchIngredient;
import com.ssafy.searchservice.dto.response.SearchIngredientListRes;
import com.ssafy.searchservice.service.JwtService;
import com.ssafy.searchservice.service.ResponseService;
import com.ssafy.searchservice.util.exception.FoodNotFoundException;
import com.ssafy.searchservice.util.exception.IngredientNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
	private final ResponseService responseService;
	private final JwtService jwtService;
	@GetMapping("/ingredient/{ingredientName}")
	public ResponseEntity<?> searchIngredient(HttpServletRequest request, @PathVariable("ingredientName") final String ingredientName) {
		log.info(jwtService.getUserSnsId(request));
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

package com.sungchef.sungchef.recommendservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sungchef.sungchef.recommendservice.dto.response.Food;
import com.sungchef.sungchef.recommendservice.dto.response.Recipe;
import com.sungchef.sungchef.recommendservice.dto.response.RecommendFood;
import com.sungchef.sungchef.recommendservice.dto.response.RecommendRecipe;
import com.sungchef.sungchef.recommendservice.dto.response.RecommendRes;
import com.sungchef.sungchef.util.responsehelper.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

	private final ResponseService responseService;

	@GetMapping("")
	public ResponseEntity<?> recommendFoodAndRecipe() {

		RecommendRes recommendRes = new RecommendRes();

		List<RecommendFood> recommendFoodList = recommendRes.getRecommendFoodList();
		List<RecommendRecipe> recommendRecipeList = recommendRes.getRecommendRecipeList();

		for (RecommendRecipe recommendRecipe : recommendRecipeList) {

			List<Recipe> recipeList = recommendRecipe.getRecipeList();

			switch (recommendRecipe.getRecommendRecipeType()) {

				case SUNG -> {
					recipeList.add(
						Recipe.builder()
							.recipeId(10)
							.recipeImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.recipeName("국가권력급 김치찌개")
							.build()
					);
				}

				case FRIDGE -> {
					recipeList.add(
						Recipe.builder()
							.recipeId(11)
							.recipeImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.recipeName("국가권력급 부대찌개")
							.build()
					);
				}

				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}

		}

		for (RecommendFood recommendFood : recommendFoodList) {

			List<Food> foodList = recommendFood.getFoodList();

			switch (recommendFood.getRecommendFoodType()) {
				case AGE -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("김치찌개")
							.build()
					);
				}
				case GENDER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("부대찌개")
							.build()
					);
				}
				case WEATHER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("김치전")
							.build()
					);
				}

				case SIMILAR_USER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("오이김치")
							.build()
					);
				}

				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}
		}
		try {
			return ResponseEntity.ok(responseService.getSuccessSingleResult(recommendRes, "추천 목록 조회 성공"));
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/emptyfridge")
	public ResponseEntity<?> recommendFood() {

		RecommendRes recommendRes = new RecommendRes();

		List<RecommendFood> recommendFoodList = recommendRes.getRecommendFoodList();

		for (RecommendFood recommendFood : recommendFoodList) {

			List<Food> foodList = recommendFood.getFoodList();

			switch (recommendFood.getRecommendFoodType()) {
				case AGE -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("김치찌개")
							.build()
					);
				}
				case GENDER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("부대찌개")
							.build()
					);
				}
				case WEATHER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("김치전")
							.build()
					);
				}

				case SIMILAR_USER -> {
					foodList.add(
						Food.builder()
							.foodImage(
								"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
							.foodName("오이김치")
							.build()
					);
				}

				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}
		}
		try {
			return ResponseEntity.ok(responseService.getSuccessSingleResult(recommendRes, "추천 목록 조회 성공"));
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

}

package com.ssafy.recommendservice.controller;

import com.ssafy.recommendservice.response.*;
import com.ssafy.recommendservice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
				case FRIDGE -> {
					for (int i = 0; i < 10; i++) {
						recipeList.add(
							Recipe.builder()
								.recipeId(11 + i)
								.recipeImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.recipeName("국가권력급 부대찌개" + i)
								.build()
						);
					}
				}

				default -> {
					return responseService.INTERNAL_SERVER_ERROR();
				}
			}

		}

		for (RecommendFood recommendFood : recommendFoodList) {

			List<Food> foodList = recommendFood.getFoodList();

			switch (recommendFood.getRecommendFoodType()) {

				case SUNG -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("돼지고기김치찌개" + i)
								.build()
						);
					}
				}
				case AGE -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("김치찌개" + i)
								.build()
						);
					}
				}
				case GENDER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("부대찌개" + i)
								.build()
						);
					}
				}
				case WEATHER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("김치전" + i)
								.build()
						);
					}
				}

				case SIMILAR_USER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("오이김치" + i)
								.build()
						);
					}
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
				case SUNG -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("돼지고기김치찌개" + i)
								.build()
						);
					}
				}
				case AGE -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("김치찌개" + i)
								.build()
						);
					}
				}
				case GENDER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("부대찌개" + i)
								.build()
						);
					}
				}
				case WEATHER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("김치전" + i)
								.build()
						);
					}
				}

				case SIMILAR_USER -> {
					for (int i = 0; i < 10; i++) {
						foodList.add(
							Food.builder()
								.foodImage(
									"https://static.wtable.co.kr/image-resize/production/service/recipe/291/4x3/a2421dff-e56c-40bd-8b40-06a91fc000a9.jpg")
								.foodName("오이김치" + i)
								.build()
						);
					}
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

package com.ssafy.recommendservice.controller;

import com.ssafy.recommendservice.response.*;
import com.ssafy.recommendservice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

	private final ResponseService responseService;

	@GetMapping("")
	public ResponseEntity<?> recommendFoodAndRecipe() {
		String[] arr1 = new String[]{
				"https://recipe1.ezmember.co.kr/cache/recipe/2015/08/17/bcc9e06e76622b60f44ad34eceaae57c1.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2021/04/19/974909172f076439ce8410f66bbb63611.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2022/11/12/2f6d63fba7a1bdebc109b9c119c9e3d71.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2016/10/19/90feafe373b731908ae65ccd458b621c1.jpg",
				"",
				"https://recipe1.ezmember.co.kr/cache/recipe/2018/11/15/8ebb2cd440d337d0ba9d6825754449d21.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2017/09/19/c0a32e877b442948068d78ab631410941.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2019/07/19/30f3262b3ea8adf53132e2fc577bfc301.jpg",
				"",
				"https://recipe1.ezmember.co.kr/cache/recipe/2018/10/14/9a290b5b8fd29b4ca5b748158d32ae231.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2019/01/13/0cd44fd99587ded681ff60500ac2cf251.jpg",
				"https://recipe1.ezmember.co.kr/cache/recipe/2015/08/12/4a35d848e786221151ce7f3d79c7ccca1.jpg"
		};
		String[] arr2= new String[]{
				"깍두기 쉽게 담그는법/새우젓, 찹쌀풀없이 담그기",
				"백종원 삼겹살 꽈리고추 볶음",
				"우리집 별미 오징어 찐만두",
				"사과랑 닭날개의 만남^-^ ",
				"",
				"시금치무침 - 초간단 반찬만들기",
				"닭요리-얼큰하고 담백한 닭개장 만드는 법",
				"스팸,감자 고추장찌개",
				"",
				"자취생요리로 좋은 간단하고 맛있는 애호박볶음",
				"도토리묵사발 :)한겨울 따끈한 도토리묵사발",
				"절대 후회없는 깻잎김치 황금레시피^^"
		};




		RecommendRes recommendRes = new RecommendRes();

		List<RecommendFood> recommendFoodList = recommendRes.getRecommendFoodList();
		List<RecommendRecipe> recommendRecipeList = recommendRes.getRecommendRecipeList();

		for (RecommendRecipe recommendRecipe : recommendRecipeList) {

			List<Recipe> recipeList = recommendRecipe.getRecipeList();

			switch (recommendRecipe.getRecommendRecipeType()) {
				case FRIDGE -> {
					for (int i = 0; i < 10; i++) {
						if (i == 4 || i ==8) continue;
						recipeList.add(
							Recipe.builder()
								.recipeId(1 + i)
								.recipeImage(
										arr1[i])
								.recipeName(arr2[i])
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

package com.ssafy.recommendservice.service;

import com.ssafy.recommendservice.dto.request.FoodIdListReq;
import com.ssafy.recommendservice.dto.request.RecipeIdListReq;
import com.ssafy.recommendservice.dto.response.*;
import com.ssafy.recommendservice.service.client.RecipeServiceClient;
import com.ssafy.recommendservice.util.result.SingleResult;
import com.ssafy.recommendservice.util.sungchefEnum.RecommendRecipeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {

	@Value("${django.url}")
	private String djangoUrl;
	private final ResponseService responseService;
	private final RecipeServiceClient recipeServiceClient;
	public ResponseEntity<?> test2() {
				RecommendList response = WebClient.create("http://localhost:8001")
				.get()
				.uri("/similar/6")
				.retrieve()
				.bodyToMono(RecommendList.class)
				.block();
		List<Integer> recipeIdList = response.getRecommend_list();

		RecipeIdListReq req = RecipeIdListReq.builder()
				.recipeIdList(recipeIdList)
				.build();
		String token = "asd";
		ResponseEntity<SingleResult<RecommendRecipeTest>> res = recipeServiceClient.getRecipeList(req, token);
		RecommendRecipeTest recommendRecipeListRes = res.getBody().getData();
		return ResponseEntity.ok(responseService.getSuccessSingleResult(
				recommendRecipeListRes
				, "레시피 조회 성공"));
	}

	public ResponseEntity<?> test(String token, String userSnsId) {

		RecommendIdList response = WebClient.create(djangoUrl)
				.get()
				.uri("/item/"+userSnsId+"/2666")
				.retrieve()
				.bodyToMono(RecommendIdList.class)
				.block();
		RecommendIdList recommendIdList = response;


		FoodIdListReq foodIdListReq = FoodIdListReq.builder()
				.foodIdList(recommendIdList.getFoodIdList())
				.build();

		RecipeIdListReq recipeIdListReq = RecipeIdListReq.builder()
				.recipeIdList(recommendIdList.getRecipeIdList())
				.build();

		ResponseEntity<SingleResult<RecommendFoodTest>> foodRes = recipeServiceClient.getFoodList(foodIdListReq, token);
		ResponseEntity<SingleResult<RecommendRecipeTest>> recipeRes = recipeServiceClient.getRecipeList(recipeIdListReq, token);


		RecommendRecipe fridgeRecommendList = RecommendRecipe.builder()
				.recommendRecipeType("FRIDGE")
				.recipeList(recipeRes.getBody().getData().getRecipeList())
				.build();

		RecommendFood sungRecommendList = RecommendFood.builder()
				.recommendFoodType("SUNG")
				.foodList(foodRes.getBody().getData().getFoodList().subList(0,10))
				.build();

		RecommendFood userRecommendList = RecommendFood.builder()
				.recommendFoodType("SIMILAR_USER")
				.foodList(foodRes.getBody().getData().getFoodList().subList(10,20))
				.build();

		RecommendFood genderRecommendList = RecommendFood.builder()
				.recommendFoodType("GENDER")
				.foodList(foodRes.getBody().getData().getFoodList().subList(20,30))
				.build();

		RecommendFood ageRecommendList = RecommendFood.builder()
				.recommendFoodType("AGE")
				.foodList(foodRes.getBody().getData().getFoodList().subList(30,40))
				.build();

		RecommendFood weatherRecommendList = RecommendFood.builder()
				.recommendFoodType("WEATHER")
				.foodList(foodRes.getBody().getData().getFoodList().subList(40,50))
				.build();

		List<RecommendRecipe> recommendRecipeList = Arrays.asList(fridgeRecommendList);
		List<RecommendFood> recommendFoodList = Arrays.asList(sungRecommendList, userRecommendList, genderRecommendList, ageRecommendList, weatherRecommendList);

		RecommendRes res = RecommendRes.builder()
				.recommendRecipeList(recommendRecipeList)
				.recommendFoodList(recommendFoodList)
				.build();


		RecommendFoodTest recommendFoodListRes = foodRes.getBody().getData();
		return ResponseEntity.ok(responseService.getSuccessSingleResult(
				res
				, "레시피 조회 성공"));
	}
}

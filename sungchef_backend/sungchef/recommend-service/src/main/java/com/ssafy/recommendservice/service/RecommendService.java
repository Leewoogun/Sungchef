package com.ssafy.recommendservice.service;

import com.ssafy.recommendservice.dto.request.FoodIdListReq;
import com.ssafy.recommendservice.dto.request.RecipeIdListReq;
import com.ssafy.recommendservice.dto.response.*;
import com.ssafy.recommendservice.service.client.RecipeServiceClient;
import com.ssafy.recommendservice.util.result.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {

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
		ResponseEntity<SingleResult<RecommendRecipeTest>> res = recipeServiceClient.getRecipeList(req);
		RecommendRecipeTest recommendRecipeListRes = res.getBody().getData();
		return ResponseEntity.ok(responseService.getSuccessSingleResult(
				recommendRecipeListRes
				, "레시피 조회 성공"));
	}

	public ResponseEntity<?> test() {

		RecommendList response = WebClient.create("http://localhost:8001")
				.get()
				.uri("/similar/10/")
				.retrieve()
				.bodyToMono(RecommendList.class)
				.block();
		List<Integer> recipeIdList = response.getRecommend_list();

		FoodIdListReq req = FoodIdListReq.builder()
				.foodIdList(recipeIdList)
				.build();
		ResponseEntity<SingleResult<RecommendFoodTest>> res = recipeServiceClient.getFoodList(req);
		RecommendFoodTest recommendFoodListRes = res.getBody().getData();
		return ResponseEntity.ok(responseService.getSuccessSingleResult(
				recommendFoodListRes
				, "레시피 조회 성공"));
	}
}

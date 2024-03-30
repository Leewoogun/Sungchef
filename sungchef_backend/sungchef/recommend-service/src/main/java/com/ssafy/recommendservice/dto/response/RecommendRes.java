package com.ssafy.recommendservice.dto.response;

import com.ssafy.recommendservice.util.sungchefEnum.RecommendFoodType;
import com.ssafy.recommendservice.util.sungchefEnum.RecommendRecipeType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendRes {

	List<RecommendFood> recommendFoodList;
	List<RecommendRecipe> recommendRecipeList;

	public RecommendRes() {

		recommendFoodList = new ArrayList<>();
		for (int i = 0; i < RecommendFoodType.values().length; i++) {
			recommendFoodList.add(
				new RecommendFood(
					RecommendFoodType.values()[i]
				)
			);
		}

		recommendRecipeList = new ArrayList<>();
		for (int i = 0; i < RecommendRecipeType.values().length; i++) {
			recommendRecipeList.add(
				new RecommendRecipe(
					RecommendRecipeType.values()[i]
				)
			);
		}

	}
}

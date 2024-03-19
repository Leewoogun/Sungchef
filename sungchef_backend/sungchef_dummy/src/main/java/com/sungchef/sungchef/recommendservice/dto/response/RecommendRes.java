package com.sungchef.sungchef.recommendservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.RecommendFoodType;
import com.sungchef.sungchef.util.sungchefEnum.RecommendRecipeType;
import lombok.Data;

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

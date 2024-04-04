package com.sungchef.sungchef.recommendservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.RecommendRecipeType;

import lombok.Data;

@Data
public class RecommendRecipe {
	RecommendRecipeType recommendRecipeType;
	List<Recipe> recipeList;

	public RecommendRecipe(RecommendRecipeType _recommendRecipeType) {
		recommendRecipeType = _recommendRecipeType;
		recipeList = new ArrayList<>();
	}
}

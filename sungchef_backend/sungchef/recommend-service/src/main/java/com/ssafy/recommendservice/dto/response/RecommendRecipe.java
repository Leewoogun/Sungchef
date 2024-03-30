package com.ssafy.recommendservice.dto.response;

import com.ssafy.recommendservice.util.sungchefEnum.RecommendRecipeType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendRecipe {
	RecommendRecipeType recommendRecipeType;
	List<Recipe> recipeList;

	public RecommendRecipe(RecommendRecipeType _recommendRecipeType) {
		recommendRecipeType = _recommendRecipeType;
		recipeList = new ArrayList<>();
	}
}

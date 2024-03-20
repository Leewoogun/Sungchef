package com.sungchef.sungchef.recipeservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RecipeDetailStepRes {
	int recipeId;
	List<RecipeDetail> recipeDetailList;

	public RecipeDetailStepRes(int _recipeId) {
		recipeId = _recipeId;
		recipeDetailList = new ArrayList<>();
	}

}

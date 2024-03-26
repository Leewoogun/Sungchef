package com.ssafy.recipeservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeDetailStepRes {
	int recipeId;
	List<RecipeDetail> recipeDetailList;

	public RecipeDetailStepRes(int _recipeId) {
		recipeId = _recipeId;
		recipeDetailList = new ArrayList<>();
	}

}

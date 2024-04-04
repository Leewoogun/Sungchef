package com.ssafy.recipeservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeDetailStepRes {
	int recipeId;
	String recipeName;
	List<RecipeStep> recipeDetailList;

	public RecipeDetailStepRes(int _recipeId, String _recipeName) {
		recipeId = _recipeId;
		recipeName = _recipeName;
		recipeDetailList = new ArrayList<>();
	}

}

package com.sungchef.sungchef.recipeservice.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RecipeDetailStepRes {
	int recipeId;
	List<RecipeDetail> recipeDetailList;
	public RecipeDetailStepRes(int recipeId) {
		recipeDetailList = new ArrayList<>();
	}

}

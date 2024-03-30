package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeIngredientInfo {
	IngredientType recipeIngredientType;
	List<RecipeIngredient> recipeIngredientList;

	public RecipeIngredientInfo(IngredientType _recipeIngredientType) {
		recipeIngredientList = new ArrayList<>();
		recipeIngredientType = _recipeIngredientType;
	}
}

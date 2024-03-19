package com.sungchef.sungchef.recipeservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Builder;
import lombok.Data;

@Data
public class RecipeIngredientInfo {
	IngredientType recipeIngredientType;
	List<RecipeIngredient> recipeIngredientList;

	public RecipeIngredientInfo(IngredientType _recipeIngredientType) {
		recipeIngredientList = new ArrayList<>();
		recipeIngredientType = _recipeIngredientType;
	}
}

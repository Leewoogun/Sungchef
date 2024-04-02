package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeIngredientInfo {
	IngredientType recipeIngredientType;
	List<RecipeIngredientDTO> recipeIngredientDTOList;

	public RecipeIngredientInfo(IngredientType _recipeIngredientType) {
		recipeIngredientDTOList = new ArrayList<>();
		recipeIngredientType = _recipeIngredientType;
	}
}

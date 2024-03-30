package com.ssafy.recipeservice.dto.response;

import com.ssafy.recipeservice.util.sungchefEnum.IngredientType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RecipeIngredientInfo {
	IngredientType recipeIngredientType;
	List<RecipeIngredient> recipeIngredientList;

//	public RecipeIngredientInfo(IngredientType _recipeIngredientType) {
//		recipeIngredientList = new ArrayList<>();
//		recipeIngredientType = _recipeIngredientType;
//	}
}

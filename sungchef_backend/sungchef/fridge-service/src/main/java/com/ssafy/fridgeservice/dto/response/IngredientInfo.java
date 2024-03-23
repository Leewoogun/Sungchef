package com.ssafy.fridgeservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.fridgeservice.util.sungchefEnum.IngredientType;

import lombok.Data;

@Data
public class IngredientInfo {
	IngredientType ingredientType;
	List<Ingredient> ingredientList;

	public IngredientInfo(IngredientType _ingredientType) {
		ingredientType = _ingredientType;
		ingredientList = new ArrayList<>();
	}
}

package com.ssafy.searchservice.dto.response;

import com.ssafy.searchservice.db.entity.Ingredient;
import com.ssafy.searchservice.util.sungchefEnum.IngredientType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchIngredient {
	int ingredientId;
	IngredientType ingredientType;
	String ingredientName;
}

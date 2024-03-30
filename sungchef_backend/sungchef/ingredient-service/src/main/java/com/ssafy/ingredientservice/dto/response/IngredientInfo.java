package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class IngredientInfo {
	IngredientType ingredientType;
	List<IngredientRes> ingredientResList;

	public IngredientInfo(IngredientType _recipeIngredientType) {
		ingredientResList = new ArrayList<>();
		ingredientType = _recipeIngredientType;
	}
}

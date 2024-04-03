package com.ssafy.ingredientservice.db.entity.client;

import java.util.List;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;

public record ClientIngredientInfo(
	IngredientType ingredientType,
	List<ClientIngredientRes> ingredientResList
)
{

}

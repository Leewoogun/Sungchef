package com.ssafy.ingredientservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecipeIngredient {
	int recipeIngredientId;
	String recipeIngredientName;
	String recipeIngredientVolume;
}

package com.sungchef.sungchef.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeDetail {
	int recipeDetailStep;
	String recipeDetailImage;
	String recipeDetailDescription;
}

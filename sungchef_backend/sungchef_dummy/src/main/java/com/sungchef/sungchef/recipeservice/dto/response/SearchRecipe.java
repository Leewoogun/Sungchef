package com.sungchef.sungchef.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchRecipe {
	int recipeId;
	String recipeName;
	String recipeImage;
	String recipeCookingTime;
	String recipeVolume;
	int recipeVisitCount;
	boolean isBookmark;
}

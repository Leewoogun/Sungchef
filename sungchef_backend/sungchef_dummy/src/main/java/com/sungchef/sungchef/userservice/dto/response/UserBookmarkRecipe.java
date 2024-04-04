package com.sungchef.sungchef.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserBookmarkRecipe {

	int recipeId;
	String recipeImage;
}
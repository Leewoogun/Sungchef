package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserMakeRecipe {
	String makeRecipeName;
	String makeRecipeImage;
	String makeRecipeCreateDate;
	String makeRecipeReview;
}

package com.ssafy.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserMakeRecipe {
	String makeRecipeName;
	String makeRecipeImage;
	String makeRecipeCreateDate;
	String makeRecipeReview;
}

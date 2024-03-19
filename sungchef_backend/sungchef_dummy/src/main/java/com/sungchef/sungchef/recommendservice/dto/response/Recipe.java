package com.sungchef.sungchef.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipe {
	int recipeId;
	String recipeImage;
	String recipeName;

}

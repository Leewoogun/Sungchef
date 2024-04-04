package com.sungchef.sungchef.recipeservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchRecipeListRes {
	List<SearchRecipe> recipeList;
}

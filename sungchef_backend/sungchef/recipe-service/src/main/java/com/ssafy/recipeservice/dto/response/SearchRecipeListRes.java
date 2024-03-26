package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SearchRecipeListRes {
	List<SearchRecipe> recipeList;
}

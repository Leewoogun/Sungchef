package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendRecipeListRes {
    List<RecommendRecipe> recipeList;
}

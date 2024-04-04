package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendRecipe {
    Integer recipeId;
    String recipeName;
    String recipeImage;
}

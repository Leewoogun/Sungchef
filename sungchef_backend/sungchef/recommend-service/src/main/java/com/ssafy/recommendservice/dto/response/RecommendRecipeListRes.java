package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class RecommendRecipeListRes {
    List<Recipe> recipeList;
}

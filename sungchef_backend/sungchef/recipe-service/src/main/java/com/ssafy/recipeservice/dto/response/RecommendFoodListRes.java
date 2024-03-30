package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RecommendFoodListRes {
    List<RecommendFood> foodList;
}

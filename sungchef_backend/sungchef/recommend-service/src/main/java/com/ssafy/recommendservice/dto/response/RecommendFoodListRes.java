package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecommendFoodListRes {
    List<Food> foodList;
}

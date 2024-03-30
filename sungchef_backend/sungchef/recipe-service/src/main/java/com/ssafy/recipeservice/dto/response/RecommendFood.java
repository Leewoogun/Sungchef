package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendFood {
    String foodName;
    String foodImage;
}

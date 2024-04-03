package com.ssafy.recommendservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RecommendIdList {
	List<Integer> recipeIdList;
	List<Integer> foodIdList;
}

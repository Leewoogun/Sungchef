package com.ssafy.searchservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SearchFoodListRes {
	List<SearchFood> foodList;
}

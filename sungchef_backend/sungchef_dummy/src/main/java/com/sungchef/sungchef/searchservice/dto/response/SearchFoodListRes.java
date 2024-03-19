package com.sungchef.sungchef.searchservice.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchFoodListRes {
	List<SearchFood> foodList;
}

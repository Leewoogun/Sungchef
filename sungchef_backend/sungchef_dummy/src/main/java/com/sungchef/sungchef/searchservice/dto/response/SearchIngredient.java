package com.sungchef.sungchef.searchservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchIngredient {
	int ingredientId;
	String ingredientName;
}

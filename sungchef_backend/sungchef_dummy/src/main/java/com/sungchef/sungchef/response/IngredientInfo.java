package com.sungchef.sungchef.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IngredientInfo {
	String ingredientName;
	int ingredientId;
}

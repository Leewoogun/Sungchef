package com.sungchef.sungchef.util.commondto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ingredient {
	String ingredientName;
	int ingredientId;
}

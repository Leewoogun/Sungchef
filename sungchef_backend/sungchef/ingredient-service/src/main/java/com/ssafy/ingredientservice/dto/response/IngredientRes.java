package com.ssafy.ingredientservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ingredient {
	String ingredientName;
	int ingredientId;
}

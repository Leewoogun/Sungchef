package com.ssafy.userservice.dto.response.fridge;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ingredient {
	String ingredientName;
	int ingredientId;
}

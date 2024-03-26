package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IngredientInfo {
	IngredientType ingredientType;
	List<Ingredient> ingredientList;
}

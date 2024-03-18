package com.sungchef.sungchef.ingredientservice.dto.response;

import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConvertProductInfo {
	IngredientType ingredientType;
	List<ConvertProduct> convertProductList;
}

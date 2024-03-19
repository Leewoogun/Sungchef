package com.sungchef.sungchef.ingredientservice.dto.response;

import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.ConvertIngredientType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConvertProductInfo {
	ConvertIngredientType ingredientType;
	List<ConvertProduct> convertProductList;
}

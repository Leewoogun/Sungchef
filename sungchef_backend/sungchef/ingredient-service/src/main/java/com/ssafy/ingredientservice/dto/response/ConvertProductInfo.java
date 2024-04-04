package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.ConvertIngredientType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ConvertProductInfo {
	ConvertIngredientType ingredientType;
	List<ConvertProduct> convertProductList;
}

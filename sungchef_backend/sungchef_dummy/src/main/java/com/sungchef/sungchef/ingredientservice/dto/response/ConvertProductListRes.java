package com.sungchef.sungchef.ingredientservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.ConvertIngredientType;

import lombok.Data;

@Data
public class ConvertProductListRes {
	List<ConvertProductInfo> convertProductList;

	public ConvertProductListRes() {
		convertProductList = new ArrayList<>();
		for (int i = 0; i < ConvertIngredientType.values().length; i++) {
			convertProductList.add(
				ConvertProductInfo.builder()
					.ingredientType(ConvertIngredientType.values()[i])
					.convertProductList(new ArrayList<>())
					.build()
			);
		}
	}
}

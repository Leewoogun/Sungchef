package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.ConvertIngredientType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

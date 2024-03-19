package com.sungchef.sungchef.fridgeservice.dto.response;

import java.util.ArrayList;
import java.util.List;
import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Data;

@Data
public class FridgeIngredientListRes {
	List<IngredientInfo> ingredientInfoList;
	public FridgeIngredientListRes() {
		ingredientInfoList = new ArrayList<>();
		for (int i = 0; i < IngredientType.values().length; i++) {
			ingredientInfoList.add(
				new IngredientInfo(IngredientType.values()[i])
			);
		}
	}
}

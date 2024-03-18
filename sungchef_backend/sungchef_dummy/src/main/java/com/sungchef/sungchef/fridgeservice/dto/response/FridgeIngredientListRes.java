package com.sungchef.sungchef.fridgeservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.commondto.IngredientInfo;
import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Data;

@Data
public class FridgeIngredientListRes {
	List<IngredientInfo> fridgeList;
	public FridgeIngredientListRes() {
		fridgeList = new ArrayList<>();
		for (int i = 0; i < IngredientType.values().length; i++) {
			fridgeList.add(IngredientInfo.builder()
					.ingredientType(IngredientType.values()[i])
					.ingredientInfoList(new ArrayList<>())
					.build());
		}
	}
}

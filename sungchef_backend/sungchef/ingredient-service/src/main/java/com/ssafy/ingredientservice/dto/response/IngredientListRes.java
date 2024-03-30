package com.ssafy.ingredientservice.dto.response;

import com.ssafy.ingredientservice.util.sungchefEnum.IngredientType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IngredientListRes {
	List<IngredientInfo> ingredientInfoList;
	public IngredientListRes() {
		ingredientInfoList = new ArrayList<>();
		for (int i = 0; i < IngredientType.values().length; i++) {
			ingredientInfoList.add(
				new IngredientInfo(
					IngredientType.values()[i]
				)
			);
		}
	}
}

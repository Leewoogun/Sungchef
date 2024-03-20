package com.sungchef.sungchef.ingredientservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Data;

@Data
public class RecipeIngredientListRes {

	int recipeId;
	List<RecipeIngredientInfo> recipeIngredientInfoList;

	public RecipeIngredientListRes() {
		recipeIngredientInfoList = new ArrayList<>();
		for (int i = 0; i < IngredientType.values().length; i++) {
			recipeIngredientInfoList.add(
				new RecipeIngredientInfo(
					IngredientType.values()[i]
				)
			);
		}
	}
}

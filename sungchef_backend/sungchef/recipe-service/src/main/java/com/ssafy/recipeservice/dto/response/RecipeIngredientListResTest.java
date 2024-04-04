package com.ssafy.recipeservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeIngredientListResTest {

	int recipeId;
	List<RecipeIngredientInfo> recipeIngredientInfoList;

//	public RecipeIngredientListRes() {
//		recipeIngredientInfoList = new ArrayList<>();
//		for (int i = 0; i < IngredientType.values().length; i++) {
//			recipeIngredientInfoList.add(
//				new RecipeIngredientInfo(
//					IngredientType.values()[i]
//				)
//			);
//		}
//	}
}

package com.sungchef.sungchef.recipeservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeDetailRes {
	int recipeId;
	String recipeName;
	String recipeDescription;
	String recipeImage;
	String recipeCookingTime;
	String recipeVolume;
	List<RecipeIngredientInfo> recipeIngredientInfoList;
	List<RecipeDetail> recipeDetailList;

	/**
	 * 내부의 리스트들을 초기화하는 함수
	 */
	public void initRecipeDetailResList() {
		recipeIngredientInfoList = new ArrayList<>();
		for (int i = 0; i < IngredientType.values().length; i++) {
			recipeIngredientInfoList.add(
				new RecipeIngredientInfo(
					IngredientType.values()[i]
				)
			);
		}
		recipeDetailList = new ArrayList<>();
	}

}

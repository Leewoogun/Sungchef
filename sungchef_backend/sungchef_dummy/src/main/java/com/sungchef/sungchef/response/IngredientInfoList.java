package com.sungchef.sungchef.response;

import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Builder;

@Builder
public class IngredientInfoList {
	IngredientType type;
	List<IngredientInfo> fridgeList;
}

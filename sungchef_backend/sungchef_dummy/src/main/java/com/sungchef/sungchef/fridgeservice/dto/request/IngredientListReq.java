package com.sungchef.sungchef.fridgeservice.dto.request;

import java.util.List;

import com.sungchef.sungchef.util.commondto.IngredientId;

import lombok.Getter;

@Getter
public class IngredientListReq {
	List<IngredientId> ingredientIdList;
}

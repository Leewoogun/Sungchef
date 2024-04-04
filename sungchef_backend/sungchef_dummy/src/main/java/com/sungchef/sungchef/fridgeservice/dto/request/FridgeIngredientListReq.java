package com.sungchef.sungchef.fridgeservice.dto.request;

import java.util.List;

import com.sungchef.sungchef.fridgeservice.dto.response.IngredientId;

import lombok.Data;
import lombok.Getter;

@Data
public class FridgeIngredientListReq {
	List<IngredientId> ingredientIdList;
}

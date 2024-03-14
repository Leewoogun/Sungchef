package com.sungchef.sungchef.response;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;

@Builder
public class GetFridgeIngredients implements Serializable {
	List<IngredientInfoList> fridgeList;
}

package com.sungchef.sungchef.response;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetFridgeIngredients implements Serializable {
	List<IngredientInfoList> fridgeList;
}

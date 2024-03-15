package com.sungchef.sungchef.response;

import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.IngredientType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IngredientInfoList {
	String type;
	List<IngredientInfo> fridgeList;
}

package com.ssafy.ingredientservice.db.entity.client;

import java.util.List;

public record ClientIngredientListRes(
	List<ClientIngredientInfo> ingredientInfoList
)
{

}

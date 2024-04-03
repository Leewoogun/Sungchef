package com.ssafy.ingredientservice.db.entity.client;

import java.util.List;

public record ClientIngredientListReq (
	List<Integer> ingredientIdList
)
{

}

package com.ssafy.ingredientservice.db.entity.client;

import java.util.List;

import com.ssafy.ingredientservice.dto.response.IngredientId;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientIngredientListReq {
	List<IngredientId> ingredientIdList;
}

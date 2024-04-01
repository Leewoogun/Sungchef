package com.ssafy.fridgeservice.dto.request;

import java.util.List;

import com.ssafy.fridgeservice.dto.response.IngredientId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Data
public class FridgeIngredientListReq {
	List<IngredientId> ingredientIdList;
}

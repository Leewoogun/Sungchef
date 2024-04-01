package com.ssafy.fridgeservice.dto.request;

import java.util.List;

import org.hibernate.mapping.Array;

import com.ssafy.fridgeservice.db.entity.Fridge;
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

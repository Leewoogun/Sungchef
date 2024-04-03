package com.ssafy.fridgeservice.dto.request;

import java.util.List;

import com.ssafy.fridgeservice.dto.response.IngredientId;

import lombok.Data;

@Data
public class IngredientList {
	List<IngredientId> ingredientIdList;
}

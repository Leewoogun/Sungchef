package com.ssafy.ingredientservice.dto.request;

import lombok.Data;
import java.util.List;

import com.ssafy.ingredientservice.dto.response.IngredientId;

@Data
public class IngredientListReq {
	List<Integer> ingredientIdList;
}

package com.ssafy.fridgeservice.dto.request;

import lombok.Data;
import java.util.List;


@Data
public class IngredientListReq {
	List<Integer> ingredientIdList;
}

package com.ssafy.recipeservice.dto.request;

import lombok.Data;

import java.util.List;


@Data
public class FoodIdListReq {
	List<Integer> FoodIdList;
}

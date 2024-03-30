package com.ssafy.recipeservice.dto.request;

import lombok.Data;

import java.util.List;


@Data
public class RecipeIdListReq {
	List<Integer> recipeIdList;
}

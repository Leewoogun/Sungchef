package com.ssafy.recommendservice.dto.response;

import com.ssafy.recommendservice.util.sungchefEnum.RecommendFoodType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RecommendFood {
	String recommendFoodType;
	List<Food> foodList;

//	public RecommendFood(RecommendFoodType _recommendFoodType) {
//		recommendFoodType = _recommendFoodType;
//		foodList = new ArrayList<>();
//	}
}

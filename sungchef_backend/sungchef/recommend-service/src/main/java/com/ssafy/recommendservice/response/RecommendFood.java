package com.ssafy.recommendservice.response;

import com.ssafy.recommendservice.util.sungchefEnum.RecommendFoodType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendFood {
	RecommendFoodType recommendFoodType;
	List<Food> foodList;

	public RecommendFood(RecommendFoodType _recommendFoodType) {
		recommendFoodType = _recommendFoodType;
		foodList = new ArrayList<>();
	}
}

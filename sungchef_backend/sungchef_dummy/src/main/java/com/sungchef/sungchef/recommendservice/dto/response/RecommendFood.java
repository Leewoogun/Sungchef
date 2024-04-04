package com.sungchef.sungchef.recommendservice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.sungchef.sungchef.util.sungchefEnum.RecommendFoodType;

import lombok.Data;

@Data
public class RecommendFood {
	RecommendFoodType recommendFoodType;
	List<Food> foodList;

	public RecommendFood(RecommendFoodType _recommendFoodType) {
		recommendFoodType = _recommendFoodType;
		foodList = new ArrayList<>();
	}
}

package com.ssafy.searchservice.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum IngredientType {

	FRUIT(0),
	VEGETABLE(1),
	RICE_GRAIN(2),
	MEAT_EGG(3),
	FISH(4),
	MILK(5),
	SAUCE(6),
	ETC(7);
	int code;

	IngredientType(int _code) {
		code = _code;
	}
}

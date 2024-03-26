package com.ssafy.ingredientservice.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum ConvertIngredientType {

	FRUIT(0),
	VEGETABLE(1),
	RICE_GRAIN(2),
	MEAT_EGG(3),
	FISH(4),
	MILK(5),
	SAUCE(6),
	ETC(7),
	NOT_CONVERTED(8);

	int code;

	ConvertIngredientType(int _code) {
		code = _code;
	}
}

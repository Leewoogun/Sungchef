package com.sungchef.sungchef.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum IngredientType {
	FRUIT("과일"),
	VEGETABLE("채소"),
	RICEGRAIN("쌀/채소"),
	MEATEGG("정육/계란"),
	FISH("수산"),
	MINK("유제품"),
	SAUCE("소스/양념/조미료"),
	ETC("기타");
	private final String name;

	IngredientType(String _name) {
		name = _name;
	}
}

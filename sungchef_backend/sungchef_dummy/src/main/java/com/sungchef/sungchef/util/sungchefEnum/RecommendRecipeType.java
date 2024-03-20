package com.sungchef.sungchef.util.sungchefEnum;

public enum RecommendRecipeType {
	SUNG(0, "성식당 종합 추천"),
	FRIDGE(1, "냉장고 기반 추천");

	int code;
	String recommendDetail;

	RecommendRecipeType(int _code, String _recommendDetail) {
		code = _code;
		recommendDetail = _recommendDetail;
	}
}

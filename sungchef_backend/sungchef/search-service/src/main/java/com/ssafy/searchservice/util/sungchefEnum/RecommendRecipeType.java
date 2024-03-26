package com.ssafy.searchservice.util.sungchefEnum;

public enum RecommendRecipeType {
	FRIDGE(0, "냉장고 기반 추천");

	int code;
	String recommendDetail;

	RecommendRecipeType(int _code, String _recommendDetail) {
		code = _code;
		recommendDetail = _recommendDetail;
	}
}

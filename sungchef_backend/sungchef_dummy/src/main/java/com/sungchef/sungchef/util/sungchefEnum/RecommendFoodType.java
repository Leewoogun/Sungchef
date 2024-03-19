package com.sungchef.sungchef.util.sungchefEnum;

public enum RecommendFoodType {
	SIMILAR_USER(2, "비슷한 유저 추천"),
	GENDER(3, "동일한 성별 추천"),
	AGE(4, "동일 나이대 추천"),
	WEATHER(5, "날씨 기반 추천");

	int code;
	String recommendDetail;
	RecommendFoodType(int _code, String _recommendDetail) {
		code = _code; recommendDetail = _recommendDetail;
	}
}

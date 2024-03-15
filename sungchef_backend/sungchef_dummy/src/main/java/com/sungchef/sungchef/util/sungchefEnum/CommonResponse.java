package com.sungchef.sungchef.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum CommonResponse {

	SUCCESS(200),
	FAILED(400),
	UNAUTHORIZED(401);

	private final int code;
	CommonResponse(int _code) {
		code = _code;
	}
}

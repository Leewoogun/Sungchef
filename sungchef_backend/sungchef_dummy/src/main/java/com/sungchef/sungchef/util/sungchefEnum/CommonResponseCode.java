package com.sungchef.sungchef.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum CommonResponseCode {

	SUCCESS(200),
	FAILED(400),
	UNAUTHORIZED(401);

	private final int code;
	CommonResponseCode(int _code) {
		code = _code;
	}
}

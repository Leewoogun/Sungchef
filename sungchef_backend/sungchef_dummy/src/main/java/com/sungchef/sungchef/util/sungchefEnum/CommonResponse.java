package com.sungchef.sungchef.util.sungchefEnum;

import lombok.Getter;

@Getter
public enum CommonResponse {

	SUCCESS(200, "성공"),
	FAILED(400, "실패");
	private final int code;
	private final String message;

	CommonResponse(int _code, String _message) {
		code = _code; message = _message;
	}
}

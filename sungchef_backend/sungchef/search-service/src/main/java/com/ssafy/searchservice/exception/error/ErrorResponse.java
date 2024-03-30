package com.ssafy.searchservice.exception.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private int status;
	private String message;
	private String code;

	public ErrorResponse(ErrorCode errorCode){
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
		this.code = errorCode.getCode();
	}

	public ErrorResponse(ErrorCode errorCode, String errorMessage){
		this.status = errorCode.getStatus();
		this.message = errorMessage;
		this.code = errorCode.getCode();
	}
}
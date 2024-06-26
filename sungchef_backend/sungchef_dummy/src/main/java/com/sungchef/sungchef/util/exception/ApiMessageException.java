package com.sungchef.sungchef.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiMessageException extends RuntimeException {

	int code;

	public ApiMessageException(String msg, Throwable t) {
		super(msg, t);
	}

	public ApiMessageException(String msg) {
		super(msg);
	}

	public ApiMessageException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public ApiMessageException() {
		super();
	}
}

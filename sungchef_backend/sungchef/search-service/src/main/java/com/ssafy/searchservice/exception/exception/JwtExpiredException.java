package com.ssafy.searchservice.exception.exception;

public class JwtExpiredException extends RuntimeException {
	public JwtExpiredException(String msg, Throwable t) {
		super(msg, t);
	}

	public JwtExpiredException(String msg) {
		super(msg);
	}

	public JwtExpiredException() {
		super();
	}
}

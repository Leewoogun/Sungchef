package com.ssafy.searchservice.exception.exception;

public class JwtException extends RuntimeException {
	public JwtException(String msg, Throwable t) {
		super(msg, t);
	}

	public JwtException(String msg) {
		super(msg);
	}

	public JwtException() {
		super();
	}
}

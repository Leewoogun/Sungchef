package com.ssafy.recipeservice.exception.exception;

public class PageConvertException extends RuntimeException {
	public PageConvertException(String msg, Throwable t) {
		super(msg, t);
	}

	public PageConvertException(String msg) {
		super(msg);
	}

	public PageConvertException() {
		super();
	}
}

package com.ssafy.userservice.exception.exception;

public class RecipeNotFoundException extends RuntimeException {
	public RecipeNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public RecipeNotFoundException(String msg) {
		super(msg);
	}

	public RecipeNotFoundException() {
		super();
	}
}

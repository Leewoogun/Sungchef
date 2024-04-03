package com.ssafy.ingredientservice.exception.exception;

public class FeignException extends RuntimeException {
	public FeignException(String message) {
		super(message);
	}
}

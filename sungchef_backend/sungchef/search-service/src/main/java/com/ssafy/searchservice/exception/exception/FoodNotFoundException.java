package com.ssafy.searchservice.exception.exception;

public class FoodNotFoundException extends RuntimeException {
	public FoodNotFoundException(String message) {
		super(message);
	}
}

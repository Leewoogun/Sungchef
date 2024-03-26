package com.ssafy.searchservice.util.exception;

public class FoodNotFoundException extends RuntimeException {
	public FoodNotFoundException(String message) {
		super(message);
	}
}

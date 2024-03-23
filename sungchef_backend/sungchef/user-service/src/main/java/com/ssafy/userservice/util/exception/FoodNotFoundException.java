package com.ssafy.userservice.util.exception;

public class FoodNotFoundException extends RuntimeException {
	public FoodNotFoundException(String message) {
		super(message);
	}
}

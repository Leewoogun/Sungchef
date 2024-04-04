package com.sungchef.sungchef.util.exception;

public class FoodNotFoundException extends RuntimeException {
	public FoodNotFoundException(String message) {
		super(message);
	}
}

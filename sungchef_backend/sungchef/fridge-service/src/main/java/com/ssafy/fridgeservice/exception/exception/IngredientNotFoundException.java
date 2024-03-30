package com.ssafy.fridgeservice.exception.exception;

public class IngredientNotFoundException extends RuntimeException {
	public IngredientNotFoundException(String message) {
		super(message);
	}
}

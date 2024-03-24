package com.ssafy.fridgeservice.util.exception;

public class IngredientNotFoundException extends RuntimeException {
	public IngredientNotFoundException(String message) {
		super(message);
	}
}

package com.ssafy.ingredientservice.exception.exception;

public class RecipeNotFoundException extends RuntimeException {
	public RecipeNotFoundException(String message) {
		super(message);
	}
}

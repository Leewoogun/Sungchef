package com.ssafy.ingredientservice.util.exception;

public class RecipeNotFoundException extends RuntimeException {
	public RecipeNotFoundException(String message) {
		super(message);
	}
}

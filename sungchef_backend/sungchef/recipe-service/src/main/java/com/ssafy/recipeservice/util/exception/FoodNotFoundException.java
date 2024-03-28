package com.ssafy.recipeservice.util.exception;

public class RecipeNotFoundException extends RuntimeException {
	public RecipeNotFoundException(String message) {
		super(message);
	}
}

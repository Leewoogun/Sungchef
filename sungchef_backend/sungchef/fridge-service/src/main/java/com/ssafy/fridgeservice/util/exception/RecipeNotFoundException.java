package com.ssafy.fridgeservice.util.exception;

public class RecipeNotFoundException extends RuntimeException {
	public RecipeNotFoundException(String message) {
		super(message);
	}
}

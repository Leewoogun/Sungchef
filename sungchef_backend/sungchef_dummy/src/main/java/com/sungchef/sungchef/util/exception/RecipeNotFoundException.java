package com.sungchef.sungchef.util.exception;

public class RecipeNotFoundException extends RuntimeException{
	public RecipeNotFoundException(String message) {
		super(message);
	}
}

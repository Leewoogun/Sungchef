package com.ssafy.userservice.exception.exception;

public class UserRecipeNotExistException extends RuntimeException {
	public UserRecipeNotExistException(String message) {
		super(message);
	}
}

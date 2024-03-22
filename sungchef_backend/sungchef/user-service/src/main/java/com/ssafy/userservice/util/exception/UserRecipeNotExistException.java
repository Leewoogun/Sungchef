package com.ssafy.userservice.util.exception;

public class UserRecipeNotExistException extends RuntimeException {
	public UserRecipeNotExistException(String message) {
		super(message);
	}
}

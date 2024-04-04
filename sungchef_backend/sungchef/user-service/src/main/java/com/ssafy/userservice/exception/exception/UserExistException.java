package com.ssafy.userservice.exception.exception;

public class UserExistException extends RuntimeException {
	public UserExistException(String message) {
		super(message);
	}
}

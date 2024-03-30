package com.ssafy.userservice.exception.exception;

public class NicknameExistException extends RuntimeException {
	public NicknameExistException(String message) {
		super(message);
	}
}

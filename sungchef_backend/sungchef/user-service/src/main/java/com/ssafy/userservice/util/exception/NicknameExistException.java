package com.ssafy.userservice.util.exception;

public class NicknameExistException extends RuntimeException {
	public NicknameExistException(String message) {
		super(message);
	}
}

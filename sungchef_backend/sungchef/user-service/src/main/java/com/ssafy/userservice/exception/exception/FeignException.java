package com.ssafy.userservice.exception.exception;

public class FeignException extends RuntimeException {
	public FeignException(String message) {
		super(message);
	}
}

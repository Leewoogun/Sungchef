package com.ssafy.fridgeservice.exception.exception;

public class EmptyFridgeException extends RuntimeException {
	public EmptyFridgeException(String message) {
		super(message);
	}
}

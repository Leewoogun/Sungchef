package com.ssafy.userservice.exception.exception;

public class FileNotSupportException extends RuntimeException {
	public FileNotSupportException(String msg, Throwable t) {
		super(msg, t);
	}

	public FileNotSupportException(String msg) {
		super(msg);
	}

	public FileNotSupportException() {
		super();
	}
}

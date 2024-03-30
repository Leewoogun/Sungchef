package com.ssafy.userservice.exception.exception;

public class FileUploadException extends RuntimeException {
	public FileUploadException(String msg, Throwable t) {
		super(msg, t);
	}

	public FileUploadException(String msg) {
		super(msg);
	}

	public FileUploadException() {
		super();
	}
}

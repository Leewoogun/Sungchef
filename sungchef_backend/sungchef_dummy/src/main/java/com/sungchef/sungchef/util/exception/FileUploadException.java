package com.sungchef.sungchef.util.exception;

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

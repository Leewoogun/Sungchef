package com.ssafy.userservice.exception.exception;

public class BookmarkNotFoundException extends RuntimeException {
	public BookmarkNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	public BookmarkNotFoundException(String msg) {
		super(msg);
	}

	public BookmarkNotFoundException() {
		super();
	}
}

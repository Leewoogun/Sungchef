package com.ssafy.userservice.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
	HANDLE_ACCESS_DENIED(403, "C002", "Access is Denied"),
	INVALID_URL(404, "C003", "URL NOT FOUND"),
	METHOD_NOT_ALLOWED(405, "C004", " Invalid Input Value"),

	// Member
	EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
	LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),



		;
	private int status;
	private final String code;
	private final String message;

}
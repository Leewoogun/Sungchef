package com.ssafy.recipeservice.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	// USER
	JWT_TOKEN_EXPIRED(401, "U001", "Jwt Expired"),
	NICKNAME_EXIST(409, "U002", "Nickname Already Exist"),
	USER_NEED_SURVEY(403, "U003", "User Need Survey"),
	USER_RECIPE_NOT_EXIST(204, "U004", "User Recipe Not Exist"),
	USER_NOT_FOUND(400, "U005", "User Not Found"),
	// Common
	INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
	HANDLE_ACCESS_DENIED(403, "C002", "Access is Denied"),
	INVALID_URL(404, "C003", "URL NOT FOUND"),
	METHOD_NOT_ALLOWED(405, "C004", " Invalid Input Value"),

	INTERNAL_SERVER_ERROR(500, "S001", "SERVER_ERROR"),
	SECURITY_ERROR(500, "S002", "SECURITY ERROR"),

	// JWT
	UNAUTHORIZED(401, "U004", "TOKEN UNAUTHORIZED"),
	JWT_ERROR(500, "S003", "JWT ERROR"),

		;
	private int status;
	private final String code;
	private final String message;

}
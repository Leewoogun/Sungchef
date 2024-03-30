package com.ssafy.userservice.service;

import static com.ssafy.userservice.exception.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.exception.error.ErrorCode;
import com.ssafy.userservice.exception.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ErrorResponseService {
	public ResponseEntity<ErrorResponse> getErrorResponse(
		ErrorCode errorCode, HttpStatus httpStatus, String errorMessage, Exception e
	) {
		log.error("handle : " + errorMessage, e);
		ErrorResponse response = new ErrorResponse(errorCode);
		return new ResponseEntity<>(response, httpStatus);
	}
}

package com.ssafy.searchservice.service;

import static com.ssafy.searchservice.exception.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.searchservice.exception.error.ErrorCode;
import com.ssafy.searchservice.exception.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ErrorResponseService {

	public ResponseEntity<ErrorResponse> getErrorResponse(
		ErrorCode errorCode, HttpStatus httpStatus,Exception e
	) {
		log.error("handle : " + e.getMessage(), e);
		ErrorResponse response = new ErrorResponse(errorCode, e.getMessage());
		return new ResponseEntity<>(response, httpStatus);
	}
}

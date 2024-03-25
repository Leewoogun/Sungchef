package com.ssafy.userservice.exceptionhandler;

import static com.ssafy.userservice.util.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ssafy.userservice.util.error.ErrorResponse;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UnexpectedTypeException.class)
	protected ResponseEntity<ErrorResponse> handelUnexpectedTypeException(UnexpectedTypeException e) {
		log.error("handelUnexpectedTypeException", e);
		ErrorResponse response = new ErrorResponse(INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("handleHttpMessageNotReadableException", e);
		ErrorResponse response = new ErrorResponse(INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	/**
	 *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("handleMethodArgumentNotValidException", e);
		ErrorResponse response = new ErrorResponse(INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
	 * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
	 */
	// @ExceptionHandler(BindException.class)
	// protected ErrorResponse handleBindException(BindException e) {
	// 	log.error("handleBindException", e);
	// 	return ErrorResponse.create(e.getCause(), HttpStatus.BAD_REQUEST, e.getMessage());
	// }

	/**
	 * enum type 일치하지 않아 binding 못할 경우 발생
	 * 주로 @RequestParam enum으로 binding 못했을 경우 발생
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		log.error("handleMethodArgumentTypeMismatchException", e);
		ErrorResponse response = new ErrorResponse(INVALID_INPUT_VALUE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		ErrorResponse response = new ErrorResponse(INVALID_URL);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
	 */
	// @ExceptionHandler(AccessDeniedException.class)
	// protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
	// 	log.error("handleAccessDeniedException", e);
	// 	ErrorResponse response = new ErrorResponse(AC);
	// 	return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	// }

	// @ExceptionHandler(BusinessException.class)
	// protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
	// 	log.error("handleEntityNotFoundException", e);
	// 	final ErrorCode errorCode = e.getErrorCode();
	// 	final ErrorResponse response = ErrorResponse.of(errorCode);
	// 	return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
	// }
	//
	//
	// @ExceptionHandler(Exception.class)
	// protected ResponseEntity<ErrorResponse> handleException(Exception e) {
	// 	log.error("handleEntityNotFoundException", e);
	// 	final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
	// 	return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	// }
}
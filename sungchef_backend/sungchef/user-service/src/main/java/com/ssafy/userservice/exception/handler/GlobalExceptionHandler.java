package com.ssafy.userservice.exception.handler;

import static com.ssafy.userservice.exception.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ssafy.userservice.exception.error.ErrorResponse;
import com.ssafy.userservice.exception.exception.*;
import com.ssafy.userservice.service.ErrorResponseService;

import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final ErrorResponseService errorResponseService;

	@ExceptionHandler(UnexpectedTypeException.class)
	protected ResponseEntity<ErrorResponse> handelUnexpectedTypeException(UnexpectedTypeException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	protected ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
		return errorResponseService.getErrorResponse(INVALID_URL, HttpStatus.NOT_FOUND, e);
	}

	/**
	 *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
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
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return errorResponseService.getErrorResponse(INVALID_URL, HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler(SecurityException.class)
	protected ResponseEntity<ErrorResponse> handleSecurityException(SecurityException e) {
		return errorResponseService.getErrorResponse(SECURITY_ERROR, HttpStatus.NOT_ACCEPTABLE, e);
	}

	/**
	 * Custom Exception
	 */
	@ExceptionHandler(FileNotSupportException.class)
	protected ResponseEntity<ErrorResponse> handleFileNotSupportException(FileNotSupportException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST,e);
	}

	@ExceptionHandler(FileSizeException.class)
	protected ResponseEntity<ErrorResponse> handleFileSizeException(FileSizeException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		return errorResponseService.getErrorResponse(USER_NOT_FOUND, HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler(FileUploadException.class)
	protected ResponseEntity<ErrorResponse> handleFileUploadException(FileSizeException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(JwtException.class)
	protected ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(JwtExpiredException.class)
	protected ResponseEntity<ErrorResponse> handleJwtExpiredException(JwtExpiredException e) {
		return errorResponseService.getErrorResponse(JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED, e);
	}

	@ExceptionHandler(UserExistException.class)
	protected ResponseEntity<ErrorResponse> handleUserExistException(UserExistException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(NicknameExistException.class)
	protected ResponseEntity<ErrorResponse> handleNicknameExistException(NicknameExistException e) {
		return errorResponseService.getErrorResponse(NICKNAME_EXIST, HttpStatus.CONFLICT, e);
	}

	@ExceptionHandler(SurveyCountException.class)
	protected ResponseEntity<ErrorResponse> handleSurveyCountException(SurveyCountException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(UserNeedSurveyException.class)
	protected ResponseEntity<ErrorResponse> handleUserNeedSurveyException(UserNeedSurveyException e) {
		return errorResponseService.getErrorResponse(USER_NEED_SURVEY, HttpStatus.FORBIDDEN, e);
	}

	@ExceptionHandler(UserNotCreatedException.class)
	protected ResponseEntity<ErrorResponse> handleUserNotCreatedException(UserNotCreatedException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(UserRecipeNotExistException.class)
	protected ResponseEntity<ErrorResponse> handleUserRecipeNotExistException(UserRecipeNotExistException e) {
		return errorResponseService.getErrorResponse(USER_RECIPE_NOT_EXIST, HttpStatus.NO_CONTENT, e);
	}

	@ExceptionHandler(RecipeNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleRecipeNotFoundException(RecipeNotFoundException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
	}

	@ExceptionHandler(BookmarkNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleBookmarkNotFoundException(BookmarkNotFoundException e) {
		return errorResponseService.getErrorResponse(INVALID_INPUT_VALUE, HttpStatus.BAD_REQUEST, e);
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
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		return errorResponseService.getErrorResponse(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
	}
}
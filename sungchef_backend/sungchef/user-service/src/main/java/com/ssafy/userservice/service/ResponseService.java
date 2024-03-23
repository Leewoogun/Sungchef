package com.ssafy.userservice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssafy.userservice.util.result.CommonResult;
import com.ssafy.userservice.util.result.ListResult;
import com.ssafy.userservice.util.result.SingleResult;
import com.ssafy.userservice.util.sungchefEnum.CommonResponseCode;

@Service
public class ResponseService {

	public CommonResult getSuccessMessageResult(String message) {
		CommonResult result = new CommonResult();
		result.setCode(CommonResponseCode.SUCCESS.getCode());
		result.setMessage(message);
		return result;
	}

	// 단일건 결과를 처리하는 메소드
	public <T> SingleResult<T> getSuccessSingleResult(T data, String msg) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		result.setMessage(msg);
		setSuccessResult(result);
		return result;
	}

	public <T> SingleResult<T> getSingleResult(T data, String msg, int code) {
		SingleResult<T> result = new SingleResult<>();
		result.setCode(code);
		result.setMessage(msg);
		result.setData(data);
		return result;
	}

	// 다중건 결과를 처리하는 메소드
	public <T> ListResult<T> getSuccessListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setData(list);
		setSuccessResult(result);
		return result;
	}

	// 다중건 결과를 처리하는 메소드 + msg 세팅
	public <T> ListResult<T> getSuccessListResult(List<T> list, String msg) {
		ListResult<T> result = new ListResult<>();
		result.setData(list);
		result.setMessage(msg);
		setSuccessResult(result);
		return result;
	}

	// 성공 결과만 처리하는 메소드
	public CommonResult getSuccessResult() {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}

	// 성공 결과만 처리하는 메소드
	public CommonResult getSuccessResult(String msg) {
		CommonResult result = new CommonResult();
		result.setCode(CommonResponseCode.SUCCESS.getCode());
		result.setMessage(msg);
		return result;
	}

	/**
	 * 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
	 */
	private void setSuccessResult(CommonResult result) {
		result.setCode(CommonResponseCode.SUCCESS.getCode());
		result.setMessage(result.getMessage());
	}

	/**
	 * 200
	 * @return ResponseEntity(200)
	 */
	public ResponseEntity<HttpStatusCode> OK() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 204
	 * @return ResponseEntity(204)
	 */
	public ResponseEntity<HttpStatusCode> NO_CONTENT() {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * 400 ERROR
	 * @return ResponseEntity(400)
	 */
	public ResponseEntity<HttpStatusCode> BAD_REQUEST() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	/**
	 * 401 ERROR
	 * @return ResponseEntity(401)
	 */
	public ResponseEntity<HttpStatusCode> UNAUTHORIZED() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	/**
	 * 403 ERROR
	 * @return ResponseEntity(403)
	 */
	public ResponseEntity<HttpStatusCode> FORBIDDEN() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	/**
	 * 404 ERROR
	 * @return ResponseEntity(404)
	 */
	public ResponseEntity<HttpStatusCode> NOT_FOUND() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * 409 ERROR
	 * @return ResponseEntity(409)
	 */
	public ResponseEntity<HttpStatusCode> CONFLICT() {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	/**
	 * 500 ERROR
	 * @return ResponseEntity(500)
	 */
	public ResponseEntity<HttpStatusCode> INTERNAL_SERVER_ERROR() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}

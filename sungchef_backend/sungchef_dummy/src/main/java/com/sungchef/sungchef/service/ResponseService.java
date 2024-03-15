package com.sungchef.sungchef.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sungchef.sungchef.util.sungchefEnum.CommonResponse;
import com.sungchef.sungchef.util.result.CommonResult;
import com.sungchef.sungchef.util.result.ListResult;
import com.sungchef.sungchef.util.result.SingleResult;

@Service
public class ResponseService {

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
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMessage(msg);
		return result;
	}

	// 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
	private void setSuccessResult(CommonResult result) {
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMessage(result.getMessage());
	}


}

package com.ssafy.userservice.config.security.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ssafy.userservice.util.error.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
public class JwtAuthenticationEntryPoint{
	// @Override
	// public void commence(HttpServletRequest request, HttpServletResponse response,
	// 	AuthenticationException authException) throws IOException {
	// 	String exception = (String)request.getAttribute("exception");
	//
	// 	if(exception == null) {
	// 		setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
	// 	}
	// 	//잘못된 타입의 토큰인 경우
	// 	else if(exception.equals(ErrorCode.INTERNAL_SERVER_ERROR.getCode())) {
	// 		setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
	// 	}
	// 	//토큰 만료된 경우
	// 	else if(exception.equals(ErrorCode.JWT_TOKEN_EXPIRED.getCode())) {
	// 		setResponse(response, ErrorCode.JWT_TOKEN_EXPIRED);
	// 	}
	// 	//지원되지 않는 토큰인 경우
	// 	else if(exception.equals(ErrorCode.INTERNAL_SERVER_ERROR.getCode())) {
	// 		setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
	// 	}
	// 	else {
	// 		setResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
	// 	}
	//
	// 	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	// }
	// //한글 출력을 위해 getWriter() 사용
	// private void setResponse(HttpServletResponse response, ErrorCode code) throws IOException {
	// 	response.setContentType("application/json;charset=UTF-8");
	// 	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	//
	// 	// JSONObject responseJson = new JSONObject();
	// 	// responseJson.put("message", code.getMessage());
	// 	// responseJson.put("code", code.getCode());
	//
	// 	response.getWriter().print(code.getMessage());
	// }
}
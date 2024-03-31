package com.ssafy.userservice.config.jwt;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// @Component
public class JwtTokenInterceptor {
// public class JwtTokenInterceptor implements RequestInterceptor {

	// @Override
	// public void apply(RequestTemplate template) {
	// 	// 현재 인증된 사용자의 인증 객체 가져오기
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//
	// 	// 인증 객체에서 JWT 토큰 가져오기
	// 	if (authentication != null && authentication.getCredentials() instanceof String) {
	// 		String jwtToken = (String) authentication.getCredentials();
	//
	// 		// HTTP 요청 헤더에 JWT 토큰 추가
	// 		template.header("Authorization", "Bearer_" + jwtToken);
	// 	}
	// }
}


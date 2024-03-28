package com.ssafy.userservice.config.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = resolveToken(request);

		// 토큰 유효성 검사
		if (token != null && jwtTokenProvider.validateToken(token)) {
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	// 헤더에서 토큰 추출
	private String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		String refreshToken = request.getHeader("Refresh");
		if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
			return accessToken.substring(7);
		}
		if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer")) {
			return refreshToken.substring(7);
		}
		return null;
	}

}
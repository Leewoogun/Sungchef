package com.ssafy.recommendservice.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.recommendservice.exception.error.ErrorCode;
import com.ssafy.recommendservice.exception.error.ErrorResponse;
import com.ssafy.recommendservice.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = resolveToken(request);
		try {
			if (token != null && jwtService.validateToken(token)) {
				Authentication authentication = jwtService.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_TOKEN_EXPIRED);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.error("Expired JWT Token", e);
		} catch (SecurityException | MalformedJwtException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.SECURITY_ERROR);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.error("Invalid JWT Token", e);
		} catch (UnsupportedJwtException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ERROR);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.error("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ERROR);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.error("JWT claims string is empty.", e);
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ERROR);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.error("JWT claims string is empty.", e);
		}
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
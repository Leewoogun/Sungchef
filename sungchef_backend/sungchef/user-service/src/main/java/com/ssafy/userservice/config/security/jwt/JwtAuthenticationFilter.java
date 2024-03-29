package com.ssafy.userservice.config.security.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.userservice.config.security.SecurityConfig;
import com.ssafy.userservice.util.error.ErrorCode;
import com.ssafy.userservice.util.error.ErrorResponse;

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
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String token = resolveToken(request);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		// } catch (AuthenticationException e) {
		// 	ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_TOKEN_EXPIRED);
		// 	response.setStatus(errorResponse.getStatus());
		// 	response.setContentType("application/json");
		// 	response.getWriter().write(errorResponse.getMessage());
		// 	log.info("Expired JWT Token", e);
		} catch (SecurityConfig.TokenNotValidateException | ExpiredJwtException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_TOKEN_EXPIRED);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.info("Expired JWT Token", e);
		} catch (SecurityException | MalformedJwtException e) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.SECURITY_ERROR);
			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(errorResponse.getMessage());
			log.info("Invalid JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// filterChain.doFilter(request, response);
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
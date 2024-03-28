package com.ssafy.userservice.config.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.userservice.service.RedisService;
import com.ssafy.userservice.util.exception.BaseException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
	// 인증에서 제외할 url
	private static final List<String> EXCLUDE_URL =
		List.of(
			// "/h2",
			"/user/signup",
			"/user/login",
			"/user/autologin",
			"/user/reissue",
			"/user/exist/**"
		);
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;

	// JWT 인증 정보를 현재 쓰레드의 SecurityContext에 저장(가입/로그인/재발급 Request 제외)
	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			String accessToken = jwtTokenProvider.resolveAccessToken(request);
			if (StringUtils.hasText(accessToken) && doNotLogout(accessToken)
				&& jwtTokenProvider.validateToken(accessToken)) {
				setAuthenticationToContext(accessToken);
			}
			// TODO: 예외처리 리팩토링
		} catch (RuntimeException e) {
			throw new BaseException(e.getMessage());
			// if (e instanceof BusinessLogicException) {
			// 	ObjectMapper objectMapper = new ObjectMapper();
			// 	String json = objectMapper.writeValueAsString(ErrorResponse.of(((BusinessLogicException) e).getExceptionCode()));
			// 	response.getWriter().write(json);
			// 	response.setStatus(((BusinessLogicException) e).getExceptionCode().getStatus());
			// }
		}
		filterChain.doFilter(request, response);
	}

	private boolean doNotLogout(String accessToken) {
		String isLogout = redisService.getValues(accessToken);
		return isLogout.equals("false");
	}

	// EXCLUDE_URL과 동일한 요청이 들어왔을 경우, 현재 필터를 진행하지 않고 다음 필터 진행
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		boolean result = EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));

		return result;
	}

	private void setAuthenticationToContext(String accessToken) {
		Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("# Token verification success!");
	}
}
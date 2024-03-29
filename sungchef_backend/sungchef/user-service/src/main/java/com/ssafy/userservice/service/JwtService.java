package com.ssafy.userservice.service;

import org.springframework.stereotype.Service;

import com.ssafy.userservice.config.security.jwt.JwtTokenProvider;

import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtTokenProvider jwtTokenProvider;
	public String getUserSnsId(String token) throws UnsupportedJwtException {
		return jwtTokenProvider.getUserSnsId(token);
	}
}

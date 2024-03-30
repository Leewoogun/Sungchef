package com.ssafy.fridgeservice.service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssafy.fridgeservice.exception.exception.JwtExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {
	private final Key key;

	// application.yml에서 secret 값 가져와서 key에 저장
	public JwtService(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	public String getUserSnsId(HttpServletRequest request) {
		try {
			String resolveToken = resolveToken(request);
			Claims claims = parseClaims(resolveToken);
			String userSnsId = claims.getSubject();
			if (userSnsId == null) throw new JwtException("userSnsId가 없는 토큰");
			return userSnsId;
		}  catch (ExpiredJwtException e) {
			// 토큰이 만료된 경우
			log.error("Expired refresh token", e);
			throw new JwtExpiredException("Expired refresh token.");
		} catch (UnsupportedJwtException e) {
			// 지원되지 않는 JWT 형식인 경우
			log.error("Unsupported JWT token", e);
			throw new UnsupportedJwtException("Unsupported JWT token.", e);
		} catch (MalformedJwtException e) {
			// JWT 형식이 잘못된 경우
			log.error("Invalid JWT token", e);
			throw new MalformedJwtException("Invalid JWT token.", e);
		} catch (IllegalArgumentException e) {
			// refreshToken 인자가 잘못된 경우 (null 또는 빈 문자열 등)
			log.error("JWT token compact of handler are invalid.", e);
			throw new IllegalArgumentException("JWT token compact of handler are invalid.", e);
		} catch (Exception e) {
			log.error("토큰 파싱 중 오류 발생", e);
			throw new JwtException("JWT Parse Error", e);
		}
	}

	private String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer")) {
			return accessToken.substring(7);
		}
		throw new JwtException("JWT Resolve Failed");
	}

	private Claims parseClaims(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public Boolean validateToken(String token) {
		Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
		return true;
	}

	public Authentication getAuthentication(String accessToken) {
		// Jwt 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new JwtException("권한 정보가 없는 토큰입니다.");
		}

		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		// UserDetails 객체를 만들어서 Authentication return
		// UserDetails: interface, User: UserDetails를 구현한 class
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
}

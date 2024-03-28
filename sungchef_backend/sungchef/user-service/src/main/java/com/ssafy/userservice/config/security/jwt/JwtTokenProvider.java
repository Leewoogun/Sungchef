package com.ssafy.userservice.config.security.jwt;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ssafy.userservice.service.RedisService;
import com.ssafy.userservice.service.UserDetailServiceImpl;
import com.ssafy.userservice.util.exception.JwtExpiredException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final Key key;
	private final UserDetailServiceImpl userDetailsService;
	private final RedisService redisService;

	// application.yml에서 secret 값 가져와서 key에 저장
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailServiceImpl userDetailsService,
		RedisService redisService) {
		this.userDetailsService = userDetailsService;
		this.redisService = redisService;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public JwtToken generateToken(Authentication authentication, String userSnsId) {
		// 권한 가져오기
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String authoritiesString = authorities.isEmpty() ? "ROLE_USER" : authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();

		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + 3600000 * 24 * 3); // 1시간 => 시연, 테스트 위한 3일
		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authoritiesString)
			.setExpiration(accessTokenExpiresIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authoritiesString)
			.setExpiration(new Date(now + 1209600000)) // 2주
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();

		redisService.setValues("user_" + userSnsId, "Bearer_" + refreshToken, Duration.ofMillis(1000 * 60 * 60 * 36));

		return JwtToken.builder()
			.grantType("Bearer")
			.accessToken("Bearer_" + accessToken)
			.refreshToken("Bearer_" + refreshToken)
			.build();
	}

	// Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
	public Authentication getAuthentication(String accessToken) {
		// Jwt 토큰 복호화
		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
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

	// 토큰 정보를 검증하는 메서드
	public Boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}


	// accessToken
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (Exception e) {
			log.info("토큰 파싱 중 오류 발생", e);
			return null;
		}
	}

	// refresh token으로 새 accessToken 생성
	public JwtToken generateTokenFromRefreshToken(String refreshToken) {
		// refresh token의 claim 검증, 추출
		Claims claims = parseRefreshToken(refreshToken);

		if (claims == null) {
			throw new SecurityException("리프레시 토큰이 유효하지 않습니다.");
		}

		String userSnsId = claims.getSubject();
		String redisRefreshToken = redisService.getValues("user_" + userSnsId);
		if (redisRefreshToken == null || !refreshToken.equals(redisRefreshToken.substring(7))) throw new JwtExpiredException("JWT Token Expired");

		// UserDetailsService를 사용하여 데이터베이스에서 사용자 정보를 조회
		UserDetails userDetails = userDetailsService.loadUserByUsername(userSnsId);

		// UserDetails를 바탕으로 Authentication 객체 생성
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		// SecurityContext에 Authentication 객체 설정 (선택적)
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return generateToken(authentication, userSnsId);
	}

	private Claims parseRefreshToken(String refreshToken) {
		try {
			// 리프레시 토큰의 형식과 서명을 검증하고, 클레임을 추출합니다.
			return Jwts.parserBuilder()
				.setSigningKey(key) // 토큰 서명 검증에 사용할 키 설정
				.build()
				.parseClaimsJws(refreshToken) // JWT 파싱 및 검증
				.getBody(); // 검증된 JWT의 클레임 반환
		} catch (ExpiredJwtException e) {
			// 토큰이 만료된 경우
			log.error("Expired refresh token", e);
			throw new SecurityException("Expired refresh token.", e);
		} catch (UnsupportedJwtException e) {
			// 지원되지 않는 JWT 형식인 경우
			log.error("Unsupported JWT token", e);
			throw new SecurityException("Unsupported JWT token.", e);
		} catch (MalformedJwtException e) {
			// JWT 형식이 잘못된 경우
			log.error("Invalid JWT token", e);
			throw new SecurityException("Invalid JWT token.", e);
		} catch (IllegalArgumentException e) {
			// refreshToken 인자가 잘못된 경우 (null 또는 빈 문자열 등)
			log.info("JWT token compact of handler are invalid.", e);
			throw new SecurityException("JWT token compact of handler are invalid.", e);
		}
	}

	public String resolveAccessToken(HttpServletRequest request) {
		return request.getHeader("Authorization").substring(7);
	}
}
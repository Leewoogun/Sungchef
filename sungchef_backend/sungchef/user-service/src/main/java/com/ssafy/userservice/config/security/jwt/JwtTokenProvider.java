package com.ssafy.userservice.config.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ssafy.userservice.util.exception.BaseException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private final String BEARER_TYPE = "Bearer";
	private final String AUTHORIZATION_HEADER = "Authorization";
	private final String REFRESH_HEADER = "Refresh";
	private final String BEARER_PREFIX = "Bearer ";

	private final Key secretKey;

	// application.yml에서 secret 값 가져와서 key에 저장
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}


	public String getJwt(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader(AUTHORIZATION_HEADER);
	}
	//
	// Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
	// public Authentication getAuthentication(String accessToken) {
	// 	// Jwt 토큰 복호화
	// 	Claims claims = parseClaims(accessToken);
	//
	// 	if (claims.get("auth") == null) {
	// 		throw new RuntimeException("권한 정보가 없는 토큰입니다.");
	// 	}
	//
	// 	// 클레임에서 권한 정보 가져오기
	// 	Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
	// 		.map(SimpleGrantedAuthority::new)
	// 		.toList();
	//
	// 	// UserDetails 객체를 만들어서 Authentication return
	// 	// UserDetails: interface, User: UserDetails를 구현한 class
	// 	// User user = new User(claims.getSubject())
	// 	User principal = new User(claims.getSubject(), "", authorities);
	// 	return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	// }

	// 토큰 정보를 검증하는 메서드
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
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

	public JwtToken generateToken(String userSnsId) {

		long now = (new Date()).getTime();
		// Claims claims = Jwts.claims().setSubject(userSnsId);
		// Access Token 생성
		String accessToken = Jwts.builder()
			.setHeaderParam("type", "jwt")
			.claim("userSnsId", userSnsId)
			.setExpiration(new Date(now + 1000 * 60 * 30))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

		// Refresh Token 생성
		String refreshToken = Jwts.builder()
			.setExpiration(new Date(now + 1000 * 60 * 60 * 36))
			.claim("userSnsId", userSnsId)
			.setSubject(userSnsId)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();

		return JwtToken.builder()
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	// accessToken
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public void accessTokenSetHeader(String accessToken, HttpServletResponse response) {
		String headerValue = BEARER_PREFIX + accessToken;
		response.setHeader(AUTHORIZATION_HEADER, headerValue);
	}

	public void refreshTokenSetHeader(String refreshToken, HttpServletResponse response) {
		response.setHeader(REFRESH_HEADER, refreshToken);
	}

	// Request Header에 Access Token 정보를 추출하는 메서드
	public String resolveAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	// Request Header에 Refresh Token 정보를 추출하는 메서드
	public String resolveRefreshToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(REFRESH_HEADER);
		if (StringUtils.hasText(bearerToken)) {
			return bearerToken;
		}
		return null;
	}

	public String getUserSnsId() throws BaseException {
		//1. JWT 추출
		String accessToken = getJwt();
		if(accessToken == null || accessToken.length() == 0){
			throw new BaseException("Empty Jwt");
		}

		// 2. JWT parsing
		Jws<Claims> claims;
		try{
			claims = Jwts.parser()
				.setSigningKey(secretKey) //Set Key
				.parseClaimsJws(accessToken); //위 Key를 가지고 파싱 및 검증 과정
		} catch (Exception ignored) {
			throw new BaseException("INVALID_JWT");
		}

		// 3. ownerIdx 추출
		return claims.getBody().get("userSnsId", String.class);  // jwt 에서 ownerIdx를 추출합니다.
	}
}

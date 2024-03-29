package com.ssafy.userservice.config.security;

import com.ssafy.userservice.config.security.jwt.JwtAuthenticationFilter;
import com.ssafy.userservice.config.security.jwt.JwtTokenProvider;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authz) -> authz

				.requestMatchers(new AntPathRequestMatcher("/user/exist/**", "GET")).permitAll()

				.requestMatchers(new AntPathRequestMatcher("/user/signup", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/reissue", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/login", "POST")).permitAll()

				.anyRequest().authenticated()

			)

			.sessionManagement((session)
				-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			// .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
			.build();


	}

}

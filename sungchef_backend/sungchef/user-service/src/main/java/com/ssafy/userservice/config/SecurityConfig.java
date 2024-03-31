package com.ssafy.userservice.config;

import com.ssafy.userservice.config.jwt.JwtAuthenticationFilter;
import com.ssafy.userservice.service.ErrorResponseService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	private final ErrorResponseService errorResponseService;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authz) -> authz

				.requestMatchers(new AntPathRequestMatcher("/user/exist/**", "GET")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/survey", "GET")).permitAll()

				.requestMatchers(new AntPathRequestMatcher("/user/signup", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/reissue", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/login", "POST")).permitAll()

				.anyRequest().authenticated()
			)
			.sessionManagement((session)
				-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.build();


	}

}

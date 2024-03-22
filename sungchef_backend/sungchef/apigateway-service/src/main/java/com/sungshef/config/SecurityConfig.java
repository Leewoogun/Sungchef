package com.sungshef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

// @Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		// Configure AuthenticationManagerBuilder
		AuthenticationManagerBuilder authenticationManagerBuilder =
			http.getSharedObject(AuthenticationManagerBuilder.class);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		http.csrf( (csrf) -> csrf.disable());
		//        http.csrf(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests((authz) -> authz
				// .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				//                        .requestMatchers("/**").access(this::hasIpAddress)
				// .requestMatchers("/**").permitAll()
				// .anyRequest().authenticated()
				.anyRequest().permitAll()
			)
			.authenticationManager(authenticationManager)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// http.addFilter(getAuthenticationFilter(authenticationManager));
		http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

		return http.build();
	}
}
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
	// private final UserDetailServiceImpl userDetailService;
	// private final BCryptPasswordEncoder bCryptPasswordEncoder;
	// private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	// private final HandlerExceptionResolver resolver;
	private final Environment env;
	private final JwtTokenProvider jwtTokenProvider;
	// private final ObjectMapper objectMapper;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		// AuthenticationManagerBuilder authenticationManagerBuilder =
		// 		http.getSharedObject(AuthenticationManagerBuilder.class);
		// authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
		//
		// AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		return http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authz) -> authz
				// .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				// .requestMatchers(HttpMethod.POST, "/user/signup", "/user/reissue", "/user/login", "/user/autologin").permitAll()
				// .requestMatchers(HttpMethod.GET, "/user/exist/**").permitAll()

				// .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/exist/**", "GET")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/**", "GET")).permitAll()

				.requestMatchers(new AntPathRequestMatcher("/user/signup", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/reissue", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/login", "POST")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/autologin", "POST")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/bookmark", "POST")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/contact", "POST")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/survey/submit", "POST")).permitAll()

				// .requestMatchers(new AntPathRequestMatcher("/user", "PUT")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/survey/submit", "PUT")).permitAll()
				// .requestMatchers(new AntPathRequestMatcher("/user/signup", "POST")).permitAll()
				//                        .requestMatchers("/**").access(this::hasIpAddress)
				// 								.requestMatchers("/**").access(
				// 										new WebExpressionAuthorizationManager("hasIpAddress('127.0.0.1') or hasIpAddress('172.30.1.48')"))
				.anyRequest().authenticated()

			)

			// .addFilter(getAuthenticationFilter(authenticationManager))
			// .authenticationManager(authenticationManager)
			.sessionManagement((session)
				-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			// .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
			.build();


	}

	// private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
	// 	return new AuthorizationDecision(ALLOWED_IP_ADDRESS_MATCHER.matches(object.getRequest()));
	// }

	public class TokenNotValidateException extends JwtException {

		public TokenNotValidateException(String message) {
			super(message);
		}

		public TokenNotValidateException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}

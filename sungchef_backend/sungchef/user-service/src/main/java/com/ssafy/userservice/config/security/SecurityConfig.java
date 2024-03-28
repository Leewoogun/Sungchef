package com.ssafy.userservice.config.security;

import com.ssafy.userservice.config.security.jwt.JwtAuthenticationFilter;
import com.ssafy.userservice.config.security.jwt.JwtTokenProvider;
import com.ssafy.userservice.service.UserDetailServiceImpl;
import com.ssafy.userservice.service.UserService;

import org.checkerframework.checker.units.qual.A;
import org.springframework.security.authentication.AuthenticationManager;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	private final UserDetailServiceImpl userDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final Environment env;
	private final JwtTokenProvider jwtTokenProvider;
	// public static final String ALLOWED_IP_ADDRESS = "127.0.0.1";
	// public static final String SUBNET = "/32";
	// public static final IpAddressMatcher ALLOWED_IP_ADDRESS_MATCHER = new IpAddressMatcher(ALLOWED_IP_ADDRESS + SUBNET);
	//	public SecurityConfig(Environment env, UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
	//		this.env = env;
	//		this.userDetailsService = userDetailsService;
	//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	//	}
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
				.requestMatchers(new AntPathRequestMatcher("/")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/**", "GET")).permitAll()

				.requestMatchers(new AntPathRequestMatcher("/user/signup", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/reissue", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/login", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/autologin", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/bookmark", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/contact", "POST")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/survey/submit", "POST")).permitAll()

				.requestMatchers(new AntPathRequestMatcher("/user", "PUT")).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/user/survey/submit", "PUT")).permitAll()
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
			.build();


	}

	// private AuthorizationDecision hasIpAddress(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
	// 	return new AuthorizationDecision(ALLOWED_IP_ADDRESS_MATCHER.matches(object.getRequest()));
	// }

	private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
		return new AuthenticationFilter(authenticationManager, userDetailService, env);
	}

}

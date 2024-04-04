package com.sungshef.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class FilterConfig {
	// @Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(r -> r.path("/fridge/**")
					.filters(
						f -> f.addRequestHeader("fridge-req", "fridge-req-header")
							.addResponseHeader("fridge-res", "fridge-res-header")
					)
					.uri("http://localhost:9003")
				)
			.route(r -> r.path("/user/**")
				.filters(
					f -> f.addRequestHeader("user-req", "user-req-header")
						.addResponseHeader("user-res", "user-res-header")
				)
				.uri("http://localhost:9001")
			)
			.build();
	}
}

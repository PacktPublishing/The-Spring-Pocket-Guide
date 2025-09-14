package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
class GatewayConfiguration {

	@Bean
	@Order(1)
	RouterFunction<ServerResponse> apiRoute() {
		return route("api")//
			.filter(tokenRelay()) //
			.before(stripPrefix())//
			.before(uri("http://localhost:8080/"))//
			.GET("/api/**", http()) //
			.POST("/api/**", http()) //
			.build();
	}

	@Bean
	@Order(2)
	RouterFunction<ServerResponse> staticRoute() {
		return route("cdn")//
			.filter(tokenRelay()) //
			.before(uri("http://localhost:8020/"))//
			.GET("/*", http()) //
			.build();
	}

}

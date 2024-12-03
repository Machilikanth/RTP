package com.toucan.rtp.gt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.toucan.rtp.gt.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	
	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
	    return ((exchange, chain) -> {
	        if (validator.isSecured.test(exchange.getRequest())) {
	            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
	                System.out.println("Missing authorization header");
	                throw new RuntimeException("Missing authorization header");
	            }

	            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

	            if (authHeader != null && authHeader.startsWith("Bearer ")) {
	                authHeader = authHeader.substring(7);
	            }
	            return jwtUtil.isTokenValid(authHeader)
	                    .flatMap(isValid -> {
	                        if (Boolean.TRUE.equals(isValid)) {
	                            return chain.filter(exchange);
	                        } else {
	                            //logger.warn("Invalid access: Token Missing");
	                            return Mono.error(new RuntimeException("Unauthorized access to application"));
	                        }
	                    })
	                    .onErrorResume(e -> {
	                       // logger.error("Token validation error", e);
	                        return Mono.error(new RuntimeException("Unauthorized access to application", e));
	                    });
	        }
	        return chain.filter(exchange);
	    });
	}


	public static class Config {
		

	}
}
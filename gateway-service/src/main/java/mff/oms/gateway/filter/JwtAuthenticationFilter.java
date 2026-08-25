package mff.oms.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import mff.oms.gateway.security.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		// Allow register/login without token
		if (path.contains("/auth/register") || path.contains("/auth/login")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		String token = authHeader.substring(7);
		try {
			Claims claims = jwtUtil.extractClaims(token);
			String email = claims.getSubject();
			// You can also set attributes if needed
			exchange = exchange.mutate().request(exchange.getRequest().mutate().header("X-User-Email", email).build())
					.build();
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}
}

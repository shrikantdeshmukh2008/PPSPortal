package mff.oms.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mff.oms.gateway.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    // Register the filter globally so it applies to all routes
    @Bean
    public GlobalFilter globalJwtFilter() {
        return jwtFilter;
    }
}

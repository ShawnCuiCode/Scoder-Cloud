package com.scoder.gateway.config;

import com.scoder.gateway.handler.SentinelFallbackHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Gateway configuration for rate limiting and cross-origin requests.
 * <p>
 * This class defines the configuration for:
 * 1. Sentinel fallback handler for rate limiting exceptions.
 * 2. CORS (Cross-Origin Resource Sharing) settings.
 *
 * @author Shawn Cui
 */
@Configuration
public class GatewayConfig {

    /**
     * Registers a custom Sentinel fallback handler.
     * This handler manages rate-limiting exceptions for the gateway.
     *
     * @return an instance of {@link SentinelFallbackHandler}.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelFallbackHandler sentinelGatewayExceptionHandler() {
        return new SentinelFallbackHandler();
    }

    /**
     * Configures a CORS filter to allow cross-origin requests.
     * <p>
     * - Allows all origins, methods, and headers.
     * - Enables credentials (cookies, authentication headers, etc.).
     * - Applies these settings to all URL paths.
     *
     * @return an instance of {@link CorsWebFilter} with the specified CORS settings.
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedMethod("*"); // Allow all HTTP methods (POST, GET, etc.)
        config.addAllowedHeader("*"); // Allow all headers
        config.setAllowCredentials(true); // Allow credentials (cookies, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS settings to all paths

        return new CorsWebFilter(source);
    }
}
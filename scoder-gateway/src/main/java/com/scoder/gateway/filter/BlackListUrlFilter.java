package com.scoder.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.scoder.common.core.web.domain.AjaxResult;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Blacklist URL filter for Gateway.
 * <p>
 * This filter blocks access to specific URLs defined in a blacklist configuration.
 *
 * @author Shawn Cui
 */
@Component
public class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilter.Config> {

    /**
     * Constructor for the filter, specifying the configuration class.
     */
    public BlackListUrlFilter() {
        super(Config.class);
    }

    /**
     * Applies the filter logic.
     * <p>
     * - Checks if the requested URL matches any patterns in the blacklist.
     * - If a match is found, the request is blocked, and an error response is returned.
     *
     * @param config The configuration for the filter, containing the blacklist patterns.
     * @return The configured GatewayFilter.
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();

            // Check if the URL matches any blacklist patterns
            if (config.matchBlacklist(url)) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

                // Return an error response if the URL is blocked
                return exchange.getResponse().writeWith(
                        Mono.just(response.bufferFactory().wrap(
                                JSON.toJSONBytes(AjaxResult.error("Access to the requested URL is forbidden"))
                        )));
            }

            // Proceed to the next filter in the chain if no match is found
            return chain.filter(exchange);
        };
    }

    /**
     * Configuration class for the BlackListUrlFilter.
     * <p>
     * Contains the list of blacklisted URLs and compiled patterns for matching.
     */
    public static class Config {

        /**
         * List of blacklisted URLs as strings (e.g., "/admin/**").
         */
        private List<String> blacklistUrl;

        /**
         * Compiled patterns for the blacklisted URLs.
         */
        private List<Pattern> blacklistUrlPattern = new ArrayList<>();

        /**
         * Checks if the given URL matches any patterns in the blacklist.
         *
         * @param url The URL to check.
         * @return True if the URL matches a blacklist pattern; otherwise, false.
         */
        public boolean matchBlacklist(String url) {
            return !blacklistUrlPattern.isEmpty() &&
                    blacklistUrlPattern.stream().anyMatch(p -> p.matcher(url).find());
        }

        /**
         * Gets the list of blacklisted URLs.
         *
         * @return The list of blacklisted URLs.
         */
        public List<String> getBlacklistUrl() {
            return blacklistUrl;
        }

        /**
         * Sets the list of blacklisted URLs and compiles the corresponding patterns.
         * <p>
         * - Converts wildcard patterns (e.g., "**") into regex patterns.
         *
         * @param blacklistUrl The list of blacklisted URLs.
         */
        public void setBlacklistUrl(List<String> blacklistUrl) {
            this.blacklistUrl = blacklistUrl;
            this.blacklistUrlPattern.clear();

            // Compile patterns for the blacklist
            this.blacklistUrl.forEach(url -> {
                this.blacklistUrlPattern.add(
                        Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE)
                );
            });
        }
    }
}
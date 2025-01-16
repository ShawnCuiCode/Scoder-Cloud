package com.scoder.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scoder.common.core.constant.CacheConstants;
import com.scoder.common.core.constant.Constants;
import com.scoder.common.core.domain.R;
import com.scoder.common.core.utils.ServletUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.redis.service.RedisService;
import com.scoder.gateway.config.properties.IgnoreWhiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * Authentication filter for the gateway.
 * <p>
 * This filter checks the validity of tokens in incoming requests,
 * ensures user sessions are valid, and enforces security policies.
 *
 * @author Shawn Cui
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * Token expiration time in seconds.
     */
    private static final long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60;

    /**
     * Secret key for token validation (currently unused).
     */
    private static final String SECRET = "scoder";

    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> sops;

    @Autowired
    private RedisService redisService;

    /**
     * Filters requests and performs authentication checks.
     * <p>
     * - Skips validation for URLs in the whitelist.
     * - Validates the token and retrieves user details from Redis.
     * - Refreshes the token expiration time in Redis.
     * - Adds user details to the request headers.
     *
     * @param exchange the current server exchange.
     * @param chain    the filter chain.
     * @return a Mono<Void> indicating when request processing is complete.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        // Skip validation for whitelisted URLs
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            return chain.filter(exchange);
        }

        // Retrieve token from request
        String token = getToken(exchange.getRequest());
        if (StringUtils.isBlank(token)) {
            return setUnauthorizedResponse(exchange, "Token cannot be empty");
        }

        // Retrieve user details from Redis
        String userStr = sops.get(getTokenKey(token));
        if (StringUtils.isNull(userStr)) {
            return setUnauthorizedResponse(exchange, "Login session has expired");
        }

        // Parse user details from Redis
        JSONObject obj = JSONObject.parseObject(userStr);
        String userId = obj.getString("userId");
        String userName = obj.getString("userName");

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userName)) {
            return setUnauthorizedResponse(exchange, "Token validation failed");
        }

        // Refresh token expiration time in Redis
        redisService.expire(getTokenKey(token), EXPIRE_TIME);

        // Add user details to request headers
        ServerHttpRequest mutableReq = exchange.getRequest()
                .mutate()
                .header("userId", userId)
                .header("userName", ServletUtils.urlEncode(userName))
                .build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        return chain.filter(mutableExchange);
    }

    /**
     * Returns an unauthorized response with the specified message.
     *
     * @param exchange the current server exchange.
     * @param msg      the error message to include in the response.
     * @return a Mono<Void> indicating when the response has been written.
     */
    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        log.error("[Authentication Error] Request path: {}", exchange.getRequest().getPath());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(msg)));
        }));
    }

    /**
     * Constructs the Redis key for the given token.
     *
     * @param token the token.
     * @return the constructed Redis key.
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * Extracts the token from the request headers.
     *
     * @param request the incoming HTTP request.
     * @return the extracted token, or null if not found.
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(CacheConstants.HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(CacheConstants.TOKEN_PREFIX)) {
            token = token.replace(CacheConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * Specifies the order in which this filter is executed.
     *
     * @return the order value (-200).
     */
    @Override
    public int getOrder() {
        return -200;
    }
}
package com.scoder.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * SentinelFallbackHandler
 * <p>
 * Custom handler for managing rate limiting exceptions in the gateway.
 * This class processes blocked requests caused by Sentinel and returns
 * a structured JSON response to the client.
 *
 * @author Shawn Cui
 */
public class SentinelFallbackHandler implements WebExceptionHandler {

    /**
     * Writes a JSON response to the client for blocked requests.
     *
     * @param response The server response to process.
     * @param exchange The current web exchange context.
     * @return A Mono signaling when the response has been written.
     */
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();

        // Set response headers for JSON content
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        // Prepare the JSON response body
        byte[] datas = "{\"code\":429,\"msg\":\"Request limit exceeded, please try again later\"}"
                .getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);

        // Write the response body to the client
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    /**
     * Handles exceptions that occur during request processing, specifically Sentinel block exceptions.
     *
     * @param exchange The current ServerWebExchange.
     * @param ex       The exception that occurred.
     * @return A Mono signaling when the exception handling is complete.
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // If the response is already committed, propagate the exception
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        // If the exception is not a BlockException, propagate it
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }

        // Handle blocked requests caused by Sentinel
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange));
    }

    /**
     * Processes blocked requests and generates a ServerResponse.
     *
     * @param exchange  The current ServerWebExchange.
     * @param throwable The throwable associated with the blocked request.
     * @return A Mono containing the ServerResponse to handle the blocked request.
     */
    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        // Delegate the request handling to Sentinel's GatewayCallbackManager
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
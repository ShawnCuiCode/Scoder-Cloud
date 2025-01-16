package com.scoder.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.scoder.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GatewayExceptionHandler
 * <p>
 * A unified exception handler for handling exceptions at the gateway level.
 * This handler ensures that exceptions are properly logged and formatted responses are returned to the client.
 *
 * @author Shawn Cui
 */
@Order(-1) // Highest precedence to ensure this handler is executed first.
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHandler.class);

    /**
     * Handles exceptions that occur during request processing in the gateway.
     *
     * @param exchange The current ServerWebExchange.
     * @param ex       The exception that occurred.
     * @return A Mono signaling when the exception handling is complete.
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        // If the response has already been committed, propagate the exception.
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        String msg;

        // Determine the appropriate error message based on the exception type.
        if (ex instanceof NotFoundException) {
            msg = "Service not found";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        } else {
            msg = "Internal server error";
        }

        // Log the exception details for debugging and monitoring purposes.
        log.error("[Gateway Exception Handler] Request Path: {}, Exception: {}", exchange.getRequest().getPath(), ex.getMessage());

        // Set response headers and status.
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        // Write the error message to the response body.
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(msg)));
        }));
    }
}
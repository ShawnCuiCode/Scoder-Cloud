package com.scoder.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * CacheRequestFilter
 * <p>
 * This filter caches request bodies for non-GET and non-DELETE requests.
 * It is primarily used to support downstream services that may need repeated access to the request body.
 *
 * @author Shawn Cui
 */
@Component
public class CacheRequestFilter extends AbstractGatewayFilterFactory<CacheRequestFilter.Config> {

    /**
     * Constructor for CacheRequestFilter.
     */
    public CacheRequestFilter() {
        super(Config.class);
    }

    /**
     * Specifies the name of the filter.
     *
     * @return The name of the filter.
     */
    @Override
    public String name() {
        return "CacheRequestFilter";
    }

    /**
     * Applies the cache request filter to the GatewayFilterChain.
     *
     * @param config The filter configuration.
     * @return The configured GatewayFilter.
     */
    @Override
    public GatewayFilter apply(Config config) {
        CacheRequestGatewayFilter cacheRequestGatewayFilter = new CacheRequestGatewayFilter();
        Integer order = config.getOrder();
        if (order == null) {
            return cacheRequestGatewayFilter;
        }
        return new OrderedGatewayFilter(cacheRequestGatewayFilter, order);
    }

    /**
     * Specifies the order of fields in the configuration.
     *
     * @return The list of field names in the configuration.
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }

    /**
     * GatewayFilter implementation for caching request bodies.
     * <p>
     * This filter captures and caches the body of non-GET and non-DELETE HTTP requests.
     */
    public static class CacheRequestGatewayFilter implements GatewayFilter {

        /**
         * Filters the incoming request and caches its body for further processing.
         *
         * @param exchange The ServerWebExchange object.
         * @param chain    The GatewayFilterChain to proceed with.
         * @return A Mono signaling completion.
         */
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            // Bypass GET and DELETE methods
            HttpMethod method = exchange.getRequest().getMethod();
            if (method == null || method.matches("GET") || method.matches("DELETE")) {
                return chain.filter(exchange);
            }

            // Read and cache the request body
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .map(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        return bytes;
                    })
                    .defaultIfEmpty(new byte[0])
                    .flatMap(bytes -> {
                        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                if (bytes.length > 0) {
                                    return Flux.just(dataBufferFactory.wrap(bytes));
                                }
                                return Flux.empty();
                            }
                        };
                        return chain.filter(exchange.mutate().request(decorator).build());
                    });
        }
    }

    /**
     * Configuration class for the CacheRequestFilter.
     */
    static class Config {

        /**
         * The order in which the filter is executed.
         */
        private Integer order;

        /**
         * Retrieves the order value.
         *
         * @return The order value.
         */
        public Integer getOrder() {
            return order;
        }

        /**
         * Sets the order value.
         *
         * @param order The order value to set.
         */
        public void setOrder(Integer order) {
            this.order = order;
        }
    }
}
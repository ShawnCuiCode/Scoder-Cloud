package com.scoder.user.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing Jackson's ObjectMapper.
 * This ensures that `Long` and `long` types are serialized as Strings
 * to avoid precision loss in JavaScript clients, where large numbers may be rounded incorrectly.
 *
 * @author Shawn Cui
 */
@Configuration
public class JacksonConfig {

    /**
     * Customizes the Jackson ObjectMapper to serialize `Long` and `long` types as Strings.
     *
     * @return A Jackson2ObjectMapperBuilderCustomizer that modifies the serialization behavior.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                // Serialize `Long` type as String to prevent precision loss
                .serializerByType(Long.class, ToStringSerializer.instance)
                // Serialize `long` primitive type as String
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
    }
}
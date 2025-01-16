package com.scoder.projects.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Jackson to handle serialization of long values as strings.
 * This avoids precision loss when transferring large numbers to the front end.
 *
 * @author Shawn
 */
@Configuration
public class JacksonConfig {

    /**
     * Customizes Jackson's ObjectMapper to serialize Long and long types as strings.
     * This prevents issues caused by JavaScript's inability to handle numbers larger than 2^53 - 1.
     *
     * @return A Jackson2ObjectMapperBuilderCustomizer to apply the custom serialization rules.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                // Serialize Java's Long wrapper type as a string
                .serializerByType(Long.class, ToStringSerializer.instance)
                // Serialize Java's primitive long type as a string
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
    }
}
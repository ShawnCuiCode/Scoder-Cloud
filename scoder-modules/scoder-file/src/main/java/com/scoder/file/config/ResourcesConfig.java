package com.scoder.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Resources Config
 *
 * @author Shawn Cui
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Value("${file.prefix}")
    public String localFilePrefix;
    @Value("${file.path}")
    private String localFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(localFilePrefix + "/**")
                .addResourceLocations("file:" + localFilePath + File.separator);
    }
}

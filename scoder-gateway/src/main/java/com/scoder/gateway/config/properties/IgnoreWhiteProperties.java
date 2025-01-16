package com.scoder.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for the gateway's white-list.
 * Allows specifying paths that bypass authentication checks.
 *
 * @author Shawn Cui
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
public class IgnoreWhiteProperties {

    /**
     * List of white-listed paths.
     * Requests to these paths will bypass gateway authentication.
     */
    private List<String> whites = new ArrayList<>();

    /**
     * Retrieves the white-listed paths.
     *
     * @return a list of white-listed paths.
     */
    public List<String> getWhites() {
        return whites;
    }

    /**
     * Sets the white-listed paths.
     *
     * @param whites a list of paths to be white-listed.
     */
    public void setWhites(List<String> whites) {
        this.whites = whites;
    }
}
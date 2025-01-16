package com.scoder.user.api.factory;

import com.scoder.user.api.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback factory for the User Service.
 * <p>
 * This class provides a mechanism to handle failures in calls to the RemoteUserService.
 * It implements the Feign FallbackFactory interface to capture and log the exception
 * and optionally return a fallback implementation of the service.
 * <p>
 * This ensures that the application can gracefully handle failures without crashing.
 *
 * @author Shawn Cui
 */
@Component // Marks this class as a Spring-managed component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    /**
     * Creates a fallback instance of the RemoteUserService.
     * <p>
     * This method is invoked when a call to the User Service fails. It logs the error
     * and can return a default implementation to handle the failure gracefully.
     *
     * @param throwable the exception that caused the service failure
     * @return a fallback implementation of RemoteUserService, or null if no fallback logic is provided
     */
    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("Fallback: Default response for someMethod:{}", throwable.getMessage());
        return null;
    }
}

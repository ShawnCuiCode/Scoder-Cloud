package com.scoder.im.api.factory;

import com.scoder.im.api.RemoteIMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback factory for the IM (Instant Messaging) service.
 * <p>
 * This class provides a fallback mechanism for the RemoteIMService
 * in case of a service failure. It uses the Feign FallbackFactory interface
 * to handle exceptions and log errors when the service is unavailable.
 * <p>
 * This ensures that the application can gracefully handle failures without crashing.
 *
 * @author Shawn Cui
 */
@Component // Marks this class as a Spring-managed component
public class RemoteIMFallbackFactory implements FallbackFactory<RemoteIMService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteIMFallbackFactory.class);

    /**
     * Creates a fallback instance of the RemoteIMService.
     * <p>
     * This method is invoked when a call to the IM service fails. It logs the error
     * and provides a fallback implementation (if needed).
     *
     * @param throwable the exception that caused the service failure
     * @return a fallback implementation of RemoteIMService or null if no fallback logic is provided
     */
    @Override
    public RemoteIMService create(Throwable throwable) {
        log.error("IM service error:{}", throwable.getMessage());
        return null;
    }
}

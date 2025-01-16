package com.scoder.file.api.factory;

import com.scoder.common.core.domain.R;
import com.scoder.file.api.RemoteFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback handler for the File Service.
 * <p>
 * This class provides a fallback mechanism for the RemoteFileService
 * in case of failures. It implements the Feign FallbackFactory interface
 * to handle exceptions and return default responses when the service is unavailable.
 *
 * @author Shawn Cui
 */
@Component // Marks this class as a Spring-managed component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteFileFallbackFactory.class);

    /**
     * Creates a fallback instance of the RemoteFileService.
     * <p>
     * This method is invoked when the RemoteFileService fails. It logs the exception and
     * provides a default implementation of the service to return a failure response.
     *
     * @param throwable the exception that caused the service failure
     * @return a fallback implementation of the RemoteFileService
     */
    @Override
    public RemoteFileService create(Throwable throwable) {
        // Log the error for debugging
        log.error("File service call failed: {}", throwable.getMessage());

        // Return a fallback implementation
        return file -> R.fail("File upload failed: " + throwable.getMessage());
    }
}
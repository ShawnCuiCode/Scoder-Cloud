package com.scoder.file.api;

import com.scoder.common.core.constant.ServiceNameConstants;
import com.scoder.common.core.domain.R;
import com.scoder.file.api.domain.SysFile;
import com.scoder.file.api.factory.RemoteFileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * RemoteFileService
 * <p>
 * This interface defines the file-related operations exposed via Feign clients.
 * It enables remote calls to the file service using HTTP requests.
 * <p>
 * - FeignClient: Defines the remote service to connect with.
 * - Upload Method: Handles file uploads with multipart/form-data content type.
 * - Fallback: Provides a fallback mechanism for failures using RemoteFileFallbackFactory.
 *
 * @FeignClient annotations specify the service configuration.
 */
@FeignClient(
        contextId = "remoteFileService", // Unique identifier for the Feign client in the application context
        value = ServiceNameConstants.FILE_SERVICE, // Name of the file service registered in the service registry
        fallbackFactory = RemoteFileFallbackFactory.class // Fallback factory for handling failures
)
public interface RemoteFileService {

    /**
     * Upload a file to the remote file service.
     * <p>
     * This method sends a POST request to the `/upload` endpoint of the file service.
     * It consumes multipart/form-data content type to handle file uploads.
     *
     * @param file the file to be uploaded (multipart data)
     * @return a response containing file information (SysFile) wrapped in an R object
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);
}
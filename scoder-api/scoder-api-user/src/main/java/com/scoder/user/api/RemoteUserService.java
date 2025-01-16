package com.scoder.user.api;

import com.scoder.common.core.constant.ServiceNameConstants;
import com.scoder.common.core.domain.R;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.user.api.domain.SysUser;
import com.scoder.user.api.factory.RemoteUserFallbackFactory;
import com.scoder.user.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Remote User Service API.
 * <p>
 * This interface defines methods for interacting with the user service.
 * It uses Feign to simplify HTTP-based service-to-service communication.
 * <p>
 * - @FeignClient: Configures the Feign client for the user service.
 * - Methods: Define HTTP endpoints for user-related operations.
 * <p>
 * Fallback handling is provided by the RemoteUserFallbackFactory to ensure
 * graceful degradation during service failures.
 *
 * @Author Shawn Cui
 */
@FeignClient(
        contextId = "remoteUserService", // Unique identifier for this Feign client
        value = ServiceNameConstants.SYSTEM_SERVICE, // Name of the target service in the registry
        fallbackFactory = RemoteUserFallbackFactory.class // Fallback factory for handling service failures
)
public interface RemoteUserService {

    /**
     * Retrieve user information by username.
     * <p>
     * Sends a GET request to fetch user details based on the provided username.
     *
     * @param username the username to search for
     * @return a response containing LoginUser details or an error message
     */
    @GetMapping(value = "/getUserByUserName/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username);

    /**
     * Register a new user.
     * <p>
     * Sends a POST request to create a new user in the system.
     *
     * @param sysUser the user information to register
     * @return a response containing the registered SysUser details or an error message
     */
    @PostMapping(value = "/register")
    public R<SysUser> registerUser(@RequestBody SysUser sysUser);

    /**
     * Retrieve user information by user ID.
     * <p>
     * Sends a GET request to fetch user details based on the provided user ID.
     *
     * @param userId the ID of the user to search for
     * @return an AjaxResult containing SysUser details or an error message
     */
    @GetMapping("/{userId}")
    public AjaxResult<SysUser> getUserById(@PathVariable(value = "userId") Long userId);
}
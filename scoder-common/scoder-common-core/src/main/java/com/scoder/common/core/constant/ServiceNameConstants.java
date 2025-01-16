package com.scoder.common.core.constant;

/**
 * Service Name Constants
 * <p>
 * This class defines the service names used throughout the application for service-to-service communication.
 * These constants are typically used in Feign clients or service discovery to reference specific services.
 * <p>
 * Using these constants ensures consistency and avoids hardcoding service names in multiple places.
 *
 * @Author Shawn Cui
 */
public class ServiceNameConstants {

    /**
     * Authentication Service
     * <p>
     * This constant represents the name of the authentication service, responsible for user login, token generation, and validation.
     */
    public static final String AUTH_SERVICE = "scoder-auth";

    /**
     * System Service
     * <p>
     * This constant represents the name of the system service, which handles user management and system configuration.
     */
    public static final String SYSTEM_SERVICE = "scoder-user";

    /**
     * File Service
     * <p>
     * This constant represents the name of the file service, used for file upload, download, and management.
     */
    public static final String FILE_SERVICE = "scoder-file";

    /**
     * Instant Messaging Service
     * <p>
     * This constant represents the name of the IM service, responsible for real-time messaging and communication.
     */
    public static final String IM_SERVICE = "scoder-im";
}
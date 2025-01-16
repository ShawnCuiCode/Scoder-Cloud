package com.scoder.common.core.constant;

/**
 * HTTP Status Codes
 * <p>
 * This class defines commonly used HTTP status codes as constants.
 * These codes represent the outcome of HTTP requests and can be
 * used throughout the application for standardized status handling.
 *
 * @Author Shawn Cui
 */
public class HttpStatus {

    /**
     * Operation successful.
     */
    public static final int SUCCESS = 200;

    /**
     * Object successfully created.
     */
    public static final int CREATED = 201;

    /**
     * Request has been accepted but not yet processed.
     */
    public static final int ACCEPTED = 202;

    /**
     * Operation completed successfully but no data is returned.
     */
    public static final int NO_CONTENT = 204;

    /**
     * Resource has been permanently moved.
     */
    public static final int MOVED_PERM = 301;

    /**
     * Redirection to a different resource.
     */
    public static final int SEE_OTHER = 303;

    /**
     * Resource has not been modified since the last request.
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * Bad request due to invalid parameters (e.g., missing or incorrect format).
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Unauthorized access (e.g., missing or invalid authentication).
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Forbidden access due to insufficient permissions or expired authorization.
     */
    public static final int FORBIDDEN = 403;

    /**
     * Resource or service not found.
     */
    public static final int NOT_FOUND = 404;

    /**
     * HTTP method not allowed for the requested resource.
     */
    public static final int BAD_METHOD = 405;

    /**
     * Resource conflict or resource is locked.
     */
    public static final int CONFLICT = 409;

    /**
     * Unsupported data or media type.
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * Internal server error.
     */
    public static final int ERROR = 500;

    /**
     * API not implemented.
     */
    public static final int NOT_IMPLEMENTED = 501;
}
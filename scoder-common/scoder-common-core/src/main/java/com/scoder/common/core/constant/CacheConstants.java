package com.scoder.common.core.constant;

/**
 * Constants for cache keys and authorization headers.
 * <p>
 * This class defines constants used throughout the application for
 * managing cache keys and authorization-related headers.
 * <p>
 * These constants help ensure consistency and avoid hardcoding
 * repeated string literals across the codebase.
 *
 * @author Shawn Cui
 */
public class CacheConstants {

    /**
     * Custom identifier for the token in request headers.
     * This is typically used to fetch the authorization token from incoming HTTP requests.
     */
    public static final String HEADER = "Authorization";

    /**
     * Prefix for the token in authorization headers.
     * Commonly used in Bearer token-based authentication schemes.
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Key to store or retrieve the user's ID.
     * This is often used in token claims or request attributes.
     */
    public static final String DETAILS_USER_ID = "userId ";

    /**
     * Key to store or retrieve the username.
     * Similar to user ID, this is commonly used in token claims or attributes.
     */
    public static final String DETAILS_USERNAME = "userName ";

    /**
     * Prefix for storing login tokens in the cache.
     * Tokens are typically stored with this prefix to organize and manage them in the caching system.
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * Header field for storing authorization information.
     * This is another way to refer to the authorization header.
     */
    public static final String AUTHORIZATION_HEADER = "authorization";
}
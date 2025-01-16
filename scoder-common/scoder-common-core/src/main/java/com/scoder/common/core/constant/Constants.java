package com.scoder.common.core.constant;

/**
 * Common constants used across the application.
 *
 * @author Shawn Cui
 */
public class Constants {
    /**
     * UTF-8 character encoding
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK character encoding
     */
    public static final String GBK = "GBK";

    /**
     * HTTP protocol prefix
     */
    public static final String HTTP = "http://";

    /**
     * HTTPS protocol prefix
     */
    public static final String HTTPS = "https://";

    /**
     * Success response code
     */
    public static final Integer SUCCESS = 200;

    /**
     * Failure response code
     */
    public static final Integer FAIL = 500;

    /**
     * Message for successful login
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * Message for logout
     */
    public static final String LOGOUT = "Logout";

    /**
     * Message for user registration
     */
    public static final String REGISTER = "Register";

    /**
     * Message for failed login
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * Key for the current page number in pagination
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Key for the number of records per page in pagination
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * Key for sorting column
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * Key for sorting direction, "desc" or "asc"
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Redis key for captcha
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * Captcha expiration time in minutes
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * Token expiration time in minutes
     */
    public static final long TOKEN_EXPIRE = 720;

    /**
     * Authorization header key for token
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * Token key
     */
    public static final String TOKEN = "token";

    /**
     * Token prefix for Bearer authentication
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Key for user ID
     */
    public static final String USER_ID = "userId";

    /**
     * Key for logged-in user details
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * Key for system configuration in Redis
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * Key for system dictionary in Redis
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * Key for team skills data
     */
    public static final String TEAM_SKILLS = "sys_dict:team_skills";

    /**
     * Key for team goals data
     */
    public static final String TEAM_GOALS = "sys_dict:team_goals";

    /**
     * Key for team goals data
     */
    public static final String TEAM_EXPERIENCE = "sys_dict:team_experience";

    /**
     * Prefix for resource paths
     */
    public static final String RESOURCE_PREFIX = "/profile";
}
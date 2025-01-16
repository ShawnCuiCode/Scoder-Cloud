package com.scoder.common.core.constant;

/**
 * User Constants
 * <p>
 * This class defines constants related to user management, including status codes,
 * menu types, validation rules, and other system-specific flags.
 * <p>
 * These constants help standardize the handling of user-related information
 * and reduce hardcoding throughout the application.
 *
 * @Author Shawn Cui
 */
public class UserConstants {

    /**
     * Unique identifier for system users within the platform.
     */
    public static final String SYS_USER = "SYS_USER";

    /**
     * Normal status.
     */
    public static final String NORMAL = "0";

    /**
     * Exception status.
     */
    public static final String EXCEPTION = "1";

    /**
     * User disabled status.
     */
    public static final String USER_DISABLE = "1";

    /**
     * Role disabled status.
     */
    public static final String ROLE_DISABLE = "1";

    /**
     * Department normal status.
     */
    public static final String DEPT_NORMAL = "0";

    /**
     * Department disabled status.
     */
    public static final String DEPT_DISABLE = "1";

    /**
     * Dictionary normal status.
     */
    public static final String DICT_NORMAL = "0";

    /**
     * Indicates whether it is a system default (Yes).
     */
    public static final String YES = "Y";

    /**
     * Indicates whether the menu is an external link (Yes).
     */
    public static final String YES_FRAME = "0";

    /**
     * Indicates whether the menu is an external link (No).
     */
    public static final String NO_FRAME = "1";

    /**
     * Menu type: Directory.
     */
    public static final String TYPE_DIR = "M";

    /**
     * Menu type: Menu.
     */
    public static final String TYPE_MENU = "C";

    /**
     * Menu type: Button.
     */
    public static final String TYPE_BUTTON = "F";

    /**
     * Layout component identifier.
     */
    public static final String LAYOUT = "Layout";

    /**
     * ParentView component identifier.
     */
    public static final String PARENT_VIEW = "ParentView";

    /**
     * Validation result code: Unique.
     */
    public static final String UNIQUE = "0";

    /**
     * Validation result code: Not unique.
     */
    public static final String NOT_UNIQUE = "1";

    /**
     * Username length limits.
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * Password length limits.
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;
}
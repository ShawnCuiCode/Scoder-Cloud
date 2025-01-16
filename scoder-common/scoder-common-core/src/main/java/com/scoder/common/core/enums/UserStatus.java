package com.scoder.common.core.enums;

/**
 * Enum representing user status.
 * <p>
 * This enum defines the possible states of a user in the system, including:
 * - OK: Active and functioning normally.
 * - DISABLE: Disabled and access is restricted.
 * - DELETED: Deleted and no longer available in the system.
 * <p>
 * Each status has a unique code and a description for clarity.
 *
 * @author Shawn Cui
 */
public enum UserStatus {
    /**
     * Active status.
     */
    OK("0", "Active"),

    /**
     * Disabled status.
     */
    DISABLE("1", "Disabled"),

    /**
     * Deleted status.
     */
    DELETED("2", "Deleted");

    /**
     * The unique code representing the status.
     */
    private final String code;

    /**
     * A description of the status.
     */
    private final String info;

    /**
     * Constructor for the UserStatus enum.
     *
     * @param code the unique code for the status
     * @param info the description of the status
     */
    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * Retrieves the code of the user status.
     *
     * @return the status code
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the description of the user status.
     *
     * @return the status description
     */
    public String getInfo() {
        return info;
    }
}
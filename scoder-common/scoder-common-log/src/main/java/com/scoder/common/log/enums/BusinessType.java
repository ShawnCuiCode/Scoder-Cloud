package com.scoder.common.log.enums;

/**
 * Enum representing different types of business operations.
 * <p>
 * This enum is used to classify various business operations in logging and auditing.
 *
 * @author Shawn Cui
 */
public enum BusinessType {
    /**
     * Other operations that don't fit into predefined categories.
     */
    OTHER,

    /**
     * Insert operation, typically used for creating new records.
     */
    INSERT,

    /**
     * Update operation, typically used for modifying existing records.
     */
    UPDATE,

    /**
     * Delete operation, typically used for removing records.
     */
    DELETE,

    /**
     * Grant operation, typically used for assigning permissions or roles.
     */
    GRANT,

    /**
     * Force operation, such as forcibly logging out a user.
     */
    FORCE,

    /**
     * Clean operation, such as clearing all data.
     */
    CLEAN
}
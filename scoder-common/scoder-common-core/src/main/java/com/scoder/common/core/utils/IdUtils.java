package com.scoder.common.core.utils;

import com.scoder.common.core.text.UUID;

/**
 * Utility class for generating unique identifiers (UUIDs).
 * <p>
 * This class provides methods to generate different types of UUIDs, including
 * standard UUIDs, simplified UUIDs without hyphens, and high-performance UUIDs
 * using `ThreadLocalRandom`.
 *
 * @author Shawn Cui
 */
public class IdUtils {

    /**
     * Generates a random UUID.
     * <p>
     * The UUID is a 128-bit identifier represented as a string in the standard
     * 36-character format (e.g., `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`).
     *
     * @return a random UUID in string format
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates a simplified UUID.
     * <p>
     * This UUID is similar to a standard UUID but without the hyphens, resulting
     * in a 32-character string (e.g., `xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`).
     *
     * @return a simplified UUID in string format
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * Generates a high-performance random UUID.
     * <p>
     * This UUID is generated using `ThreadLocalRandom` for better performance,
     * especially in multithreaded environments. The format is the standard
     * 36-character UUID string.
     *
     * @return a high-performance random UUID in string format
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * Generates a high-performance simplified UUID.
     * <p>
     * This UUID is similar to a standard UUID but without the hyphens, and it
     * is generated using `ThreadLocalRandom` for better performance. The result
     * is a 32-character string.
     *
     * @return a high-performance simplified UUID in string format
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }
}
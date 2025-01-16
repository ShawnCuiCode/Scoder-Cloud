package com.scoder.common.core.constant;

/**
 * Constants for Task Scheduling.
 * <p>
 * This class defines common constants used in task scheduling, including
 * keys for task properties and misfire handling strategies. It also includes
 * an enumeration for task statuses.
 *
 * @Author Shawn Cui
 */
public class ScheduleConstants {

    /**
     * Key for the class name of the task.
     */
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /**
     * Key for the properties associated with the task.
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * Default misfire handling strategy.
     * Indicates the scheduler will use its default behavior for misfires.
     */
    public static final String MISFIRE_DEFAULT = "0";

    /**
     * Misfire handling strategy: Execute the task immediately.
     * Ignores any missed triggers and triggers the task right away.
     */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /**
     * Misfire handling strategy: Trigger the task once.
     * Executes the task once for the missed execution time.
     */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /**
     * Misfire handling strategy: Do nothing.
     * Does not trigger the task for the missed execution time.
     */
    public static final String MISFIRE_DO_NOTHING = "3";

    /**
     * Enum representing the status of a task.
     */
    public enum Status {
        /**
         * Normal status: Task is active and running as scheduled.
         */
        NORMAL("0"),

        /**
         * Paused status: Task is temporarily halted.
         */
        PAUSE("1");

        private final String value;

        /**
         * Constructor for Status enum.
         *
         * @param value the string representation of the status
         */
        private Status(String value) {
            this.value = value;
        }

        /**
         * Retrieves the string value of the status.
         *
         * @return the status value
         */
        public String getValue() {
            return value;
        }
    }
}
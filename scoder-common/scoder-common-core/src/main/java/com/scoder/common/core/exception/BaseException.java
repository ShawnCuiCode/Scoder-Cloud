package com.scoder.common.core.exception;

/**
 * Base Exception
 * <p>
 * This class serves as the foundation for custom exceptions in the application.
 * It provides a structure to include additional information, such as:
 * - Module: The module or component where the exception occurred.
 * - Code: A specific error code for identifying the exception type.
 * - Args: Additional parameters associated with the error code.
 * - DefaultMessage: A default error message for the exception.
 * <p>
 * Subclasses can extend this base class to represent specific types of exceptions.
 *
 * @author Shawn Cui
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * The module where the exception occurred.
     */
    private String module;

    /**
     * The error code associated with the exception.
     */
    private String code;

    /**
     * Parameters related to the error code for dynamic messages.
     */
    private Object[] args;

    /**
     * The default message for the exception.
     */
    private String defaultMessage;

    /**
     * Constructs a BaseException with all details.
     *
     * @param module         the module where the exception occurred
     * @param code           the specific error code
     * @param args           additional parameters for the error code
     * @param defaultMessage the default message for the exception
     */
    public BaseException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Constructs a BaseException without a default message.
     *
     * @param module the module where the exception occurred
     * @param code   the specific error code
     * @param args   additional parameters for the error code
     */
    public BaseException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    /**
     * Constructs a BaseException with a module and default message.
     *
     * @param module         the module where the exception occurred
     * @param defaultMessage the default message for the exception
     */
    public BaseException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    /**
     * Constructs a BaseException with a code and parameters, without specifying a module.
     *
     * @param code the specific error code
     * @param args additional parameters for the error code
     */
    public BaseException(String code, Object[] args) {
        this(null, code, args, null);
    }

    /**
     * Constructs a BaseException with only a default message.
     *
     * @param defaultMessage the default message for the exception
     */
    public BaseException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    /**
     * Retrieves the module where the exception occurred.
     *
     * @return the module name
     */
    public String getModule() {
        return module;
    }

    /**
     * Retrieves the error code associated with the exception.
     *
     * @return the error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the parameters associated with the error code.
     *
     * @return the parameters array
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Retrieves the default message for the exception.
     *
     * @return the default error message
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }
}
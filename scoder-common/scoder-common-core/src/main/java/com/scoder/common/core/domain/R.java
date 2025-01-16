package com.scoder.common.core.domain;

import com.scoder.common.core.constant.Constants;

import java.io.Serializable;

/**
 * Generic Response Wrapper (R)
 * <p>
 * This class serves as a standardized structure for API responses.
 * It includes a status code, message, and optional data payload.
 * <p>
 * - `code`: Represents the response status (e.g., success or failure).
 * - `msg`: Contains a message describing the result of the operation.
 * - `data`: Holds the response data (if applicable).
 * <p>
 * Utility methods like `ok` and `fail` are provided for constructing
 * response objects in a consistent and concise manner.
 *
 * @param <T> The type of the data payload in the response.
 * @Author Shawn Cui
 */
public class R<T> implements Serializable {
    /**
     * Success status code, derived from Constants.
     */
    public static final int SUCCESS = Constants.SUCCESS;
    /**
     * Failure status code, derived from Constants.
     */
    public static final int FAIL = Constants.FAIL;
    private static final long serialVersionUID = 1L;
    /**
     * Status code of the response.
     */
    private int code;

    /**
     * Message providing additional information about the response.
     */
    private String msg;

    /**
     * The data payload of the response.
     */
    private T data;

    /**
     * Creates a success response with no data or message.
     *
     * @param <T> The type of the response data.
     * @return A success response object.
     */
    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    /**
     * Creates a success response with data but no message.
     *
     * @param data The response data.
     * @param <T>  The type of the response data.
     * @return A success response object containing the data.
     */
    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    /**
     * Creates a success response with data and a custom message.
     *
     * @param data The response data.
     * @param msg  The custom message.
     * @param <T>  The type of the response data.
     * @return A success response object containing the data and message.
     */
    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    /**
     * Creates a failure response with no data or message.
     *
     * @param <T> The type of the response data.
     * @return A failure response object.
     */
    public static <T> R<T> fail() {
        return restResult(null, FAIL, null);
    }

    /**
     * Creates a failure response with a custom message.
     *
     * @param msg The custom message.
     * @param <T> The type of the response data.
     * @return A failure response object containing the message.
     */
    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    /**
     * Creates a failure response with data but no message.
     *
     * @param data The response data.
     * @param <T>  The type of the response data.
     * @return A failure response object containing the data.
     */
    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    /**
     * Creates a failure response with data and a custom message.
     *
     * @param data The response data.
     * @param msg  The custom message.
     * @param <T>  The type of the response data.
     * @return A failure response object containing the data and message.
     */
    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    /**
     * Creates a failure response with a custom status code and message.
     *
     * @param code The custom status code.
     * @param msg  The custom message.
     * @param <T>  The type of the response data.
     * @return A failure response object with the status code and message.
     */
    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    /**
     * Constructs a response object with the specified data, status code, and message.
     *
     * @param data The response data.
     * @param code The status code.
     * @param msg  The message.
     * @param <T>  The type of the response data.
     * @return The constructed response object.
     */
    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    // Getters and setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
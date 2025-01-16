package com.scoder.common.core.web.domain;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents a standard response object for API operations.
 *
 * @param <T> The type of the data included in the response.
 * @author Shawn Cui
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("API response object")
public class AjaxResult<T> {

    private static final long serialVersionUID = 1L;

    /**
     * The status code of the response.
     */
    @ApiModelProperty("Response status code")
    private int code;

    /**
     * The message of the response.
     */
    @ApiModelProperty("Response message")
    private String msg;

    /**
     * The data object included in the response.
     */
    @ApiModelProperty("Response data object")
    private T data;

    /**
     * Constructs a new AjaxResult with the specified code and message.
     *
     * @param code the status code
     * @param msg  the message
     */
    public AjaxResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Creates a success response with no data.
     *
     * @return a success response
     */
    public static AjaxResult<Void> success() {
        return AjaxResult.success("success");
    }

    /**
     * Creates a success response with the specified data.
     *
     * @param data the response data
     * @return a success response
     */
    public static <T> AjaxResult<T> success(T data) {
        return AjaxResult.success("success", data);
    }

    /**
     * Creates a success response with the specified message.
     *
     * @param msg the response message
     * @return a success response
     */
    public static AjaxResult<Void> success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * Creates a success response with the specified message and data.
     *
     * @param msg  the response message
     * @param data the response data
     * @return a success response
     */
    public static <T> AjaxResult<T> success(String msg, T data) {
        return new AjaxResult<>(HttpStatus.HTTP_OK, msg, data);
    }

    /**
     * Creates an error response with no additional details.
     *
     * @return an error response
     */
    public static AjaxResult<Void> error() {
        return AjaxResult.error("fail");
    }

    /**
     * Creates an error response with the specified message.
     *
     * @param msg the response message
     * @return an error response
     */
    public static AjaxResult<Void> error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * Creates an error response with the specified message and data.
     *
     * @param msg  the response message
     * @param data the response data
     * @return an error response
     */
    public static <T> AjaxResult<T> error(String msg, T data) {
        return new AjaxResult<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    /**
     * Creates an error response with the specified status code and message.
     *
     * @param code the status code
     * @param msg  the response message
     * @return an error response
     */
    public static AjaxResult<Void> error(int code, String msg) {
        return new AjaxResult<>(code, msg, null);
    }
}
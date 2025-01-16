package com.scoder.common.security.handler;

import com.scoder.common.core.exception.BaseException;
import com.scoder.common.core.exception.CustomException;
import com.scoder.common.core.exception.DemoModeException;
import com.scoder.common.core.exception.PreAuthorizeException;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.core.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global Exception Handler
 * <p>
 * Handles all exceptions throughout the application and provides unified error responses.
 *
 * @author Shawn Cui
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle BaseException
     *
     * @param e BaseException instance
     * @return AjaxResult with the error message
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult handleBaseException(BaseException e) {
        log.error("BaseException: {}", e.getMessage(), e);
        return AjaxResult.error(e.getDefaultMessage());
    }

    /**
     * Handle CustomException
     *
     * @param e CustomException instance
     * @return AjaxResult with the error code and message
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage(), e);
        if (StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    /**
     * Handle general Exception
     *
     * @param e Exception instance
     * @return AjaxResult with the error message
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * Handle BindException (validation errors during binding)
     *
     * @param e BindException instance
     * @return AjaxResult with the first validation error message
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        log.error("BindException: {}", e.getMessage(), e);
        String message = e.getAllErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation error");
        return AjaxResult.error(message);
    }

    /**
     * Handle MethodArgumentNotValidException (validation errors in method arguments)
     *
     * @param e MethodArgumentNotValidException instance
     * @return AjaxResult with the first field validation error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage(), e);
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("Validation error");
        return AjaxResult.error(message);
    }

    /**
     * Handle PreAuthorizeException (authorization issues)
     *
     * @param e PreAuthorizeException instance
     * @return AjaxResult with a permission error message
     */
    @ExceptionHandler(PreAuthorizeException.class)
    public AjaxResult handlePreAuthorizeException(PreAuthorizeException e) {
        log.warn("PreAuthorizeException: {}", e.getMessage(), e);
        return AjaxResult.error("You do not have permission. Please contact the administrator.");
    }

    /**
     * Handle DemoModeException
     *
     * @param e DemoModeException instance
     * @return AjaxResult with a demo mode restriction message
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e) {
        log.warn("DemoModeException: {}", e.getMessage(), e);
        return AjaxResult.error("Operation not allowed in demo mode.");
    }
}
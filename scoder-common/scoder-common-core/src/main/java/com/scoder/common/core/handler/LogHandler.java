package com.scoder.common.core.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Log handler for capturing and logging Controller-level operations.
 * Simplified for basic logging purposes.
 *
 * @author Shawn Cui
 */
@Aspect
@Component
@Slf4j
public class LogHandler {

    /**
     * Log details before Controller method execution.
     */
    @Before("execution(* com.scoder..*Controller.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // Prepare request parameters for logging
        String params = extractArguments(joinPoint);

        log.info("Request Path: [{}], Request Parameters: [Header: {}, Body: {}]",
                request.getServletPath(),
                request.getHeader("Authorization"),
                params);
    }

    /**
     * Log details after Controller method execution.
     */
    @After("execution(* com.scoder..*Controller.*(..))")
    public void afterController() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("Request Completed: [{}]", request.getServletPath());
    }

    /**
     * Around advice to capture execution details of Controller methods.
     */
    @Around("execution(* com.scoder..*Controller.*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        // Execute the target method
        Object result = joinPoint.proceed();

        // Log the result (if necessary)
        log.info("Method executed, result: {}", result);

        return result;
    }

    /**
     * Log details when an exception is thrown in Controller methods.
     */
    @AfterThrowing(value = "execution(* com.scoder..*Controller.*(..))", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // Prepare request parameters for logging
        String params = extractArguments(point);

        log.error("Request Error Path: [{}], Request Parameters: [Header: {}, Body: {}], Exception: [{}]",
                request.getServletPath(),
                request.getHeader("Authorization"),
                params,
                e.getMessage());
    }

    /**
     * Extract and format method arguments for logging.
     *
     * @param joinPoint The join point containing method arguments.
     * @return JSON string of method arguments.
     */
    private String extractArguments(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        String params = "";
        if (arguments != null && arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] instanceof ServletRequest ||
                        arguments[i] instanceof ServletResponse ||
                        arguments[i] instanceof MultipartFile) {
                    continue; // Skip request/response and file arguments
                }
                try {
                    params = JSON.toJSONString(arguments);
                } catch (Exception e) {
                    params = arguments.toString();
                }
            }
        }
        return params;
    }
}
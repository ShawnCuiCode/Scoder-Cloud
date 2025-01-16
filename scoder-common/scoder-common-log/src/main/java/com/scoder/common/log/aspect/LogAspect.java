package com.scoder.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.log.annotation.Log;
import com.scoder.common.log.service.AsyncLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Aspect for handling operation log annotations.
 * <p>
 * Automatically logs method calls annotated with {@link Log} and processes exceptions if thrown.
 *
 * @author Shawn Cui
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private AsyncLogService asyncLogService;

    /**
     * Pointcut for methods annotated with @Log.
     */
    @Pointcut("@annotation(com.scoder.common.log.annotation.Log)")
    public void logPointCut() {
        // Pointcut definition
    }

    /**
     * Handles successful method execution for methods annotated with @Log.
     *
     * @param joinPoint  the join point of the method
     * @param jsonResult the result returned by the method
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * Handles exceptions thrown by methods annotated with @Log.
     *
     * @param joinPoint the join point of the method
     * @param exception the exception thrown
     */
    @AfterThrowing(value = "logPointCut()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        handleLog(joinPoint, exception, null);
    }

    /**
     * Processes the log for the executed method.
     *
     * @param joinPoint  the join point of the method
     * @param exception  the exception thrown, if any
     * @param jsonResult the result returned by the method
     */
    private void handleLog(final JoinPoint joinPoint, final Exception exception, Object jsonResult) {
        try {
            // Retrieve the @Log annotation
            Log logAnnotation = getAnnotationLog(joinPoint);
            if (logAnnotation == null) {
                return;
            }

            // Handle logging logic (e.g., save logs, async logging)
            String methodName = joinPoint.getSignature().toShortString();
            String args = argsArrayToString(joinPoint.getArgs());

            logger.info("Method: {}, Arguments: {}, Result: {}, Exception: {}",
                    methodName, args, jsonResult, exception != null ? exception.getMessage() : "None");


        } catch (Exception exp) {
            logger.error("Exception in LogAspect handleLog: {}", exp.getMessage(), exp);
        }
    }

    /**
     * Retrieves the @Log annotation from the method.
     *
     * @param joinPoint the join point of the method
     * @return the Log annotation, or null if not present
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return null;
        }
        Method method = ((MethodSignature) signature).getMethod();
        return method != null ? method.getAnnotation(Log.class) : null;
    }

    /**
     * Converts method arguments to a string representation.
     *
     * @param paramsArray the array of method arguments
     * @return a string representation of the arguments
     */
    private String argsArrayToString(Object[] paramsArray) {
        if (paramsArray == null || paramsArray.length == 0) {
            return "";
        }

        StringBuilder params = new StringBuilder();
        for (Object param : paramsArray) {
            if (StringUtils.isNotNull(param) && !isFilterObject(param)) {
                try {
                    params.append(JSON.toJSONString(param)).append(" ");
                } catch (Exception ignored) {
                    // Ignore serialization errors
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * Determines if an object should be filtered from logging.
     *
     * @param obj the object to check
     * @return true if the object should be filtered, false otherwise
     */
    private boolean isFilterObject(Object obj) {
        if (obj instanceof MultipartFile || obj instanceof HttpServletRequest || obj instanceof HttpServletResponse || obj instanceof BindingResult) {
            return true;
        }
        if (obj instanceof Collection) {
            for (Object item : (Collection<?>) obj) {
                if (item instanceof MultipartFile) {
                    return true;
                }
            }
        }
        if (obj instanceof Map) {
            for (Object value : ((Map<?, ?>) obj).values()) {
                if (value instanceof MultipartFile) {
                    return true;
                }
            }
        }
        return false;
    }
}
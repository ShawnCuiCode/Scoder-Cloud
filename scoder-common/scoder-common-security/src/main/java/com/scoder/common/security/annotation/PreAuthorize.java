package com.scoder.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 *
 * @author Shawn Cui
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

    public String hasPermi() default "";

    public String lacksPermi() default "";

    public String[] hasAnyPermi() default {};

    public String hasRole() default "";

    public String lacksRole() default "";

    public String[] hasAnyRoles() default {};
}

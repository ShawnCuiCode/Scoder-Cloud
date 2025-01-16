package com.scoder.common.log.annotation;

import com.scoder.common.log.enums.BusinessType;
import com.scoder.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author Shawn Cui
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    public String title() default "";


    public BusinessType businessType() default BusinessType.OTHER;


    public OperatorType operatorType() default OperatorType.MANAGE;


    public boolean isSaveRequestData() default true;
}

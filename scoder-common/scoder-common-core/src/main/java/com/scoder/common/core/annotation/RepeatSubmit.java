package com.scoder.common.core.annotation;

import java.lang.annotation.*;

/**
 * @author Shawn Cui
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

}

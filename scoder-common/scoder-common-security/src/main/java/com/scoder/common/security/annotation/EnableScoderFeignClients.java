package com.scoder.common.security.annotation;

import com.scoder.common.security.feign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * @author Shawn Cui
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableScoderFeignClients {
    String[] value() default {};

    String[] basePackages() default {"com.scoder"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {FeignAutoConfiguration.class};

    Class<?>[] clients() default {};
}

package com.security.login.config;

import java.lang.annotation.*;

/**
 *忽略token校验
 *
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSecurity {
}

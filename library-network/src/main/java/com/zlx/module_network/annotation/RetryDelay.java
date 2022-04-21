package com.zlx.module_network.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Target(METHOD)
@Retention(RUNTIME)
public @interface RetryDelay {

    long value() default 0;
}

package com.vectory.common.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckLoginAnno {
    String message() default "当前无用户登录";
}
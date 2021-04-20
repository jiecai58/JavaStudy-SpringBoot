package com.study.interceptor.annotation;

import com.study.interceptor.enum1.DesensitionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {
    DesensitionType type();

    String[] attach() default "";

}

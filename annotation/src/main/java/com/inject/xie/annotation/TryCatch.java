package com.inject.xie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface TryCatch {
    String[] target();
    boolean after() default false;
    String exceptionDesc() default  "";
    boolean extend() default false;
}

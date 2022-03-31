package com.nsu.stu.meet.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {
    Class<?> clazz();
    boolean half() default false;
}

package com.nsu.stu.meet.annotation;


import com.nsu.stu.meet.common.base.LimitBaseService;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {
    Class clazz();
}

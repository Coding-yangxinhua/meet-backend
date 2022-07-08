package com.nsu.stu.meet.annotation;

import java.lang.annotation.*;

/**
 * @author Xinhua X Yang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface LimitQuery {
    String userColumn() default "user_id";
    String limitColumn() default "limit_id";
}

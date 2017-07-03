package com.lanwei.haq.comm.annotation;

import java.lang.annotation.*;

/**
 * @作者: liuxinyun
 * @日期: 2017/6/15 15:06
 * @描述:
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String description() default "";
}

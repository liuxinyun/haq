package com.lanwei.haq.comm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解此参数是新增的实体，如果被注解的参数是BaseEntity的字类，则
 * 该实体的继承属性会被赋值
 *
 * @author liuxinyun
 * @created 2016/12/26 11:21
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddEntity {

    String value() default "";

}

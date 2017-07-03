package com.lanwei.haq.comm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在controller层的实体添加此属性，会自动注入当前的用户，
 * 参数类型必须为@{link com.com.huajinyuce.ymr.entity.user.UserEntity}
 *
 * @author liuxinyun
 * @created 2016/12/26 11:16
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

    String value() default "";
}

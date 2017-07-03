package com.lanwei.haq.comm.util.Excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @类名 Excel 注解类
 * @作者 张世通
 * @时间 2016年8月11日 下午5:31:01
 * @version 1.0
 * @描述 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

	String cellName() default "";

	int cellNums() default 0;
}

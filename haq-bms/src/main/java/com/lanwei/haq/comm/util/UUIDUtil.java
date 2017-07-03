package com.lanwei.haq.comm.util;

import java.util.UUID;

/**
 * @类名：UUIDUtil.java
 * @作者：one
 * @时间：2016年4月5日 下午8:32:32
 * @版权：pengkaione@icloud.com
 * @描述： 
 */
public class UUIDUtil {

	/**
	 * 获取guid
	 * @return
	 */
	public static String getUUID32(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取guid
	 * @return
	 */
	public static String getUUID36(){
		return UUID.randomUUID().toString();
	}
	
}

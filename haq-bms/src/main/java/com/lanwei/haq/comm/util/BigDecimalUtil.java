package com.lanwei.haq.comm.util;

import java.math.BigDecimal;

/**
* @类名：BigDecimalUtil.java
* @作者：one
* @时间：2016年6月22日 上午11:30:17
* @版权：pengkaione@icloud.com
* @描述： 
*/
public class BigDecimalUtil {
	
	/**
	 * 完整精度计算  加法
	 * @param bigDecimalA 
	 * @param bigDecimalB
	 * @return
	 */
	public static BigDecimal add(BigDecimal bigDecimalA,BigDecimal bigDecimalB){
		if(bigDecimalA!=null && bigDecimalB!=null)
			return bigDecimalA.add(bigDecimalB);
		return null;
	}
	
	/**
	 * 完整精度计算  减法
	 * @param bigDecimalA 被减数
	 * @param bigDecimalB 减数
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal bigDecimalA,BigDecimal bigDecimalB){
		if(bigDecimalA!=null && bigDecimalB!=null)
			return bigDecimalA.subtract(bigDecimalB);
		return null;
	}
	
	/**
	 * 完整精度计算  乘法
	 * @param bigDecimalA 
	 * @param bigDecimalB  
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal bigDecimalA,BigDecimal bigDecimalB){
		if(bigDecimalA!=null && bigDecimalB!=null)
			return bigDecimalA.multiply(bigDecimalB);
		return null;
	}
	
	/**
	 * 完整精度计算  除法
	 * @param bigDecimalA 被除数
	 * @param bigDecimalB 除数
	 * @return
	 */
	public static BigDecimal divide(BigDecimal bigDecimalA,BigDecimal bigDecimalB){
		if(bigDecimalA!=null && bigDecimalB!=null)
			return bigDecimalA.divide(bigDecimalB,30,BigDecimal.ROUND_HALF_EVEN);
		return null;
	}
}

package com.lanwei.haq.comm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static String F_YEAR_MM_DD = "yyyy-MM-dd";
	private static String F_YEAR_MM_DD_H_M_S = "yyyy-MM-dd HH:mm:ss";
	private static String F_YEARMMDDHMS = "yyyyMMddHHmmss";
	private static String F_YEARMMDD = "yyyyMMdd";
	private static String F_YEAR_MM_DD_H_M= "yyyy-MM-dd HH:mm";
	private static SimpleDateFormat YEAR_MM_DD = new SimpleDateFormat(F_YEAR_MM_DD);
	private static SimpleDateFormat YEAR_MM_DD_H_M_S = new SimpleDateFormat(F_YEAR_MM_DD_H_M_S);
	private static SimpleDateFormat YEARMMDDHMS = new SimpleDateFormat(F_YEARMMDDHMS);
	private static SimpleDateFormat YEARMMDD = new SimpleDateFormat(F_YEARMMDD);
	private static SimpleDateFormat YEAR_MM_DD_H_M = new SimpleDateFormat(F_YEAR_MM_DD_H_M);

	/**
	 * 
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:51:22
	 * @version 1.0
	 * @param edc
	 * @return
	 */
	public static Date paseDate(EnumDateCode edc,String date){
		try{
			switch (edc) {
				case YEAR_MM_DD :
					return YEAR_MM_DD.parse(date);
				case YEAR_MM_DD_HH_MM_SS :
					return YEAR_MM_DD_H_M_S.parse(date);
				case YEARMMDDHHMMSS :
					return YEARMMDDHMS.parse(date);
				case YEARMMDD :
					return YEARMMDD.parse(date);
				case YEAR_MM_DD_HH_MM :
					return YEAR_MM_DD_H_M.parse(date);
			}
		}catch(Exception e){}
		 return null;
	}
	
	/**
	 * 返回全标准格式时间字符
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:26:12
	 * @version 1.0
	 * @return
	 */
	public static String formatDate(){
		return format(EnumDateCode.YEAR_MM_DD_HH_MM_SS,new Date());
	}
	/**
	 * 返回全标准格式时间字符
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:26:12
	 * @version 1.0
	 * @return
	 */
	public static String formatDate(EnumDateCode edc){
		return format(edc,new Date());
	}
	
	/**
	 * 返回全标准格式时间字符  yyyy-MM-dd HH:mm:ss
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:26:12
	 * @version 1.0
	 * @return
	 */
	public static String formatDate(Date date){
		return format(EnumDateCode.YEAR_MM_DD_HH_MM_SS,date);
	}
	
	/**
	 * 返回全标准格式时间字符
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:26:12
	 * @version 1.0
	 * @return
	 */
	public static String formatDate(EnumDateCode edc,Date date){
		return format(edc,date);
	}
	
	/**
	 * 内部使用工具
	 * @description 
	 * @author  王童博
	 * @date 2016年8月16日下午5:46:03
	 * @version 1.0
	 * @param edc
	 * @param date
	 * @return
	 */
	private static String format(EnumDateCode edc,Date date){
		try{
			switch (edc) {
				case YEAR_MM_DD :
					return YEAR_MM_DD.format(date);
				case YEAR_MM_DD_HH_MM_SS :
					return YEAR_MM_DD_H_M_S.format(date);
				case YEARMMDDHHMMSS :
					return YEARMMDDHMS.format(date);
				case YEARMMDD :
					return YEARMMDD.format(date);
				case YEAR_MM_DD_HH_MM :
					return YEAR_MM_DD_H_M.format(date);
			}
		}catch(Exception e){}
		 return null;
	}
}
package com.lanwei.haq.comm.util;

import java.text.ParseException;
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
	 * @param edc
	 * @param date
	 * @return
	 */
	public static Date paseDate(EnumDateCode edc,String date) throws ParseException {
		Date d = YEAR_MM_DD_H_M_S.parse(date);
		switch (edc) {
			case YEAR_MM_DD :
				d = YEAR_MM_DD.parse(date);
				break;
			case YEAR_MM_DD_HH_MM_SS :
				d = YEAR_MM_DD_H_M_S.parse(date);
				break;
			case YEARMMDDHHMMSS :
				d = YEARMMDDHMS.parse(date);
				break;
			case YEARMMDD :
				d = YEARMMDD.parse(date);
				break;
			case YEAR_MM_DD_HH_MM :
				d = YEAR_MM_DD_H_M.parse(date);
				break;
			default:
					break;
		}
		return d;
	}
	
	/**
	 * 默认返回当前时间全标准格式时间字符
	 * @return
	 */
	public static String formatDate(){
		return format(EnumDateCode.YEAR_MM_DD_HH_MM_SS,new Date());
	}

	/**
	 * 返回当前时间指定格式字符串
	 * @param edc
	 * @return
	 */
	public static String formatDate(EnumDateCode edc){
		return format(edc,new Date());
	}
	
	/**
	 * 默认返回某日期全标准格式时间字符  yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return format(EnumDateCode.YEAR_MM_DD_HH_MM_SS,date);
	}

	/**
	 * 返回某日期指定格式字符串
	 * @param edc
	 * @param date
	 * @return
	 */
	public static String formatDate(EnumDateCode edc,Date date){
		return format(edc,date);
	}
	
	/**
	 * @param edc
	 * @param date
	 * @return
	 */
	private static String format(EnumDateCode edc,Date date){
		String result = YEAR_MM_DD_H_M_S.format(date);
		try{
			switch (edc) {
				case YEAR_MM_DD :
					result = YEAR_MM_DD.format(date);
					break;
				case YEAR_MM_DD_HH_MM_SS :
					result = YEAR_MM_DD_H_M_S.format(date);
					break;
				case YEARMMDDHHMMSS :
					result = YEARMMDDHMS.format(date);
					break;
				case YEARMMDD :
					result = YEARMMDD.format(date);
					break;
				case YEAR_MM_DD_HH_MM :
					result = YEAR_MM_DD_H_M.format(date);
					break;
				default:
						break;
			}
		}catch(Exception e){}
		 return result;
	}
}
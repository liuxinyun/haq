package com.lanwei.haq.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @作者：刘新运
 * @日期：2017/6/28 0:35
 * @描述：类
 */

public class DateUtil {

    /**
     * 常用时间格式
     * @param date
     * @return
     */
    public static String format(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

    /**
     * @param date
     * @param format 时间格式
     * @return
     */
    public static String format(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取当前时间
     * @return
     */
    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前小时
     * @return
     */
    public static long getCurrentHour(){
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.YYYY_MM_DD_HH);
        String s = sdf.format(new Date());
        long res = 0;
        try {
            res = sdf.parse(s).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

}

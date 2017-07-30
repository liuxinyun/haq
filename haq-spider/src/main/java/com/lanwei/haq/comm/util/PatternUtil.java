package com.lanwei.haq.comm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者: liuxinyun
 * @日期: 2017/1/11 14:08
 * @描述: 常用规则校验
 */
public final class PatternUtil {


    /**
     * 验证邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return match(regex, str);
    }

    /**
     * 验证IP
     * @param str
     * @return
     */
    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    /**
     * 验证网址
     * @param str
     * @return
     */
    public static boolean IsUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    /**
     * 验证电话
     * @param str
     * @return
     */
    public static boolean IsTelephone(String str) {
        String regex = "^(\\d{3,4}-)?\\d{6,8}$";
        return match(regex, str);
    }

    /**
     * 验证密码（字符与数字同时出现）
     * @param str
     * @return
     */
    public static boolean IsPassword(String str) {
        String regex = "[A-Za-z]+[0-9]";
        return match(regex, str);
    }

    /**
     * 验证输入密码长度（6-18位）
     * @param str
     * @return
     */
    public static boolean IsPasswLength(String str) {
        String regex = "^\\d{6,18}$";
        return match(regex, str);
    }

    /**
     * 验证邮编
     * @param str
     * @return
     */
    public static boolean IsPostcode(String str) {
        String regex = "^\\d{6}$";
        return match(regex, str);
    }

    /**
     * 验证手机号
     * @param str
     * @return
     */
    public static boolean IsMobile(String str) {
        String regex = "^[1]+[1-9]+\\d{9}$";
        return match(regex, str);
    }

    /**
     * 验证身份证号
     * @param str
     * @return
     */
    public static boolean IsIDcard(String str) {
        String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }

    /**
     * 验证输入含两位小数
     * @param str
     * @return
     */
    public static boolean IsDecimal(String str) {
        String regex = "^[0-9]+(.[0-9]{2})?$";
        return match(regex, str);
    }

    /**
     * 验证一年的12个月
     * @param str
     * @return
     */
    public static boolean IsMonth(String str) {
        String regex = "^(0?[[1-9]|1[0-2])$";
        return match(regex, str);
    }

    /**
     * 验证一个月的31天
     * @param str
     * @return
     */
    public static boolean IsDay(String str) {
        String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
        return match(regex, str);
    }

    /**
     * 验证数字输入
     * @param str
     * @return
     */
    public static boolean IsNumber(String str) {
        String regex = "^[0-9]*$";
        return match(regex, str);
    }

    /**
     * 验证非零的正整数
     * @param str
     * @return
     */
    public static boolean IsIntNumber(String str) {
        String regex = "^\\+?[1-9][0-9]*$";
        return match(regex, str);
    }

    /**
     * 验证大写字母
     * @param str
     * @return
     */
    public static boolean IsUpChar(String str) {
        String regex = "^[A-Z]+$";
        return match(regex, str);
    }

    /**
     * 验证小写字母
     * @param str
     * @return
     */
    public static boolean IsLowChar(String str) {
        String regex = "^[a-z]+$";
        return match(regex, str);
    }

    /**
     * 验证验证输入字母
     * @param str
     * @return
     */
    public static boolean IsLetter(String str) {
        String regex = "^[A-Za-z]+$";
        return match(regex, str);
    }

    /**
     * 验证验证输入汉字
     * @param str
     * @return
     */
    public static boolean IsChinese(String str) {
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        return match(regex, str);
    }

    /**
     * 验证验证输入字符串的长度范围是否正确
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static boolean IsLength(String str,int start, int end) {
        String regex = "^.{"+start+","+end+"}$";
        return match(regex, str);
    }

    /**
     * @param regex
     * 正则表达式字符串
     * @param str
     * 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}

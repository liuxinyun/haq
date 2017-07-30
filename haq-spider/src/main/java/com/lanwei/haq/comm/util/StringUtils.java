package com.lanwei.haq.comm.util;

/**
 * String类型的工具类，封装一些常用的string的方法
 *
 * @author Sun xushe.
 * @date 2016/12/19 22:57
 * @copyright
 */
public class StringUtils {

    /**
     * 判断传入字符串是否为null,或者为空串
     *
     * @param string 传入的字符串
     * @return
     */
    public static boolean isBlank(String string) {
        return null == string || string.trim().equals("");
    }

    /**
     * 判断传入字符串是否为null,或者为空串
     *
     * @param string 传入的字符串
     * @return
     */
    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * 清除null的字符串，防止页面出现null的情况
     *
     * @param str
     * @return String
     */
    public static String wipeNull(String str) {
        return wipeNull(str, false);
    }


    /**
     * 清除null的字符串，防止页面出现null的情况
     *
     * @param str
     * @param trim 是否要去除空串
     * @return String
     */
    public static String wipeNull(String str, boolean trim) {
        if (isNotBlank(str))
            return trim ? str.trim() : str;
        return "";
    }

    /**
     * 首字母大写，用于构建getter，setter方法
     *
     * @param str 需要转换的字符串
     * @return String
     */
    public static String upcaseFirstChar(String str) {
        if (isBlank(str))
            return null;
        str = str.trim();
        return (str.charAt(0) + "").toUpperCase() + str.substring(1, str.length());
    }

    /**
     * 传入文件名称，获取文件类型（即获取文件的后缀，不带.）
     *
     * @param fileName
     * @return String
     */
    public static String getSuffix(String fileName) {
        if (isNotBlank(fileName) && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }
        return null;
    }

    /**
     * 转换String 为byte , 转换失败的情况下会默认转换成0
     *
     * @param value
     * @return
     */
    public static byte toByte(String value) {
        return toByte(value, (byte) 0);
    }

    /**
     * 转换String为byte，转换失败返回defValue
     *
     * @param value
     * @return
     */
    public static byte toByte(String value, byte defValue) {
        if (isNotBlank(value) && value.matches("\\d+")) {
            try {
                return Byte.parseByte(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    /**
     * 转换String为短整型 , 转换失败的情况下会默认转换成0
     *
     * @param value
     * @return
     */
    public static short toShort(String value) {
        return toShort(value, (short) 0);
    }

    /**
     * 转换String为短整型，转换失败返回defValue
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static short toShort(String value, short defValue) {
        if (isNotBlank(value) && value.matches("\\d+")) {
            try {
                return Short.parseShort(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    /**
     * 转换String 为整数 , 转换失败的情况下会默认转换成0
     *
     * @param value
     * @return
     */
    public static int toInt(String value) {
        return toInt(value, 0);
    }

    /**
     * 转换String为整型，转换失败返回defValue
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static int toInt(String value, int defValue) {
        if (isNotBlank(value) && value.matches("\\d+")) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    /**
     * 转换String为长整型 , 转换失败的情况下会默认转换成0
     *
     * @param value
     * @return
     */
    public static long toLong(String value) {
        return toLong(value, 0);
    }

    /**
     * 转换String为长整型，转换失败返回defValue
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static long toLong(String value, long defValue) {
        if (isNotBlank(value) && value.matches("\\d+")) {
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

}

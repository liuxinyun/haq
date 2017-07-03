package com.lanwei.haq.comm.util;


import java.util.ArrayList;
import java.util.List;

/**
 * list工具类
 *
 * @author sunxushe
 * @created 2016/12/22 17:18
 */
public class ListUtil {

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        return null == list || list.isEmpty();
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    /**
     * 整型数组去零
     *
     * @param a
     * @return
     */
    public static int[] removeZero(int[] a) {
        List<Integer> list = new ArrayList<>();
        for (int b : a) {
            if (b != 0) {
                list.add(b);
            }
        }
        if (list.size() == 0) {
            return null;
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * 拼接数组为字符串，例：数组[1,2,3]  返回 1separator2separator3
     *
     * @param arr
     * @param separator 分割串
     * @return
     */
    public static String join(Object[] arr, String separator) {
        StringBuilder result = new StringBuilder("");
        if (null != arr) {
            int l = arr.length;
            for (int i = 0; i < l; i++) {
                result.append(arr[i]).append(i == l - 1 ? "" : separator);
            }
        }
        return result.toString();
    }
}

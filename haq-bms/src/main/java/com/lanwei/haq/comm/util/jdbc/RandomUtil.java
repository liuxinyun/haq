package com.lanwei.haq.comm.util.jdbc;

import java.util.Random;

/**
 * @author liuxinyun
 */
public class RandomUtil {
    /**
     * 获取指定长度的随机数字.
     * @param limit 指定的长度.
     * @return
     */
    public static String getRandomNumLimited(int limit) {
        Random random = new Random();
        StringBuilder validateCode = new StringBuilder();

        int randomBound;
        while ((randomBound = limit - validateCode.length()) > 0){
            validateCode.append(random.nextInt((int)Math.pow(10,randomBound)));
        }
        return validateCode.toString();
    }

    /**
     * 获取集合或数组的随机下标.
     * @param size 集合或数组的长度.
     * @return
     */
    public static int getRandomIndexNum(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }
}

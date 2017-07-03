package com.lanwei.haq.comm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计项目的代码
 *
 * @author Sun xushe
 * @date 2017/2/27 12:38
 */
public class CodeUtil {

    public static final byte TYPE_ALL = 0;
    public static final byte TYPE_JAVA = 1;
    public static final byte TYPE_HTML = 2;
    public static final byte TYPE_JS = 3;
    public static final byte TYPE_CSS = 4;

    private static Map<Byte, String> typeMap;

    static {
        typeMap = new HashMap<>();
        typeMap.put(TYPE_ALL, null);
        typeMap.put(TYPE_JAVA, ".java");
        typeMap.put(TYPE_HTML, ".html");
        typeMap.put(TYPE_JS, ".js");
        typeMap.put(TYPE_CSS, ".css");
    }

    /**
     * 统计给定文件下的所有的代码行数
     *
     * @param path
     * @return
     */
    public static long countNumber(String path) {
        return countNumber(path, TYPE_ALL);
    }


    /**
     * 统计给定文件下给定类型的的所有的代码行数
     *
     * @param path
     * @return
     */
    public static long countNumber(String path, byte type) {
        long c = 0;
        long l = System.currentTimeMillis();
        String t = typeMap.get(type);
        File file;
        BufferedReader in;
        List<File> fList = new ArrayList<>();
        out("开始执行统计代码");
        if (StringUtils.isNotBlank(path)) {
            file = new File(path);
            out("开始查找文件类型为{}类型的文件...", t);
            if (file.exists()) {
                searchFileUnder(new File[]{file}, fList, t);
            }
            out("查找结束，一共查询到{}个{}类型的文件,耗时{}ms", fList.size() + "", t, System.currentTimeMillis() - l + "");
            if (fList.size() != 0) {
                try {
                    long n = 1;
                    out("开始读取代码行数");
                    for (File f : fList) {
                        if (n % 20 == 0)
                            System.out.print("。");

                        if (n % 800 == 0)
                            System.out.println();

                        n++;

                        in = new BufferedReader(new FileReader(f));
                        while (in.readLine() != null) {
                            c++;
                        }
                    }
                    out("");
                    out("结束读取代码行数");
                } catch (Exception e) {
                    out("读取文件流发生异常，异常原因为：" + e.getMessage());
                    e.printStackTrace();
                }

            }
            out("结束执行统计代码。一共有{}行{}代码,一共耗时{}ms", c + "", t, System.currentTimeMillis() - l + "");
        } else {
            out("结束执行统计代码。path : {} 为空", path);
        }
        return c;
    }

    /**
     * 递归循环获取所有的文件
     *
     * @param files
     * @param fList
     */
    private static void searchFileUnder(File[] files, List<File> fList, String type) {

        if (null != files && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (StringUtils.isNotBlank(type) && !file.getName().contains(type)) {
                        continue;
                    }
                    fList.add(file);
                } else {
                    out("正在查找" + file.getAbsolutePath() + "路径下的文件");
                    searchFileUnder(file.listFiles(), fList, type);
                }
            }
        }
    }

    /**
     * 输出字符串
     *
     * @param text
     * @param params
     */
    private synchronized static void out(String text, String... params) {
        if (null != params) {
            for (String param : params) {
                text = text.replaceFirst("\\{}", StringUtils.wipeNull(param));
            }
        }
        System.err.println(text);
    }

}

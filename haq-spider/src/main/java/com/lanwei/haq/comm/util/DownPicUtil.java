package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSONObject;
import com.lanwei.haq.comm.entity.ImgPath;
import com.lanwei.haq.comm.jdbc.MyJedisService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者：刘新运
 * @日期：2017/7/2 15:12
 * @描述：类
 */
@Component
public class DownPicUtil {

    private static final Logger logger = LoggerFactory.getLogger(DownPicUtil.class);

    //tomcat路径
    private static final String LOCALURL = "http://172.16.1.12:28081/img/images/";
    //tomcat本地路径
    private static final String LOCALPATH = "/home/haqteam/install/tomcat28081/webapps/img/images/";

    // 编码
    private static final String ENCODING = "UTF-8";
    // 获取img标签正则
    private static final String IMGURL_REG = "<[\\s]{0,3}img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";

    @Resource
    private MyJedisService statisJedisService;


    /**
     * 爬取网页上的图片并保存在指定路径
     * @param path
     * @param url
     */
    public static void downloadPic(String path, String url) {
        // 获得html文本内容
        String html = getHtml(url);
        if (null != html) {
            // 获取图片标签
            List<String> imgUrl = getImageUrl(html);
            // 获取图片src地址
            List<String> imgSrc = getImageSrc(imgUrl);
            // 下载图片并保存到本地
            downloadToLocal(path,imgSrc);
        }
    }

    /**
     * 获取给定html标签内的图片并保存到本地
     * @param path
     * @param html
     */
    public static void htmlToLocal(String path, String html) {
        if (null != html && !"".equals(html)) {
            // 获取图片标签
            List<String> imgUrl = getImageUrl(html);
            // 获取图片src地址
            List<String> imgSrc = getImageSrc(imgUrl);
            // 下载图片
            downloadToLocal(path,imgSrc);
        }
    }

    /**
     * 获取给定html标签内的图片并保存到Ftp
     * @param html
     * @return html中的图片源地址，以及替换地址后的内容
     */
    public Map<String, String> htmlToFtp(String html) {
        // 获取图片标签
        List<String> imgUrl = getImageUrl(html);
        // 获取图片src地址
        List<String> imgSrc = getImageSrc(imgUrl);
        // 下载图片
        return downloadToFtp(html, imgSrc);

    }

    /***
     * 获取HTML内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String getHtml(String url){
        try {
            Document document = Jsoup.connect(url).timeout(3000).get();
            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取ImageUrl地址
     *
     * @param HTML
     * @return
     */
    public static List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /***
     * 获取ImageSrc地址
     *
     * @param listImageUrl
     * @return
     */
    public static List<String> getImageSrc(List<String> listImageUrl) {
        List<String> listImgSrc = new ArrayList<>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0,
                        matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    /***
     * 下载图片并保存
     *
     * @param path 文件路径
     * @param listImgSrc
     */
    private static void downloadToLocal(String path, List<String> listImgSrc) {

        for (String url : listImgSrc) {
            /**
             * 以下部分从try-catch中拿出来，保证无论存储到本地是否出异常，均能将图片替换。
             */
            if (url.contains("?")){
                url = url.substring(0, url.indexOf("?"));
            }
            String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
            saveImgToLocal(url, path, imageName);
        }
    }

    /**
     * 下载并保存到本地tomcat路径下
     * @param listImgSrc
     */
    private Map<String, String> downloadToFtp(String html, List<String> listImgSrc) {
        String date = DateUtil.format(new Date(), Constant.YYYYMMDD);
        List<ImgPath> imgPaths = new LinkedList<>();
        for (String url : listImgSrc) {
            String temp = url;
            if (temp.contains("?")){
                temp = temp.substring(0, temp.indexOf("?"));
            }
            String imageName = temp.substring(temp.lastIndexOf("/") + 1, temp.length());
            html = html.replace(url, LOCALURL+date+"/"+imageName);//替换原地址到本地网址
            ImgPath urlPath = new ImgPath(imageName, url, LOCALURL+date+"/"+imageName);
            imgPaths.add(urlPath);
            //将要保存的图片信息存入redis-list，由另一个线程专门读取存入本地
            ImgPath path = new ImgPath(imageName, url, LOCALPATH+date+"/");
            statisJedisService.lpush(Constant.REDIS_IMG_INFO, JSONObject.toJSONString(path));
        }
        String img_path = JSONObject.toJSONString(imgPaths);
        Map<String, String> result = new HashMap<>();
        result.put("img", img_path);
        result.put("html", html);
        return result;
    }

    /**
     * 将图片保存到本地
     * @param imgUrl
     * @param path
     * @param imgName
     */
    public static void saveImgToLocal(String imgUrl, String path, String imgName){
        try {
            // 构造URL
            URL uri = new URL(imgUrl);
            URLConnection urlConnection = uri.openConnection(Constant.PROXY);
            urlConnection.setReadTimeout(15000);
            BufferedInputStream bin = new BufferedInputStream(urlConnection.getInputStream());
            FileUtil.mkdirs(path);
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File(path + "/" + imgName)));
            int r;
            while ((r = bin.read()) != -1){
                bout.write(r);
            }
            bin.close();
            bout.close();
        } catch (Exception e) {
            logger.error("saveImgToLocal failed for "+e.getMessage(), e);
        }
    }

}

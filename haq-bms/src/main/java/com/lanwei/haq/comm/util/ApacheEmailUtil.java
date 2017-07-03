package com.lanwei.haq.comm.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @作者: liuxinyun
 * @日期: 2017/4/5 18:10
 * @描述:
 */
public class ApacheEmailUtil {

    /**
     * 无附件
     * @param host 邮箱服务器
     * @param port 邮箱端口号
     * @param userName 邮箱账号
     * @param password 邮箱密码
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param from 发件人
     * @param to 接收人
     * @return
     */
    public static boolean sendNoAttach(String host, String port, String userName, String password, String subject, String content, String from, String to){
        return send(host,port,userName,password,subject,content,from,to,null,null,(byte) 0);
    }

    /**
     * 带本地附件
     * @param host 邮箱服务器
     * @param port 邮箱端口号
     * @param userName 邮箱账号
     * @param password 邮箱密码
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param from 发件人
     * @param to 接收人
     * @param fileName 附件名称
     * @param fileUrl 附件地址
     * @return
     */
    public static boolean sendLocalAttach(String host, String port, String userName, String password, String subject, String content, String from, String to, String[] fileName, String[] fileUrl){
        return send(host,port,userName,password,subject,content,from,to,fileName,fileUrl,(byte) 1);
    }

    /**
     * 带网络附件
     * @param host 邮箱服务器
     * @param port 邮箱端口号
     * @param userName 邮箱账号
     * @param password 邮箱密码
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param from 发件人
     * @param to 接收人
     * @param fileName 附件名称
     * @param fileUrl 附件地址
     * @return
     */
    public static boolean sendWebAttach(String host, String port, String userName, String password, String subject, String content, String from, String to, String[] fileName, String[] fileUrl){
        return send(host,port,userName,password,subject,content,from,to,fileName,fileUrl,(byte) 2);
    }

    /**
     * 发送邮件
     * @param host 邮箱服务器
     * @param port 邮箱端口号
     * @param userName 邮箱账号
     * @param password 邮箱密码
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param from 发件人
     * @param to 接收人
     * @param fileName 附件名称
     * @param fileUrl 附件地址
     * @param type 类型：0无附件，1本地附件，2网络附件
     * @return
     */
    public static boolean send(String host, String port, String userName, String password, String subject, String content, String from, String to, String[] fileName, String[] fileUrl, byte type){
        boolean res = true;
        //创建邮件信息
        MultiPartEmail email = new MultiPartEmail();
        try {
            email.setHostName(host);
            email.setSmtpPort(Integer.parseInt(port));
            email.setAuthentication(userName,password);
            email.setFrom(from.split(";")[0], from.split(";")[1]);
            email.addTo(to,"我");
            email.setSubject(subject);
            email.setMsg(content);
            if (type == 1){
                //添加本地附件
                for (int i=0; i<fileUrl.length; i++){
                    FileDataSource file = new FileDataSource(fileUrl[i]);
                    email.attach((DataSource)file,fileName[i],null);
                }
            }else if(type == 2){
                //添加网络附件
                for (int i=0; i<fileUrl.length; i++){
                    try {
                        email.attach(new URL(fileUrl[i]),fileName[i],fileName[i]);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        res = false;
                    }
                }
            }
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

}

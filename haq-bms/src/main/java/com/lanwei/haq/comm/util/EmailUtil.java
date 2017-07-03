package com.lanwei.haq.comm.util;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Description  发送邮件
 * @Author 		wangxq
 * @Date 		2017年3月14日
 */
public class EmailUtil{
	
	private static Logger logger = Logger.getLogger(EmailUtil.class);
	private static MimeMessage mimeMsg; 			// MIME邮件对象
	private static Session session; 				// 专门用来发送邮件的Session会话
    private static Properties props; 				// 封装邮件发送时的一些配置信息的一个属性对象
	private static String username;					// smtp认证发件人的用户名 
    private static String password;					// smtp认证发件人的密码
    private static Multipart mp; 					// Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    
    /**
     * Description	邮件发送（带附件,带抄送,带密送）
     * @return		boolean
     */
    public static Map<String, Object> sendEmail(String smtp, String portNumber, String subject, String content, String[] fileName, String[] fileUrl, String to, String copyTo,
    		String blindTo, String from, String username, String password){
    	Map<String, Object> result = new HashMap<String, Object>();
    	String isCopy = "";
    	String isBlind = "";
    	/****** 设置邮箱服务器并创建邮件 ******/
        mail(smtp, portNumber);
        setNeedAuth(true);
        /****** 校验与放置 ******/
        if(!setSubject(subject)){														//主题
        	result.put("result", false);
			result.put("msg", "设置邮件主题发生错误!");
			return result;
        }											
        if(!setBody(content)){															//内容
        	result.put("result", false);
			result.put("msg", "设置邮件正文时发生错误!");
			return result;
        } 											
        if( null != fileUrl && !"".equals(fileUrl) ){									//文件名
        	if(!addFileAffix(fileName,fileUrl)){
        		result.put("result", false);
    			result.put("msg", "增加邮件附件时发生错误!");
    			return result;
        	}						
        }
        if(!setTo(to)){																	//收件人
        	result.put("result", false);
			result.put("msg", "设置收信人时发生错误!");
			return result;
        }											
        if( null != copyTo && !"".equals(copyTo) ){										//抄送人
        	if(!setCopyTo(copyTo)){
        		result.put("result", false);
    			result.put("msg", "设置抄送人时发生错误!");
    			return result;
        	}									
        	isCopy = "havaCopy";
        }
        if( null != blindTo && !"".equals(blindTo) ){									//密送人
        	if(!setBlindTo(blindTo)){
        		result.put("result", false);
    			result.put("msg", "设置密送人时发生错误!");
    			return result;
        	}									
        	isBlind = "havaBlind";
        }
        if(!setFrom(from)){																//发件人
        	result.put("result", false);
			result.put("msg", "设置发信人时发生错误!");
			return result;
        }												
        setNamePass(username, password);												//账号,密码
        /****** 邮件发送 ******/
        return sendOut(isCopy,isBlind);
    }

    /**
     * 发送邮件，不带抄送密送人、不带附件
     * @param smtp          SMTP服务器的地址
     * @param portNumber    SMTP服务器端口号
     * @param subject       邮件主题
     * @param content       邮件内容
     * @param to            收件人
     * @param from          发件人
     * @param username      发件人账号
     * @param password      发件人密码
     */
    public static Map<String, Object> sendEmail(String smtp, String portNumber, String subject, String content,  String to, String from, String username, String password){
        return sendEmail(smtp, portNumber,subject,content,null, null,to, null, null,from, username, password);
    }

	/**
     * Description
     * @param 	smtp SMTP服务器的地址，比如要用QQ邮箱，那么应为："smtp.qq.com"，163为："smtp.163.com"
     * @param   portNumber 服务器端口号
     */
    private static void mail(String smtp, String portNumber){
        setSmtpHost(smtp, portNumber);							// 设置邮件服务器 
        createMimeMessage();									// 创建邮件
    }
    
    /**
     * Description 	设置发送邮件的主机(JavaMail需要Properties来创建一个session对象.
     * 				它将寻找字符串"mail.smtp.host",属性值就是发送邮件的主机)
     * @param 		hostName String   服务器地址
     * @param 		portNumber String 端口号
     */
    private static void setSmtpHost(String hostName, String portNumber){
        if(props == null){
            props = System.getProperties(); 					//获得系统属性对象
            //props.put("mail.transport.protocol","smtp");		//设置协议
        	props.put("mail.smtp.host",hostName); 				//设置SMTP主机(接收邮件的服务器)
        	props.put("mail.smtp.port",portNumber);				//设置设置端口号
        	//props.put("mail.smtp.connectiontimeout", "10000");
        	//props.put("mail.smtp.timeout", "10000");
        }
    }
    
    /**
     * Description 	这个Session类代表JavaMail中的一个邮件session,
     * 				每一个基于JavaMail的应用程序至少有一个session但是可以有任意多的session.
     * 				在这个例子中,Session对象需要知道用来处理邮件的SMTP服务器.
     */
    private static boolean createMimeMessage(){
        try {
            session = Session.getDefaultInstance(props,null); 	//用props对象来创建并初始化session对象
        }catch(Exception e){
        	logger.error("邮件异常：", e);
            e.printStackTrace();
            return false;
        }
        try {
            mimeMsg = new MimeMessage(session); 				//用session对象来创建并初始化邮件对象
            mp = new MimeMultipart();							//生成附件组件的实例
            return true;
        } catch(Exception e){
        	logger.error("邮件异常：", e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Description	设置SMTP的身份认证(是否需要验证)
     * @param 		need
     */
    private static void setNeedAuth(boolean need){
        if(props == null) props = System.getProperties();
        if(need){
            props.put("mail.smtp.auth","true");
        }else{
            props.put("mail.smtp.auth","false");
        }
    }
    
    /**
     * Description	设置用户名和密码
     * @param 		name   用户名
     * @param 		pass   密码
     */
    private static void setNamePass(String name,String pass){
        username = name;
        password = pass;
    }
    
    /**
     * Description	设置邮件主题
     * @param 		mailSubject
     */
    private static boolean setSubject(String mailSubject) {
        try{
            mimeMsg.setSubject(mailSubject);
            return true;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Description	设置邮件内容,并设置其为文本格式或HTML文件格式,编码方式为UTF-8
     * @param 		mailBody String
     */
    private static boolean setBody(String mailBody) {
        try{
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
            mp.addBodyPart(bp);																												//在组件上添加邮件文本
            return true;
        } catch(Exception e){
        	logger.error("邮件异常：", e);
        	e.printStackTrace();
        	return false;
        }
    }
    
    /**
     * Description	添加附件
     * @param 		fileUrl(String) 邮件附件的地址，只能是本机地址而不能是网络地址，否则抛出异常
     */
    private static boolean addFileAffix(String[] fileName, String[] fileUrl){
        try{
            for (int i = 0; i < fileUrl.length; i++) {
            	BodyPart bp = new MimeBodyPart();
            	FileDataSource fileds = new FileDataSource(fileUrl[i]);						//得到数据源  
            	bp.setDataHandler(new DataHandler(fileds));									//得到附件本身并至入BodyPart
            	bp.setFileName(MimeUtility.encodeText(fileName[i]));						//发送的附件前加上一个用户名的前缀
            	mp.addBodyPart(bp);	
            }
            return true;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
            e.printStackTrace();
            return false;
        }
    }
      
    /**
     * Description	设置发信人
     * @param 		from(String) 发件人地址
     */
    private static boolean setFrom(String from){
        try{
            //设置自定义发件人昵称

            String[] nick=from.split(";");
            String branchName="";
            try {
                branchName=javax.mail.internet.MimeUtility.encodeText(nick[1]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mimeMsg.setFrom(new InternetAddress(branchName+" <"+nick[0]+">"));
            //设置发信人
            return true;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
        	e.printStackTrace();
        	return false;
        }
    }
    
    /**  
     * Description 	设置收信人
     * @param 		to(String) 收件人的地址
     */
    private static boolean setTo(String to){
        if(to == null)return false;
        try{
            mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            return true;  
        }catch(Exception e){
        	logger.error("邮件异常：", e);
        	e.printStackTrace();
            return false; 
        }
    }
    
    /**
     * Description	设置抄送人
     * @param 		copyTo(String) 抄送人地址
     */
    private static boolean setCopyTo(String copyTo){
        if(copyTo == null)return false;
        try{
        	mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyTo));
        	return true;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
        	e.printStackTrace();
        	return false;
        }
    }
    
    /**
     * Description	设置密送人
     * @param 		blindTo(String) 密送人地址
     */
    private static boolean setBlindTo(String blindTo){
        if(blindTo == null)return false;
        try{
        	mimeMsg.setRecipients(Message.RecipientType.BCC,(Address[])InternetAddress.parse(blindTo));
        	return true;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
        	e.printStackTrace();
        	return false;
        }
    }
    
    /**
     * Description	发送邮件
     * @param 		isCopy(String)  是否带抄送人
     * @param 		isBlind(String) 是否带密送人
     */
    private static Map<String, Object> sendOut(String isCopy, String isBlind){
    	Map<String, Object> result = new HashMap<String, Object>();
        try{
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            Session mailSession = Session.getInstance(props,null);
            Transport transport = mailSession.getTransport("smtp");
            //真正的连接邮件服务器并进行身份验证  
            try {
            	transport.connect((String)props.get("mail.smtp.host"),username,password);			
			} catch (Exception e) {
				if( StringUtils.isNotBlank(e.getMessage()) && e.getMessage().toLowerCase().indexOf("authentication") >= 0 ){
	    			result.put("msg", "您的邮箱密码错误!");
				}else{
	    			result.put("msg", "网络连接失败!");
				}
				logger.error("邮件异常：", e);
				e.printStackTrace();
				result.put("result", false);
				return result;
			}
            //发送邮件
            try {
            	 transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));			
			} catch (Exception e) {
				logger.error("邮件异常：", e);
				e.printStackTrace();
				result.put("result", false);
				result.put("msg", "设置收件人错误!");
				return result;
			}
            //抄送人
            if( "havaCopy".equals(isCopy)){
            	try {
            		transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));	
				} catch (Exception e) {
					
					logger.error("邮件异常：", e);
					e.printStackTrace();
					result.put("result", false);
					result.put("msg", "设置抄送人错误!");
					return result;
				}
            }
            //密送人
            if( "havaBlind".equals(isBlind)){
            	try {
            		//transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.BCC));	
				} catch (Exception e) {
					logger.error("邮件异常：", e);
					e.printStackTrace();
					result.put("result", false);
					result.put("msg", "设置密送人错误!");
					return result;
				}
            }
            //transport.send(mimeMsg);
            transport.close();
            result.put("result", true);
			result.put("msg", "发送成功!");
			return result;
        }catch(Exception e){
        	logger.error("邮件异常：", e);
            e.printStackTrace();
            result.put("result", false);
			result.put("msg", "发送失败!");
			return result;
        }
    }
}
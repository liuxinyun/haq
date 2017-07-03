package com.lanwei.haq.comm.util.AES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	private static final Logger logger = LoggerFactory.getLogger(AES.class);
	private static final String default_charset = "UTF-8";
	private static final String default_key = "mW63*I@h!e^vF2s1";
	private static final String default_iv  = "LBNmAg(psx6D*e7F";
	
	/**
	 * 加密<自定义，该加密必须是自定义解密>
	 * @param content 加密内容
	 * @return
	 */
	public String encrypt(String content) {
		 try {
			 String pass = encrypt(content.getBytes(default_charset),default_key.getBytes(default_charset) ,default_iv.getBytes(default_charset));
			 return pass.substring(4, 8)+pass.substring(16, 20)+pass.substring(0, 4)+pass.substring(12, 16)+pass.substring(8, 12)+pass.substring(20, pass.length());
		 }catch(Exception e){
			 logger.debug("--------------自定义加密异常-------------");
			 return null;
		 }
	}
	/**
	 * 加密
	 * @param content 加密内容
	 * @param key 密钥
	 * @param iv 加密向量
	 * @return
	 */
	public String encrypt(String content, String key,String iv) {
		key=(key==null||"".equals(key))?default_key:key;
		iv=(iv==null||"".equals(iv))?default_key:iv;
		 try {
			 return encrypt(content.getBytes(default_charset),key.getBytes(default_charset) ,iv.getBytes(default_charset));
		 }catch(Exception e){
			 logger.debug("--------------加密异常-------------");
			 return null;
		 }
	}
	
    /**
     * 加密
     * @param content 需要加密的内容
     * @param key 加密密码
     * @param iv 加密向量
     * @return 加密后的字节数据
     */
    public String encrypt(byte[] content, byte[] key, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式" 
            IvParameterSpec ivps = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);//
           return new BASE64Encoder().encode(cipher.doFinal(content));
        } catch (Exception e) { 
        	 logger.debug("--------------加密异常-------------");
            return null;
        }
    }
    
    /**
     * 解密
     * @param content 密串
     * @param key 密钥
     * @param iv 向量
     * @return
     */
    public String decrypt(String content, String key, String iv) {
    	key=(key==null||"".equals(key))?default_key:key;
		iv=(iv==null||"".equals(iv))?default_key:iv;
    	try{
    		return decrypt(new BASE64Decoder().decodeBuffer(content),key.getBytes(default_charset) ,iv.getBytes(default_charset));
    	}catch(Exception e){
    		logger.debug("--------------解密异常-------------");
    		return null;
    	}
    }
    /**
     * 解密<自定义，该解密必须是自定义加密>
     * @param content 密串
     * @return
     */
    public String decrypt(String content) {
    	try{
    		String pass = content.substring(8, 12)+content.substring(0, 4)+content.substring(16, 20)+content.substring(12, 16)+content.substring(4, 8)+content.substring(20, content.length());
    		return decrypt(new BASE64Decoder().decodeBuffer(pass),default_key.getBytes(default_charset) ,default_iv.getBytes(default_charset));
    	}catch(Exception e){
    		logger.debug("--------------自定义解密异常-------------");
    		return null;
    	}
    }
    /**
     * 解密
     * @param content 密串
     * @param key 密钥
     * @param iv 向量
     * @return
     */
    public String decrypt(byte[] content, byte[] key, byte[] iv) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);//
            return new String(cipher.doFinal(content),default_charset);
        } catch (Exception e) {
        	logger.debug("--------------解密异常-------------");
            return null;
        }
    }
    
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public byte[] parseHexStr2Byte(String hexStr) { 
		try{
			if (hexStr.length() < 1){
	        	 return null;  
	        }
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	            result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
		}catch(Exception e){
			logger.debug("--------------转换异常-------------");
			return null;
		}
       
	}  
 
}

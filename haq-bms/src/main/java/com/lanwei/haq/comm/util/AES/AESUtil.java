package com.lanwei.haq.comm.util.AES;

public class AESUtil {

	private static AES aes = new AES();
	/**
	 * 加密<自定义，该加密必须是自定义解密>
	 * @param content 加密内容
	 * @return
	 */
	public static String encrypt(String content) {
		return aes.encrypt(content);
	}
	/**
	 * 加密
	 * @param content 加密内容
	 * @param key 密钥
	 * @param iv 加密向量
	 * @return
	 */
	public static String encrypt(String content, String key,String iv) {
		return aes.decrypt(content, key, iv);
	}
	
    /**
     * 加密
     * @param content 需要加密的内容
     * @param key 加密密码
     * @param iv 加密向量
     * @return 加密后的字节数据
     */
    public String encrypt(byte[] content, byte[] key, byte[] iv) {
        return aes.encrypt(content, key, iv);
    }
    
    /**
     * 解密
     * @param content 密串
     * @param key 密钥
     * @param iv 向量
     * @return
     */
    public String decrypt(String content, String key, String iv) {
    	return aes.decrypt(content, key, iv);
    }
    /**
     * 解密<自定义，该解密必须是自定义加密>
     * @param content 密串
     * @return
     */
    public static String decrypt(String content) {
    	return aes.decrypt(content);
    }
    /**
     * 解密
     * @param content 密串
     * @param key 密钥
     * @param iv 向量
     * @return
     */
    public static String decrypt(byte[] content, byte[] key, byte[] iv) {
        return aes.decrypt(content, key, iv);
    }
    
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public byte[] parseHexStr2Byte(String hexStr) {  
	   return aes.parseHexStr2Byte(hexStr);
	}  
 
}

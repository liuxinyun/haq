package com.lanwei.haq.comm.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

public class HttpConnectionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpConnectionUtil.class);

	/**
	 * 无参 GET 请求
	 * @param url
	 * @return
	 */
	public static String getString(String url){
		return httpString(url, null, null, "GET",false);
	}
	/**
	 * 无参 POST 请求
	 * @param url
	 * @return
	 */
	public static String postString(String url){
		return httpString(url, null, null, "POST",false);
	}
	/**
	 * 参数 GET 请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getString(String url,Map<String,Object> params){
		return httpString(url, params, null, "GET",false);
	}
	/**
	 * 参数 POST 请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postString(String url,Map<String,Object> params){
		return httpString(url, params, null, "POST",false);
	}
	/**
	 * 参数+头部 GET 请求
	 * @param url
	 * @param params
	 * @param head
	 * @return
	 */
	public static String getString(String url,Map<String,Object> params,Map<String,String> head){
		return httpString(url, params, head, "GET",false);
	}
	/**
	 * 参数+头部 POST 请求
	 * @param url
	 * @param params
	 * @param head
	 * @return
	 */
	public static String postString(String url,Map<String,Object> params,Map<String,String> head){
		return httpString(url, params, head, "POST",false);
	}

	/**
	 * GET 请求 获得流数据
	 * @param url
	 * @return
	 */
	public static InputStream getInputStream(String url){
		return httpInputStream(url, null, null, "GET",false);
	}
	/**
	 * POST 请求 获得流数据
	 * @param url
	 * @return
	 */
	public static InputStream postInputStream(String url){
		return httpInputStream(url, null, null, "POST",false);
	}
	/**
	 * 参数 GET 请求 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream getInputStream(String url,Map<String,Object> params){
		return httpInputStream(url, params, null, "GET",false);
	}
	/**
	 * 参数 POST 请求 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream postInputStream(String url,Map<String,Object> params){
		return httpInputStream(url, params, null, "POST",false);
	}
	/**
	 * 参数+头部 GET 请求 获得流数据
	 * @param url
	 * @param params
	 * @param head
	 * @return
	 */
	public static InputStream getInputStream(String url,Map<String,Object> params,Map<String,String> head){
		return httpInputStream(url, params, head, "GET",false);
	}
	/**
	 * 参数+头部 POST 请求 获得流数据
	 * @param url
	 * @param params
	 * @param head
	 * @return
	 */
	public static InputStream postInputStream(String url,Map<String,Object> params,Map<String,String> head){
		return httpInputStream(url, params, head, "POST",false);
	}

	/**
	 * GET 请求SSL
	 * @param url
	 * @return
	 */
	public static String getStringSSL(String url){
		return httpString(url, null, null, "GET",true);
	}
	/**
	 * POST 请求SSL
	 * @param url
	 * @return
	 */
	public static String postStringSSL(String url){
		return httpString(url, null, null, "POST",true);
	}
	/**
	 * 参数 GET 请求SSL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getStringSSL(String url,Map<String,Object> params){
		return httpString(url, params, null, "GET",true);
	}
	/**
	 * 参数 POST 请求SSL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postStringSSL(String url,Map<String,Object> params){
		return httpString(url, params, null, "POST",true);
	}
	/**
	 * 参数+头部 GET 请求SSL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getStringSSL(String url,Map<String,Object> params,Map<String,String> head){
		return httpString(url, params, head, "GET",true);
	}
	/**
	 * 参数+头部 POST 请求SSL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postStringSSL(String url,Map<String,Object> params,Map<String,String> head){
		return httpString(url, params, head, "POST",true);
	}
	
	/**
	 * GET 请求SSL 获得流数据
	 * @param url
	 * @return
	 */
	public static InputStream getInputStreamSSL(String url){
		return httpInputStream(url, null, null, "GET",true);
	}
	/**
	 * POST 请求SSL 获得流数据
	 * @param url
	 * @return
	 */
	public static InputStream postInputStreamSSL(String url){
		return httpInputStream(url, null, null, "POST",true);
	}
	/**
	 * 参数 GET 请求SSL 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream getInputStreamSSL(String url,Map<String,Object> params){
		return httpInputStream(url, params, null, "GET",true);
	}
	/**
	 * 参数 POST 请求SSL 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream postInputStreamSSL(String url,Map<String,Object> params){
		return httpInputStream(url, params, null, "POST",true);
	}
	/**
	 * 参数+头部 get请求SSL 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream getInputStreamSSL(String url,Map<String,Object> params,Map<String,String> head){
		return httpInputStream(url, params, head, "GET",true);
	}
	/**
	 * 参数+头部 POST 请求SSL 获得流数据
	 * @param url
	 * @param params
	 * @return
	 */
	public static InputStream postInputStreamSSL(String url,Map<String,Object> params,Map<String,String> head){
		return httpInputStream(url, params, head, "POST",true);
	}
	
	/**
	 * 标准网络请求得到String
	 * @param url
	 * @param params
	 * @param head
	 * @param type
	 * @return
	 */
	private static String httpString(String url,Map<String,Object> params,Map<String,String> head,String type,boolean needSSL){
		OutputStreamWriter writer = null;
		InputStream inStream  = null;
		try{
			if(needSSL){
				HttpsURLConnection conn = httpsURLConnection(type, url, head);
				if(params!=null && !params.isEmpty()){
					writer = new OutputStreamWriter(conn.getOutputStream());
					//发送参数
			        writer.write(JSON.toJSONString(params));
			        writer.flush();
				}
				inStream = conn.getInputStream();
				logger.debug("-----------------------------接口请求成功！ HttpsConnectionUtil - httpString---------------------------------------------");
				return new String(readInputStream(inStream));
			}else{
				HttpURLConnection conn = httpURLConnection(type, url, head);
				if(params!=null && !params.isEmpty()){
					writer = new OutputStreamWriter(conn.getOutputStream());
			        //发送参数
			        writer.write(JSON.toJSONString(params));
			        writer.flush();
				}
				inStream = conn.getInputStream();
				logger.debug("-----------------------------接口请求成功！ HttpConnectionUtil - httpString---------------------------------------------");
				return new String(readInputStream(inStream));
			}
		}catch(Exception e){
			logger.error("-----------------------------接口请求失败！ HttpConnectionUtil - httpString---------------------------------------------");
			e.printStackTrace();
		}finally {
			try{
				if(writer!=null) {
                    writer.close();
                }
			}catch(Exception e){}
			try{
				if(inStream!=null) {
                    inStream.close();
                }
			}catch(Exception e){}
		}
		return null;
	}
	/**
	 * 标准网络请求得到输入流
	 * @param url
	 * @param params
	 * @param head
	 * @param type
	 * @return
	 */
	private static InputStream httpInputStream(String url,Map<String,Object> params,Map<String,String> head,String type,boolean needSSL){
		OutputStreamWriter writer = null;
		try{
			if(needSSL){
				HttpsURLConnection conn = httpsURLConnection(type, url, head);
				if(params!=null && !params.isEmpty()){
					writer = new OutputStreamWriter(conn.getOutputStream());
			        //发送参数
			        writer.write(JSON.toJSONString(params));
			        writer.flush();
				}
				logger.debug("-----------------------------接口请求成功！ HttpsConnectionUtil - httpInputStream---------------------------------------------");
				return conn.getInputStream();
			}else{
				HttpURLConnection conn = httpURLConnection(type, url, head);
				if(params!=null && !params.isEmpty()){
					writer = new OutputStreamWriter(conn.getOutputStream());
			        //发送参数
			        writer.write(JSON.toJSONString(params));
			        writer.flush();
				}
				logger.debug("-----------------------------接口请求成功！ HttpConnectionUtil - httpInputStream---------------------------------------------");
				return conn.getInputStream();
			}	
		}catch(Exception e){
			logger.error("-----------------------------接口请求失败！ HttpConnectionUtil - httpInputStream---------------------------------------------");
			e.printStackTrace();
		}finally {
			try{
				if(writer!=null) {
                    writer.close();
                }
			}catch(Exception e){}
		}
		return null;
	}

	
	/**
	 * 构建一个标准网络连接
	 * @param requestMethod
	 * @param head
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private static HttpURLConnection httpURLConnection(String requestMethod,String url,Map<String, String> head) throws Exception {
		HttpURLConnection httpc = (HttpURLConnection) new URL(url).openConnection();
		httpc.setRequestMethod(requestMethod);// 提交模式
		httpc.setDoOutput(true);// 是否输入参数
		httpc.setRequestProperty("charset", "utf-8");
		httpc.setUseCaches(false);
		if(head!=null){
			for (Entry<String, String> entry: head.entrySet()) {
				httpc.setRequestProperty(entry.getKey(), entry.getValue());
			} 
		}
		return httpc;
	}

	/**
	 * 构建需SSL证书的https网络连接
	 * @param requestMethod
	 * @param url
	 * @param head
	 * @return
	 * @throws Exception
	 */
	private static HttpsURLConnection httpsURLConnection(String requestMethod,String url,Map<String, String> head) throws Exception {
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {return null;}
			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}}};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		// 创建URL对象
		URL myURL = new URL(url);
		// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
		HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
		httpsConn.setSSLSocketFactory(ssf);
		httpsConn.setRequestMethod(requestMethod);
		httpsConn.setDoOutput(true);// 是否输入参数
		httpsConn.setDoInput(true);
		httpsConn.setRequestProperty("charset", "utf-8");
		httpsConn.setUseCaches(false);
		if(head!=null){
			for (Entry<String, String> entry: head.entrySet()) {
				httpsConn.setRequestProperty(entry.getKey(), entry.getValue());
			} 
		}
		return httpsConn;
	}
	
	/**
	 * 从输入流中读取数据
	 * @param inStream
	 * @return
	 * @throws Exception 
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		try(ByteArrayOutputStream outStream = new ByteArrayOutputStream();){
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			byte[] data = outStream.toByteArray();
			return data;
		}finally {
			if(inStream!=null) {
                inStream.close();
            }
		}
	}
}

package com.lanwei.haq.comm.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringContextUtil{ 

	private static WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
	  /**
		  * 获取注入bean 
		  * @param id
		  * @return
		  */
		public static Object getBean(String id) {
			try {
				return applicationContext.getBean(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
	}
/**   
 * ApplicationContext.java
 *
 * @author Rick
 * @date 2014-7-25 下午11:33:15 
 * @verion 0.1.0
 */
package com.sborm.core;

import org.springframework.context.ApplicationContext;

/** 
 * Context，在DatabaseRouter初始化后设置
 * 
 * @author fengli
 * @date 2014-7-25 下午11:33:15 
 *
 */
public class ApplicationContextHelper {

	private static ApplicationContext context;
	
	public static void setApplicationContext(ApplicationContext _context) {
		if (context == null) {
			context = _context;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static Object getBean(String key) {
		return context.getBean(key);
	}
}

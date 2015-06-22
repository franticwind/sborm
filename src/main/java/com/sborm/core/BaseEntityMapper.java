package com.sborm.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.sborm.core.annotation.Column;

/** 
 * Entity属性字段名映射类
 * 
 * @author Rick
 * @date 2015-6-22 下午05:21:04 
 *
 */
public class BaseEntityMapper {

	private static Map<String, Map<String, String>> columnMapper = new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> propertyMapper = new HashMap<String, Map<String, String>>();
	
	public static void intiEntity(String className) {
		try {
			Object obj = Class.forName(className).newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			Map<String, String> columnMapping = new HashMap<String, String>();
			Map<String, String> propertyMapping = new HashMap<String, String>();
			for (int i = 0; i < fields.length; i++) {
	            // 这个是检查类中属性是否含有注解
	            if (fields[i].isAnnotationPresent(Column.class)) {
	                // 获取注解
	            	Column annotation = fields[i].getAnnotation(Column.class);
	            	String columnName = annotation.value();
	            	String propertyName = fields[i].getName();
	            	columnMapping.put(columnName, propertyName);
	            	propertyMapping.put(propertyName, columnName);
	            }
	        }
			columnMapper.put(className, columnMapping);
			propertyMapper.put(className, propertyMapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getPropertyName(String className, String columnName) {
		String p = columnMapper.get(className).get(columnName);
		return p == null ? columnName : p;
	}
	
	public static String getColumnName(String className, String propertyName) {
		String c = propertyMapper.get(className).get(propertyName);
		return c == null ? propertyName : c;
	}
}

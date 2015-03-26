package com.sborm.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sborm.core.BaseEntity;

/**
 * pojo信息读取工具类，主要是读取entity对象信息，主动去掉BaseEntity属性
 * 
 * @author fengli
 * @date 2014-7-11 下午2:25:10
 * 
 */
public class EntityUtil {
	
	public static String EMPTY = "";

	/**
	 * 反射得到实例对象的信息跟值
	 * 
	 * @param entity
	 * @return
	 */
	public static Map<String, Object> getEntityInfo(BaseEntity entity) {
		Field[] fs = entity.getClass().getDeclaredFields();
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			if ("serialVersionUID".equals(name) || "subTableFlag".equals(name)) {
				continue;
			}
			try {
				Method m = entity.getClass().getMethod("get" + getFirstUpperString(name), null);
				Object buf = m.invoke(entity, null);
				valuesMap.put(name, buf);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valuesMap;
	}
	
	/**
	 * 获取实体对象信息，排除null值
	 * @param entity
	 * @return
	 */
	public static Map<String, Object> getEntityInfoWithoutNULL(BaseEntity entity) {
		Field[] fs = entity.getClass().getDeclaredFields();
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			if ("serialVersionUID".equals(name) || "subTableFlag".equals(name)) {
				continue;
			}
			try {
				Method m = entity.getClass().getMethod(
						"get" + getFirstUpperString(name), null);
				Object buf = m.invoke(entity, null);
				if (buf != null) {// 排除null值的
					valuesMap.put(name, buf);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return valuesMap;
	}
	
	/**
	 * 获取列和值
	 * @param data
	 * @return
	 */
	public static Object[][] getColumnAndValue(Map<String, Object> data) {
		Object[][] result = new Object[2][];
		Object[] columns = new Object[data.size()];
		Object[] values = new Object[data.size()];
		Set<String> keys = data.keySet();
		int i = 0;
		for (String key : keys) {
			columns[i] = key;
			values[i] = data.get(key);
			i++;
		}
		result[0] = columns;
		result[1] = values;
		return result;
	}
	
	/**
	 * 获取列和值，排除null值
	 * @param data
	 * @return
	 */
	public static Map<String, Object>  getColumnAndValueWithoutNull(Map<String, Object> data) {
		Set<String> keys = data.keySet();
		Map<String, Object> result = new HashMap<String, Object>();
		for (String key : keys) {
			Object o = data.get(key);
			if (o != null) {
				result.put(key, o);
			}
		}
		return result;
	}
	
	/**
	 * 首字母大写
	 * @param s
	 * @return
	 */
	public static String getFirstUpperString(String s) {
		if (s == null || s.length() < 1) {
			return EMPTY;
		}
		String s1 = s.substring(0, 1);
		String s2 = s.substring(1);
		return s1.toUpperCase() + s2;
	}
}

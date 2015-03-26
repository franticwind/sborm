package com.sborm.core.utils;

import java.lang.annotation.Annotation;

import com.sborm.core.EntityInfo;
import com.sborm.core.annotation.Database;
import com.sborm.core.annotation.Table;

/**
 * 注解相关工具类
 * 
 * @author fengli
 * @date 2014-7-10 下午5:50:16
 * 
 */
public class AnnotationUtil {

	/**
	 * 获取所有注解信息
	 * 
	 * @param className
	 * @return
	 */
	public static Annotation[] getAllAnnotaions(String className) {
		try {
			Annotation annos[] = Class.forName(className).getAnnotations();
			return annos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有注解信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static Annotation[] getAllAnnotaions(Class<?> clazz) {
		try {
			Annotation annos[] = clazz.getAnnotations();
			return annos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否是实体类，必须都包含Table，Database注解，Entity专用注解，其他类尽量不要使用
	 * 如果是实体类，会设置table和database属性，并返回改EntityInfo实例
	 * 
	 * @param annotaions
	 * @return
	 */
	public static EntityInfo isEntity(Annotation[] annotaions) {
		boolean hasTableAnnotation = false;
		boolean hasDatabaseAnnotaion = false;
		EntityInfo i = new EntityInfo();
		if (annotaions != null && annotaions.length > 0) {
			for (Annotation a : annotaions) {
				if (a instanceof Table) {
					hasTableAnnotation = true;
					i.setTable(((Table) a).value());
				} else if (a instanceof Database) {
					hasDatabaseAnnotaion = true;
					i.setDatabase(((Database) a).value());
				}
			}
		}
		if (hasDatabaseAnnotaion & hasTableAnnotation) {
			return i;
		} else {
			return null;
		}
	}
}

/**   
 * EntityScanner.java
 *
 * @author Rick
 * @date 2014-7-25 下午11:50:14 
 * @verion 0.1.0
 */
package com.sborm.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanIsAbstractException;
import org.springframework.beans.factory.InitializingBean;

import com.sborm.core.utils.AnnotationUtil;

/** 
 * 实体类扫描器，仅扫描符合特征的Entity类，并返回实例化EntityInfo类集合
 * EntityInfo包含 数据库名、表名、类名、RowMapper信息，都会在这个类中实例化
 * 
 * @author fengli
 * @date 2014-7-25 下午11:50:14 
 *
 */
public class EntityScanner implements InitializingBean {

	public static List<EntityInfo> getAllEntities() {
		String[] annotationClass = ApplicationContextHelper.getApplicationContext().getBeanDefinitionNames();
		List<EntityInfo> result = new ArrayList<EntityInfo>();
		if (annotationClass != null && annotationClass.length > 0) {
			for (String c : annotationClass) {
				if (!c.contains("org.spring")) {// 排除spring相关的类，稍微过滤下
					try {
						Class<?> o = ApplicationContextHelper.getBean(c).getClass();
						if (o != null && !Modifier.isAbstract(o.getModifiers())) {
							Annotation[] annotations = o.getAnnotations();
							if (annotations != null && annotations.length >= 3) {
								EntityInfo buf = AnnotationUtil.isEntity(annotations);
								if (buf != null) {
									buf.setClassName(o.getName());
									buf.setRowMapper(BaseEntityRowMapper.newInstance(o));
									result.add(buf);
								}
							}
						}
					} catch (Exception e) {
						if (!(e instanceof BeanIsAbstractException)) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
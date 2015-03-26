package com.sborm.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库注解
 * 
 * @author Rick
 * @date 2014-11-14 下午09:46:53 
 *
 */
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE) 
public @interface Database {

	String value();
}

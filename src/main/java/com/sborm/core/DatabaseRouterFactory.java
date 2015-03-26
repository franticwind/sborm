package com.sborm.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库路由工厂
 * 
 * @author fengli
 * @date 2014-7-11 下午3:14:58
 *
 */
public class DatabaseRouterFactory {

	private static final Map<String, DatabaseRouter> mapping = new HashMap<String, DatabaseRouter>();
	
	/**
	 * 添加数据库路由
	 * @param dr
	 */
	public static void addRouter(DatabaseRouter dr) {
		mapping.put(dr.getDbName(), dr);
	}
	
	/**
	 * 获取默认数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
	 * default默认取第一个配置
	 * @param entity
	 * @return
	 */
	public static JdbcTemplate getDefault(BaseEntity entity) {
		if (entity.getDatabaseRouterKey() != null) {
			return getByKey(entity.getDatabase(), entity.getDatabaseRouterKey());
		}
		return mapping.get(entity.getDatabase()).getDefault();
	}
	
	/**
	 * 获取默认读数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
	 * 如果没有配置读库，则去默认
	 * @param entity
	 * @return
	 */
	public static JdbcTemplate getReader(BaseEntity entity) {
		if (entity.getDatabaseRouterKey() != null) {
			return getByKey(entity.getDatabase(), entity.getDatabaseRouterKey());
		}
		return mapping.get(entity.getDatabase()).getReader();
	}
	
	/**
	 * 获取默认写数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
	 * 如果没有配置写库，则去默认
	 * @param entity
	 * @return
	 */
	public static JdbcTemplate getWriter(BaseEntity entity) {
		if (entity.getDatabaseRouterKey() != null) {
			return getByKey(entity.getDatabase(), entity.getDatabaseRouterKey()); 
		}
		return mapping.get(entity.getDatabase()).getWriter();
	}
	
	/**
	 * 获取指定的节点
	 * @param dbName
	 * @param key
	 * @return
	 */
	private static JdbcTemplate getByKey(String dbName, String key) {
		return mapping.get(dbName).getByKey(key);
	}
}

package com.sborm.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.Assert;

/**
 * 实体容器类，包括数据库，表名/表前缀等注解信息，启动时候统一加载，方便后续提取注解信息
 * 对RowMapper进行集中实例化，后续直接提取，对于自定义的Bean，第一次查询的时候初始化
 * 
 * @author fengli
 * @date 2014-7-9 下午4:39:15
 * 
 */
public class EntityContainer {

	private static final Map<String, String> tableMapping = new HashMap<String, String>();
	private static final Map<String, String> databaseMapping = new HashMap<String, String>();
	private static final Map<String, ParameterizedRowMapper<?>> rowMapping = new HashMap<String, ParameterizedRowMapper<?>>();

	private static boolean hasInit = false;
	
	/**
	 * 扫描所有代码注解
	 * 
	 * @Table
	 * @Database
	 */
	public synchronized static void init() {
		if (hasInit == false) {
			hasInit = true;
//			initScanPackage();
			List<EntityInfo> list = EntityScanner.getAllEntities();
			if (list != null && list.size() > 0) {
				for (EntityInfo ei : list) {
					tableMapping.put(ei.getClassName(), ei.getTable());
					databaseMapping.put(ei.getClassName(), ei.getDatabase());
					rowMapping.put(ei.getClassName(), ei.getRowMapper());
				}
			} else {
				Assert.notEmpty(list, "没有指定的Entity！");
			}
		}
	}
	
	/**
	 * 获取表注解信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getTable(BaseEntity entity) {
		return tableMapping.get(entity.getClass().getName());
	}
	
	/**
	 * 获取表注解信息
	 * @param clazz
	 * @return
	 */
	public static String getTable(Class<?> clazz) {
		return tableMapping.get(clazz.getName());
	}

	/**
	 * 获取数据库注解信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getDatabase(BaseEntity entity) {
		return databaseMapping.get(entity.getClass().getName());
	}
	
	/**
	 * 获取数据库注解信息
	 * @param clazz
	 * @return
	 */
	public static String getDatabase(Class<?> clazz) {
		return databaseMapping.get(clazz.getName());
	}

	/**
	 * 获取RowMapper实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static ParameterizedRowMapper<?> getRowMapper(Class<?> clazz) {
		ParameterizedRowMapper<?> rm = rowMapping.get(clazz.getName());
		if (rm == null) {
			rm = BaseEntityRowMapper.newInstance(clazz);
			rowMapping.put(clazz.getName(), rm);
		}
		return rm;
	}
	
	/**
	 * 获取row mapper
	 *
	 * @param entity
	 * @return
	 */
	public static ParameterizedRowMapper<?> getRowMapper(BaseEntity entity) {
		return getRowMapper(entity.getClass());
	}

}

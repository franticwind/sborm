package com.sborm.core;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * 实体类信息，类加载初始化时候使用
 * 
 * @author fengli
 * @date 2014-7-10 下午6:20:57
 *
 */
public class EntityInfo {

	private String className;
	private String table;
	private String database;
	private ParameterizedRowMapper<?> rowMapper;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public ParameterizedRowMapper<?> getRowMapper() {
		return rowMapper;
	}
	public void setRowMapper(ParameterizedRowMapper<?> rowMapper) {
		this.rowMapper = rowMapper;
	}
}

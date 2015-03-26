package com.sborm.core.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象语法组装基类，所有builder继承该类
 * 
 * @author fengli
 * @date 2014-7-8 下午3:32:40
 *
 */
public abstract class GrammarBuilder {

	public List<Object> parameters = new ArrayList<Object>();
	public StringBuilder sql = new StringBuilder("");
	
	/**
	 * 获取sql对象
	 * @return
	 */
	public StringBuilder getSql() {
		return sql;
	}
	
	/**
	 * 获取参数列表对象
	 * @return
	 */
	public List<Object> getParameters() {
		return parameters;
	}
	
	/**
	 * 添加到上级builder
	 * @param superBuilder
	 */
	public void appendTo(GrammarBuilder superBuilder) {
		clear();
		this.build();
		superBuilder.getSql().append(" " + sql.toString() + " ");
		if (parameters.size() > 0) {
			superBuilder.getParameters().addAll(parameters);
		}	
	}
	
	/**
	 * 情况组装器
	 */
	public void clear() {
		this.sql = new StringBuilder();
		this.parameters = new ArrayList<Object>(); 
	}
	
	/**
	 * 获取sql语句
	 * @return
	 */
	public String toSql() {
//		if (reset) {
//			clear();
//		}
//		reset = true;
//		this.build();
		return sql.toString();
	}
	
	/**
	 * 连接外部参数
	 * @param externalParameters
	 * @return
	 */
	public String toSql(List<Object> externalParameters) {
//		clear();
		externalParameters.addAll(this.parameters);
//		this.build();
		this.parameters = externalParameters;
		return sql.toString();
	}
	
	/**
	 * 抽象组装接口
	 */
	public abstract GrammarBuilder build();
}

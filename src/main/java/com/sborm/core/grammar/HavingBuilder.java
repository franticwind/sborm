package com.sborm.core.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Having 语法组装
 * @author fengli
 * @date 2014-7-8 下午2:44:15
 *
 */
public class HavingBuilder extends GrammarBuilder {

	private final QueryMode buildType;
	private final List<QueryCondition> condtions = new ArrayList<QueryCondition>();
	
	/**
	 * 构造函数，指定组装语句类型，AND/OR
	 * @param buildType
	 */
	public HavingBuilder(QueryMode buildType) {
		this.buildType = buildType;
	}
	
	/**
	 * 添加查询条件
	 * 调用方法：addCondition(QueryCondition.EQ(column, 1) ... 
	 * @param condition
	 * @return
	 */
	public HavingBuilder addCondition(QueryCondition condition) {
		condtions.add(condition);
		return this;
	}
	
	/**
	 * 组装查询语句
	 * @param buildType 条件组装方式，1：所有and 2：所有or
	 * @return
	 */
	@Override
	public GrammarBuilder build() {
		super.clear();
		int size = condtions.size();
		int index = 0;
		String spliter = "";
		if (this.buildType == QueryMode.AND) {
			spliter = " and ";
		} else {
			spliter = " or ";
		}
		if (size > 0) {
			super.sql.append("having ");
			for (QueryCondition c : condtions) {
				super.sql.append(c.toSql());
				super.parameters.addAll(c.getValues());
				if (index < (size - 1)) {
					super.sql.append(spliter);
				}
				index++;
			}
		}
		return this;
	}
}

package com.sborm.core.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件组装器，满足大多数单表查询场景，所有条件都是and或者or关系，否则，还是自己写sql吧
 * @author fengli
 * @date 2014-7-7 下午2:56:31
 *
 */
public class WhereBuilder extends GrammarBuilder {

	private final List<QueryCondition> condtions = new ArrayList<QueryCondition>();
	private QueryMode mode;
	
	/**
	 * 构造函数，指定组装语句类型，AND/OR
	 * @param mode
	 */
	public WhereBuilder(QueryMode mode) {
		this.mode = mode;
	}
	
	public void setQueryMode(QueryMode mode) {
		this.mode = mode;
	}
	/**
	 * 添加查询条件
	 * 调用方法：add(QueryCondition.EQ(column, 1) ... 
	 * @param condition
	 * @return
	 */
	public WhereBuilder add(QueryCondition condition) {
		condtions.add(condition);
		return this;
	}
	
	@Override
	public GrammarBuilder build() {
		super.clear();
		int size = condtions.size();
		int index = 0;
		String spliter = "";
		if (this.mode == QueryMode.AND) {
			spliter = " and ";
		} else {
			spliter = " or ";
		}
		if (size > 0) {
			super.sql.append("where ");
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

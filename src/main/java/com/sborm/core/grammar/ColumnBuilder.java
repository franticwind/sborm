package com.sborm.core.grammar;

import java.util.HashSet;
import java.util.Set;

/**
 * 查询字段组装器
 * @author fengli
 * @date 2014-7-7 下午5:20:30
 *
 */
public class ColumnBuilder extends GrammarBuilder {

	private boolean hasAddGroupCount = false;
	private static final String GROUP_COUNT = "groupCount";
	// 字段不能重复
	private final Set<String> columns = new HashSet<String>();
	
	/**
	 * 添加查询的字段（一个字段只能添加一次）
	 * @param columns
	 * @return
	 */
	public ColumnBuilder select(String ... columns) {
		for (String c : columns) {
			if (c != null && c.length() > 0) {
				this.columns.add(c);
			}
		}
		return this;
	}
	
	/**
	 * 添加查询的字段（去重，一般都是单字段查询了）
	 * @param columns
	 * @return
	 */
	public ColumnBuilder distinct(String columns) {
		this.columns.add("distinct (" + columns + ") as " + columns);
		return this;
	}
	
	/**
	 * 添加分组统计字段（一般只会有一个统计项的吧？？？）
	 * @return
	 */
	public ColumnBuilder groupCount() {
		if (!hasAddGroupCount) {// 只能添加一次
			this.columns.add("count(0) as " + GROUP_COUNT);
			hasAddGroupCount = true;
		}
		return this;
	}
	
	@Override
	public GrammarBuilder build() {
		super.clear();
		int size = columns.size();
		int index = 0;
		if (size > 0) {
			for (String c : columns) {
				super.sql.append(c);
				if (index < (size - 1)) {
					super.sql.append(",");
				}
				index++;
			}
		} else {
			super.sql.append("*");
		}
		return this;
	}
	
}

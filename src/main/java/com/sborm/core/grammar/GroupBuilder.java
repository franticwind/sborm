package com.sborm.core.grammar;

import java.util.Set;
import java.util.TreeSet;

/**
 * 分组语法组装
 * 通常分组只是做统计用，完了会有一些字段展示，
 * 因此，在BaseEntity中设置一个存放分组统计的属性
 * 
 * @author fengli
 * @date 2014-7-8 上午10:45:08
 *
 */
public class GroupBuilder extends GrammarBuilder {
	
	// 字段不能重复
	private final Set<String> columns = new TreeSet<String>();

	/**
	 * 选择分组，顺序和添加的顺序一致
	 * @param columns
	 * @return
	 */
	public GroupBuilder selectColumn(String columns) {
		this.columns.add(columns);
		return this;
	}
	
	@Override
	public GrammarBuilder build() {
		super.clear();
		int size = columns.size();
		int index = 0;
		if (size > 0) {
			super.sql.append("group by ");
			for (String c : columns) {
				super.sql.append(c);
				if (index < (size - 1)) {
					super.sql.append(",");
				}
				index++;
			}
		}
		return this;
	}
}

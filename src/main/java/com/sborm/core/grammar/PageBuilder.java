package com.sborm.core.grammar;

import com.sborm.core.PageResult;

/**
 * 分页语句组装
 * @author fengli
 * @date 2014-7-15 上午10:50:20
 *
 */
public class PageBuilder extends GrammarBuilder {
	
	private final int startIndex;
	private final int pageSize;
	
	public PageBuilder(PageResult<?> page) {
		startIndex = page.getFirstRecordIndex();
		pageSize = page.getPageSize();
	}
	
	public PageBuilder(int pageIndex, int pageSize) {
		this.startIndex = (pageIndex > 0 && pageSize > 0) ? (pageIndex - 1) * pageSize : 0;
		this.pageSize = pageSize;
	}

	@Override
	public GrammarBuilder build() {
		super.clear();
		super.sql.append(" limit ?,?");
		super.parameters.add(startIndex);
		super.parameters.add(pageSize);
		return this;
	}

}

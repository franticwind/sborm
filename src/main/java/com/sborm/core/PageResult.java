package com.sborm.core;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * 
 * @author Rick
 * @date 2015-3-16 下午10:58:31 
 *
 */
public class PageResult<T> implements Serializable {
	
	private static final long serialVersionUID = 8616490771677129692L;
	
	private int pageIndex;	// 当前页，从1开始
	private int pageSize;
	private int pageCount;
	private long resultCount;
	private List<T> results;
	
	public PageResult(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public PageResult(List<T> results, int pageIndex, int pageSize, int resultCount) {
		this.pageIndex = pageIndex;
		// 默认20，如果pageSize非大于0的时候，采用默认值
		this.pageSize = pageSize > 0 ? pageSize : 20;
		this.resultCount = resultCount;
		this.pageCount = (resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
		this.results = results;
	}
	
	public PageResult(int pageIndex, int pageSize, int resultCount) {
		this.pageIndex = pageIndex;
		// 默认20，如果pageSize非大于0的时候，采用默认值
		this.pageSize = pageSize > 0 ? pageSize : 20;
		this.resultCount = resultCount;
		this.pageCount = (resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		this.pageCount = (int)(resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public long getResultCount() {
		return resultCount;
	}
	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public boolean hasNextPage() {
		return pageIndex < pageCount ? true : false;
	}
	public boolean hasPrevPage() {
		return pageIndex > 1 ? true : false;
	}
	public int getFirstRecordIndex() {
		if (pageIndex > 0 && pageSize > 0) {
			return (pageIndex - 1) * pageSize;
		} else {
			return 0;
		}
	}
}

package com.sborm.example.dao;

import com.sborm.core.dao.IBaseDao;

public interface ITestDao extends IBaseDao {

	/**
	 * 复杂查询 demo
	 * @param objects
	 */
	public void complexSelect(Object... objects);
}

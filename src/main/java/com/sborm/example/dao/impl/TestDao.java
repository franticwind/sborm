package com.sborm.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.sborm.core.dao.BaseDao;
import com.sborm.example.dao.ITestDao;

@Repository
public class TestDao extends BaseDao implements ITestDao {
	

	@Override
	public void complexSelect(Object... objects) {
	}
}

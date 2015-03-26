package com.sborm.core.dao;

import java.util.List;
import java.util.Map;

import com.sborm.core.BaseEntity;
import com.sborm.core.DatabaseRouterFactory;
import com.sborm.core.EntityContainer;
import com.sborm.core.PageResult;
import com.sborm.core.grammar.QueryBuilder;
import com.sborm.core.grammar.SQL;
import com.sborm.core.utils.EntityUtil;

/**
 * DAO基类实现，所有DAO方法继承该类
 * 
 * @author fengli
 * @date 2014-7-11 下午2:34:10
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao implements IBaseDao {
	
	@Override
	public Object get(QueryBuilder queryBuilder) {
		List<?> list = select(queryBuilder);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<?> select(QueryBuilder queryBuilder) {
		List<?> list = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).query(SQL.select(queryBuilder),
				queryBuilder.getParameters().toArray(), EntityContainer.getRowMapper(queryBuilder.getEntity()));
		return list;
	}

	@Override
	public void select(QueryBuilder queryBuilder, PageResult<?> pageResult) {
		// 组织统计builder
		QueryBuilder countBuilder = new QueryBuilder(queryBuilder.getEntity());
		countBuilder.setWhereBuilder(queryBuilder.getWhereBuilder());
		long count = count(countBuilder);
		// 设置分页，如果存在会覆盖
		queryBuilder.page(pageResult);
		List list = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).query(SQL.select(queryBuilder), 
				queryBuilder.parameters.toArray(), EntityContainer.getRowMapper(queryBuilder.getEntity()));
		pageResult.setResultCount(count);
		pageResult.setResults(list);
	}

	@Override
	public int insert(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getColumnAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], null);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int insertIgnore(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getColumnAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], SQL.OPT_IGNORE);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int insertDelayed(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getColumnAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], SQL.OPT_DELAYED);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int replace(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getColumnAndValue(pojo);
		String sql = SQL.replace(entity, buf[0], null);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}

	@Override
	public int relpaceDelayed(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getColumnAndValue(pojo);
		String sql = SQL.replace(entity, buf[0], SQL.OPT_DELAYED);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int update(QueryBuilder queryBuilder) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(queryBuilder.getEntity());
		Map<String, Object> fields = EntityUtil.getColumnAndValueWithoutNull(pojo);
		String sql = SQL.update(queryBuilder, fields);
		int c = DatabaseRouterFactory.getWriter(queryBuilder.getEntity()).update(sql, queryBuilder.getParameters().toArray());
		return c;
	}
	
	@Override
	public int updateIncrement(String column, int value, QueryBuilder queryBuilder) {
		String sql = SQL.updateIncrement(column, value, queryBuilder);
		int c = DatabaseRouterFactory.getWriter(queryBuilder.getEntity()).update(sql, queryBuilder.getParameters().toArray());
		return c;
	}

	@Override
	public int delete(QueryBuilder queryBuilder) {
		String sql = SQL.delete(queryBuilder);
		int c = DatabaseRouterFactory.getWriter(queryBuilder.getEntity()).update(sql, queryBuilder.getParameters().toArray());
		return c;
	}

	@Override
	public long count(QueryBuilder queryBuilder) {
		String sql = SQL.count(queryBuilder);
		long c = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).queryForLong(sql, queryBuilder.getParameters().toArray());
		return c;
	}
	
}

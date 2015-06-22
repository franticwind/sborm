package com.sborm.core.dao;

import java.util.List;
import java.util.Map;

import com.sborm.core.BaseEntity;
import com.sborm.core.DatabaseRouterFactory;
import com.sborm.core.EntityContainer;
import com.sborm.core.PageResult;
import com.sborm.core.grammar.QueryBuilder;
import com.sborm.core.grammar.QueryMode;
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
	public Object getByExample(BaseEntity entity) {
		return getByExample(entity, QueryMode.AND);
	}
	
	@Override
	public Object getByExample(BaseEntity entity, QueryMode mode) {
		return get(entity.getQueryBuilder().setQueryMode(mode));
	}
	
	@Override
	public List<?> select(QueryBuilder queryBuilder) {
		List<?> list = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).query(SQL.select(queryBuilder),
				queryBuilder.getParameters().toArray(), EntityContainer.getRowMapper(queryBuilder.getEntity()));
		return list;
	}
	
	@Override
	public List<?> selectByExample(BaseEntity entity) {
		return selectByExample(entity, QueryMode.AND);
	}
	
	@Override
	public List<?> selectByExample(BaseEntity entity, QueryMode mode) {
		return select(entity.getQueryBuilder().setQueryMode(mode));
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
	public PageResult<?> select(QueryBuilder queryBuilder, int pageIndex, int pageSize) {
		QueryBuilder countBuilder = new QueryBuilder(queryBuilder.getEntity());
		countBuilder.setWhereBuilder(queryBuilder.getWhereBuilder());
		long count = count(countBuilder);
		queryBuilder.page(pageIndex, pageSize);
		List<?> list = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).query(SQL.select(queryBuilder), 
				queryBuilder.parameters.toArray(), EntityContainer.getRowMapper(queryBuilder.getEntity()));
		PageResult pageResult = new PageResult(list, pageIndex, pageSize, (int)count);
		return pageResult;
	}
	
	@Override
	public PageResult<?> selectByExample(BaseEntity entity, int pageIndex, int pageSize) {
		return selectByExample(entity, QueryMode.AND, pageIndex, pageSize);
	}
	
	@Override
	public PageResult<?> selectByExample(BaseEntity entity, QueryMode mode, int pageIndex, int pageSize) {
		return select(entity.getQueryBuilder().setQueryMode(mode), pageIndex, pageSize);
	}

	@Override
	public int insert(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getPropertyAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], null);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int insertIgnore(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getPropertyAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], SQL.OPT_IGNORE);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int insertDelayed(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getPropertyAndValue(pojo);
		String sql = SQL.insert(entity, buf[0], SQL.OPT_DELAYED);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}
	
	@Override
	public int replace(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getPropertyAndValue(pojo);
		String sql = SQL.replace(entity, buf[0], null);
		int c = DatabaseRouterFactory.getWriter(entity).update(sql, buf[1]);
		return c;
	}

	@Override
	public int relpaceDelayed(BaseEntity entity) {
		Map<String, Object> pojo = EntityUtil.getEntityInfo(entity);
		Object[][] buf = EntityUtil.getPropertyAndValue(pojo);
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
	public int updateByExample(BaseEntity entity) {
		return updateByExample(entity, QueryMode.AND);
	}
	
	@Override
	public int updateByExample(BaseEntity entity, QueryMode mode) {
		return update(entity.getQueryBuilder().setQueryMode(mode));
	}
	
	@Override
	public int updateIncrement(String column, int incr, QueryBuilder queryBuilder) {
		String sql = SQL.updateIncrement(column, incr, queryBuilder);
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
	public int deleteByExample(BaseEntity entity) {
		return deleteByExample(entity, QueryMode.AND);
	}
	
	@Override
	public int deleteByExample(BaseEntity entity, QueryMode mode) {
		return delete(entity.getQueryBuilder().setQueryMode(mode));
	}

	@Override
	public long count(QueryBuilder queryBuilder) {
		String sql = SQL.count(queryBuilder);
		long c = DatabaseRouterFactory.getReader(queryBuilder.getEntity()).queryForLong(sql, queryBuilder.getParameters().toArray());
		return c;
	}
	
	@Override
	public long countByExample(BaseEntity entity) {
		return countByExample(entity, QueryMode.AND);
	}
	
	@Override
	public long countByExample(BaseEntity entity, QueryMode mode) {
		return count(entity.getQueryBuilder().setQueryMode(mode));
	}
}

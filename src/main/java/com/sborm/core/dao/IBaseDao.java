package com.sborm.core.dao;

import java.util.List;

import com.sborm.core.BaseEntity;
import com.sborm.core.PageResult;
import com.sborm.core.grammar.QueryBuilder;
import com.sborm.core.grammar.QueryMode;

/**
 * 基类Dao接口，只能满足单表，简单业务操作
 * @author fengli
 * @date 2014-6-27 上午11:29:43
 *
 * @param 
 */
public interface IBaseDao {
	
	/**
	 * 获取单个对象
	 * @param queryBuilder
	 * @return
	 */
	public Object get(QueryBuilder queryBuilder);
	
	/**
	 * 根据Entity模板查询，默认AND模式
	 *
	 * @param entity
	 * @return
	 */
	public Object getByExample(BaseEntity entity);
	
	/**
	 * 根据Entity模板查询，自定义AND或者OR
	 *
	 * @param entity
	 * @param mode
	 * @return
	 */
	public Object getByExample(BaseEntity entity, QueryMode mode);
	
	/**
	 * 无分页查询
	 * @param queryBuilder
	 * @return
	 */
	public List<?> select(QueryBuilder queryBuilder);
	
	/**
	 * 根据Entity模板查询，默认AND模式
	 *
	 * @param entity
	 * @return
	 */
	public List<?> selectByExample(BaseEntity entity);
	
	/**
	 * 根据Entity模板查询，自定义AND或者OR
	 *
	 * @param entity
	 * @param mode
	 * @return
	 */
	public List<?> selectByExample(BaseEntity entity, QueryMode mode);
	
	/**
	 * 分页查询
	 * @param queryBuilder
	 * @param pageResult
	 */
	@Deprecated
	public void select(QueryBuilder queryBuilder, PageResult<?> pageResult);
	
	/**
	 * 分页查询
	 *
	 * @param queryBuilder
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageResult<?> select(QueryBuilder queryBuilder, int pageIndex, int pageSize);
	
	/**
	 * 分页查询，根据Entity模板查询，默认AND模式
	 *
	 * @param entity
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageResult<?> selectByExample(BaseEntity entity, int pageIndex, int pageSize);
	
	/**
	 * 分页查询，根据Entity模板查询，自定义查询模板
	 *
	 * @param entity
	 * @param mode
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageResult<?> selectByExample(BaseEntity entity, QueryMode mode, int pageIndex, int pageSize);
	
	/**
	 * 插入记录
	 * insert into
	 * @param entity
	 * @return
	 */
	public int insert(BaseEntity entity);
	
	/**
	 * 插入记录，如果存在主键则忽略，没有主键的情况跟insert方法一样
	 * insert ignore into
	 *
	 * @param entity
	 * @return
	 */
	public int insertIgnore(BaseEntity entity);
	
	/**
	 * 延时模式插入
	 * insert delayed ... 
	 *
	 * @param entity
	 * @return
	 */
	public int insertDelayed(BaseEntity entity);
	
	/**
	 * 覆盖记录，必须要有主键
	 *
	 * @param entity
	 * @return
	 */
	public int replace(BaseEntity entity);
	
	/**
	 * 延时模式覆盖
	 * replace delayed ... 
	 *
	 * @param entity
	 * @return
	 */
	public int relpaceDelayed(BaseEntity entity);
	
	/**
	 * 更新记录
	 * @param queryBuilder
	 * @return
	 */
	public int update(QueryBuilder queryBuilder);
	
	/**
	 * 更新记录，根据entity模板，默认AND模式
	 *
	 * @param entity
	 * @return
	 */
	public int updateByExample(BaseEntity entity);
	
	/**
	 * 更新记录，根据entity模板，自定义AND或者OR模式
	 *
	 * @param entity
	 * @param mode
	 * @return
	 */
	public int updateByExample(BaseEntity entity, QueryMode mode);
	
	/**
	 * 更新记录，指定字段累加，提供这个接口避免需要先查询出来再累加
	 * update xxx set a+=1 where ...
	 * @param column
	 * @param incr
	 * @param queryBuilder
	 * @return
	 */
	public int updateIncrement(String column, int incr, QueryBuilder queryBuilder);
	
	/**
	 * 删除记录
	 * @param queryBuilder
	 * @return
	 */
	public int delete(QueryBuilder queryBuilder);
	
	/**
	 * 删除记录，根据entity模板，默认AND
	 *
	 * @param entity
	 * @return
	 */
	public int deleteByExample(BaseEntity entity);
	
	/**
	 * 删除记录，根据entity模板，自定义AND或者OR
	 *
	 * @param entity
	 * @param mode
	 * @return
	 */
	public int deleteByExample(BaseEntity entity, QueryMode mode);
	
	/**
	 * 统计
	 * @param queryBuilder
	 * @return
	 */
	public long count(QueryBuilder queryBuilder);
	
	/**
	 * 统计，根据entity模板，默认AND
	 *
	 * @param entity
	 * @return
	 */
	public long countByExample(BaseEntity entity);
	
	/**
	 * 统计，根据entity模板，自定义AND或者OR
	 *
	 * @param entity
	 * @param mode
	 * @return
	 */
	public long countByExample(BaseEntity entity, QueryMode mode);
	
}

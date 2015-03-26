package com.sborm.core.dao;

import java.util.List;

import com.sborm.core.BaseEntity;
import com.sborm.core.PageResult;
import com.sborm.core.grammar.QueryBuilder;

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
	 * 无分页查询
	 * @param queryBuilder
	 * @return
	 */
	public List<?> select(QueryBuilder queryBuilder);
	
	/**
	 * 分页查询
	 * @param queryBuilder
	 * @param pageResult
	 */
	public void select(QueryBuilder queryBuilder, PageResult<?> pageResult);
	
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
	 * 更新记录，指定字段累加
	 * update xxx set a+=1 where ...
	 * @param column
	 * @param value
	 * @param queryBuilder
	 * @return
	 */
	public int updateIncrement(String column, int value, QueryBuilder queryBuilder);

	/**
	 * 删除记录
	 * @param queryBuilder
	 * @return
	 */
	public int delete(QueryBuilder queryBuilder);
	
	/**
	 * 统计
	 * @param queryBuilder
	 * @return
	 */
	public long count(QueryBuilder queryBuilder);
	
}

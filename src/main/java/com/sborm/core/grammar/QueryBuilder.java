package com.sborm.core.grammar;

import com.sborm.core.BaseEntity;
import com.sborm.core.PageResult;

/**
 * Sql 组装器，统一处理查询语法，核心类
 * 
 * @author fengli
 * @date 2014-7-8 上午9:50:07
 * 
 */
public class QueryBuilder extends GrammarBuilder {

	// 实体类，必选
	private final BaseEntity entity;
	private ColumnBuilder columnBuilder;
	private WhereBuilder whereBuilder;
	private OrderBuilder orderBuilder;
	private GroupBuilder groupBuilder;
	private HavingBuilder havingBuilder;
	private PageBuilder pageBuilder;

	public QueryBuilder(BaseEntity entity) {
		this.entity = entity;
	}

	public ColumnBuilder getColumnBuilder() {
		return columnBuilder;
	}

	public void setColumnBuilder(ColumnBuilder columnBuilder) {
		this.columnBuilder = columnBuilder;
	}

	public WhereBuilder getWhereBuilder() {
		return whereBuilder;
	}

	public void setWhereBuilder(WhereBuilder whereBuilder) {
		this.whereBuilder = whereBuilder;
	}

	public OrderBuilder getOrderBuilder() {
		return orderBuilder;
	}

	public void setOrderBuilder(OrderBuilder orderBuilder) {
		this.orderBuilder = orderBuilder;
	}

	public GroupBuilder getGroupBuilder() {
		return groupBuilder;
	}

	public void setGroupBuilder(GroupBuilder groupBuilder) {
		this.groupBuilder = groupBuilder;
	}

	public HavingBuilder getHavingBuilder() {
		return havingBuilder;
	}

	public void setHavingBuilder(HavingBuilder havingBuilder) {
		this.havingBuilder = havingBuilder;
	}

	public PageBuilder getPageBuilder() {
		return pageBuilder;
	}

	public void setPageBuilder(PageBuilder pageBuilder) {
		this.pageBuilder = pageBuilder;
	}

	public BaseEntity getEntity() {
		return entity;
	}


	/**
	 * 获取列组装器对象
	 * @return
	 */
	public ColumnBuilder columns() {
		if (this.columnBuilder == null) {
			this.columnBuilder = new ColumnBuilder();
		}
		return this.columnBuilder;
	}

	/**
	 * 获取Where组装对象，默认and条件组合模式
	 * @return
	 */
	public WhereBuilder where() {
		if (this.whereBuilder == null) {
			this.whereBuilder = new WhereBuilder(QueryMode.AND);
		}
		return this.whereBuilder;
	}
	
	/**
	 * 获取Where组装对象，自定义条件组合模式
	 * @param mode
	 * @return
	 */
	public WhereBuilder where(QueryMode mode) {
		if (this.whereBuilder == null) {
			this.whereBuilder = new WhereBuilder(mode);
		}
		return this.whereBuilder;
	}
	
	/**
	 * 设置查询模式，就是多个查询条件的组合模式
	 * 只提供and或者or两种单一的组合，不支持混合模式，如果不设置，默认为and
	 *
	 * @param mode
	 */
	public QueryBuilder setQueryMode(QueryMode mode) {
		if (this.whereBuilder != null) {
			this.whereBuilder.setQueryMode(mode);
		}
		return this;
	}

	/**
	 * 或者排序对象
	 * @return
	 */
	public OrderBuilder order() {
		if (this.orderBuilder == null) {
			this.orderBuilder = new OrderBuilder();
		}
		return this.orderBuilder;
	}

	/**
	 * 获取group对象
	 * @return
	 */
	public GroupBuilder group() {
		if (this.groupBuilder == null) {
			this.groupBuilder = new GroupBuilder();
		}
		return this.groupBuilder;
	}

	/**
	 * 添加having条件
	 * 
	 * @param havingBuilder
	 * @return
	 */
	public QueryBuilder having(HavingBuilder havingBuilder) {
		this.havingBuilder = havingBuilder;
		return this;
	}
	
	/**
	 * 获取having组装对象，默认and条件组合模式
	 * @return
	 */
	public HavingBuilder having() {
		if (this.havingBuilder == null) {
			this.havingBuilder = new HavingBuilder(QueryMode.AND);
		}
		return this.havingBuilder;
	}
	
	/**
	 * 获取having组装对象，自定义条件组合模式
	 * @param mode
	 * @return
	 */
	public HavingBuilder having(QueryMode mode) {
		if (this.havingBuilder == null) {
			this.havingBuilder = new HavingBuilder(mode);
		}
		return this.havingBuilder;
	}

	/**
	 * 设置分页参数
	 * 
	 * @param pageBuilder
	 * @return
	 */
	public QueryBuilder page(int pageIndex, int pageSize) {
		this.pageBuilder = new PageBuilder(pageIndex, pageSize);
		return this;
	}
	
	/**
	 * 设置分页参数
	 * @param page
	 * @return
	 */
	public QueryBuilder page(PageResult<?> page) {
		this.pageBuilder = new PageBuilder(page);
		return this;
	}

	@Override
	public GrammarBuilder build() {
//			super.sql.append("select ");
//			// 组装查询字段
//			if (columnBuilder != null) {
//				columnBuilder.appendTo(this);
//			} else {
//				super.sql.append(" * ");
//			}
//			// 组装表名
//			super.sql.append(" from ");
//			super.sql.append(entity.getDatabase() + "."
//					+ entity.getFullTableName());
//		super.sql = new StringBuilder();
		// 组装where
		if (whereBuilder != null) {
			whereBuilder.appendTo(this);
		}
		// 组装group
		if (groupBuilder != null) {
			groupBuilder.appendTo(this);
		}
		// 组装having
		if (havingBuilder != null) {
			havingBuilder.appendTo(this);
		}
		// 组装order
		if (orderBuilder != null) {
			orderBuilder.appendTo(this);
		}
		// 组装分页
		if (pageBuilder != null) {
			pageBuilder.appendTo(this);
		}
		return this;
	}
}

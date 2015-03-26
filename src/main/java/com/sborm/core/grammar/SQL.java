package com.sborm.core.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sborm.core.BaseEntity;

/**
 * sql语法拼装类
 * 
 * @author fengli
 * @date 2014-7-11 下午2:35:55
 * 
 */
public class SQL {
	
	public static final String OPT_IGNORE = "ignore";
	public static final String OPT_DELAYED = "delayed";

	/**
	 * insert 语句
	 * 
	 * @param entity
	 * @param column
	 * @return
	 */
	public static String insert(BaseEntity entity, Object[] column, String option) {
		String opt = option == null ? "" : option;
		StringBuilder sb = new StringBuilder("insert " + opt + " into "
				+ entity.getDatabase() + "." + entity.getFullTableName() + " (");
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		for (int i = 0; i < column.length; i++) {
			fields.append(column[i]);
			values.append("?");
			if (i < column.length - 1) {
				fields.append(",");
				values.append(",");
			}
		}
		sb.append(fields.toString());
		sb.append(") values (");
		sb.append(values.toString());
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * replace语句
	 *
	 * @param entity
	 * @param column
	 * @return
	 */
	public static String replace(BaseEntity entity, Object[] column, String option) {
		String opt = option == null ? "" : option;
		StringBuilder sb = new StringBuilder("replace " + opt + " into "
				+ entity.getDatabase() + "." + entity.getFullTableName() + " (");
		StringBuilder fields = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		for (int i = 0; i < column.length; i++) {
			fields.append(column[i]);
			values.append("?");
			if (i < column.length - 1) {
				fields.append(",");
				values.append(",");
			}
		}
		sb.append(fields.toString());
		sb.append(") values (");
		sb.append(values.toString());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * update 语句
	 * 
	 * @param entity
	 * @param column
	 * @return
	 */
	public static String update(QueryBuilder queryBuilder, Map<String, Object> fields) {
		if (queryBuilder.getEntity() != null) {
			queryBuilder.sql.append("update ");
			queryBuilder.sql.append(queryBuilder.getEntity().getDatabase()
					+ "." + queryBuilder.getEntity().getFullTableName());
			int size = fields.size();
			int index = 0;
			if (size > 0) {
				queryBuilder.sql.append(" set ");
				for (String c : fields.keySet()) {
					queryBuilder.sql.append(c + "=?");
					if (index < (size - 1)) {
						queryBuilder.sql.append(",");
					}
					index++;
				}
				List<Object> parametersList = new ArrayList<Object>();
				for (String c : fields.keySet()) {
					parametersList.add(fields.get(c));
				}
				return queryBuilder.build().toSql(parametersList);
			} else {
				throw new GrammarException("没有需要更新的字段！");
			}
		} else {
			throw new GrammarException("实体类不能为空！");
		}
	}
	
	/**
	 * 字段累加
	 *
	 * @param column
	 * @param value
	 * @param queryBuilder
	 * @return
	 */
	public static String updateIncrement(String column, int value, QueryBuilder queryBuilder) {
		if (queryBuilder.getEntity() != null) {
			queryBuilder.sql.append("update ");
			queryBuilder.sql.append(queryBuilder.getEntity().getDatabase()
					+ "." + queryBuilder.getEntity().getFullTableName());
			queryBuilder.sql.append(" set " + column + "=" + column + " + " + value + " ");
			return queryBuilder.build().toSql();
		} else {
			throw new GrammarException("实体类不能为空！");
		}
	}

	/**
	 * select 语句
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public static String select(QueryBuilder queryBuilder) {
		if (queryBuilder.getEntity() != null) {
			queryBuilder.clear();
			queryBuilder.sql.append("select ");
			// 组装查询字段
			if (queryBuilder.getColumnBuilder() != null) {
				queryBuilder.getColumnBuilder().appendTo(queryBuilder);
			} else {
				queryBuilder.sql.append(" * ");
			}
			// 组装表名
			queryBuilder.sql.append(" from ");
			queryBuilder.sql.append(queryBuilder.getEntity().getDatabase()
					+ "." + queryBuilder.getEntity().getFullTableName());
			return queryBuilder.build().toSql();
		} else {
			throw new GrammarException("实体类不能为空！");
		}
	}

	/**
	 * count语句
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public static String count(QueryBuilder queryBuilder) {
		if (queryBuilder.getEntity() != null) {
			queryBuilder.sql.append("select count(0) from ");
			// 组装表名
			queryBuilder.sql.append(queryBuilder.getEntity().getDatabase()
					+ "." + queryBuilder.getEntity().getFullTableName());
			return queryBuilder.build().toSql();
		} else {
			throw new GrammarException("实体类不能为空！");
		}
	}

	/**
	 * delete语句
	 * 
	 * @param queryBuilder
	 * @return
	 */
	public static String delete(QueryBuilder queryBuilder) {
		if (queryBuilder.getEntity() != null) {
			queryBuilder.sql.append("delete from ");
			// 组装表名
			queryBuilder.sql.append(queryBuilder.getEntity().getDatabase()
					+ "." + queryBuilder.getEntity().getFullTableName());
			return queryBuilder.build().toSql();
		} else {
			throw new GrammarException("实体类不能为空！");
		}
	}

}

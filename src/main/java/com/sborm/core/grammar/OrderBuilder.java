package com.sborm.core.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序方式
 * @author fengli
 * @date 2014-5-26 下午3:42:53
 *
 */
public class OrderBuilder extends GrammarBuilder {
	
	private final List<Order> orders = new ArrayList<Order>();
	
	private class Order {
		
		private final String column;
		private final OrderMode mode;
		
		public Order(String column, OrderMode mode) {
			this.column = column;
			this.mode = mode;
		}
		
		public String getColumn() {
			return column;
		}
		
		public OrderMode getMode() {
			return mode;
		}
	}
	
	/**
	 * 添加排序条件，具体字段先后顺序和调用的顺序一致
	 * @param column
	 * @param mode
	 * @return
	 */
	public OrderBuilder add(String column, OrderMode mode) {
		orders.add(new Order(column, mode));
		return this;
	}
	
	/**
	 * 添加升序排序条件
	 *
	 * @param column
	 * @return
	 */
	public OrderBuilder addASC(String column) {
		orders.add(new Order(column, OrderMode.ASC));
		return this;
	}
	
	/**
	 * 添加兼续排序条件
	 *
	 * @param column
	 * @return
	 */
	public OrderBuilder addDESC(String column) {
		orders.add(new Order(column, OrderMode.DESC));
		return this;
	}
	
	@Override
	public GrammarBuilder build() {
		super.clear();
		int size = orders.size();
		int index = 0;
		if (size > 0) {
			super.sql.append("order by ");
			for (Order order : orders) {
				super.sql.append(order.getColumn() + " " + (order.getMode() == OrderMode.ASC ? "ASC" : "DESC"));
				if (index < (size - 1)) {
					super.sql.append(",");
				}
				index++;
			}
		}
		return this;
	}
}

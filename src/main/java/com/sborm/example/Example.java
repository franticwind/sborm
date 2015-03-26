package com.sborm.example;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sborm.core.PageResult;
import com.sborm.core.grammar.OrderMode;
import com.sborm.core.grammar.QueryBuilder;
import com.sborm.core.grammar.QueryCondition;
import com.sborm.example.bean.Demo;
import com.sborm.example.dao.ITestDao;

public class Example {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	static ITestDao dao = (ITestDao) ctx.getBean("testDao");
	
	/**
	 * 测试插入
	 */
	public static void testInsert() {
		try {
			Demo demo = new Demo();
			demo.setCreateTime(new Date());
			demo.setName("demo_" + new Random().nextInt(10000));
			demo.setType(new Random().nextInt(5));
			demo.setPassword("000000");
			dao.insert(demo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试更新
	 */
	public static void testUpdate() {
		try {
			Demo demo = new Demo();
			demo.setName("newname");
			QueryBuilder q = new QueryBuilder(demo);
			q.where().add(QueryCondition.EQ(Demo.Columns.id, 1));	// =条件
			int c = dao.update(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试删除
	 */
	public static void testDelete() {
		try {
			Demo demo = new Demo();
			QueryBuilder q = new QueryBuilder(demo);
			q.where().add(QueryCondition.EQ(Demo.Columns.id, 1));	// =条件
			int c = dao.delete(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 普通查询
	 */
	public static void testSelect() {
		try {
			Demo demo = new Demo();
			QueryBuilder q = new QueryBuilder(demo);
			q.columns().select(Demo.Columns.id, Demo.Columns.name);	// 不选择默认查询所有，多参数模式
			q.where()
					.add(QueryCondition.EQ(Demo.Columns.name, "test"))	// =条件
					.add(QueryCondition.BETWEEN(Demo.Columns.createTime, "2014-07-10 11", "2014-07-19 12")); // between条件
			q.order().add(Demo.Columns.createTime, OrderMode.DESC);		// 条件排序
			List<?> list = dao.select(q);
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页查询
	 */
	public static void testSelectPage() {
		try {
			Demo demo = new Demo();
			QueryBuilder q = new QueryBuilder(demo);
			q.columns().select(Demo.Columns.id, Demo.Columns.name);	// 不选择默认查询所有，多参数模式
			q.where()
					.add(QueryCondition.EQ(Demo.Columns.name, "test"))	// =条件
					.add(QueryCondition.BETWEEN(Demo.Columns.createTime, "2014-07-15 11", "2014-07-19 12")); // between条件
			q.order().add(Demo.Columns.createTime, OrderMode.DESC);		// 条件排序
			
			PageResult pr = new PageResult<Demo>(1, 1);
			dao.select(q, pr);
			System.out.println(pr.getResultCount() + " - " + pr.getPageCount());
		} catch (Exception e) {
			
		}
	}

	public static void main(String[] args) {
		//testInsert();
		//testUpdate();
		//testDelete();
		//testSelect();
		//testSelectPage();
		System.out.println("finish");
	}
}

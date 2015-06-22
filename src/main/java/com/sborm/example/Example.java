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
import com.sborm.core.grammar.QueryMode;
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
			q.columns().select(Demo.Columns.id, Demo.Columns.name + " as name1");	// 不选择默认查询所有，多参数模式
			q.where()
					.add(QueryCondition.EQ(Demo.Columns.name, "newname"))	// =条件
					.add(QueryCondition.BETWEEN(Demo.Columns.createTime, "2014-07-10 11", "2014-07-19 12")); // between条件
			q.order().add(Demo.Columns.createTime, OrderMode.DESC);		// 条件排序
			List<?> list = dao.select(q);
			System.out.println(((Demo)list.get(0)).getAliasFields("name1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 基于bean querybuilder 模式查询
	 */
	public static void testSelect2() {
		try {
			Demo demo = new Demo();
			demo.queryBuilder.selectColumn(Demo.Columns.id, Demo.Columns.name);	// 不指定默认查询全部，每个查询项可以指定别名
			demo.queryBuilder.whereNameEQ("newname");
			demo.queryBuilder.whereCreateTimeBETWEEN("2014-07-10 11", "2014-07-19 12");
			demo.queryBuilder.orderByCreateTimeDESC();
			// 方式1：根据entity查询，默认AND组织多个where条件
			List list = dao.selectByExample(demo);
			System.out.println(list.size() + "  --  " + ((Demo)list.get(0)).getName());
			// 方式2：根据entity查询，自定义组织多个where条件
			list = dao.selectByExample(demo, QueryMode.OR);
			System.out.println(list.size() + "  --  " + ((Demo)list.get(0)).getName());
			// 方式3：直接获取querybuilder查询，可以设置where条件组织方式，默认是and
			list = dao.select(demo.getQueryBuilder().setQueryMode(QueryMode.AND));
			System.out.println(list.size() + "  --  " + ((Demo)list.get(0)).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页查询，普通模式
	 */
	public static void testSelectPage() {
		try {
			Demo demo = new Demo();
			QueryBuilder q = new QueryBuilder(demo);
			q.columns().select(Demo.Columns.id, Demo.Columns.name);	// 不选择默认查询所有，多参数模式
			// 也可以指定where条件组织关系，or或者and
			q.where(QueryMode.OR)
					.add(QueryCondition.EQ(Demo.Columns.name, "test"))	// =条件
					.add(QueryCondition.BETWEEN(Demo.Columns.createTime, "2014-07-15 11", "2014-07-19 12")); // between条件
			q.order().add(Demo.Columns.createTime, OrderMode.DESC);		// 条件排序
			
			PageResult pr = dao.select(q, 1, 1);
			System.out.println(pr.getResultCount() + " - " + pr.getPageCount());
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 根据bean querybuilder 模式分页查询，参考testSelect2
	 */
	public static void testSelectPage2() {
		try {
			Demo demo = new Demo();
			demo.queryBuilder.whereNameEQ("test");
			demo.queryBuilder.whereCreateTimeBETWEEN("2014-07-15 11", "2014-07-19 12");
			demo.queryBuilder.orderByCreateTimeDESC();
			PageResult pr = dao.selectByExample(demo, QueryMode.OR, 1, 1);
			System.out.println(pr.getResultCount() + " - " + pr.getPageCount());
		} catch (Exception e) {
			
		}
	}

	public static void main(String[] args) {
		testInsert();
		//testUpdate();
		//testDelete();
//		testSelect2();
//		testSelectPage();
//		testSelectPage2();
//		System.out.println("finish");
	}
}

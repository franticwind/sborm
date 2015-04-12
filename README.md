## SBORM - 介绍&使用说明 ##
Rick @2015-03-19


## 一、SBORM介绍

1、目前只考虑支持mysql；

2、基于spring jdbc的上层封装，底层jdbc操作基于JdbcTemplate，对于使用spring jdbc的人会有一点价值，比较简洁的封装可以节省很多重复劳动，具体节省多少可以看看example；

3、实现一套简单的ORM（直接使用spring rowmapper,insert自己实现），可以基于对象进行crud和相对复杂（感觉比hibernate强大一点）的sql操作；

4、基于对象指定查询的字段，大部分时候可以忘掉表结构进行业务开发；

5、支持简单的数据库路由，读写分离（半自动，需要指定取writer还是reader，默认规则reader采用随机的方式，当然也可以手动指定）；

6、支持简单的分表，主要是针对一定规则的分表，比如百分表、千分表，也可以自己指定分表后缀；

7、简单的单表查询（比如所有条件是and或者or结构），基本实现0sql代码编写（类似HibernateTemplate selectByExample、findByCriteria、find等方法）；

8、简单的单表排序支持，支持多个排序条件组合；

9、对于复杂的sql查询，提供获取jdbctemplate实例进行操作，类似spring jdbc的常规用法；

10、提供Entity代码生成接口，Entity并非简单的pojo（尽可能不要去修改此类），引入字段常量类，方便查询的时候指定选择字段，从而更好实现查询条件的封装；

## 二、为什么写SBORM？
**1、hibernate：**过于臃肿，使用不够灵活，优化难(其实主要是因为很少用)，HQL感觉就是个渣，在mysql几乎一统天下的背景下，跨数据库级别的兼容吃力不讨好。Hibernate的对象化关联处理确实挺强大，但是使用起来坑太多，有多少人敢在项目中大范围使用真不知道，屠龙刀不是人人都提的起啊。

**2、mybatis：**轻量级，基于xml的模式感觉不利于封装，代码量不小，基于xml维护也麻烦（个人观点，现在注解模式貌似也挺不错），感觉mybatis更适合存在dba角色的年代，可以远离代码进行sql调优，复杂的查询拼装起来也更加优雅（java基本就是if else ...）,但是对于查询业务简单但是数据库集群环境的场景有点憋屈（其实对mybatis使用也不多，瞎评论^_^）。

**3、spring jdbc：**小巧，灵活，足够优秀，个人比较喜欢使用，但是代码量偏大，原生的接口重复劳动量大，比如insert、mapper之类的；

SBORM只是针对spring jdbc的一些不方便的地方，做了一些封装，更加简化日常的开发工作，基于spring jdbc的RowMapper自动实现对象映射，也勉强算的上叫ORM，只是大部分功能已经由spring jdbc实现了。

平时不太喜欢使用hibernate和mybatis，主要是使用spring jdbc，写这个东西的出发点主要是平时使用spring jdbc觉得比较麻烦，重复性的代码偏多，一方面通过自动mapper降低返回结果处理工作量，另一方面参考hibernate对象化查询条件的模式，写了一个QueryBudiler，使得更多简单的单表查询可以通过对象组织查询、更改逻辑，避免过多去写相似性的SQL语句，减少DAO接口量。

## 三、一些亮点
**1、Entity的设计：**很多人看了也许会说，这个不是POJO，不是纯粹的Java Bean，显得很另类。但是有多人在开发过程中（特别是在写sql的时候），经常要去看看表结构设计？还有多少次因为改了表某个字段，还得遍历去查找哪些sql使用了这个字段？多少次看到在代码中直接传入字段名作为查询参数感到别扭？如果将表结构字段都用java对象去描述，能够解决这些问题，就不必要在乎是不是POJO了，后面看example的时候应该能体会这么做的一些好处，至少我觉得是挺方便的，将大部分查询脱离表结构设计。

**2、简单的数据库路由：**如果分库结构不是太复杂（比如简单的读写分离、或者多个库集成），BaseDao可以自动进行路由（比如读写分离，根据业务模式指定读、写库），如果非默认的路由规则，也可以通过手动设置的模式，进行数据库路由。数据库路由直接由Entity指定，所有的路由都是根据Entity识别，也就是说查询也是围绕Entity展开的，避免类似使用spring jdbc的时候，各种template实例跳来跳去，硬编码引入，写一个业务还得看看到底该用哪个template，尤其是多个数据库共用一个template实例的时候。

**3、QueryBuilder：**单表查询基本上都可以实现零Sql（除非查询条件特别复杂的），更新、删除等操作也可以通过QueryBuilder进行批量处理，不局限于根据主键来处理。

**4、分表操作的支持：**对于分表操作和常规的使用没有区别，只是指定分表规则，mybatis好像也可以通过制定参数实现分表处理，没搞清楚hibernate对这个是怎么处理的(hibernate好像是bean和表一对一绑定的)？

**5、改造RowMapper：**基于BeanPropertyRowMapper，做了些修改，改进思路主要是针对别名、函数、联合查询模式下，单个bean无法接收一些字段的问题，通过BaseEntity设置一个map，存储这些数据，改造的BaseEntityRowMapper的mapRow方法中做了特殊处理，详细的方法请看我的博客：[《关于Spring JDBC RowMapper的一点改进思路》](http://blog.csdn.net/yefeng_918/article/details/44947459)

**6、基于Entity组织查询条件：**很多朋友反馈基于QueryBuilder组织查询条件代码结构非常复杂，于是借助代码生成，在每个Entity上的添加了一个内部类EntityQueryBuildr，基于每个字段编写各种查询方法，主要是where、order两种模式，如果需要group、having等语法，还是要依赖QueryBuilder。代码结构如下(具体可以参考Demo类，以及后面的Example)：  
```java  
public void selectColumn(String ... columns) {
	entity.getQueryBuilder().columns().select(columns);
}
public EntityQueryBuilder whereIdEQ (Object value) {
	entity.getQueryBuilder().where().add(QueryCondition.EQ(Columns.id, value));
	return this;
}
public EntityQueryBuilder whereIdNEQ (Object value) {
	entity.getQueryBuilder().where().add(QueryCondition.NEQ(Columns.id, value));
	return this;
}

```


## 四、使用说明

###1、配置说明
**主要分为几个部分：**
######a、datasource配置：包括数据源、连接池等，和普通的spring配置一致；  
demo(连接池使用看个人喜好):  
<pre>
<code>
&lt;bean name="abstractDataSource" class="org.logicalcobwebs.proxool.ProxoolDataSource"
	abstract="true">
	&lt;property name="driver" value="com.mysql.jdbc.Driver" />
	&lt;property name="maximumConnectionCount" value="120" />
	&lt;property name="minimumConnectionCount" value="2" />
	&lt;property name="maximumActiveTime" value="600000" />
	&lt;property name="prototypeCount" value="2" />
	&lt;property name="simultaneousBuildThrottle" value="120" />
	&lt;property name="houseKeepingTestSql" value="select 1"/>
	&lt;property name="testBeforeUse" value="true" />
	&lt;property name="trace" value="${jdbc.trace}" />
&lt;/bean>
&lt;bean id="datasource" parent="abstractDataSource">
	&lt;property name="driverUrl"
		value="jdbc:mysql://${jdbc.main.database}?useUnicode=true&amp;characterEncoding=utf-8&amp;zeroDateTimeBehavior=convertToNull" />
	&lt;property name="user" value="${jdbc.main.user}" />
	&lt;property name="password" value="${jdbc.main.password}" />
	&lt;property name="alias" value="datasource"/>
&lt;/bean>
</code>
</pre>

######b、database router配置：类似jdbctemplate配置（内部会创建jdbctemplate对象），代替原来jdbctemplate配置，举例说明
原JdbcTemplate配置如下：
<pre>
<code>
&lt;bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	&lt;property name="dataSource">
		&lt;ref bean="dataSource" />
	&lt;/property>
&lt;/bean>
</code>
</pre>

DatabaseRouter配置如下：  
<pre>
<code>
&lt;bean id="user" class="com.sborm.core.DatabaseRouter">
	&lt;property name="servers">
		&lt;list>
			&lt;value>key1,W,datasource&lt;/value>
			&lt;value>key2,R,datasource&lt;/value>
			&lt;value>key3,R,datasource&lt;/value>
		&lt;/list>
	&lt;/property>
	&lt;property name="dbName" value="test">&lt;/property>
&lt;/bean>
</code>
</pre>

**class:**com.sborm.core.DatabaseRouter为数据库路由类，负责数据库信息映射，jdbctemplate实例创建，数据库路由（读写分离）等；  
**servers：**服务器列表，通常会存在主从的架构，通常一台服务器需要配置一个datasource，对应需要配置一个database实例，key1,W,datasource，对应分别是database标识（单个database router内不能重复）、服务器属性（A：读写，W：写，R：读）、datasource引用；  
**dbName：**数据库名称，需要指定，通过Entity实例的注解获取路由；  

以上是所有必须配置，其实和DataSource、JdbcTemplate结合配置类似，只是封装了JdbcTemplate的实例化和自动选择。  
**和JdbcTemplate配置区别：**JdbcTemplate淡化数据库的概念，围绕数据库服务器展开，多个数据库如果在同一台物理服务器上，都可以共用一个实例，但是一个数据库有多个节点需要配置多个template实例（基于不同的datasource），当然也可以保持和datasource 1:1的配置，模糊数据库的概念，在具体查询业务中指定数据库。  
DatabaseRouter围绕数据库展开，可以配置1个或者多个节点，更多是从分布式数据库架构作为设计基础，datasource的配置量还是和物理服务器数量保持一致，但是每个数据库必须配置一个bean，bean可以引用多个节点的datasource，每个节点可以配置读、写属性，自动读写分离将根据该属性进行判断。

###2、数据库路由选择规则
至少要配置一个,只能配置单个写库，可以配置多个读库，具体在DatabaseRouterFactory类中实现;  
提供以下方法：  
```java
/**
 * 获取默认数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
 * default默认取第一个配置
 * @param entity
 * @return
 */
public static JdbcTemplate getDefault(BaseEntity entity)；

/**
 * 获取默认读数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
 * 如果没有配置读库，则去默认
 * @param entity
 * @return
 */
public static JdbcTemplate getReader(BaseEntity entity);

/**
 * 获取默认写数据库节点，如果设置了key则获取指定的节点，可以在entity设置key
 * 如果没有配置写库，则去默认
 * @param entity
 * @return
 */
public static JdbcTemplate getWriter(BaseEntity entity);
```

  
###3、生成Entity结构说明

```java
@Database("test")		// 绑定数据库名为test
@Table("demo")			// 绑定表名或者表前缀为demo
@Component			// 加入这个是为了让spring自动扫描，找不到很好的能那种多种环境的类加载模式，暂时这么搞
public class Demo extends BaseEntity implements Serializable {

	public static final long serialVersionUID = 840187169979836738L;

	public static final class Columns {
		public static final String id = "id";
		public static final String name = "name";
		public static final String password = "password";
		public static final String createTime = "createTime";
		public static final String type = "type";
	}

	private Integer id;
	private String name;
	private String password;
	private Date createTime;
	private Integer type;

	// getter & setter ...
}
```

**@Databse**，生成是指定数据库名称，根据Entity中该信息进行数据库路由，所有查询基于Entity和读写分离规则，不在关系jbdctemplate引用问题；  
**@Table**，映射的表名或者表前缀（规则行分表需要）；
Columns类，解析表所有列，方便基于Entity查询的时候指定查询条件，避免字段信息硬编码(貌似是一大特色，后面无sql查询很多是基于这个)；  

###4、排序说明
排序有OrderBuilder实现，根据添加顺序组装；  
```java
QueryBuilder q = new QueryBuilder(demo);
q.order().add(Demo.Columns.createTime, OrderMode.DESC);			// 指定排序模式
q.order().addASC(Demo.Columns.createTime);				// 升序排序模式
q.order().addDESC(Demo.Columns.createTime);				// 降序排序模式
```

###5、分页处理说明
**调用接口：**public void select(QueryBuilder queryBuilder, PageResult<?> pageResult)
方法实现内部调用PageBuilder组装分页语句，自动根据条件统计总数量；

###6、关于实体类扫描（EntityScanner）
主要功能是初始化Entity的RowMapper，以及table、database映射（貌似也可以使用时候在初始化，没想好，先这么做）；  
需要配置配置context:component-scan，每个Entity都要加入Component注解，主要是通过Spring来做实例化，有点绕；

###7、核心类说明
**DatabaseRouter：**负责初始化数据库实例，创建JdbcTemplate实例，读写分离规则处理等；  
**DatabaseRouterFactory：**databaserouter工具类；  
**EntityContainer：**实体容器，主要是获取RowMapper实例（可以参考第6点说明，通过EntityScanner做RowMapper实例化）；  
**GrammarBuilder：**一系列语法组装器抽象父类;  
**QueryCondition：**查询参数对象，封装常用的查询语法，包括：=, <>, >, >=, <, <=, like, in, not in, between，对应方法处理；  
**QueryBuilder：**查询条件入口类，对外最核心的类，所有基于对象操作数据库的处理都由该类实现,包括ColumnBuilder、GroupBuilder、HavingBuilder、PageBuilder、WhereBuilder组合而成；
**SQL：**负责QueryBuilder到sql的翻译处理；  
**IBaseDao：**dao基础接口，由BaseDao实现，提供基本的CRUD操作，还有一些比较常用的方法、分页方法等；  
**EntityGenerator：**Entity代码生成工具类，可以根据配置文件规则生成（参考第8点）；  

###8、Entity代码生成工具使用说明
一共提供三种代码生成的模式，具体如下：  
```java
/**
 * 生成单个表结构对应的Bean，属性名和字段名保持一致
 * 
 * @param ip 				数据库IP
 * @param port				数据库端口
 * @param database			数据库名
 * @param userName			数据库用户名
 * @param password			数据库密码
 * @param table				表名
 * @param annotationTable 	注解表名，默认和表名一直，但是分表的时候需要填写为前缀
 * @param className			生成类名，为null或空则默认使用表名，首字母大写，
 * 							如果类名包含完整的package路径，则忽略packageName参数
 * @param packageName		包名
 * @param targetDir			目标生成文件目录
 * @param encoding			文件编码
 * @throws Exception
 */
public static void generateTable(String ip, int port, String database,
		String userName, String password, String table, String annotationTable, String className,
		String packageName, String targetDir, String encoding);
		
/**
 * 生成所有表结构对应的Bean，默认采用表名作类名，首字母大写，如果某些表需要指定特定格式类名的，则初始化classNameMapping进行设置
 * 
 * @param ip 					数据库IP
 * @param port					数据库端口
 * @param database				数据库名
 * @param userName				数据库用户名
 * @param password				数据库密码
 * @param includeTables			包含的表名（通常是针对要过滤的表名设置，保存一个接口）
 * @param excludeTables			要过滤的表名（分表，只需要生成一个，包含字符串的模式）
 * @param classNameMapping		类名映射，如果不采用默认类名，需要指定表-类名映射
 * @param packageName			包名
 * @param targetDir				生成目标路劲
 * @param encoding				文件编码
 * @throws Exception
 */
public static void generateAllTable(String ip, int port, String database,
		String userName, String password, Map<String, String> includeTables,
		String[] excludeTables, Map<String, String> classNameMapping,
		String packageName, String targetDir, String encoding);
			
/**
 * 
 * （推荐这种模式）根据配置文件统一生成Entity，对于存在多个数据库的情况还有点麻烦，需要多个配置文件并多次调用。
 * 
 * 文档格式，1列或者2列或者3列，逗号分割，
 * 1列：表名，实体类注解表名采用实际表名，类名采用实际表名（首字母大写）
 * 2列（通常是因为表名不规则）：第一列为表名，第二列为类名（package可写可不写，不写采用默认），这种方式，实体类注解表名采用实际表名
 * 3列（通常是因为分表）：第一列为表名，第二列为实体类注解表名，第三列才类名（package可写可不写，不写采用默认）
 * 
 * demo:
 * 表名,类名
 * table					默认类名（规则是第一个字母大写，这个生成的类名为Table）
 * table_test,TableTest		指定类名（非分表，即注解的table名为table_test,生成类名为TableTest）
 * table_00,table_,Table	分表并指定类名（第一列为随机取一个分表名，第二列为注解table前缀为table_，第三列为该系列分表的统一类名Table）
 * ...
 * 
 * @param ip				数据库IP
 * @param port				数据库端口
 * @param database			数据库名
 * @param userName			数据库用户名
 * @param password			数据库密码
 * @param configFilePath	配置文件路径
 * @param packageName		包名
 * @param targetDir			生产目标文件路径
 * @param encoding			编码
 * @throws Exception
 */
public static void generateByFile(String ip, int port, String database, String userName, String password, 
		String configFilePath, String packageName, String targetDir, String encoding);
```


##五、例子（参考代码中的example包）
```java
package com.sborm.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.sborm.core.dao.BaseDao;
import com.sborm.example.dao.ITestDao;

@Repository
public class TestDao extends BaseDao implements ITestDao {
}
```
**实际上这个类没有任何方法。**

```java
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
			demo.query.selectColumn(Demo.Columns.id, Demo.Columns.name);	// 不指定默认查询全部，每个查询项可以指定别名
			demo.query.whereNameEQ("newname");
			demo.query.whereCreateTimeBETWEEN("2014-07-10 11", "2014-07-19 12");
			demo.query.orderByCreateTimeDESC();
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
			demo.query.whereNameEQ("test");
			demo.query.whereCreateTimeBETWEEN("2014-07-15 11", "2014-07-19 12");
			demo.query.orderByCreateTimeDESC();
			PageResult pr = dao.selectByExample(demo, QueryMode.OR, 1, 1);
			System.out.println(pr.getResultCount() + " - " + pr.getPageCount());
		} catch (Exception e) {
			
		}
	}

	public static void main(String[] args) {
		//testInsert();
		//testUpdate();
		//testDelete();
		testSelect2();
		testSelectPage();
		testSelectPage2();
		System.out.println("finish");
	}
}
```

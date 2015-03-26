package com.sborm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库路由选择器 提供读写服务器选择（多个读机器按随机选择模式，写机器默认一个吧，同一个表分布做不同的机器写怎么办？？？）
 * 按业务自定义选择（根据key选择）
 * 
 * bean xml 配置：
 * 
 * <bean="database1" class=""> <property name="servers"> <list>
 * <value>key1,W,template</value> <value>key2,R,template</value>
 * <value>key3,R,template</value> </list> </property> </bean>
 * 
 * @author fengli
 * @date 2014-7-9 下午3:49:07
 * 
 */
public class DatabaseRouter extends ApplicationObjectSupport implements InitializingBean {

	private String dbName;
	private List<String> servers;
	private JdbcTemplate defaultTemplate;
	private final List<JdbcTemplate> readers = new ArrayList<JdbcTemplate>();
	private final List<JdbcTemplate> writers = new ArrayList<JdbcTemplate>();
	private final Map<String, JdbcTemplate> mapping = new HashMap<String, JdbcTemplate>();
	private int readerCount = 0;
	private int writerCount = 0;
	private final Random random = new Random();

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	/**
	 * 获取默认数据库节点
	 * @return
	 */
	public JdbcTemplate getDefault() {
		return defaultTemplate;
	}

	/**
	 * 获取读数据库节点，存在多个则随机读取，只有一个的话返回默认读节点或默认节点
	 * @return
	 */
	public JdbcTemplate getReader() {
		if (readerCount > 1) {
			int index = random.nextInt(readerCount);
			return readers.get(index);
		} else if (readerCount == 1) {
			return readers.get(0);
		}
		return defaultTemplate;
	}

	/**
	 * 获取写数据库节点，默认返回第一个，如果存在多个写库（这种情况应该比较少），则只能调用getByKey方法
	 * @return
	 */
	public JdbcTemplate getWriter() {
		if (writerCount > 0) {
			return writers.get(0);
		}
		return defaultTemplate;
	}

	/**
	 * 根据配置的key获取数据库节点
	 * @param key
	 * @return
	 */
	public JdbcTemplate getByKey(String key) {
		return mapping.get(key);
	}

	/**
	 * 解析配置信息
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		ApplicationContextHelper.setApplicationContext(getApplicationContext());
		// 执行初始化
		EntityContainer.init();
		if (servers != null && servers.size() > 0) {
			for (String item : servers) {
				String infos[] = item.split(",");
				if (infos != null && infos.length == 3) {
					JdbcTemplate t = new JdbcTemplate();
					t.setDataSource((DataSource)getApplicationContext().getBean(infos[2]));
					mapping.put(infos[0], t);
					if ("R".equals(infos[1])) {
						readers.add(t);
						readerCount++;
					} else if ("W".endsWith(infos[1])) {
						writers.add(t);
						writerCount++;
					} else {
						defaultTemplate = t;
					}
					if (defaultTemplate == null) {// 默认取第一个
						defaultTemplate = t;
					}
				}
			}
		}
		DatabaseRouterFactory.addRouter(this);
	}
}

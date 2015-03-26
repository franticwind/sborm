package com.sborm.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @date 2014-7-15 下午11:02:38
 * @author fengli
 */
public class CommonJdbc {

	/**
	 * 获取数据库连接
	 * 
	 * @param ip
	 * @param port
	 * @param database
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String ip, int port, String database,
			String userName, String password) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + ip
				+ ":" + port + "/" + database, userName, password);
		return conn;
	}

	/**
	 * 获取所有表结构，可以设置多个过滤器
	 * 
	 * @param filters
	 * @return
	 */
	public static Set<String> getAllTables(Connection conn, String[] tables,
			String[] exFilters) throws Exception {
		Set<String> result = new HashSet<String>();
		Map<String, String> buf = new HashMap<String, String>();
		if (tables != null) {
			for (String t : tables) {
				result.add(t);
			}
		}
		String sql = "show tables;";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		while(rs.next()){
			String tableName = rs.getString(1);
			buf.put(tableName, "");
		}
		if (exFilters != null) {
			for (String ex : exFilters) {
				Set<String> keys = buf.keySet();
				List<String> buffer = new ArrayList<String>();
				for (String key : keys) {
					if (key.contains(ex)) {
						buffer.add(key);
					}
				}
				for (String s : buffer) {
					buf.remove(s);
				}
			}
		}
		Set<String> keys = buf.keySet();
		result.addAll(keys);
		return result;
	}

	/**
	 * 获取表字段定义
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, String>> getColumns(Connection conn, String database,
			String tableName) throws SQLException {
		String sql = "SELECT column_name, data_type FROM information_schema.columns " +
				"WHERE table_name='" + tableName + "'" + " and table_schema='" + database + "'";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		while (rs.next()) {
			Map<String, String> buf = new HashMap<String, String>();
			buf.put("c", rs.getString(1));
			buf.put("t", rs.getString(2));
			result.add(buf);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		Connection conn = getConnection("10.10.10.7", 3306, "zhenai_sms", "u_96333", "u_96333");
		Set<String> tbs = getAllTables(conn, null, null);
		for (String t : tbs) {
			System.out.println(t);
			List<Map<String, String>> buf = getColumns(conn, "", t);
			for (Map<String, String> b : buf) {
				System.out.println(b.get("c") + " - " + b.get("t"));
			}
			System.out.println("  ");
		}
		System.out.println();
	}
}

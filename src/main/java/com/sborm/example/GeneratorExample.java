package com.sborm.example;

import java.util.HashMap;
import java.util.Map;

import com.sborm.core.generator.EntityGenerator;

/**
 * 生成代码例子
 * @author fengli
 * @date 2014-7-17 下午3:05:49
 *
 */
public class GeneratorExample {
	
	// 数据库信息
	private final static String ip = "localhost";
	private final static int port = 3306;
	private final static String userName = "root";
	private final static String password = "";
	
	private final static String targetDir = "D:/test/";
	private final static String database = "test";
	
	
	/**
	 * 生成单个表
	 */
	public static void generateOneTable() {
		try {
			// 默认包名
			EntityGenerator.generateTable(ip, port, database, userName, password, 
				"test_00", "test", "Test", "com.sborm.example.bean", targetDir, EntityGenerator.ENCODING_UTF8);
			
			// 自定义包名
			EntityGenerator.generateTable(ip, port, database, userName, password, 
					"test_00","test", "com.sborm.example.bean.Test1", "com.sborm.example.bean", targetDir, EntityGenerator.ENCODING_UTF8);
			
			// 默认类名 
			EntityGenerator.generateTable(ip, port, database, userName, password, 
					"test_00", "test", null, "com.sborm.example.bean", targetDir, EntityGenerator.ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成所有表实体类，默认处理
	 */
	public static void generateAllTable1() {
		try {
			// 默认生成所有的，默认表名
			EntityGenerator.generateAllTable(ip, port, database, userName, password, 
					null, null, null, "com.sborm.example.bean", targetDir, EntityGenerator.ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成所有表实体类，自定义
	 */
	public static void generateAllTable2() {
		try {
			Map<String, String> includeTables = new HashMap<String, String>();
			// 只选择test_00，key为真实表名，value为实体类注解表名前缀
			includeTables.put("test_00", "test");
			// 去掉除了test_00之外的分表
			String[] excludeTables = new String[]{"test"};
			Map<String, String> classNameMapping = new HashMap<String, String>();
			// 类名映射（默认为表名，首字母大写）
			classNameMapping.put("test_00", "Test");
			EntityGenerator.generateAllTable(ip, port, database, userName, password, includeTables, excludeTables, 
					classNameMapping, "com.sborm.example.bean", targetDir, EntityGenerator.ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据配置文件生成
	 */
	public static void genterateByFile() {
		try {
			EntityGenerator.generateByFile(ip, port, database, userName, password, 
					"E:/projects/workspace/com.sborm/src/main/java/com/sborm/example/example.txt",
					"com.sborm.example.bean",
					targetDir, EntityGenerator.ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		genterateByFile();
	}
}

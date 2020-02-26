package com.kiy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.kiy.driver.Log;

/**
 * 服务配置参数
 * 
 * @author HLX Tel:18996470535
 * @date 2018年3月30日 Copyright:Copyright(c) 2018
 */
public class Config {

	public static String CONFIG_FILENAME = "config.properties";

	// 数据库最大连接数
	public static int DB_MAX = 3;
	// 数据库主机
	public static String DB_URL;
	// 数据库用户
	public static String DB_USER;
	// 数据库密码
	public static String DB_PASSWORD;
	
	//
	public static long USER_ID;

	public static String getWorkPath() {
		String path = System.getProperty("user.dir");
		return path;
	}

	public static void load() {
		File file = new File(CONFIG_FILENAME);
		if (!file.exists())
			file = new File(getWorkPath() + File.separatorChar + CONFIG_FILENAME);

		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream(file)) {
			properties.load(input);
		} catch (IOException ex) {
			Log.error(ex);
			throw new RuntimeException(ex);
			// 必须成功加载配置文件，否则无法运行
		}

		DB_MAX = Integer.parseInt(properties.getProperty("DB_MAX"));
		DB_URL = properties.getProperty("DB_URL");
		DB_USER = properties.getProperty("DB_USER");
		DB_PASSWORD = properties.getProperty("DB_PASSWORD");
		USER_ID = Long.valueOf(properties.getProperty("USER_ID"));
	}

	public static void check() {
		// 检查配置文件配置项是否完整

		if (DB_MAX < 1)
			// 数据库连接至少为1
			throw new IllegalArgumentException("Config error DB_MAX");
		if (isEmpty(DB_URL))
			// 数据库连接字符串不能为空
			throw new IllegalArgumentException("Config error DB_URL");

	}


	public static final boolean isEmpty(String s) {
		if (s == null)
			return true;
		if (s.length() > 0)
			return false;
		return true;
	}

	public static boolean getBoolean(String value) {
		if (value == null)
			return false;
		else if ("YES".equalsIgnoreCase(value))
			return true;
		else if ("ON".equalsIgnoreCase(value))
			return true;
		else
			return false;
	}

	public static String toBoolean(boolean value) {
		if (value)
			return "ON";
		else
			return "OFF";
	}
}
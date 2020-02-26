package com.kiy.cloud.data;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kiy.cloud.Config;
import com.kiy.cloud.Log;


/**
 * 数据主控类
 * @author HLX Tel:18996470535 
 * @date 2018年6月6日 
 * Copyright:Copyright(c) 2018
 */
public final class Data {
	
	private static SqlSessionFactory factory;

	private Data() {
	}

	public static SqlSessionFactory getSqlSessionFactory(){
		return factory;
	}

	public static void initialize() {

		Log.info("Data initialize ...");
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(Config.MYBATIS_CONFIG_URL);
			factory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			Log.error(e);
		}
		
		Log.info("Data initialized");
		
	}

	public static void stop() {

		Log.info("Data stop ...");
		factory = null;

		Log.info("Data stopped");
	}

}
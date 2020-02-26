package com.kiy.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kiy.common.Config;
import com.kiy.driver.Log;

/**
 * 数据主类
 * 
 * @author HLX Tel:18996470535
 * @date 2018年3月30日 Copyright:Copyright(c) 2018
 */
public final class Data {


	private Data() {
		// 对象只能有一个实例
	}

	public static void initialize() {
		Log.info("Data initialize ...");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			Log.error(ex);
			throw new RuntimeException("MySql Deiver not found", ex);
		}

		Log.info("Data initialized");
	}

	public static void destroy() {
		Log.info("Data destroy ...");

		Log.info("Data destroyed");
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
		} catch (SQLException e) {
			Log.error(e);
		}
		return null;
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}

	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				Log.error(e);
			}
		}

	}
	
}
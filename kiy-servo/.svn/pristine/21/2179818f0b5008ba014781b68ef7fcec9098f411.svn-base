/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.kiy.servo.Log;

/**
 * 数据库连接池
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class DBConnection {

	public static final String DB_MYSQL = "mysql";
	public static final String DB_ORACLE = "oracle";

	private BlockingQueue<Connection> cache;

	private String url;
	private String user;
	private String password;

	public DBConnection(String type, String url, String user, String password, int max) {
		this.url = url;
		this.user = user;
		this.password = password;

		cache = new ArrayBlockingQueue<Connection>(max);

		if (DB_MYSQL.equalsIgnoreCase(type)) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				Log.error(ex);
				throw new RuntimeException("MySql Deiver not found", ex);
			}
		} else if (DB_ORACLE.equalsIgnoreCase(type)) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException ex) {
				Log.error(ex);
				throw new RuntimeException("Oracle Driver not found", ex);
			}
		} else {
			Log.error("Config error DB_TYPE");
			throw new IllegalArgumentException("Config error DB_TYPE");
		}
	}

	public Connection get() {
		Connection connection = cache.poll();
		if (connection != null) {
			try {
				if (connection.isValid(1/* TIMEOUT 1 SECOND */))
					return connection;

				if (!connection.isClosed())
					connection.close();

			} catch (SQLException ex) {
				Log.error(ex);
			}
			connection = null;
		}

		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			Log.error(ex);
			throw new RuntimeException("Database connect failed", ex);
		}
		return connection;
	}

	public void put(Connection connection) {
		if (connection == null)
			return;

		try {
			if (connection.isClosed())
				return;

			if (!connection.getAutoCommit())
				connection.setAutoCommit(true);

			if (cache.offer(connection))
				return;

			connection.close();

		} catch (SQLException ex) {
			Log.error(ex);
		}
	}

	public void destroy() {
		while (!cache.isEmpty()) {
			Connection connection = cache.poll();
			try {
				connection.close();
			} catch (SQLException ex) {
				Log.error(ex);
			}
		}
		cache = null;
	}
	
	public void closeRS(ResultSet rs){
		if (rs == null) {
			return;
		}
		
		try {
			if (rs.isClosed()) {
				return;
			}
		} catch (SQLException e) {
			Log.error(e);
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			Log.error(e);
			
		}
	}
}
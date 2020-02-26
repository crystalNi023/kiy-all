/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

/**
 * 基于数据库系统的数据提供类
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class DataBank implements DBOperation {

	protected final DBConnection dbc;

	public DataBank(DBConnection c) {
		dbc = c;
	}
}
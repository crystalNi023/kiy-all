/**
 * 2017年8月5日
 */
package com.kiy.cloud.master;

import io.netty.channel.Channel;

/**
 * 为连接提供辅助信息
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Client {

	public static final int NONE = 0;
	public static final int SMART_HOME_SERVO = 1;
	public static final int SMART_HOME_MOBILE=2;
	public static final int SMART_CITY_SERVO=3;
	public static final int SMART_CITY_CONTROLLER = 4;
	public static final int SMART_CITY_MOBILE=5;
	public static final int SMART_CITY_SCREEN=6;
	
	private final Channel channel;
	private boolean check;
	private String company;
	private String servo;
	private String user;
	private String id;
	private int type;
	// 重试次数
	private int retry;

	public Client(Channel c) {
		channel = c;
		type = NONE;
		retry = 0;
		check = false;
		user = id = c.id().asShortText();
	}

	public Channel getChannel() {
		return channel;
	}

	public int getType() {
		return type;
	}

	public void setType(int value) {
		type = value;
	}

	public boolean isServo() {
		if(type==SMART_CITY_SERVO||type==SMART_HOME_SERVO) {
			return true;
		}
		return false;
	}

	public boolean isMobile() {
		if(type==SMART_CITY_MOBILE||type==SMART_HOME_MOBILE) {
			return true;
		}
		return false;
	}

	public boolean isController() {
		return SMART_CITY_CONTROLLER == type;
	}

	public boolean isSmartHome() {
		if(type==SMART_HOME_SERVO||type==SMART_HOME_MOBILE) {
			return true;
		}
		return false;
	}
	
	public boolean isSmartCity() {
		if(type==SMART_CITY_SERVO||type==SMART_CITY_CONTROLLER||type==SMART_CITY_MOBILE||type==SMART_CITY_SCREEN) {
			return true;
		}
		return false;
	}
	
	public String getID() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String value) {
		user = value;
	}

	public String getServo() {
		return servo;
	}

	public void setServo(String value) {
		servo = value;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String value) {
		company = value;
	}

	public int Retry() {
		return retry++;
	}

	public boolean isChecked() {
		return check && user != null && user.length() > 0;
	}

	public void checked() {
		// 已检验
		check = true;
	}

	public void unChecked() {
		// 未检验
		check = false;
	}
}
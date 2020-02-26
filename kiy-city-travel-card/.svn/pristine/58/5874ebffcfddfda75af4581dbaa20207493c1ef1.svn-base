package com.kiy.common;

import com.kiy.common.Types.Power;

public class User extends Unit{
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户权限
	 */
	private Power power;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 联系方式
	 */
	private String phone;
	
	/**
	 * 制卡开始号码
	 */
	private long beginNumber;
	
	/**
	 * 制卡结束号码
	 */
	private long endNumber;
	
	/**
	 * 禁用(true为禁用，false为正常)
	 */
	private boolean enable;
	
	/**
	 * 用户制卡数
	 */
	private int cardsNum;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Power getPower() {
		return power;
	}

	public void setPower(Power power) {
		this.power = power;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getBeginNumber() {
		return beginNumber;
	}

	public void setBeginNumber(long beginNumber) {
		this.beginNumber = beginNumber;
	}

	public long getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(long endNumber) {
		this.endNumber = endNumber;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	

	public int getCardsNum() {
		return cardsNum;
	}

	public void setCardsNum(int cardsNum) {
		this.cardsNum = cardsNum;
	}

	/**
	 * 检查制卡权限
	 * @param number
	 * @return
	 */
	public boolean  checkWritePower(long number){
		if(number>=beginNumber&&number<=endNumber){
			return true;
		}
		return false;
	}
}

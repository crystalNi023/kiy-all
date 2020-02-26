package com.kiy.driver;

import java.util.Date;

/**
 * 
 * @author HLX 
 * Date: 2018年3月5日
 * Card 实体类
 */
public class MCard {
	/**
	 * 记次卡：1
	 */
	public static final int NUMBER_TYPE = 1;
	
	/**
	 * 计时卡：2
	 */
	public static final int TIME_TYPE = 2;
	
	/**
	 * 卡类型:(0-计次卡/1-计时卡)
	 */
	private int type;
	
	/**
	 * 卡号
	 */
	private String cardNumber;
	
	/**
	 * 次数(计次卡使用)最大65535
	 */
	private int number;
	
	/**
	 * 有效时间小时(计时卡使用)
	 */
	private int hour;
	
	/**
	 * 第一次刷卡时间(计时卡使用)
	 */
	private Date date;
	/**
	 * remark
	 */
	private String remark;
	
	
	/**
	 * 获取类型
	 * @return 卡的类型(0-计次卡/1-计时卡)
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 设置类型
	 * @param type 卡的类型(0-计次卡/1-计时卡)
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 获取卡号 8位
	 * @return
	 */
	public String getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * 设置卡号 8位 (00000001 - 99999999)
	 * @param cardNumber 卡号
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	/**
	 * 获取计次卡次数
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 *	设置次数
	 * @param number 次数
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * 获取有效时间
	 * @return 有效时间
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * 设置有效时间
	 * @param hour 有效时间
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * 获取计时卡第一次刷卡时间
	 * @return 计时卡第一次刷卡时间
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * 设置计时卡第一次刷卡时间
	 * @param date 计时卡第一次刷卡时间
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}

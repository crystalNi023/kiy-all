package com.kiy.common;

public class GetCard extends Unit{
	/**
	 * 领卡用户
	 */
	private long getUserId;
	
	/**
	 * 发卡用户
	 */
	private long giveUserId;
	
	/**
	 * 开始号段
	 */
	private int begin;
	
	/**
	 * 结束号段
	 */
	private int end;
	
	/**
	 * 领卡数量
	 */
	private int count;
	
	/**
	 * 给卡用户真实姓名
	 */
	private String giveUserRealname;
	
	/**
	 * 领卡用户真实姓名
	 */
	private String getUserRealname;

	public long getGetUserId() {
		return getUserId;
	}

	public void setGetUserId(long getUserId) {
		this.getUserId = getUserId;
	}

	public long getGiveUserId() {
		return giveUserId;
	}

	public void setGiveUserId(long giveUserId) {
		this.giveUserId = giveUserId;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getGiveUserRealname() {
		return giveUserRealname;
	}

	public void setGiveUserRealname(String giveUserRealname) {
		this.giveUserRealname = giveUserRealname;
	}

	public String getGetUserRealname() {
		return getUserRealname;
	}

	public void setGetUserRealname(String getUserRealname) {
		this.getUserRealname = getUserRealname;
	}
	
	
	
}

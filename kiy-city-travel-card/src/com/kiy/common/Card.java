package com.kiy.common;

import com.kiy.common.Types.Kind;

public class Card extends Unit{
	/**
	 * 制卡用户
	 */
	private long user_id;
	
	/**
	 * 卡号
	 */
	private long number;
	
	/**
	 * 卡类型
	 */
	private Kind kind;

	public long getUserId() {
		return user_id;
	}

	public void setUserId(long user_id) {
		this.user_id = user_id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}
	
	
}

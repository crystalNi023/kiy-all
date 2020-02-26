package com.dec.pro.entity.test;

import java.io.Serializable;

import com.dec.pro.entity.BaseEntity;

public class TestEntity extends BaseEntity implements Serializable{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2262220337156795589L;
	private String name;//名称
	private int type;//类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}

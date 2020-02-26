package com.kiy.common;

import java.util.Date;


/**
 * 实体类基类
 * @author HLX Tel:18996470535 
 * @date 2018年4月2日 
 * Copyright:Copyright(c) 2018
 */
public abstract class Unit {

	/**
	 * ID相同即为相等
	 */
	public boolean equals(Object e) {
		if (e == null)
			return false;
		
		if(this.hashCode()==e.hashCode()) 
			return true;
		
		if (id == 0)
			return false;

		if (this == e)
			return true;

		if (e instanceof Unit) {
			Unit u = (Unit) e;
			if (u.id == 0)
				return false;
			return id == u.id;
		}
		return false;
	}

	/**
	 * 唯一标识
	 */
	protected long id;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 最后更新时间
	 */
	private Date updated;

	/**
	 * 获取实体对象唯一标识
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * 设置实体对象唯一标识
	 * 
	 * @param value
	 *            the id to set
	 */
	public void setId(Long value) {
		id = value;
	}


	/**
	 * 获取实体对象备注
	 * 
	 * @return the remark
	 */
	public String getRemark() {
		if (remark == null)
			return Util.EMPTY;
		return remark;
	}

	/**
	 * 设置实体对象备注
	 * 
	 * @param value
	 *            the remark to set
	 */
	public void setRemark(String value) {
		remark = value;
	}

	/**
	 * 获取实体对象创建时间
	 * 
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * 设置实体对象创建时间
	 * 
	 * @param value
	 *            the created to set
	 */
	public void setCreated(Date value) {
		created = value;
	}

	/**
	 * 获取实体对象最后更改时间
	 * 
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * 设置实体对象最后更改时间
	 * 
	 * @param value
	 *            the updated to set
	 */
	public void setUpdated(Date value) {
		updated = value;
	}
}
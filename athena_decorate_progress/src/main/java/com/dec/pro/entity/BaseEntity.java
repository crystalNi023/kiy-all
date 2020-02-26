package com.dec.pro.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 753040273328803432L;
	protected String id;//编号--统一32位uuid
	protected String remark;// 备注
	protected Date created;// 创建时间
	protected Date updated;// 更新时间
	/**
	 * 分页查询时用
	 */
	protected int pageNo = 1; // 从第几页开始查询
	protected int pageSize = 10; // 每页条数
	
	
}

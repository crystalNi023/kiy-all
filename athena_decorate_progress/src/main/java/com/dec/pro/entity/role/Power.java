package com.dec.pro.entity.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Power extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5939237835181003125L;
	private String url;// 具体操作路径
	private String name;// 名称
}

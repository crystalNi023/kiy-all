package com.dec.pro.entity.project;

import com.dec.pro.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ImageFlow extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4549130691744592645L;
	private String cusId;//客户id
	private double flowLimit;//流量上限（M）--保留1位小数
    private double flowUsed;//流量已用
    private double flowThisTime;//本次上传
}

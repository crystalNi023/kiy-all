package com.dec.pro.entity.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 流程进度
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Progress extends BaseEntity{/**
	 * 
	 */
	private static final long serialVersionUID = 7915657640866398932L;
	private String procId;//流程id
	private int schedule;//进度（%）0~100
	private int type;//类型 1.进度提示 2.整改/延期申请 3.验收申请
	private ProgressRecord progressRecord;//进度记录
}

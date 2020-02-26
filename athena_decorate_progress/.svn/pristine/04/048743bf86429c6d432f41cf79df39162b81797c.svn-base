package com.dec.pro.entity.project;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 进度记录
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ProgressRecord extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3627493129130548891L;
	private String progId;//进度id 
	private String promptMsg;//提示信息
	private Date promptTime;//提示时间
	private int promptType;//提示类型（1装修公司,2客户,3系统管理员,4工程部经理,5装修设计师,6项目经理,7现场负责人）
	private String replyContent;//客户回复内容
	private Date replyTime;//回复时间
	private int replyType;//回复类型 1.手动  2.自动（app设置）自动：客户已授权工程进度审核默认为通过，重要变更请直接联系客户
	private int status;//审核状态 1.审核中 2.已通过  3未通过 
	private int timeAssess;//动态时间评估      +延期 -提前
	private Date assessDate;//计划延期/提前完成时间
}

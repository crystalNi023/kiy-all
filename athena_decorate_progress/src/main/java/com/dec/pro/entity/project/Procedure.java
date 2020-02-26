package com.dec.pro.entity.project;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import com.dec.pro.entity.BaseEntity;
/**
 * 项目流程
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Procedure extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4422908165006189431L;
	private String proId;//项目id
    private String name;//流程名称
    private int process ;//流程进度（%） 0~100（默认0）
    private int accept;//验收  1.待验收 2.通过 3.不通过（默认1）
    private int status;//状态   1.未启动  2.装修中 3.已完成（默认1）
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date procStartTime;//计划启动时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date procEndTime;//计划结束时间
    private int timeAssess;//动态时间评估      +延期 -提前
    private int notice;//通知 1.新消息 2.已读消息 
    private String refuseMsg;//不通过的原因
    
	private List<Progress> progressList;//进度流程
	private List<ChangeRecord> changeRecordList;//需求变更
}

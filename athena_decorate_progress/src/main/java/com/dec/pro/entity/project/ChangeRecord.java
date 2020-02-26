package com.dec.pro.entity.project;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 需求变更记录
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ChangeRecord extends BaseEntity{/**
	 * 
	 */
	private static final long serialVersionUID = 1513379592301974500L;
	private String procId;//流程id
	private String changeContent;//客户需求变更
	private Date changeTime;//变更时间
	private String changeReply;//装修回复内容
	private Date replyTime;//回复时间
	private int replyType;//回复类型 （1装修公司,2客户,3系统管理员,4工程部经理,5装修设计师,6项目经理,7现场负责人）
    private int notice;//通知 1.新消息 2.已读消息 
	
	private List<String> changeRecordUrls;//需求变更图片
	
}

package com.dec.pro.entity.company;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import com.dec.pro.entity.BaseEntity;
/**
 * 打卡信息
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class PunchRecord extends BaseEntity{
	

	private static final long serialVersionUID = 8536492410652485263L;
	private String  constructorId	         ;//施工员id
	private String  workToday        ;//今日工作
	private String  declarationPlace ;//上班申报打卡地点
	private String  realPlace 		 ;//上班实际打卡地点
	private String  offDeclarationPlace ;//下班申报打卡地点
	private String  offRealPlace 		;//下班实际打卡地点
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date    workingTime      ;//上班时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date    offWorkTime      ;//下班时间
	
	//返回字段
	private String constructorName;//施工员姓名
	private String constructorPhone;//施工员电话号码
	private int status;//1:已打卡 0：未打卡
	private String title;//标题（打卡或未打卡）
	private String content;//内容（打卡或未打卡人数）
	
//	//返回字段
//	private int flag;//标记   1.工作类型 2.打卡状态
//	private String  name            ;//姓名
//	private String  headPortrait    ;//头像
//	private int  count    ;//统计数量（工作类型，打卡状态）
} 

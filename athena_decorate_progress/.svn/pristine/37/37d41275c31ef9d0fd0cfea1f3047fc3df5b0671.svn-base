package com.dec.pro.entity.sms;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 短信实体类
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SMS extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2909528873363256893L;
	private String cId;//短信客户id
	private String name;//姓名
	private String phone;//电话
	private String idCard;//身份证
	private String content;//短信内容
	private int status;//1：发送成功 2：发送失败
	private String msg;//返回消息
	private String smsId;//短信平台返回id
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date repayDate;//还款日期
	private int stage;//分期期数
	private String used;//用途
	private String templateId;//模板id
	private String sex;//性别
	private int    age;//年龄
	private String explain;//通话后备注
	private int  isContect;//状态  1.未通话 2.已通话

}

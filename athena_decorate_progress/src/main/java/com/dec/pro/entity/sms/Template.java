package com.dec.pro.entity.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 短信模板
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Template extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1731365290479481824L;
	
	private String templateId;//模板id
	private String templateName;//模板名称
	private String autograph;//短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字
	private String content;//模板内容
	private int type;//短信类型 0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})
}

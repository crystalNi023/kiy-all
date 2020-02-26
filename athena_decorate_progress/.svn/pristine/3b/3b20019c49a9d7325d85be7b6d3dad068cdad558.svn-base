package com.dec.pro.entity.user;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
import com.dec.pro.entity.role.Role;
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class User extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8834721756799349600L;
	private String username;//用户名
	private String password;//密码
	private boolean enable=true;//1启用  0禁用
	private Set<Role> roles;//拥有的角色
	private int type;//类型（1装修公司,2客户,3系统管理员,4工程部经理,5装修设计师,6项目经理,7现场负责人,8施工员）（默认2）
	private String comId;//公司id --公司员工账号
	private String code;//短信验证码
	//消息推送需要(设备登录时传)
	private String deviceTokens;//设备token
	private int deviceType;//设备类型 1：android 2：ios
}

package com.dec.pro.entity.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 装修团队信息
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class DecorationTeam extends BaseEntity{

	private static final long serialVersionUID = -8622330100742811613L;
	private String comId;//公司id
	private String userId;//用户id
	private String name;//姓名
	private String phone;//电话
	private int module;//负责模块	1工程部经理 2装修设计师 3项目经理 4现场负责人 5施工员
	private String address;//联系地址
}

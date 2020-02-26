package com.dec.pro.entity.customer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 客户信息
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Customer extends BaseEntity{/**
	 * 
	 */
	private static final long serialVersionUID = -8078077941586675561L;
    private String name;//客户姓名
    private String mobile;//第二联系方式电话
    private String phone;//移动电话（首选）
    private String email;//电子邮箱
    private String address;//工程地址
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date signTime;//合同签订时间
    private String userId;//客户绑定用户
    //返回字段
    private String proId;//项目id
    private int process;//项目进度（总进度）
    //用于条件查询
    private String comId;//公司id
}

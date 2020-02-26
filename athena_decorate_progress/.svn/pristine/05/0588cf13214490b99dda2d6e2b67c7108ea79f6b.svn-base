package com.dec.pro.util.Exception;
/**
 * 自定义一个父类异常
 * @author Administrator
 *
 */
public class UserException extends RuntimeException {
	/*
	 * 1000  成功
	 * 1001  用户不存在，请先注册！
	 * 1002  用户名错误！ 
	 * 1003  密码错误！
	 * 1004  用户名和密码为空
	 * 1005  用户名为空
	 * 1006  密码为空
	 * 1007  验证码错误
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public UserException(String code,String msg){
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
		
}

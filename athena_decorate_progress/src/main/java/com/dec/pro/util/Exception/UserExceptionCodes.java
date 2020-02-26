package com.dec.pro.util.Exception;
/**
 * 自定义枚举
 * 枚举错误码
 * @author Administrator
 *
 */
public enum UserExceptionCodes {
	USER_NOT_EXIST("1001","User not exist"),
	USER_NAME_ERROR("1002","Username error"),
	USER_PASSWORD_ERROR("1003","Userpassword error"),
	USER_NAME_PASSWORD_IS_NULL("1004","Username and password is null"),
	USER_NAME_IS_NULL("1005","Username is null"),
	USER_PASSWORD_IS_NULL("1006","Userpassword is null"),
	VERIFICATION_CODE_ERROR("1007","Verification code error"),
	REGISTER_FAILED("1008","Register failed"),
	USER_NAME_REGISTERED("1009","Username has been registered"),
	SEND_SMSVERIFICATION_CODE_FAILED("1010","Send SMS verification code failed");
	
	private String value;
	private String desc;
	
	private UserExceptionCodes(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
		
}

package com.dec.pro.util.Exception;
/**
 * 异常：用户密码错误 1003
 * @author Administrator
 *
 */
public class UserPasswordErrorExption extends UserException {

	private static final long serialVersionUID = 1L;
	
	public UserPasswordErrorExption(String msg) {
		super(UserExceptionCodes.USER_PASSWORD_ERROR.getValue(), msg);
	}

}

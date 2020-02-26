package com.dec.pro.util.Exception;
/**
 * 异常：该用户名已被注册 1009
 * @author Administrator
 *
 */
public class UserNameRegisteredException extends UserException {

	private static final long serialVersionUID = 1L;
	
	public UserNameRegisteredException(String msg) {
		super(UserExceptionCodes.USER_NAME_REGISTERED.getValue(), msg);
	}
	

}

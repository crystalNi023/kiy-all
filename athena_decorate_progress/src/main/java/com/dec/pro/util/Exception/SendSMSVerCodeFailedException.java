package com.dec.pro.util.Exception;
/**
 * 异常：发送短信验证码失败 1010
 * @author Administrator
 *
 */
public class SendSMSVerCodeFailedException extends UserException {

	private static final long serialVersionUID = 1L;
	
	public SendSMSVerCodeFailedException(String msg) {
		super(UserExceptionCodes.SEND_SMSVERIFICATION_CODE_FAILED.getValue(), msg);
	}

}

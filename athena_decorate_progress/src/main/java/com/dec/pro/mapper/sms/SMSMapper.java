package com.dec.pro.mapper.sms;

import com.dec.pro.entity.sms.SMS;
import com.dec.pro.mapper.BaseMapper;

public interface SMSMapper extends BaseMapper<SMS>{
	/**
	 * 插入客户实体
	 * 
	 * @param t
	 * @return
	 */
	public int addClient(SMS sms);
}

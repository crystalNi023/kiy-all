package com.dec.pro.service.push.ios;

import com.dec.pro.service.push.IOSNotification;

public class IOSListcast extends IOSNotification {
	public IOSListcast(String appkey,String appMasterSecret) throws Exception{
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "listcast");	
	}
	
	public void setDeviceToken(String tokens) throws Exception {
    	setPredefinedKeyValue("device_tokens", tokens);
    }
}

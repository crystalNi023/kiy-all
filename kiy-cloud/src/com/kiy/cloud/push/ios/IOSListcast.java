package com.kiy.cloud.push.ios;

import com.kiy.cloud.push.IOSNotification;


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

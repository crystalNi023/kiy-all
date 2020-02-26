package com.dec.pro.service.push.android;

import com.dec.pro.service.push.AndroidNotification;

public class AndroidListcast extends AndroidNotification {
	public AndroidListcast(String appkey,String appMasterSecret) throws Exception {
			setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "listcast");	
	}
	
	public void setDeviceToken(String tokens) throws Exception {
    	setPredefinedKeyValue("device_tokens", tokens);
    }

}
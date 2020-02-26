package com.kiy.cloud.push;

import com.kiy.cloud.push.android.AndroidUnicast;
import com.kiy.cloud.push.ios.IOSUnicast;



public class PushUtil {
	private static final String ios_appkey = "5c850cbf61f564c89a000c27";
	private static final String ios_appMasterSecret = "jmh3t5abdfvwfwjiqslcydhm4qfz5in5";
	private static final String android_appkey = "5c9c3eab203657a6dd000fec";
	private static final String android_appMasterSecret = "pyvuvzkja30zanvq2mxk8qwnww4oib41";
	//private String timestamp = null;
	private static PushClient client = new PushClient();

	public static void main(String[] args) {
		try {
			sendAndroidUnicast("AoES5_QA-MmOVxQibZqka9vplWT1ZVLESswqX6m49y7_","烟雾1发出报警消息");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean sendAndroidUnicast(String tokens,String alert ) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(android_appkey,android_appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(tokens);
		unicast.setTicker( "Athena C&C 提示信息");
		unicast.setTitle(  "Athena C&C");
		unicast.setText(alert);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		return client.send(unicast);
	}
	
	
	public static boolean sendAndroidLisatcast() throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(android_appkey,android_appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( "your device token");
		unicast.setTicker( "Android unicast ticker");
		unicast.setTitle("中文的title");
		unicast.setText("Android unicast text");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		return client.send(unicast);
	}

	public static void sendIOSLisatcast(String tokens,String alert) throws Exception {
		IOSUnicast unicast = new IOSUnicast(ios_appkey,ios_appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(tokens);
		unicast.setAlert(alert);
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		//unicast.setTestMode();
		unicast.setProductionMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	public static boolean sendIOSUnicast(String tokens,String alert) throws Exception {
		IOSUnicast unicast = new IOSUnicast(ios_appkey,ios_appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(tokens);
	//	 alert="{\"title\":\"title\",\"subtitle\":\"subtitle\",\"body\":\"body\"}";
		unicast.setAlert(alert);
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		//unicast.setTestMode();
		unicast.setProductionMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		return client.send(unicast);
	}
	
	
}

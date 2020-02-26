package com.kiy.cloud.tool;

import java.util.UUID;

/**
 * 
 * @author Administrator
 *
 */
public class OauthServiceTool {
	
	/**
	 * 获取Oauth服务Code
	 */
	public static String getOauthCode(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}

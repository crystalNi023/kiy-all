package com.dec.pro.util;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;



/**
 * 手机短信通知
 * @author Administrator
 *
 */
public final class SMSUtil {
	private static Logger logger=Logger.getLogger(SMSUtil.class);
	/**
	 * 接口访问URL
	 */
	public static final  String SEND_BATCH_URL = "https://open.ucpaas.com/ol/sms/sendsms_batch";
	/**
	 * 指定模板单发
	 */
	public static final  String SEND_ONE_URL="https://open.ucpaas.com/ol/sms/sendsms";
	/**
	 * 接口访问URL
	 */
	public static final  String ADD_TEMPLATE_URL = "https://open.ucpaas.com/ol/sms/addsmstemplate";
	/**
	 * 用户的账号唯一标识“Account Sid”
	 */
	public static final  String SID = "5664da464476fb959b02886eb486fc0f";
	/**
	 * 用户密钥“Auth Token”
	 */
	public static final  String TOKEN = "eb28748b5e63e8aaa2867743b341e9fd";
	/**
	 * 创建应用时系统分配的唯一标示(appId)
	 */
	public static final  String APPID = "adbe8f1b8abf4ae7a0382cb68c41c5f5";
	/**
	 * 短信验证模板号
	 */
	public static final  String DECORATE_ID_VERIFY="419688";//装修软件验证码
	/**
	 * 短信通知模板
	 */
	public static final  String DECORATE_ACCOUNT_NUMBER = "423078";//装修软件账号和密码
	/**
	 * 客户验证码存储
	 */
	public static ConcurrentHashMap<String, VerificationCode> codes = new ConcurrentHashMap<String, VerificationCode>();

	public static void main(String[] args) throws Exception {
		sendSMSVerify("13167877180");
	}
	/**
	 * 给手机号发送验证码
	 * @param mobbile
	 */
	public static boolean sendSMSVerify(final String mobile) throws Exception{
		String code=getRandomString();//生成验证码
		String result;
		try {
			result = sendSmsOne(DECORATE_ID_VERIFY,code,mobile,UUid.getUUid());
		} catch (Exception e) {
			throw new RuntimeException("验证码发送失败");
		}
		
		if(isOk(result,mobile)){//短信发送成功后存储起
			codes.put(mobile, new VerificationCode(code));
			return true;
		}else{
			return false;
		}
			
	}
	/**
	 * 检验用户输入的验证码       1内分钟有效
	 * @param mobile
	 * @param code
	 * @return
	 */
	public static boolean checkVerify(String mobile,String code){
		if(mobile==null || code==null){
			return false;
		}
		VerificationCode verificationCode = codes.get(mobile);
		System.err.println(verificationCode+"verificationCode");
		if(verificationCode==null){
			return false;
		}else{
			//验证码10分钟失效  (注册+密码重置)
			if(System.currentTimeMillis()-verificationCode.getSendTime()>600000){
				codes.remove(mobile);
				return false;
			}
			if (Objects.equals(code, verificationCode.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param sid 用户的账号唯一标识“Account Sid”，在开发者控制台获取
	 * @param token 用户密钥“Auth Token”，在开发者控制台获取
	 * @param appid创建应用时系统分配的唯一标示（位置）
	 * @param templateid -模板ID
	 * @param param 模板中的替换参数， 多个以英文逗号分隔（如：“a,b,c”）数中不能含有特殊符号“【】”和“,”
	 * @param mobile 接收的手机号，多个手机号码以英文逗号分隔 （如：“18011984299,18011801180”），最多单次支持100个号码，如果号码重复，则只发送一条，暂仅支持国内号码
	 * @param uid 用户透传ID，随状态报告返回
	 * @return
	 */
	public static String sendSmsBatch(String sid, String token, String appid, String templateId, String param, String mobile, String uid){
		String result="";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateId);
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid", uid);
			logger.info("短信接口请求body = " + jsonObject.toJSONString());
			result = HttpClientUtil.postJson(SEND_BATCH_URL, jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("短信接口返回result = "+result);
		return result;
	}
	/**
	 * 
	 * @param sid
	 * @param token
	 * @param appid
	 * @param type  短信类型：0:通知短信、5:会员服务短信（需企业认证）、4:验证码短信(此类型content内必须至少有一个参数{1})
	 * @param templateName 短信模板名称，限6个汉字或20个数字、英文字、符号
	 * @param autograph 短信签名，建议使用公司名/APP名/网站名，限2-12个汉字、英文字母和数字，不能纯数字
	 * @param content 短信内容，最长500字，不得含有【】符号，可支持输入参数，参数示例“{1}”、“{2}”
	 * @return
	 */
	public static String addTemplate(String sid, String token, String appid, int type, String templateName, String autograph, String content){
		String result=null;
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sid", sid);
		jsonObject.put("token", token);
		jsonObject.put("appid", appid);
		jsonObject.put("type", type);
		jsonObject.put("autograph", autograph);
		jsonObject.put("template_name", templateName);
		jsonObject.put("content", content);
		logger.info("添加短信模板 = " + jsonObject.toJSONString());
		result = HttpClientUtil.postJson(ADD_TEMPLATE_URL, jsonObject);
		logger.info("添加短信模板接口返回result = "+result);
		return result;
	}
	/**
	 * 
	 * @param sid 用户的账号唯一标识“Account Sid”，在开发者控制台获取
	 * @param token 用户密钥“Auth Token”，在开发者控制台获取
	 * @param appid创建应用时系统分配的唯一标示（位置）
	 * @param templateid -模板ID
	 * @param param 模板中的替换参数， 多个以英文逗号分隔（如：“a,b,c”）数中不能含有特殊符号“【】”和“,”
	 * @param mobile 接收的手机号，多个手机号码以英文逗号分隔 （如：“18011984299,18011801180”），最多单次支持100个号码，如果号码重复，则只发送一条，暂仅支持国内号码
	 * @param uid 用户透传ID，随状态报告返回
	 * @return
	 */
	public static String sendSmsOne(String sid, String token, String appid, String templateId, String param, String mobile, String uid) throws Exception{
		String result="";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sid", sid);
			jsonObject.put("token", token);
			jsonObject.put("appid", appid);
			jsonObject.put("templateid", templateId);
			jsonObject.put("param", param);
			jsonObject.put("mobile", mobile);
			jsonObject.put("uid", uid);
			logger.info("短信接口请求body = " + jsonObject.toJSONString());
			result = HttpClientUtil.postJson(SEND_ONE_URL, jsonObject);
		} catch (Exception e) {
			logger.error("短信接口错误消息："+e.getMessage());
			throw new RuntimeException("短信接口请求失败！");
		}
		logger.info("短信接口返回result = "+result);
		return result;
	}
	/**
	 * 
	 * @param type
	 * @param templateName
	 * @param autograph
	 * @param content
	 * @return
	 */
	public static String addTemplate(int type, String templateName, String autograph, String content){
		return addTemplate(SID,TOKEN,APPID,type,templateName,autograph,content);
	}
	/**
	 * 
	 * @param param
	 * @param mobile
	 * @param uid
	 * @return
	 */
	public static String sendSmsBatch(String TemplateId, String param, String mobile, String uid){
		return sendSmsBatch(SID,TOKEN,APPID,TemplateId,param,mobile,uid);
	}
	/**
	 * 
	 * @param param
	 * @param mobile
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	public static String sendSmsOne(String templateId,String param, String mobile, String uid) throws Exception{
		System.err.println("发送短信成功");
		return sendSmsOne(SID,TOKEN,APPID,templateId,param,mobile,uid);
	}
	
	public static final char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	public static Random random = new Random();
	/**
	 * 获取随机验证码
	 * @return
	 */
	public static String getRandomString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= 5; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}
	/**
	 * 验证码实体
	 * @author Administrator
	 *
	 */
	public static class VerificationCode {

		public VerificationCode(String code) {
			super();
			this.code = code;
			this.sendTime = System.currentTimeMillis();
		}

		private String code;

		private long sendTime;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public long getSendTime() {
			return sendTime;
		}

		public void setSendTime(long sendTime) {
			this.sendTime = sendTime;
		}

	}
	
	/**
	 * 验证短信是否发送成功
	 * @param result
	 * @return
	 */
	public static boolean isOk(String result,String mobbile){
		//result={"code":"000000","count":"1","create_date":"2019-01-08 14:02:47","mobile":"13167877180","msg":"OK","smsid":"66cf7c950474ffc429ef926eec967794","uid":"419e6a7a3fa5422294f758c82a75e7ad"}
		if(result==null)
			return false;
		JSONObject js = (JSONObject) JSONObject.parse(result);
		if("000000".equals(js.get("code"))){
			logger.info("号码为："+mobbile+" 的验证码发送成功！");
			return true;
		}else{
			logger.error("号码为："+mobbile+" 的验证码发送失败！");
			logger.error("错误码："+js.getString("code")+"    错误消息为："+js.getString("msg"));
			return false;
		}
	}
}

package com.kiy.cloud.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;






import com.google.gson.Gson;
import com.kiy.cloud.Config;
import com.kiy.cloud.Executor;
import com.kiy.cloud.Log;
import com.kiy.common.Tool;

/**
 * 手机短信通知
 * 
 * @author HLX Tel:18996470535
 * @date 2018年4月24日 Copyright:Copyright(c) 2018
 */
public final class SMS {
	/**
	 * 接口访问URL
	 */
	public static final  String URL = "https://open.ucpaas.com/ol/sms/sendsms_batch";
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
	 * 报警通知模板号
	 */
	public static final  String TEMPLATE_ID = "314479";
	/**
	 * 短信验证模板号
	 */
	public static final  String TEMPLATE_ID_VERIFY = "318053";

	public static ConcurrentHashMap<String, VerificationCode> codes = new ConcurrentHashMap<String, VerificationCode>();

	public static void main(String[] args) {
		Executor.initialize();
		Config.load();
		String sendSMSVerify = SMS.sendSMSVerify("15320831347");
		System.err.println(sendSMSVerify);
	}

	/**
	 * 验证用户输入验证码是否正确
	 * 
	 * @param phone 手机号
	 * @param code 验证码
	 * @return
	 */
	public static boolean ensureVerificationCode(String phone, String code) {
		if (Tool.isEmpty(phone)) {
			return false;
		}

		if (Tool.isEmpty(code)) {
			return false;
		}

		VerificationCode verificationCode = codes.get(phone);
		if (verificationCode == null) {
			return false;
		} else {
			// 判断验证码是否失效,10分钟过期
			if (System.currentTimeMillis() - verificationCode.getSendTime() >= 600000) {
				codes.remove(phone);
				return false;
			}
			if (Objects.equals(code, verificationCode.getCode())) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 如果有错误，返回错误消息
	 * @param mobile
	 * @return
	 */
	public static String sendSMSVerify(final String mobile) {
		String error = "";
		final String randomString = getRandomString();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("sid", SID);
			map.put("token", TOKEN);
			map.put("appid", APPID);
			map.put("param", randomString);
			map.put("mobile", mobile);
			map.put("templateid", TEMPLATE_ID_VERIFY);
			String result = jsonPost(URL, map);
			codes.put(mobile, new VerificationCode(randomString));
			if (result == null)
				return null;
			result = result.replace("result:", "");
			error = (String) new JSONObject(result).getJSONArray("report").getJSONObject(0).get("msg");
		} catch (Exception e) {
			Log.error("短信接口错误消息：" + e.getMessage());
		}
		if (error.equalsIgnoreCase("ok")) {
			error = null;//成功返回错误消息为空
		}
		return error;
	}

	/**
	 * 发送报警通知短信
	 * 
	 * @param params 短信模板中的动态参数 例如尊敬的用户，您的家里检测到{1}，请及时检查并处理。
	 * @param mobile 手机号 多个一逗号连接 例如 "18888888888,17777777777"
	 */
	public static void sendSMSAlarm(final String params, final String mobile) {
		Executor.getEventLoopGroup().execute(new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<>();
				map.put("sid", SID);
				map.put("token", TOKEN);
				map.put("appid", APPID);
				map.put("param", params);
				map.put("mobile", mobile);
				map.put("templateid", TEMPLATE_ID);
				String jsonPost = jsonPost(URL, map);
				if (Config.DEBUG)
					Log.debug(jsonPost);
			}
		});
	}

	/**
	 * 发送HttpPost请求
	 * 
	 * @param strURL 服务地址
	 * @param params
	 * 
	 * @return 成功:返回json字符串
	 */
	private static String jsonPost(String strURL, Map<String, String> params) {
		OutputStreamWriter out = null;
		InputStream is = null;
        BufferedReader br = null;
        String result = null;
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

			Gson gson = new Gson();

			out.append(gson.toJson(params));

			out.flush();
			out.close();

			int code = connection.getResponseCode();
			
			if (code == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                   // sbf.append("\r\n");
                }
                result = sbf.toString();
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}

			// 读取响应
			int length =  connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				return new String(data, "UTF-8"); // utf-8编码;
			}
		} catch (IOException e) {
			Log.error("Exception occur when send http post request!");
		} finally {
			if (out != null) {
				try {
					// 报java.io.IOException: Stream closed异常
					// out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
		return result; // 错误信息
	}

	public static final char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	public static Random random = new Random();

	public static String getRandomString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= 5; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}

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
}

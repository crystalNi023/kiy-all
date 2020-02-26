package com.dec.pro.interfaces.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.action.user.UserController;
import com.dec.pro.entity.push.Push;
import com.dec.pro.entity.user.User;
import com.dec.pro.filter.UserRealm;
import com.dec.pro.service.push.PushService;
import com.dec.pro.service.user.UserService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.SMSUtil;
import com.dec.pro.util.UUid;

@Controller(value="用户")
public class UserInterface {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRealm userRealm;
	@Autowired
	private PushService pushService;
	private Logger logger=Logger.getLogger(UserController.class);
	private String code;
	private String msg;
	
	@RequestMapping(value = "app/user/login", name = "用户登录", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> login(User user){
		Map<String, Object> map=new HashMap<>();
		String deviceTokens = user.getDeviceTokens();
		int deviceType = user.getDeviceType();
		logger.info("登录请求！");
		try {
			GetField.getOTM(" APP用户登录:",user);
			user.setType(2);//标识为客户登陆
			User u = userService.loginAPP(user);
			Push push=pushService.getPushByUserId(u.getId());
			//登录成功后添加设备token
			if(push!=null){//存在则更新
				push.setDeviceTokens(deviceTokens);
				push.setDeviceType(deviceType);
				push.setUserId(u.getId());
				pushService.update(push);
			}else{
				push=new Push();
				push.setId(UUid.getUUid());
				push.setDeviceTokens(deviceTokens);
				push.setDeviceType(deviceType);
				push.setUserId(u.getId());
				pushService.add(push);
			}
//			customerService.get
			map.put("result", u);
			code="0000";
			msg="成功";
		} catch (Exception e) {
			code = "0001";
			msg = e.getMessage();
		}
		map.put("msg", msg);
		map.put("code", code);
		return map;
	}
	@RequestMapping(value = "app/user/sendMobileCode", name = "发送重置密码手机验证码", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> sendVerificationCode(String phone){
		Map<String, Object> map=new HashMap<>();
		System.out.println("手机号:"+phone);//需处理恶意连续发送问题
		try {
			GetField.getOTM(" APP发送重置密码手机验证码:",phone);
			if (userService.getUserByUsername(phone) != null) {
				try {
					SMSUtil.sendSMSVerify(phone);
					code = "0000";
					msg = "成功";
				} catch (Exception e) {
					code = "0001";
					msg = e.getMessage();
				}
			} else {
				code = "0001";
				msg = "用户未注册";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("code", code);
		return map;
	}
	@RequestMapping(value = "app/user/resetPassword", name = "密码重置", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> resetUserPassword(User user) {
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP密码重置:",user);
			userService.resetPassword(user);
			code="0000";
			msg="修改成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("msg", msg);
		map.put("code", code);
		return map;
	}
	@RequestMapping(value = "app/user/codeCheck", name = "验证码校验", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> codeCheck(User user) {
		Map<String, Object> map=new HashMap<>();
		try {
			boolean checkVerify = SMSUtil.checkVerify(user.getUsername(), user.getCode());
			msg=checkVerify?"成功":"验证码有误";
			code =checkVerify?"0000":"0001";
		} catch (Exception e) {
			code="0001";
			msg="验证码验证出错";
		}
		map.put("msg", msg);
		map.put("code", code);
		return map;
	}
}



package com.dec.pro.action.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.company.DecorationCompany;
import com.dec.pro.entity.user.User;
import com.dec.pro.filter.UserRealm;
import com.dec.pro.service.user.UserService;
import com.dec.pro.util.AES;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.SMSUtil;
import com.dec.pro.util.UUid;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRealm userRealm;
	private String code;
	private String msg;
	private Logger logger=Logger.getLogger(UserController.class);
	@RequestMapping(value = "user/register", name = "用户注册", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String userRegister(User user,DecorationCompany decorationCompany,@RequestParam("businessLicense") MultipartFile[] businessLicense,ModelMap modelMap) {
		user.setId(UUid.getUUid());
		try {
			GetField.getOTM(" PC用户注册:",user);
			userService.register(user,decorationCompany,businessLicense);
			code="1";
			msg = "成功";
		} catch (Exception e) {
			code="0";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return JSON.toJSONString(code);
	}
	@RequestMapping(value = "user/codeCheck", name = "验证码校验", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String codeCheck(User user,ModelMap modelMap) {
		try {
			boolean checkVerify = SMSUtil.checkVerify(user.getUsername(), user.getCode());
			msg=checkVerify?"成功":"验证码有误";
			code =checkVerify?"00":"01";
		} catch (Exception e) {
			code="01";
			msg="验证码验证出错";
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("msg",msg);
 		return code;
	}
	@RequestMapping(value = "user/resetPassword", name = "密码重置", method = {RequestMethod.POST, RequestMethod.GET })
	public String resetUserPassword(User user,ModelMap modelMap) {
		try {
			userService.resetPassword(user);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
 		return "test/login";
	}

	@RequestMapping(
		value = "user/sendMobileCode", name = "发送手机验证码", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> sendVerificationCode(String phone, int type, ModelMap modelMap) {
		System.out.println("手机号:" + phone);// 需处理恶意连续发送问题
		Map<String, Object> resultMap=new HashMap<>();
		try {
			if (type == 1) {// 1.注册码验证 2.重置密码验证
				if (userService.getUserByUsername(phone) == null) {
					SMSUtil.sendSMSVerify(phone);
					code = "00";
					msg = "成功";
				} else {
					code = "01";
					msg = "用户名已注册";
				}
			} else if (type == 2) {
				if (userService.getUserByUsername(phone) == null) {
					code = "01";
					msg = "用户名未注册";
				} else {
					SMSUtil.sendSMSVerify(phone);
					code = "00";
					msg = "成功";
				}
			}
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code", code);
		modelMap.addAttribute("msg", msg);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	@RequestMapping(value = "user/login", name = "用户登录", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> userLogin(User user,ModelMap modelMap){
		logger.info("登陆信息info测试");
		Map<String, Object> resultMap=new HashMap<>();
		User u = null ;
		try {
			GetField.getOTM(" PC用户登陆:",user);
			user.setType(1);//标识为公司登陆
			u = userService.loginAPP(user);
			modelMap.put("result", u);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		//String jUser = JSON.toJSONString(u);
		resultMap.put("msg", msg);
		resultMap.put("code", code);
		resultMap.put("result", u);
		return resultMap;
	}
//	@RequestMapping(value = "user/login", name = "用户登录", method = {RequestMethod.POST, RequestMethod.GET })
//	public String userLogin(User user,ModelMap modelMap){
//		logger.info("登录请求！");
//		// 获取认证对象的包装对象
//		Subject subject = SecurityUtils.getSubject();
//		
//		// 获取一个认证的令牌:
//		// 直接获取页面的用户和密码进行校验
//		AuthenticationToken authenticationToken = new UsernamePasswordToken(user.getUsername(), MD5.encrypt(user.getPassword()));
//		logger.info(MD5.encrypt(user.getPassword()));
//		// 认证过程
//		try {
//			// 如果成功，就不抛出异常，会自动将用户放入session的一个属性
//			subject.login(authenticationToken);
//			// 成功,返回首页
//			return "index/index";
//		} catch (UnknownAccountException e) {
//			// 用户名错误
//			logger.info("UserAction.usernamenotfound");
//			// 返回登陆页面
//			return "error";
//		} catch (IncorrectCredentialsException e) {
//			// 密码错误
//			logger.info("UserAction.passwordinvalid");
//			// 返回登陆页面
//			return "error";
//		} catch (AuthenticationException e) {
//			// 认证失败
//			e.printStackTrace();
//			// 页面上进行提示
//			logger.info("UserAction.loginfail");
//			// 返回登陆页面
//			return "error";
//		}	
//	}
	
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        // 先退出,再更新登录时间
        // User user = userRealm.getLoginedUser();
        SecurityUtils.getSubject().logout();
        return "redirect:'/login'";
    }
	@RequestMapping(value = "user/queryById", name = "查询单个用户", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String queryUserById(String id,ModelMap modelMap) {
		User user = null;
		try {
			GetField.getOTM(" PC查询单个用户:",id);
			user = userService.getOne(id);
			user.setPassword(AES.decrypt(user.getPassword()));
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		String jUserString = JSON.toJSONString(user);
		modelMap.addAttribute("user",user);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jUserString;
	}
	@RequestMapping(value = "user/queryList", name = "按条件检索用户", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryUserList(User user,ModelMap modelMap) {
		Page<User> pages = null;
		try {
			pages = userService.getUserPages(user);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jUser = JSON.toJSONString(pages);
		//logger.info("返回的json数据位："+jUser);
		modelMap.addAttribute("jUser",jUser);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return "test/test";
	}
	@RequestMapping(value = "user/removeById", name = "删除用户", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> removeUserById(String id,ModelMap modelMap) {
		int count = 0;
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC删除用户:",id);
			count = userService.delete(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	@RequestMapping(value = "user/modifyById", name = "修改用户", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> modifyUserById(User user,ModelMap modelMap) {
		int count = 0;
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC修改用户:",user);
			user.setPassword(AES.encrypt(user.getPassword()));
			count = userService.update(user);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	//登录
	public String login(User user,ModelMap modelMap){
		
		return null;
	}
	//修改密码
}

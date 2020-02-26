package com.dec.pro.action.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.sms.SMS;
import com.dec.pro.service.sms.SMSService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.IdCardDiscern;

@Controller
public class SMSController {
	@Autowired
	private SMSService smsService;
	private String code;
	private String msg;
	@RequestMapping(value="sms/increaseClient",name="添加短信客户",method={RequestMethod.POST,RequestMethod.GET})
	public String increaseClient(SMS sms,ModelMap modelMap){
		int count = 0 ;
		try {
			GetField.getOTM(" PC添加短信客户:",sms);
			count = smsService.addClient(sms);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg = "添加短信客户失败";
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/test";
	}
	@RequestMapping(value="sms/updateClientByPhone",produces = "application/json; charset=utf-8",name="根据电话更新客户信息",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String updateSMSClientById(SMS sms,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC根据电话更新客户信息:",sms);
			count = smsService.update(sms);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		System.out.println("count="+count);
		//return String.valueOf(count);
		return "test/test";
	}
	@RequestMapping(value="sms/queryClientById",name="查询短信客户",method={RequestMethod.POST,RequestMethod.GET})
	public String querySMSClientById(String id,ModelMap modelMap){
		SMS sms = null;
		try {
			GetField.getOTM(" PC查询短信客户:",id);
			sms = smsService.getOne(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		String jSMSClient=JSON.toJSONString(sms);
		modelMap.addAttribute("jSMSClient",jSMSClient);
		return "test/test";
	}
	
	@RequestMapping(value="sms/queryListClient",produces = "application/json; charset=utf-8",name="查询多个客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryListSMSClient(SMS sms,ModelMap modelMap){
//		Page<SMS> pages = null;
//		try {
//			pages = smsService.getSMSClientList(sms);
//		} catch (Exception e) {
//			msg=e.getMessage();
//			code="01";
//		}
//		String jSMSClients=JSON.toJSONString(pages);
//		modelMap.addAttribute("jSMSClients",jSMSClients);
//		modelMap.addAttribute("code",code);
//		modelMap.addAttribute("msg",msg);
		List<SMS> listSms = null;
		try {
			listSms = smsService.getListSMS(sms);
			if(listSms!=null){
				for (SMS smsR : listSms) {
					if(smsR!=null){
						try {
							String idCard = smsR.getIdCard();
							String name = smsR.getName();
							String sex = IdCardDiscern.getSex(idCard);
							int age = IdCardDiscern.getAge(idCard);
							smsR.setSex(sex);
							smsR.setAge(age);
							smsR.setName(hideName(name));
						} catch (Exception e) {
							System.out.println("异常信息："+e.getMessage());
						}
				
					}
				}
			}
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jListSms=JSON.toJSONString(listSms);
		modelMap.addAttribute("jListSms",jListSms);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jListSms;
	}
	/**
	 * 发送短信消息
	 * @return
	 */
	@RequestMapping(value="sms/sendOneMsg",name="短信单发",method={RequestMethod.POST,RequestMethod.GET})
	public String sendOneSMSMsg(SMS sms,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC短信单发:",sms);
			count = smsService.sendOneSMSMsg(sms);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("count", count);
		return "test/test";
	}

	@RequestMapping(value="sms/sendListMsg",produces = "application/json; charset=utf-8",name="短信群发",method={RequestMethod.POST,RequestMethod.GET})
	public String sendListSMSMsg(@RequestBody List<SMS> lSms,ModelMap modelMap){
		System.out.println(lSms);
		int count = 0 ;
		try {
			GetField.getOTM(" PC短信群发:",lSms);
			count = smsService.sendListSMSMsg(lSms);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg = "短信群发失败！";
		}
		modelMap.addAttribute("count", count);
		return "test/test";
	}
	public String hideName(String name){
		StringBuffer sb=new StringBuffer("");
		if(name.length()>=1){
			for (int i = 0; i < name.length(); i++) {
				if(i==0)
					sb.append(name.charAt(i));
				else
					sb.append("*");
			}
		}else{
			sb.append("未知");
		}
		return sb.toString();
	}
}

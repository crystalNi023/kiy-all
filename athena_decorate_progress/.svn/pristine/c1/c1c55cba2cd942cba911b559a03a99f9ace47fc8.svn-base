package com.dec.pro.action.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.sms.Template;
import com.dec.pro.service.sms.TemplateService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.UUid;

@Controller
public class TemplateController {
	@Autowired
	private TemplateService templateService;
	private String code;
	private String msg;
	
	@RequestMapping(value = "sms/increaseTemplate", name = "添加模板", method = {RequestMethod.POST, RequestMethod.GET })
	public String increaseTemplate(Template template,ModelMap modelMap) {
		template.setId(UUid.getUUid());
//		template.setTemplateId("318053");
//		template.setType(0);
//		template.setAutograph("凯迎智能管家");
//		template.setContent("您修改密码的验证码为{1}，请尽快填写完成验证，为保证您的账户安全，请勿外泄");
//		template.setRemark("凯迎智能管家");
		//Map<String,String> resultMap = templateService.addTemplate(template);
		int count = 0 ;
		try {
			GetField.getOTM(" PC添加模板:",template);
			count = templateService.addTemplate(template);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("count",count);
		return "test/test";
	}	
	
	@RequestMapping(value = "sms/queryListTemplate",produces = "application/json; charset=utf-8", name = "查询模板信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String queryListTemplate(Template template,ModelMap modelMap) {
		List<Template> list = null;
		try {
			list = templateService.getTemplateList(template);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jTemplateList=JSON.toJSONString(list);
		modelMap.addAttribute("jTemplateList",jTemplateList);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jTemplateList;
	}
}

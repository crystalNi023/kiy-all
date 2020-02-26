package com.dec.pro.action.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.company.DecorationCompany;
import com.dec.pro.service.company.DecorationCompanyService;
import com.dec.pro.util.GetField;

@Controller
public class DecorationCompanyController {
	
	@Autowired
	DecorationCompanyService decorationCompanyService;
	private String code;
	private String msg;
	@RequestMapping(value="decorationCompany/queryById",name="通过id查询装修公司",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String getDecorationCompanyMessage(String id,ModelMap modelMap){
		DecorationCompany decorationCompany2 = null;
		try {
			GetField.getOTM(" PC通过id查询装修公司:",id);
			decorationCompany2 = decorationCompanyService.getOne(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("decorationCompany",decorationCompany2);
		return JSON.toJSONString(decorationCompany2);
	} 
	
	@RequestMapping(value="decorationCompany/modifyById",name="修改装修公司",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyDecorationCompanyById(DecorationCompany decorationCompany,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改装修公司:",decorationCompany);
			count = decorationCompanyService.update(decorationCompany);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		String countString=JSON.toJSONString(count);
		modelMap.addAttribute("count",count);
		return countString;
	} 

}

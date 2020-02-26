package com.dec.pro.action.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.project.ProcedureTemplate;
import com.dec.pro.service.project.ProcedureTemplateService;
import com.dec.pro.util.GetField;

@Controller
public class ProcedureTemplateController {
	@Autowired
	private ProcedureTemplateService procedureTemplateService;
	private String code;
	private String msg;
	
	
	@RequestMapping(value="procedureTemplate/queryList",produces="text/html;charset=UTF-8",name="查询流程名称模板",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProcedureTemplateList(ProcedureTemplate procedureTemplate,ModelMap modelMap){
		List<ProcedureTemplate> procedureTemplateList = null;
		try {
			procedureTemplateList =procedureTemplateService.getList(GetField.getOTM(" PC端查询流程模板:",procedureTemplate));
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jProcedureTemplateList=JSON.toJSONString(procedureTemplateList);
		modelMap.addAttribute("jProcedureTemplateList",jProcedureTemplateList);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jProcedureTemplateList;
	} 
}

package com.dec.pro.action.project;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.project.Procedure;
import com.dec.pro.service.project.ProcedureService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class ProcedureController {
	@Autowired
	private ProcedureService procedureService;
	private String code;
	private String msg;
	
	@RequestMapping(value="procedure/increase",name="添加流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String increaseProcedure(Procedure procedure, ModelMap modelMap) {
		procedure.setId(UUid.getUUid());
		procedure.setStatus(1);//状态1.未启动 2.装修中 3.已完成
		int count = 0;
		try {
			GetField.getOTM(" PC添加流程:",procedure);
			count = procedureService.addProcedure(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/procedure";
	}
	@RequestMapping(value="procedure/modifyById",name="修改流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> modifyProcedureById(Procedure procedure,ModelMap modelMap){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC修改流程:",procedure);
			procedureService.update(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	} 
	@RequestMapping(value="procedure/reLaunch",name="重新发起流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> reLaunch(Procedure procedure,ModelMap modelMap){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC重新发起流程:",procedure);
			procedureService.updateProcedure(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	} 
	@RequestMapping(value="procedure/removeById",name="移除流程",method={RequestMethod.POST,RequestMethod.GET})
	public String removeProcedureById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除流程:",id);
			count = procedureService.delete(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/procedure";
	} 
	@RequestMapping(value="procedure/queryById",name="通过id查询流程",method={RequestMethod.POST,RequestMethod.GET})
	public String queryProcedureById(String id,ModelMap modelMap){
		Procedure procedure = null;
		try {
			GetField.getOTM(" PC通过id查询流程:",id);
			procedure =procedureService.getOne(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("procedure",procedure);
		return "test/procedure";
	} 
	@RequestMapping(value="procedure/queryList",produces="text/html;charset=UTF-8",name="分页查询流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProcedureList(Procedure procedure,ModelMap modelMap){
		Page<Procedure> pages = null;
		try {
			pages =procedureService.getProcedureList(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jProcedures=JSON.toJSONString(pages);
		modelMap.addAttribute("jProcedures",jProcedures);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jProcedures;
	} 
}

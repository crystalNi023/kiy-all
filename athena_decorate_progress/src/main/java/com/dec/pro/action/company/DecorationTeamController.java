package com.dec.pro.action.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.company.DecorationTeam;
import com.dec.pro.service.company.DecorationTeamService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class DecorationTeamController {
	
	@Autowired
	DecorationTeamService decorationTeamService;
	private String code;
	private String msg;
	@RequestMapping(value="decorationTeam/increase",name="新增装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseDecorationTeam(DecorationTeam decorationTeam,ModelMap modelMap){
		decorationTeam.setId(UUid.getUUid());
		Map<String, Object> resultMap=new HashMap<>();
		int count = 0;
		try {
			GetField.getOTM(" PC新增装修团队:",decorationTeam);
			count =	decorationTeamService.addDecorationTeam(decorationTeam);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	
	@RequestMapping(value="decorationTeam/queryList",name="分页查询装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryDecorationTeamList(DecorationTeam decorationTeam,ModelMap modelMap){
		Page<DecorationTeam> pages = null;
		try {
			pages =decorationTeamService.getDecorationTeamList(decorationTeam);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jDecorationTeams=JSON.toJSONString(pages);
		modelMap.addAttribute("jDecorationTeams",jDecorationTeams);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jDecorationTeams;
	} 
	
	@RequestMapping(value = "decorationTeam/distributionUser", name = "给装修团队分配账号", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> distributionUser(DecorationTeam decorationTeam,String username,String password,ModelMap modelMap) {
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC给装修团队分配账号:",decorationTeam);
			decorationTeamService.distributionUser(username,password,decorationTeam);
			code="00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	@RequestMapping(value="decorationTeam/queryListByComId",name="根据公司查询装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryDecorationTeamListByComId(DecorationTeam decorationTeam,ModelMap modelMap){
		List<DecorationTeam> decorationTeams = null;
		try {
			GetField.getOTM(" PC根据公司查询装修团队:",decorationTeam);
			decorationTeams =decorationTeamService.getListByComId(decorationTeam.getComId());
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jDecorationTeams=JSON.toJSONString(decorationTeams);
		modelMap.addAttribute("jDecorationTeams",jDecorationTeams);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jDecorationTeams;
	} 
	
	
	@RequestMapping(value="decorationTeam/modifyById",name="修改装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyDecorationTeamById(DecorationTeam decorationTeam,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改装修团队:",decorationTeam);
			count = decorationTeamService.update(decorationTeam);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		modelMap.addAttribute("count",count);
		return null;
	} 
	
	@RequestMapping(value="decorationTeam/deleteById",name="根据id删除装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String deleteDecorationTeamById(DecorationTeam decorationTeam,ModelMap modelMap){
		try {
			GetField.getOTM(" PC根据id删除装修团队:",decorationTeam);
			int delete = decorationTeamService.delete(decorationTeam.getId());
			if (delete==1) {
				code="00";
				msg="成功";
			}else{
				code="01";
				msg="删除失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		return code;
	} 
	@RequestMapping(value="decorationTeam/relationProject",name="关联项目和装修员工",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> relationProject(String projectId,String decorationTeamId){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC关联项目和装修员工:",decorationTeamId);
			int add = decorationTeamService.relationProject(projectId,decorationTeamId);
			if (add==1) {
				code="00";
				msg="成功";
			}else{
				code="01";
				msg="操作失败！";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	} 
	@RequestMapping(value="decorationTeam/deleteRelation",name="解除装修员工和关联项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> deleteRelation(String projectId,String decorationTeamId){
		Map<String, Object> resultMap=new HashMap<>();
		try {
			GetField.getOTM(" PC解除装修员工和关联项目:",decorationTeamId);
			int delete = decorationTeamService.deleteRelation(projectId,decorationTeamId);
			if (delete==1) {
				code="00";
				msg="成功";
			}else{
				code="01";
				msg="操作失败！";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	} 
}

package com.dec.pro.action.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.project.Project;
import com.dec.pro.service.project.ProjectService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	private String code;
	private String msg;
	
	@Transactional
	@RequestMapping(value="project/increase",name="发起项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseProject(@RequestBody Project project, ModelMap modelMap) {
		Map<String, Object> resultMap=new HashMap<>();
		project.setId(UUid.getUUid());
		try {
			GetField.getOTM(" PC发起项目:",project);
			projectService.addProject(project);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	@RequestMapping(value="project/modifyById",name="修改项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyProjectById(Project project,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改项目:",project);
			count = projectService.update(project);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/project";
	} 
	@RequestMapping(value="project/removeById",name="移除项目",method={RequestMethod.POST,RequestMethod.GET})
	public String removeProjectById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除项目:",id);
			count = projectService.delete(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/project";
	} 
	@RequestMapping(value="project/queryById",name="通过id查询项目",method={RequestMethod.POST,RequestMethod.GET})
	public String queryProjectById(String id,ModelMap modelMap){
		Project project = null;
		try {
			GetField.getOTM(" 通过id查询项目:",id);
			project = projectService.getOne(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("project",project);
		return "test/project";
	} 
	@RequestMapping(value="project/queryList",produces="text/html;charset=UTF-8",name="分页查询项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProjectList(Project project,ModelMap modelMap){
		Page<Project> pages = null;
		try {
			pages =projectService.getProjectList(project);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jProjects=JSON.toJSONString(pages);
		modelMap.addAttribute("jProjects",jProjects);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jProjects;
	} 
	@RequestMapping(value="project/news",produces="text/html;charset=UTF-8",name="查询项目是否有新消息",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProjectList(@RequestParam("proIdList[]")List<String> proId,ModelMap modelMap){
		List<Project> projectList2 = null;
		System.out.println(proId);
		try {
			GetField.getOTM(" 查询项目是否有新消息:",proId);
			projectList2 = projectService.getNews(proId);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		String jProjects=JSON.toJSONString(projectList2);
		modelMap.addAttribute("jProjects",jProjects);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jProjects;
	} 
}

package com.dec.pro.interfaces.project;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.project.Project;
import com.dec.pro.service.project.ProjectService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;

@Controller(value="项目")
public class ProjectInterface {
	@Autowired
	private ProjectService projectService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/project/query",name="根据客户id查询项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryProject(Project project) throws Exception{
		Map<String, Object> map=new HashMap<>();
		Project projectListByCusId = null;
		try {
			GetField.getOTM(" APP根据客户id查询项目:",project);
			projectListByCusId = projectService.getProjectListByCusId(project);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",projectListByCusId);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
	@RequestMapping(value="app/project/queryList",name="分页查询项目",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryProjectList(Project project) throws Exception{
		Map<String, Object> map=new HashMap<>();
		Page<Project> pages = null;
		try {
			pages =projectService.getProjectList(project);
			GetField.getOTM(" APP分页查询项目:",project);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",pages);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
	/**
	 * 获取摄像头列表
	 * @param project（查询参数comId和userId）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="app/project/queryListForCamera",name="分页查询项目摄像头",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryProjectListForCamera(Project project) throws Exception{
		Map<String, Object> map=new HashMap<>();
		Page<Project> pages = null;
		try {
			pages =projectService.getProjectListForCamera(project);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",pages);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
}

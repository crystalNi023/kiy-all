package com.dec.pro.action.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.project.Progress;
import com.dec.pro.service.project.ProgressService;
import com.dec.pro.util.GetField;

@Controller
public class ProgressController {
	@Autowired
	private ProgressService progressService;
	private String code;
	private String msg;
	
	@RequestMapping(value="progress/increase",name="添加流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String increaseProgress(Progress progress, ModelMap modelMap) {
		int count = 0;
		try {
			GetField.getOTM(" PC添加流程进度:",progress);
			count = progressService.increaseProgress(progress);
			if (count == 1) {
				code = "00";
				msg = "成功";
			}else{
				code = "01";
				msg = "操作失败";
			}
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/progress";
	}
	@RequestMapping(value="progress/postponeAndAcceptance",name="整改、延期申请/验收申请",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String postponeAndAcceptanceProgress(Progress progress, ModelMap modelMap) {
		try {
			GetField.getOTM(" PC整改、延期申请/验收申请:", progress);
			int count = progressService.addPostponeAndAcceptance(progress);
			code = "00";
			msg = "成功";
			modelMap.addAttribute("count", count);
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code", code);
		modelMap.addAttribute("msg", msg);
		return "test/progress";
	}
	@RequestMapping(value="progress/modifyById",name="修改流程进度",method={RequestMethod.POST,RequestMethod.GET})
	public String modifyProgressById(Progress progress,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改流程进度:",progress);
			count = progressService.update(progress);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/progress";
	} 
	@RequestMapping(value="progress/removeById",name="移除流程进度",method={RequestMethod.POST,RequestMethod.GET})
	public String removeProgressById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除流程进度:",id);
			count = progressService.delete(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/progress";
	} 
	@RequestMapping(value="progress/queryById",name="通过id查询流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProgressById(String id,ModelMap modelMap){
		Progress progress = null;
		try {
			GetField.getOTM(" PC通过id查询流程进度:",id);
			progress =progressService.getOne(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("progress",progress);
		return "test/progress";
	} 
	@RequestMapping(value="progress/queryList",name="分页查询流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryProgressList(Progress progress,ModelMap modelMap){
		List<Progress> resultList = null;
		try {
			resultList =progressService.getProgressList(progress);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		String jProgresss=JSON.toJSONString(resultList);
		modelMap.addAttribute("jProgresss",jProgresss);
		return jProgresss;
	} 
}

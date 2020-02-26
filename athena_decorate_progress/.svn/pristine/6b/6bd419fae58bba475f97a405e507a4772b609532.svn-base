package com.dec.pro.interfaces.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.entity.project.Progress;
import com.dec.pro.entity.project.ProgressRecord;
import com.dec.pro.service.project.ProgressService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;

@Controller(value="流程进度")
public class ProgressInterface {
	@Autowired
	private ProgressService progressService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/progress/queryList",name="分页查询流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryProjectList(Progress progress){
		Map<String, Object> map=new HashMap<>();
		List<Progress> resultList = null;
		try {
			resultList =progressService.getProgressList(progress);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",resultList);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/progress/queryListAll",name="分页查询项目下所有流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryListAll(Procedure procedure){
		Map<String, Object> map=new HashMap<>();
		Page<Procedure> progressListAll = null;
		try {
			GetField.getOTM(" APP分页查询所有流程进度:",procedure);
			progressListAll = progressService.getProgressListAll(procedure);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result", progressListAll);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/progress/increase",name="添加流程进度",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseProgress(Progress progress,String promptMsg,int promptType) {
		Map<String, Object> map=new HashMap<>();
		ProgressRecord progressRecord = new ProgressRecord();
		progressRecord.setPromptMsg(promptMsg);
		progressRecord.setPromptType(promptType);
		progress.setProgressRecord(progressRecord);
		int count = 0;
		try {
			GetField.getOTM("APP添加流程进度:",progress);
			count = progressService.increaseProgress(progress);
			if (count == 1) {
				code = "0000";
				msg = "成功";
			}else{
				code = "0001";
				msg = "操作失败";
			}
		} catch (Exception e) {
			code = "0001";
			msg = e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/progress/postponeAndAcceptance",name="整改、延期申请/验收申请",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> postponeAndAcceptanceProgress(Progress progress,int promptType,String promptMsg,Integer timeAssess) {
		Map<String, Object> map=new HashMap<>();
		ProgressRecord progressRecord = new ProgressRecord();
		progressRecord.setPromptMsg(promptMsg);
		progressRecord.setPromptType(promptType);
		if (timeAssess!=null) {
			progressRecord.setTimeAssess(timeAssess);
		}
		progress.setProgressRecord(progressRecord);
		int count = 0;
		try {
			GetField.getOTM(" APP整改、延期申请/验收申请:", progress);
			count = progressService.addPostponeAndAcceptance(progress);
			if (count == 1) {
				code = "0000";
				msg = "成功";
			}else{
				code = "0001";
				msg = "操作失败";
			}
		} catch (Exception e) {
			code = "0001";
			msg=e.getMessage();
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
}

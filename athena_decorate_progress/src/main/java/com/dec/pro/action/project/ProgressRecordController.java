package com.dec.pro.action.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.project.ProgressRecord;
import com.dec.pro.service.project.ProgressRecordService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.UUid;

@Controller
public class ProgressRecordController {
	@Autowired
	private ProgressRecordService progressRecordService;
	private String code;
	private String msg;
	
	@RequestMapping(value="progressRecord/increase",name="添加进度记录",method={RequestMethod.POST,RequestMethod.GET})
	public String increaseProgressRecord(ProgressRecord progressRecord, ModelMap modelMap) {
		progressRecord.setId(UUid.getUUid());
		progressRecord.setPromptTime(new Date());//设置提示时间
		int count = 0;
		try {
			GetField.getOTM(" PC添加进度记录:",progressRecord);
			count = progressRecordService.add(progressRecord);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return "test/progressRecord";
	}
	@RequestMapping(value="progressRecord/modifyById",name="修改进度记录",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyProgressRecordById(ProgressRecord progressRecord,ModelMap modelMap){
		progressRecord.setReplyTime(new Date());//设置回复时间
		int count = 0;
		try {
			GetField.getOTM(" PC修改进度记录:",progressRecord);
			count = progressRecordService.updateProgressRecord(progressRecord);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count", count);
		return JSON.toJSONString(count);
	} 
	@RequestMapping(value="progressRecord/removeById",name="移除进度记录",method={RequestMethod.POST,RequestMethod.GET})
	public String removeProgressRecordById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除进度记录:",id);
			count = progressRecordService.delete(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/progressRecord";
	} 
	@RequestMapping(value="progressRecord/queryById",name="通过id查询进度记录",method={RequestMethod.POST,RequestMethod.GET})
	public String queryProgressRecordById(String id,ModelMap modelMap){
		ProgressRecord progressRecord = null ;
		try {
			GetField.getOTM(" PC通过id查询进度记录:",id);
			progressRecord = progressRecordService.getOne(id);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("progressRecord",progressRecord);
		return "test/progressRecord";
	} 
}

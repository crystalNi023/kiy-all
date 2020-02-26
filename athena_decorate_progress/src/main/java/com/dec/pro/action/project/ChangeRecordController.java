package com.dec.pro.action.project;

import java.util.Date;
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
import com.dec.pro.entity.project.ChangeRecord;
import com.dec.pro.entity.project.Procedure;
import com.dec.pro.service.project.ChangeRecordService;
import com.dec.pro.util.GetField;

@Controller
public class ChangeRecordController {
	@Autowired
	private ChangeRecordService changeRecordService;
	private String code;
	private String msg;
	@RequestMapping(value="changeRecord/modify",name="修改需求变更",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyChangeRecord(ChangeRecord changeRecord,ModelMap modelMap){
		changeRecord.setReplyTime(new Date());//设置回复时间
		int add = 0 ;
		try {
			GetField.getOTM(" PC修改需求变更:",changeRecord);
			add = changeRecordService.update(changeRecord);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return JSON.toJSONString(add);
	}
	@RequestMapping(value="changeRecord/queryByProId",name="获取项目所有需求变更",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryChangeRecordByProId(Procedure procedure,ModelMap modelMap){
		List<Procedure> listByProId = null;
		try {
			GetField.getOTM(" PC获取项目所有需求变更:",procedure);
			listByProId = changeRecordService.getListByProId(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return JSON.toJSONString(listByProId);
	}
	@RequestMapping(value="changeRecord/readByProId",name="设置项目需求变更消息为已读",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> readByProId(Procedure procedure,ModelMap modelMap){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			GetField.getOTM(" PC设置项目需求变更消息为已读:", procedure);
			changeRecordService.readByProId(procedure);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
}

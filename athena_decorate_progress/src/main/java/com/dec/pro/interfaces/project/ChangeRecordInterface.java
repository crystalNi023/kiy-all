package com.dec.pro.interfaces.project;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.project.ChangeRecord;
import com.dec.pro.entity.project.Procedure;
import com.dec.pro.service.project.ChangeRecordService;
import com.dec.pro.util.GetField;

@Controller(value="需求变更")
public class ChangeRecordInterface {
	@Autowired
	private ChangeRecordService changeRecordService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/changeRecord/add",name="新增需求变更",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> addChangeRecord(ChangeRecord changeRecord,@RequestParam("changeRecordImgs") MultipartFile[] changeRecordImgs){
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP新增需求变更:",changeRecord);
			int add = changeRecordService.add(changeRecord, changeRecordImgs);
			if(add==1){
				code="0000";
				msg="提交成功";
			}else {
				code="0001";
				msg="提交失败";
			}
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/changeRecord/queryByProId",name="获取项目所有需求变更",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryChangeRecordByProId(Procedure procedure,ModelMap modelMap){
		Map<String, Object> map=new HashMap<>();
		List<Procedure> result = null;
		try {
			GetField.getOTM(" APP获取项目所有需求变更:",procedure);
			result = changeRecordService.getListByProId(procedure);
			code="0000";
			msg="提交成功";
		} catch (Exception e) {
			code="0001";
			msg="提交失败";
			msg = e.getMessage();
		}
		map.put("result", result);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
}

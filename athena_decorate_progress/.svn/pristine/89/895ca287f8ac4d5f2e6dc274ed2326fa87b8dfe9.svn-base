package com.dec.pro.interfaces.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.project.ProgressRecord;
import com.dec.pro.service.project.ProgressRecordService;
import com.dec.pro.util.GetField;

@Controller(value="进度记录")
public class ProgressRecordInterface {
	
	@Autowired
	private ProgressRecordService progressRecordService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/progressRecord/modifyById",name="修改进度记录",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> modifyProgressRecordById(ProgressRecord progressRecord){
		Map<String, Object> map=new HashMap<>();
		progressRecord.setReplyTime(new Date());//设置回复时间
		try {
			GetField.getOTM(" APP修改进度记录:",progressRecord);
			progressRecordService.updateProgressRecord(progressRecord);
			code="0000";
			msg="成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
}

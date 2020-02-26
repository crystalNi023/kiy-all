package com.dec.pro.interfaces.feedback;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.feedback.Feedback;
import com.dec.pro.service.feedback.FeedbackService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.UUid;

@Controller(value="意见反馈")
public class FeedBackInterface {
	@Autowired
	private FeedbackService feedbackService;
	private String code="0000";
	private String msg="成功";
	
	@RequestMapping(value="app/feedback/increase",name="添加意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseFeedback(Feedback feedback,@RequestParam("feedbackImage") MultipartFile[] feedbackImage){
		Map<String, Object> map=new HashMap<>();
		feedback.setId(UUid.getUUid());
		try {
			GetField.getOTM(" APP添加意见反馈:",feedback);
			feedbackService.addFeedback(feedback,feedbackImage);
			code="0000";
			msg="成功";
		} catch (Exception e) {
			code = "0001";
			msg = e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
}

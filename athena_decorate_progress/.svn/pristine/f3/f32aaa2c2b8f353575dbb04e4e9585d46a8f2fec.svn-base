package com.dec.pro.action.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.feedback.Feedback;
import com.dec.pro.service.feedback.FeedbackService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;
	private String code;
	private String msg;
	
	@RequestMapping(value="feedback/increase",name="添加意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String increaseFeedback(Feedback feedback,@RequestParam("feedbackImage") MultipartFile[] feedbackImage,ModelMap modelMap){
		feedback.setId(UUid.getUUid());
		int count = 0;
		try {
			GetField.getOTM(" PC添加意见反馈:",feedback);
			count = feedbackService.addFeedback(feedback,feedbackImage);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="feedback/removeById",name="移除意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	public String removeFeedbackById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除意见反馈:",id);
			count = feedbackService.delete(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="feedback/modifyById",name="修改意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	public String modifyFeedbackById(Feedback feedback,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改意见反馈:",feedback);
			count = feedbackService.update(feedback);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="feedback/queryById",name="通过id查询意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	public String queryFeedbackById(String id,ModelMap modelMap){
		Feedback feedback = null;
		try {
			GetField.getOTM(" PC通过id查询意见反馈:",id);
			feedback = feedbackService.getOne(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		String jFeedback=JSON.toJSONString(feedback);
		modelMap.addAttribute("jFeedback",jFeedback);
		return "test/test";
	}
	@RequestMapping(value="feedback/queryList",name="分页查询意见反馈",method={RequestMethod.POST,RequestMethod.GET})
	public String queryFeedbackList(Feedback feedback,ModelMap modelMap){
		Page<Feedback> pages=null;
		try {
			pages = feedbackService.getFeedbackList(feedback);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jFeedbacks=JSON.toJSONString(pages);
		modelMap.addAttribute("jFeedbacks",jFeedbacks);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return "test/test";
	}
	
	@RequestMapping(value="feedback/queryPowerByFeedbackId",name="通过意见反馈id查权限",method={RequestMethod.POST,RequestMethod.GET})
	public String queryPowerByFeedbackId(String feedbackId,ModelMap modelMap){
		
		return null;
	}
}

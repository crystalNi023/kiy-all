package com.dec.pro.service.sms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dec.pro.entity.sms.Template;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.GetField;
@Service
public class TemplateService extends BaseService<Template>{
	//private Logger logger=Logger.getLogger(TemplateService.class);
	public List<Template> getTemplateList(Template template) throws Exception{
		return mapper.getList(GetField.getOTM(" 查询模板信息:",template));
	}
	
	/*public Map<String,String> addTemplate(Template template){
		Map<String,String> resultMap = new HashMap<String,String>();
		//短信添加模板
		int type=template.getType();
		String templateName = template.getTemplateName();
		String autograph = template.getAutograph();
		String content = template.getContent();
		String result = SMSUtil.addTemplate(type, templateName, autograph, content);
		//添加数据库
		 JSONObject js=JSONObject.parseObject(result);
		 if(js!=null && "000000".equals(js.getString("code"))){
				template.setTemplateId(js.getString("templateid"));//设置模板id
			int count =	mapper.add(template);	
			logger.info("短信模板已申请:"+count+"条");
			}
		 String code = js.getString("code");
		 String msg = js.getString("msg");
		 resultMap.put("code", code);
		 resultMap.put("msg", msg);
		return resultMap;
	}*/
	public int addTemplate(Template template){
		 int count =	mapper.add(template);
		return count;
	}
}

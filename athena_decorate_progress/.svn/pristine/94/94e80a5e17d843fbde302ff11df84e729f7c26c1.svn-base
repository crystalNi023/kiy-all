package com.dec.pro.action.test;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.test.TestEntity;
import com.dec.pro.service.test.TestService;

@Controller(value="测试/测试")
public class TestController {
	@Autowired
	private TestService ts;
	private Logger logger=Logger.getLogger(TestController.class);
	
	@RequestMapping(name="添加测试" , value="test/add" ,method={RequestMethod.POST,RequestMethod.GET})
	public String addTest(TestEntity te,ModelMap modelMap) throws Exception{
		System.out.println("请求进来了！");
		
		te.setId(UUID.randomUUID().toString().replace("-", ""));
		te.setCreated(new Date());
		System.out.println("id=="+te.getId()+"   "+te.getId().length());
		System.out.println("name=="+te.getName());
		System.out.println("type=="+te.getType());
		System.out.println("createTime=="+te.getCreated());
		ts.add(te);
		return null;
	}
	@RequestMapping(name="查询测试" , value="test/query" ,method={RequestMethod.POST,RequestMethod.GET})
	public String getTest(String id,ModelMap modelMap) throws Exception{
		TestEntity te = ts.getOne(id);
		String jsonTe = JSON.toJSONString(te);//对象转json
		logger.info("jsonTe=="+jsonTe);
		logger.error("jsonTe=="+jsonTe);
		System.out.println("jsonTe=="+jsonTe);
		modelMap.addAttribute("test",jsonTe);
		return "test/show";
	}
}

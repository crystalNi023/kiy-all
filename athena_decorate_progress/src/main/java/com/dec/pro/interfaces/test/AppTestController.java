package com.dec.pro.interfaces.test;

	import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.test.TestEntity;
import com.dec.pro.service.test.TestService;

	@Controller(value="app测试/app测试")
	public class AppTestController {
		@Autowired
		private TestService ts;
		//private Logger logger=Logger.getLogger(AppTestController.class);
		
//		@RequestMapping(name="添加app测试" , value="appTest/add" ,method={RequestMethod.POST,RequestMethod.GET})
//		@ResponseBody
//		public Map<String, Object> addTest(HttpServletRequest request,HttpServletResponse  response){
//			System.out.println("请求进来了！");
//			System.out.println("测试名称："+request.getParameter("name"));
////			System.out.println("id=="+te.getId()+"   "+te.getId().length());
////			System.out.println("name=="+te.getName());
////			System.out.println("type=="+te.getType());
////			System.out.println("createTime=="+te.getCreated());
////			ts.add(te);
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("ok", "0000");
//			return map;
//		}
		@RequestMapping(name="添加app测试" , value="appTest/add",method={RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		public Map<String, Object> addTest(TestEntity test,ModelMap modelMap){
			System.out.println("请求进来了！");
			System.out.println("测试名称："+test.getName());
//			System.out.println("id=="+te.getId()+"   "+te.getId().length());
//			System.out.println("name=="+te.getName());
//			System.out.println("type=="+te.getType());
//			System.out.println("createTime=="+te.getCreated());
//			ts.add(te);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("0000", "成功");
			return map;
		}
	}


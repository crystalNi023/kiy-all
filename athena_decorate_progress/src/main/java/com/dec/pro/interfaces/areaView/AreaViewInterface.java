package com.dec.pro.interfaces.areaView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.areaView.AreaView;
import com.dec.pro.entity.customer.Customer;
import com.dec.pro.service.areaView.AreaViewService;
import com.dec.pro.util.GetField;

@Controller(value="摄像头")
public class AreaViewInterface {
	@Autowired
	AreaViewService areaViewService;
	
	private String code;
	private String msg;
	
	@RequestMapping(value = "app/areaView/getCamera", name = "获取摄像头", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getCamera(Customer customer){
		Map<String, Object> map=new HashMap<>();
		List<AreaView> cameraList = null;
		try {
			GetField.getOTM(" APP获取摄像头:",customer);
			cameraList = areaViewService.getListByProId(customer.getProId());
			code="0000";
			msg="成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result", cameraList);
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}
	@RequestMapping(value = "app/areaView/getCameraByUsername", name = "通过用户名获取摄像头", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> getCameraByUsername(String  username){
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP通过用户名获取摄像头:",username);
			List<AreaView> cameraList = areaViewService.getListByUsername(username);
			map.put("result", cameraList);
			code="0000";
			msg="成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}

}

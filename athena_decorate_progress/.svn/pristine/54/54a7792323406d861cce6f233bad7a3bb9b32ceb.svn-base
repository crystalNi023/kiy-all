package com.dec.pro.interfaces.company;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.company.DecorationCompany;
import com.dec.pro.service.company.DecorationCompanyService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;

@Controller(value="装修公司")
public class DecorationCompanyInterface {
	@Autowired
	private DecorationCompanyService decorationCompanyService;
	private String code="0000";
	private String msg="成功";
	
	@RequestMapping(value="app/decorationCompany/queryList",name="分页查询公司",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> queryDecorationCompanyList(DecorationCompany decorationCompany){//只含分页查询条件
		Map<String, Object> map=new HashMap<>();
		Page<DecorationCompany> pages = null;
		try {
			pages =decorationCompanyService.getDecorationCompanyList(decorationCompany);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",pages);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/decorationCompany/queryOne",name="查询单个公司信息",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> queryDecorationCompanyOne(String id){//公司id
		Map<String, Object> map=new HashMap<>();
		DecorationCompany decorationCompany = null;
		try {
			decorationCompany =decorationCompanyService.getOne(id);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",decorationCompany);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/decorationCompany/queryBySerialNumber",name="根据设备序列号查询公司信息",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> queryDecorationCompanyBySerialNumber(String serialNumber){//公司id
		Map<String, Object> map=new HashMap<>();
		DecorationCompany decorationCompany = null;
		try {
			decorationCompany =decorationCompanyService.getOneBySerialNumber(serialNumber);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("result",decorationCompany);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/decorationCompany/modifyDeviceAccountById",name="修改设备登录信息",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> modifyDecorationCompanyById(DecorationCompany decorationCompany){//只含分页查询条件
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP修改设备登录信息:",decorationCompany);
			decorationCompanyService.updateDecorationCompanyById(decorationCompany);
			code = "0000";
			msg = "修改成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}

}

package com.dec.pro.action.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.customer.Customer;
import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.service.customer.CustomerService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class CustomerController<V> {                                                                                                                                                                                                                                                                                                                                       
	@Autowired
	private CustomerService customerService;
	private String code;
	private String msg;
	
	@Transactional
	@RequestMapping(value="customer/increase",name="添加客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseCustomer(Customer customer,String comId, ModelMap modelMap, @RequestParam("contractImgs") MultipartFile[] contractImgs, @RequestParam("designImgs") MultipartFile[] designImgs){
		Map<String, Object> resultMap=new HashMap<>();
		customer.setId(UUid.getUUid());
			try {
				GetField.getOTM(" PC添加客户客户信息:",customer);
				GetField.getOTM(" PC添加客户公司编号:",comId);
				customerService.addCustomer(customer,comId, contractImgs,designImgs);
				code="00";
				msg="成功";
			} catch (Exception e) {
				code = "01";
				msg = e.getMessage();
		}
		resultMap.put("code", code);
		resultMap.put("msg", msg);
		return resultMap;
	}
	@RequestMapping(value="customer/modifyById",name="修改客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String modifyCustomerById(Customer customer,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改客户信息:",customer);
			count = customerService.update(customer);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/customer";
	}
	@RequestMapping(value="customer/removeById",name="移除客户",method={RequestMethod.POST,RequestMethod.GET})
	public String removeCustomerById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除客户:",id);
			count = customerService.delete(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/customer";
	} 
	@RequestMapping(value="customer/queryById",name="通过id查询客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryCustomerById(String id,ModelMap modelMap){
		Customer customer = null;
		try {
			GetField.getOTM(" PC通过id查询客户:",id);
			customer =customerService.getOne(id);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("customer",customer);
		return JSON.toJSONString(customer);
	} 
	@RequestMapping(value="customer/queryList",name="分页查询客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryCustomerList(Customer customer,ModelMap modelMap){
		Page<Customer> pages = null;
		try {
			pages =customerService.getCustomerList(customer);
			code="00";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="01";
		}
		String jCustomers=JSON.toJSONString(pages);
		modelMap.addAttribute("jCustomers",jCustomers);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return jCustomers;
	} 
	
	@RequestMapping(value="customer/getImgList",name="通过客户id查询图片",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryImgByCustomer(ImageResource imageResource,ModelMap modelMap){
		List<String> contractImgs = new ArrayList<>();
		List<String> designImgs = new ArrayList<>();
		HashMap<String, List<String>> hashMap = new HashMap<>();
		try {
			GetField.getOTM(" PC通过客户id查询图片:",imageResource);
			List<ImageResource> imgs = customerService.getImgByCustomerId(imageResource.getCusId());
			for (ImageResource imageResource2 : imgs) {
				if (imageResource2.getResourceType()==1) { // 合同
					contractImgs.add(imageResource2.getResourceUrl());
				} else if (imageResource2.getResourceType()==2) { // 设计图
					designImgs.add(imageResource2.getResourceUrl());
				}
			}
			hashMap.put("contractImgs", contractImgs);
			hashMap.put("designImgs", designImgs);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return JSON.toJSONString(hashMap);
	} 
	@Transactional
	@RequestMapping(value="customer/uploadImg",name="上传图片",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> uploadImg(Customer customer,ModelMap modelMap,@RequestParam("contractImgs") MultipartFile[] contractImgs,@RequestParam("designImgs") MultipartFile[] designImgs)throws Exception {
			Map<String, Object> resultMap=new HashMap<>();
			if (contractImgs.length > 0 || designImgs.length > 0) {
			try {
				GetField.getOTM(" PC上传图片:",customer);
				customerService.uploadImg(customer, contractImgs,designImgs);
				resultMap.put("code","00");
				resultMap.put("msg","成功");
			} catch (Exception e) {
				resultMap.put("code","01");
				resultMap.put("msg",e.getMessage());
			}
		}
			
		return resultMap;
	} 
}

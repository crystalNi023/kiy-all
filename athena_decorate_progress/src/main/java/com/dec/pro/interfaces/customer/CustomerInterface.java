package com.dec.pro.interfaces.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.customer.Customer;
import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.service.customer.CustomerService;
import com.dec.pro.util.GetField;

@Controller(value="客户信息")
public class CustomerInterface {
	@Autowired
	private CustomerService customerService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value = "app/customer/uploadImg", name = "上传图片", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> uploadImg(Customer customer,@RequestParam("contractImgs") MultipartFile[] contractImgs,@RequestParam("designImgs") MultipartFile[] designImgs){
		Map<String, Object> map=new HashMap<>();
		if (contractImgs.length > 0 || designImgs.length > 0) {
			try {
				GetField.getOTM(" APP上传图片:",customer);
				customerService.uploadImg(customer, contractImgs, designImgs);
				map.put("code", "0000");
				map.put("msg", "上传图片成功");
			} catch (Exception e) {
				map.put("code", "0001");
				map.put("msg", e.getMessage());
			}
		}
		return map;
	} 
	@RequestMapping(value="app/customer/getImgList",name="通过客户id查询图片",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryImgByCustomer(Customer customer,ImageResource imageResource){
		Map<String, Object> map=new HashMap<>();
		if (imageResource.getResourceType()!=1 && imageResource.getResourceType()!=2) {
			code="0001";
			msg="参数错误";
		}else {
			List<String> img = new ArrayList<>();
			try {
				GetField.getOTM(" APP通过客户id查询图片:",customer);
				List<ImageResource> imgs = customerService.getResourceTypeImg(customer.getId(),imageResource.getResourceType());
				for (ImageResource imageResource2 : imgs) {
					img.add(imageResource2.getResourceUrl());
				}
				map.put("result",img);
				code = "0000";
				msg = "获取成功";
			} catch (Exception e) {
				code="0001";
				msg=e.getMessage();
			}
		}
		map.put("code", code);
		map.put("msg",msg);
		return map;
	} 
	@RequestMapping(value="app/customer/queryList",name="通过用户id获取客户",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object>  queryCustomerList(String userId){
		Map<String, Object> map=new HashMap<>();
		Customer customer2 = null;
		try {
			customer2 =customerService.getCustomerByUserId(userId);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result",customer2);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
} 
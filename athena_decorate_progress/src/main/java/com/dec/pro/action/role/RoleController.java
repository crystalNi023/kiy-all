package com.dec.pro.action.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.role.Role;
import com.dec.pro.service.role.RoleService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class RoleController {
	@Autowired
	private RoleService roleService;
	private String code;
	private String msg;
	@RequestMapping(value="role/increase",name="添加角色",method={RequestMethod.POST,RequestMethod.GET})
	public String increaseRole(Role role,ModelMap modelMap){
		role.setId(UUid.getUUid());
		int count = 0;
		try {
			GetField.getOTM(" PC添加角色:",role);
			count = roleService.add(role);
			code = "00";
			msg = "成功";
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="role/removeById",name="移除角色",method={RequestMethod.POST,RequestMethod.GET})
	public String removeRoleById(String id,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC移除角色:",id);
			count = roleService.delete(id);
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="role/modifyById",name="修改角色",method={RequestMethod.POST,RequestMethod.GET})
	public String modifyRoleById(Role role,ModelMap modelMap){
		int count = 0;
		try {
			GetField.getOTM(" PC修改角色:",role);
			count = roleService.update(role);
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		modelMap.addAttribute("count",count);
		return "test/test";
	}
	@RequestMapping(value="role/queryById",name="通过id查询角色",method={RequestMethod.POST,RequestMethod.GET})
	public String queryRoleById(String id,ModelMap modelMap){
		Role role = null;
		try {
			GetField.getOTM(" PC通过id查询角色:",id);
			role = roleService.getOne(id);
		} catch (Exception e) {
			code = "01";
			msg=e.getMessage();
		}
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		String jRole=JSON.toJSONString(role);
		modelMap.addAttribute("jRole",jRole);
		return "test/test";
	}
	@RequestMapping(value="role/queryList",name="分页查询角色",method={RequestMethod.POST,RequestMethod.GET})
	public String queryRoleList(Role role,ModelMap modelMap){
		Page<Role> pages=null;
		try {
			pages = roleService.getRoleList(role);
		} catch (Exception e) {
			code="01";
			msg=e.getMessage();
		}
		String jRoles=JSON.toJSONString(pages);
		modelMap.addAttribute("jRoles",jRoles);
		modelMap.addAttribute("code",code);
		modelMap.addAttribute("msg",msg);
		return "test/test";
	}
	//权限
	@RequestMapping(value="role/queryPowerByRoleId",name="通过角色id查权限",method={RequestMethod.POST,RequestMethod.GET})
	public String queryPowerByRoleId(String roleId,ModelMap modelMap){
		
		return null;
	}
}

package com.dec.pro.service.role;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.dec.pro.entity.role.Power;
import com.dec.pro.entity.role.Role;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;

@Service
public class RoleService extends BaseService<Role> {
	/**
	 * 按条件分页查询
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Role> getRoleList(Role r) throws Exception {
		pages.setPageNo(r.getPageNo());
		pages.setPageSize(r.getPageSize());
		Page<Role> page;
		page = getPage(pages, GetField.getOTM(" 分页查询角色:",r));
		return page;
	}
	public Set<Power> getPowerByRoleId(String roleId){
		return null;
	}
}

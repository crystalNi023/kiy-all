package com.dec.pro.filter;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dec.pro.entity.role.Power;
import com.dec.pro.entity.role.Role;
import com.dec.pro.entity.user.User;
import com.dec.pro.service.user.UserService;

public class UserRealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;
	private Logger logger=Logger.getLogger(UserRealm.class); 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
		logger.info("进入授权.....");
		String username = (String) collection.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 根据用户名查询当前用户拥有的角色
		Set<Role> roles = null;
		try {
			roles = userService.getRoleByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<String> roleNames = new HashSet<String>();
		for (Role role : roles) {
			roleNames.add(role.getName());
			logger.info("role_name:"+role.getName());
		}
		
		// 将角色名称提供给info
		authorizationInfo.setRoles(roleNames);
		
		// 根据用户名查询当前用户权限
		Set<Power> powers = null;
		try {
			powers = userService.getPowerByUsername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> powerNames = new HashSet<String>();
		for (Power power : powers) {
			powerNames.add(power.getName());
			logger.info("power_name:"+power.getName());
		}
		
		// 将权限名提供给info
		authorizationInfo.setStringPermissions(powerNames);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		logger.info("进入身份认证.....");
		String username = (String) token.getPrincipal();
		User user = null;
		try {
			user = userService.getUserByUsername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			// 用户不存在抛出异常
			throw new UnknownAccountException();
		}
		
		if (user.isEnable() == false) {//0 不可用 1 可用
			// 用户被管理员锁定抛出异常
			throw new LockedAccountException();
		}
		
		logger.info("password:"+user.getPassword());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		return authenticationInfo;
	}
}

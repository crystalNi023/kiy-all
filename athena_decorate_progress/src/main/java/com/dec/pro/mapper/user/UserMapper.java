package com.dec.pro.mapper.user;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.role.Power;
import com.dec.pro.entity.role.Role;
import com.dec.pro.entity.user.User;
import com.dec.pro.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User>{
	public User getUserByUsername(@Param("username") String username);
	public Set<Role> selectRoleByUsername(@Param("username") String username);
	public Set<Power> selectPowerByUsername(@Param("username") String username);
}

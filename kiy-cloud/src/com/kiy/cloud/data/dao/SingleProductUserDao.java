package com.kiy.cloud.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiy.cloud.data.bean.SingleProductUserBean;

public interface SingleProductUserDao {
	
	/**
	 * 根据id获取一条单品记录
	 * @param id
	 * @return
	 */
	public SingleProductUserBean getOne(@Param("id") String id);

	/**
	 * 创建单品用户
	 * @param singleProductUserBean
	 * @return
	 * @throws Exception
	 */
	public boolean create(SingleProductUserBean singleProductUserBean);
	
	/**
	 * 关联雄迈账号
	 * @param singleProductUserBean
	 * @return
	 */
	public boolean relationXMDeviceAccount(SingleProductUserBean singleProductUserBean);
	
	/**
	 * 删除单品用户
	 * @param id
	 * @return
	 */
	public boolean delete(@Param("id") String id);
	
	/**
	 * 根据用户名获取单品用户信息
	 * @param username
	 * @return
	 */
	public SingleProductUserBean selectByUsername(@Param("username") String username);
	
	/**
	 * 根据userID获取关联用户列表
	 * @param userId
	 * @return
	 */
	public List<SingleProductUserBean> getListBySingleProductUserId(@Param("singleProductUserId") String singleProductUserId);
	
	/**
	 * 修改单品用户密码
	 * @return
	 */
	public boolean modifyPassword(String username,String password);
	
}

package com.kiy.cloud.data.bean;

public class SingleProductUserBean {
	/**
	 * 单品用户id
	 */
	private String id;
	
	/**
	 * 关联的单品用户id
	 */
	private String singleProductUserId;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 雄迈登陆账户
	 */
	private String xmDeviceAccount;
	
	/**
	 * 雄迈登陆密码
	 */
	private String xmDevicePassword;
	
	/**
	 * 是否可用
	 */
	private boolean enable = true;
	
	/**
	 * 用户类型（1：主账户  2：子账户）
	 */
	private int type;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private String created;
	
	/**
	 * 更新时间
	 */
	private String updated;
	
	/**
	 * 获取单品用户id
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置单品用户id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取关联单品用户id
	 * @return
	 */
	public String getSingleProductUserId() {
		return singleProductUserId;
	}
	
	/**
	 * 设置关联单品用户id
	 * @param userId
	 */
	public void setSingleProductUserId(String singleProductUserId) {
		this.singleProductUserId = singleProductUserId;
	}
	
	/**
	 * 获取用户名
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 设置用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 获取密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取雄迈登陆账户
	 * @return
	 */
	public String getXmDeviceAccount() {
		return xmDeviceAccount;
	}

	/**
	 * 设置雄迈登陆账户
	 * @param xmDeviceAccount
	 */
	public void setXmDeviceAccount(String xmDeviceAccount) {
		this.xmDeviceAccount = xmDeviceAccount;
	}

	/**
	 * 获取雄迈登陆密码
	 * @return
	 */
	public String getXmDevicePassword() {
		return xmDevicePassword;
	}

	/**
	 * 设置雄迈登陆密码
	 * @param xmDevicePassword
	 */
	public void setXmDevicePassword(String xmDevicePassword) {
		this.xmDevicePassword = xmDevicePassword;
	}

	/**
	 * 获取是否可用
	 * @return
	 */
	public boolean isEnable() {
		return enable;
	}
	
	/**
	 * 设置是否可用
	 * @param enable
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	/**
	 * 获取用户类型
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * 设置用户类型
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 获取备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 获取创建时间
	 * @return
	 */
	public String getCreated() {
		return created;
	}
	
	/**
	 * 设置创建时间
	 * @param created
	 */
	public void setCreated(String created) {
		this.created = created;
	}
	
	/**
	 * 获取更新时间
	 * @return
	 */
	public String getUpdated() {
		return updated;
	}
	
	/**
	 * 设置更新时间
	 * @param updated
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
}

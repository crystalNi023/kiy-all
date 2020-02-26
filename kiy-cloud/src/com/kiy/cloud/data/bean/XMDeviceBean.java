package com.kiy.cloud.data.bean;

import java.util.Date;


/**
 * 雄迈设备实体
 * @author Administrator
 *
 */
public class XMDeviceBean{

	/**
	 * 雄迈设备id
	 */
	private String id;
	/**
	 * 关联单品用户id
	 */
	private String singleProductUserId;
	/**
	 * 设备序列号
	 */
	private String deviceMac;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 登录名称
	 */
	private String loginName;
	/**
	 * 登陆密码
	 */
	private String loginPsw;
	/**
	 * 设备ip
	 */
	private String deviceIp;
	/**
	 * 设备在线状态
	 */
	private int state;
	/**
	 * 设备端口号
	 */
	private int nPort;
	/**
	 * 设备类型
	 */
	private int nType;
	/**
	 * 扩展
	 */
	private int nId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 最后更新时间
	 */
	private Date updated;
	/**
	 * 获取雄迈设备id
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置雄迈设备id
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
	 * 获取设备序列号
	 * @return
	 */
	public String getDeviceMac() {
		return deviceMac;
	}
	/**
	 * 设置设备序列号
	 * @param deviceMac
	 */
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	/**
	 * 获取设备名称
	 * @return
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置设备名称
	 * @param deviceName
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取登陆名称
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置登陆名称
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取登陆密码
	 * @return
	 */
	public String getLoginPsw() {
		return loginPsw;
	}
	/**
	 * 设置登陆密码
	 * @param loginPsw
	 */
	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}
	/**
	 * 获取设备ip
	 * @return
	 */
	public String getDeviceIp() {
		return deviceIp;
	}
	/**
	 * 设置设备ip
	 * @param deviceIp
	 */
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	/**
	 * 获取设备在线状态
	 * @return
	 */
	public int getState() {
		return state;
	}
	/**
	 * 设置设备在线状态
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * 获取端口号
	 * @return
	 */
	public int getNPort() {
		return nPort;
	}
	/**
	 * 设置端口号
	 * @param nPort
	 */
	public void setNPort(int nPort) {
		this.nPort = nPort;
	}
	/**
	 * 获取设备类型
	 * @return
	 */
	public int getNType() {
		return nType;
	}
	/**
	 * 设置设备类型
	 * @param nType
	 */
	public void setNType(int nType) {
		this.nType = nType;
	}
	/**
	 * 获取扩展
	 * @return
	 */
	public int getNId() {
		return nId;
	}
	/**
	 * 设置扩展
	 * @param nID
	 */
	public void setNId(int nId) {
		this.nId = nId;
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
	public Date getCreated() {
		return created;
	}
	/**
	 * 设置创建时间
	 * @param created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	/**
	 * 获取最后更新时间
	 * @return
	 */
	public Date getUpdated() {
		return updated;
	}
	/**
	 * 设置最后更新时间
	 * @param updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}

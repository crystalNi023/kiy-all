package com.kiy.cloud.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiy.cloud.data.bean.XMDeviceBean;

public interface XmDeviceDao {
	
	/**
	 * 新增雄迈设备
	 * @param xmDeviceBean
	 * @return
	 */
	public boolean create(XMDeviceBean xmDeviceBean);
	
	/**
	 * 删除雄迈设备
	 * @param deviceMac
	 * @return
	 */
	public boolean deleteByDeviceMac(@Param("deviceMac") String deviceMac);
	
	/**
	 * 修改设备姓名
	 * @param xmDeviceBean
	 * @return
	 */
	public boolean updateDeviceName(XMDeviceBean xmDeviceBean);
	
	/**
	 * 根据单品用户id获取关联摄像头列表
	 * @param singleProductUserId
	 * @return
	 */
	public List<XMDeviceBean> selectBySingleProductUserId(@Param("singleProductUserId") String singleProductUserId);
	
	/**
	 * 绑定雄迈设备和单品用户
	 * @param singleProductUserId
	 * @param xmDeviceId
	 * @return
	 */
	public boolean createSingleProductXMDevice(XMDeviceBean xmDeviceBean);
	
	/**
	 * 根据设备序列号获取雄迈设备
	 * @param deviceMac
	 * @return
	 */
	public XMDeviceBean getOneByDeviceMac(@Param("deviceMac") String deviceMac);
	
	/**
	 * 解除雄迈设备和单品用户绑定关系
	 * @param singleProductUserId
	 */
	public boolean deleteSingleProductXMDevice(XMDeviceBean xmDeviceBean);
	
}

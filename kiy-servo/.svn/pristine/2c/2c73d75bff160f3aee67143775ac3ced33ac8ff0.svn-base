package com.kiy.servo.messageque;

import com.kiy.common.Device;
import com.kiy.servo.driver.DriverAdpater;

/**
 * 设备请求消息
 * @author HLX Tel:18996470535 
 * @date 2018年4月17日 
 * Copyright:Copyright(c) 2018
 */
public class MessageDevice {
	
	
	/**
	 * 
	 * @param adpater 通讯适配器
	 * @param device	设备
	 * @param isClient	是否为客户端请求
	 * @param isWriteDevice 是否为控制设备
	 */
	public MessageDevice(DriverAdpater adpater,Device device,boolean isClient,boolean isWriteDevice){
		this.adpater = adpater;
		this.device = device;
		this.isClient = isClient;
		this.isWriteDevice = isWriteDevice;
	}
	
	/**
	 * 通讯适配器
	 */
	private DriverAdpater adpater;
	
	/**
	 * 设备 目标设备
	 */
	private Device device;
	
	/**
	 * 是否为客户端请求 true是/false不是
	 */
	private boolean isClient;
	
	/**
	 * 是否为控制设备请求
	 */
	private boolean isWriteDevice;
	
	/**
	 * 执行设备请求指令
	 * @return 携带了请求成功后的设备数据
	 */
	public Device execute(){
		if (isWriteDevice) {
			adpater.setStatus(device);
		}else {
			adpater.getStatus(device);
		}
		return device;
	}

	/**
	 * 获取是否为客户端请求
	 * @return
	 */
	public boolean isClient() {
		return isClient;
	}


	/**
	 * 	设置为是否为客户端请求
	 */
	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}


	/**
	 * 获取是否为控制设备请求
	 * @return
	 */
	public boolean isWriteDevice() {
		return isWriteDevice;
	}

	/**
	 * 设置为是否为控制设备请求
	 * @param isWriteDevice
	 */
	public void setWriteDevice(boolean isWriteDevice) {
		this.isWriteDevice = isWriteDevice;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		MessageDevice mDevice  = (MessageDevice)obj;
		if (mDevice.isWriteDevice)
			return false;
		         
		
		
		if(this.device.getId().equals(mDevice.device.getId())){
			return true;
		}
		return false;
	}

	
}

package com.kiy.controller;

import java.util.HashSet;
import java.util.Set;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;

/**
 * 
 * @author HLX 2017.5.25
 *
 */
public class SearchDevice {
	private Servo servo;
	private Set<Device> devices;
	//private Set<Zone> zones;

	//private Set<Device> searchResult;
	

	/**
	 * 用于通过字符串搜索设备的类
	 * @param s2 伺服器
	 */
	public SearchDevice(Servo s2) {
		this.servo = s2;
		if (servo != null) {
			devices = servo.getDevices();
			//zones = servo.getZones();
		}
	}

	/**
	 * Search equipment
	 * 
	 * @param name
	 */
	private Set<Device> searchForDevice(String searchInfo) {
		Set<Device> searchResult = new HashSet<Device>();
		if (Tool.isEmpty(searchInfo)) {
			return searchResult;
		}

		
		if (devices != null && !devices.isEmpty()) {
			/**
			 * Search device by device name
			 */
			for (Device device : devices) {
				if (device.getName().contains(searchInfo)) {
					searchResult.add(device);
				}
			}
			
			/**
			 * Search device by device install address
			 */
			for (Device device : devices) {
				if (device.getAddress().contains(searchInfo)) {
					searchResult.add(device);
				}
			}
			
			
		}
		
		return searchResult;
		
	}

	

	
	/**
	 * 搜索设备 没有搜索结果返回为空
	 * @return
	 */
	public Set<Device> search(String searchInfo) {
		
		return searchForDevice(searchInfo);
	}

}

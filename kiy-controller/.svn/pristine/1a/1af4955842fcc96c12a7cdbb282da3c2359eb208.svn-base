package com.kiy.controller;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Vendor;

/**
 * 设备筛选
 * 
 * @author HLX
 *
 */
public class DeviceFiliter {
	private Menu menuDeviceVendor;
	private Menu menuDeviceType;
	private Menu menuDeviceLink;

	private Servo servo;

	/*
	 * 筛选方式，先筛选厂商，其次筛选类型，最后筛选连接方式
	 */
	private Set<Device> deviceForVendor;
	private Set<Device> deviceForKind;
	private Set<Device> devices;

	private Set<Device> allDevices;

	/**
	 * 
	 * @param menuDeviceVendor Menu 设备厂商筛选菜单
	 * @param menuDeviceType Menu 设备类型筛选菜单
	 * @param menuDeviceLink Menu 设备连接方式筛选菜单
	 */
	public DeviceFiliter(Menu deviceVendor, Menu deviceType, Menu deviceLink, Servo s) {
		menuDeviceVendor = deviceVendor;
		menuDeviceType = deviceType;
		menuDeviceLink = deviceLink;
		servo = s;

		if (servo != null) {
			allDevices = s.getDevices();
		}
	}

	public Set<Device> getFilterDevices() {

		if (allDevices == null) {
			return null;
		}

		deviceForVendor = new HashSet<Device>();
		deviceForKind = new HashSet<Device>();
		devices = new HashSet<Device>();

		if (menuDeviceVendor != null && menuDeviceVendor.getItems() != null) {
			MenuItem[] items = menuDeviceVendor.getItems();
			if (items.length > 0) {
				for (MenuItem item : items) {
					if (item.getSelection()) {
						Object data = item.getData();
						if (data != null && data instanceof Vendor) {
							Vendor v = (Vendor) data;
							for (Device device : allDevices) {
								if (device.getVendor() == v) {
									deviceForVendor.add(device);
								}
							}
						}
					}
				}
			}
		}

		if (menuDeviceType != null && menuDeviceType.getItems() != null) {
			if (deviceForVendor == null) {
				return null;
			}
			MenuItem[] items = menuDeviceType.getItems();
			if (items.length > 0) {
				for (MenuItem item : items) {
					if (item.getSelection()) {
						Object data = item.getData();
						if (data != null && data instanceof Kind) {
							Kind kind = (Kind) data;
							for (Device device : deviceForVendor) {
								if (device.getKind() == kind) {
									deviceForKind.add(device);
								}
							}
						}
					}
				}
			}
		}

		if (menuDeviceLink != null && menuDeviceLink.getItems() != null) {
			if (deviceForKind == null) {
				return null;
			}
			MenuItem[] items = menuDeviceLink.getItems();
			if (items.length > 0) {
				for (MenuItem item : items) {
					if (item.getSelection()) {
						Object data = item.getData();
						if (data != null && data instanceof Link) {
							Link link = (Link) data;
							for (Device device : deviceForKind) {
								if (device.getLink() == link) {
									devices.add(device);
								}
							}
						}
					}
				}
			}
		}

		return devices;

	}
}

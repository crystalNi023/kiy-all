package com.kiy.controller;

import java.util.ArrayList;
import java.util.List;

import com.kiy.common.Device;
import com.kiy.resources.Lang;

/**
 * 打开设备历史记录
 * @author HLX
 *
 */
public class AHistoryDeviceStatus extends A<FMain> {
	
	private List<Device> openWindow;

	public AHistoryDeviceStatus(FMain m) {
		super(m);
		openWindow = new ArrayList<>();
	}

	@Override
	protected void run() {
		
		if (main.getSelectionDevice() != null) {
			Device d = main.getSelectionDevice();
			for (Device opneDevice : openWindow) {
				if (opneDevice == d) {
					return;
				}
			}
			openWindow.add(d);
			FHistoryDeviceStatus f = new FHistoryDeviceStatus(main.getShell());
			main.getServerManager().add(f, d.getServo());
			f.open(d, main.getServerManager());
			openWindow.remove(d);
		} else {
			F.mbInformation(main.getShell(), Lang.getString("AHistoryDeviceStatus.MessageBoxDeviceHistoryStatusTitle.text"), Lang.getString("AHistoryDeviceStatus.MessageBoxDeviceHistoryStatusContent.text"));
		}
	}

}

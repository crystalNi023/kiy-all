package com.kiy.controller;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Zone;
import com.kiy.resources.Lang;

/**
 * 用于查看属性动作
 * 
 * @author hlx
 *
 */
public class ACheckProperty extends A<FMain> {

	/**
	 * 
	 * @param m FMain
	 */
	public ACheckProperty(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getSelectionServo();
		Device device = main.getSelectionDevice();
		Zone zone = main.getSelectionZone();

		// 判断选择的伺服器
		if (servo != null) {
			// 打开伺服器属性窗口
			FServoDetails f = new FServoDetails(main.getShell(), servo);
			f.open();
		} else if (device != null) {
			// 打开设备属性窗口
			FDeviceDetails fDeviceDetails = new FDeviceDetails(main.getShell());
			fDeviceDetails.open(device, null);
		} else if (zone != null) {
			FZoneDetails f = new FZoneDetails(main.getShell(), zone);
			f.open();
		} else {
			F.mbInformation(main.getShell(), Lang.getString("ACheckProperty.MessageBoxCheckPropertyTitle.text"),Lang.getString("ACheckProperty.MessageBoxCheckPropertyContent.text"));
		}
	}

}

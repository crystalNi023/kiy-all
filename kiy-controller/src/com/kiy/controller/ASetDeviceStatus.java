package com.kiy.controller;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.resources.Lang;

/**
 * 设置设备状态
 * 
 * @author hlx
 *
 */
public class ASetDeviceStatus extends A<FMain> {

	public ASetDeviceStatus(FMain m) {
		super(m);
	}

	@Override
	protected void run() {

		Servo servo = main.getCurrentServo();
		Device d = main.getSelectionDevice();

		if (d == null) {
			F.mbInformation(main.getShell(), Lang.getString("ASetStatus.MessageBoxGetStatusTitle.text"), Lang.getString("AGetStatus.MessageBoxGetStatusContent.text"));
			return;
		}

		if (servo == null) {
			throw new NullPointerException();
		}

		ServoManager serverManager = main.getServerManager();
		FDeviceStatus f = new FDeviceStatus(main.getShell());
		serverManager.add(f, servo);
		f.open(d, servo);
		serverManager.off(f);

	}

}

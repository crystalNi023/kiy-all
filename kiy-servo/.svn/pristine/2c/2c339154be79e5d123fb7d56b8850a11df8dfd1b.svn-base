package com.kiy.servo.driver.rs485;

import com.kiy.common.Device;
import com.kiy.common.Types.Status;
import com.kiy.servo.Config;
import com.kiy.servo.driver.SerialPortReceiver;

public class VolexSerialPort extends SerialPortReceiver{
	// 读设备
	public static final int READ = 0;
	
	// 控制设备
	public static final int WRITE = 1;

		public VolexSerialPort(Device d) {
			super(d);
			coder = new VolexCoder();
			data_bits = 8;
			stop_bits = 1;
			parity_check = 0;
		}

		@Override
		public void getStatus(Device d) {
			if (device.equals(d)) {
				d.setStatus(isActive() ? Status.NONE : Status.OFFLINE);
				return;
			}
			if (System.currentTimeMillis() - d.getTickStatus() > Config.DRIVER_INTERVAL * 1000) {
				send(d, VolexCoder.STATUS_0x02, READ);
				d.setTickStatus(System.currentTimeMillis());
			}

		}

		@Override
		public void setStatus(Device d) {
			if (device.equals(d)) {
				d.setStatus(isActive() ? Status.NONE : Status.OFFLINE);
				return;
			}
			send(d, VolexCoder.STATUS_0x02, WRITE);
			d.setTickStatus(System.currentTimeMillis());

		}

		@Override
		public void getConfig(Device d) {

		}

		@Override
		public void setConfig(Device d) {

		}

		@Override
		public void initialize() {
			// TODO Auto-generated method stub

		}

	}

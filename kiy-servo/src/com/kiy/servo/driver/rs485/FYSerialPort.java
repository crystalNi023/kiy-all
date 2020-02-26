package com.kiy.servo.driver.rs485;

import com.kiy.common.Device;
import com.kiy.servo.driver.SerialPortReceiver;

public class FYSerialPort extends SerialPortReceiver {

	public FYSerialPort(Device d) {
		super(d);
		coder = new FYCoder();
		data_bits = 8;
		stop_bits = 1;
		parity_check = 0;
	}

	@Override
	public void getStatus(Device d) {
		
	}

	@Override
	public void setStatus(Device d) {

	}

	@Override
	public void getConfig(Device d) {

	}

	@Override
	public void setConfig(Device d) {

	}

	@Override
	public void initialize() {

	}

}

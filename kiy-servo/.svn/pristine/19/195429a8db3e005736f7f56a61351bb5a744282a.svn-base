/**
 * 2017年6月18日
 */
package com.kiy.servo.driver.rs485;

import com.kiy.common.Device;
import com.kiy.common.Types.Status;
import com.kiy.servo.Config;
import com.kiy.servo.driver.SerialPortAdpater;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class HBSerialPort extends SerialPortAdpater {

	public HBSerialPort(Device d) {
		super(d);
		coder = new HBCoder();
		data_bits = 8;
		stop_bits = 1;
		parity_check = 2; //偶校验
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void getStatus(Device d) {
		if (device.equals(d)) {
			d.setStatus(isActive() ? Status.NONE : Status.OFFLINE);
			return;
		}
		
		if (System.currentTimeMillis() - d.getTickStatus() > Config.DRIVER_INTERVAL * 1000) {
			send(d, HBCoder.STATUS, 0);
			d.setTickStatus(System.currentTimeMillis());
		}
	}

	@Override
	public synchronized void setStatus(Device d) {
	
	}

	@Override
	public synchronized void getConfig(Device d) {
	}

	@Override
	public synchronized void setConfig(Device d) {
	}
}
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
public final class LRSerialPort extends SerialPortAdpater {

	public LRSerialPort(Device d) {
		super(d);
		coder = new LRCoder();
		data_bits = 8;
		stop_bits = 2;
		parity_check = 0;// NONE
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
			send(d, LRCoder.STATUS, 0);
			send(d, LRCoder.DEGREE, 0);
			d.setTickStatus(System.currentTimeMillis());
		}
	}

	@Override
	public synchronized void setStatus(Device d) {
		if (device.equals(d))
			return;

		send(d, LRCoder.CONTROL, 0);
		d.setTickStatus(System.currentTimeMillis());
	}

	@Override
	public synchronized void getConfig(Device d) {
	}

	@Override
	public synchronized void setConfig(Device d) {
	}
}
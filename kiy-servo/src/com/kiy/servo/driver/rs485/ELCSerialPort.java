/**
 * 2017年6月19日
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
public final class ELCSerialPort extends SerialPortAdpater {

	public ELCSerialPort(Device d) {
		super(d);
		coder = new ELCCoder();
		data_bits = 8;
		stop_bits = 1;
		parity_check = 2;// EVEN
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

		if (System.currentTimeMillis() - device.getTickStatus() > Config.DRIVER_INTERVAL * 1000) {
			send(d, ELCCoder.DEGREE, 0);
			// send(d, ELCCoder.STATUS_RELAY, 0);
			// send(d, ELCCoder.STATUS_SWITCH, 0);
			d.setTickStatus(System.currentTimeMillis());
		}
	}

	@Override
	public synchronized void setStatus(Device d) {
		if (device.equals(d))
			return;

		send(d, ELCCoder.CONTROL_RELAYS, 0);
		d.setTickStatus(System.currentTimeMillis());
	}

	@Override
	public synchronized void getConfig(Device d) {
	}

	@Override
	public synchronized void setConfig(Device d) {
	}
}
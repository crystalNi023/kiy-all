/**
  * 2017年6月20日
 */
package com.kiy.servo.driver.tcp;

import com.kiy.common.Device;
import com.kiy.servo.driver.Coder;
import com.kiy.servo.driver.DriverAdpater;

/**
 * 
 *
 * @author Crystal Ni
 * @date 2019年5月27日
 *
 */
public final class TCPDriver extends DriverAdpater {

	protected Coder coder;
	public TCPDriver(Device d) {
		super(d);
		coder = new TCPCoder();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void getStatus(Device d) {
	}

	@Override
	public void setStatus(Device d) {
		if (device.equals(d))
			return;

		send(d, (byte)1, 1);
		d.setTickStatus(System.currentTimeMillis());
	}

	@Override
	public void getConfig(Device d) {
	}

	@Override
	public void setConfig(Device d) {
	}
	
	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void send(Device device, byte code, int tag) {
		coder.encode(device, null, code, tag);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
}
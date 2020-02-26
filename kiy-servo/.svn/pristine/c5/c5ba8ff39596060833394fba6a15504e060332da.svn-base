/**
  * 2017年6月20日
 */
package com.kiy.servo.driver.ks;

import com.kiy.common.Device;
import com.kiy.common.Types.Kind;
import com.kiy.servo.Config;
import com.kiy.servo.driver.SocketAdpater;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class KSEthernet extends SocketAdpater {

	public KSEthernet(Device d) {
		super(d);
		coder = new KSCoder();
	}

	@Override
	public void initialize() {
	}

	@Override
	public void start() {
		super.start();
		send(device, KSCoder.GET_DATETIME, 0);
		// Log.debug(this.isActive() + ":" +device.getTickStatus());
	}

	@Override
	public void getStatus(Device d) {
		if (System.currentTimeMillis() - d.getTickStatus() > Config.DRIVER_INTERVAL * 1000) {
			if (device.equals(d)) {
				send(d, KSCoder.GET_DATETIME, 0);
			} else {
				send(d, KSCoder.GET_METER_STATUS, 0);
				if (d.getKind() == Kind.SWITCH || d.getKind() == Kind.SENSOR_MOISTURE_TEMPERATURE || d.getKind() == Kind.SOIL_SENSOR_MOISTURE_TEMPERATURE)
					send(d, KSCoder.GET_METER_STATUS, 1);
			}
			d.setTickStatus(System.currentTimeMillis());
		}
	}

	@Override
	public void setStatus(Device d) {
		if (device.equals(d))
			return;

		send(d, KSCoder.SET_METER_STATUS, 0);
		d.setTickStatus(System.currentTimeMillis());
	}

	@Override
	public void getConfig(Device d) {
	}

	@Override
	public void setConfig(Device d) {
	}
}
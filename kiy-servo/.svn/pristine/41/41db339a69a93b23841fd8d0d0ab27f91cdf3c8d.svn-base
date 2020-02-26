/**
 * 2017年6月20日
 */
package com.kiy.servo.driver.ks;

import com.kiy.common.Device;
import com.kiy.common.Types.Kind;
import com.kiy.servo.Config;
import com.kiy.servo.driver.SerialPortAdpater;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class KSSerialPort extends SerialPortAdpater {

	public KSSerialPort(Device d) {
		super(d);
		coder = new KSCoder();
		data_bits = 8;
		stop_bits = 1;
		parity_check = 0;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void getStatus(Device d) {
		if (System.currentTimeMillis() - d.getTickStatus() > Config.DRIVER_INTERVAL * 1000) {
			if (device.equals(d)) {
				// 凯星根设备(通信适配器)应当是集中器,集中器通过获取时间检测是否在线
				send(d, KSCoder.GET_DATETIME, 0);
			} else {
				send(d, KSCoder.GET_METER_STATUS, 0);
				// 判断是否是二合一设备(设备中有两个通信地址用于分别获取不同的数据)
				if (d.getKind() == Kind.SWITCH || d.getKind() == Kind.SENSOR_MOISTURE_TEMPERATURE||d.getKind()==Kind.SOIL_SENSOR_MOISTURE_TEMPERATURE)
					send(d, KSCoder.GET_METER_STATUS, 1);
			}
			d.setTickStatus(System.currentTimeMillis());
		}
	}

	@Override
	public synchronized void setStatus(Device d) {
		if (device.equals(d))
			return;

		send(d, KSCoder.SET_METER_STATUS, 0);
		d.setTickStatus(System.currentTimeMillis());
	}

	@Override
	public synchronized void getConfig(Device d) {
	}

	@Override
	public synchronized void setConfig(Device d) {
	}
}
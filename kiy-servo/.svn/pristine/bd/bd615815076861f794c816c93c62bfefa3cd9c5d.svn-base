/**
 * 2017年6月26日
 */
package com.kiy.servo.test;

import com.kiy.common.Feature;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.Adpater;
import com.kiy.common.devices.ELCPowerMeter;
import com.kiy.servo.driver.rs485.ELCSerialPort;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestELCSerialPort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Adpater adpater = new Adpater(Vendor.ELECALL);
		adpater.setSerialPort("COM4");
		adpater.setSerialBaudRate(9600);

		ELCPowerMeter device = new ELCPowerMeter();
		device.setNumber("1");
		device.setPhaseCheck(4);

		ELCSerialPort com = new ELCSerialPort(adpater);
		com.initialize();
		com.start();
		com.getStatus(device);
		System.out.println(device.getStatus());
		for (Feature f : device.getFeatures()) {
			System.out.println(f.getName() + f.getText());
		}
		com.stop();
	}

}

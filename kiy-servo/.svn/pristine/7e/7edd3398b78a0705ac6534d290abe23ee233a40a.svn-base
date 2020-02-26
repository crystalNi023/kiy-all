/**
 * 
 */
package com.kiy.servo.test;

import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.Adpater;
import com.kiy.common.devices.LRTransformerTemperature;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.driver.rs485.LRSerialPort;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 * @date 2016年12月28日
 */
public class TestLRSerialPort {

	public static void main(String[] args) throws Exception {
		System.out.println("START TEST");

		Servo servo = new Servo();
		Adpater adpater = new Adpater(Vendor.LINGRUI);
		adpater.newId();
		adpater.setSerialPort("COM4");
		adpater.setSerialBaudRate(9600);
		servo.add(adpater);

		LRTransformerTemperature device = new LRTransformerTemperature();
		device.newId();
		device.setNumber("1");
		device.setRelayId(adpater.getId());
		device.setPhaseCheck(3);
		device.setPhasePower(3);
		servo.add(device);

		Config.DRIVER_INTERVAL = 1;
		Executor.initialize();
		LRSerialPort sp = new LRSerialPort(adpater);
		sp.initialize();
		sp.start();

		int count = 100;
		Long time = System.currentTimeMillis();
		while (count > 0) {
			sp.getStatus(device);
			count--;
			System.out.println("COUNT:" + count + " STATUS:" + device.getStatus());
			Feature[] fs = device.getFeatures();
			if (fs != null)
				for (int index = 0; index < fs.length; index++) {
					System.out.print(device.getFeature(index).getName());
					System.out.println(device.getFeature(index).getText());
				}
		}
		time = System.currentTimeMillis() - time;

		System.out.println("TIME:" + time);
		sp.stop();
	}
}
package com.kiy.servo.test;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.Adpater;
import com.kiy.servo.Executor;
import com.kiy.servo.driver.rs485.RFSerialPort;

public class TestRFSerialPort {

	public static void main(String[] args) throws InterruptedException {
		Servo servo = new Servo();
		Adpater adpater = new Adpater(Vendor.RFIN);
		adpater.newId();
		adpater.setSerialPort("COM6");
		adpater.setSerialBaudRate(38400);
		servo.add(adpater);
		
//		LRTransformerTemperature device = new LRTransformerTemperature();
//		device.newId();
//		device.setRelayId(adpater.getId());
//		device.setNumber("1");
//		servo.add(device);
		
		Executor.initialize();
		RFSerialPort sp = new RFSerialPort(adpater);
		sp.initialize();
		sp.start();

		while (true) {
			Thread.sleep(1000);
			System.out.print("...");
		}

		// sp.stop();
	}

	public static void print(Device device) {
		Feature[] fs = device.getFeatures();
		for (int index = 0; index < fs.length; index++) {
			System.out.print(device.getFeature(index).getName());
			System.out.println(device.getFeature(index).getText());
		}
	}
}
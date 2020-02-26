package com.kiy.servo.test;

import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.Adpater;
import com.kiy.common.devices.MudbusSwitchMB0020;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.driver.rs485.MudbusSerialPort;


/**
 * 
 * @author HLX Tel:18996470535
 * @date 2018年4月12日 Copyright:Copyright(c) 2018
 */
public class TestMudbusSerialPort {

	public static void main(String[] args) throws Exception {
		System.out.println("START TEST");

		Servo servo = new Servo();
		Adpater adpater = new Adpater(Vendor.MUDBUS);
		adpater.newId();
		adpater.setSerialPort("COM3");
		adpater.setSerialBaudRate(9600);
		servo.add(adpater);
		
		/**
		 * MB0011
		 */
		MudbusSwitchMB0020 device = new MudbusSwitchMB0020();
		device.newId();
		device.setNumber("254");
		device.setRelayId(adpater.getId());
		servo.add(device);
		device.setFeed(false);

		Config.DRIVER_INTERVAL = 1;
		Executor.initialize();
		MudbusSerialPort sp = new MudbusSerialPort(adpater);
		sp.initialize();
		sp.start();

		// int count = 100;
		Long time = System.currentTimeMillis();
		// while (count > 0) {
		sp.setStatus(device);
		sp.getStatus(device);
	
		// count--;
		// System.out.println("COUNT:" + count + " STATUS:" +
		// device.getStatus());
		Feature[] fs = device.getFeatures();
		if (fs != null)
			for (int index = 0; index < fs.length; index++) {
				System.out.print(device.getFeature(index).getName()+" ");
				System.out.println(device.getFeature(index).getText());
			}
		// }
		time = System.currentTimeMillis() - time;

		System.out.println("TIME:" + time);
		sp.stop();
	}
}
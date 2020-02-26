/**
 * 2017骞�6鏈�19鏃�
 */
package com.kiy.servo.test;

import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.Adpater;
import com.kiy.common.devices.HBElectricityMeter3;
//import com.kiy.common.devices.JXIluminaceSensor;
//import com.kiy.common.devices.JXMoistureTemperature;
//import com.kiy.common.devices.LRTransformerTemperature;
import com.kiy.servo.Executor;
import com.kiy.servo.driver.rs485.HBSerialPort;
//import com.kiy.servo.driver.rs485.JXSerialPort;
//import com.kiy.servo.driver.rs485.LRSerialPort;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestRS485 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Servo servo = new Servo();
//		Adpater adpater = new Adpater(Vendor.YIYOU);
//		Adpater adpater = new Adpater(Vendor.LINGRUI);
//		Adpater adpater = new Adpater(Vendor.ELECALL);
		
		Adpater adpater = new Adpater(Vendor.HUABANG); 
		adpater.newId();
		adpater.setSerialPort("COM3");
		adpater.setSerialBaudRate(1200);
		servo.add(adpater);
		
//		ELCPowerMeter device  = new ELCPowerMeter();
//		device.newId();
//		device.setNumber("1");
//		device.setPhaseCheck(4);
//		device.setSerialBaudRate(9600);
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		LRTransformerTemperature device = new LRTransformerTemperature();
//		device.newId();
//		device.setNumber("1");
//		device.setRelayId(adpater.getId());
//		device.setPhaseCheck(3);
//		device.setPhasePower(3);
//		servo.add(device);
		
//		JXMoistureTemperature device = new JXMoistureTemperature();
//		device.newId();
//		device.setNumber("1");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
		HBElectricityMeter3 device  = new HBElectricityMeter3();
		device.newId();
//		device.setNumber("286151500102");
		device.setNumber("020150516128");
//		device.setNumber("222222222222");
		device.setRelayId(adpater.getId());
		servo.add(device);
		
//		ELCPowerMeter device  = new ELCPowerMeter();
//		device.newId();
//		device.setNumber("1");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//
//		YYSmokeSensor device = new YYSmokeSensor();
//		device.newId();
//		device.setNumber("3");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYTemperatureMoistureTransmitter device = new YYTemperatureMoistureTransmitter();
//		device.newId();
//		device.setNumber("2");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYPowerSensor device = new YYPowerSensor();
//		device.newId();
//		device.setNumber("1");
//		device.setPhaseCheck(3);
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYAudibleAndVisualAlarm device = new YYAudibleAndVisualAlarm();
//		device.newId();
//		device.setNumber("1");
//		device.setBeep(true);
//		device.setLight(false);
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYSmokeSensor device = new YYSmokeSensor();
//		device.newId();
//		device.setNumber("1");
//		device.setSerialBaudRate(4800);
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYDoorSensor device = new YYDoorSensor();
//		device.newId();
//		device.setNumber("1");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYTemperatureMoistureTransmitter device = new YYTemperatureMoistureTransmitter();
//		device.newId();
//		device.setSerialBaudRate(9600);
//		device.setNumber("2");
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
//		YYPowerSensor device = new YYPowerSensor();
//		device.newId();
//		device.setNumber("1");
//		device.setPhaseCheck(3);
//		device.setRelayId(adpater.getId());
//		servo.add(device);
		
		

		Executor.initialize();
//		YYSerialPort sp = new YYSerialPort(adpater);
//		LRSerialPort sp = new LRSerialPort(adpater);
//		ELCSerialPort sp = new ELCSerialPort(adpater);
//		JXSerialPort sp = new JXSerialPort(adpater);
		HBSerialPort sp = new HBSerialPort(adpater);
		sp.initialize();
		sp.start();
		Long time = System.currentTimeMillis();
//		while (count > 0) {
			sp.getStatus(device);
//			count--;
//			System.out.println("COUNT:" + count);
			Feature[] fs = device.getFeatures();
			if (fs != null)
				for (int index = 0; index < fs.length; index++) {
					System.out.println(device.getFeature(index).getText());
				}
//		}
//		sp.setStatus(device);
		time = System.currentTimeMillis() - time;

		System.out.println(time);
		sp.stop();

	}

}

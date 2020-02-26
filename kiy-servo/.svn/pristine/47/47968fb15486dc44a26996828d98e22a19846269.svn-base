/**
 * 
 */
package com.kiy.servo.test;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.devices.KSAir;
import com.kiy.common.devices.KSConcentrator;
import com.kiy.common.devices.KSElectricityMeter1;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.driver.ks.KSSerialPort;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 * @date 2016年12月28日
 */
public class TestKSSerialPort {

	public static void main(String[] args) throws Exception {
		System.out.println("START TEST");

		Servo servo = new Servo();
		KSConcentrator adpater = new KSConcentrator();
		adpater.newId();
		adpater.setSerialPort("COM1");
		adpater.setSerialBaudRate(19200);
		servo.add(adpater);
		
		KSAir device2 = new KSAir();
		device2.newId();
		device2.setNumber("61440"); /*61440 第一个按钮地址*/
		device2.setRelayId(adpater.getId());
		servo.add(device2);
		
//		device2.setControl(AirButton.OFF.getValue());
//		device2.setLearn(true);
		/**
		 * 61440 关 61440 开
		 */
		
		KSElectricityMeter1 device1 = new KSElectricityMeter1();
		device1.newId();
		device1.setNumber("61440"); /*61440 第一个按钮地址*/
		device1.setRelayId(adpater.getId());
		device1.setPhaseCheck(2);
		device1.setPhasePower(2);
		servo.add(device1);

//		KSTemperatureSensor device2 = new KSTemperatureSensor();
//		device2.newId();
//		device2.setNumber("8800");
//		device2.setRelayId(adpater.getId());
//		servo.add(device2);

		Config.DRIVER_INTERVAL = 6;
		Executor.initialize();
		KSSerialPort sp = new KSSerialPort(adpater);
		sp.initialize();
		sp.start();

		/**
		 * 空调控制器学习时，首先收到指令后灯会常亮，这是需要用遥控器对空调控制器按下相应的按键，灯灭后学习成功
		 */
		
//		sp.getStatus(device1);
//		print(device1);

		//sp.getStatus(device2);
		//print(device2);

		// FEED
		device1.getFeature(1).setValue(0);  /*0学习,1控制*/
		// BEEP
		device1.getFeature(2).setValue(0);
		System.out.println(device1.isFeed());
		System.out.println(device1.isBuzzer());
		
		sp.setStatus(device2); /* 发送   空调学习*/
		
//		sp.setStatus(device1); /* 发送 空调(电表学习)*/
		
//
//		// FEED
//		device1.getFeature(1).setValue(0);
//		// BEEP
//		device1.getFeature(2).setValue(0);
//		sp.setStatus(device1);
//		print(device1);

		sp.stop();
	}

	public static void print(Device device) {
		Feature[] fs = device.getFeatures();
		for (int index = 0; index < fs.length; index++) {
			System.out.print(device.getFeature(index).getName());
			System.out.println(device.getFeature(index).getText());
		}
	}
}
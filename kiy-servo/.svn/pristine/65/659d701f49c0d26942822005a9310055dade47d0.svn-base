/**
 * 2017年2月16日
 */
package com.kiy.servo.test;

import java.util.Date;
import java.util.UUID;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.KSTemperatureSensor;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestDeviceMemory {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		long T = System.currentTimeMillis();

		Servo servo = new Servo();

		for (int index = 0; index < 100; index++) {
			Device device = new KSTemperatureSensor();
			device.setId(UUID.randomUUID().toString());
			// device.setKind(Kind.SENEOR_TEMPERATURE);
			// device.setLink(Link.ETHERNET);
			device.setName("name" + index);
			device.setNetworkIp("255.255.255.255");
			device.setNetworkPort(1234);
			device.setNumber(Integer.toString(index));
			device.setPassword("1234567890");
			device.setRemark("remark");
			device.setSerialPort("COM4");
			device.setSerialBaudRate(19200);
			device.setVendor(Vendor.KAISTAR);
			device.setCreated(new Date());
			device.setUpdated(new Date());
			servo.add(device);
		}

		System.out.println("C100:" + (System.currentTimeMillis() - T));
		T = System.currentTimeMillis();

		for (int index = 0; index < 100000; index++) {
			Device device = new KSTemperatureSensor();
			device.setId(UUID.randomUUID().toString());
			// device.setKind(Kind.SENEOR_MOISTURE);
			// device.setLink(Link.ETHERNET);
			device.setName("name" + index);
			device.setNetworkIp("255.255.255.255");
			device.setNetworkPort(1234);
			device.setNumber(Integer.toString(index));
			device.setPassword("1234567890");
			device.setRemark("remark");
			device.setSerialPort("COM4");
			device.setSerialBaudRate(19200);
			device.setVendor(Vendor.KAISTAR);
			device.setCreated(new Date());
			device.setUpdated(new Date());
			servo.add(device);
		}

		System.out.println("C10W:" + (System.currentTimeMillis() - T));
		System.out.println("COUNT:" + servo.getUnitCount());

		Thread.currentThread().join();
	}

	static {
		System.out.println("CLASS LOAD");
	}
}

/**
 * 2017年2月19日
 */
package com.kiy.servo.test;

import com.kiy.servo.driver.rs485.CRC16M;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Test {

	public S s = new Sb();

	public class S {
		protected int value;

		public S() {
			value = 0;
		}

		public int getValue() {
			return value;
		}
	}

	public class Sb extends S {
		public Sb() {
			value = 1;
		}
	}

	public static void main(String[] args) {
		Test t = new Test();
		System.out.println(t.s.getValue());
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("222");
		String toSend="55000003041E";
		//crc16校验码测试
		byte[] bytes = CRC16M.getSendBuf(toSend);
		System.out.println(CRC16M.getBufHexStr(bytes));
		
	}
}
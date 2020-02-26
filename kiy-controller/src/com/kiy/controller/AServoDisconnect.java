/**
 * 2017年1月17日
 */
package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;

/**
 * 断开伺服器连接
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class AServoDisconnect extends A<FMain> {

	public AServoDisconnect(FMain m) {
		super(m);
	}

	@Override
	protected void run() {

		Servo servo = main.getCurrentServo();
		if (servo != null) {
			CtrClient client = servo.getClient();
			if (client == null)
				return;

			client.stop();
			client.close();
			main.getTreeZoneView().update(servo);
			main.getTreeRelayView().update(servo);
			main.status();
		}

	}
}
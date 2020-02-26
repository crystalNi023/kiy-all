/**
 * 2017年1月17日
 */
package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;

/**
 * 伺服器连接
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class AServoConnect extends A<FMain> {

	public AServoConnect(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		if (servo != null) {
			if (servo.isAutoConnect()) {
				;
			} else {
				FLogin f = new FLogin(main.getShell());
				if (f.open(servo) == null) {
					return;
				}
			}
			CtrClient client = servo.getClient();
			if (client == null) {
				client = new CtrClient(servo);
				servo.setClient(client);
			}
			client.setHandler(main.getServerManager().getHandler());
			client.start();

			main.status();
		}

	}
}
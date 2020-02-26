/**
 * 2017年1月18日
 */
package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;

/**
 * 创建伺服器
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class AServoCreate extends A<FMain> {

	public AServoCreate(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		FServo f = new FServo(main.getShell());
		Servo s = f.open(null);
		if (s != null) {
			main.getServerManager().add(s);
			main.getTreeZoneView().insert(s);
			main.getTreeRelayView().insert(s);
			CtrClient client = new CtrClient(s);
			s.setClient(client);
//			client.setTimeHeartbeat(180);
			client.setHandler(main.getServerManager().getHandler());
			client.start();
		}
	}
}
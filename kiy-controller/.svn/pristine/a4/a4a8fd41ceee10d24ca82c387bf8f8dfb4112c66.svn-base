package com.kiy.controller;

import java.util.ArrayList;
import java.util.List;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;

/**
 * 打开用户角色界面
 * @author HLX 2017.3.23
 *
 */
public class AUserAndRole extends A<FMain> {

	private List<Servo> openWindow = new ArrayList<>();

	public AUserAndRole(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		CtrClient client = servo.getClient();
		
		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if(judgeClientActive){
			return;
		}

		for (Servo opneServo : openWindow) {
			if (opneServo == servo) {
				return;
			}
		}
		openWindow.add(servo);

		FUserAndRole fUserAndRole = new FUserAndRole();
		ServoManager serverManager = main.getServerManager();
		serverManager.add(fUserAndRole, servo);
		
		fUserAndRole.open(servo, serverManager);
		serverManager.off(fUserAndRole);
		openWindow.remove(servo);
	}

}

package com.kiy.controller;

import com.kiy.common.Servo;
import com.kiy.common.User;

public class AQueryPersonalLog extends A<FMain> {

	public AQueryPersonalLog(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		User user = servo.getUser();

		FQueryLog fQueryLog = new FQueryLog(main.getShell());
		ServoManager serverManager = main.getServerManager();
		serverManager.add(fQueryLog, servo);
		fQueryLog.open(user, servo);
		serverManager.off(fQueryLog);
	}
}

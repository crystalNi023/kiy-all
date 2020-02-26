package com.kiy.controller;

import com.kiy.common.Servo;
import com.kiy.common.User;

public class ACheckUserInfo extends A<FMain> {

	/**
	 * @param m
	 */
	public ACheckUserInfo(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo currentServo = main.getCurrentServo();

		if (currentServo == null) {
			return;
		}

		User user = currentServo.getUser();
		
		if (user == null) {
			throw new NullPointerException("查看用户信息时,当前伺服器下用户不能为空");
		}

		FUserDetails f = new FUserDetails(main.getShell(), user);
		f.open();
	}

}

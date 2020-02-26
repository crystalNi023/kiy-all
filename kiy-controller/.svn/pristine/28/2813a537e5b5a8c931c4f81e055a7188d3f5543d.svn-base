package com.kiy.controller;

import com.kiy.common.Servo;

/**
 * 打开伺服器配置界面
 * 
 * @author 35210
 *
 */
public class AServoConfig extends A<FMain> {

	public AServoConfig(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		if (servo != null) {
			FServoConfig f = new FServoConfig(main.getShell());
			main.getServerManager().add(f, servo);
			f.open(servo);
			main.getServerManager().off(f);
		}
	}

}

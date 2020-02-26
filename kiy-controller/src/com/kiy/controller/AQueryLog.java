package com.kiy.controller;

import java.util.ArrayList;
import java.util.List;

import com.kiy.common.Servo;

/**
 * 查看所有日志
 * 
 * @author HLX
 *
 */
public class AQueryLog extends A<FMain> {

	private List<Servo> openWindow = new ArrayList<>();

	/**
	 * 
	 * @param m FMain
	 */
	public AQueryLog(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		for (Servo opneServo : openWindow) {
			if (opneServo == servo) {
				return;
			}
		}
		openWindow.add(servo);

		FQueryLog f = new FQueryLog(main.getShell());
		ServoManager serverManager = main.getServerManager();
		serverManager.add(f, servo);
		f.open(null, servo);
		serverManager.off(f);
		openWindow.remove(servo);
	}

}

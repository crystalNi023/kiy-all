package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Scene;
import com.kiy.common.Servo;

/**
 * 更新场景
 * 
 * @author HLX
 *
 */
public class AUpdateScene extends A<FSceneRecord> {

	private Servo servo;
	private Scene scene;

	public AUpdateScene(FSceneRecord m, Servo servo1, Scene s) {
		super(m);
		servo = servo1;
		scene = s;
	}

	@Override
	protected void run() {
		sendMessage();
	}

	public void sendMessage() {
		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}
		FScene f = new FScene(main.getShell());
		f.open(servo, scene);
	}
}

package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Linkage;
import com.kiy.common.Servo;

/**
 * 更新联动
 * 
 * @author HLX
 *
 */
public class AUpdateLinkage extends A<FLinkageRecord> {

	private Servo servo;
	private Linkage linkage;

	public AUpdateLinkage(FLinkageRecord m, Servo servo1, Linkage l) {
		super(m);
		servo = servo1;
		linkage = l;
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
		FLinkage f = new FLinkage(main.getShell());
		f.open(servo, linkage);
	}
}

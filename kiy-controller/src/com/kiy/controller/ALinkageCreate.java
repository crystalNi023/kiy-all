/**
 * 2017年1月20日
 */
package com.kiy.controller;

import org.eclipse.swt.widgets.Shell;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;

/**
 * 创建联动任务
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class ALinkageCreate extends A<FMain> {

	/**
	 * @param m FMain
	 */
	public ALinkageCreate(FMain m) {
		super(m);
	}

	public ALinkageCreate() {
	}

	@Override
	protected void run() {
		// 判断伺服器节点是否存在
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		sendMessage(servo,client , main.getShell());
	}

	public void sendMessage(Servo servo, CtrClient client,Shell parent) {
		FLinkage f = new FLinkage(parent);
		f.open(servo,null);
	}
}
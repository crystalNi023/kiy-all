package com.kiy.controller;

import java.util.ArrayList;
import java.util.List;

import com.kiy.common.Servo;
import com.kiy.resources.Lang;

/**
 * 查看联动列表
 * @author chen pan
 *
 */
public class ALinkageRecord extends A<FMain> {
	private List<Servo> openWindow = new ArrayList<>();

	/**
	 * 
	 * @param m FMain
	 */
	public ALinkageRecord(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getCurrentServo();
		
		if (servo != null) {

			for (Servo opneServo : openWindow) {
				if (opneServo == servo) {
					return;
				}
			}
			openWindow.add(servo);

			FLinkageRecord f = new FLinkageRecord(main.getShell());
			ServoManager serverManager = main.getServerManager();
			serverManager.add(f, servo);
			f.open(servo);
			serverManager.off(f);
			openWindow.remove(servo);
		} else {
			F.mbInformation(main.getShell(), Lang.getString("ASceneRecord.MessageBoxTitle.text"), Lang.getString("ASceneRecord.MessageBoxContent.text"));
		}
	}

}

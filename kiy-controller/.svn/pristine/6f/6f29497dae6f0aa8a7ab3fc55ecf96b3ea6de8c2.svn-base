package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.CreateZone;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MZone.Builder;

/**
 * 创建区域
 * 
 * @author HLX
 *
 */
public final class ACreateZone extends A<FMain> {

	private Zone selectionZone;

	public ACreateZone(FMain m) {
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
		if (judgeClientActive) {
			return;
		}

		FZone form = new FZone(main.getShell());
		if (main.getSelectionZone() != null) {
			selectionZone = main.getSelectionZone();
			Zone zone = form.open(servo, null, selectionZone);
			if (zone != null) {
				Message.Builder b_m = Message.newBuilder();
				b_m.setKey(CtrClient.getKey());
				b_m.setUserId(servo.getUser().getId());
				CreateZone.Builder createZone = CreateZone.newBuilder();
				Builder builder = createZone.getItemBuilder();
				
				builder.setId(zone.getId());
				builder.setName(zone.getName());
				builder.setRemark(zone.getRemark());
				if (zone.getParentId() != null)
					builder.setParent(zone.getParentId());
				b_m.setCreateZone(createZone);
				client.send(b_m.build());
			}
		} else {
			Zone zone = form.open(servo, null, null);
			if (zone != null) {
				Message.Builder b_m = Message.newBuilder();
				b_m.setKey(CtrClient.getKey());
				b_m.setUserId(servo.getUser().getId());
				CreateZone.Builder createZone = CreateZone.newBuilder();
				Builder builder = createZone.getItemBuilder();
				
				builder.setId(zone.getId());
				builder.setName(zone.getName());
				builder.setRemark(zone.getRemark());
				if (zone.getParentId() != null)
					builder.setParent(zone.getParentId());
				b_m.setCreateZone(createZone);
				client.send(b_m.build());
			}
		}
	}

}

package com.kiy.controller;

import java.util.Date;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Maintain;
import com.kiy.common.Servo;
import com.kiy.protocol.Messages.CreateMaintain;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MMaintain.Builder;
import com.kiy.resources.Lang;

/**
 * 创建设备维护记录
 * 
 * @author (KeDaiQin TEL:18302367318)
 *
 */
public final class AMaintainCreate extends A<FMain> {

	/**
	 * @param m
	 */
	public AMaintainCreate(FMain m) {
		super(m);
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

		Device d = main.getSelectionDevice();

		if (d == null) {
			F.mbInformation(main.getShell(), Lang.getString("AMainTainRecords.MessageBoxMaintenanceRecordsTitle.text"), Lang.getString("AMainTainRecords.MessageBoxMessage.text"));
			return;
		}

		FMaintain form = new FMaintain(main.getShell());
		Maintain maintain = form.open(d, null);
		if (maintain != null) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			CreateMaintain.Builder createMaintain = CreateMaintain.newBuilder();
			Builder builder = createMaintain.getItemBuilder();
			
			builder.setId(maintain.getId());
			builder.setDeviceId(maintain.getDeviceId());
			builder.setRepair(maintain.getRepair().getValue());
			builder.setSn(maintain.getSn());
			builder.setLoad(maintain.getLoad());
			builder.setPower(maintain.getPower());
			builder.setMutual(maintain.getMutual());
			builder.setRadix(maintain.getRadix());
			builder.setEnergyBalance(maintain.getEnergyBalance());
			builder.setCreated(new Date().getTime());
			b_m.setCreateMaintain(createMaintain.build());
			b_m.setUserId(servo.getUser().getId());
			client.send(b_m.build());
		}
	}

}
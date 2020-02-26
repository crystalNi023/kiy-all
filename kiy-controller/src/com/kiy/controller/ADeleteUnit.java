package com.kiy.controller;

import org.eclipse.swt.SWT;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.DeleteDevice;
import com.kiy.protocol.Messages.DeleteZone;
import com.kiy.protocol.Messages.Message;
import com.kiy.resources.Lang;

/**
 * 删除Unit(Servo,Zone,Device)
 * 
 * @author HLX
 *
 */
public class ADeleteUnit extends A<FMain> {

	private static final int NEED_TIP = 0;// 需要删除提示
	private static final int NO_TIP = 1;// 不需要删除提示，用于删除一个区域时，同时删除该区域下的所有区域与设备时不需要每一个都给提示

	public ADeleteUnit(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Servo servo = main.getSelectionServo();
		Device device = main.getSelectionDevice();
		Zone zone = main.getSelectionZone();

		// 判断删除的伺服器
		if (servo != null) {
			sendDelMessage(servo.getClient(), servo);
		}
		// 判断删除的设备
		else if (device != null) {
			sendDelMessage(device.getServo().getClient(), device, NEED_TIP);
		}
		// 判断删除的区域
		else if (zone != null) {
			deleteZone(zone, NEED_TIP);
		} else {
			F.mbInformation(main.getShell(), Lang.getString("ADeleteUnit.MessageBoxDeleteTitle.text"), Lang.getString("ADeleteUnit.MessageBoxDeleteContent.text"));
			return;
		}
	}

	/**
	 * 发送删除伺服器消息
	 * 
	 * @param client CtrClient
	 * @param servo Servo
	 */
	private void sendDelMessage(CtrClient client, Servo servo) {

		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteUnit.deleteServo.text"),String.format(Lang.getString("ADeleteUnit.deleteQuesion.text"), servo.getName()));

		if (open == SWT.OK) {
			// 如果伺服器不为空，就先断开伺服器
			if (client != null) {
				client.stop();
			}
			
			main.getServerManager().off(servo);
			main.getTreeZoneView().remove(servo);
			main.getTreeRelayView().remove(servo);
			main.getTreeView().removeAll();
			
		} else {
			// 删除提示框选择取消，不删除
		}

	}

	/**
	 * 发送删除区域消息
	 * 
	 * @param zone Zone
	 * @param tip Int NO_TIP是不需要提示就删除，NEED_TIP是需要提示删除
	 */
	private void deleteZone(Zone zone, int tip) {
		sendDelMessage(zone.getServo().getClient(), zone, tip);
	}

	/**
	 * 发送删除区域消息
	 * 
	 * @param client CtrClient 客户端对象
	 * @param zone Zone 区域对象
	 * @param tip Int NO_TIP是不需要提示就删除，NEED_TIP是需要提示删除
	 */
	private void sendDelMessage(CtrClient client, Zone zone, int tip) {
		if (zone == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);

		if (judgeClientActive) {
			return;
		}

		switch (tip) {
			case NEED_TIP:

				int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteUnit.deleteZone.text"), String.format(Lang.getString("ADeleteUnit.deleteQuesionZone.text"), zone.getName()));

				if (open == SWT.OK) {
					if (zone.getDevices() != null) {
						for (Device device : zone.getDevices()) {
							sendDelMessage(device.getServo().getClient(), device, NO_TIP);
						}
					}

					if (zone.getZones() != null) {
						for (Zone z : zone.getZones()) {
							deleteZone(z, NO_TIP);
						}
					}

					Message.Builder b_m = Message.newBuilder();
					DeleteZone.Builder b_cr = DeleteZone.newBuilder();
					b_cr.setId(zone.getId());
					b_m.setDeleteZone(b_cr);
					b_m.setUserId(client.getServo().getUser().getId());
					b_m.setKey(CtrClient.getKey());
					client.send(b_m.build());

				} else {
					// 删除提示框选择取消，不删除
				}
				break;
			case NO_TIP:
				if (zone.getDevices() != null) {
					for (Device device : zone.getDevices()) {
						sendDelMessage(device.getServo().getClient(), device, NO_TIP);
					}
				}

				if (zone.getZones() != null) {
					for (Zone z : zone.getZones()) {
						deleteZone(z, NO_TIP);
					}
				}

				Message.Builder b_m = Message.newBuilder();
				DeleteZone.Builder b_cr = DeleteZone.newBuilder();
				b_cr.setId(zone.getId());
				b_m.setDeleteZone(b_cr);
				b_m.setUserId(client.getServo().getUser().getId());
				b_m.setKey(CtrClient.getKey());
				client.send(b_m.build());
				break;
			default:
				break;
		}
	}

	/**
	 * 发送删除设备消息
	 * 
	 * @param client CtrClient 客户端对象
	 * @param device Device 设备对象
	 * @param tip Int NO_TIP是不需要提示就删除，NEED_TIP是需要提示删除
	 */
	private void sendDelMessage(CtrClient client, Device device, int tip) {

		if (device == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);

		if (judgeClientActive) {
			return;
		}

		switch (tip) {

			case NEED_TIP:
				int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteUnit.deleteDevice.text"), String.format(Lang.getString("ADeleteUnit.deleteQuesion.text"), device.getName()));

				if (open == SWT.OK) {
					Message.Builder b_m = Message.newBuilder();
					DeleteDevice.Builder b_cr = DeleteDevice.newBuilder();
					b_cr.setId(device.getId());
					b_m.setDeleteDevice(b_cr);
					b_m.setUserId(client.getServo().getUser().getId());
					b_m.setKey(CtrClient.getKey());
					client.send(b_m.build());
				} else {
					// 删除提示框选择取消，不删除
				}
				break;
			case NO_TIP:
				Message.Builder b_m = Message.newBuilder();
				DeleteDevice.Builder b_cr = DeleteDevice.newBuilder();
				b_cr.setId(device.getId());
				b_m.setDeleteDevice(b_cr);
				b_m.setUserId(client.getServo().getUser().getId());
				b_m.setKey(CtrClient.getKey());
				client.send(b_m.build());
				break;
			default:
				break;
		}
	}
}

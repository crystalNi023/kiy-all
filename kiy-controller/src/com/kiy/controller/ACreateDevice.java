/**
 * 2017年1月20日
 */
package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Vendor;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.CreateDevice;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MDevice;
import com.kiy.protocol.Units.MDevice.Builder;

/**
 * 创建设备
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class ACreateDevice extends A<FMain> {
	private Zone selectionZone;
	private Device createDevice;

	private Vendor vendor;
	private Kind kind;
	private Model model;

	/**
	 * 
	 * @param m FMain
	 */
	public ACreateDevice(FMain m, Vendor v, Kind k,Model mo) {
		super(m);
		vendor = v;
		kind = k;
		model = mo;
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

		FDevice f = new FDevice(main.getShell());
		main.getServerManager().add(f, main.getCurrentServo());
		
		if (main.getSelectionDevice() != null) {
			Device selectionDevice = main.getSelectionDevice();
			createDevice = f.open(servo, null, null, selectionDevice, vendor, kind , model);
		} else if (main.getSelectionZone() != null) {
			selectionZone = main.getSelectionZone();
			createDevice = f.open(servo, null, selectionZone, null, vendor, kind , model);
		} else {
			createDevice = f.open(servo, null, null, null, vendor, kind , model);
		}
		
		main.getServerManager().off(f);
		
		if (createDevice != null) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setUserId(servo.getUser().getId());
			b_m.setKey(CtrClient.getKey());
			CreateDevice.Builder b_cr = CreateDevice.newBuilder();
			Builder newBuilder = MDevice.newBuilder();
			newBuilder.setId(createDevice.getId());
			newBuilder.setVendor(createDevice.getVendor().getValue());
			newBuilder.setKind(createDevice.getKind().getValue());
			newBuilder.setModel(createDevice.getModel().getValue());
			newBuilder.setLink(createDevice.getLink().getValue());
			newBuilder.setName(createDevice.getName());
			newBuilder.setNumber(createDevice.getNumber());
			newBuilder.setZoneId(createDevice.getZoneId());
			newBuilder.setSn(createDevice.getSn());
			newBuilder.setLoad(createDevice.getLoad());
			newBuilder.setPower(createDevice.getPower());
			newBuilder.setRelayId(createDevice.getRelayId());
			newBuilder.setNetworkPort(createDevice.getNetworkPort());
			newBuilder.setNetworkIp(createDevice.getNetworkIp());
			newBuilder.setSerialPort(createDevice.getSerialPort());
			newBuilder.setSerialBaudRate(createDevice.getSerialBaudRate());
			newBuilder.setRemark(createDevice.getRemark());
			newBuilder.setSn(createDevice.getSn());
			newBuilder.setAddress(createDevice.getAddress());
			newBuilder.setDelay(createDevice.getDelay());
			newBuilder.setMutual(createDevice.getMutual());
			newBuilder.setUsername(createDevice.getUsername());
			newBuilder.setPassword(createDevice.getPassword());
			newBuilder.setProduced(createDevice.getProduced().getTime());
			newBuilder.setInstalled(createDevice.getInstalled().getTime());
			newBuilder.setPhaseCheck(createDevice.getPhaseCheck());
			newBuilder.setPhasePower(createDevice.getPhasePower());
			newBuilder.setNotice(createDevice.getNotice());
			newBuilder.setDetect(createDevice.getDetect());
			b_cr.setItem(newBuilder.build());
			

			b_m.setCreateDevice(b_cr);
			client.send(b_m.build());
		}
	}
}
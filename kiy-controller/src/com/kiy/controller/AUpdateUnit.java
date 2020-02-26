/**
 * 2017年1月18日
 */
package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.protocol.Messages.UpdateZone;
import com.kiy.protocol.Units.MDevice.Builder;
import com.kiy.protocol.Units.MZone;

/**
 * 更新Unit(Servo,Zone,Device)
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class AUpdateUnit extends A<FMain> {

	/**
	 * 
	 * @param m FMain
	 */
	public AUpdateUnit(FMain m) {
		super(m);
	}

	@Override
	protected void run() {

		Device selectionDevice = main.getSelectionDevice();
		Zone selectionZone = main.getSelectionZone();
		if (selectionDevice != null) {
			servo = selectionDevice.getServo();
			CtrClient client = servo.getClient();

			boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
			if (judgeClientActive) {
				return;
			}

			Zone zone = selectionDevice.getZone();
			String zoneId = "";
			if(zone != null){
				zoneId = selectionDevice.getZoneId();
			}
			
			FDevice f = new FDevice(main.getShell());
			main.getServerManager().add(f, main.getCurrentServo());
			Device rdevice = f.open(servo, selectionDevice, null, null, selectionDevice.getVendor(), selectionDevice.getKind(),selectionDevice.getModel());
			main.getServerManager().off(f);
			if (f.isUpdate) {
				if (rdevice != null) {
					Message.Builder b_m = Message.newBuilder();
					b_m.setKey(CtrClient.getKey());
					b_m.setUserId(servo.getUser().getId());
					UpdateDevice.Builder updateDevice = UpdateDevice.newBuilder();
					Builder builder = updateDevice.getItemBuilder();
					
					builder.setId(rdevice.getId());
					builder.setVendor(selectionDevice.getVendor().getValue());
					builder.setKind(selectionDevice.getKind().getValue());
					builder.setModel(selectionDevice.getModel().getValue());
					if(rdevice.getLink()!=null){
						builder.setLink(rdevice.getLink().getValue());
					}
					if(rdevice.getUse()!=null) {
						builder.setUse(rdevice.getUse().getValue());
					}
					builder.setSn(rdevice.getSn());
					builder.setName(rdevice.getName());
					builder.setNumber(rdevice.getNumber());
					if(!zoneId.equals(rdevice.getZoneId())){
						main.moveDeviceOnTree(selectionDevice, servo.getZone(rdevice.getZoneId()));
					}
					
					builder.setZoneId(rdevice.getZoneId());
					if(rdevice.getRelayId()!=null)
						builder.setRelayId(rdevice.getRelayId());
					builder.setNetworkPort(rdevice.getNetworkPort());
					builder.setNetworkIp(rdevice.getNetworkIp());
					builder.setSerialPort(rdevice.getSerialPort());
					builder.setSerialBaudRate(rdevice.getSerialBaudRate());
					builder.setRemark(rdevice.getRemark());
					builder.setLoad(rdevice.getLoad());
					builder.setPower(rdevice.getPower());
					builder.setAddress(rdevice.getAddress());
					builder.setDelay(rdevice.getDelay());
					builder.setMutual(rdevice.getMutual());
					builder.setUsername(rdevice.getUsername());
					builder.setPassword(rdevice.getPassword());
					builder.setProduced(rdevice.getProduced().getTime());
					builder.setInstalled(rdevice.getInstalled().getTime());
					builder.setNotice(rdevice.getNotice());
					builder.setDetect(rdevice.getDetect());
					builder.setPhasePower(rdevice.getPhasePower());
					builder.setPhaseCheck(rdevice.getPhaseCheck());

					b_m.setUpdateDevice(updateDevice);
					client.send(b_m.build());
				}
				return;
			}

		} else if (selectionZone != null) {
			servo = selectionZone.getServo();
			CtrClient client = servo.getClient();

			boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
			if (judgeClientActive) {
				return;
			}

			FZone f = new FZone(main.getShell());
			final Zone z = main.getSelectionZone();
			Zone rZone = f.open(servo, z, null);
			if (f.isUpdate) {
				if (rZone != null) {
					Message.Builder b_m = Message.newBuilder();
					b_m.setKey(CtrClient.getKey());
					b_m.setUserId(servo.getUser().getId());
					UpdateZone.Builder updateZone = UpdateZone.newBuilder();
					MZone.Builder builder = updateZone.getItemBuilder();
					
					builder.setId(z.getId());
					builder.setName(z.getName());
					builder.setRemark(z.getRemark());
					if (z.getParentId() != null)
						builder.setParent(z.getParentId());
					b_m.setUpdateZone(updateZone);
					client.send(b_m.build());
				}
				return;
			}

		} else {
			FServo f = new FServo(main.getShell());
			f.open((main.getCurrentServo()));
		}

	}
}
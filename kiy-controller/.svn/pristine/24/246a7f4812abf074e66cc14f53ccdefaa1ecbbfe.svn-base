package com.kiy.controller;


import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapContent;
import org.geotools.swt.SwtMapPane;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.controller.view.MapView;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.protocol.Units.MDevice.Builder;

/**
 * 在地图上更新设备
 * 
 * @author HLX
 *
 */
public class AUpdateDeviceForMap extends A<FMain> {

	private MapView mv; // 用来获取设备坐标信息

	/**
	 * 
	 * @param m
	 *            FMain
	 * @param mv
	 *            MapView
	 * @param pane
	 *            SwtMapPane
	 */
	public AUpdateDeviceForMap(FMain m, MapView mv) {
		super(m);
		this.mv = mv;
	}

	@Override
	protected void run() {
		SwtMapPane pane = mv.getPane();
		if(pane == null){
			return;
		}
		
		if(pane.isDisposed()){
			return;
		}
		
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			return;
		}

		DirectPosition2D mapPosition = mv.getMapPosition();
		if (mapPosition == null) {
			return;
		}
		
		MapContent content = pane.getMapContent();
		String deviceId = F.getDeviceIdFormapPosition(pane, content, mapPosition);

		if (deviceId == null) {
			return;
		}

		// 获取设备状态
		Device d = servo.getDevice(deviceId);

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if(judgeClientActive){
			return;
		}

		if (d != null) {
			FDevice f = new FDevice(main.getShell());
			main.getServerManager().add(f, main.getCurrentServo());
			Device rdevice =f.open(servo, d, null, null, d.getVendor(), d.getKind(),d.getModel());
			main.getServerManager().off(f);
			if (f.isUpdate) {
				if (rdevice != null) {
					Message.Builder b_m = Message.newBuilder();
					b_m.setKey(CtrClient.getKey());
					b_m.setUserId(servo.getUser().getId());
					UpdateDevice.Builder updateDevice = UpdateDevice.newBuilder();
					Builder builder = updateDevice.getItemBuilder();
					
					builder.setId(rdevice.getId());
					builder.setVendor(rdevice.getVendor().getValue());
					builder.setKind(rdevice.getKind().getValue());
					builder.setModel(rdevice.getModel().getValue());
					builder.setLink(rdevice.getLink().getValue());
					builder.setSn(rdevice.getSn());
					builder.setName(rdevice.getName());
					builder.setNumber(rdevice.getNumber());
					builder.setZoneId(rdevice.getZoneId());
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
					b_m.setUpdateDevice(updateDevice);
					client.send(b_m.build());
				}
				return;
			}
		}

	}

}

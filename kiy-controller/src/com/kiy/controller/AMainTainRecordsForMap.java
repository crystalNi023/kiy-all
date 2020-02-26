package com.kiy.controller;


import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapContent;
import org.geotools.swt.SwtMapPane;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.controller.view.MapView;

/**
 * 在地图上查看设备维护记录
 * @author HLX
 *
 */
public class AMainTainRecordsForMap extends A<FMain> {

	private MapView mv; // 用来获取设备坐标信息

	/**
	 * 
	 * @param m FMain
	 * @param mv MapView
	 */
	public AMainTainRecordsForMap(FMain m, MapView mv) {
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

		if (d != null) {
			FMaintainRecord f = new FMaintainRecord(main.getShell());
			main.getServerManager().add(f, d.getServo());
			f.open(d, main.getServerManager());

		}
	}

}

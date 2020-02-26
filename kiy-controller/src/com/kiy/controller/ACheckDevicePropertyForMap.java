package com.kiy.controller;


import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapContent;
import org.geotools.swt.SwtMapPane;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.controller.view.MapView;

/**
 * 在地图上查看设备属性
 * 
 * @author HLX
 *
 */
public class ACheckDevicePropertyForMap extends A<FMain> {
	private MapView mv; // 用来获取设备坐标信息

	/**
	 * 
	 * @param m FMain
	 * @param mv MapView
	 */
	public ACheckDevicePropertyForMap(FMain m, MapView mv) {
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
			// 打开设备属性窗口
			FDeviceDetails f = new FDeviceDetails(main.getShell());
			f.open(d,null);
		}
	}

}

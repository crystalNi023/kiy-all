package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.MapContent;
import org.geotools.swt.SwtMapPane;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.controller.view.MapView;
import com.kiy.resources.Lang;

/**
 * 只是在地图上移除一个设备，并不是删除该设备
 * 
 * @author HLX
 *
 */
public class ADeleteDeviceOnMapForMap extends A<FMain> {
	private MapView mv; // 用来获取设备坐标信息

	/**
	 * 
	 * @param m FMain
	 * @param mv MapView
	 */
	public ADeleteDeviceOnMapForMap(FMain m, MapView mv) {
		super(m);
		this.mv = mv;

	}

	@Override
	protected void run() {
		SwtMapPane pane = mv.getPane();
		if (pane == null) {
			return;
		}

		if (pane.isDisposed()) {
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

		Device device = servo.getDevice(deviceId);

		// 显示删除提示框
		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("MoveDevice.text"),String.format(Lang.getString("MoveDevice.tip"),device.getName()));

		if (open == SWT.OK) {
			mv.deleteDeviceOnMap(servo, device);
		} else {
			// 不移动
		}

	}

}

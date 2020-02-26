package com.kiy.controller.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.swt.event.MapMouseEvent;
import org.geotools.swt.tool.CursorTool;
import com.kiy.common.Servo;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * 添加设备光标
 * 
 * @author 35210
 *
 */
public class AddDeviceTool extends CursorTool {

	private Servo servo;
	private String deviceId;
	private MapView mapView;
	
	private boolean isAdd;

	public AddDeviceTool(int triggerButtonMask) {
		super(triggerButtonMask);
	}

	public AddDeviceTool(String deviceId,MapView mp,Servo s) {
		this(CursorTool.ANY_BUTTON);
		this.deviceId = deviceId;
		mapView = mp;
		this.servo = s;
	}
	
	 public Cursor getCursor() {
	        return Display.getCurrent().getSystemCursor(SWT.CURSOR_CROSS); 
	 }


	/**
	 * 鼠标点击事件，根据鼠标点击将要添加的设备添加到地图上
	 */
	@Override
	public void onMouseClicked(MapMouseEvent ev) {
		super.onMouseClicked(ev);
		if (!isTriggerMouseButton(ev)) {
			return;
		}
		
		if(isAdd==false){
			DirectPosition2D mapPosition = ev.getMapPosition();
			Coordinate coordinate = new Coordinate(mapPosition.getX(), mapPosition.getY());
			if(mapView==null){
				return;
			}
			
			mapView.addDevice(deviceId, coordinate,servo,true);
			isAdd = true;
		}
		
	}

	@Override
	public boolean canDraw() {
		return false;
	}

	@Override
	public boolean canMove() {
		return false;
	}

}

package com.kiy.servo.recognize;

import java.util.List;

import com.kiy.common.Device;
import com.kiy.common.Notice;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Origin;
import com.kiy.common.Types.Status;
import com.kiy.common.Types.Use;
import com.kiy.common.devices.KSSmokeSensor;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.protocol.Units.MNotice.Builder;
import com.kiy.servo.cloud.Cloud;
import com.kiy.servo.data.Data;
import com.kiy.servo.driver.Driver;
import com.kiy.servo.driver.DriverAdpater;
import com.kiy.servo.master.Master;
import com.kiy.servo.master.MatcherLogon;
import com.kiy.servo.messageque.MessageDevice;
import com.kiy.servo.messageque.MessageDeviceDeque;

/**
 * 语音控制指令
 * 
 * @author HLX Tel:18996470535
 * @date 2018年5月22日 Copyright:Copyright(c) 2018
 */
public class SpeechCommand {

	private SpeechCommand() {
	}

	public static String openDevice(Device device) {
		Kind kind = device.getKind();
		DriverAdpater adpater = Driver.getAdpater(device);
		// 1.控制
		switch (kind) {
			case METER_ELECTRICITY_ONE:
			case METER_GAS:
				device.getFeature(1).setValue(1);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您打开"+device.getName();
			case CURTAIN_CONTROLLER:
			case WINDOWS_CONTROLLER:
			case VALVE:	
				device.getFeature(0).setValue(1);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您打开"+device.getName();
			case SWITCH:
				device.getFeature(2).setValue(1);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您打开"+device.getName();
//			case AIR_CONDITIONER:
//				device.getFeature(1).setValue(0);
//				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
//				sendSetStatus(device);
//				return "正在为您打开"+device.getName();
			default:
				break;
		}
	
		return "无法为您打开该设备，检查该设备是否支持开启关闭功能";
	}

	public static String offDevice(Device device) {
		Kind kind = device.getKind();
		DriverAdpater adpater = Driver.getAdpater(device);
		switch (kind) {
			case METER_ELECTRICITY_ONE:
			case METER_GAS:
				device.getFeature(1).setValue(0);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您关闭"+device.getName();
			case CURTAIN_CONTROLLER:
			case WINDOWS_CONTROLLER:
			case VALVE:	
				device.getFeature(0).setValue(0);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您关闭"+device.getName();
			case SWITCH:
				device.getFeature(2).setValue(0);
				MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
				sendSetStatus(device);
				return "正在为您关闭"+device.getName();
			default:
				break;
		}
		return "无法为您关闭该设备，检查该设备是否支持开启关闭功能";
	}
	
	public static String queryDevice(Device d){
		Kind kind = d.getKind();
		switch (kind) {
			case NONE:
				return d.getName()+"当前没有可读取的参数";
			case CONCENTRATOR:
				return d.getName()+"当前的状态为"+Tool.getStatusString(d.getStatus().getValue());
			case REPEATER:
				return d.getName()+"当前的状态为"+Tool.getStatusString(d.getStatus().getValue());
			case ADAPTER:
				return d.getName()+"当前的状态为"+Tool.getStatusString(d.getStatus().getValue());
			case METER_ELECTRICITY_ONE:
				return d.getName()+"当前的耗电量为"+d.getIndicate();
			case METER_ELECTRICITY_THREE:
				return d.getName()+"当前的耗电量为"+d.getIndicate();
			case METER_WATER:
				return d.getName()+"当前的读数为"+d.getIndicate();
			case METER_GAS:
				return d.getName()+"当前的读数为"+d.getIndicate();
			case METER_HEAT:
				return d.getName()+"当前的读数为"+d.getIndicate();
			case SENSOR_SMOKE:
				if (d instanceof KSSmokeSensor) {
					return d.getName()+"当前的烟雾浓度为"+d.getIndicate();
				}else{
					return d.getName()+"当前的状态为"+Tool.getStatusString(d.getStatus().getValue());
				}
			case SENSOR_SWITCHING_OPEN:
				return d.getName()+"当前的状态为"+d.getIndicate();
			case SENSOR_POWER:
				return d.getName()+"当前的读数为"+d.getIndicate();
			case SENSOR_SWITCHING_OFF:
				return d.getName()+"当前的状态为"+d.getIndicate();
			case AUDIBLE_VISUAL_ALARM:
				return d.getName()+"当前的状态为"+d.getIndicate();
			case SENSOR_TEMPERATURE:
				return d.getName()+"当前的温度为"+d.getIndicate();
			case SENSOR_MOISTURE:
				return d.getName()+"当前的湿度为"+d.getIndicate();
			case SENSOR_MOISTURE_TEMPERATURE:
				return d.getName()+"当前的温度湿度为"+d.getIndicate();
			case SENSOR_ILLUMINANCE:
				return d.getName()+"当前的光照度为"+d.getIndicate();
			case SOIL_SENSOR_MOISTURE_TEMPERATURE:
				return d.getName()+"当前的温度湿度为"+d.getIndicate();
			case SENSOR_PM:
				//
				return d.getName()+"当前的读数为"+d.getIndicate();
			case CURTAIN_CONTROLLER:
				return d.getName()+"当前的开合度为"+d.getIndicate();
			case SENSOR_METHANE:
				return d.getName()+"当前的浓度为"+d.getIndicate();
			case SENSOR_CO:
				return d.getName()+"当前的浓度为"+d.getIndicate();
			case SWITCH:
				return d.getName()+"当前的状态为"+d.getIndicate();
			case WINDOWS_CONTROLLER:
				return d.getName()+"当前的开合度为"+d.getIndicate();
			case VALVE:
				return d.getName()+"当前的开合度为"+d.getIndicate();
			case TRANSFORMER_TEMPERATURE_CONTROLLER:
				return d.getName()+"当前的读数为"+d.getIndicate();
			case POWER_METER:
				return d.getName()+"当前的功率为"+d.getIndicate();
			default:
				break;
		}
		return "当前没有可读取的参数";
	}
	
	/**
	 * 打开场景
	 * @return
	 */
	public static String openScene(Scene scene){
		/* 开启场景 */
		List<SceneDevice> openDevice = scene.getOpenDevices();
		for (SceneDevice sceneDevice : openDevice) {
			controlSceneDevice(sceneDevice);
		}
		return "正在为您打开"+scene.getName();
	} 
	
	private static void controlSceneDevice(SceneDevice sceneDevice ){
		Device device = Data.getServo().getDevice(sceneDevice.getDeviceId());
		if (device == null) {
			/* 设备不存在 */
		} else {
			DriverAdpater adpater = Driver.getAdpater(device);
			if (adpater == null) {
				device.setStatus(Status.C_RELAY);
			} else {
				if (!adpater.isActive()) {
					device.setStatus(Status.OFFLINE);
				} else {
					if (!device.getDetect()) {
						device.setStatus(Status.OFFLINE);
					} else {

						device.getFeature(sceneDevice.getFeatureIndex()).setValue(sceneDevice.getFeatureValue());
						MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
						sendSetStatus(device);
						sendControlNotice(device);
					}
				}
			}
		}
	}
	
	private static void sendControlNotice(Device device) {
		/* 电源总控属于安防设备如果被控制需要发送通知给用户 */
		if (device.getNotice()) {
			if (device.getKind() == Kind.SWITCH && device.getUse() == Use.PWOER) {
				Message.Builder msg = Message.newBuilder();
				Builder noticeBuilder = msg.getNoticeBuilder();
				noticeBuilder.setDeviceId(device.getId());

				noticeBuilder.setContent("语音系统关闭了您的电源总控开关");
				Message m = msg.build();
				Cloud.send(m);
				Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);

				Notice n = new Notice();
				n.setDeviceId(device.getId());
				n.setContent("Control");
				n.setUserId("");
				Data.CreateNotice(n);
			}
		}
	}
	
	
	/**
	 * 发送控制设备状态消息
	 */
	private static void sendSetStatus(Device d) {
		// 记录日志
		Data.CreateDeviceStatus(d, Origin.USER);

		Message.Builder msg = Message.newBuilder();
		WriteDeviceStatus.Builder wrsb = msg.getWriteDeviceStatusBuilder();
		MDeviceStatus.Builder itemBuilder = wrsb.getItemBuilder();
		
		itemBuilder.setDeviceId(d.getId());
		itemBuilder.setStatus(d.getStatus().getValue());
		itemBuilder.setCreated(d.getTickStatus());
		for (int index = 0; index < d.getFeatureCount(); index++) {
			itemBuilder.putItems(index, d.getFeature(index).getValue());
		}
		// 发送
		wrsb.setItem(itemBuilder);
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}
}

package com.kiy.servo.driver.rs485;

import io.netty.buffer.ByteBuf;

import java.util.HashSet;
import java.util.Set;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Origin;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.FYSerialRadioFrequencyFY0001;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.servo.Log;
import com.kiy.servo.cloud.Cloud;
import com.kiy.servo.data.Data;
import com.kiy.servo.driver.Coder;
import com.kiy.servo.job.Linkager;
import com.kiy.servo.master.Master;
import com.kiy.servo.master.MatcherLogon;

/**
 * 
 * @author 飞音云
 *
 */
public class FYCoder extends Coder {
	/**
	 * 获取状态
	 */
	public static final byte STATUS = (byte) 0x0080;

	public static Set<Device> fYSerialRadioFrequency = new HashSet<>();
	
	private String hex = null;
	
	@Override
	public int getMaxFrame() {
		return 32;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {

	}

	@Override
	public int frame(Device device, ByteBuf in) {
		// 已收到字节总数
		int readable = in.readableBytes();

		if (readable < 16)
			return Coder.CONTINUE;

		if (readable > 32) {
			device.setStatus(Status.C_OVERFLOW);
			return Coder.OVERFLOW;
		}
		
		return Coder.OK;
	}

	@Override
	public void decode(Device device, ByteBuf in, byte code, int tag) {
		
		if (in.readableBytes() == 0) {
			return;
		}
		
		//遍历输入流，取出第1个到3位字节。例（FD EA 29 46 57 DF）FD开始位，DF结束位，第四位可能会发生改变，所以取FD后三位做匹配。
		int number = 0;
		String[] str = new String[in.capacity()/5+1];
		  for(int i=0; i<in.capacity()&&i+5<in.capacity(); i++){
			  if (Integer.toHexString(in.getUnsignedByte(i)).equalsIgnoreCase("fd")&&Integer.toHexString(in.getUnsignedByte(i+5)).equalsIgnoreCase("df")) {
				  str[number++] = fixed(Integer.toHexString(in.getUnsignedByte(i+1)),2)+fixed(Integer.toHexString(in.getUnsignedByte(i+2)),2)+fixed(Integer.toHexString(in.getUnsignedByte(i+3)),2);
			}
		  }
		  hex = str[2];
		  Log.debug("HEX："+hex);
		  Log.debug("CLOUD WRITER_IDLE");

		for (Device d : fYSerialRadioFrequency) {
			if (d.getSn().equalsIgnoreCase(hex) ) {
				device = (FYSerialRadioFrequencyFY0001)d;
			}
		}

		if (device == null) {
			Servo servo = Data.getServo();
			if (servo != null) {
				Set<Device> devices = servo.getDevices();
				for (Device de : devices) {
					if (de.getModel()==Model.FY_0001) {
						FYSerialRadioFrequencyFY0001 fy = (FYSerialRadioFrequencyFY0001)de;
						
							//sn填写设备收到的报警串口号
							if (fy.getSn().equalsIgnoreCase(hex)&&fy.isFeed()==false) {
								fy.setFeed(true);
								sendSetStatus(fy, null);
								device = fy;
								//**************暂时添加***************
							/*	//厨房水浸报警联动水阀关闭
								Device adapterDevice = servo.getDevice("0faec0ef-24e3-480d-8288-821d6040d933");
								ESGateWayES0002 gateWay = (ESGateWayES0002) adapterDevice;
								if (device.getId().equals("d1ae023a-befe-4c82-9527-f4038365ad9b")) {
									//水阀
									Device deviceOne =  servo.getDevice("412627bb-b72c-4104-acae-f98d71e9bb36");
									ESSwitchTouchTwoES0006_01 switchOne = (ESSwitchTouchTwoES0006_01)deviceOne;
									switchOne.setSwitchOne(true);//关闭时是true
									new MQTTDriver(gateWay).setStatus(switchOne);
								}else//厨房燃气报警联动推窗器打开
									if(device.getId().equals("fcc0c01b-f523-4700-9687-25c9e75d4feb")) {
									//推窗器
									Device deviceTwo =  servo.getDevice("f2532e7c-1d98-452c-8181-4e4290f78fb5");
									ESWindowPusherES0025 windowPusher = (ESWindowPusherES0025)deviceTwo;
									windowPusher.setControl(true);
									windowPusher.setFeed(1);
									new MQTTDriver(gateWay).setStatus(windowPusher);
								}*/
								//*********************************
						}
					}
				}
			}
		}

//		if (device == null) {
//			return;
//		}
	}
	/**
	 * 返回指定长度字符串（前面补len-str.length()个0）
	 * @param str
	 * @param len
	 * @return
	 */
	private String fixed(String str,int len){
		if(null==str || str.isEmpty()){
			str="";
		}
		if(str.length()!=len)
			for(int i=0;i<len-str.length();i++){
				str="0"+str;
		}
		return str;
	}
	
	/**
	 * 发送控制设备状态消息
	 */
	private static void sendSetStatus(Device d, String error) {
		// 记录日志
		Data.CreateDeviceStatus(d, Origin.UNKNOWN);

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

		if (error != null) {
			msg.setError(error);
		}
		Linkager.executeLinkage(d);//设备上报触发联动
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}

}

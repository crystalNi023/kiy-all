package com.kiy.servo.driver.rs485;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import com.kiy.common.Device;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Origin;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.ModbusSerialRadioFrequencyMB0021;
import com.kiy.common.devices.ModbusSerialRadioFrequencyMB0022;
import com.kiy.common.devices.MudbusAudibleAndVisualAlarm;
import com.kiy.common.devices.MudbusCOSensor;
import com.kiy.common.devices.MudbusCombustibleGasSensor;
import com.kiy.common.devices.MudbusHumitureSensorMB0002;
import com.kiy.common.devices.MudbusHumitureSensorMB0003;
import com.kiy.common.devices.MudbusHumitureSensorMB0004;
import com.kiy.common.devices.MudbusIluminaceSensor;
import com.kiy.common.devices.MudbusMethaneSensorMB0006;
import com.kiy.common.devices.MudbusPMSensor;
import com.kiy.common.devices.MudbusPowerSensorMB0009;
import com.kiy.common.devices.MudbusPowerSensorMB0018;
import com.kiy.common.devices.MudbusSmokeSensorMB0011;
import com.kiy.common.devices.MudbusSmokeSensorMB0012;
import com.kiy.common.devices.MudbusSoilHumitureSensor;
import com.kiy.common.devices.MudbusSwitchMB0014;
import com.kiy.common.devices.MudbusSwitchMB0020;
import com.kiy.common.devices.MudbusSwitchMB0020_01;
import com.kiy.common.devices.MudbusSwitchingOffSensorMB0013;
import com.kiy.common.devices.MudbusSwitchingOffSensorMB0017;
import com.kiy.common.devices.MudbusSwitchingOffSensorMB0019;
import com.kiy.common.devices.MudbusSwitchingOpenSensorMB0016;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.servo.Config;
import com.kiy.servo.Log;
import com.kiy.servo.cloud.Cloud;
import com.kiy.servo.data.Data;
import com.kiy.servo.driver.Coder;
import com.kiy.servo.master.Master;
import com.kiy.servo.master.MatcherLogon;

/**
 * MUDBUS 协议Coder
 * 
 * @author HLX Tel:18996470535
 * @date 2018年4月26日 Copyright:Copyright(c) 2018
 */
public final class MudbusCoder extends Coder {

	/**
	 * 获取MB_0014状态,获取MB_0020状态,获取MB_0020_01状态
	 */
	public static final byte STATUS_0x01 = 0x01;
	/**
	 * 获取MB_0013,MB_0016,MB_0017,MB_0011,MB_0007,MB_0018,MB_0019状态
	 */
	public static final byte STATUS_0x02 = 0x02;
	/**
	 * 常规获取状态
	 */
	public static final byte STATUS_0x03 = 0x03;
	/**
	 * 获取MB_0003状态
	 */
	public static final byte STATUS_0x04 = 0x04;
	/**
	 * 控制MB_0014,MB_0020状态,MB_0020_01状态
	 */
	public static final byte STATUS_0x05 = 0x05;
	/**
	 * 控制MB_0015状态
	 */
	public static final byte STATUS_0x10 = 0x10;

	@Override
	public int getMaxFrame() {
		return 128;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {
		if (device.getModel() != Model.MB_0022)
			out.writeByte(Integer.parseInt(device.getNumber()));
		out.writeByte(code);
		
		switch (device.getModel()) {
			case MB_0001:// 光照度传感器
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(7);
					out.writeShort(2);
					break;
				}
				break;
			case MB_0002:// 温湿度采集器(集成与PM传感器于一体)
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(0);
					out.writeShort(2);
					break;
				}
				break;
			case MB_0003:// 温湿度传感器(用于安防主机)
				if (code == STATUS_0x04) {
					// read_device
					out.writeShort(0);
					out.writeShort(2);
					break;
				}
				break;
			case MB_0004:// 温湿度传感器(用于配电室托管)
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(0);// 设置起始位置为0
					out.writeShort(2);// 设置数据长度为2
				}
				break;
			case MB_0005:// 土壤温湿度传感器
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(2);
					out.writeShort(2);
					break;
				}
				break;
			case MB_0006:// 甲烷采集器(单个设备)
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(6);
					out.writeShort(1);
					break;
				}
				break;
			case MB_0007:// 可燃气体采集器(开关量设备集成))
				if (code == STATUS_0x02) {
					// read_device
					out.writeShort(0);
					out.writeShort(4);
					break;
				}
				break;
			case MB_0008:// PM2.5/PM10采集器
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(4);
					out.writeShort(6);
					break;
				}
				break;
			case MB_0009:// 断电传感器(用于配电室托管)
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(3);
					out.writeShort(1);
					break;
				}
				break;
			case MB_0010:// 一氧化碳采集器
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(6);
					out.writeShort(1);
					break;
				}
				break;
			case MB_0011:// 烟雾传感器(开关量设备集成)
				
				if (code == STATUS_0x02) {
					// read_device
					out.writeShort(0);
					out.writeShort(4);
					break;
				}
				break;
			case MB_0012:// 烟雾传感器
				if (code == STATUS_0x03) {
					// read_device
					out.writeShort(3);// 设置起始位置为3
					out.writeShort(1);// 设置数据长度为1
					break;
				}
				break;
			case MB_0013:// 开关量传感器常闭
				if (code == STATUS_0x02) {
					// read_device
					out.writeShort(0);
					out.writeShort(8);
					break;
				}
				break;
			case MB_0014:// 通断控制器
				if (code == STATUS_0x01) {
					// read_device
					out.writeShort(0);
					out.writeShort(1);
					break;
				}
				if (code == STATUS_0x05) {
					// write_device
					MudbusSwitchMB0014 mudbusSwitch = (MudbusSwitchMB0014) device;
					if (mudbusSwitch.isFeed()) {
						out.writeShort(0);
						out.writeShort(0);
					} else {
						out.writeShort(0);
						out.writeByte(0xFF);
						out.writeByte(0);
					}
					break;
				}
				break;
			case MB_0015:// 声光报警器
				if (code == STATUS_0x10) {
					MudbusAudibleAndVisualAlarm dev = (MudbusAudibleAndVisualAlarm) device;
					out.writeShort(0x001A);
					out.writeShort(1);
					boolean beepOne = dev.isBeep();
					boolean light = dev.isLight();
					if (beepOne && light) {
						out.writeByte(1);
					} else if (beepOne && !light) {
						out.writeByte(3);
					} else if (!beepOne && light) {
						out.writeByte(2);
					} else {
						out.writeByte(0);
					}
					break;
				}
				break;
			case MB_0016:// 开关量传感器常开-用于配电室托管
				if (code == STATUS_0x02) {
					out.writeShort(0); // 寄存器地址
					out.writeShort(1); // 寄存器数量
				}
				break;
			case MB_0017:// 开关量传感器常闭-用于配电室托管
				if (code == STATUS_0x02) {
					out.writeShort(0); // 寄存器地址
					out.writeShort(1); // 寄存器数量
				}
				break;
			case MB_0018:// 断电传感器(开关量设备集成)
				if (code == STATUS_0x02) {
					// read_device
					out.writeShort(0);
					out.writeShort(4);
					break;
				}
				break;
			case MB_0019:// 开关量传感器-常闭(开关量设备集成)
				if (code == STATUS_0x02) {
					// read_device
					out.writeShort(0);
					out.writeShort(4);
					break;
				}
				break;
			case MB_0020:// MudBus继电器控制(ZZ-IO204开关量集成)(第一路)
				if (code == STATUS_0x01) {
					// read_device
					out.writeShort(0);
					out.writeShort(2);
					break;
				}
				//写线圈操作（控制开关）
				if (code == STATUS_0x05) {
					// write_device
					MudbusSwitchMB0020 mudbusSwitch = (MudbusSwitchMB0020) device;
					if (!mudbusSwitch.isFeed()) {
						out.writeShort(0);
						out.writeByte(0xFF);// 开
						out.writeByte(0);
					} else {
						out.writeShort(0);
						out.writeShort(0);
					}
					break;
				}
				break;
			case MB_0020_01:// MudBus继电器控制(ZZ-IO204开关量集成)(第二路)
				if (code == STATUS_0x01) {
					// read_device
					out.writeShort(1);
					out.writeShort(2);
					break;
				}
				//写线圈操作（控制开关）
				if (code == STATUS_0x05) {
					// write_device
					MudbusSwitchMB0020_01 mudbusSwitch = (MudbusSwitchMB0020_01) device;
					if (!mudbusSwitch.isFeed()) {
						out.writeShort(1);
						out.writeShort(0);
					} else {
						out.writeShort(1);
						out.writeByte(0xFF);// 关（和电源总控开关相反）
						out.writeByte(0);
					}
					break;
				}
				break;
			default:
				break;
		}

		CRC.write(out);
		if (Config.DEBUG)
		Log.debug("send：  " + device.getName() + ":" + (ByteBufUtil.hexDump(out)));
	}

	@Override
	public int frame(Device device, ByteBuf in) {
		// 已收到字节总数
		int readable = in.readableBytes();

		if (readable < 5)
			return Coder.CONTINUE;

		if (readable > 128) {
			device.setStatus(Status.C_OVERFLOW);
			return Coder.OVERFLOW;
		}

		// 协议指令
		short code = in.getUnsignedByte(1);
		// 协议字节数
		short length = 0;
		// 根据指令判断长度
		if (code == STATUS_0x03 || code == STATUS_0x02 || code == STATUS_0x01 || code == STATUS_0x04) {
			length = in.getUnsignedByte(2);
		} else {
			length = 3;
		}

		if (length + 5 > readable) {
			return Coder.CONTINUE;
		} else {
			length += 5;
		}
		return Coder.OK;
	}

	@Override
	public void decode(Device device, ByteBuf in, byte code, int tag) {
//		if (in == null) 
//			return;
		if (device!=null && in!=null) {
		if(Config.DEBUG)
			Log.debug("resv：  " + device.getName() + ": " + (ByteBufUtil.hexDump(in)));
		}
//		 (安防主机V1.1协议中42,43是飞音云功能码)
		int number = 1;
		ModbusSerialRadioFrequencyMB0022 fy22 = null;
		for (int i = 0; i < in.capacity() && i + 5 < in.capacity(); i++) {
			if (Integer.toHexString(in.getUnsignedByte(i)).equalsIgnoreCase("fd") && Integer.toHexString(in.getUnsignedByte(i + 5)).equalsIgnoreCase("df")) {
				String str = fixed(Integer.toHexString(in.getUnsignedByte(i + 1)), 2) + fixed(Integer.toHexString(in.getUnsignedByte(i + 2)), 2) + fixed(Integer.toHexString(in.getUnsignedByte(i + 3)), 2);
				for (Device device2 : Data.getServo().getDevices()) {
					if (device2.getModel() == Model.MB_0021) {
						device2.setStatus(Status.NONE); 
						ModbusSerialRadioFrequencyMB0021 fy = (ModbusSerialRadioFrequencyMB0021) device2;
						if (fy.isLearn()) {
							fy.setSn(str);
							sendSetStatus(fy, "学习成功");
							Data.UpdateDevice(fy);
							fy.setLearn(false);
							fy.setFeed(false);
							break;
						}
						
						// 判断是否报警
						if (str.equalsIgnoreCase(fy.getSn()) && !fy.isFeed()) {
							fy.setFeed(true);
							sendSetStatus(fy, null);
							number++;
						}
					}else if(device2.getModel() == Model.MB_0022){
						device2.setStatus(Status.NONE);
						fy22 = (ModbusSerialRadioFrequencyMB0022) device2;
						switch (fy22.getLearn()) {
							case 1:
								fy22.setNumber(str);
								sendSetStatus(fy22, "学习开成功");
								Data.UpdateDevice(fy22);
								fy22.setLearn(0);
								fy22.setFeed(0);	
								break;
							case 2:
								fy22.setSn(str);
								sendSetStatus(fy22, "学习关成功");
								Data.UpdateDevice(fy22);
								fy22.setLearn(0);
								fy22.setFeed(0);
								break;
							default:
								break;
						}
//						if (Config.DEBUG)
//						Log.debug("设备:" + fy22.getName()+"是否学习  " + fy22.getLearn());
						if (str.equalsIgnoreCase(fy22.getNumber()) ) {
							fy22.setFeed(1);
						}else if (str.equalsIgnoreCase(fy22.getSn()) ) {
							fy22.setFeed(2);
						}
						number++;
					}
				}
				device = null;
			}
		}
		if (number != 1) {
			if (fy22 != null) {
				sendSetStatus(fy22, null);
			}
			// 将buf清零
			in.setZero(0,in.capacity());
		}

		if (device == null) {
			return;
		}

		if (in.readableBytes() == 0) {
			device.setStatus(Status.C_TIMEOUT);
			return;
		}

		// 地址 B1
		if ((byte) Integer.parseInt(device.getNumber()) != in.readByte()) {
			device.setStatus(Status.C_ADDRESS);
			return;
		}

		// 代码 B1
		if (code != in.readByte()) {
			device.setStatus(Status.C_COMMAND);
			return;
		}

		// 复位正常
		device.setStatus(Status.NONE);

		// 长度 B1
		in.readByte();

		switch (code) {
			case STATUS_0x03:
				switch (device.getModel()) {
				// 光照度传感器
					case MB_0001: {
						MudbusIluminaceSensor dev = (MudbusIluminaceSensor) device;
						int readInt = in.readInt();
						dev.setDegree(readInt);
						break;
					}
					// 温湿度采集器(集成与PM传感器于一体)
					case MB_0002: {
						if (in.readableBytes()!=6) 
							break;
						MudbusHumitureSensorMB0002 dev = (MudbusHumitureSensorMB0002) device;
						short readShort = in.readShort();
						dev.getFeature(0).setValue(readShort);
						readShort = in.readShort();
						dev.getFeature(1).setValue(readShort);
						// 主动推到APP
						sendSetStatus(dev, null);
						break;
					}
					// 温湿度传感器(用于配电室托管)
					case MB_0004: {
						MudbusHumitureSensorMB0004 dev = (MudbusHumitureSensorMB0004) device;
						dev.setMoisture((float) (in.readShort() / 10.0));
						dev.setTemperature((float) (in.readShort() / 10.0));
						break;
					}
					// 土壤温湿度传感器
					case MB_0005: {
						MudbusSoilHumitureSensor dev = (MudbusSoilHumitureSensor) device;
						short readShort = in.readShort();
						dev.getFeature(0).setValue(readShort);
						readShort = in.readShort();
						dev.getFeature(1).setValue(readShort);
						break;
					}
					// 甲烷采集器(单个设备)
					case MB_0006: {
						MudbusMethaneSensorMB0006 dev = (MudbusMethaneSensorMB0006) device;
						/*
						 * 例如可燃气体 0x0002(十六进制)=2=>可燃气体=0.2% 该值为爆炸下限 百分比
						 * 具体参考JXMethaneSensor
						 */
						short readShort = in.readShort();
						dev.setDegree(readShort);
						break;
					}
					// PM2.5/PM10采集器
					case MB_0008: {
						if (in.readableBytes()!=14) 
							break;
						MudbusPMSensor dev = (MudbusPMSensor) device;
						short pm25 = in.readShort();/* 读取PM2.5值 */
						dev.setPM25(pm25);
						// 读取无用值8个
						in.readInt();
						in.readInt();
						short pm10 = in.readShort();/* 读取PM10值 */
						dev.setPM10(pm10);
						sendSetStatus(dev, null);
						break;
					}
					// 断电传感器(用于配电室托管)
					case MB_0009: {
						MudbusPowerSensorMB0009 dev = (MudbusPowerSensorMB0009) device;
						// 状态 0000通/0001断
						if (in.readShort() == 1) {
							dev.setFeed(true);// 断开
						} else {
							dev.setFeed(false);// 接通
						}
						break;
					}
					// 一氧化碳采集器
					case MB_0010: {
						MudbusCOSensor dev = (MudbusCOSensor) device;
						short co = in.readShort();/* 读取CO值 189-> 18.9ppm */
						dev.getFeature(0).setValue(co);
						break;
					}
					// 烟雾传感器
					case MB_0012: {
						MudbusSmokeSensorMB0012 dev = (MudbusSmokeSensorMB0012) device;
						// 状态 0000正常/0001报警
						dev.setFeed(in.readShort() == 1);
						break;
					}
					case MB_0015:// 声光报警器
						break;
					case MB_0016:// 开关量传感器常开-用于配电室托管
						break;
					case MB_0017:// 开关量传感器常闭-用于配电室托管
						break;
					default:
						break;
				}
				break;
			case STATUS_0x02: {
				if (device.getModel() == Model.MB_0013) {
					MudbusSwitchingOffSensorMB0013 mudbusSwitchingOffSensor = (MudbusSwitchingOffSensorMB0013) device;
					byte feed = in.readByte();
					mudbusSwitchingOffSensor.getFeature(0).setValue(feed);
					break;
				}
				if (device.getModel() == Model.MB_0017) {
					MudbusSwitchingOffSensorMB0017 dev = (MudbusSwitchingOffSensorMB0017) device;
					// 新版本传感器
					byte readByte = in.readByte();
					if (readByte == 1) {
						dev.setFeed(true);
					} else {
						dev.setFeed(false);
					}
					break;
				}
				if (device.getModel() == Model.MB_0016) {
					MudbusSwitchingOpenSensorMB0016 dev = (MudbusSwitchingOpenSensorMB0016) device;
					// 新版本传感器
					byte readByte = in.readByte();
					if (readByte == 1) {
						dev.setFeed(true);
					} else {
						dev.setFeed(false);
					}
					break;
				}
				if (device.getModel() == Model.MB_0011) { /* 烟雾传感器 */
					MudbusSmokeSensorMB0011 dev = (MudbusSmokeSensorMB0011) device;
					byte source = in.readByte();
					dev.setFeed(getBit(source, 2));
					break;
				}
				if (device.getModel() == Model.MB_0007) { /* 可燃气体 */
					MudbusCombustibleGasSensor dev = (MudbusCombustibleGasSensor) device;
					byte source = in.readByte();
					dev.setFeed(getBit(source, 1));
					break;
				}
				if (device.getModel() == Model.MB_0018) { /* 断电传感器 */
					if (in.readableBytes()!=3) 
						break;
					MudbusPowerSensorMB0018 dev = (MudbusPowerSensorMB0018) device;
					byte source = in.readByte();
					// 20190128  修改反一下
					dev.setFeed(!getBit(source, 3));
					sendSetStatus(dev, null);
					break;
				}
				if (device.getModel() == Model.MB_0019) { /* 开关量传感器 门磁 */
					MudbusSwitchingOffSensorMB0019 dev = (MudbusSwitchingOffSensorMB0019) device;
					byte source = in.readByte();
					dev.setFeed(getBit(source, 4));
					break;
				}
				break;
			}
			case STATUS_0x01: {
				if (device.getModel() == Model.MB_0014) {
					MudbusSwitchMB0014 mudbusSwitch = (MudbusSwitchMB0014) device;
					byte readbyte = in.readByte();/* 0为开 1为关 */
					mudbusSwitch.setFeed(readbyte != 0);
					break;
				}
				if (device.getModel() == Model.MB_0020) {
					if (in.readableBytes()!=3) 
						break;
					MudbusSwitchMB0020 mudbusSwitch = (MudbusSwitchMB0020) device;
					byte source = in.readByte();
					boolean bit = getBit(source, 1);
					mudbusSwitch.setFeed(!bit);
					sendSetStatus(mudbusSwitch, null);
					break;
				}
				//读取第二位开关状态
				if (device.getModel() == Model.MB_0020_01) {
					if (in.readableBytes()!=3) 
						break;
					MudbusSwitchMB0020_01 mudbusSwitch = (MudbusSwitchMB0020_01) device;
					byte source = in.readByte();
					Log.debug((ByteBufUtil.hexDump(in)));
					boolean bit = getBit(source, 1);
					mudbusSwitch.setFeed(bit);
					sendSetStatus(mudbusSwitch, null);
					break;
				}
				break;
			}
			case STATUS_0x05: {
				if (device.getModel() == Model.MB_0014) {
					MudbusSwitchMB0014 mudbusSwitch = (MudbusSwitchMB0014) device;
					in.readByte();
					if (in.readByte() == -1) { /* 关 */
						mudbusSwitch.setFeed(true);
					} else {/* 开 */
						mudbusSwitch.setFeed(false);
					}
					in.readByte();
					break;
				}
				if (device.getModel() == Model.MB_0020) {
					MudbusSwitchMB0020 mudbusSwitch = (MudbusSwitchMB0020) device;
					in.readByte();
					if (in.readByte() == 0) {
						mudbusSwitch.setFeed(true);
					} else {/* 开 */
						mudbusSwitch.setFeed(false);
					}
					in.readByte();
					break;
				}
				if (device.getModel() == Model.MB_0020_01) {
					MudbusSwitchMB0020_01 mudbusSwitch = (MudbusSwitchMB0020_01) device;
					in.readShort();
					if (in.readByte() == 0) {/* 开 */
						mudbusSwitch.setFeed(false);
					} else {
						mudbusSwitch.setFeed(true);
					}
					break;
				}
				break;
			}

			case STATUS_0x10: {
				if (device.getModel() == Model.MB_0015) {
					MudbusAudibleAndVisualAlarm dev = (MudbusAudibleAndVisualAlarm) device;
					// 状态01为开/00为关
					byte readByte = in.readByte();
					if (readByte == 0) {
						dev.setBeep(false);
						dev.setLight(false);
					} else if (readByte == 1) {
						dev.setBeep(true);
						dev.setLight(true);
					} else if (readByte == 2) {
						dev.setBeep(false);
						dev.setLight(true);
					} else if (readByte == 3) {
						dev.setBeep(true);
						dev.setLight(false);
					}
					break;
				}
				break;
			}
			case STATUS_0x04: {
				if (device.getModel() == Model.MB_0003) {
					MudbusHumitureSensorMB0003 dev = (MudbusHumitureSensorMB0003) device;
					short readShort = in.readShort();
					dev.getFeature(1).setValue(readShort);
					readShort = in.readShort();
					dev.getFeature(0).setValue(readShort);
					sendSetStatus(dev, null);
					break;
				}
				break;
			}
			default:
				break;
		}

		// CRC16
		in.skipBytes(2);
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
//		Linkager.executeLinkage(d);// 设备上报触发联动
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}

	/**
	 * 返回指定长度字符串（前面补len-str.length()个0）
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	private String fixed(String str, int len) {
		if (null == str || str.isEmpty()) {
			str = "";
		}
		if (str.length() != len)
			for (int i = 0; i < len - str.length(); i++) {
				str = "0" + str;
			}
		return str;
	}
	
}
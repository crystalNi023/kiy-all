/**
 * 2017年2月16日
 */
package com.kiy.servo.driver.ks;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.kiy.common.Device;
import com.kiy.common.Tool;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.KSAir;
import com.kiy.common.devices.KSConcentrator;
import com.kiy.common.devices.KSCurtainController;
import com.kiy.common.devices.KSElectricityMeter1;
import com.kiy.common.devices.KSGasMeter;
import com.kiy.common.devices.KSIlluminanceSensor;
import com.kiy.common.devices.KSMoistureSensor;
import com.kiy.common.devices.KSPMSensor;
import com.kiy.common.devices.KSSmokeSensor;
import com.kiy.common.devices.KSSoilMosistureTemperatureSensor;
import com.kiy.common.devices.KSSwitch;
import com.kiy.common.devices.KSSwitchingOffSensor;
import com.kiy.common.devices.KSSwitchingOpenSensor;
import com.kiy.common.devices.KSTemperatureMoistureSensor;
import com.kiy.common.devices.KSTemperatureSensor;
import com.kiy.common.devices.KSValve;
import com.kiy.common.devices.KSWaterMeter;
import com.kiy.common.devices.KSWindowController;
import com.kiy.servo.Log;
import com.kiy.servo.driver.Coder;

/**
 * 凯星设备通信协议编解码辅助
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class KSCoder extends Coder {
	/**
	 * 报头,发送到集中器
	 */
	protected static final byte HEAD_DOWN = 'S';// 0x53
	/**
	 * 报头,接收从集中器
	 */
	protected static final byte HEAD_UP = 'T';// 0x54

	/**
	 * 连接集中器（旧版 无密码）
	 */
	protected static final byte CODE_HAND = 0x73;
	/**
	 * 连接集中器（新版 密码登录）
	 */
	protected static final byte CONNECT = 0x0F;
	/**
	 * 断开集中器（旧）, 无返回指令
	 */
	protected static final byte DISCONNECT = 0x01;
	/**
	 * 断开集中器(新),无返回指令
	 */
	protected static final byte CODE_DISCONNECT_NO = (byte) 0xC9;
	/**
	 * 获取集中器时间
	 */
	protected static final byte GET_DATETIME = 0x08;
	/**
	 * 设置集中器时间
	 */
	protected static final byte SET_DATETIME = 0x04;
	/**
	 * 获取表设备状态
	 */
	protected static final byte GET_METER_STATUS = 0x3F;
	/**
	 * 设置表设备状态
	 */
	protected static final byte SET_METER_STATUS = 0x32;
	/**
	 * 获取表设备多项数据
	 */
	protected static final byte GET_METER_DATA = 0x40;
	/**
	 * 设置表工作模式
	 */
	protected static final byte SET_METER_MODE = 0x37;
	/**
	 * 设置表计费模式
	 */
	protected static final byte SET_METER_CHARGE = 0x36;
	/**
	 * 读取表计费模式
	 */
	protected static final byte GET_METER_CHARGE = 0x44;
	/**
	 * 设置集中器参数
	 */
	protected static final byte SET_CONCENTRATOR = (byte) 203;
	/**
	 * 获取集中器参数
	 */
	protected static final byte GET_CONCENTRATOR = (byte) 207;
	/**
	 * 集中器添加设备(用于自动抄读)
	 */
	protected static final byte ADD_DEVICE = (byte) 205;
	/**
	 * 集中器删除设备(用于自动抄读)
	 */
	protected static final byte DEL_DEVICE = (byte) 106;

	public KSCoder() {
	}

	@Override
	public int getMaxFrame() {
		return 255;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {
		// 报文格式 [HEAD 1|LEN 1|CODE 1|DATA *|CS 1|CX 1] LEN = [LEN~CX]

		// 报头
		out.writeByte(KSCoder.HEAD_DOWN);
		// 报长
		out.writeByte(0);
		// 指令 1B
		out.writeByte(code);

		switch (code) {
			case CONNECT: {
				KSConcentrator dvc = (KSConcentrator) device;
				// 集中器序号 4B
				out.writeIntLE(Integer.parseInt(dvc.getNumber()));
				// 集中器密码 10B
				if (Tool.isEmpty(dvc.getPassword())) {
					out.writeZero(10);
				} else {
					byte[] values = null;
					try {
						values = dvc.getPassword().getBytes("ASCII");
					} catch (UnsupportedEncodingException ex) {
						Log.error(ex);
					}
					if (values == null)
						out.writeZero(10);
					else
						out.writeBytes(values, 0, 10);
				}
				// OK 53 0E 0F 00 00 00 00 00 00 00 00 00 00 1D 01
				// OK 53 12 0F 00 00 00 00 00 00 00 00 00 00 00 00 00 00 21 1D
			}
				break;
			case GET_DATETIME: {
				// 无额外数据
			}
				break;
			case SET_DATETIME: {
				KSConcentrator dvc = (KSConcentrator) device;
				// 时间 7B
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dvc.getDatetime());
				out.writeByte(calendar.get(Calendar.SECOND)); // 0~59
				out.writeByte(calendar.get(Calendar.MINUTE)); // 0~59
				out.writeByte(calendar.get(Calendar.HOUR_OF_DAY)); // 0~23
				out.writeByte(calendar.get(Calendar.DAY_OF_MONTH)); // 1~31
				out.writeByte(calendar.get(Calendar.MONTH) + 1); // 1~12
				out.writeByte(calendar.get(Calendar.DAY_OF_WEEK)); // 0~6
				out.writeByte(calendar.get(Calendar.YEAR) - 2000); // 0~99
			}
				break;
			case GET_METER_STATUS: {
				// 表号 3B
				out.writeMediumLE(Integer.parseInt(device.getNumber()) + tag);
				// 中继表号 3B*N
				List<Device> relays = device.getRelays();
				for (int index = 1; index < relays.size(); index++) {
					out.writeMediumLE(Integer.parseInt(relays.get(index).getNumber()));
				}
			}
				break;
			case SET_METER_STATUS: {
				// 表选项 1B
				byte option = 0;
				// 表状态 1B
				byte status = 0;
				// 按表号
				switch (device.getModel()) {
				// encode write_device 单项电表
					case KS_0003: {
						KSElectricityMeter1 dvc = (KSElectricityMeter1) device;
						// 通断
						option = setBit(option, true, 8);
						if (dvc.isFeed()) {
							status = setBit(status, false, 8);// Bit7=0 电表通电
						} else {
							status = setBit(status, true, 8);// bit7=1电表断电
						}
						// 蜂鸣
						option = setBit(option, true, 6);
						if (dvc.isBuzzer()) {
							status = setBit(status, true, 7);// bit6=1电表告警
						} else {
							status = setBit(status, false, 7);// Bit6=0 电表关告警
						}
					}
						break;
					// encode write_device 空调学习控制器
					case KS_0020: {
						KSAir dvc = (KSAir) device;
						option = setBit(option, true, 8);
						if (dvc.getLearn()) {
							status = setBit(status, true, 8); // Bit7==1 为学习指令
						} else {
							status = setBit(status, false, 8);// bit7=0 为控制指令
						}

						// 蜂鸣
						option = setBit(option, true, 6);
						status = setBit(status, false, 7);// Bit6=0 电表关告警
					}
						break;
					// encode write_device 窗帘控制器
					case KS_0016: {
						KSCurtainController dvc = (KSCurtainController) device;
						// 通断
						if (dvc.isStop()) {
							option = setBit(option, true, 6);
							status = setBit(status, true, 7);// bit6=1电表告警
						} else {
							// 通断
							option = setBit(option, true, 8);
							if (dvc.isFeed()) {
								status = setBit(status, false, 8);// Bit7=0 电表通电
							} else {
								status = setBit(status, true, 8);// bit7=1电表断电
							}
						}
					}
						break;
					// encode write_device 陕西凯星窗户控制器
					case KS_0017: {
						KSWindowController dvc = (KSWindowController) device;
						// 停止
						if (dvc.isStop()) {
							option = setBit(option, true, 6);
							status = setBit(status, true, 7);// bit6=1电表告警
						} else {
							// 通断
							option = setBit(option, true, 8);
							if (dvc.isFeed()) {
								status = setBit(status, false, 8);// Bit7=0 电表通电
							} else {
								status = setBit(status, true, 8);// bit7=1电表断电
							}
						}
					}
						break;
					// encode write_device 燃气表，陕西凯星电子
					case KS_0005: {
						KSGasMeter dvc = (KSGasMeter) device;
						// 通断
						option = setBit(option, true, 8);
						if (dvc.isFeed())
							status = setBit(status, false, 8);// Bit7=0 电表通电
						else
							status = setBit(status, true, 8);// bit7=1电表断电
						// 蜂鸣
						option = setBit(option, true, 6);
						if (dvc.isBuzzer())
							status = setBit(status, true, 7);// bit6=1电表告警
						else
							status = setBit(status, false, 7);// Bit6=0 电表关告警
					}
						break;
					// encode write_device 陕西凯星通断控制器
					case KS_0019: {
						KSSwitch dvc = (KSSwitch) device;
						// 通断
						option = setBit(option, true, 8);
						if (dvc.isFeed())
							status = setBit(status, false, 8);// Bit7=0 电表通电
						else
							status = setBit(status, true, 8);// bit7=1电表断电
					}
						break;
					// encode write_device 阀门控制，陕西凯星电子
					case KS_0018: {
						KSValve dvc = (KSValve) device;
						// 阀门：0全关,0.3开,0.6开,1全开
						if (dvc.getFeed() <= 0) {
							option = setBit(option, true, 6);
							status = setBit(status, true, 7);// b6=1全关
						} else if (dvc.getFeed() > 0 && dvc.getFeed() <= 30) {
							option = setBit(option, true, 8);
							status = setBit(status, true, 8);// b7=1 1/3开
						} else if (dvc.getFeed() > 30 && dvc.getFeed() < 100) {// 0.6
							option = setBit(option, true, 6);
							status = setBit(status, false, 7);// b6=0 2/3开
						} else if (dvc.getFeed() >= 100) {
							option = setBit(option, true, 8);
							status = setBit(status, false, 8);// b7=0全开
						}
					}
						break;
					default:
						break;
				}
				out.writeByte(option);
				out.writeByte(status);

				// 表功率 1B
				out.writeByte(0);
				// 群号 1B
				out.writeByte(0);
				// 表号 3B
				if (device.getModel() == Model.KS_0020) {
					/*
					 * 空调控制器是根据陕西集中器通讯协议的地址位来进行设备操控，每一个地址对应的是相应的按钮
					 */
					KSAir dev = (KSAir) device;
					/* 空调控制器的第一个Feature保存的是需要按下的按钮 设备地址位 == 设备起始地址+设备按钮枚举-2 */
					out.writeMediumLE((Integer.parseInt(device.getNumber()) + dev.getControl()));
				} else {
					out.writeMediumLE(Integer.parseInt(device.getNumber()));
					// 中继表号 3B*N
					List<Device> relays = device.getRelays();
					for (int index = 1; index < relays.size(); index++) {
						out.writeMediumLE(Integer.parseInt(relays.get(index).getNumber()));
					}
				}
			}
				break;
		}
		// 报长(实际上是除开报头剩余全部都计算在内)
		out.setByte(1, out.readableBytes() + 1);
		// 校验码
		writeParity(out);

	}

	@Override
	public int frame(Device device, ByteBuf in) {
		int byteLength = in.readableBytes();

		if (byteLength < 2)
			return Coder.CONTINUE;

		if (byteLength > 255) {
			device.setStatus(Status.C_OVERFLOW);
			return Coder.OVERFLOW;
		}

		short length = in.getUnsignedByte(1);

		if (length < 0) {
			device.setStatus(Status.C_FORMAT);
			return Coder.FORMAT;
		}

		if (length == 0) {
			// T 00
			length = 2;
		} else if (length == 0xFF) {
			// T FF
			length = 2;
		} else if (length == 0xFE) {
			// T FE
			length = 2;
		} else if (length == 0xFD) {
			// T FD
			length = 2;
		} else if (length + 1 > byteLength)
			return Coder.CONTINUE;
		else {
			length += 1;
		}

		return Coder.OK;
	}

	@Override
	public void decode(Device device, ByteBuf in, byte code, int tag) {
		if (in.readableBytes() == 0) {
			// 超时未收到数据
			device.setStatus(Status.C_TIMEOUT);
			return;
		}
		// 报头
		if (in.readByte() != KSCoder.HEAD_UP) {
			// 报头不匹配
			device.setStatus(Status.C_FORMAT);
			return;
		}
		// 报长
		short length = in.readUnsignedByte();
		if (length == 0) {
			// T 00
			device.setStatus(Status.NONE);
			return;
		} else if (length == 0xFF) {
			// 数据错误
			device.setStatus(Status.C_UNKNOWN);
			return;
		} else if (length == 0xFE) {
			// 终端设备无应答
			device.setStatus(Status.OFFLINE);
			return;
		} else if (length == 0xFD) {
			// 集中器忙
			device.setStatus(Status.C_BUSY);
			return;
		} else {
			// 复位为正常
			device.setStatus(Status.NONE);
		}
		// 命令
		if (code != in.readByte()) {
			device.setStatus(Status.C_COMMAND);
			return;
		}

		switch (code) {
			case CONNECT: {
				KSConcentrator dvc = (KSConcentrator) device;
				// 读取密码 10B
				dvc.setPassword((String) in.readCharSequence(10, Charset.forName("ASCII")));
				// 读取时间 7B
				dvc.setDatetime(KSCoder.newDate(in.readByte(), // 秒
					in.readByte(), // 分
					in.readByte(), // 时
					in.readByte(), // 日
					in.readByte(), // 月
					in.readByte(), // 星期
					in.readByte() // 年
				));
				// 54 15 F 0 0 0 0 0 0 0 0 0 0 31 13 C 1D C 4 10 B1 31
			}
				break;
			case GET_DATETIME: {
				KSConcentrator dvc = (KSConcentrator) device;
				// 读取时间 7B
				dvc.setDatetime(KSCoder.newDate(in.readByte(), // 秒
					in.readByte(), // 分
					in.readByte(), // 时
					in.readByte(), // 日
					in.readByte(), // 月
					in.readByte(), // 星期
					in.readByte() // 年
				));
			}
				break;
			case SET_DATETIME: {
				// T00 不会到达此处
			}
				break;
			case GET_METER_STATUS: {
				// 表状态 1B
				byte status = in.readByte();
				switch (device.getModel()) {
				// decode read_device 单相电表，陕西凯星电子
					case KS_0003: {
						KSElectricityMeter1 dvc = (KSElectricityMeter1) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// 蜂鸣
						if (getBit(status, 7))
							dvc.setBuzzer(true);// bit6=1 电表告警
						else
							dvc.setBuzzer(false);// bit6=0 电表不告警

						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 窗帘控制器，陕西凯星电子
					case KS_0016: {
						KSCurtainController dvc = (KSCurtainController) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
						dvc.setDegree(in.readMediumLE());
					}
						break;
					// decode read_device 窗户控制器 凯星电子
					case KS_0017: {
						KSWindowController dvc = (KSWindowController) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
						dvc.setDegree(in.readMediumLE());
					}
						break;
					// decode read_device 燃气表，陕西凯星电子
					case KS_0005: {
						KSGasMeter dvc = (KSGasMeter) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// 蜂鸣
						if (getBit(status, 7))
							dvc.setBuzzer(true);// bit6=1 电表告警
						else
							dvc.setBuzzer(false);// bit6=0 电表不告警

						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decoed read_device 开关量传感器，陕西凯星电子 (长开)
					case KS_0014: {
						KSSwitchingOpenSensor dvc = (KSSwitchingOpenSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree(in.readMediumLE() > 0);
					}
						break;
					// decode read_device 开关量传感器，陕西凯星电子 (长闭)
					case KS_0015: {
						KSSwitchingOffSensor dvc = (KSSwitchingOffSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree(in.readMediumLE() > 0);
					}
						break;
					// decode read_device 水表，陕西凯星电子
					case KS_0006: {
						KSWaterMeter dvc = (KSWaterMeter) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// 流向
						if (getBit(status, 7))
							dvc.setFlow(false);// bit6=1 电表告警
						else
							dvc.setFlow(true);// bit6=0 电表反转
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星PM2.5采集
					case KS_0012: {
						KSPMSensor dvc = (KSPMSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星光照采集
					case KS_0007: {
						KSIlluminanceSensor dvc = (KSIlluminanceSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星湿度采集
					case KS_0008: {
						KSMoistureSensor dvc = (KSMoistureSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星温湿度采集
					case KS_0010: {
						KSTemperatureMoistureSensor dvc = (KSTemperatureMoistureSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						if (tag == 0) {
							dvc.setTemperature((float) (in.readMediumLE() / 100.00));
						} else {
							dvc.setMoisture((float) (in.readMediumLE() / 100.00));
						}

					}
						break;
					// decode read_device 陕西凯星土壤温湿度采集
					case KS_0011 : {
						KSSoilMosistureTemperatureSensor dvc = (KSSoilMosistureTemperatureSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						if (tag == 0) {
							dvc.setMoisture((float) (in.readMediumLE() / 100.00));
						} else {
							dvc.setTemperature((float) (in.readMediumLE() / 100.00));
						}

					}
						break;
					// decode read_device 陕西凯星温度采集
					case KS_0009: {
						KSTemperatureSensor dvc = (KSTemperatureSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setTemperature((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星烟雾传感器
					case KS_0013: {
						KSSmokeSensor dvc = (KSSmokeSensor) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						dvc.setDegree((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星通断控制器
					case KS_0019: {
						KSSwitch dvc = (KSSwitch) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常

						// 表底数 3B
						if (tag == 0)
							dvc.setA((float) (in.readMediumLE() / 100.00));
						else
							dvc.setV((float) (in.readMediumLE() / 100.00));
					}
						break;
					// decode read_device 陕西凯星阀门
					case KS_0018: {
						KSValve dvc = (KSValve) device;
						int value = status >> 6 & 3;
						if (value == 0)
							dvc.setFeed(100);
						else if (value == 1)
							dvc.setFeed(60);
						else if (value == 2)
							dvc.setFeed(30);
						else if (value == 3)
							dvc.setFeed(0);
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					default:
						break;
				}
			}
				break;
			case SET_METER_STATUS: {
				// 表状态 1B
				byte status = in.readByte();
				switch (device.getModel()) {
				// decode write_device 陕西凯星单项电表
					case KS_0003: {
						KSElectricityMeter1 dvc = (KSElectricityMeter1) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// 蜂鸣
						if (getBit(status, 7))
							dvc.setBuzzer(true);// bit6=1 电表告警
						else
							dvc.setBuzzer(false);// bit6=0 电表不告警

						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					// decode write_device 陕西凯星空调控制器
					case KS_0020: {
						KSAir dvc = (KSAir) device;
						dvc.setStatus(Status.NONE);
					}
						break;
					// decode write_device 陕西凯星窗帘控制器
					case KS_0016: {
						KSCurtainController dvc = (KSCurtainController) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					// decode write_device 陕西凯星窗户控制器
					case KS_0017: {
						KSWindowController dvc = (KSWindowController) device;
						// 工作
						if (getBit(status, 8))
							dvc.setFeed(false);// bit7=1 电表断电
						else
							dvc.setFeed(true);// bit7=0 电表通电
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					// decode write_device 陕西凯星通断控制器
					case KS_0019: {
						KSSwitch dvc = (KSSwitch) device;
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					// decode write_device 陕西凯星阀门
					case KS_0018: {
						KSValve dvc = (KSValve) device;
						int value = status >> 6 & 3;
						if (value == 0)
							dvc.setFeed(100);
						else if (value == 1)
							dvc.setFeed(60);
						else if (value == 2)
							dvc.setFeed(30);
						else if (value == 3)
							dvc.setFeed(0);
						// ROM
						if (getBit(status, 6))
							dvc.setRom(true);// bit5=1 ROM出错旦纠正
						else
							dvc.setRom(true);// bit5=0 ROM正常
						if (getBit(status, 5))
							dvc.setRom(false);// bit4=1 ROM坏掉
						else
							dvc.setRom(true);// bit4=0 ROM正常
						// 信号
						if (getBit(status, 3))
							dvc.setSignal(false);// bit2=1 信号线异常
						else
							dvc.setSignal(true);
						// 时钟
						if (getBit(status, 2))
							dvc.setClock(false);// bit1=1 无时钟
						else
							dvc.setClock(true);// bit1=0 时钟正常
					}
						break;
					// decode write_device 陕西凯星燃气表
					case KS_0005:
						break;
					default:
						break;
				}
			}
				break;
			default:
				break;
		}
		// 校验
		in.readByte();
		in.readByte();

	}

	/**
	 * 计算并添加报尾校验位
	 * 
	 * @param buffer
	 */
	private void writeParity(ByteBuf buffer) {
		byte cs, cx, value;
		int index = buffer.readerIndex();
		// 报头排除
		index++;
		// 从长度开始
		cs = cx = buffer.getByte(index++);
		for (; index < buffer.readableBytes(); index++) {
			value = buffer.getByte(index);
			cs += value;
			cx ^= value;
		}
		buffer.writeByte(cs);
		buffer.writeByte(cx);
	}

	public static Date newDate(int second, int minute, int hour, int day, int month, int week, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year + 2000, month, day, hour, minute, second);
		return calendar.getTime();
	}
}
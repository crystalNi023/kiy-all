/**
 * 2017年6月19日
 */
package com.kiy.servo.driver.rs485;

import com.kiy.common.Device;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.ELCPowerMeter;
import com.kiy.servo.driver.Coder;

import io.netty.buffer.ByteBuf;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class ELCCoder extends Coder {
	/**
	 * 获取继电器输出状态(不通)
	 */
	public static final byte STATUS_RELAY = 0x01;
	/**
	 * 获取开关量输入状态（不通）
	 */
	public static final byte STATUS_SWITCH = 0x02;
	/**
	 * 控制单路继电器
	 */
	public static final byte CONTROL_RELAY = 0x05;
	/**
	 * 控制多路继电器
	 */
	public static final byte CONTROL_RELAYS = 0x0F;

	/**
	 * 读取寄存器
	 */
	public static final byte DEGREE = 0x03;
	/**
	 * 读取寄存器
	 */
	public static final byte DEGREE_2 = 0x04;
	/**
	 * 设置数据
	 */
	public static final byte CONFIG = 0x10;

	@Override
	public int getMaxFrame() {
		return 128;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {
		ELCPowerMeter dvc = (ELCPowerMeter) device;
		// 地址
		out.writeByte(dvc.getByteNO());
		// 代码
		out.writeByte(code);

		switch (code) {
			case STATUS_RELAY: {
				// 起始继电器地址:0x0000(固定)
				out.writeZero(2);
				// 继电器个数:0x0004(最大)
				out.writeShort(dvc.getPhaseCheck());
			}
				break;
			case STATUS_SWITCH: {
				// 起始开关地址:0x0000(固定)
				out.writeZero(2);
				// 开关个数:0x000C最大
				out.writeShort(0x0C);
			}
				break;
			case DEGREE:
			case DEGREE_2: {
				// 起始寄存器地址:0x0001~0x004E
				out.writeShort(0x001E);
				// 寄存器个数
				out.writeShort(0x0031);
			}
				break;
			case CONTROL_RELAY: {
				// 继电器地址:0x0000~0x0003
				out.writeShort(0x0003);
				// 继电器状态:0xFF00开/0x0000关
				out.writeShort(0xFF00);
			}
				break;
			case CONTROL_RELAYS: {
				// 起始继电器:0x0000(固定)
				out.writeShort(0x0000);
				// 继电器个数:0x0004(固定)
				out.writeShort(0x0004);
				// 数据字节数
				out.writeByte(1);
				// 设置状态
				byte value = 0x00;
				value = Coder.setBit(value, dvc.getRelay1(), 1);
				value = Coder.setBit(value, dvc.getRelay2(), 2);
				value = Coder.setBit(value, dvc.getRelay3(), 3);
				value = Coder.setBit(value, dvc.getRelay4(), 4);
				out.writeByte(value);
			}
				break;
		}
		CRC.write(out);
		// out.writeByte(0x74);
		// out.writeByte(0xDE);
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

		short code = in.getUnsignedByte(1);
		// 协议字节数
		short length = 0;

		if (code == DEGREE || code == STATUS_RELAY || code == STATUS_SWITCH) {
			length = in.getUnsignedByte(2);
		} else if (code == CONFIG || code == CONTROL_RELAY || code == CONTROL_RELAYS) {
			length = 3;
		} else {
			device.setStatus(Status.C_FORMAT);
			return Coder.FORMAT;
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
		if (in.readableBytes() == 0) {
			device.setStatus(Status.C_TIMEOUT);
			return;
		}

		ELCPowerMeter dvc = (ELCPowerMeter) device;

		if (dvc == null) {
			device.setStatus(Status.C_DRIVER);
			return;
		}

		// 地址 B1
		if (dvc.getByteNO() != in.readByte()) {
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
		in.skipBytes(1);

		switch (code) {
			case STATUS_RELAY: {
				byte value = in.readByte();
				// 1通0关
				dvc.setRelay1(Coder.getBit(value, 1));
				dvc.setRelay2(Coder.getBit(value, 2));
				dvc.setRelay3(Coder.getBit(value, 3));
				dvc.setRelay4(Coder.getBit(value, 4));
			}
				break;
			case STATUS_SWITCH: {
				// 1通0关
				byte value = in.readByte();
				dvc.setSwitch1(Coder.getBit(value, 1));
				dvc.setSwitch2(Coder.getBit(value, 2));
				dvc.setSwitch3(Coder.getBit(value, 3));
				dvc.setSwitch4(Coder.getBit(value, 4));
				dvc.setSwitch5(Coder.getBit(value, 5));
				dvc.setSwitch6(Coder.getBit(value, 6));
				dvc.setSwitch7(Coder.getBit(value, 7));
				dvc.setSwitch8(Coder.getBit(value, 8));
				value = in.readByte();
				dvc.setSwitch9(Coder.getBit(value, 9));
				dvc.setSwitch10(Coder.getBit(value, 10));
				dvc.setSwitch11(Coder.getBit(value, 11));
				dvc.setSwitch12(Coder.getBit(value, 12));
			}
				break;
			case DEGREE:
			case DEGREE_2: {
				// 001E
				dvc.setVUD(in.readShort());
				// 001F
				dvc.setCUD(in.readShort());
				// 0020
				in.skipBytes(2);
				// 0021 开关信息
				byte switch1 = in.readByte();
				byte switch2 = in.readByte();
				dvc.setSwitch1(Coder.getBit(switch1, 1));
				dvc.setSwitch2(Coder.getBit(switch1, 2));
				dvc.setSwitch3(Coder.getBit(switch1, 3));
				dvc.setSwitch4(Coder.getBit(switch1, 4));
				dvc.setRelay1(Coder.getBit(switch1, 5));
				dvc.setRelay2(Coder.getBit(switch1, 6));
				dvc.setRelay3(Coder.getBit(switch1, 7));
				dvc.setRelay4(Coder.getBit(switch1, 8));
				dvc.setSwitch5(Coder.getBit(switch2, 1));
				dvc.setSwitch6(Coder.getBit(switch2, 2));
				dvc.setSwitch7(Coder.getBit(switch2, 3));
				dvc.setSwitch8(Coder.getBit(switch2, 4));
				dvc.setSwitch9(Coder.getBit(switch2, 5));
				dvc.setSwitch10(Coder.getBit(switch2, 6));
				dvc.setSwitch11(Coder.getBit(switch2, 7));
				dvc.setSwitch12(Coder.getBit(switch2, 8));

				// 0022 保留
				in.skipBytes(2);
				// 0023 电压小数点
				byte dpt = in.readByte();
				// 0023 电流小数点
				byte dct = in.readByte();
				// 0024 功率小数点
				byte dpq = in.readByte();
				// 0024 功率符号位
				byte sign = in.readByte();

				dvc.setUA((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setUB((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setUC((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setUAB((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setUBC((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setUCA((float) (in.readShort() / 10000.0 * Math.pow(10, dpt)));
				dvc.setIA((float) (in.readShort() / 10000.0 * Math.pow(10, dct)));
				dvc.setIB((float) (in.readShort() / 10000.0 * Math.pow(10, dct)));
				dvc.setIC((float) (in.readShort() / 10000.0 * Math.pow(10, dct)));

				dvc.setPA((float) (in.readUnsignedShort() / (Coder.getBit(sign, 1) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setPB((float) (in.readUnsignedShort() / (Coder.getBit(sign, 2) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setPC((float) (in.readUnsignedShort() / (Coder.getBit(sign, 3) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setPS((float) (in.readUnsignedShort() / (Coder.getBit(sign, 4) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setQA((float) (in.readUnsignedShort() / (Coder.getBit(sign, 5) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setQB((float) (in.readUnsignedShort() / (Coder.getBit(sign, 6) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setQC((float) (in.readUnsignedShort() / (Coder.getBit(sign, 7) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));
				dvc.setQS((float) (in.readUnsignedShort() / (Coder.getBit(sign, 8) ? -10000.0 : +10000.0) * Math.pow(10, dpq)));

				dvc.setPFA((float) (in.readShort() / 1000.0));
				dvc.setPFB((float) (in.readShort() / 1000.0));
				dvc.setPFC((float) (in.readShort() / 1000.0));
				dvc.setPFS((float) (in.readShort() / 1000.0));
				dvc.setSA((float) (in.readShort() / 10000.0 * Math.pow(10, dpq)));
				dvc.setSB((float) (in.readShort() / 10000.0 * Math.pow(10, dpq)));
				dvc.setSC((float) (in.readShort() / 10000.0 * Math.pow(10, dpq)));
				dvc.setSS((float) (in.readShort() / 10000.0 * Math.pow(10, dpq)));
				dvc.setF((float) (in.readShort() / 100.0));

				dvc.setWPP(in.readInt());
				dvc.setWPN(in.readInt());
				dvc.setWQP(in.readInt());
				dvc.setWQN(in.readInt());
				dvc.setEPP(in.readFloat());
				dvc.setEPN(in.readFloat());
				dvc.setEQP(in.readFloat());
				dvc.setEQN(in.readFloat());

			}
				break;
			case CONTROL_RELAY: {
				// 继电器地址:0x0000~0x0004
				in.readShort();
				// 继电器状态:0xFF00/0x0000
				in.readShort();
			}
				break;
			case CONTROL_RELAYS: {
				// 起始继电器地址:0x0000;
				in.readShort();
				// 继电器个数:0x0004
				in.readShort();
			}
				break;
		}
		// CRC
		in.skipBytes(2);
	}

	// N81/E81/081
}
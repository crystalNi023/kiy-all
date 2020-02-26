/**
 * 2017年6月18日
 */
package com.kiy.servo.driver.rs485;

import com.kiy.common.Device;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.HBElectricityMeter3;
import com.kiy.servo.driver.Coder;

import io.netty.buffer.ByteBuf;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class HBCoder extends Coder {
	public static final byte HEAD = 0x68;
	/**
	 * 读数据
	 */
	public static final byte STATUS = 0x01;
	/**
	 * 电能量
	 */
	public static final byte POWER = (byte) 195;
	/**
	 * 正向电能总电能
	 */
	public static final byte TOTAL_POWER = 0x43;

	@Override
	public int getMaxFrame() {
		return 128;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {
		//cs 校验和
		int cs = 0;
		
		cs = cs+HEAD;
		out.writeByte(HEAD);
		
		String number = device.getNumber();
		if(number.length()==1){
			Byte.parseByte(number);
			out.writeByte(0);
			out.writeByte(0);
			out.writeByte(0);
			out.writeByte(0);
			out.writeByte(0);
			cs = cs+Integer.parseInt(number);
		}else{
			for(int i=0;i<6;i++){
				if(number.length()>=(i+1)*2){
					int iTen = Integer.parseInt(number.substring(number.length()-i*2-2, number.length()-i*2),16);
					out.writeByte(iTen);
					cs = cs+iTen;
				}else{
					out.writeByte(0);
					cs = cs+0;
				}
				
			}
		}	
		
	
		out.writeByte(HEAD);
		
		out.writeByte(code);
		
		out.writeByte(2);

		out.writeByte(TOTAL_POWER);
		
		out.writeByte(POWER);
		
		cs = cs+HEAD+code+2+TOTAL_POWER+POWER;
		String hexString = Integer.toHexString(cs);	
		cs = Integer.parseUnsignedInt(hexString.substring(hexString.length()-2, hexString.length()), 16);
		
		out.writeByte(cs);
		
		out.writeByte(0x16);

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

		// 协议字节数
		short length = in.getUnsignedByte(2);

		if (length + 5 > readable)
			return Coder.CONTINUE;
		else
			length += 5;

		return Coder.OK;
	}

	@Override
	public void decode(Device device, ByteBuf in, byte code, int tag) {
		if (in.readableBytes() == 0) {
			device.setStatus(Status.C_TIMEOUT);
			return;
		}
		
		byte readByte = in.readByte();
		if (readByte == -2) {
			if (in.readByte() != HEAD) {
				return;
			}
		} else {
			if (readByte != HEAD) {
				return;
			}
		}
		
		StringBuilder readString = new StringBuilder();
//		String readString = "";
		for (int i = 0; i < 6; i++) {
			byte readByte2 = in.readByte();
			if (Integer.toHexString(readByte2).length() < 2) {
				readString.append(0);
				readString.append(Integer.toHexString(readByte2));
//				readString = 0 +  + readString;
			} else {
				readString.append(Integer.toHexString(readByte2));
			}
		}

		if(!readString.equals(device.getNumber())){
			device.setStatus(Status.C_ADDRESS);
			return;
		}
		
		in.readByte();
		in.readByte();
		in.readByte();

		if (in.readByte() != TOTAL_POWER) {
			device.setStatus(Status.C_COMMAND);
			return;
		}
		if (in.readByte() != POWER) {
			device.setStatus(Status.C_COMMAND);
			return;
		}

		StringBuilder readData = new StringBuilder();
		
		for (int i = 0; i < 4; i++) {
			String temp = Integer.toHexString(in.readUnsignedByte() - 51);
			if (temp.length() < 2) {
				readData.append(0);
				readData.append(temp);
			} else {
				readData.append(temp);
			}
		}
		
		float degree = (float) (Integer.valueOf(readData.toString()) / 100.0);
		HBElectricityMeter3 dev = (HBElectricityMeter3) device;
		dev.setDegree(degree);
	}
}
/**
 * 2017年6月19日
 */
package com.kiy.servo.driver;

import com.kiy.common.Device;

import io.netty.buffer.ByteBuf;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class Coder {

	/**
	 * 编码/解码/帧验证成功
	 */
	public static final int OK = 0;
	/**
	 * 帧未完(收到半帧)
	 */
	public static final int CONTINUE = 1;
	/**
	 * 帧溢出(超出最长字节)
	 */
	public static final int OVERFLOW = 2;
	/**
	 * 帧未知(无法解析帧)
	 */
	public static final int FORMAT = 3;

	/**
	 * 帧可能的最大长度
	 */
	public abstract int getMaxFrame();

	/**
	 * 输出到设备
	 * 
	 */
	public abstract void encode(Device device, ByteBuf out, byte code, int tag);

	/**
	 * 帧完整性判断（半帧，粘帧）
	 */
	public abstract int frame(Device device, ByteBuf in);

	/**
	 * 从设备读取
	 * 
	 */
	public abstract void decode(Device device, ByteBuf in, byte code, int tag);

	/**
	 * 设置字节位 0为false,1为true,左高右低index[87654321]
	 */
	public static byte setBit(byte source, boolean value, int index) {
		index--;
		byte mask = (byte) (1 << index);
		if (value) {
			source |= mask;
		} else {
			source &= (~mask);
		}
		return source;
	}

	/**
	 * 读取字节位 1为true,0为fasle,左高右低index[87654321]
	 */
	public static boolean getBit(byte source, int index) {
		index--;
		return (source >> index & 1) == 1;
	}
}
package com.kiy.driver;

import java.util.Calendar;
import java.util.Date;

import io.netty.buffer.ByteBuf;

/**
 * 设备通信协议编解码辅助
 * 
 * @author HLX Date: 2018年3月5日
 */
public final class CardCoder {

	/**
	 * 读卡指令(2区8块)
	 */
	public static final int READ_CARD_ONE_ORDER = 0;
	/**
	 * 读卡指令(2区9块)
	 */
	public static final int READ_CARD_TWO_ORDER = 1;
	/**
	 * 写卡指令(2区8块)
	 */
	public static final int WRITE_CARD_ONE_ORDER = 2;
	/**
	 * 写卡指令(2区9块)
	 */
	public static final int WRITE_CARD_TWO_ORDER = 3;

	/**
	 * 读卡(2区8块)
	 */
	private static final byte[] READ_CARD_ONE = { 0x4B, 0x52, 0x30, 0x32, 0x30,
			0x38, 0x21 };
	/**
	 * 读卡(2区9块)
	 */
	private static final byte[] READ_CARD_TWO = { 0x4B, 0x52, 0x30, 0x32, 0x30,
			0x39, 0x21 };

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
	 * 没有找到卡
	 */
	private static final String NO_CARD = "ER1!";
	/**
	 * 读卡机密码错误
	 */
	private static final String PASSWORD_ERROR = "ER4!";
	/**
	 * 写卡成功
	 * 
	 * @return
	 */
	public static final String DONE = "DONE!";
	/**
	 * 写卡 头部(2区8块)
	 */
	private static final byte[] WRITE_HEAD_ONE = { 0x4B, 0x57, 0x30, 0x32,
			0x30, 0x38 };
	/**
	 * 写卡 头部(2区9块)
	 */
	private static final byte[] WRITE_HEAD_TWO = { 0x4B, 0x57, 0x30, 0x32,
			0x30, 0x39 };
	/**
	 * 写卡 尾部(2区8块)
	 */
	private static final byte[] WRITE_QUEUE_ONE = { 0x30, 0x30, 0x30, 0x30,
			0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
			0x30, 0x30, 0x30, 0x21 };
	/**
	 * 写卡 尾部(2区9块)
	 */
	private static final byte[] WRITE_QUEUE_TWO = { 0x30, 0x30, 0x30, 0x30,
			0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
			0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
			0x30, 0x30, 0x21 };

	public int getMaxFrame() {
		return 48;
	}

	public void encode(MCard card, ByteBuf out, int order) {
		switch (order) {
		case READ_CARD_ONE_ORDER:
			out.writeBytes(READ_CARD_ONE);
			break;
		case READ_CARD_TWO_ORDER:
			out.writeBytes(READ_CARD_TWO);
			break;
		case WRITE_CARD_ONE_ORDER:
			// HEAD
			out.writeBytes(WRITE_HEAD_ONE);
			// 卡号
			String cardNumber = card.getCardNumber();
			int parseInt = Integer.parseInt(cardNumber);
			String numHex = Integer.toHexString(parseInt);
			for(int i = 0;i<8-numHex.length();i++){
				out.writeByte(0x30);
			}
			for (int i = 0; i < numHex.length(); i++) {
				char charAt = numHex.charAt(i);
				out.writeByte(charAt);
			}
			// 卡类型
			int type = card.getType();
			out.writeByte(0x30);
			if (type == MCard.NUMBER_TYPE) {
				out.writeByte(0x31);
			} else {
				out.writeByte(0x32);
			}
			// 有效时长
			int hour = card.getHour();
			String hexString = Integer.toHexString(hour);
			for(int i = 0;i<4-hexString.length();i++){
				out.writeByte(0x30);
			}

			for (int i = 0; i < hexString.length(); i++) {
				char charAt = hexString.charAt(i);
				out.writeByte(charAt);
			}
			out.writeBytes(WRITE_QUEUE_ONE);
			break;
		case WRITE_CARD_TWO_ORDER:
			// HEAD
			out.writeBytes(WRITE_HEAD_TWO);
			// 剩余次数
			int number = card.getNumber();
			String numberHex = Integer.toHexString(number);
			for(int i = 0;i<4-numberHex.length();i++){
				out.writeByte(0x30);
			}
			for (int i = 0; i < numberHex.length(); i++) {
				char charAt = numberHex.charAt(i);
				out.writeByte(charAt);
			}

			//尾部
			out.writeBytes(WRITE_QUEUE_TWO);
			break;
		default:
			break;
		}

	}

	public int frame(ByteBuf in) {
		int byteLength = in.readableBytes();

		if (byteLength < 10)
			return CardCoder.CONTINUE;

		if (byteLength > 48) {
			return CardCoder.OVERFLOW;
		}
		short length = in.getUnsignedByte(1);
		if (length < 0) {
			return CardCoder.FORMAT;
		}
		if (length + 1 > byteLength)
			return CardCoder.CONTINUE;

		return CardCoder.OK;
	}

	public void decode(MCard card, ByteBuf in, int order) {
		if (in.readableBytes() < 4) {
			Log.error("读取超时");
			card.setRemark("读取超时");
			return;
		}

		switch (order) {
		case READ_CARD_ONE_ORDER:
			// 读取卡号
			char r1 = (char) in.readByte();
			char r2 = (char) in.readByte();
			char r3 = (char) in.readByte();
			char r4 = (char) in.readByte();
			char[] c = { r1, r2, r3, r4 };
			String s = new String(c);
			if (s.equals(NO_CARD)) {
				// 没有找到卡
				Log.info("Did not find IC card");
				card.setRemark("没有找到IC卡");
			} else if (s.equals(PASSWORD_ERROR)) {
				// 制卡机密码错误
				Log.info("Card machine password is wrong");
				card.setRemark("制卡机密码错误");
			} else {
				Log.info("SUSCESS");
				char r5 = (char) in.readByte();
				char r6 = (char) in.readByte();
				char r7 = (char) in.readByte();
				char r8 = (char) in.readByte();

				char[] charNumber = { r1, r2, r3, r4, r5, r6, r7, r8 };
				int number = Integer.valueOf(new String(charNumber), 16);
				System.out.println("卡号为 = " + number);
				String str = String.format("%08d", number); 
				card.setCardNumber(str);
				// 读取卡的类型
				char r9 = (char) in.readByte();
				char r10 = (char) in.readByte();

				char[] charType = { r9, r10 };
				card.setType(Integer.valueOf(new String(charType)));
				// 读取卡的有效时间
				char r11 = (char) in.readByte();
				char r12 = (char) in.readByte();
				char r13 = (char) in.readByte();
				char r14 = (char) in.readByte();

				char[] charTime = { r11, r12, r13, r14 };
				int time = Integer.valueOf(new String(charTime), 16);
				card.setHour(time);
				in.clear();
			}
			break;
		case READ_CARD_TWO_ORDER:
			char rr1 = (char) in.readByte();
			char rr2 = (char) in.readByte();
			char rr3 = (char) in.readByte();
			char rr4 = (char) in.readByte();
			char[] c1 = { rr1, rr2, rr3, rr4 };
			String s1 = new String(c1);
			if (s1.equals(NO_CARD)) {
				// 没有找到卡
				Log.info("Did not find IC card");
				card.setRemark("没有找到IC卡");
			} else if (s1.equals(PASSWORD_ERROR)) {
				// 制卡机密码错误
				Log.info("Card machine password is wrong");
				card.setRemark("制卡机密码错误");
			} else {
				card.setRemark("读卡成功");
				// 读取记次卡剩余次数
				char[] charCount = { rr1, rr2, rr3, rr4 };
				int count = Integer.valueOf(new String(charCount), 16);
				card.setNumber(count);
				// 读取计时卡第一次刷卡时间
				char rr5 = (char) in.readByte();
				char rr6 = (char) in.readByte();
				char rr7 = (char) in.readByte();
				char rr8 = (char) in.readByte();
				char rr9 = (char) in.readByte();
				char rr10 = (char) in.readByte();
				char rr11 = (char) in.readByte();
				char rr12 = (char) in.readByte();
				char rr13 = (char) in.readByte();
				char rr14 = (char) in.readByte();
				Date newDate = newDate(new char[] { rr13, rr14 }, new char[] {
						rr11, rr12 }, new char[] { rr9, rr10 }, new char[] {
						rr7, rr8 }, new char[] { rr5, rr6 });
				card.setDate(newDate);
				in.clear();
			}
			break;
		case WRITE_CARD_ONE_ORDER:
		case WRITE_CARD_TWO_ORDER:
			char w1 = (char) in.readByte();
			char w2 = (char) in.readByte();
			char w3 = (char) in.readByte();
			char w4 = (char) in.readByte();
			char[] w = { w1, w2, w3, w4 };
			String s2 = new String(w);
			if (s2.equals(NO_CARD)) {
				// 没有找到卡
				Log.info("Did not find IC card");
				card.setRemark("没有找到IC卡");
			} else if (s2.equals(PASSWORD_ERROR)) {
				// 制卡机密码错误
				Log.info("Card machine password is wrong");
				card.setRemark("制卡机密码错误");
			} else {
				char w5 = (char) in.readByte();
				char[] charinfo = { w1, w2, w3, w4, w5 };
				String info = new String(charinfo);
				if (info.equals(DONE)) {
					Log.info("Business card success");
					card.setRemark("制卡成功");
				}
			}
			break;
		default:
			break;
		}
	}

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

	public static Date newDate(char[] minute, char[] hour, char[] day,
			char[] month, char[] year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(new String(year)) + 2000,
				Integer.valueOf(new String(month)),
				Integer.valueOf(new String(day)),
				Integer.valueOf(new String(hour)),
				Integer.valueOf(new String(minute)));
		return calendar.getTime();
	}

	public Integer parse(char s) {
		byte b = (byte) s;
		String hexString = Integer.toHexString(b);
		return Integer.valueOf(hexString);
	}

}
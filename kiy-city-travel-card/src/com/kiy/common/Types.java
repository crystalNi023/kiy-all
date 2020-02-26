package com.kiy.common;

public class Types {
	private Types() {
	}

	/**
	 * 可定义值的枚举
	* @author HLX Tel:18996470535 
	 * @date 2018年4月2日 
	 * Copyright:Copyright(c) 2018
	 */
	public interface EnumValue {
		public int getValue();
	}

	/**
	 * 可聚合的枚举
	* @author HLX Tel:18996470535 
	 * @date 2018年4月2日 
	 * Copyright:Copyright(c) 2018
	 */
	public interface EnumMarge extends EnumValue {
	}
	
	/**
	 * 用户权限
	 * @author HLX Tel:18996470535 
	 * @date 2018年4月2日 
	 * Copyright:Copyright(c) 2018
	 */
	public enum Power implements EnumValue {
		/**
		 * 管理员权限
		 */
		ADMINISTRATOR(1),
		/**
		 * 制卡
		 */
		WRITE_CARD(2),
		/**
		 * 读卡
		 */
		READ_CARD(3),
		/**
		 * 读写卡
		 */
		READ_WRITE_CARD(4);
	

		private final int code;

		private Power(int c) {
			code = c;
		}

		public int getValue() {
			return code;
		}

		public static final Power valueOf(int value) {
			switch (value) {
			case 1:
				return Power.ADMINISTRATOR;
			case 2:
				return Power.WRITE_CARD;
			case 3:
				return Power.READ_CARD;
			case 4:
				return Power.READ_WRITE_CARD;
			default:
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * 卡片类型
	 * @author HLX Tel:18996470535 
	 * @date 2018年4月2日 
	 * Copyright:Copyright(c) 2018
	 */
	public enum Kind implements EnumValue {
		/**
		 * 一次卡
		 */
		ONCE(1),
		/**
		 * 一日卡
		 */
		ONE_DAY(2),
		/**
		 * 两日卡
		 */
		TWO_DAY(3);
	

		private final int code;

		private Kind(int c) {
			code = c;
		}

		public int getValue() {
			return code;
		}

		public static final Kind valueOf(int value) {
			switch (value) {
			case 1:
				return Kind.ONCE;
			case 2:
				return Kind.ONE_DAY;
			case 3:
				return Kind.TWO_DAY;
			default:
				throw new IllegalArgumentException();
			}
		}
	}
}

package com.kiy.driver;

/**
 * 日志输出控制类
 * @author HLX 
 * Date: 2018年3月5日
 */
public final class Log {

	private static final String FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\t%2$s%n";

	public static void debug(String msg) {
		if(ConfigDriver.DEBUG)
		System.out.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void debug(Throwable ex) {
		if(ConfigDriver.DEBUG){
			if (ex == null)
				return;
			
			System.out.printf(FORMAT, System.currentTimeMillis(), ex.getMessage());
			if (!isKnownException(ex))
				ex.printStackTrace(System.err);
		}
	}

	public static void info(String msg) {
		if(ConfigDriver.DEBUG)
		System.out.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void info(Throwable ex) {
		if(ConfigDriver.DEBUG){
			if (ex == null)
				return;
			System.out.printf(FORMAT, System.currentTimeMillis(), ex.getMessage());
			if (!isKnownException(ex))
				ex.printStackTrace(System.out);
		}
	}

	public static void error(String msg) {
		if(ConfigDriver.DEBUG)
		System.err.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void error(Throwable ex) {
		if(ConfigDriver.DEBUG){
			if (ex == null)
				return;
			System.err.printf(FORMAT, System.currentTimeMillis(), ex.getMessage());
			if (!isKnownException(ex))
				ex.printStackTrace(System.err);
		}
	}

	/**
	 * 判断是否是已知悉异常,已知悉异常不需要输出详细信息, 例如UnknownHostException表示主机不可用,输出详细错误信息没有实际意义。
	 * 
	 * @param cause
	 * @return
	 */
	public static boolean isKnownException(Throwable cause) {
		return cause instanceof java.net.UnknownHostException ||
				cause instanceof java.net.NoRouteToHostException;
	}
}
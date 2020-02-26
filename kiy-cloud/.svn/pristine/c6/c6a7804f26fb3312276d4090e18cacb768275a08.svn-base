/**
 * 2017年2月16日
 */
package com.kiy.cloud;

/**
 * 日志（基于标准输入出）
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Log {

	private static final String FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\t%2$s%n";

	/**
	 * 判断是否是已知悉异常,已知悉异常不需要输出详细信息, 例如UnknownHostException表示主机不可用,输出详细错误信息没有实际意义。
	 * 
	 * @param cause
	 * @return
	 */
	public static boolean isKnownException(Throwable cause) {
		return cause instanceof java.io.IOException || 
				cause instanceof java.net.UnknownHostException || 
				cause instanceof java.net.NoRouteToHostException;
	}

	public static void debug(String msg) {
		System.out.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void debug(Throwable ex) {
		System.out.printf(FORMAT, System.currentTimeMillis(), ex);
		if (!isKnownException(ex))
			ex.printStackTrace(System.err);
	}

	public static void info(String msg) {
		System.out.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void error(String msg) {
		System.err.printf(FORMAT, System.currentTimeMillis(), msg);
	}

	public static void error(Throwable ex) {
		System.err.printf(FORMAT, System.currentTimeMillis(), ex);
		if (!isKnownException(ex))
			ex.printStackTrace(System.err);
	}
}
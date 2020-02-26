/**
 * 2017年2月18日
 */
package com.kiy.servo;

import java.util.concurrent.ScheduledExecutorService;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 线程池执行类
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Executor {
	
	private Executor(){}

	private static EventLoopGroup loop;

	public static void initialize() {
		Log.info("Executor initialize ...");

		int thread = Config.THREAD;
		if (thread == 0)
			// Netty默认2倍数,根据实际情况提高到4倍数
			thread = Runtime.getRuntime().availableProcessors() * 4;

		// NIO for a large number of connections (>= 1000)
		loop = new NioEventLoopGroup(thread);
		// Old for a small number of connections (< 1000)

		Log.info("Executor initialized thread count " + thread);
	}

	public static EventLoopGroup getEventLoopGroup() {
		return loop;
	}

	public static ScheduledExecutorService getScheduledExecutorService() {
		return loop;
	}

	public static void destroy() {
		Log.info("Executor destroy ...");
		if (loop != null && loop.isShutdown()) {
			loop.shutdownGracefully();
			loop = null;
		}
		Log.info("Executor destroyed");
	}
}
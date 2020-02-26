package com.kiy.driver;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 线程池执行类
 * @author HLX 
 * Date: 2018年3月5日
 */
public final class Executor {
	
	private Executor(){
		
	}

	private static EventLoopGroup loop;

	public static void initialize() {
		Log.info("Executor initialize ...");
		int thread = Runtime.getRuntime().availableProcessors() * 4;
		loop = new NioEventLoopGroup(thread);
	}

	public static EventLoopGroup getEventLoopGroup() {
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
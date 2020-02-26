package com.kiy.servo.messageque;

import com.kiy.servo.Log;

/**
 * 设备巡游服务
 * @author HLX Tel:18996470535 
 * @date 2018年4月17日 
 * Copyright:Copyright(c) 2018
 */
public final class MessageQue {
	
	private static MessageDeviceDeque messageDeviceDeque;
	
	private MessageQue() {
	}

	public static void initialize() {

		Log.info("MQ initialize ...");
		messageDeviceDeque = MessageDeviceDeque.getInstance();
		Log.info("MQ initialized ...");
	}


public static void start(){
		Log.info("MQ Start ...");
		Thread threadMessageQue = new Thread(messageDeviceDeque,"MessageDevice thread");
		threadMessageQue.start();
		Log.info("MQ Started ...");
	}
	
	public static void stop(){
		Log.info("MQ Stop ...");
		if (messageDeviceDeque!=null) {
			messageDeviceDeque.stop();
		}
		Log.info("MQ Stoped ...");
	}
	
}
	//public static void start(){
	//	Log.info("MQ Start ...");
//		Thread threadMessageQue = new Thread(messageDeviceDeque,"MessageDevice thread");
//		threadMessageQue.start();
		
//		ExecutorService pool = Executors.newFixedThreadPool(1);
//		pool.execute(MessageDeviceDeque.getInstance());
	//	
	//	Executor.getEventLoopGroup().execute(MessageDeviceDeque.getInstance());
	//	
	//	Log.info("MQ Started ...");
	//}
	//
	//public static void stop(){
	//	Log.info("MQ Stop ...");
	//	if (messageDeviceDeque!=null) {
	//		messageDeviceDeque.stop();
	//	}
	//	Log.info("MQ Stoped ...");
	//}
//}
package com.kiy.servo.messageque;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 设备消息队列
 * 
 * @author HLX Tel:18996470535
 * @date 2018年4月17日 Copyright:Copyright(c) 2018
 */
public class MessageDeviceDeque implements Runnable {

	private ConcurrentLinkedDeque<MessageDevice> deque;
	
	private boolean running = true;

	
	private MessageDeviceDeque() {
		deque = new ConcurrentLinkedDeque<>();
	}


	public static volatile  MessageDeviceDeque messageDeviceDeque;

	public static MessageDeviceDeque getInstance() {
		if (messageDeviceDeque == null) {
			synchronized (MessageDeviceDeque.class){
				if (messageDeviceDeque == null) {
					messageDeviceDeque = new MessageDeviceDeque();
				}
			}
		}
		return messageDeviceDeque;
		
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				run();
			}
			
			if (!deque.isEmpty()) {
				MessageDevice poll = deque.poll();
				if (poll!=null) {
					poll.execute();
				}
			}
		}
	}

	public void start() {
		running = true;
		run();
	}

	/**
	 * 
	 * @param messageDevice
	 */
	public void add(MessageDevice messageDevice) {
		if (messageDevice.isWriteDevice()) {
			deque.addFirst(messageDevice);
		} else {
			boolean contains = deque.contains(messageDevice);
			if (!contains) {
				deque.addLast(messageDevice);
			}

		}
	}
	
	public void stop() {
		running = false;
	}

}

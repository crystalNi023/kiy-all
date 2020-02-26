package com.kiy.cloud.recognize;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSON;
import com.kiy.cloud.Config;
import com.kiy.cloud.Log;
import com.kiy.common.Tool;

/**
 * 语音识别消息队列
 * 
 * @author HLX Tel:18996470535
 * @date 2018年5月21日 Copyright:Copyright(c) 2018
 */
public class RecognizeMessageQueue implements Runnable {
	private ConcurrentLinkedQueue<SpeechMessage> queue;
	private RecognizeListener recognizeListener;

	private boolean running = true;

	private RecognizeMessageQueue() {
		queue = new ConcurrentLinkedQueue<>();
	}

	public void setRecognoizeListener(RecognizeListener listener) {
		recognizeListener = listener;
	}

	public static volatile RecognizeMessageQueue recognizeMessageQueue;

	public static RecognizeMessageQueue getInstance() {
		if (recognizeMessageQueue == null) {
			synchronized (RecognizeMessageQueue.class) {
				if (recognizeMessageQueue == null) {
					recognizeMessageQueue = new RecognizeMessageQueue();
				}
			}
		}
		return recognizeMessageQueue;

	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if (!queue.isEmpty()) {
				 SpeechMessage poll = queue.poll();
				 boolean recognize = poll.isRecognize();
				 String msg = poll.getMsg();
				 if (recognize) {
					 Path path = FileSystems.getDefault().getPath(msg);
						byte[] data;
						try {
							data = Files.readAllBytes(path);
								HttpResponse response = HttpUtil.sendAsrPost(data, "pcm", 16000, Config.RECOGNIZE_URL, Config.RECOGNIZE_ACCESS_KEY_ID, Config.RECOGNIZE_ACCESS_KEY_SECRET);
								recognizeListener.onRecognizeSuccess(msg, JSON.toJSONString(response));
						} catch (IOException e) {
							Log.error(e);
						}
				}else {
					HttpResponse response = HttpUtil.sendTtsPost(msg, poll.getFilename());
					recognizeListener.onRecognizeSuccess(poll.getFilename(), JSON.toJSONString(response));
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
	 * @param msg 语音识别时-文件路径 语音合成时-合成语音的字符串
	 * @param 语音合成字符串 语音合成时-合成语音文件名
	 * @param speech
	 */

	public void add(String msg, String filename, boolean speech) {

		if (Tool.isEmpty(msg)) {
			return;
		}
		SpeechMessage speechMessage = new SpeechMessage();
		speechMessage.setRecognize(speech);
		speechMessage.setMsg(msg);
		speechMessage.setFilename(filename);
		queue.add(speechMessage);
	}

	public void stop() {
		running = false;
	}

}

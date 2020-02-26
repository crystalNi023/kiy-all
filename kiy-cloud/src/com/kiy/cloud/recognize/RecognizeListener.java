package com.kiy.cloud.recognize;

public interface RecognizeListener {
	/**
	 * 语音识别成功
	 * @param result
	 * @return
	 */
	public void onRecognizeSuccess(String filepath,String result);
}

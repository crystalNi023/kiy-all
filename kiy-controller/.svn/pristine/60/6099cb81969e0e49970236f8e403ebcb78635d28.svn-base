package com.kiy.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class ASound {
	public static void play(String Filename) {
		try {
			InputStream in = new FileInputStream(Filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
			if (AudioPlayer.player.isAlive()) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			AudioPlayer.player.stop(as);

			// 循环播放
			// AudioData data = as.getData();
			// ContinuousAudioDataStream gg = new
			// ContinuousAudioDataStream(data);
			// AudioPlayer.player.start(gg);

			// 用url作为声音源（source）
			// AudioStream as = new AudioStream(new
			// URL("SOURCEPATH").openStream());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

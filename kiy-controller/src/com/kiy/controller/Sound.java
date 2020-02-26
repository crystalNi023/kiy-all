/**
 * 2017年3月31日
 */
package com.kiy.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.kiy.client.CtrClient;

import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 播放声音
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Sound implements Runnable {

	private static Sound instance;
	private AtomicReference<String> path;
	private HashMap<String, InputStream> data;

	private Sound() {
		path = new AtomicReference<String>();
		data = new HashMap<String, InputStream>();
	}

	/**
	 * 播放声音1次
	 * 
	 * @param path
	 *            资源位置或者文件位置 /com/kiy/resources/alarm.wav
	 */
	public static void play(String path) {
		if (path == null)
			throw new NullPointerException();

		if (instance == null)
			instance = new Sound();

		if (instance.path.get() == null) {
			instance.path.set(path);
			CtrClient.getES().execute(instance);
		}
	}

	@Override
	public void run() {
		String p = path.get();
		InputStream r = data.get(p);
		if (r == null) {
			// "/com/kiy/resources/alarm.wav"
			InputStream in = Sound.class.getResourceAsStream(p);
			try {
				if (in == null)
					in = new FileInputStream(p);

				byte[] b = new byte[in.available()];
				in.read(b);
				in.close();
				ByteArrayInputStream m = new ByteArrayInputStream(b);
				m.mark(0);
				data.put(p, m);
				r = m;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {

				r.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		path.set(null);

		try {
			AudioStream as = new AudioStream(r);
			AudioPlayer.player.start(as);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			AudioInputStream is = AudioSystem.getAudioInputStream(r);
			AudioFormat format = is.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			byte[] values = new byte[1024];
			int length = 0;
			{
				length = is.read(values);
				line.write(values, 0, length);
			}
			while (length > 0)
				;
			line.drain();
			line.stop();
			line.close();
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public static void dispose() {
		if (instance == null)
			return;
		try {
			for (Object r : instance.data.values()) {
				if (r instanceof AudioDataStream) {
					AudioDataStream a = (AudioDataStream) r;
					a.close();
					continue;
				}
				if (r instanceof SourceDataLine) {
					SourceDataLine a = (SourceDataLine) r;
					a.close();
					continue;
				}
			}
		} catch (IOException e) {
			// 销毁过程中出现异常,只有忽略了
			e.printStackTrace();
		}
		instance.data.clear();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("PLAY 1");
		Sound.play("/com/kiy/resources/alarm.wav");
		Thread.sleep(5000);
		System.out.println("PLAY 2");
		Sound.play("/com/kiy/resources/alarm.wav");
		CtrClient.shutdown();
	}
}
package com.kiy.cloud.notice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kiy.cloud.Log;

/**
 * 虚拟SMTP服务
 * 
 * @author 张希
 *
 */
public final class MailSMTP implements Runnable {

	private boolean running = false;
	private ServerSocket server = null;
	private Thread thread = null;
	private ExecutorService pool = null;

	public MailSMTP() {
		pool = Executors.newFixedThreadPool(3);
		thread = new Thread(this);
	}

	public final void start() {
		running = true;
		thread.start();
	}

	@Override
	public void run() {
		while (running) {
			try {
				server = new ServerSocket(25);
			} catch (IOException ex) {
				Log.error(ex);
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					Log.error(e);
				}
			}
			if (server != null) {
				while (running) {
					try {
						Socket client = server.accept();
						pool.execute(new Client(client));
					} catch (IOException ex) {
						Log.error(ex);
					}
				}
			}
		}
	}

	public final void close() {
		running = false;
		try {
			server.close();
		} catch (IOException ex) {
			Log.error(ex);
		}
		thread.interrupt();
	}

	/////////////////////////////////////////////////

	private final class Client implements Runnable {

		private Socket socket = null;

		public Client(Socket s) {
			socket = s;
		}

		@Override
		public void run() {
			try {
				String line = null;
				InputStreamReader input = new InputStreamReader(socket.getInputStream());
				BufferedReader reader = new BufferedReader(input);
				while ((line = reader.readLine()) != null && !line.equals("")) {
					Log.debug(line);
				}
			} catch (IOException ex) {
				Log.error(ex);
			} finally {
				try {
					socket.close();
				} catch (IOException ex) {
					Log.error(ex);
				}
			}
		}
	}
}

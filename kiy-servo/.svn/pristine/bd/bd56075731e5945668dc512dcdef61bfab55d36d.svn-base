package com.kiy.servo.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import com.kiy.common.Device;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.Log;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class SocketAdpater extends DriverAdpater {

	private String key;
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	protected Coder coder;

	public SocketAdpater(Device d) {
		super(d);
	}

	@Override
	public void start() {
		if (coder == null)
			return;

		InetSocketAddress address = new InetSocketAddress(device.getNetworkIp(), device.getNetworkPort());
		if (address.isUnresolved()) {
			Log.error(device.getNetworkIp() + ":" + device.getNetworkPort() + " is unresolved.");
			reset();
			return;
		}

		socket = new Socket();
		try {
			socket.connect(address);
			socket.setSoTimeout(Config.DRIVER_TIMEOUT * 1000);
			out = socket.getOutputStream();
			in = socket.getInputStream();

			key = device.getNetworkIp() + ':' + device.getNetworkPort();
		} catch (IOException ex) {
			Log.error(ex);
			reset();
		}
	}

	@Override
	public synchronized void send(Device device, byte code, int tag) {
		if (out == null || in == null)
			return;

		ByteBuf bf = ByteBufAllocator.DEFAULT.buffer(coder.getMaxFrame());
		// ENCODE
		coder.encode(device, bf, code, tag);
		if (bf.readableBytes() == 0) {
			Log.error(key + " encode 0 byte");
			bf.release();
			return;
		}

		try {
			// WRITE
			bf.readBytes(out, bf.readableBytes());
			out.flush();
			// READ
			bf.clear();
			w: while (true) {
				bf.writeBytes(in, coder.getMaxFrame());
				switch (coder.frame(device, bf)) {
					case Coder.OK:
						break w;
					case Coder.CONTINUE:
						continue w;
					case Coder.OVERFLOW:
						bf.release();
						Log.error(key + " OVERFLOW");
						return;
					case Coder.FORMAT:
						Log.error(key + " FORMAT");
						bf.release();
						return;
					default:
						Log.error(key + " UNKNOWN ");
						bf.release();
						return;
				}
			}
		} catch (SocketTimeoutException ex) {
			Log.error(ex);
			bf.clear();
		} catch (IOException ex) {
			Log.error(ex);
			bf.release();
			reset();
			return;
		}

		// DECODE
		coder.decode(device, bf, code, tag);
		bf.release();
	}

	@Override
	public void reset() {
		stop();
		Executor.getScheduledExecutorService().schedule(new Runnable() {
			@Override
			public void run() {
				start();
			}
		}, Config.DRIVER_RESTART, TimeUnit.SECONDS);
	}

	@Override
	public void stop() {
		if (socket == null)
			return;

		try {
			if (!socket.isConnected()) {

				if (in != null) {
					in.close();
					in = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
				socket.shutdownInput();
				socket.shutdownOutput();
			}
			if (!socket.isClosed())
				socket.close();
		} catch (IOException ex) {
			Log.error(ex);
		}

		socket = null;
	}

	@Override
	public boolean isActive() {
		return socket != null && socket.isConnected();
	}

	@Override
	public void destroy() {
		coder = null;
	}
}
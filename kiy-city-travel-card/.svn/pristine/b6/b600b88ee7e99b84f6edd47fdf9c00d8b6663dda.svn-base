package com.kiy.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import purejavacomm.CommPort;
import purejavacomm.CommPortIdentifier;
import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.SerialPortEvent;
import purejavacomm.SerialPortEventListener;
import purejavacomm.UnsupportedCommOperationException;

/**
 * 
 * @author HLX Tel:18996470535
 * @date 2018年4月2日 Copyright:Copyright(c) 2018
 */
public class CardSerialPort {

	private String key;
	//
	// 串口对象
	private SerialPort sp;
	// 输入流,连接后可用
	private InputStream in;
	// 输出流,连接后可用
	private OutputStream out;

	// 数据解码
	private CardCoder coder;
	// 数据位
	private int dataBits = SerialPort.DATABITS_8;
	// 停止位
	private int stopBits = SerialPort.STOPBITS_1;
	// 数据校验模式
	private int parityCheck = SerialPort.PARITY_NONE;

	public CardSerialPort() {
		coder = new CardCoder();
	}

	public void start() {
		if (coder == null)
			return;

		CommPortIdentifier portIdentifier = null;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(ConfigDriver.ID_CARD_SERIAL_PORT);
			if (portIdentifier.isCurrentlyOwned()) {

				Log.error("Port is currently in use.");
				reset();
				return;
			}
		} catch (NoSuchPortException ex) {
			Log.error("Port " + ConfigDriver.ID_CARD_SERIAL_PORT + " is inexistent.");
			reset();
			return;
		}

		CommPort cp = null;
		try {
			cp = portIdentifier.open("ID_CARD", ConfigDriver.DRIVER_RESTART * 1000);
			if (cp instanceof SerialPort) {
				sp = (SerialPort) cp;
				sp.setSerialPortParams(ConfigDriver.ID_CARD_BAUD_RATE, dataBits, stopBits, parityCheck);
				sp.setInputBufferSize(coder.getMaxFrame());
				sp.setOutputBufferSize(coder.getMaxFrame());
				sp.enableReceiveTimeout(ConfigDriver.DRIVER_TIMEOUT * 1000);
				sp.enableReceiveThreshold(0);

				sp.addEventListener(new EventListener());

				sp.notifyOnBreakInterrupt(true);
				sp.notifyOnCarrierDetect(true);
				sp.notifyOnCTS(true);
				sp.notifyOnDSR(true);
				sp.notifyOnDataAvailable(true);
				sp.notifyOnFramingError(true);
				sp.notifyOnOutputEmpty(true);
				sp.notifyOnOverrunError(true);
				sp.notifyOnParityError(true);
				sp.notifyOnRingIndicator(true);

				in = sp.getInputStream();
				out = sp.getOutputStream();

				key = ConfigDriver.ID_CARD_SERIAL_PORT + ':' + ConfigDriver.ID_CARD_BAUD_RATE + ',' + dataBits + stopBits + parityCheck;
				Log.info("OPEN " + key);
			} else {
				cp.close();
				Log.error("Port " + ConfigDriver.ID_CARD_SERIAL_PORT + " is not serial port.");
				reset();
			}
		} catch (PortInUseException | UnsupportedCommOperationException | TooManyListenersException | IOException ex) {
			Log.error(ex);
			if (cp != null)
				cp.close();
			reset();
		}
	}

	public void send(MCard card, int order) {
		if (in == null || out == null)
			return;

		ByteBuf bf = ByteBufAllocator.DEFAULT.buffer(coder.getMaxFrame());

		// ENCODE
		coder.encode(card, bf, order);
		if (bf.readableBytes() == 0) {
			Log.error(key + " encode 0 byte");
			bf.release();
			return;
		}

		Log.debug("SEND " + key + " byte " + bf.readableBytes());

		try {
			// WRITE
			bf.readBytes(out, bf.readableBytes());
			out.flush();

			// READ
			bf.clear();
			int count = (int) (sp.getReceiveTimeout() * 0.58);
			w: while (--count > 0) {
				bf.writeBytes(in, coder.getMaxFrame());
				switch (coder.frame(bf)) {
					case CardCoder.OK:
						break w;
					case CardCoder.CONTINUE:
						Thread.sleep(1);
						continue w;
					case CardCoder.OVERFLOW:
						bf.release();
						Log.error(key + " OVERFLOW");
						return;
					case CardCoder.FORMAT:
						Log.error(key + " FORMAT");
						bf.release();
						return;
					default:
						Log.error(key + " UNKNOWN ");
						bf.release();
						return;
				}
			}

		} catch (IOException | InterruptedException ex) {
			Log.error(ex);
			bf.release();
			reset();
			return;
		}

		Log.debug("RECV " + key + " byte " + bf.readableBytes());

		// DECODE
		coder.decode(card, bf, order);
		bf.release();
	}

	/**
	 * ����
	 * 
	 * @param card
	 * @return
	 */
	public MCard readCard(MCard card) {
		send(card, CardCoder.READ_CARD_ONE_ORDER);
		send(card, CardCoder.READ_CARD_TWO_ORDER);
		return card;
	}

	/**
	 * д��
	 * 
	 * @param card
	 * @return
	 */
	public int writeCard(MCard card) {
		send(card, CardCoder.WRITE_CARD_ONE_ORDER);
		send(card, CardCoder.WRITE_CARD_TWO_ORDER);
		return 0;
	}

	public void reset() {
		stop();
		Executor.getEventLoopGroup().schedule(new Runnable() {
			@Override
			public void run() {
				start();
			}
		}, ConfigDriver.DRIVER_RESTART, TimeUnit.SECONDS);
	}

	public void stop() {
		if (sp == null)
			return;

		try {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
			sp.close();
			sp = null;
		} catch (IOException ex) {
			Log.error(ex);
		}
	}

	public boolean isActive() {
		return sp != null && in != null && out != null;
	}

	public void destroy() {
		coder = null;
	}

	/**
	 * 串口事件
	 */
	private class EventListener implements SerialPortEventListener {

		@Override
		public void serialEvent(SerialPortEvent e) {
			switch (e.getEventType()) {
				case SerialPortEvent.DATA_AVAILABLE:
					// 数据到达
					// Log.debug("DATA_AVAILABLE 数据到达");
					break;
				case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
					// 缓冲区空
					// Log.debug("OUTPUT_BUFFER_EMPTY 缓冲区空");
					break;
				case SerialPortEvent.BI:
					// 通讯中断
					// Log.debug("BI 通讯中断");
					break;
				case SerialPortEvent.CD:
					// 载波检测
					// Log.debug("CD 载波检测");
					break;
				case SerialPortEvent.CTS:
					// 清除发送
					// Log.debug("CTS 清除发送");
					break;
				case SerialPortEvent.DSR:
					// 设备就绪
					// Log.debug("DSR 设备就绪");
					break;
				case SerialPortEvent.FE:
					// 帧错误
					// Log.debug("FE 帧错误");
					break;
				case SerialPortEvent.OE:
					// 溢位错误
					// Log.debug("OE 溢位错误");
					break;
				case SerialPortEvent.PE:
					// 奇偶校验错
					// Log.debug("PE 奇偶校验错");
					break;
				case SerialPortEvent.RI:
					// 振铃
					// Log.debug("RI 振铃");
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 获取所有串口
	 */
	public static List<String> getAllPort() {
		ArrayList<String> items = new ArrayList<String>();
		Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
		String item = null;
		while (ports.hasMoreElements()) {
			Object p = ports.nextElement();
			if (p instanceof CommPortIdentifier) {
				CommPortIdentifier portIdentifier = (CommPortIdentifier) p;

				switch (portIdentifier.getPortType()) {
					case CommPortIdentifier.PORT_PARALLEL:
						item = portIdentifier.getName();
						break;
					case CommPortIdentifier.PORT_SERIAL:
						item = portIdentifier.getName();
						break;
					default:
						item = portIdentifier.getName();
				}
				items.add(item);
			}
		}
		return items;
	}

}
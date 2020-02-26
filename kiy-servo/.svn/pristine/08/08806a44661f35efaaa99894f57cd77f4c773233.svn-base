package com.kiy.servo.driver.tcp;

import io.netty.buffer.ByteBuf;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

import com.kiy.common.Device;
import com.kiy.servo.Log;
import com.kiy.servo.driver.Coder;

/**
 * tcp设备通信协议编解码辅助
 *
 * @author Crystal Ni
 * @date 2019年5月27日
 *
 */
public final class TCPCoder extends Coder {
	
	// 读设备
	public static final int READ = 0;
	
	// 控制设备
	public static final int WRITE = 1;

	public TCPCoder() {
	}

	@Override
	public int getMaxFrame() {
		return 255;
	}

	@Override
	public void encode(Device device, ByteBuf out, byte code, int tag) {
		// 在这里新添加TCP的开锁功能
		Socket socket = null;
		try {
			socket = new Socket(device.getNetworkIp(), device.getNetworkPort());
			String str = null;
			byte[] bytes = null;
			OutputStream os = socket.getOutputStream();
			switch (tag) {
			case WRITE:
				if (device.getFeature(0).getValue() == 1) {
					// 开锁
					str = "02 C4 14 7A 01 53 FF"; //发送的16进制开锁命令
					bytes = hexStringToByteArray(str);
					os.write(bytes);
					Log.debug("执行开锁命令。");
				} else if (device.getFeature(0).getValue() == 0) {
					// 关锁
					str = "02 C4 14 7A 02 54 FF";
					bytes = hexStringToByteArray(str);
					os.write(bytes);
					Log.debug("执行关锁命令。");
				}
				break;
			default:
				break;
			}
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {

				}
			}
		}
	}


	@Override
	public void decode(Device device, ByteBuf in, byte code, int tag) {
		
	}

	public static Date newDate(int second, int minute, int hour, int day, int month, int week, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year + 2000, month, day, hour, minute, second);
		return calendar.getTime();
	}

	@Override
	public int frame(Device device, ByteBuf in) {
		// TODO Auto-generated method stub
		return 0;
	}
	 /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
/**
 * 2017年2月16日
 */
package com.kiy.servo.driver;

import com.kiy.common.Device;

/**
 * 设备关联客户端
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class DriverAdpater {

	protected final Device device;

	public DriverAdpater(Device d) {
		device = d;
	}

	/**
	 * 获取状态
	 */
	public abstract void getStatus(Device d);

	/**
	 * 设置状态/控制
	 */
	public abstract void setStatus(Device d);

	/**
	 * 获取设备设定
	 */
	public abstract void getConfig(Device d);

	/**
	 * 设置设备设定
	 */
	public abstract void setConfig(Device d);

	/**
	 * 初始化
	 */
	public abstract void initialize();

	/**
	 * 启动
	 */
	public abstract void start();

	/**
	 * 发送
	 * 
	 * @param device 设备
	 * @param code 指令
	 * @param tag 自定义标志
	 */
	public abstract void send(Device device, byte code, int tag);

	/**
	 * 重置
	 */
	public abstract void reset();

	/**
	 * 停止
	 */
	public abstract void stop();

	/**
	 * 销毁
	 */
	public abstract void destroy();

	/**
	 * 是否可用
	 */
	public abstract boolean isActive();
}
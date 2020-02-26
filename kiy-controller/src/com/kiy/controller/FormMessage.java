/**
 * 2017年3月28日
 */
package com.kiy.controller;

import java.util.Map;

import com.kiy.common.Servo;
import com.kiy.common.Unit;
import com.kiy.protocol.Messages.Message;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public interface FormMessage {

	public void received(Servo s, Message m,Map<Message, Unit> units);

	public void close();
}

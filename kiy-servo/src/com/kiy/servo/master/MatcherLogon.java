/**
 * 2017年3月22日
 */
package com.kiy.servo.master;

import com.kiy.common.User;
import com.kiy.servo.Processer;

import io.netty.channel.Channel;
import io.netty.channel.ServerChannel;
import io.netty.channel.group.ChannelMatcher;

/**
 * 客户端筛选器，匹配所有已登录的用户连接
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class MatcherLogon implements ChannelMatcher {

	/**
	 * 匹配所有登录成功的连接
	 */
	public static MatcherLogon Logon = new MatcherLogon(null);

	private final Channel channel;
	// private final User user;

	public MatcherLogon(Channel c) {
		channel = c;
		// user = c.attr(MasterHandler.USER).get();
	}

	@Override
	public boolean matches(Channel c) {
		// 排除监听对象
		if (ServerChannel.class.isInstance(c))
			return false;

		// 排除自身
		if (channel != null && channel == c)
			return false;

		// 排除未登录对象
		if (c.hasAttr(Processer.USER)) {
			User u = c.attr(Processer.USER).get();
			if (u == null)
				return false;

			return true;
		}
		return false;
	}
}
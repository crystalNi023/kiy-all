/**
 * 2017年8月9日
 */
package com.kiy.cloud.master;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.PlatformDependent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class MasterGroup {

	private Channel server = null;
	private final ConcurrentMap<ChannelId, Channel> channels;
	private final ConcurrentMap<String, Channel> channel_keys;
	private final ChannelFutureListener remover = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			remove(future.channel());
		}
	};

	public MasterGroup() {
		channels = PlatformDependent.newConcurrentHashMap();
		channel_keys = PlatformDependent.newConcurrentHashMap();
	}

	public void setServer(Channel c) {
		server = c;
		c.closeFuture().addListener(remover);
	}

	public Channel getServer() {
		return server;
	}

	public ConcurrentMap<String, Channel> getChannel_keys() {
		return channel_keys;
	}

	public void add(Channel c) {
		boolean added = channels.putIfAbsent(c.id(), c) == null;
		if (added) {
			c.closeFuture().addListener(remover);
			if (c.hasAttr(MasterHandler.CLIENT)) {
				Client client = c.attr(MasterHandler.CLIENT).get();
				channel_keys.put(client.getUser(), c);
			}
		}
	}
	
	public void add(String key,Channel c) {
		channel_keys.put(key, c);
	}

	public void update(String old, String key, Channel c) {
		if (old != null)
			channel_keys.remove(old);

		if (key != null)
			channel_keys.put(key, c);
	}

	public void remove(Channel c) {
		c = channels.remove(c.id());
		if (c == null)
			;
		else {
			if (c.hasAttr(MasterHandler.CLIENT)) {
				Client client = c.attr(MasterHandler.CLIENT).get();
				channel_keys.remove(client.getUser());
			}
			c.closeFuture().removeListener(remover);
		}
	}

	public boolean contains(String key) {
		return channel_keys.containsKey(key);
	}

	public Channel get(ChannelId id) {
		return channels.get(id);
	}

	public Channel get(String key) {
		return channel_keys.get(key);
	}

	public void close() {
		if (server != null) {
			server.close();
		}
		for (Channel c : channels.values()) {
			c.close();
		}
	}
	
	
	public boolean Send2LocalServo(Object message, String key){
		// 发送给单个匹配的连接
		Channel c = channel_keys.get(key);
		if (c == null)
			return false;
		c.writeAndFlush(message);
		return true;
	}

	public boolean SendOne(Object message, String key) {
		// 发送给单个匹配的连接
		Channel c = channel_keys.get(key);
		if (c == null)
			return false;
		c.writeAndFlush(message);
		return true;
	}
	
	
	public ConcurrentMap<String, Channel> getOnlineServoKey() {
		ConcurrentMap<String, Channel> map = new ConcurrentHashMap<>();
		Set<String> keySet = channel_keys.keySet();
		for (String key : keySet) {
			Channel c = channel_keys.get(key);
			if (c.hasAttr(MasterHandler.CLIENT)) {
				Client client = c.attr(MasterHandler.CLIENT).get();
				if (client.isServo()) {
					map.put(key, c);
				}
			}
		}
		return map;
	}

	
	public int SendClients(Object message, String servo) {
		// 发送给多个匹配的连接,包括Mobile和Controller
		int count = 0;
		Client client = null;
		for (Channel c : channels.values()) {
			if (c.hasAttr(MasterHandler.CLIENT)) {
				client = c.attr(MasterHandler.CLIENT).get();
				if (client.isMobile() || client.isController()) {
					if (servo.equals(client.getServo())) {
						c.writeAndFlush(ReferenceCountUtil.retain(message));
						count++;
					}
				}
			}
		}
		return count;
	}

	public int sendControllers(Object message, String servo) {
		// 发送给多个匹配的连接,Controller
		int count = 0;
		Client client = null;
		for (Channel c : channels.values()) {
			if (c.hasAttr(MasterHandler.CLIENT)) {
				client = c.attr(MasterHandler.CLIENT).get();
				if (client.isController()) {
					if (servo.equals(client.getServo())) {
						c.writeAndFlush(ReferenceCountUtil.retain(message));
						count++;
					}
				}
			}
		}
		return count;
	}
	
}
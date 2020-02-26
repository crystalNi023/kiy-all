/**
 * 2017年2月17日
 */
package com.kiy.servo.master;

import java.net.InetSocketAddress;

import com.kiy.common.Tool;
import com.kiy.protocol.Messages;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.Log;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 主控服务
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Master {

	private static DefaultChannelGroup group;
	private static InetSocketAddress address;

	private Master() {
	}

	public static ChannelGroup getGroup() {
		return group;
	}

	public static void initialize() {
		if (!Config.MASTER) {
			Log.info("Master disabled");
			return;
		}

		Log.info("Master initialize ...");

		if (Tool.isEmpty(Config.MASTER_IP))
			address = new InetSocketAddress(Config.MASTER_PORT);
		else
			address = new InetSocketAddress(Config.MASTER_IP, Config.MASTER_PORT);

		// 检查是否能解析地址
		if (address.isUnresolved())
			throw new RuntimeException("MASTER_IP Unresolved");

		group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

		Log.info("Master initialized");
		
	}

	public static void start() {
		if (group == null)
			return;

		Log.info("Master start ...");

		EventLoopGroup event = Executor.getEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(event, event);

		if (event instanceof NioEventLoopGroup)
			bootstrap.channel(NioServerSocketChannel.class);
		if (event instanceof OioEventLoopGroup)
			bootstrap.channel(OioServerSocketChannel.class);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				ChannelPipeline pipeline = sc.pipeline();
				pipeline.addLast(new ProtobufVarint32FrameDecoder());
				pipeline.addLast(new ProtobufDecoder(Messages.Message.getDefaultInstance()));
				pipeline.addLast(new MasterHandler(group));
				pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
				pipeline.addLast(new ProtobufEncoder());
			}
		});

		ChannelFuture future = bootstrap.bind(address);
		group.add(future.channel());

		Log.info("Master started " + address);
		
	}

	public static void stop() {
		if (group == null)
			return;

		Log.info("Master stop ...");

		group.close();

		Log.info("Master stopped");
	}

	public static void destroy() {
		if (group == null)
			return;

		Log.info("Master destory ...");

		address = null;
		group = null;

		Log.info("Master destoryed");
	}
}
/**
 * 2017年6月16日
 */
package com.kiy.servo.cloud;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import com.kiy.common.Tool;
import com.kiy.protocol.Messages;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.Log;
import com.kiy.servo.Shell;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Cloud {
	
	private Cloud(){}

	private static Channel channel;

	/**
	 * 初始化;必须先初始化才能启动 start()
	 */
	public static void initialize() {
		if (!Config.CLOUD) {
			Log.info("Cloud disabled");
			return;
		}

		Log.info("Cloud initialize ...");
	}

	/**
	 * 启动连接;重复执行此方法不会开启多个连接
	 */
	public static void start() {
		if (!Config.CLOUD)
			return;

		if (channel != null)
			return;

		Log.info("Cloud start ...");

		EventLoopGroup event = Executor.getEventLoopGroup();
		final Bootstrap bootstrap = new Bootstrap();

		bootstrap.group(event);
		if (event instanceof NioEventLoopGroup)
			bootstrap.channel(NioSocketChannel.class);
		if (event instanceof OioEventLoopGroup)
			bootstrap.channel(OioSocketChannel.class);

		bootstrap.remoteAddress(Config.CLOUD_HOST, Config.CLOUD_PORT);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				ChannelPipeline pipeline = sc.pipeline();
				pipeline.addLast(new IdleStateHandler(Config.CLOUD_RESET * 2, Config.CLOUD_RESET, 0));
				pipeline.addLast(new ProtobufVarint32FrameDecoder());
				pipeline.addLast(new ProtobufDecoder(Messages.Message.getDefaultInstance()));
				pipeline.addLast(new CloudHandler());
				pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
				pipeline.addLast(new ProtobufEncoder());
			}
		});

		channel = bootstrap.connect().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture cf) throws Exception {
				if (cf.isSuccess()) {
					Log.info("Cloud connect success with " + Config.CLOUD_HOST + ":" + Config.CLOUD_PORT);
				} else {
					Log.info("Cloud connect failure with " + Config.CLOUD_HOST + ":" + Config.CLOUD_PORT);
					if (cf.cause() != null)
						Log.error(cf.cause());
					reset();
				}
			}
		}).channel();

		Log.info("Cloud started");
	}

	/**
	 * 提供给其它模块发送数据到云端;Cloud内部不应当调用此方法。
	 * 
	 * @param msg 需要发送的Message对象
	 */
	public static synchronized void send(Object msg) {
		if (channel == null)
			return;
		channel.writeAndFlush(msg);
	}

	/**
	 * 重置连接,将延迟Config.CLOUD_RESET设置的间隔秒之后进行连接(执行start方法)。
	 * A.如果是网络异常导致的中断应当延迟后在进行重连接。 B.如果是心跳超时则应当立即连接，无须延迟后连接。
	 */
	public static synchronized void reset() {
		if (channel == null || channel.isActive())
			return;

		Log.info("Cloud reset ...");

		stop();

		// 延迟重置
		Executor.getEventLoopGroup().schedule(new Runnable() {
			@Override
			public void run() {
				start();
			}
		}, Config.CLOUD_RESET, TimeUnit.SECONDS);

		// 重启前是否有ip，没有则重启服务。
		String ip = Tool.getLinuxLocalIp();
		if (null == ip || "".equals(ip)) {// 未获取到ip时重启系统
			Log.info("未获取到ip时重启系统:    reboot -f");
			try {
				Log.info("未获取到ip时重启系统:    reboot -f");
				Shell.exec("reboot -f");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 终止网络连接,需要时可以执行start方法重新启动
	 */
	public static synchronized void stop() {
		if (channel == null)
			return;

		Log.info("Cloud stop ...");

		channel.close();
		channel = null;

		Log.info("Cloud stopped");
	}

	/**
	 * 销毁对象,销毁后不能执行start/stop;通常在服务退出时执行此方法。
	 */
	public static void destroy() {
		Log.info("Cloud destory ...");
		Log.info("Cloud destoryed");
	}

	/**
	 * 获取云端连接
	 * @return
	 */
	public static Channel getChannel() {
		return channel;
	}
	
	
}
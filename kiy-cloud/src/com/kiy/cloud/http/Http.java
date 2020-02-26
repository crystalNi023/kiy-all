
package com.kiy.cloud.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import com.kiy.cloud.Config;
import com.kiy.cloud.Executor;
import com.kiy.cloud.Log;
import com.kiy.cloud.recognize.RecognizeMessageQueue;
import com.kiy.common.Tool;

public final class Http {

	private static SslContext ssl = null;
	private static InetSocketAddress address;
	private static Channel channel;
	private static HttpProcess process;

	private Http() {
	}

	public static void initialize() {
		Log.info("Http initialize ...");
		int port = 0;
		if ("https".equalsIgnoreCase(Config.HTTP_MODE)) {
			port = Config.HTTP_SSL_PORT;
			try {
				SelfSignedCertificate ssc = new SelfSignedCertificate();
				ssl = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
			} catch (CertificateException | SSLException ex) {
				ssl = null;
				Log.error(ex);
				throw new RuntimeException(ex);
			}
		} else {
			port = Config.HTTP_PORT;
		}
		if (Tool.isEmpty(Config.HTTP_IP))
			address = new InetSocketAddress(port);
		else
			address = new InetSocketAddress(Config.HTTP_IP, port);

		// 检查是否能解析地址
		if (address.isUnresolved())
			throw new RuntimeException("HTTP_IP Unresolved");

		process = new HttpProcess();
		Thread threadMessageQue = new Thread(RecognizeMessageQueue.getInstance(),"RecognizeMessageQueue thread");
		threadMessageQue.start();

		Log.info("Http initialized");
	}

	public static void start() {
		if (process == null)
			return;

		Log.info("Http start ...");

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
				if (ssl == null) {
					// 未启用SSL
				} else {
					pipeline.addLast(ssl.newHandler(sc.alloc()));
				}
				pipeline.addLast(new HttpRequestDecoder());
				pipeline.addLast(new HttpResponseEncoder());
				pipeline.addLast(new HttpObjectAggregator(1024*1024*64, false));
				pipeline.addLast("http-chunked", new ChunkedWriteHandler());
				pipeline.addLast(new HttpRequestHandler(process));
				
			}
		});

		channel = bootstrap.bind(address).channel();

		Log.info("Http started " + address);
	}

	public static void stop() {
		if (process == null)
			return;

		Log.info("Http stop ...");

		channel.close();

		Log.info("Http stopped");
	}

	public static void destroy() {
		if (process == null)
			return;

		Log.info("Http destory ...");

		process = null;
		address = null;
		channel = null;

		Log.info("Http destoryed");
	}
}
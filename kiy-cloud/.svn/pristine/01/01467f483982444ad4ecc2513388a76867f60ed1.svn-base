package com.kiy.cloud.http;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;

import com.kiy.cloud.Config;
import com.kiy.cloud.Log;
import com.kiy.cloud.recognize.RecognizeListener;
import com.kiy.cloud.recognize.RecognizeMessageQueue;

/**
 * HTTP访问处理
 * @author HLX Tel:18996470535 
 * @date 2018年5月16日 
 * Copyright:Copyright(c) 2018
 */
public final class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	public static final String JSON = "/json/";  //json 请求
	public static final String XML = "/xml/";	// xml 请求
	public static final String HTML = "/html/";	// html 请求
	public static final String DATA = "/data/";	// 文件请求

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
	public static final int HTTP_CACHE_SECONDS = 60;

	private HttpProcess process;

	public HttpRequestHandler(HttpProcess p) {
		process = p;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (request.decoderResult() == DecoderResult.SUCCESS) {
			HttpParameters parameters = new HttpParameters();

			// GET	
			QueryStringDecoder get = new QueryStringDecoder(request.uri());
			parameters.setDecoder(get);

			// POST	
			if (request.method() == HttpMethod.POST) {
				HttpPostRequestDecoder post = new HttpPostRequestDecoder(request);
				parameters.setDecoder(post);
			}

			if (get.path().startsWith("/test/")) {
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
				response.headers().set(HttpHeaderNames.LOCATION,"https://www.baidu.com");
				
				// SEND
				if (HttpUtil.isKeepAlive(request)) {
					response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
					response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
					ctx.writeAndFlush(response);
				} else {
					ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
				}
			}else {
				// RESPONSE
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new ByteBufOutputStream(response.content()), "UTF-8"));
				// JSON/XML /json/select_devices
				if (get.path().startsWith(JSON)) {  // JSON 请求
					response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");
					process.process(HttpProcess.CODE_JSON, get.path().substring(JSON.length()), parameters, writer, request);
				}else if(get.path().startsWith(HTML)){
					response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=UTF-8");
					process.process(HttpProcess.CODE_HTML, get.path().substring(HTML.length()), parameters, writer, request);
				}else if (get.path().startsWith(XML)) { // XML 请求
					response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/xml;charset=UTF-8");
					process.process(HttpProcess.CODE_XML, get.path().substring(XML.length()), parameters, writer, request);
				} else if (get.path().startsWith(DATA)) { // 文件请求 
					loadFile(ctx, request, writer, get);
					return;
				} else if (get.path().startsWith("/phonetic_synthesis.wav")) { //语音合成
					speechSynthesisHandle(ctx, request,parameters,writer);
					return;
				}else {
					if (get.path().equals("/favicon.ico")) {
						writer.close();
					} else {
						response.headers().set(HttpHeaderNames.CONTENT_TYPE, "html/text;charset=UTF-8");
						writer.println("ERROR");
						response.setStatus(HttpResponseStatus.BAD_REQUEST);
					}
				}
				writer.flush();

				// SEND
				if (HttpUtil.isKeepAlive(request)) {
					response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
					response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
					ctx.writeAndFlush(response);
				} else {
					ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
				}
			}
			
			
		} else {
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED);
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Log.error(cause);
		ctx.channel().disconnect();
		ctx.channel().close();
	}
	
	
	/**
	 * 语音合成
	 * @throws IOException 
	 */
	private void speechSynthesisHandle(ChannelHandlerContext ctx,FullHttpRequest request,HttpParameters parameters,PrintWriter writer) throws IOException{
		String value = parameters.getValue("string");
		
		final String filename = UUID.randomUUID().toString().replace("-", "");
		RecognizeMessageQueue instance = RecognizeMessageQueue.getInstance();
		instance.add(value, filename, HttpProcess.SPEECH_SYNTHESIS);
		
		final StringBuilder result = new StringBuilder();
		instance.setRecognoizeListener(new RecognizeListener() {
			@Override
			public void onRecognizeSuccess(String filepath, String re) {
				if (filepath.equals(filename)) {
					result.append(re);
				}
			}
		});
		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Log.error(e);
				Thread.currentThread().interrupt();
			}
			if (result.length() > 0) {
				break;
			}
		}
		
		
		File file = new File(Config.getWorkPath()+Config.PHONETIC_URL,filename+".wav");
		if (file.isHidden() || !file.exists()||!file.isFile()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			writer.close();
			return;
		}
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException fnfd) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		long fileLength = randomAccessFile.length();
		HttpResponse response1 = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response1.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");	
		HttpUtil.setContentLength(response1, fileLength);
		setContentTypeHeader(response1, file);
		
		if (HttpUtil.isKeepAlive(request)) {
			response1.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
		ctx.write(response1);
		ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 1024*1024*24), ctx.newProgressivePromise());
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if (!HttpUtil.isKeepAlive(request))
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		randomAccessFile.close();
		
		file.delete();
	}
	
	/**
	 * 加载图片文件
	 * @param ctx
	 * @param request
	 * @param writer
	 * @param get
	 * @throws IOException
	 */
	private void loadFile(ChannelHandlerContext ctx,FullHttpRequest request,PrintWriter writer,QueryStringDecoder get) throws IOException{
		if (request.method() != HttpMethod.GET) { /*只支持GET请求，不支持POST请求*/
			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			writer.close();
			return;
		}
		StringBuilder path = new StringBuilder();
		path.append(Config.getWorkPath());
		String[] split = get.path().split("/");
		for (int i = 0; i < split.length; i++) {
			if (i == 0) {
				path.append(split[i]);
			} else {
				path.append(File.separator+split[i]);
			}
		}
		File file = new File(path.toString());
		if (file.isHidden() || !file.exists()||!file.isFile()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			writer.close();
			return;
		}
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException fnfd) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		long fileLength = randomAccessFile.length();
		HttpResponse response1 = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response1.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8");	
		HttpUtil.setContentLength(response1, fileLength);
		setContentTypeHeader(response1, file);
		
		if (HttpUtil.isKeepAlive(request)) {
			response1.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
		ctx.write(response1);
		
		ctx.write(new HttpChunkedInput(new ChunkedFile(randomAccessFile, 0, fileLength, 1024*1024*24)), ctx.newProgressivePromise());  
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if (!HttpUtil.isKeepAlive(request))
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		randomAccessFile.close();
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private void setContentTypeHeader(HttpResponse response, File file) {
		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
	}
	

}
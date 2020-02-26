/**
 * 2017年2月17日
 */
package com.kiy.servo.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestSocketBind {

    public static void main(String[] args) throws Exception {
	boolean running = true;
	ExecutorService executor = Executors.newCachedThreadPool();
	ServerSocket socket = new ServerSocket();
	socket.bind(new InetSocketAddress(8999));
	System.out.println("START");
	while (running) {
	    final Socket client = socket.accept();
	    System.out.println(client);
	    executor.execute(new Runnable() {
		@Override
		public void run() {
		    try {
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			while (client.isConnected()) {
			    int length = in.available();
			    if (length > 0) {
				while (length > 0) {
				    out.write(in.read());
				    length--;
				}
				out.flush();
			    }
			}
		    } catch (Exception ex) {
			ex.printStackTrace();
		    } finally {
			try {
			    client.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }
		}
	    });
	}
	socket.close();
    }
}
package com.kiy.servo.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import com.kiy.servo.driver.rs485.CRC;


public class Test1 {
	public static void main(String[] args) {
//		JsonObject jsonObject = new JsonObject();
//		jsonObject.addProperty("deviceKey", "00005T3U");
//		jsonObject.addProperty("cmd", "read");
//		JsonObject jsonObject2 = new JsonObject();
//		JsonArray jsonArray = new JsonArray();
//		JsonObject channel1 = new JsonObject();
//		channel1.addProperty("channel", 1);
//		channel1.addProperty("switch", true);
//		JsonObject channel2 = new JsonObject();
//		channel2.addProperty("channel", 2);
//		channel2.addProperty("switch", true);
//		JsonObject channel3 = new JsonObject();
//		channel3.addProperty("channel", 3);
//		channel3.addProperty("switch", true);
//		JsonObject channel4 = new JsonObject();
//		channel4.addProperty("channel", 4);
//		channel4.addProperty("switch", true);
//		jsonArray.add(channel1);
//		jsonArray.add(channel2);
//		jsonArray.add(channel3);
//		jsonArray.add(channel4);
//		jsonObject2.add("channel_collection", jsonArray);
//		jsonObject.add("function", jsonObject2);
		
//		System.err.println(jsonObject.toString());
		//55 00 00 03 04 1E 7E D6

		String req="5500000304"+fixed(Integer.toHexString(30),2);
		ByteBuf bf =  ByteBufAllocator.DEFAULT.buffer(18);;
		byte[] bytes = req.getBytes();
		for (byte b : bytes) {
			bf.writeByte(b);
			System.out.print(b);
		}
		CRC.write(bf);
		 
		String str;
	    if(bf.hasArray()) { // 处理堆缓冲区
	        str = new String(bf.array(), bf.arrayOffset() + bf.readerIndex(), bf.readableBytes());
	    } else { // 处理直接缓冲区以及复合缓冲区
//	        byte[] byt = new byte[bf.readableBytes()];
	        bf.getBytes(bf.readerIndex(), bytes);
	        str = new String(bytes, 0, bf.readableBytes());
	    }
	   System.out.println("str="+str);
	}
	/**
	 * 返回指定长度字符串（前面补len-str.length()个0）
	 * @param str
	 * @param len
	 * @return
	 */
	private static String fixed(String str,int len){
		if(null==str || str.isEmpty()){
			str="";
		}
		if(str.length()!=len)
			for(int i=0;i<len-str.length();i++){
				str="0"+str;
		}
		return str;
	}
}

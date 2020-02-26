package com.dec.pro.util;

import java.io.File;

public class Check {
	public static void main(String[] args) {
		System.out.println(isEmpty(null));
		System.out.println(isEmpty(""));
		System.out.println(isEmpty("ss"));
	}
	/**
	 * 判断字符串是否为null或""
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return true==(null==str||str.trim().length()==0)?true:false;
	}
	/**
	 * 判断字符串是否为null或"" 
	 * @param str
	 * @return
	 */
	public static String isEmptyReturnNull(String str){
		if( null==str || "".equals(str))
			str=null;
		return str;
	}
	/**
	 * 判断文件或路径是否存在，不存在则创建
	 * @param file
	 * @return
	 */
	public static boolean checkFileIsExists(File file){
		 if (!file.getParentFile().exists()) {
			 return file.getParentFile().mkdirs();
			}
		 return true;
		 }
}
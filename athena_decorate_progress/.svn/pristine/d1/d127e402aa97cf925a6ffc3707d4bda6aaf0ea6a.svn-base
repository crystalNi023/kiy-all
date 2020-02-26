package com.dec.pro.util;

import java.io.File;

public class Util {
	/**
	 * 验证文件夹是否存在，不存在则创建
	 * @param file
	 */
	public static void checkFileIsExists(File file){
		 if (!file.getParentFile().exists()) {
	            boolean result = file.getParentFile().mkdirs();
	            if (!result) {
	                System.err.println("文件夹创建失败");
	            }
	        }
	}
}

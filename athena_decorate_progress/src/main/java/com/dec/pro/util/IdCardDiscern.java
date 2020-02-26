package com.dec.pro.util;

import java.util.Calendar;

/**
 * 二代身份证识别
 * @author Administrator
 *
 */
public class IdCardDiscern {
	/**
	 * 获取性别
	 * @param idCard
	 * @return
	 */
	public static String getSex(String idCard){
		String sex="未知";
		if(idCard != null && idCard.length()>=18){
			//第17位数字表示性别：奇数表示男性，偶数表示女性；
			if(Integer.valueOf(idCard.substring(16, 17))%2==1)
				sex="男";
			else
				sex="女";
		}
		return sex;
	}
	/**
	 * 获取年龄
	 * @param idCard
	 * @return
	 */
	public static int getAge(String idCard){
		 Calendar date = Calendar.getInstance();
	     int year = date.get(Calendar.YEAR);
		int age=0;
		if(idCard != null && idCard.length()>=18){
			age=year-Integer.valueOf(idCard.substring(6, 10));
		}
		return age;
	}
	public static void main(String[] args) {
		String idCard="360104198307090434";
		System.out.println(IdCardDiscern.getAge(idCard));
		System.out.println(IdCardDiscern.getSex(idCard));
	}
}

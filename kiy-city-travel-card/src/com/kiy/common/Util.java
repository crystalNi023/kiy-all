package com.kiy.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Util {
	public static final String EMPTY = "";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String dateFormat(Date d) {
		if (d == null)
			return EMPTY;
		return simpleDateFormat.format(d);
	}

	/**
	 * String转Date
	 * @param s
	 * @throws ParseException
	 */
	public static final Date parse(String s) throws ParseException {
		if (s == null)
			return null;
		return simpleDateFormat.parse(s);

	}

	/**
	 * MD5 加密
	 * 
	 * @param str
	 * @return
	 */
	public static final String MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		// MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
		// MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
		// MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
		// MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
		// MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
		// MD5 ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz") =
		// f29939a25efabaef3b87e2cbfe641315
		// MD5 ("8a683566bcc7801226b3d8b0cf35fd97") =
		// cf2cb5c89c5e5eeebef4a76becddfcfd
	}

	public static final boolean isEmpty(String s) {
		if (s == null)
			return true;
		if (s.length() > 0)
			return false;
		return true;
	}

	/**
	 * 返回字符串
	 * 
	 * @param string
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return EMPTY;
		}
		String str = String.valueOf(obj);
		if (!isEmpty(str)) {
			return str;
		}
		return EMPTY;
	}
	
	/**
	 * 获取当前时间减一天
	 * @throws ParseException 
	 */
	public static Date getDateReduceOne() throws ParseException{
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		Date endDate = simpleDateFormat.parse(simpleDateFormat.format(date.getTime()));
		return endDate;
	}
}

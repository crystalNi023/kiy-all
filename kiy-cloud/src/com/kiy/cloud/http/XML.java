/**
 * 2017年2月17日
 */
package com.kiy.cloud.http;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class XML {

	/**
	 * <?xml version="1.0" encoding="utf-8"?>
	 * 
	 * @param p
	 */
	public static void document(PrintWriter p) {
		p.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	}

	/**
	 * <name>
	 * 
	 * @param p
	 * @param name
	 */
	public static void elementBegin(PrintWriter p, String name) {
		p.print('<');
		p.print(name);
		p.print('>');
	}

	/**
	 * </name>
	 * 
	 * @param p
	 * @param name
	 */
	public static void elementEnd(PrintWriter p, String name) {
		p.print('<');
		p.print('/');
		p.print(name);
		p.print('>');
	}

	public static void element(PrintWriter p, String name, Date value) {
		DateFormat f = DateFormat.getDateTimeInstance();
		element(p, name, f.format(value));
	}

	public static void element(PrintWriter p, String name, Boolean value) {
		element(p, name, value ? "true" : "false");
	}

	public static void element(PrintWriter p, String name, int value) {
		element(p, name, Integer.toString(value));
	}
	
	public static void element(PrintWriter p, String name, float value) {
		element(p, name, Float.toString(value));
	}

	/**
	 * <name>value</name>
	 * 
	 * @param p
	 * @param name
	 * @param value
	 */
	public static void element(PrintWriter p, String name, String value) {
		p.print('<');
		p.print(name);
		p.print('>');
		p.print(value);
		p.print("</");
		p.print(name);
		p.print('>');
	}
}
package com.kiy.view;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.driver.Log;



/**
 * 创建时间:2017年6月21日上午9:48:21
 *
 * @author chenpan
 *
 * 
 */
public class F {
	public static final String EMPTY = "";

	/**
	 * 输入校验,数值(整数,小数)
	 */
	public static final VerifyListener vNumber = new VerifyListener() {

		@Override
		public void verifyText(VerifyEvent e) {
			if (e.character > 0) {
				if (e.character == SWT.DEL || e.character == SWT.BS) {
					return;
				}
				if (Character.isDigit(e.character) || e.character == '.') {
					e.doit = true;
					return;
				}
				e.doit = false;
				return;
			}
			if (e.text != null && e.text.length() > 0) {
				// 粘帖复制
				char value;
				for (int index = 0; index < e.text.length(); index++) {
					value = e.text.charAt(index);
					if (Character.isDigit(value) || value == '.') {
						continue;
					} else {
						e.doit = false;
						return;
					}
				}
			}
		}
	};

	/**
	 * 检测聚合值中是否包含指定值
	 * 
	 * @param source
	 * @param value
	 * @return
	 */
	public static boolean contains(int source, int value) {
		return (source & value) != 0;
	}

	
	/**
	 * 输入校验,数值(整数)
	 */
	public static final VerifyListener intNumber = new VerifyListener() {

		@Override
		public void verifyText(VerifyEvent e) {
			if (e.character > 0) {
				// 删除
				if (e.character == SWT.DEL || e.character == SWT.BS) {
					return;
				}
				// 数字
				if (Character.isDigit(e.character)) {
					e.doit = true;
					return;
				}
				e.doit = false;
				return;
			}
			if (e.text != null && e.text.length() > 0) {
				// 粘帖复制
				char value;
				for (int index = 0; index < e.text.length(); index++) {
					value = e.text.charAt(index);
					if (Character.isDigit(value)) {
						continue;
					} else {
						e.doit = false;
						return;
					}
				}
			}

		}
	};



	/**
	 * 将子窗口置于父窗口居中
	 */
	public static void parentCenter(Shell parent, Shell shell) {
		Rectangle parentBounds = null;
		if (parent != null) {
			parentBounds = parent.getBounds();
		} else {
			parentBounds = shell.getParent().getBounds();
		}
		Rectangle shellBounds = shell.getBounds();
		shell.setLocation(parentBounds.x + (parentBounds.width - shellBounds.width) / 2, parentBounds.y + (parentBounds.height - shellBounds.height) / 2);
	}

	/**
	 * 将主窗口置于桌面中部
	 * 
	 * @param display
	 * @param shell
	 */
	public static void screenCenter(Display display, Shell shell) {
		Rectangle bounds = display.getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
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
			Log.error(e);
			throw new RuntimeException(e);
		}
	}

	public static final boolean isEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		if (s.length() > 0)
			return false;
		return true;
	}

	/**
	 * 只允许输入数字，可以是小数点后两位
	 * 
	 * @param event
	 * @param text
	 */
	public static void numberCheck(VerifyEvent event, Text text) {
		event.doit = false;
		char myChar = event.character;
		if (event.text != null && event.text.length() > 0) {
			// 粘帖复制
			char value;
			for (int index = 0; index < event.text.length(); index++) {
				value = event.text.charAt(index);
				if (Character.isDigit(value) || value == '.') {
					continue;
				} else {
					event.doit = false;
					return;
				}
			}
		}
		if (text.getText().indexOf('.') == -1) {// 没有小数点时，可以输入小数点。(因为此验证是确保一次只能输入一个字符。)
			if (Character.isDigit(myChar) || myChar == '\b' || myChar == '.') {
				event.doit = true;
			}
		} else {// 只要有小数点，就不能输入小数点。
			if (Character.isDigit(myChar) || myChar == '\b') {
				event.doit = true;
			} // 小数点后够两位，输入无效
			if (text.getText().substring(text.getText().indexOf('.') + 1).length() > 1) {
				event.doit = false;
			}
		} // 退格有效
		if (myChar == '\b')
			event.doit = true;

	}

	public static final VerifyListener verifyMoney = new VerifyListener() {
		@Override
		public void verifyText(VerifyEvent event) {
			if (event.widget instanceof Text) {
				event.doit = Pattern.matches("\\d*|(\\d*\\.{0,1})|(\\d*\\.{0,1}\\d{0,2})", ((Text) event.widget).getText());
			}
		}
	};


	/**
	 * 只能输入整数
	 * 
	 * @param event
	 * @param text
	 */
	public static void unIntNumberCheck(VerifyEvent event, Text text) {
		event.doit = false;
		char myChar = event.character;
		if (Character.isDigit(myChar) || myChar == '\b') {
			event.doit = true;
		}
		if (myChar == '\b')
			event.doit = true;
	}

	/**
	 * 返回字符串
	 * 
	 * @param string
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return F.EMPTY;
		}
		String str = String.valueOf(obj);
		if (!F.isEmpty(str)) {
			return str;
		}
		return F.EMPTY;
	}



	/**
	 * 获得时间控件的Date对象
	 * 
	 * @param 日期控件对象
	 * @return Date对象
	 */
	public static Date getDate(DateTime dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
		return calendar.getTime();
	}

	/**
	 * 获得时间控件的Date对象
	 * 
	 * @param 日期控件对象
	 * @return Date对象
	 */
	public static Date getTime(DateTime dateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(0, 0, 0, dateTime.getHours(), dateTime.getMinutes());
		return calendar.getTime();
	}
	
	/**
	 * 设置时间控件的日期
	 * 
	 * @param 日期控件对象
	 * @return Date对象
	 */
	public static void setDate(DateTime dateTime,Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy:MM:dd");
		String[] dateS = formater.format(date).split(":");
		dateTime.setYear(Integer.valueOf(dateS[0]));
		dateTime.setMonth(Integer.valueOf(dateS[1]) - 1);
		dateTime.setDay(Integer.valueOf(dateS[2]));
	}

	/**
	 * 设置时间控件的时间
	 * 
	 * @param 日期控件对象
	 * @return Date对象
	 */
	public static void setTime(DateTime dateTime,Date time) {
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
		String[] timeS = formater.format(time).split(":");
		dateTime.setHours(Integer.valueOf(timeS[0]));
		dateTime.setMinutes(Integer.valueOf(timeS[1]));
		dateTime.setSeconds(Integer.valueOf(timeS[2]));
	}


}
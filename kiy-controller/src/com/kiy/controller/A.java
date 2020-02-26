/**
 * 2017年1月17日
 */
package com.kiy.controller;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.kiy.common.Servo;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class A<T> extends SelectionAdapter {

	protected T main;

	protected Servo servo;

	public A() {

	}

	public A(T m) {
		if (m == null)
			throw new NullPointerException();
		main = m;
	}

	public A(Servo s) {
		if (s == null)
			throw new NullPointerException();
		servo = s;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		run();
	}

	protected abstract void run();

}
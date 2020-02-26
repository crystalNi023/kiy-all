/**
 * 2017年1月19日
 */
package com.kiy.controller;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class AGuide extends A<FMain> {

	public AGuide(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		FGuide f = new FGuide();
		f.open();
	}
}
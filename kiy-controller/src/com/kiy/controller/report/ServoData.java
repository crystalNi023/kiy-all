/**
 * 2017年8月17日
 */
package com.kiy.controller.report;

import java.util.Set;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

import com.kiy.common.*;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class ServoData extends BrowserFunction {

	private final Servo servo;

	public ServoData(Browser b, Servo s, String n) {
		super(b, n);
		servo = s;
	}

	@Override
	public Object function(Object[] arguments) {
		if (servo == null)
			return null;

		StringBuilder sb = new StringBuilder();
		buildZones(sb, servo.getRootZones());

		return sb.toString();
	}

	private void buildZones(StringBuilder sb, Set<Zone> items) {
		if (items == null || items.size() == 0)
			return;

		if (sb.length() > 0)
			sb.append(",zones:[");
		else
			sb.append("[");

		boolean first = true;
		for (Zone zone : items) {
			if (!first) {
				sb.append(",");
			} else {
				first = false;
			}
			sb.append("{");
			sb.append("id:\"");
			sb.append(zone.getId());
			sb.append("\"");

			sb.append(",name:\"");
			sb.append(zone.getName());
			sb.append("\"");

			buildZones(sb, zone.getZones());
			buildDevices(sb, zone.getDevices());

			sb.append("}");
		}
		sb.append("]");
	}

	private void buildDevices(StringBuilder sb, Set<Device> items) {
		if (items == null || items.size() == 0)
			return;

		if (sb.length() > 0)
			sb.append(",");
		sb.append("devices:[");

		boolean first = true;
		for (Device device : items) {
			if (!first) {
				sb.append(",");
			} else {
				first = false;
			}
			sb.append("{");
			sb.append("id:\"");
			sb.append(device.getId());
			sb.append("\"");

			sb.append(",name:\"");
			sb.append(device.getName());
			sb.append("\"");

			sb.append(",vendor:\"");
			sb.append(device.getVendor());
			sb.append("\"");

			sb.append(",kind:\"");
			sb.append(device.getKind());
			sb.append("\"");

			sb.append("}");
		}
		sb.append("]");
	}
}
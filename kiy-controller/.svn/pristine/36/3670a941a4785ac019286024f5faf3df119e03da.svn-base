/**
 * 2017年4月2日
 */
package com.kiy.controller.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.Types;
import com.kiy.common.Unit;
import com.kiy.controller.F;
import com.kiy.controller.FMain;
import com.kiy.resources.Resource;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TreeRelayView {

	private final String KEY = "DATA";

	private final Tree tree;

	private final Map<Unit, TreeItem> items;
	private final Comparator<Unit> comparator;

	public TreeRelayView(Tree t) {
		if (t == null)
			throw new NullPointerException("Tree cannot be null");

		if (t.isDisposed())
			throw new IllegalArgumentException("Tree is disposed");

		if (!Types.contains(t.getStyle(), SWT.VIRTUAL))
			throw new IllegalArgumentException("Tree must be SWT.VIRTUAL");

		items = new ConcurrentHashMap<>();
		comparator = new Comparator<Unit>() {
			@Override
			public int compare(Unit a, Unit b) {
				if (a instanceof Servo) {
					if (b instanceof Servo)
						return String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName());
					if (b instanceof Device)
						return -1;
				} else if (a instanceof Device) {
					if (b instanceof Device)
						return String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName());
				}
				return 1;
			}
		};

		tree = t;
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (tree.isDisposed()) {
					return;
				}
				show(e);
			}
		});
	}

	private void show(Event e) {
		TreeItem item = (TreeItem) e.item;
		TreeItem parent = item.getParentItem();

		List<?> list = null;
		Object o = null;

		if (parent == null) {
			list = (List<?>) tree.getData(KEY);
		} else {
			list = (List<?>) parent.getData(KEY);
		}

		if (list == null) {
		} else {
			if (e.index >= 0 && e.index < list.size()) {
				o = list.get(e.index);
			} else {
			}
		}

		if (o == null) {
			return;
		}

		item.setData(KEY, null);

		if (o instanceof Servo) {
			Servo s = (Servo) o;
			item.setData(s);
			items.put(s, item);
			update(s);
		} else if (o instanceof Device) {
			Device d = (Device) o;
			item.setData(d);
			items.put(d, item);
			update(d);
		} else {
		}
	}

	public Tree getTree() {
		return tree;
	}

	public void refresh(Set<Servo> s) {
		if (tree.isDisposed()) {
			return;
		}

		tree.clearAll(true);
		List<Unit> list = new ArrayList<Unit>(s);
		Collections.sort(list, comparator);
		tree.setData(KEY, list);
		tree.setItemCount(list.size());
	}

	public void insert(Servo s) {
		if (s == null)
			return;

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) tree.getData(KEY);
		if (list == null)
			return;

		list.add(s);
		Collections.sort(list, comparator);

		tree.clearAll(false);
		tree.setItemCount(list.size());
	}

	public void insert(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		Unit p = d.getRelay();
		if (p == null)
			p = d.getServo();

		if (p == null)
			return;

		TreeItem parent = items.get(p);
		if (parent == null)
			return;
		
		if(parent.isDisposed()){
			return;
		}

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) parent.getData(KEY);
		if (list == null)
			return;

		list.add(d);
		Collections.sort(list, comparator);

		parent.clearAll(false);
		parent.setItemCount(list.size());
	}

	public void update(Servo s) {
		if (tree.isDisposed()) {
			return;
		}

		if (s == null)
			return;

		TreeItem item = items.get(s);
		if (item == null)
			return;
		
		if(item.isDisposed()){
			return;
		}

		item.setText(Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());

		CtrClient client = s.getClient();
		if (client == null)
			item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_wrong.png"));
		else if (client.isConnected())
			item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));
		else
			item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_disconnected.png"));

		if (client == null) {
			// 从来未连接过
			item.setItemCount(0);
			return;
		}

		if (item.getData(KEY) == null) {
			List<Unit> list = new ArrayList<Unit>();
			list.addAll(s.getDirectDevices());
			Collections.sort(list, comparator);
			item.setData(KEY, list);
			item.setItemCount(list.size());
		}
	}

	/**
	 * 刷新设备树视图
	 * 
	 * @param d
	 */
	public void update(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		TreeItem item = items.get(d);
		if (item == null)
			return;
		
		if(item.isDisposed()){
			return;
		}

		item.setText(d.getName());
		
		item.setImage(F.getDeviceImage(d));

		if (item.getData(KEY) == null) {
			List<Unit> list = new ArrayList<Unit>();
			list.addAll(d.getRelayChildren());
			Collections.sort(list, comparator);
			item.setData(KEY, list);
			item.setItemCount(list.size());
		}
	}

	public void remove(Servo s) {
		if (tree.isDisposed()) {
			return;
		}

		if (s == null)
			return;

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) tree.getData(KEY);
		if (list == null)
			return;

		list.remove(s);
		Collections.sort(list, comparator);

		tree.clearAll(false);
		tree.setItemCount(list.size());

		items.remove(s);
	}

	public void remove(Servo s, Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		if (s == null)
			return;

		Unit p = s.getDevice(d.getRelayId());
		if (p == null)
			p = s;

		TreeItem parent = items.get(p);
		if (parent == null)
			return;

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) parent.getData(KEY);
		if (list == null)
			return;

		list.remove(d);
		Collections.sort(list, comparator);

		parent.clearAll(false);
		parent.setItemCount(list.size());

		TreeItem item = items.remove(d);
		if (item == null)
			return;
	}
}

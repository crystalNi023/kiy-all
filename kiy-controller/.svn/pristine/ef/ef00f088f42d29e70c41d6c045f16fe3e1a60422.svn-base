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
import com.kiy.common.Zone;
import com.kiy.controller.F;
import com.kiy.controller.FMain;
import com.kiy.resources.Resource;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TreeZoneView {

	private final String KEY = "DATA";

	private final Tree tree;

	private final Map<Unit, TreeItem> items;
	private final Comparator<Unit> comparator;
	

	public TreeZoneView(Tree t) {
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
					if (b instanceof Zone)
						return -1;
					if (b instanceof Device)
						return -1;
				} else if (a instanceof Zone) {
					if (b instanceof Zone)
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
		} else if (o instanceof Zone) {
			Zone z = (Zone) o;
			item.setData(z);
			items.put(z, item);
			update(z);
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
		if (tree.isDisposed()) {
			return;
		}

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
	
	public void insert(Zone z) {
		if (tree.isDisposed()) {
			return;
		}

		if (z == null)
			return;

		Unit p = z.getParent();
		if (p == null)
			p = z.getServo();

		if (p == null)
			return;

		TreeItem parent = items.get(p);
		if (parent == null)
			return;

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) parent.getData(KEY);
		if (list == null)
			return;

		list.add(z);
		Collections.sort(list, comparator);

		parent.clearAll(false);
		parent.setItemCount(list.size());
	}

	public void insert(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		Unit p = d.getZone();
		if (p == null)
			p = d.getServo();

		if (p == null)
			return;

		TreeItem parent = items.get(p);
		if (parent == null)
			return;

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

		item.setText(Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());
		item.setData(s);
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
			List<Unit> list = new ArrayList<>();
			list.addAll(s.getRootZones());
			list.addAll(s.getRootDevices());
			Collections.sort(list, comparator);
			item.setData(KEY, list);
			item.setItemCount(list.size());
		}
	}

	public void update(Zone z) {
		if (tree.isDisposed()) {
			return;
		}
		if (z == null)
			return;

		TreeItem item = items.get(z);
		if (item == null)
			return;

		item.setText(z.getName());
		item.setData(z);

		if (item.getExpanded())
			item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));
		else
			item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));

		if (item.getData(KEY) == null) {
			List<Unit> list = new ArrayList<Unit>();
			list.addAll(z.getZones());
			list.addAll(z.getDevices());
			Collections.sort(list, comparator);
			item.setData(KEY, list);
			item.setItemCount(list.size());
		}
	}

	public void moveDevice(Device d,Zone newZone){
		if (tree.isDisposed()) {
			return;
		}

		if (d==null)
			return;
		
		
		TreeItem item = items.get(d);// 设备所在item
		
		if(d.getZone()==null){
			Tree parent = item.getParent();
			//移除原根节点设备
			@SuppressWarnings("unchecked")
			List<Unit> oldList = (List<Unit>)parent.getData(KEY);
			
			oldList.remove(d);
			
			Collections.sort(oldList, comparator);
			
			parent.clearAll(false);
			parent.setData(KEY, oldList);
			parent.setItemCount(oldList.size());
		}else{
			if ( item == null) {
				return;
			}
			
			TreeItem parent = item.getParentItem();
			//移除原根节点设备
			@SuppressWarnings("unchecked")
			List<Unit> oldList = (List<Unit>)parent.getData(KEY);
			
			oldList.remove(d);
			
			Collections.sort(oldList, comparator);
			
			parent.clearAll(false);
			parent.setData(KEY, oldList);
			parent.setItemCount(oldList.size());
		}
		
		//将设备添加到新的根节点
		if(newZone == null){
			TreeItem treeItem = items.get(d.getServo());
			if(treeItem==null){
				//移动的区域未展开初始化时不操作
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<Unit> newList = (List<Unit>)treeItem.getData(KEY);
			newList.add(d);
			Collections.sort(newList, comparator);
			treeItem.clearAll(false);
			treeItem.setData(KEY, newList);
			treeItem.setItemCount(newList.size());
		}else{
			TreeItem treeItem = items.get(newZone);
			
			if(treeItem==null){
				//移动的区域未展开初始化时不操作
				return;
			}

			@SuppressWarnings("unchecked")
			List<Unit> newList = (List<Unit>)treeItem.getData(KEY);
			newList.add(d);
			Collections.sort(newList, comparator);
			treeItem.clearAll(false);
			treeItem.setData(KEY, newList);
			treeItem.setItemCount(newList.size());
		}
	}
	
	public void update(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;
		
		TreeItem item = items.get(d);
		
		if (item == null)
			return;
		
		item.setData(d);
		item.setText(d.getName());
		
		item.setImage(F.getDeviceImage(d));

		item.setItemCount(0);
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

	public void remove(Servo s, Zone z) {
		if (tree.isDisposed()) {
			return;
		}

		if (z == null)
			return;

		if (s == null)
			return;

		Unit p = s.getZone(z.getParentId());
		if (p == null)
			p = s;

		TreeItem parent = items.get(p);
		if (parent == null)
			return;

		@SuppressWarnings("unchecked")
		List<Unit> list = (List<Unit>) parent.getData(KEY);
		if (list == null)
			return;

		list.remove(z);
		Collections.sort(list, comparator);

		parent.clearAll(false);
		parent.setItemCount(list.size());

		TreeItem item = items.remove(z);
		if (item == null)
			return;
	}

	public void remove(Servo s, Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		if (s == null)
			return;

		Unit p = s.getZone(d.getZoneId());
		if (p == null)
			p = s;

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

		list.remove(d);
		Collections.sort(list, comparator);

		parent.clearAll(false);
		parent.setItemCount(list.size());

		TreeItem item = items.remove(d);
		if (item == null)
			return;
	}
}
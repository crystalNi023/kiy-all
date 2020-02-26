/**
 * 2017年4月2日
 */
package com.kiy.controller.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types;
import com.kiy.common.Unit;
import com.kiy.common.Zone;
import com.kiy.controller.F;
import com.kiy.controller.FMain;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * 基于Table的设备状态视图
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TreeView {

	private final Tree tree;

	private final Map<Device, TreeItem> items;
	private final List<Device> list;
	private Set<Device> set;

	private Unit parent;

	public TreeView(Tree t) {
		if (t == null)
			throw new NullPointerException("Table cannot be null");

		if (t.isDisposed())
			throw new IllegalArgumentException("Table is disposed");

		if (!Types.contains(t.getStyle(), SWT.VIRTUAL))
			throw new IllegalArgumentException("Table must be SWT.VIRTUAL");

		tree = t;

		items = new ConcurrentHashMap<>();
		list = new ArrayList<>();

		initialize();
	}

	private void initialize() {
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (tree.isDisposed()) {
					return;
				}
				show(event);
			}
		});

		Listener columnListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (tree.isDisposed()) {
					return;
				}
				sort((TreeColumn) e.widget);
			}
		};
		TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setWidth(180);
		column1.setText(Lang.getString("Table.deviceName"));
		column1.setMoveable(true);
		column1.addListener(SWT.Selection, columnListener);

		TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
		column2.setWidth(140);
		column2.setText(Lang.getString("Table.number"));
		column2.setMoveable(true);
		column2.addListener(SWT.Selection, columnListener);

		TreeColumn column4 = new TreeColumn(tree, SWT.LEFT);
		column4.setWidth(100);
		column4.setText(Lang.getString("Table.alarm"));
		column4.setMoveable(true);
		column4.addListener(SWT.Selection, columnListener);

		TreeColumn column5 = new TreeColumn(tree, SWT.LEFT);
		column5.setWidth(155);
		column5.setText(Lang.getString("Table.date"));
		column5.setMoveable(true);
		column5.addListener(SWT.Selection, columnListener);

		// 无排序
		TreeColumn column6 = new TreeColumn(tree, SWT.LEFT);
		column6.setWidth(250);
		column6.setText(Lang.getString("Table.address"));
		column6.setMoveable(true);

		tree.setSortColumn(column4);
		tree.setSortDirection(SWT.UP);
	}

	private void show(Event e) {
		// 每个item第一次显示时
		Device d = null;
		TreeItem item = (TreeItem) e.item;
		Tree parent = item.getParent();
		if (parent != tree) {
			return;
		}

		if (e.index >= 0 && e.index < list.size())
			d = list.get(e.index);

		if (d == null)
			return;

		item.setData(d);
		items.put(d, item);
		update(d);
	}

	private void sort(TreeColumn widget) {
		TreeColumn column = tree.getSortColumn();
		if (widget == null && column == null)
			return;

		int dir = tree.getSortDirection();
		if (widget == null) {
			widget = column;
		} else if (column == widget) {
			dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			tree.setSortDirection(dir);
		} else {
			dir = SWT.UP;
			tree.setSortColumn(widget);
			tree.setSortDirection(dir);
		}

		final int direction = dir;
		switch (tree.indexOf(widget)) {
			case 0:
				Collections.sort(list, new Comparator<Device>() {
					@Override
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(b.getName(), a.getName());
					}
				});
				break;
			case 1:
				Collections.sort(list, new Comparator<Device>() {
					@Override
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(a.getIndicate(), b.getIndicate());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(b.getIndicate(), a.getIndicate());
					}
				});
				break;
			case 2:
				Collections.sort(list, new Comparator<Device>() {
					@Override
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getStatus().getValue(), b.getStatus().getValue());
						else
							return Integer.compare(b.getStatus().getValue(), a.getStatus().getValue());
					}
				});
				break;
			case 3:
				Collections.sort(list, new Comparator<Device>() {
					@Override
					public int compare(Device a, Device b) {
						Date da = a.getUpdated();
						Date db = b.getUpdated();

						if (SWT.UP == direction) {
							if (da == null) {
								if (db == null)
									return 0;
								else
									return -1;
							} else {
								if (db == null)
									return 1;
								else
									return da.compareTo(db);
							}
						} else {
							if (da == null) {
								if (db == null)
									return 0;
								else
									return 1;
							} else {
								if (db == null)
									return -1;
								else
									return db.compareTo(da);
							}
						}
					}

				});
				break;
		}

		tree.clearAll(true);
	}

	public Tree getTree() {
		return tree;
	}

	/**
	 * 刷新整个表
	 * 
	 * @param s
	 */
	public void refresh(Servo s) {
		if (tree.isDisposed()) {
			return;
		}

		if (s == null)
			return;

		parent = s;

		set = s.getDevices();
		tree.removeAll();
		items.clear();
		list.clear();
		list.addAll(set);
		sort(null);
		tree.setItemCount(list.size());

	}

	/**
	 * 刷新整个表
	 * 
	 * @param z
	 */
	public void refresh(Zone z) {
		if (tree.isDisposed()) {
			return;
		}

		if (z == null)
			return;

		parent = z;

		set = z.getDevices();
//		set = getDevice(z);
		
		tree.removeAll();
		items.clear();
		list.clear();
		list.addAll(set);
		sort(null);
		tree.setItemCount(list.size());

	}

//	public Set<Device> getDevice(Zone z){
//		if(z == null){
//			return null;
//		}
//		Set<Device> devices = new HashSet<>();
//		
//		Set<Device> devices1 = z.getDevices();
//		devices.addAll(devices1);
//		
//		if(!z.getZones().isEmpty()){
//			for(Zone zone:z.getZones()){
//				Set<Device> device = getDevice(zone);
//				devices.addAll(device);
//			}
//		}
//		
//		return devices;
//	}
	
	
	public void search(Set<Device> d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null) {
			tree.removeAll();
			return;
		}

		set = d;
		tree.removeAll();
		items.clear();
		list.clear();
		list.addAll(set);
		sort(null);
		tree.setItemCount(list.size());

	}

	/**
	 * 刷新整个表
	 * 
	 * @param d 用于点击中继加载中继下设备
	 */
	public void refresh(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;
		parent = d;

		set = d.getRelayChildren();
		// //点击中继设备，将中继设备与该中继下的设备显示在tableView中
		// if(set == null){
		// set = new HashSet<Device>();
		// }
		// set.add(d);
		tree.removeAll();
		items.clear();
		list.clear();
		list.addAll(set);
		sort(null);
		tree.setItemCount(list.size());

	}

	/**
	 * 更新设备,方法将判断是否应当显示在当前视图中
	 * 
	 * @param d
	 */
	public void update(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		if (parent == null)
			return;

		if (parent.equals(d.getServo()==null?null:d.getServo()) || parent.equals(d.getZone()==null?null:d.getZone()) || parent.equals(d.getRelay()==null?null:d.getRelay())) {

			if (set.contains(d)) {
				TreeItem item = items.get(d);

				if (item == null)
					return;

				item.removeAll();

				// 1.设备ip/名称
				item.setText(0, d.getName());
				// 2.设备读数
				item.setText(1, d.getIndicate());
				// 3.设备告警状态
				item.setText(2, Lang.getString("AlarmType." + d.getStatus().toString()));
				item.setImage(0,F.getDeviceImage(d));
				// 5.设备更新时间
				item.setText(3, F.format(new Date(d.getTickStatus()))); // s.getUpdated()
				// 6.设备安装地址
				item.setText(4, d.getAddress());

				if (d.getFeatures() != null && d.getFeatures().length >= 0) {
					Feature[] features = d.getFeatures();
					for (Feature feature : features) {
						if (feature.PRIMARY) {
							TreeItem treeItem = new TreeItem(item, SWT.LEFT);
							treeItem.setText(2, feature.getAlarm() != null ? Lang.getString("AlarmType." + feature.getAlarm().name()) : "no alarm");
							treeItem.setData(d);
							treeItem.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/dot.png"));
							treeItem.setText(0, feature.getName() != null ? feature.getName() : "no name");
							treeItem.setText(1, feature.getText() != null ? feature.getText() : "no value");
						}
					}
				} else {
					// 没有feature
				}

			} else {
				insert(d);
			}
		} else {
			remove(d);
		}
	}

	/**
	 * 添加设备,方法将判断是否应当显示在当前视图中
	 * 
	 * @param d
	 */
	public void insert(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (parent == null)
			return;

		if (parent.equals(d.getServo()) || parent.equals(d.getZone()) || parent.equals(d.getRelay())) {
			tree.removeAll();
			set.add(d);
			list.add(d);
			sort(null);
			tree.setItemCount(list.size());
		}
	}

	/**
	 * 移除设备从当前视图中
	 * 
	 * @param d
	 */
	public void remove(Device d) {
		if (tree.isDisposed()) {
			return;
		}

		if (d == null)
			return;

		items.remove(d);

		if (list.remove(d)) {
			sort(null);
			tree.setItemCount(list.size());
			tree.clearAll(true);
		}
	}

	public void removeAll() {
		tree.removeAll();
	}

	public void expandAll() {
		TreeItem[] items = tree.getItems();
		for (int i = 0; i < items.length; i++) {
			if (!items[i].getExpanded()) {
				items[i].setExpanded(true);
			}
		}
	}
	
	public void selectAll(){
//		TreeItem[] items = tree.getItems();
//		for (int i = 0; i < items.length; i++) {
			tree.selectAll();
//		}
	}

	public void packUpAll() {
		TreeItem[] items = tree.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getExpanded()) {
				items[i].setExpanded(false);
			}
		}
	}
}
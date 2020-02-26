package com.kiy.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Vendor;
import com.kiy.common.Zone;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

public class FSelectDevice extends Dialog {
	private Tree treeDevice;
	private Set<Device> tag;
	private Shell shell;
	private Servo servo;
	private CCombo comboDeviceKind;
	private CCombo comboDeviceModel;
	private List<Vendor> vendors;
	private List<Kind> kinds;
	private List<Model> models;
	private Vendor vendor;

	public boolean isEnsure = false;
	private CCombo comboDeviceVendor;

	public FSelectDevice(Shell parent) {
		super(parent);
	}

	public Set<Device> open(Servo s, Set<Device> devices) {
		if (s == null) {
			throw new IllegalArgumentException("Servo Cannot be null!!!");
		}
		servo = s;
		tag = devices;

		createContents();
		F.center(getParent(), shell);

		// 确认按钮
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(154, 310, 67, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.addSelectionListener(btnAcceptSelectionAdapter);

		// 全选按钮
		Button btnAll = new Button(shell, SWT.NONE);
		btnAll.setBounds(41, 310, 67, 27);
		btnAll.setText(Lang.getString("FSelectDay.ButtonSelectAll.text"));
		btnAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TreeItem item : treeDevice.getItems()) {
					item.setChecked(true);
					checkAllChildren(item.getItems(), true);
				}
			}
		});

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return tag;
	}

	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.setSize(295, 381);
		shell.setText("选择设备");

		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBounds(16, 16, 49, 23);
		lblNewLabel.setText("厂商：");

		comboDeviceVendor = new CCombo(shell, SWT.BORDER);
		comboDeviceVendor.setBounds(73, 18, 180, 21);
		vendors = Tool.getVendors();
		for (int i = 0; i < vendors.size(); i++) {
			comboDeviceVendor.add(Lang.getString("Vendor." + vendors.get(i).name()), i);
		}
		comboDeviceVendor.addSelectionListener(comboDeviceVendorSelectionAdapter);

		CLabel label = new CLabel(shell, SWT.NONE);
		label.setText("类型：");
		label.setBounds(16, 54, 44, 23);

		comboDeviceKind = new CCombo(shell, SWT.BORDER);
		comboDeviceKind.setBounds(73, 56, 180, 21);
		comboDeviceKind.addSelectionListener(comboDeviceKindSelectionAdapter);

		CLabel label2 = new CLabel(shell, SWT.NONE);
		label2.setText("型号：");
		label2.setBounds(16, 92, 44, 23);

		comboDeviceModel = new CCombo(shell, SWT.BORDER);
		comboDeviceModel.setBounds(73, 94, 180, 21);
		comboDeviceModel.addSelectionListener(comboDeviceModelSelectionAdapter);

		treeDevice = new Tree(shell, SWT.BORDER | SWT.CHECK);
		treeDevice.setBounds(16, 129, 256, 173);
		treeDevice.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					boolean checked = item.getChecked();
					checkAllChildren(item.getItems(), checked);
					// 触发这个的Item的grayed = false，因为这是个CHECK事件，要么全选，要么全不选。
					checkParent(item.getParentItem(), checked, false);
				}
			}
		});

	}

	private void accept() {
		if (tag == null) {
			tag = new HashSet<>();
		}

		tag.clear();
		TreeItem[] items = treeDevice.getItems();
		for (TreeItem treeItem : items) {
			if (treeItem.getChecked() == true) {
				Object data = treeItem.getData();
				if (data != null && data instanceof Device) {
					Device device = (Device) data;
					tag.add(device);
				}
			}
			getCheckDeviceForChild(treeItem);
		}

		shell.close();
	}

	private void getCheckDeviceForChild(TreeItem item) {
		if (item.getItemCount() <= 0) {
			return;
		}

		TreeItem[] items = item.getItems();
		for (TreeItem treeItem : items) {
			if (treeItem.getChecked() == true) {
				Object data = treeItem.getData();
				if (data != null && data instanceof Device) {
					Device device = (Device) data;
					tag.add(device);
				}
			}
			getCheckDeviceForChild(treeItem);
		}
	}

	/**
	 * 根据厂商获取类型
	 */
	private SelectionAdapter comboDeviceVendorSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int index = comboDeviceVendor.getSelectionIndex();
			vendor = vendors.get(index);
			if (vendor != null) {
				treeDevice.removeAll();
				comboDeviceKind.removeAll();
				comboDeviceModel.removeAll();
				kinds = Tool.getKindsByVendor(vendor);
				for (int i = 0; i < kinds.size(); i++) {
					comboDeviceKind.add(Lang.getString("Kind." + kinds.get(i)), i);
				}
			}
		}
	};

	/**
	 * 根据类型获取型号
	 */
	private SelectionAdapter comboDeviceKindSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int index = comboDeviceKind.getSelectionIndex();
			Kind kind = kinds.get(index);
			if (kind != null) {
				treeDevice.removeAll();
				comboDeviceModel.removeAll();
				models = Tool.getModelsByVendor(vendor, kind);
				for (int i = 0; i < models.size(); i++) {
					comboDeviceModel.add(models.get(i).name(), i);
				}
			}
		};
	};

	/**
	 * 设备型号下拉框点击事件（用于更新树结构）
	 */
	private SelectionAdapter comboDeviceModelSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			treeDevice.removeAll();

			Set<Device> rootDevices = servo.getRootDevices();
			Set<Zone> rootZones = servo.getRootZones();
			// 1.根级设备
			int index = comboDeviceModel.getSelectionIndex();
			Model model = models.get(index);
			for (Device device : rootDevices) {
				if (device.getModel() == model) {
					TreeItem item = new TreeItem(treeDevice, SWT.NONE);
					item.setText(device.getName());
					item.setData(device);
				}
			}
			// 2.递归显示区域下的设备
			for (Zone zone : rootZones) {
				showDeviceForZone(treeDevice, zone);
			}
		};
	};

	/**
	 * 确定按钮事件
	 */
	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			accept();
		}
	};

	/**
	 * 子节点选中
	 * 
	 * @param children
	 * @param checked
	 */
	private void checkAllChildren(TreeItem[] children, boolean checked) {
		if (children.length == 0)
			return;
		for (TreeItem child : children) {
			child.setChecked(checked);
			child.getParentItem().setGrayed(false);
			checkAllChildren(child.getItems(), checked);
		}
	}

	/**
	 * 父节点选中
	 * 
	 * @param parent
	 * @param checked
	 * @param grayed
	 */
	private void checkParent(TreeItem parent, boolean checked, boolean grayed) {
		if (parent == null)
			return;
		for (TreeItem child : parent.getItems()) {

			if (checked != child.getChecked()) {
				// 选中状态不相同，没有全部选中
				checked = true;
				grayed = true;
				break;
			} else {
				grayed = false;
			}
		}
		parent.setChecked(checked);
		parent.setGrayed(grayed);

		TreeItem parentItem = parent.getParentItem();
		checkParent(parentItem, checked, grayed);
	}

	private void showDeviceForZone(Tree parent, Zone zone) {
		int index = comboDeviceModel.getSelectionIndex();
		Model model = models.get(index);
		TreeItem item = new TreeItem(parent, SWT.CHECK);
		item.setText(zone.getName());
		item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));
		Set<Device> devices = zone.getDevices();
		for (Device device : devices) {
			if (device.getModel() == model) {
				TreeItem item1 = new TreeItem(item, SWT.CHECK);
				item1.setText(device.getName());
				item1.setData(device);
				item1.setImage(F.getDeviceImageIngnoreStatus(device));
			}
		}
		Set<Zone> zones = zone.getZones();
		for (Zone zone2 : zones) {
			showDeviceForZone(item, zone2);
		}
		item.setExpanded(true);
	}

	private void showDeviceForZone(TreeItem parent, Zone zone) {
		int index = comboDeviceModel.getSelectionIndex();
		Model model = models.get(index);
		TreeItem item = new TreeItem(parent, SWT.CHECK);
		item.setText(zone.getName());
		item.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));
		Set<Device> devices = zone.getDevices();
		for (Device device : devices) {
			if (device.getModel() == model) {
				TreeItem item1 = new TreeItem(item, SWT.CHECK);
				item1.setText(device.getName());
				item1.setData(device);
				item1.setImage(F.getDeviceImageIngnoreStatus(device));
			}
		}
		Set<Zone> zones = zone.getZones();
		for (Zone zone2 : zones) {
			showDeviceForZone(item, zone2);
		}
		item.setExpanded(true);
	}

}

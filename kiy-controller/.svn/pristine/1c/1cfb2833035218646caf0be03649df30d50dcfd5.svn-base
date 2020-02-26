package com.kiy.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.controller.view.AddDeviceTool;
import com.kiy.controller.view.MapView;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.geotools.swt.SwtMapPane;

/**
 * 
 * @author HLX Window of Add Not added Device On the Map
 * 
 *
 */
public class FAddDeviceOnMap extends Dialog {

	private Servo servo;
	private Set<Device> exitsDevice;
	private List<Device> unAddDecices;
	private Shell shell;
	private Table table;
	private SwtMapPane pane;
	private MapView mapView;

	/**
	 * 
	 * @param arg0
	 */
	public FAddDeviceOnMap(Shell arg0) {
		super(arg0);
	}

	/**
	 * 
	 * Open A Window of Add Not added Device On the Map
	 * 
	 * @param s Servo
	 * @param exitsDevices Set<Device> A Collection of devices already on the
	 *            map
	 * @param p SwtMapPane
	 * @param mapView MapView
	 */
	public void open(Servo s, Set<Device> exitsDevices, SwtMapPane p, MapView mapView) {
		this.servo = s;
		this.exitsDevice = exitsDevices;
		this.mapView = mapView;
		unAddDecices = new ArrayList<Device>();
		pane = p;

		if (servo == null) {
			throw new NullPointerException("servo can not be null");
		}

		Set<Device> devices = servo.getDevices();

		for (Device device1 : devices) {
			unAddDecices.add(device1);
		}

		if (exitsDevices != null) {
			for (Device device : exitsDevice) {
				unAddDecices.remove(device);
			}
		}

		defaultSortZone();

		createContent();

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.SYSTEM_MODAL | SWT.BORDER | SWT.CLOSE);
		shell.setSize(574, 521);
		shell.setText(Lang.getString("FAddDeviceToMap.Title.text"));

		F.center(getParent(), shell);

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(16, 16, 537, 421);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.addListener(SWT.MouseDoubleClick, listener);

		TableColumn deviceName = new TableColumn(table, SWT.LEFT);
		deviceName.setWidth(113);
		deviceName.setMoveable(true);
		deviceName.addListener(SWT.Selection, columnListener);
		deviceName.setText(Lang.getString("FAddDeviceToMap.DeviceName.text"));

		TableColumn deviceZone = new TableColumn(table, SWT.LEFT);
		deviceZone.setWidth(120);
		deviceZone.setMoveable(true);
		deviceZone.addListener(SWT.Selection, columnListener);
		deviceZone.setText(Lang.getString("FAddDeviceToMap.Zone.text"));

		TableColumn deviceAddress = new TableColumn(table, SWT.LEFT);
		deviceAddress.setWidth(301);
		deviceAddress.setMoveable(true);
		deviceAddress.addListener(SWT.Selection, columnListener);
		deviceAddress.setText(Lang.getString("FAddDeviceToMap.Address.text"));

		showDevice();

		Button btnAccpet = new Button(shell, SWT.NONE);
		btnAccpet.setBounds(349, 445, 98, 30);
		btnAccpet.setText(Lang.getString("Ensure.text"));
		btnAccpet.addSelectionListener(btnAcceptSelectionAdapter);

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(455, 445, 98, 30);
		btnCancel.setText(Lang.getString("Cancel.text"));
		btnCancel.addSelectionListener(btnCancelSelectionAdapter);

	}

	private void showDevice() {
		for (Device device : unAddDecices) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, device.getName());
			item.setImage(0, Resource.getImage(FMain.class, String.format("/com/kiy/resources/device_%s_none.png", Tool.toLowerCase(device.getKind()))));
			item.setText(1, device.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : device.getZone().getName());
			item.setText(2, device.getAddress());
			item.setData(device);
		}
	}

	/**
	 * sort Listener
	 */
	Listener columnListener = new Listener() {
		@Override
		public void handleEvent(Event e) {
			sort((TableColumn) e.widget);
		}
	};

	/**
	 * default devices sort,base on install Zone
	 */
	private void defaultSortZone() {
		Collections.sort(unAddDecices, new Comparator<Device>() {
			public int compare(Device a, Device b) {
				return String.CASE_INSENSITIVE_ORDER.compare(a.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : a.getZone().getName(), b.getZone() == null ? Lang.getString("RootDevice") : b.getZone().getName());
			}
		});
	}

	/**
	 * sort devices
	 * 
	 * @param c
	 */
	private void sort(TableColumn c) {
		TableColumn sortColumn = table.getSortColumn();
		if (c == null && sortColumn == null)
			return;

		int dir = table.getSortDirection();
		if (c == null) {
			c = sortColumn;
		} else if (sortColumn == c) {
			dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		} else {
			table.setSortColumn(c);
			dir = SWT.UP;
		}

		final int direction = dir;
		switch (table.indexOf(c)) {
			case 0:
				Collections.sort(unAddDecices, new Comparator<Device>() {
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(b.getName(), a.getName());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(a.getName(), b.getName());
					}
				});
				break;
			case 1:
				Collections.sort(unAddDecices, new Comparator<Device>() {
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(a.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : a.getZone().getName(), b.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : b.getZone().getName());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(b.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : b.getZone().getName(), a.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : a.getZone().getName());
					}
				});
				break;
			case 2:
				Collections.sort(unAddDecices, new Comparator<Device>() {
					public int compare(Device a, Device b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(a.getAddress(), b.getAddress());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(b.getAddress(), a.getAddress());
					}
				});
				break;
			default:
				break;
		}
		table.setSortDirection(dir);
		table.removeAll();

		showDevice();
	}

	/**
	 * Accept Button SelectionAdapter
	 */
	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			TableItem[] selection = table.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FAddDeviceToMap.NoSelect.text"), Lang.getString("FAddDeviceToMap.NoSelect.Content"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof Device) {
				Device device = (Device) data;
				String id = device.getId();
				pane.setCursorTool(new AddDeviceTool(id, mapView, servo));
			}
			shell.close();
		}

	};
	
	private Listener listener = new Listener() {
		
		@Override
		public void handleEvent(Event arg0) {
			TableItem[] selection = table.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FAddDeviceToMap.NoSelect.text"), Lang.getString("FAddDeviceToMap.NoSelect.Content"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof Device) {
				Device device = (Device) data;
				String id = device.getId();
				pane.setCursorTool(new AddDeviceTool(id, mapView, servo));
			}
			shell.close();
		}
	};

	/**
	 * Cancel Button SelectionAdapter
	 */
	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			shell.close();
		}

	};
}

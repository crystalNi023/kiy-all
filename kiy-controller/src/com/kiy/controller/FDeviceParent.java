package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.resources.Lang;

public class FDeviceParent extends Dialog {
	private Shell shell;
	public String combValue;
	private Tree treeZone;
	private TreeItem item;

	public FDeviceParent(Shell parent) {
		super(parent);
	}

	public void open(Shell fDevice, Servo servo) {
		createContents(fDevice, servo);
		F.center(getParent(), shell);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(Shell fDevice, Servo servo) {
		shell = new Shell(fDevice, SWT.BORDER | SWT.CLOSE);

		shell.setSize(343, 344);
		shell.setText(Lang.getString("FDeviceParent.shellName"));

		treeZone = new Tree(shell, SWT.BORDER);
		treeZone.setBounds(16, 16, 304, 250);

		Set<Device> devices = servo.getDevices();
		for (Device device : devices) {
			if (device.getRelay() == null) {
				TreeItem items = new TreeItem(treeZone, SWT.NONE);
				items.setText(device.getName());
				items.setData(device);
				listDevices(device, items);
			}
		}

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(240, 274, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));

		treeZone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				item = (TreeItem) e.item;
			}
		});
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (item == null) {
					shell.close();
					return;
				}

				combValue = item.getText();

				shell.close();
			}
		});
	}

	private void listDevices(Device rootDevice, TreeItem item) {
		Set<Device> relayChildren = rootDevice.getRelayChildren();
		if (relayChildren != null && (relayChildren.size() > 0)) {
			for (Device device : relayChildren) {
				TreeItem suvItem = new TreeItem(item, SWT.NONE);
				suvItem.setText(device.getName());
				item.setData(device);
				listDevices(device, suvItem);
			}
		}
	}
}
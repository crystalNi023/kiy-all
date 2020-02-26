package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.resources.Lang;

public class FRelayParent extends FZone {
	protected Shell shell;
	protected String combValue;
	private TreeItem item;
	private TreeItem item2;

	public FRelayParent(Shell parent) {
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
		shell.setText(Lang.getString("FRelayParent.ShellTitle.text"));

		Tree treeRelay = new Tree(shell, SWT.BORDER);
		treeRelay.setBounds(16, 16, 304, 250);

		// 获取所有根区域
		Set<Device> rootRelays = servo.getDevices();
	
		for (Device rootRelay : rootRelays) {
			Device rootRelay2 = rootRelay.getRelay();
			if(rootRelay2==null){
				item = new TreeItem(treeRelay, SWT.NONE);
				item.setText(rootRelay.getName());
				// 列出树形结构
				listRelays(rootRelay, item);
			}
		}

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(240, 274, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		treeRelay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				item2 = (TreeItem) e.item;
			}
		});
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(item2==null){
					shell.close();
					return;
				}
				combValue = item2.getText();
				shell.close();
			}
		});
	}

	// 递归树形父区域
	private void listRelays(Device rootRelay, TreeItem item) {
		Set<Device> relays = rootRelay.getRelayChildren();
		if (relays != null && (relays.size() > 0)) {
			for (Device relay : relays) {
				TreeItem subItem = new TreeItem(item, SWT.None);
				subItem.setText(relay.getName());
				listRelays(relay, subItem);
			}
		}
	}

}
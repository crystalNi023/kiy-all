package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.resources.Lang;

public class FOwnerParent extends FZone {
	private Tree treeOwner;
	protected Shell shell;
	private TreeItem ownerItem;
	protected String combValue;

	public FOwnerParent(Shell parent) {
		super(parent);
	}

	public void open(Shell fOwner) {
		createContents(fOwner);
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
	private void createContents(Shell fOwner) {
		shell = new Shell(fOwner, SWT.BORDER | SWT.CLOSE);

		shell.setSize(448, 370);
		shell.setText(Lang.getString("FOwnerParent.ShellTitle.text"));

		treeOwner = new Tree(shell, SWT.BORDER | SWT.CHECK);
		treeOwner.setBounds(16, 10, 406, 286);

		Button btnAll = new Button(shell, SWT.NONE);
		btnAll.addSelectionListener(btnAllSelectionAdapter);
		btnAll.setBounds(254, 304, 80, 27);
		btnAll.setText(Lang.getString("FOwnerParent.ButtonSelectAll.text"));

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(342, 304, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.addSelectionListener(btnAcceptSelectionAdapter);
	}

	private String getChecked(TreeItem ownerItem) {
		String itemData = null;

		if (ownerItem.getChecked()) {
			if (ownerItem.getData() != null) {
				itemData = (String) ownerItem.getData();
			}
		}

		return itemData == null ? "null" : itemData;
	}
	
	private void checkTreeItem(TreeItem[] items) {
		for (TreeItem item : items) {
			item.setChecked(true);
			checkTreeItem(item.getItems());
		}
	}
	
	private SelectionAdapter btnAllSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			checkTreeItem(treeOwner.getItems());
		}
		
	};

	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			ownerItem = treeOwner.getItem(0);
			String value = getChecked(ownerItem);
			combValue = value;
			shell.close();
		}
		
	};
}
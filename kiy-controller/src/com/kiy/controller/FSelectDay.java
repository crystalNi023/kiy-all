package com.kiy.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Types.Day;
import com.kiy.resources.Lang;

public class FSelectDay extends FZone {
	/**
	 * 
	 */
	private Shell shell;
	private Tree treeDay;
	private List<Day> selectDay;
	private List<Day> tag;

	public boolean isEnsure = false;

	public List<Day> getSelectDay() {
		return selectDay;
	}

	public FSelectDay(Shell parent) {
		super(parent);
	}

	public void open(Shell fTask, List<Day> days) {
		tag = days;
		selectDay = new ArrayList<Day>();

		createContents(fTask);
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
	private void createContents(Shell fTask) {
		shell = new Shell(fTask, SWT.BORDER | SWT.CLOSE);

		shell.setSize(295, 381);
		shell.setText(Lang.getString("FSelectDay.ShellTitle.text"));

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent arg0) {
				make();
			}
		});

		treeDay = new Tree(shell, SWT.BORDER | SWT.CHECK);
		treeDay.setBounds(16, 16, 256, 284);

		for (Day day : Day.values()) {
			if (day == Day.NONE) {
				continue;
			}
			TreeItem item = new TreeItem(treeDay, SWT.NONE);
			item.setText(Lang.getString("Day." + day.name()));
			item.setData(day);
		}

		Button btnAll = new Button(shell, SWT.NONE);
		btnAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TreeItem item : treeDay.getItems()) {
					item.setChecked(true);
				}
			}
		});
		btnAll.setBounds(16, 308, 80, 27);
		btnAll.setText(Lang.getString("FSelectDay.ButtonSelectAll.text"));

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(192, 308, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TreeItem treeItem : treeDay.getItems()) {
					if (treeItem.getChecked()) {
						selectDay.add((Day) treeItem.getData());
					}
				}
				isEnsure = true;
				shell.close();
			}
		});
		
		Button btnReverse = new Button(shell, SWT.NONE);
		btnReverse.setBounds(104, 308, 80, 27);
		btnReverse.setText(Lang.getString("UnAllSelect"));
		btnReverse.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TreeItem[] items = treeDay.getItems();
				for (TreeItem item : items) {
					item.setChecked(!item.getChecked());
				}
			}
			
		});
	}

	private void make() {
		if (tag == null) {
			return;
		}

		for (Day day : tag) {
			for (TreeItem treeItem : treeDay.getItems()) {
				if (treeItem.getData() != null && treeItem.getData() instanceof Day) {
					if (day == treeItem.getData()) {
						treeItem.setChecked(true);
					}
				}
			}
		}
	}

}

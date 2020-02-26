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
import com.kiy.common.Types.Month;
import com.kiy.resources.Lang;

public class FSelectMonth extends FZone {
	/**
	 * 
	 */
	private Shell shell;
	private TreeItem item;
	private Tree treeMonth;
	private List<Month> selectMonth;
	private List<Month> tag ;

	public FSelectMonth(Shell parent) {
		super(parent);
	}
	
	public List<Month> getSelectMonth() {
		return selectMonth;
	}

	public void open(Shell fTask,List<Month> t) {
		selectMonth = new ArrayList<>();
		tag = t;
		
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
		shell.setText(Lang.getString("FSelectMonth.ShellTitle.text"));
		
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent arg0) {
				make();
			}
		});

		treeMonth = new Tree(shell, SWT.BORDER | SWT.CHECK);
		treeMonth.setBounds(16, 16, 256, 284);

		for (Month month : Month.values()) {
			if (month == Month.NONE) {
				continue;
			}
			item = new TreeItem(treeMonth, SWT.NONE);
			item.setText(Lang.getString("Month." + month.name()));
			item.setData(month);
		}

		Button btnAll = new Button(shell, SWT.NONE);
		btnAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TreeItem item : treeMonth.getItems()) {
					item.setChecked(true);
				}
			}
		});
		btnAll.setBounds(16, 308, 80, 27);
		btnAll.setText(Lang.getString("FSelectMonth.ButtonSelectAll.text"));

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(192, 308, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				for(TreeItem treeItem : treeMonth.getItems()){
					if(treeItem.getChecked()){
						selectMonth.add((Month) treeItem.getData());
					}
				}
				shell.close();
			}

		});
		
		Button btnReverse = new Button(shell, SWT.NONE);
		btnReverse.setBounds(104, 308, 80, 27);
		btnReverse.setText(Lang.getString("UnAllSelect")); 
		btnReverse.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TreeItem[] items = treeMonth.getItems();
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

		for (Month month : tag) {
			for (TreeItem treeItem : treeMonth.getItems()) {
				if (treeItem.getData() != null && treeItem.getData() instanceof Month) {
					if (month == treeItem.getData()) {
						treeItem.setChecked(true);
					}
				}
			}
		}
	}
}
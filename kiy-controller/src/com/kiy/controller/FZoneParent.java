package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Servo;
import com.kiy.common.Zone;
import com.kiy.resources.Lang;

public class FZoneParent extends FZone {
	protected Shell shell;
	protected String combValue;
	private TreeItem item;
	private TreeItem item2;
	private Label lblNewLabel;
	protected Zone combZoneValue;
	private Zone mZone;

	public FZoneParent(Shell parent) {
		super(parent);
	}

	public void open( Servo servo,Zone zone) {
		mZone = zone;
		
		createContents(servo);
		F.center(getParent(), shell);

		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(34, 27, 61, 17);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(44, 52, 80, 27);

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
	private void createContents( Servo servo) {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);

		shell.setSize(336, 368);
		shell.setText(Lang.getString("FZoneParent.ShellName.text"));

		Tree treeZone = new Tree(shell, SWT.BORDER);
		treeZone.setBounds(16, 16, 299, 273);
		
		if(mZone!=null){
			// 获取所有根区域
			Set<Zone> rootZones = servo.getRootZones();
			for (Zone rootZone : rootZones) {
				if(mZone == rootZone){
					continue;
				}
				item = new TreeItem(treeZone, SWT.NONE);
				item.setText(rootZone.getName());
				item.setData(rootZone);
				// 列出树形结构
				listZones(rootZone, item);
			}
		}else{
			// 获取所有根区域
			Set<Zone> rootZones = servo.getRootZones();
			for (Zone rootZone : rootZones) {
			
				item = new TreeItem(treeZone, SWT.NONE);
				item.setText(rootZone.getName());
				item.setData(rootZone);
				// 列出树形结构
				listZones(rootZone, item);
			}
		}

		// 弹出框点击确认
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(235, 297, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		treeZone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				item2 = (TreeItem) e.item;
				item2.setData(item.getData());
			}
		});
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				combValue = item2.getText();
				combZoneValue = (Zone) item2.getData();
				shell.close();
			}
		});
	}

	// 递归树形父区域
	private void listZones(Zone rootZone, TreeItem item) {
		Set<Zone> zones = rootZone.getZones();
		if (zones != null && (zones.size() > 0)) {
			for (Zone zone : zones) {
				if(mZone!=null){
					if(mZone == zone){
						continue;
					}
				}
				
				TreeItem subItem = new TreeItem(item, SWT.None);
				subItem.setText(zone.getName());
				listZones(zone, subItem);
			}
		}
	}
	
}

package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Zone;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class FZoneDetails extends Dialog {
	private Zone mZone;
	private Shell shell;
	private CLabel zoneName;
	private CLabel zoneRemark;
	private CLabel zoneParent;

	public FZoneDetails(Shell parent, Zone z) {
		super(parent);
		mZone = z;
	}

	public void open() {
		createContents();

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

	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.SYSTEM_MODAL);
		shell.setSize(450, 224);
		shell.setText(Lang.getString("FZone.zone"));

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				make();
			}
		});

		CLabel lblNewLabel_4 = new CLabel(shell, SWT.NONE);
		lblNewLabel_4.setImage(Resource.getImage(FZoneDetails.class, "/com/kiy/resources/area_32.png"));
		lblNewLabel_4.setBounds(16, 16, 32, 32);

		Label lblNewLabel_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_3.setBounds(0, 56, 450, 2);

		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBounds(16, 66, 76, 20);
		lblNewLabel.setText(Lang.getString("FZoneDetauls.ZoneName"));

		zoneName = new CLabel(shell, SWT.NONE);
		zoneName.setBounds(100, 66, 328, 20);

		CLabel lblNewLabel_1 = new CLabel(shell, SWT.NONE);
		lblNewLabel_1.setBounds(16, 94, 76, 20);
		lblNewLabel_1.setText(Lang.getString("FZone.parent"));

		zoneParent = new CLabel(shell, SWT.NONE);
		zoneParent.setBounds(100, 94, 328, 20);

		CLabel lblNewLabel_2 = new CLabel(shell, SWT.NONE);
		lblNewLabel_2.setBounds(16, 122, 76, 20);
		lblNewLabel_2.setText(Lang.getString("FZone.remarks"));

		zoneRemark = new CLabel(shell, SWT.NONE);
		zoneRemark.setBounds(100, 122, 328, 20);

		Label lblNewLabel_5 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_5.setBounds(0, 150, 450, 2);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(348, 160, 80, 27);
		btnNewButton.setText(Lang.getString("FDeviceCurrentStatus.ButtonClose.text"));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}

	private void make() {
		if (mZone != null) {
			zoneName.setText(mZone.getName());
			zoneRemark.setText(mZone.getRemark());
			if (mZone.getParent() != null) {
				zoneParent.setText(mZone.getParent().getName());
			} else {
				zoneParent.setText(Lang.getString("FZone.rootZone"));
			}

		}
	}
}

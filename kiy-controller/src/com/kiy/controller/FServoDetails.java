package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Servo;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class FServoDetails extends Dialog {
	private Servo mServo;
	private Shell shell;
	private Label textIPAddress;
	private Label textPort;
	private Label textUser;

	public FServoDetails(Shell arg0, Servo s) {
		super(arg0);
		mServo = s;
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
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE|SWT.SYSTEM_MODAL);
		shell.setSize(450, 254);
		shell.setText(Lang.getString("Servo.text"));

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				make();
			}
		});

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(Resource.getImage(FServoDetails.class, "/com/kiy/resources/device_server_32.png"));
		lblNewLabel.setBounds(16, 16, 32, 32);

		Label lblNewLabel_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_1.setBounds(0, 56, 444, 10);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setText(Lang.getString("FServo.LabelHostname/IPAddress.text") + ":");
		lblNewLabel_2.setBounds(16, 74, 109, 20);

		textIPAddress = new Label(shell, SWT.NONE);
		textIPAddress.setBounds(143, 74, 285, 20);

		Label label = new Label(shell, SWT.NONE);
		label.setText(Lang.getString("FServo.LabelPort.text") + ":");
		label.setBounds(16, 102, 109, 20);

		textPort = new Label(shell, SWT.NONE);
		textPort.setBounds(143, 102, 285, 20);

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(16, 130, 109, 20);
		lblNewLabel_3.setText(Lang.getString("FServo.GroupAccount.text"));

		textUser = new Label(shell, SWT.NONE);
		textUser.setBounds(143, 130, 285, 20);

		Label lblNewLabel_4 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_4.setBounds(0, 158, 444, 11);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(330, 177, 98, 30);
		btnNewButton.setText(Lang.getString("FDeviceCurrentStatus.ButtonClose.text"));

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}

	private void make() {
		if (mServo != null) {
			textIPAddress.setText(mServo.getIp());
			textPort.setText(String.valueOf(mServo.getPort()));
			if(mServo.getUsername()!=null)
				textUser.setText(mServo.getUsername());
		}
	}
}

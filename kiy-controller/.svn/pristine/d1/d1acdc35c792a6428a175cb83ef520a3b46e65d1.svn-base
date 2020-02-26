/**
 * 2017年1月18日
 */
package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FServo extends Dialog {

	private Servo servo;
	private Shell shell;
	private Text textHost;
	private Spinner textPort;
	private Text textUsername;
	private Text textPassword;
	private Button btnAuto;
	private Group group;
	private Button btnCancel;
	private Button btnAccept;
	private Label labelBottom;
	private CLabel labelText;

	public boolean isUpdate;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FServo(Shell parent) {
		super(parent, SWT.APPLICATION_MODAL);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Servo open(Servo tag) {
		servo = tag;
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
		return servo;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.SYSTEM_MODAL);
		shell.setSize(450, 385);
		shell.setText(Lang.getString("Servo.text"));
		shell.addFocusListener(focusAdapter);

		labelText = new CLabel(shell, SWT.NONE);
		labelText.setBounds(16, 16, 372, 32);
		labelText.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/verify.png"));
		labelText.setVisible(false);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(396, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_server_32.png"));

		labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 64, 450, 2);

		Label labelHost = new Label(shell, SWT.NONE);
		labelHost.setBounds(16, 74, 304, 17);
		labelHost.setText(Lang.getString("FServo.LabelHostname/IPAddress.text"));

		textHost = new Text(shell, SWT.BORDER);
		textHost.setBounds(16, 99, 304, 23);
		textHost.addModifyListener(modifyListener);
		textHost.addFocusListener(focusAdapter);
		textHost.setTextLimit(64);

		Label labelPort = new Label(shell, SWT.NONE);
		labelPort.setBounds(328, 74, 100, 17);
		labelPort.setText(Lang.getString("FServo.LabelPort.text"));

		textPort = new Spinner(shell, SWT.BORDER);
		textPort.setTextLimit(5);
		textPort.setMaximum(65535);
		textPort.setMinimum(1);
		textPort.setSelection(1230);
		textPort.setBounds(328, 99, 100, 23);
		textPort.addFocusListener(focusAdapter);
		textPort.addModifyListener(modifyListener);

		group = new Group(shell, SWT.NONE);
		group.setText(Lang.getString("FServo.GroupAccount.text"));
		group.setBounds(16, 140, 412, 134);

		CLabel labelUsername = new CLabel(group, SWT.NONE);
		labelUsername.setBounds(16, 26, 61, 23);
		labelUsername.setText(Lang.getString("FServo.LableUser.text"));

		textUsername = new Text(group, SWT.BORDER);
		textUsername.setBounds(85, 26, 311, 23);
		textUsername.setTextLimit(32);
		textUsername.addModifyListener(modifyListener);
		textUsername.addFocusListener(focusAdapter);

		CLabel labelPassword = new CLabel(group, SWT.NONE);
		labelPassword.setBounds(16, 57, 61, 23);
		labelPassword.setText(Lang.getString("FServo.LablePassword.text"));

		textPassword = new Text(group, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(85, 57, 311, 23);
		textPassword.setTextLimit(32);
		textPassword.addModifyListener(modifyListener);
		textPassword.addFocusListener(focusAdapter);

		labelKey = new CLabel(group, SWT.NONE);
		labelKey.setText("Key:");
		labelKey.setBounds(16, 88, 61, 23);
		labelKey.setVisible(false);
		labelKey.addFocusListener(focusAdapter);

		textKey = new Text(group, SWT.BORDER);
		textKey.setBounds(85, 88, 311, 23);
		textKey.setVisible(false);
		textKey.setTextLimit(128);
		textKey.addModifyListener(modifyListener);
		textKey.addFocusListener(focusAdapter);

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 307, 450, 2);

		btnAuto = new Button(shell, SWT.CHECK);
		btnAuto.setBounds(16, 282, 98, 17);
		btnAuto.setText(Lang.getString("FServo.ButtonAutoConnect.text"));
		btnAuto.addFocusListener(focusAdapter);

		btnCloud = new Button(shell, SWT.CHECK);
		btnCloud.setText(Lang.getString("FServo.CloudLogin"));
		btnCloud.setBounds(122, 282, 98, 17);
		btnCloud.addFocusListener(focusAdapter);

		btnCloud.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnCloud.getSelection()) {
					labelKey.setVisible(true);
					textKey.setVisible(true);
				} else {
					labelKey.setVisible(false);
					textKey.setVisible(false);
				}
			}

		});

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = true;
				accept();
			}
		});
		btnAccept.setBounds(260, 317, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);

		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancel();
			}
		});

		btnCancel.setBounds(348, 317, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));

		make();
	}

	/**
	 * 回显值
	 */
	private void make() {
		if (servo != null) {
			textHost.setText(servo.getIp());
			textPort.setSelection(servo.getPort());
			btnAuto.setSelection(servo.isAutoConnect());
			textUsername.setText(servo.getUsername());
			textPassword.setText(servo.getPassword());
			btnCloud.setSelection(servo.isCloudLogin());
			textKey.setText(servo.getLoginKey());
			if (btnCloud.getSelection()) {
				labelKey.setVisible(true);
				textKey.setVisible(true);
			} else {
				labelKey.setVisible(false);
				textKey.setVisible(false);
			}
		}
	}

	/**
	 * 验证输入
	 */
	private void verify() {
		btnAccept.setEnabled(false);
		labelText.setVisible(true);

		if (Tool.isEmpty(textHost.getText())) {
			labelText.setText(Lang.getString("FServo.VerifyNoServo"));
			return;
		}

		if (Tool.isEmpty(textUsername.getText())) {
			labelText.setText(Lang.getString("FServo.VerifyNoUser"));
			return;
		}

		if (Tool.isEmpty(textPassword.getText())) {
			labelText.setText(Lang.getString("FServo.VerifNoPassword"));
			return;
		}

		labelText.setText(Tool.EMPTY);
		labelText.setVisible(false);
		btnAccept.setEnabled(true);
	}

	/**
	 * 确认
	 */
	private void accept() {
		if (servo == null) {
			servo = new Servo();
		}
		servo.setIp(textHost.getText());

		servo.setPort(textPort.getSelection());
		servo.setAutoConnect(btnAuto.getSelection());
		servo.setUsername(textUsername.getText());
		servo.setPassword(Tool.MD5(textPassword.getText()));
		servo.setLoginKey(textKey.getText());
		servo.setCloudLogin(btnCloud.getSelection());

		shell.close();
	}

	ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	FocusAdapter focusAdapter = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent arg0) {
			verify();
		}
	};
	private Text textKey;
	private CLabel labelKey;
	private Button btnCloud;

	/**
	 * 取消
	 */
	private void cancel() {
		servo = null;
		shell.close();
	}
}
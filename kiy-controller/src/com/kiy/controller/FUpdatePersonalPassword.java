package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Tool;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * 更改用户个人密码
 * 
 * @author HLX 2017.6.15
 *
 */
/*
 * 
 * TODO 1.输入原始密码 2.输入新密码 3.再一次输入新密码
 */
public class FUpdatePersonalPassword extends Dialog {
	private Shell shell;
	public boolean isUpdate = false;
	private Text newPassword;
	private Text ensurePassword;
	private Text oldPassword;
	private CLabel labelVerify;
	private Button btnAccept;
	private String password;
	private String userOldPassword;

	public FUpdatePersonalPassword(Shell arg0) {
		super(arg0);
	}

	public String open() {

		createContent();
		F.center(getParent(), shell);

		shell.layout();
		shell.open();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return password;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.SYSTEM_MODAL | SWT.DIALOG_TRIM);
		shell.setSize(370, 235);
		shell.setText(Lang.getString("FUpdatePersonalPassword.shellName.text"));

		labelVerify = new CLabel(shell, SWT.NONE);
		labelVerify.setVisible(false);
		labelVerify.setImage(Resource.getImage(FModifyPassword.class, "/com/kiy/resources/verify.png"));
		labelVerify.setBounds(16, 16, 297, 32);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(319, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/user_create_32.png"));

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 64, 370, 2);

		CLabel lblOldPassword = new CLabel(shell, SWT.NONE);
		lblOldPassword.setBounds(16, 74, 79, 23);
		lblOldPassword.setText(Lang.getString("FUpdatePersonalPassword.oldPassword.text"));

		oldPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		oldPassword.setBounds(101, 74, 250, 23);
		oldPassword.setTextLimit(32);
		oldPassword.addModifyListener(listener);

		CLabel lblnewPassword = new CLabel(shell, SWT.NONE);
		lblnewPassword.setBounds(16, 105, 79, 23);
		lblnewPassword.setText(Lang.getString("FModifyPassword.LabelNewPassword.text"));

		newPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		newPassword.setBounds(101, 105, 250, 23);
		newPassword.setTextLimit(32);
		newPassword.addModifyListener(listener);

		CLabel lblensurePassword = new CLabel(shell, SWT.NONE);
		lblensurePassword.setBounds(16, 136, 79, 23);
		lblensurePassword.setText(Lang.getString("FModifyPassword.LabelEnsurePassword.text"));

		ensurePassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		ensurePassword.setBounds(101, 136, 250, 23);
		ensurePassword.addModifyListener(listener);
		ensurePassword.setTextLimit(32);

		Label labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 167, 370, 2);

		// 取消
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(271, 177, 80, 23);
		btnCancel.setText(Lang.getString("Cancel.text"));
		// 确定

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(183, 177, 80, 23);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);

		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = true;
				accept();
				shell.close();
			}
		});
	}

	private void accept() {
		password = newPassword.getText();
		userOldPassword = oldPassword.getText();
	}

	private ModifyListener listener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	private void verify() {
		btnAccept.setEnabled(false);
		if (Tool.isEmpty(oldPassword.getText())) {
			labelVerify.setVisible(true);
			labelVerify.setText(Lang.getString("FModifyPassword.EnterOldPassword"));
			return;
		}

		if (Tool.isEmpty(newPassword.getText())) {
			labelVerify.setVisible(true);
			labelVerify.setText(Lang.getString("FModifyPassword.EnterNewPassword"));
			return;
		}

		if (Tool.isEmpty(ensurePassword.getText())) {
			labelVerify.setVisible(true);
			labelVerify.setText(Lang.getString("FModifyPassword.EnterNewPasswordAgain"));
			return;
		}

		if (!newPassword.getText().equals(ensurePassword.getText())) {
			labelVerify.setVisible(true);
			labelVerify.setText(Lang.getString("FModifyPassword.Wrong"));
			return;
		}

		labelVerify.setText(Tool.EMPTY);
		labelVerify.setVisible(false);
		btnAccept.setEnabled(true);
	}

	public String getUserOldPassword() {
		return userOldPassword;
	}

}

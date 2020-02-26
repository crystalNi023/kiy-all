package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Tool;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.custom.CLabel;

public class FModifyPassword extends Dialog {
	private Shell shell;
	public boolean isUpdate = false;
	private Text newPassword;
	private Text ensurePassword;
	private String password;
	private Button btnAccept;
	private CLabel labelVerify;

	public FModifyPassword(Shell parent) {
		super(parent);
	}

	public String open(String userName) {
		createContent();
		shell.setText(Lang.getString("FModifyPassword.ShellName.text") + userName);
		
		F.center(getParent(), shell);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return password;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setSize(370, 207);

		labelVerify = new CLabel(shell, SWT.NONE);
		labelVerify.setVisible(false);
		labelVerify.setImage(Resource.getImage(FModifyPassword.class, "/com/kiy/resources/verify.png"));
		labelVerify.setBounds(16, 16, 278, 32);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(302, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/user_create_32.png"));

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 64, 364, 2);

		CLabel lblnewPassword = new CLabel(shell, SWT.NONE);
		lblnewPassword.setBounds(16, 74, 79, 23);
		lblnewPassword.setText(Lang.getString("FModifyPassword.LabelNewPassword.text"));

		newPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		newPassword.setBounds(101, 74, 250, 23);
		newPassword.setTextLimit(30);
		newPassword.addModifyListener(listener);

		CLabel lblensurePassword = new CLabel(shell, SWT.NONE);
		lblensurePassword.setBounds(16, 105, 79, 23);
		lblensurePassword.setText(Lang.getString("FModifyPassword.LabelEnsurePassword.text"));

		ensurePassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		ensurePassword.setTextLimit(30);
		ensurePassword.setBounds(101, 105, 250, 23);
		ensurePassword.addModifyListener(listener);

		Label labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 136, 364, 2);

		// 取消
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setBounds(271, 146, 80, 23);
		btnCancel.setText(Lang.getString("Cancel.text"));
		// 确定

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(183, 146, 80, 23);
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
	}

	private ModifyListener listener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	private void verify() {
		btnAccept.setEnabled(false);
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
}

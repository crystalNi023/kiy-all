/**
 * 2017年1月19日
 */
package com.kiy.controller;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.resources.Lang;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FLogin extends Dialog {

	private Servo servo;
	private Shell shell;
	private Label lblText;
	private Text textUsername;
	private Text textPassword;
	private Button btnAutoConnect;
	private boolean closing;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FLogin(Shell parent) {
		super(parent, 0);
		closing = false;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Servo open(Servo s) {
		if (s == null)
			throw new NullPointerException();

		servo = s;
		createContents();
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
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.addShellListener(shellAdapter);
		shell.setSize(460, 218);
		shell.setText(Lang.getString("FLogin.ShellTitle.text"));

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(btnCancelSelectionAdapter);
		btnCancel.setBounds(358, 142, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));

		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.addSelectionListener(btnAcceptSelectionAdapter);
		btnAccept.setBounds(268, 142, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));

		lblText = new Label(shell, SWT.NONE);
		lblText.setBounds(16, 16, 422, 17);
		lblText.setText("...");

		textUsername = new Text(shell, SWT.BORDER);
		textUsername.setBounds(85, 41, 353, 23);

		textPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(85, 72, 353, 23);

		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setAlignment(SWT.RIGHT);
		lblUsername.setBounds(16, 44, 61, 17);
		lblUsername.setText(Lang.getString("FLogin.LableUser.text"));

		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setAlignment(SWT.RIGHT);
		lblPassword.setBounds(16, 75, 61, 17);
		lblPassword.setText(Lang.getString("FLogin.LablePassword.text"));

		btnAutoConnect = new Button(shell, SWT.CHECK);
		btnAutoConnect.setBounds(85, 103, 343, 17);
		btnAutoConnect.setText(Lang.getString("FLogin.ButtonRemenberUserAndPassword.text"));

		F.center(getParent(), shell);
	}

	private void make() {
		lblText.setText(servo.toString());
		if (servo.getUsername() != null)
			textUsername.setText(servo.getUsername());
		btnAutoConnect.setSelection(servo.isAutoConnect());
	}

	private void cancel() {
		servo = null;
		close();
	}

	private void accept() {
		servo.setUsername(textUsername.getText());
		servo.setPassword(Tool.MD5(textPassword.getText()));
		servo.setAutoConnect(btnAutoConnect.getSelection());
		close();
	}

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}
	
	/**
	 * ShellAdapter
	 */
	private ShellAdapter shellAdapter = new ShellAdapter() {
		@Override
		public void shellActivated(ShellEvent e) {
			make();
		}

		@Override
		public void shellClosed(ShellEvent e) {
			closing = true;
			close();
		}
	};
	
	/**
	 * Cancel Button SelectionAdapter
	 */
	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			cancel();
		}
	};
	
	/**
	 * Accept Button SelectionAdapter
	 */
	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			accept();
		}
	};
}
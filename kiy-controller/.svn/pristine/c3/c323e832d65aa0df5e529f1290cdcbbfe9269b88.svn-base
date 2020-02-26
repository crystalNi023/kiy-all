package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

/**
 * 修改用户个人资料，无法更改角色，区域等敏感信息
 * 
 * @author HLX
 *
 */
public class FUpdatePersonalInfo extends Dialog {
	private User tag;
	private Shell shell;
	private Label labelVerify;
	private Text textName;
	private Text textRealname;
	private Text textPhone;
	private Text textMobile;
	private Text textEmail;
	private Text textRemark;
	private Button btnAccpet;
	private Servo servo;

	public boolean isUpdate = false;

	public FUpdatePersonalInfo(Shell arg0) {
		super(arg0);
	}

	public User open(User u, Servo s) {
		tag = u;
		servo = s;

		createContent();

		shell.open();
		shell.layout();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return tag;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.SYSTEM_MODAL | SWT.DIALOG_TRIM);
		shell.setText(Lang.getString("FUpdatePersonlInfo.Title.text"));
		shell.setSize(473, 333);

		F.center(getParent(), shell);
		labelVerify = new Label(shell, SWT.NONE);
		labelVerify.setBounds(16, 16, 397, 32);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(419, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/user_create_32.png"));

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 64, 473, 2);

		// 名称
		CLabel lblName = new CLabel(shell, SWT.NONE);
		lblName.setBounds(16, 74, 60, 23);
		lblName.setText(Lang.getString("FUser.LabelUserName.text"));

		textName = new Text(shell, SWT.BORDER);
		textName.setBounds(101, 74, 350, 23);
		textName.setTextLimit(32);
		textName.addModifyListener(listener);

		// 真实姓名
		CLabel lblRealname = new CLabel(shell, SWT.NONE);
		lblRealname.setBounds(16, 105, 60, 23);
		lblRealname.setText(Lang.getString("FUser.LabelRealName.text"));

		textRealname = new Text(shell, SWT.BORDER);
		textRealname.setBounds(101, 105, 350, 23);
		textRealname.setTextLimit(32);
		textRealname.addModifyListener(listener);
		textRealname.addFocusListener(listener2);

		// 手机
		CLabel lblMobile = new CLabel(shell, SWT.NONE);
		lblMobile.setBounds(16, 136, 71, 23);
		lblMobile.setText(Lang.getString("FUser.LabelMobile.text"));

		textMobile = new Text(shell, SWT.BORDER);
		textMobile.setBounds(101, 136, 350, 23);
		textMobile.setTextLimit(32);
		textMobile.addModifyListener(listener);
		textMobile.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent arg0) {
				F.intNumberCheck(arg0, textMobile);
			}
		});

		// 固定电话
		CLabel lblPhone = new CLabel(shell, SWT.NONE);
		lblPhone.setBounds(16, 167, 71, 23);
		lblPhone.setText(Lang.getString("FUser.LabelPhone.text"));

		textPhone = new Text(shell, SWT.BORDER);
		textPhone.setBounds(101, 167, 350, 23);
		textPhone.setTextLimit(32);
		textPhone.addModifyListener(listener);
		textPhone.addFocusListener(listener2);

		// 邮件
		CLabel lblEmail = new CLabel(shell, SWT.NONE);
		lblEmail.setBounds(16, 198, 60, 23);
		lblEmail.setText(Lang.getString("FUser.LabelEmail.text"));

		textEmail = new Text(shell, SWT.BORDER);
		textEmail.setBounds(101, 198, 350, 23);
		textEmail.setTextLimit(64);
		textEmail.addModifyListener(listener);
		textEmail.addFocusListener(listener2);

		// 备注
		CLabel lblRemark = new CLabel(shell, SWT.NONE);
		lblRemark.setBounds(16, 229, 60, 23);
		lblRemark.setText(Lang.getString("FUser.LabelRemark.text"));

		textRemark = new Text(shell, SWT.BORDER);
		textRemark.setBounds(101, 229, 350, 23);
		textRemark.setTextLimit(128);
		textRemark.addModifyListener(listener);
		textRemark.addFocusListener(listener2);

		Label labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 260, 473, 2);

		shell.addShellListener(new ShellAdapter() {

			@Override
			public void shellActivated(ShellEvent arg0) {
				make();
			}

		});

		btnAccpet = new Button(shell, SWT.NONE);
		btnAccpet.setText(Lang.getString("Ensure.text"));
		btnAccpet.setEnabled(false);
		btnAccpet.setBounds(278, 270, 80, 27);
		btnAccpet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				isUpdate = true;
				accpet();
				shell.close();
			}

		});

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setText(Lang.getString("Cancel.text"));
		btnCancel.setBounds(371, 270, 80, 27);
		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				isUpdate = false;
				shell.close();
			}

		});

	}

	private void make() {
		if (tag == null) {
			return;
		}

		if (tag.getName() != null) {
			textName.setText(tag.getName());
			textName.setSelection(tag.getName().length());
		}

		if (tag.getRealname() != null)
			textRealname.setText(tag.getRealname());
		if (tag.getMobile() != null)
			textMobile.setText(tag.getMobile());
		if (tag.getPhone() != null)
			textPhone.setText(tag.getPhone());
		if (tag.getEmail() != null)
			textEmail.setText(tag.getEmail());
		if (tag.getRemark() != null)
			textRemark.setText(tag.getRemark());
	}

	private void accpet() {
		tag.setName(textName.getText());
		tag.setRealname(textRealname.getText());
		tag.setMobile(textMobile.getText());
		tag.setPhone(textPhone.getText());
		tag.setEmail(textEmail.getText());
		tag.setRemark(textRemark.getText());
	}

	private void verify() {

		btnAccpet.setEnabled(false);
		if (Tool.isEmpty(textName.getText())) {
			labelVerify.setVisible(true);
			labelVerify.setText(Lang.getString("FUser.VerifyTextNameIsNull.text"));
			return;
		}
		String name = textName.getText();
		Set<User> users = servo.getUsers();
		for (User user : users) {
			if (name.equals(user.getName()) && !name.equals(tag.getName())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FUser.VerifyTextNameIsExist.text"));
				return;
			}
		}
		labelVerify.setText(Tool.EMPTY);
		labelVerify.setVisible(false);
		btnAccpet.setEnabled(true);

	}

	private ModifyListener listener = new ModifyListener() {
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	private FocusListener listener2 = new FocusListener() {
		public void focusLost(FocusEvent arg0) {

		}

		public void focusGained(FocusEvent arg0) {
			verify();
		}
	};
}

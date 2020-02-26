package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Role;
import com.kiy.common.User;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;

public class FUserDetails extends Dialog {

	private User tag;
	private Shell shell;
	private Label textName;
	private Label textStatus;
	private List listRole;
	private Label textRealname;
	private Label textMobile;
	private Label textPhone;
	private Label lblEmail;
	private Label lblRemark;
	private Label textZone;
	private Label textNickname;

	public FUserDetails(Shell arg0, User u) {
		super(arg0);
		tag = u;
	}

	public void open() {
		createContents();

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
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.setSize(479, 530);
		shell.setText(Lang.getString("FUser.ShellName.text"));

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				make();
			}
		});
		F.center(getParent(), shell);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(Resource.getImage(FUserDetails.class, "/com/kiy/resources/user_create_32.png"));
		lblNewLabel.setBounds(16, 16, 32, 32);

		Label lblNewLabel_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_1.setBounds(0, 56, 479, 2);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setText(Lang.getString("FUseretails.UserName"));
		lblNewLabel_2.setBounds(16, 66, 76, 20);

		textName = new Label(shell, SWT.NONE);
		textName.setBounds(100, 66, 357, 20);

		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(16, 94, 76, 20);
		lblNewLabel_3.setText(Lang.getString("FUpdateUser.LabelUserStatus.text"));

		textStatus = new Label(shell, SWT.NONE);
		textStatus.setBounds(100, 94, 357, 20);

		Label label = new Label(shell, SWT.NONE);
		label.setBounds(16, 122, 76, 20);
		label.setText(Lang.getString("FUserDetail.Role"));

		listRole = new List(shell, SWT.BORDER);
		listRole.setBounds(100, 122, 357, 133);

		textZone = new Label(shell, SWT.NONE);
		textZone.setBounds(100, 263, 357, 20);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(16, 263, 76, 20);
		label_1.setText(Lang.getString("FUser.LabelZone.text"));

		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBounds(16, 291, 76, 20);
		lblNewLabel_4.setText(Lang.getString("FUser.LabelRealName.text"));

		textRealname = new Label(shell, SWT.NONE);
		textRealname.setBounds(100, 291, 357, 20);
		
		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setText(Lang.getString("FUserDetails.label_7.text")); //$NON-NLS-1$
		label_7.setBounds(16, 319, 76, 20);
		
		textNickname = new Label(shell, SWT.NONE);
		textNickname.setBounds(100, 319, 357, 20);

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setBounds(16, 347, 76, 20);
		label_2.setText(Lang.getString("FUser.LabelMobile.text"));

		textMobile = new Label(shell, SWT.NONE);
		textMobile.setBounds(100, 347, 357, 20);

		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setBounds(16, 375, 76, 20);
		label_3.setText(Lang.getString("FUser.LabelPhone.text"));

		textPhone = new Label(shell, SWT.NONE);
		textPhone.setBounds(100, 375, 357, 20);

		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setBounds(16, 403, 76, 20);
		label_4.setText(Lang.getString("FUser.LabelEmail.text"));

		lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setBounds(100, 403, 357, 20);

		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setBounds(16, 431, 76, 20);
		label_5.setText(Lang.getString("FUser.LabelRemark.text"));

		lblRemark = new Label(shell, SWT.NONE);
		lblRemark.setBounds(100, 431, 357, 20);

		Label label_6 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_6.setBounds(0, 459, 479, 2);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(377, 469, 80, 27);
		btnNewButton.setText(Lang.getString("FDeviceCurrentStatus.ButtonClose.text"));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
	}

	private void make() {
		if (tag != null) {
			textName.setText(tag.getName());
			textStatus.setText(tag.getEnable() ? Lang.getString("FUpdateUser.RadioButtonAbel.text") : Lang.getString("FUpdateUser.RadioButtonUnAbel.text"));

			Set<Role> roles = tag.getRoles();
			for (Role role : roles) {
				listRole.add(role.getName());
			}
			textZone.setText(tag.getZone() == null ? "" : tag.getZone().getName());
			if (tag.getRealname() != null)
				textRealname.setText(tag.getRealname());
			if (tag.getNickname() != null)
				textNickname.setText(tag.getNickname());
			if (tag.getMobile() != null)
				textMobile.setText(tag.getMobile());
			if (tag.getPhone() != null)
				textPhone.setText(tag.getPhone());
			if (tag.getEmail() != null)
				lblEmail.setText(tag.getEmail());
			if (tag.getRemark() != null)
				lblRemark.setText(tag.getRemark());
		}
	}

}

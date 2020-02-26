package com.kiy.view;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.User;
import com.kiy.data.DataOperation;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 修改密码
 * @author HLX Tel:18996470535 
 * @date 2018年4月3日 
 * Copyright:Copyright(c) 2018
 */
public class FModifyPassword extends FDialog{
	private Text password;
	private Text ensurePassword;

	private User user;

	public User open(User u) {
		this.user = u;
		show();
		return user;
	}
	
	
	public FModifyPassword(Shell p) {
		super(p);
		labelRemark.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		body.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	}

	@Override
	protected void initialize() {
		setText("修改密码");
		setSize(380,240);

		CLabel label = new CLabel(body, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBounds(10, 10, 82, 23);
		label.setText("新密码:");
		
		CLabel label_1 = new CLabel(body, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("确认新密码:");
		label_1.setBounds(10, 39, 82, 23);
		
		password = new Text(body, SWT.BORDER | SWT.PASSWORD);
		password.setBounds(98, 10, 250, 23);
		
		ensurePassword = new Text(body, SWT.BORDER | SWT.PASSWORD);
		ensurePassword.setBounds(98, 39, 250, 23);
	}

	@Override
	protected void make() {
		
	}

	@Override
	protected void verify() {
		if (F.isEmpty(password.getText())) {
			labelMessage.setText("新密码不能为空");
			return;
		}
		if (!password.getText().equals(ensurePassword.getText())) {
			labelMessage.setText("两次输入密码不一致");
			return;
		}
		labelMessage.setText(F.EMPTY);
	}

	@Override
	protected void cancel() {
		user = null;
	}

	@Override
	protected void accept() {
		user.setPassword(F.MD5(password.getText()));
		boolean setPasswordForUser = DataOperation.setPasswordForUser(user);
		if(setPasswordForUser){
			FPrompt.showSuccess(getParent(), "修改密码成功");
		}else{
			FPrompt.showInformation(getParent(), "修改密码失败");
		}
	}

}

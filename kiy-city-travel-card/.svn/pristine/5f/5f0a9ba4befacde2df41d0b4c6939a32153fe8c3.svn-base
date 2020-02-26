package com.kiy.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Types.Power;
import com.kiy.common.User;
import com.kiy.data.DataOperation;
import org.eclipse.wb.swt.SWTResourceManager;
/**
 * 更新用户
 * @author HLX Tel:18996470535 
 * @date 2018年4月3日 
 * Copyright:Copyright(c) 2018
 */
public class FUpdateUser extends FDialog{
	private User user;
	
	private Text username;
	private Text realname;
	private Text phone;
	private Text remark;
	private CCombo power;
	private Button btnRadioNormal;
	private Button buttonRadioEnable;

	public User open(User u) {
		this.user = u;
		show();
		return user;
	}
	
	public FUpdateUser(Shell p) {
		super(p);
		labelRemark.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		body.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	}

	@Override
	protected void initialize() {
		setSize(380, 360);
		setText("更新用户");
		
		CLabel lblNewLabel = new CLabel(body, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(10, 10, 67, 23);
		lblNewLabel.setText("用户名:");
		
		username = new Text(body, SWT.BORDER);
		username.setBounds(83, 10, 256, 23);
		username.setTextLimit(10);
		
		CLabel label_2 = new CLabel(body, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_2.setText("权限:");
		label_2.setBounds(10, 39, 67, 23);
		
		power = new CCombo(body, SWT.BORDER);
		power.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		power.setEditable(false);
		power.setBounds(83, 39, 256, 21);
		Power[] values = Power.values();
		for (Power p : values) {
			power.add(p.name());
			power.setData(p.name(), p);
		}
		power.select(0);
		
		CLabel label_3 = new CLabel(body, SWT.NONE);
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_3.setText("真实姓名:");
		label_3.setBounds(10, 126, 67, 23);
		
		realname = new Text(body, SWT.BORDER);
		realname.setBounds(83, 126, 256, 23);
		realname.setTextLimit(10);
		
		CLabel label_4 = new CLabel(body, SWT.NONE);
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_4.setText("联系方式:");
		label_4.setBounds(10, 97, 67, 23);
		
		phone = new Text(body, SWT.BORDER);
		phone.setBounds(83, 97, 256, 23);
		phone.setTextLimit(11);
		
		CLabel label_5 = new CLabel(body, SWT.NONE);
		label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_5.setText("备注:");
		label_5.setBounds(10, 155, 67, 23);
		
		remark = new Text(body, SWT.BORDER);
		remark.setBounds(83, 155, 256, 23);
		
		CLabel label_6 = new CLabel(body, SWT.NONE);
		label_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_6.setText("状态:");
		label_6.setBounds(10, 68, 67, 23);
		
		btnRadioNormal = new Button(body, SWT.RADIO);
		btnRadioNormal.setBounds(83, 68, 97, 23);
		btnRadioNormal.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btnRadioNormal.setText("正常");
		btnRadioNormal.setSelection(true);
		
		buttonRadioEnable = new Button(body, SWT.RADIO);
		buttonRadioEnable.setText("禁用");
		buttonRadioEnable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		buttonRadioEnable.setBounds(186, 68, 97, 23);
		btnRadioNormal.setSelection(false);
	}

	@Override
	protected void make() {
		username.setText(user.getUsername());
		power.setText(F.getString(user.getPower().name()));
		phone.setText(F.getString(user.getPhone()));
		realname.setText(F.getString(user.getRealname()));
		remark.setText(F.getString(user.getRemark()));
		btnRadioNormal.setSelection(!user.isEnable());
	}

	@Override
	protected void verify() {
		if (F.isEmpty(username.getText())) {
			labelMessage.setText("用户名不能为空");
			return;
		}
		if (F.isEmpty(realname.getText())) {
			labelMessage.setText("真实姓名不能为空");
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
		user.setUsername(username.getText());
		user.setPower((Power) power.getData(power.getText()));
		user.setRealname(realname.getText());
		user.setPhone(phone.getText());
		user.setRemark(remark.getText());
		user.setEnable(!btnRadioNormal.getSelection());
		
		boolean createUser = DataOperation.UpdateUser(user);
		if(createUser){
			FPrompt.showSuccess(getParent(), "更新用户成功");
		}else{
			FPrompt.showInformation(getParent(), "更新用户失败");
		}
		
	}

	
}

package com.kiy.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.User;
import com.kiy.data.DataOperation;

import org.eclipse.wb.swt.SWTResourceManager;

public class FCreateGetCard extends FDialog {

	private int counts = 110;
	private Text beginNum;
	private Text endNum;
	private Text count;
	private Text remark;

	private User giveUser;
	private User getUser;

	public FCreateGetCard(Shell p) {
		super(p);
		labelRemark.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		body.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelImage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelMessage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
	}

	public User open(User u1, User u2) {
		this.giveUser = u1;
		this.getUser = u2;
		show();
		return getUser;
	}

	@Override
	protected void initialize() {
		setSize(450, 300);
		setText("新增领卡记录");

		CLabel lblNewLabel = new CLabel(body, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(16, 0, 67, 23);
		lblNewLabel.setText("开始号段:");

		CLabel label = new CLabel(body, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setText("结束号段:");
		label.setBounds(16, 31, 67, 23);

		CLabel label_1 = new CLabel(body, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("数量:");
		label_1.setBounds(16, 62, 67, 23);

		CLabel label_2 = new CLabel(body, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_2.setText("备注:");
		label_2.setBounds(16, 89, 67, 23);

		beginNum = new Text(body, SWT.BORDER);
		beginNum.setBounds(104, 0, 256, 23);
		beginNum.setTextLimit(8);
		beginNum.addVerifyListener(new VerifyListenerOne());

		endNum = new Text(body, SWT.BORDER);
		endNum.setBounds(104, 31, 256, 23);
		endNum.setTextLimit(8);
		endNum.addModifyListener(new ModifyListenerOne());
		endNum.addVerifyListener(new VerifyListenerOne());

		count = new Text(body, SWT.BORDER);
		count.setBounds(104, 62, 256, 23);
		count.setEditable(false);

		remark = new Text(body, SWT.BORDER);
		remark.setBounds(104, 89, 256, 23);
	}

	@Override
	protected void make() {

	}

	@Override
	protected void verify() {
		if (F.isEmpty(beginNum.getText())) {
			labelMessage.setText("开始号段不能为空");
			return;
		}
		if (F.isEmpty(endNum.getText())) {
			labelMessage.setText("结束号段不能为空");
			return;
		}
		if(counts<0) {
			labelMessage.setText("结束号段不能小于开始号段");
			return;
		}
		labelMessage.setText(F.EMPTY);
	}

	@Override
	protected void cancel() {

	}

	@Override
	protected void accept() {
		/**
		 * 新增领卡记录
		 */
		counts = Integer.parseInt(endNum.getText()) - Integer.parseInt(beginNum.getText());
		boolean createGetCard = DataOperation.createGetCardRecord(giveUser.getId(), getUser.getId(), Integer.parseInt(beginNum.getText()), Integer.parseInt(endNum.getText()), remark.getText());
		if (createGetCard) {
			FPrompt.showSuccess(getParent(), "领卡成功");
		} else {
			FPrompt.showInformation(getParent(), "领卡失败");
		}
	}
	
	private class ModifyListenerOne implements ModifyListener{

		@Override
		public void modifyText(ModifyEvent arg0) {
			counts = Integer.parseInt(endNum.getText()) - Integer.parseInt(beginNum.getText());
			count.setText(Integer.toString(counts));
		}
		
	}

	private class VerifyListenerOne implements VerifyListener{

		@Override
		public void verifyText(VerifyEvent e) {
			boolean b = ("0123456789".indexOf(e.text)>=0);
				e.doit = b;
		}
	}
	
}

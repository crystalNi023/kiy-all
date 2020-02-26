package com.kiy.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Units.MLog;
import com.kiy.resources.Lang;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class FUserLogDetails extends Dialog {
	
	private Servo mServo;
	private MLog mLog;
	private Shell shell;
	private Label textCreateTime;
	private Label textUserName;
	private Label textStatus;
	private Label textCommand;
	private Group groupParameter;
	private Group groupRemark;
	private Label lblRemark;
	private Label textTime;
	private Text textParameter;
	private Button btnCancel;

	public FUserLogDetails(Shell parent) {
		super(parent);
	}

	public void open(Servo servo, MLog log) {
		mServo = servo;
		mLog = log;

		createContent();
		F.center(getParent(), shell);

		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(406, 489, 80, 27);
		btnCancel.setText(Lang.getString("FUserLogDetails.btnNewButton.text")); //$NON-NLS-1$
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void createContent() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				make();
			}
		});
		shell.setSize(500, 551);
		shell.setText(Lang.getString("FUserLogDetails.ShellName.text"));

		int baseY = 26;
		if(FMain.THIS_IS_MAC){
			baseY = 16;
		}else{
			baseY = 26;
		}
		
		Group group = new Group(shell, SWT.NONE);
		group.setBounds(16, 16, 470, 181);
		group.setText(Lang.getString("FUserLogDetails.groupDetails.text"));
		
		{
			// 创建时间
			Label lblCreateTime = new Label(group, SWT.NONE);
			lblCreateTime.setBounds(16, baseY, 69, 23);
			lblCreateTime.setText(Lang.getString("FUserLogDetails.LableCreateTime.text"));

			textCreateTime = new Label(group, SWT.NONE);
			textCreateTime.setBounds(93, baseY, 351, 23);

			// 用户名

			Label lblUserName = new Label(group, SWT.NONE);
			lblUserName.setBounds(16, baseY+(F.MARGIN+F.LABEL_HEIGHT), 60, 23);
			lblUserName.setText(Lang.getString("FUserLogDetails.LableUserName.text"));

			textUserName = new Label(group, SWT.NONE);
			textUserName.setBounds(93, baseY+(F.MARGIN+F.LABEL_HEIGHT), 351, 23);

			// 指令
			Label lblCommand = new Label(group, SWT.NONE);
			lblCommand.setBounds(16, baseY+2*(F.MARGIN+F.LABEL_HEIGHT), 60, 23);
			lblCommand.setText(Lang.getString("FUserLogDetails.LableCommand.text"));

			textCommand = new Label(group, SWT.NONE);
			textCommand.setBounds(93, baseY+2*(F.MARGIN+F.LABEL_HEIGHT), 351, 23);

			// 状态
			Label lblStatus = new Label(group, SWT.NONE);
			lblStatus.setText(Lang.getString("FUserLogDetails.LableStatus.text"));
			lblStatus.setBounds(16, baseY+3*(F.MARGIN+F.LABEL_HEIGHT), 60, 23);

			textStatus = new Label(group, SWT.NONE);
			textStatus.setBounds(93, baseY+3*(F.MARGIN+F.LABEL_HEIGHT), 351, 23);

			// 耗时
			Label lblTime = new Label(group, SWT.NONE);
			lblTime.setBounds(15, baseY+4*(F.MARGIN+F.LABEL_HEIGHT), 60, 23);
			lblTime.setText(Lang.getString("FUserLogDetails.LableTime.text"));

			textTime = new Label(group, SWT.NONE);
			textTime.setBounds(93, baseY+4*(F.MARGIN+F.LABEL_HEIGHT), 351, 23);
		}

		groupParameter = new Group(shell, SWT.NONE);
		groupParameter.setBounds(16, 216, 470, 173);
		groupParameter.setText(Lang
				.getString("FUserLogDetails.groupParameter.text"));

		textParameter = new Text(groupParameter, SWT.BORDER | SWT.READ_ONLY
				| SWT.V_SCROLL);
		textParameter.setBounds(16, baseY, 438, 141);

		groupRemark = new Group(shell, SWT.NONE);
		groupRemark.setBounds(16, 397, 470, 84);
		groupRemark.setText(Lang.getString("FUserLogDetails.groupRemark.text"));

		lblRemark = new Label(groupRemark, SWT.NONE);
		lblRemark.setBounds(16, baseY, 438, 42);

	}

	private void make() {
		if (mLog != null) {
			// 创建时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create = sdf.format(new Date(mLog.getCreated()));
			String createTime = String.valueOf(create);
			textCreateTime.setText(String.valueOf(createTime));
			// 用户名

			if (mLog.getUserId() != null) {

				User user = mServo.getUser(mLog.getUserId());
				if (user != null) {
					String userName = String.valueOf(user.getName());
					textUserName.setText(userName);
				} else {
					textUserName
							.setText(Lang
									.getString("FUserLogDetails.textUserNameNoUser.text"));
				}
			} else {

			}
			// 指令
			ActionCase forNumber = ActionCase.forNumber(mLog.getCommand());
			String command = String.valueOf(forNumber.name());
			textCommand.setText(Lang.getString("Command." + command));

			// 状态
			int status2 = mLog.getResult();
			String status = String.valueOf(Result.forNumber(status2).name());
			textStatus.setText(Lang.getString("Status." + status));

			// 参数
			String parameter = mLog.getParameter();
			textParameter.setText(parameter);
			textParameter.setSelection(parameter.length() + 3);

			// 备注
			if (!Tool.isEmpty(mLog.getRemark())) {
				lblRemark.setText(mLog.getRemark());
			}

			int time = mLog.getTime();
			textTime.setText(String.valueOf(time + "ms"));
		}
	}
}

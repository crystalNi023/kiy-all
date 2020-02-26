package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Role;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Button;

public class FRoleDetails extends Dialog {
	private Role tag;
	private Shell shell;
	private Label textName;
	private Label textRemark;

	private TreeItem treeDefaultCommand;
	private TreeItem treeDeviceCommand;
	private TreeItem treeQueryCommand;
	private TreeItem treeOperation;
	private TreeItem treeService;
	private Tree treeCommand;

	public FRoleDetails(Shell arg0, Role r) {
		super(arg0);
		tag = r;
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
		shell.setSize(450, 390);
		shell.setText(Lang.getString("FRole.ShellTitle.text"));

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				make();
			}
		});

		F.center(getParent(), shell);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/role_create_32.png"));
		lblNewLabel.setBounds(16, 16, 32, 32);

		Label lblNewLabel_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_1.setBounds(0, 56, 444, 10);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(16, 74, 54, 20);
		lblNewLabel_2.setText(Lang.getString("FRole.LabelRoleName.text"));

		textName = new Label(shell, SWT.NONE);
		textName.setBounds(78, 74, 350, 20);

		Label label = new Label(shell, SWT.NONE);
		label.setBounds(16, 102, 54, 20);
		label.setText(Lang.getString("FRole.LabelRoleCommand.text"));

		treeCommand = new Tree(shell, SWT.NONE);
		treeCommand.setBounds(78, 105, 350, 155);

		treeDefaultCommand = new TreeItem(treeCommand, SWT.NONE);
		treeDefaultCommand.setText(Lang.getString("FRoleDetails.defaultCommand"));

		treeDeviceCommand = new TreeItem(treeCommand, SWT.NONE);
		treeDeviceCommand.setText(Lang.getString("FRole.TreeItemCommandDecice.text"));
		treeDeviceCommand.setChecked(true);

		treeQueryCommand = new TreeItem(treeCommand, SWT.NONE);
		treeQueryCommand.setText(Lang.getString("FRole.TreeItemCommandInquery.text"));

		treeOperation = new TreeItem(treeCommand, SWT.NONE);
		treeOperation.setText(Lang.getString("FRole.TreeItemCommandOperating.text"));
		treeOperation.setExpanded(true);

		treeService = new TreeItem(treeCommand, SWT.NONE);
		treeService.setText(Lang.getString("FRole.TreeItemCommandServer.text"));

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(16, 280, 54, 20);
		label_1.setText(Lang.getString("FRole.LabelRemark.text"));

		textRemark = new Label(shell, SWT.NONE);
		textRemark.setBounds(78, 280, 350, 20);

		Label lblNewLabel_3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_3.setBounds(0, 308, 444, 10);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(348, 326, 80, 27);
		btnNewButton.setText(Lang.getString("FDeviceCurrentStatus.ButtonClose.text"));
		for (ActionCase c : ActionCase.values()) {
			if (c == ActionCase.ACTION_NOT_SET) {
				break;
			}
			if (c.getNumber() > 199) {
				TreeItem item = new TreeItem(treeService, 0);
				item.setText(Lang.getString("Command." + c.name()));
				item.setData(c);
			}
		}

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
			textRemark.setText(tag.getRemark());

			for (Integer c : tag.getCommands()) {
				if (c == ActionCase.ACTION_NOT_SET.getNumber()) {
					break;
				}

				if (c > 0 && c < 20) {
					TreeItem item = new TreeItem(treeDefaultCommand, 0);
					item.setText(Lang.getString("Command." + ActionCase.forNumber(c).name()));
				} else if (c >= 20 && c < 30) {
					TreeItem item = new TreeItem(treeDeviceCommand, 0);
					item.setText(Lang.getString("Command." + ActionCase.forNumber(c).name()));
				} else if (c >= 30 && c < 101) {
					TreeItem item = new TreeItem(treeQueryCommand, 0);
					item.setText(Lang.getString("Command." + ActionCase.forNumber(c).name()));
				} else if (c >= 101 && c < 199) {
					TreeItem item = new TreeItem(treeOperation, 0);
					item.setText(Lang.getString("Command." + ActionCase.forNumber(c).name()));
				} else if (c > 199) {
					TreeItem item = new TreeItem(treeService, 0);
					item.setText(Lang.getString("Command." + ActionCase.forNumber(c).name()));
				}
			}

		}
	}
}

/**
 * 2017年1月19日
 */
package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.custom.CLabel;

/**
 * @author HLX
 *
 */
public class FRole extends Dialog {
	private Servo servo;
	private Role tag;
	private Shell shell;
	private Text textName;
	private Text textRemark;
	private boolean closing;
	private Button btnAll;
	private Button btnAccept;
	private Button btnCancel;
//	private TreeItem treeDeviceCommand;
	private TreeItem treeQueryCommand;
	private TreeItem treeOperation;
	private TreeItem treeService;
	private Tree treeCommand;

	public boolean isUpdate = false;
	private CLabel labelVerify;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FRole(Shell parent, Servo s) {
		super(parent, 0);
		closing = false;
		servo = s;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Role open(Role s) {
		tag = s;
		createContents();
		F.center(getParent(), shell);

		shell.setTabList(new Control[] { textName, treeCommand, textRemark, btnAll, btnAccept, btnCancel });
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.addShellListener(shellAdapter);
		shell.setSize(429, 381);
		shell.setText(Lang.getString("FRole.ShellTitle.text"));
		{
			// 验证信息
			labelVerify = new CLabel(shell, SWT.NONE);
			labelVerify.setVisible(false);
			labelVerify.setImage(Resource.getImage(FRole.class, "/com/kiy/resources/verify.png"));
			labelVerify.setBounds(16, 16, 353, 32);

			Label labelImage = new Label(shell, SWT.NONE);
			labelImage.setBounds(377, 16, 32, 32);
			labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/role_create_32.png"));

			Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
			labelTop.setBounds(0, 64, 423, 2);

			// 名称
			CLabel lblName = new CLabel(shell, SWT.NONE);
			lblName.setBounds(16, 74, 35, 23);
			lblName.setText(Lang.getString("FRole.LabelRoleName.text"));

			textName = new Text(shell, SWT.BORDER);
			textName.setBounds(59, 74, 350, 23);
			textName.setTextLimit(32);
			textName.addModifyListener(listener);

			// 权限
			CLabel lblPower = new CLabel(shell, SWT.NONE);
			lblPower.setBounds(16, 105, 35, 23);
			lblPower.setText(Lang.getString("FRole.LabelRoleCommand.text"));
			
			treeCommand = new Tree(shell, SWT.CHECK);
			treeCommand.setBounds(59, 105, 350, 155);
			treeCommand.addFocusListener(listener2);
			treeCommand.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					if (event.detail == SWT.CHECK) {
						TreeItem item = (TreeItem) event.item;
						boolean checked = item.getChecked();
						checkAllChildren(item.getItems(), checked);
						// 触发这个的Item的grayed = false，因为这是个CHECK事件，要么全选，要么全不选。
						checkParent(item.getParentItem(), checked, false);
					}
				}
			});

			treeQueryCommand = new TreeItem(treeCommand, SWT.CHECK);
			treeQueryCommand.setText(Lang.getString("FRole.TreeItemCommandInquery.text"));

			treeOperation = new TreeItem(treeCommand, SWT.CHECK);
			treeOperation.setText(Lang.getString("FRole.TreeItemCommandOperating.text"));

			treeService = new TreeItem(treeCommand, SWT.CHECK);
			treeService.setText(Lang.getString("FRole.TreeItemCommandServer.text"));

			// 备注
			CLabel lblRemark = new CLabel(shell, SWT.NONE);
			lblRemark.setBounds(16, 268, 35, 23);
			lblRemark.setText(Lang.getString("FRole.LabelRemark.text"));

			textRemark = new Text(shell, SWT.BORDER);
			textRemark.setBounds(59, 268, 350, 23);
			textRemark.setTextLimit(128);
			textRemark.addModifyListener(listener);
			textRemark.addFocusListener(listener2);

		}

		/*
		 * 权限
		 */
		{
//			// 添加设备权限
//			for (ActionCase c : ActionCase.values()) {
//				if (c == ActionCase.ACTION_NOT_SET) {
//					break;
//				}
//				if (c.getNumber() >= 20 && c.getNumber() < 30) {
//					TreeItem item = new TreeItem(treeDeviceCommand, 0);
//					item.setText(Lang.getString("Command." + c.name()));
//					item.setData(c);
//				}
//			}

			for (ActionCase c : ActionCase.values()) {
				if (c == ActionCase.ACTION_NOT_SET) {
					break;
				}
				if (c.getNumber() > 30 && c.getNumber() < 101) {
					TreeItem item = new TreeItem(treeQueryCommand, 0);
					item.setText(Lang.getString("Command." + c.name()));
					item.setData(c);
				}
			}
			for (ActionCase c : ActionCase.values()) {
				if (c == ActionCase.ACTION_NOT_SET) {
					break;
				}
				if (c.getNumber() >= 101 && c.getNumber() < 199) {
					TreeItem item = new TreeItem(treeOperation, 0);
					item.setText(Lang.getString("Command." + c.name()));
					item.setData(c);
				}
			}
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

		}
		Label labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 299, 423, 2);

		// 全选
		btnAll = new Button(shell, SWT.NONE);
		btnAll.addFocusListener(listener2);
		btnAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkTreeItem(treeCommand.getItems());
			}

			public void checkTreeItem(TreeItem[] items) {
				for (TreeItem item : items) {
					item.setChecked(true);
					checkTreeItem(item.getItems());
					checkParent(item.getParentItem(), true, false);
				}
			}
		});

		btnAll.setBounds(59, 309, 80, 27);
		btnAll.setText(Lang.getString("FRole.ButtonSelectAll.text"));

		// 确定
		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				accept();
				isUpdate = true;
			}
		});
		btnAccept.setBounds(241, 309, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);

		// 取消
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = false;
				cancel();
			}
		});
		btnCancel.setBounds(329, 309, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));
		make();
	}

	private FocusListener listener2 = new FocusAdapter() {
		public void focusGained(FocusEvent arg0) {
			verify();
		}
	};

	/**
	 * 添加内容
	 */
	private void make() {
		if (tag == null)
			return;

		textName.setText(tag.getName());
		textName.setSelection(tag.getName().length());
		textRemark.setText(tag.getRemark());

//		checkChildren(tag, treeDeviceCommand);
		checkChildren(tag, treeQueryCommand);
		checkChildren(tag, treeOperation);
		checkChildren(tag, treeService);
	}

	private void cancel() {
		tag = null;
		close();
	}

	/**
	 * 接收内容
	 */
	private void accept() {
		if (tag == null) {
			tag = new Role();
			tag.newId();
		}

		tag.setName(textName.getText());
		tag.setRemark(textRemark.getText());
		tag.offAllCommands();

		/**
		 * 默认上传默认权限
		 */

		for (ActionCase c : ActionCase.values()) {
			if (c == ActionCase.ACTION_NOT_SET) {
				break;
			}
			if (c.getNumber() > 0 && c.getNumber() < 31) {
				tag.addCommand(c.getNumber());
			}
		}

//		checked(treeDeviceCommand.getItems());
		checked(treeQueryCommand.getItems());
		checked(treeOperation.getItems());
		checked(treeService.getItems());
		close();
	}

	private void checked(TreeItem[] items) {
		for (TreeItem item : items) {
			if (item.getChecked()) {
				if (item.getData() != null && item.getData() instanceof ActionCase) {
					tag.addCommand(((ActionCase) item.getData()).getNumber());
				}
			}
			checked(item.getItems());
		}
	}

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}

	/**
	 * 父节点选中
	 * 
	 * @param parent
	 * @param checked
	 * @param grayed
	 */
	private void checkParent(TreeItem parent, boolean checked, boolean grayed) {
		if (parent == null)
			return;
		for (TreeItem child : parent.getItems()) {

			if (checked != child.getChecked()) {
				// 选中状态不相同，没有全部选中
				checked = true;
				grayed = true;
				break;
			} else {
				grayed = false;
			}
		}
		parent.setChecked(checked);
		parent.setGrayed(grayed);
	}

	/**
	 * 子节点选中
	 * 
	 * @param children
	 * @param checked
	 */
	private void checkAllChildren(TreeItem[] children, boolean checked) {
		if (children.length == 0)
			return;
		for (TreeItem child : children) {
			child.setChecked(checked);
			child.getParentItem().setGrayed(false);
		}

	}

	private void checkChildren(Role tag, TreeItem treeItem) {
		for (TreeItem itemClear : treeItem.getItems()) {
			itemClear.setChecked(false);
		}

		int itemCount = treeItem.getItemCount();
		int count = 0;
		for (TreeItem item : treeItem.getItems()) {
			ActionCase command = (ActionCase) item.getData();
			if (tag.hasCommand(command.getNumber())) {
				item.setChecked(true);
				count++;
			}
		}
		if (itemCount == count) {
			treeItem.setChecked(true);
			treeItem.setGrayed(false);
		} else if (count > 0) {
			treeItem.setChecked(true);
			treeItem.setGrayed(true);
		} else {
			treeItem.setChecked(false);
			treeItem.setGrayed(false);
		}
	}

	private ModifyListener listener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	private void verify() {
		btnAccept.setEnabled(false);
		labelVerify.setVisible(true);
		if (Tool.isEmpty(textName.getText())) {
			labelVerify.setText(Lang.getString("FRole.verifyRoleNameIsNull.text"));
			return;
		}
		String name = textName.getText();
		Set<Role> roles = servo.getRoles();
		for (Role role : roles) {
			if (tag != null) {
				if (name.equals(role.getName()) && !name.equals(tag.getName())) {
					labelVerify.setVisible(true);
					labelVerify.setText(Lang.getString("FRole.verifyRoleNameIsExsit.text"));
					return;
				}
			} else if (name.equals(role.getName())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FRole.verifyRoleNameIsExsit.text"));
				return;
			}

		}
		labelVerify.setText(Tool.EMPTY);
		labelVerify.setVisible(false);
		btnAccept.setEnabled(true);
	}

	private ShellAdapter shellAdapter = new ShellAdapter() {

		@Override
		public void shellClosed(ShellEvent arg0) {
			closing = true;
			isUpdate = false;
			close();
		}

	};
}
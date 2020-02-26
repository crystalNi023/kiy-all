/**
 * 2017年1月19日
 */
package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.common.Zone;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.widgets.Label;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CCombo;

/**
 * 用户窗口
 * 
 * @author HLX
 */
public class FUser extends Dialog {
	public boolean isUpdate = false;

	private Button btnZone;
	private User tag;
	private Servo servo;
	private Shell shell;
	private boolean closing;
	private Text textName;
	private Text textRealname;
	private Text textMobile;
	private Text textPhone;
	private Text textEmail;
	private Tree treeRole;
	private Text textRemark;
	private Button btnAccept;
	private Button btnRadioButtonAble;
	private Button btnRadioButtonDisable;
	private Zone[] mZones;
	private CCombo comboZone;
	private Text textPassword;
	private Text textEnsurePassword;
	private Text textNickname;

	private CLabel labelVerify;

	private Role selectionRole;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FUser(Shell parent) {
		super(parent, 0);
		closing = false;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public User open(Servo s, User u, Role r) {
		selectionRole = r;
		tag = u;
		servo = s;
		// isCreate = action;

		createContents();
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
		shell.addShellListener(new ShellAdapter() {

			@Override
			public void shellClosed(ShellEvent e) {
				closing = true;
				close();
			}
		});

		shell.setSize(473, 655);
		shell.setText(Lang.getString("FUser.ShellName.text"));

		labelVerify = new CLabel(shell, SWT.NONE);
		labelVerify.setVisible(false);
		labelVerify.setImage(Resource.getImage(FUser.class, "/com/kiy/resources/verify.png"));
		labelVerify.setBounds(16, 16, 379, 32);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(403, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/user_create_32.png"));

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 64, 500, 2);

		// 名称
		CLabel lblName = new CLabel(shell, SWT.NONE);
		lblName.setBounds(16, 74, 79, 23);
		lblName.setText(Lang.getString("FUser.LabelUserName.text"));

		textName = new Text(shell, SWT.BORDER);
		textName.setBounds(101, 74, 350, 23);
		textName.setTextLimit(32);
		textName.addModifyListener(listener);

		// 密码
		CLabel lblPassword = new CLabel(shell, SWT.NONE);
		lblPassword.setBounds(16, 105, 79, 23);
		lblPassword.setText(Lang.getString("FUpdateUser.lblNewLabel.text")); //$NON-NLS-1$

		textPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(101, 105, 350, 23);
		textPassword.setTextLimit(20);
		textPassword.addModifyListener(listener);
		textPassword.addFocusListener(listener2);

		// 确认密码:
		CLabel lblEnsurePassword = new CLabel(shell, SWT.NONE);
		lblEnsurePassword.setBounds(16, 136, 79, 23);
		lblEnsurePassword.setText(Lang.getString("FUpdateUser.lblNewLabel_1.text"));

		textEnsurePassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		textEnsurePassword.setBounds(101, 136, 350, 23);
		textEnsurePassword.setTextLimit(20);
		textEnsurePassword.addModifyListener(listener);
		textEnsurePassword.addFocusListener(listener2);

		// 用户状态
		CLabel lblConfirm = new CLabel(shell, SWT.NONE);
		lblConfirm.setBounds(16, 167, 74, 23);
		lblConfirm.setText(Lang.getString("FUpdateUser.LabelUserStatus.text"));

		btnRadioButtonAble = new Button(shell, SWT.RADIO);
		btnRadioButtonAble.setBounds(98, 167, 60, 23);
		btnRadioButtonAble.setText(Lang.getString("FUpdateUser.RadioButtonAbel.text"));
		btnRadioButtonAble.setSelection(true);
		btnRadioButtonAble.addFocusListener(listener2);

		btnRadioButtonDisable = new Button(shell, SWT.RADIO);
		btnRadioButtonDisable.setBounds(166, 167, 60, 23);
		btnRadioButtonDisable.setText(Lang.getString("FUpdateUser.RadioButtonUnAbel.text"));
		btnRadioButtonDisable.addFocusListener(listener2);

		if (tag != null) {
			lblPassword.setEnabled(false);
			textPassword.setEnabled(false);
			lblEnsurePassword.setEnabled(false);
			textEnsurePassword.setEnabled(false);
		} else {
			lblPassword.setEnabled(true);
			textPassword.setEnabled(true);
			lblEnsurePassword.setEnabled(true);
			textEnsurePassword.setEnabled(true);
		}
		// 角色(树)
		Label lblRole = new Label(shell, SWT.NONE);
		lblRole.setBounds(16, 198, 60, 23);
		lblRole.setText(Lang.getString("FUser.LabelRole.text"));

		treeRole = new Tree(shell, SWT.BORDER | SWT.CHECK);
		treeRole.setBounds(101, 198, 350, 130);
		treeRole.addFocusListener(listener2);

		// 区域(树)
		CLabel lblZone = new CLabel(shell, SWT.NONE);
		lblZone.setBounds(16, 336, 79, 23);
		lblZone.setText(Lang.getString("FUser.LabelZone.text"));

		comboZone = new CCombo(shell, SWT.READ_ONLY | SWT.BORDER);
		comboZone.setBounds(101, 336, 318, 23);
		comboZone.add(Lang.getString("FUser.ComboDefaultZone.text"));
		comboZone.setText(Lang.getString("FUser.ComboDefaultZone.text"));
		comboZone.addFocusListener(listener2);

		btnZone = new Button(shell, SWT.NONE);
		btnZone.addFocusListener(listener2);
		btnZone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FZoneParent dialog = new FZoneParent(shell);
				dialog.open(servo,null);
				if (dialog.combValue != null) {
					comboZone.setText(dialog.combValue);
					comboZone.setData(dialog.combZoneValue);
				}
			}
		});

		btnZone.setBounds(427, 336, 23, 23);
		btnZone.setText("...");

		// 真实姓名
		CLabel lblRealname = new CLabel(shell, SWT.NONE);
		lblRealname.setBounds(16, 367, 79, 23);
		lblRealname.setText(Lang.getString("FUser.LabelRealName.text"));

		textRealname = new Text(shell, SWT.BORDER);
		textRealname.setBounds(101, 367, 350, 23);
		textRealname.setTextLimit(32);
		textRealname.addModifyListener(listener);
		textRealname.addFocusListener(listener2);
		
		CLabel lblNickname = new CLabel(shell, SWT.NONE);
		lblNickname.setText(Lang.getString("FUser.lblNickname.text")); //$NON-NLS-1$
		lblNickname.setBounds(16, 398, 79, 23);
		
		textNickname = new Text(shell, SWT.BORDER);
		textNickname.setTextLimit(32);
		textNickname.setBounds(101, 398, 350, 23);
		// 手机
		CLabel lblMobile = new CLabel(shell, SWT.NONE);
		lblMobile.setBounds(16, 429, 79, 23);
		lblMobile.setText(Lang.getString("FUser.LabelMobile.text"));

		textMobile = new Text(shell, SWT.BORDER);
		textMobile.setBounds(101, 429, 350, 23);
		textMobile.addModifyListener(listener);
		textMobile.setTextLimit(32);
		textMobile.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent arg0) {
				F.intNumberCheck(arg0, textMobile);
			}
		});

		// 固定电话
		CLabel lblPhone = new CLabel(shell, SWT.NONE);
		lblPhone.setBounds(16, 458, 79, 23);
		lblPhone.setText(Lang.getString("FUser.LabelPhone.text"));

		textPhone = new Text(shell, SWT.BORDER);
		textPhone.setBounds(101, 458, 350, 23);
		textPhone.setTextLimit(32);
		textPhone.addModifyListener(listener);
		textPhone.addFocusListener(listener2);

		// 邮件
		CLabel lblEmail = new CLabel(shell, SWT.NONE);
		lblEmail.setBounds(16, 489, 79, 23);
		lblEmail.setText(Lang.getString("FUser.LabelEmail.text"));

		textEmail = new Text(shell, SWT.BORDER);
		textEmail.setBounds(101, 489, 350, 23);
		textEmail.setTextLimit(64);
		textEmail.addModifyListener(listener);
		textEmail.addFocusListener(listener2);

		// 备注
		CLabel lblRemark = new CLabel(shell, SWT.NONE);
		lblRemark.setBounds(16, 520, 79, 23);
		lblRemark.setText(Lang.getString("FUser.LabelRemark.text"));

		textRemark = new Text(shell, SWT.BORDER);
		textRemark.setBounds(101, 520, 350, 23);
		textRemark.addModifyListener(listener);
		textRemark.setTextLimit(128);
		textRemark.addFocusListener(listener2);

		Label labelBottom = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelBottom.setBounds(0, 551, 467, 2);

		// 取消
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancel();
				isUpdate = false;
				close();
			}
		});
		btnCancel.setBounds(371, 561, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));

		// 确定
		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(283, 561, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				accept();
				isUpdate = true;
				close();
			}
		});

		for (Role role : servo.getRoles()) {
			TreeItem item = new TreeItem(treeRole, 0);
			item.setText(role.getName());
			item.setData(role);
		}

		Set<Zone> zones = servo.getZones();
		mZones = new Zone[zones.size() + 1];
		int index = 1;
		for (Zone zone : zones) {
			comboZone.add(zone.getName());
			comboZone.setData(zone);
			mZones[index] = zone;
			index++;
		}

		F.center(getParent(), shell);

		if (selectionRole != null) {
			for (TreeItem item : treeRole.getItems()) {
				Role role = (Role) item.getData();
				if (selectionRole == role)
					item.setChecked(true);
			}
		}
		
		make();

	}

	private void make() {

		if (tag == null)
			return;
		if (tag.getName() != null) {
			textName.setText(tag.getName());
			textName.setSelection(tag.getName().length());
		}
		if(tag.getNickname()!=null)
			textNickname.setText(tag.getNickname());
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
		btnRadioButtonAble.setSelection(false);
		btnRadioButtonDisable.setSelection(false);
		if (tag.getEnable()) {
			btnRadioButtonAble.setSelection(true);
		} else {
			btnRadioButtonDisable.setSelection(true);
		}

		// 设置区域

		if (tag.getZone() != null) {
			comboZone.setText(tag.getZone().getName());
		} else {
			comboZone.setText(Lang.getString("FUser.ComboDefaultZone.text"));
		}

		for (TreeItem item : treeRole.getItems()) {
			Role role = (Role) item.getData();
			if (tag.hasRole(role))
				item.setChecked(true);
		}
	}

	private void cancel() {
		tag = null;
		close();
	}

	private void accept() {
		if (tag == null) {
			tag = new User();
			tag.setPassword(textPassword.getText());
			tag.newId();
		}

		if (btnRadioButtonAble.getSelection())
			tag.setEnable(true);
		if (btnRadioButtonDisable.getSelection())
			tag.setEnable(false);
		tag.setName(textName.getText());
		tag.setRealname(textRealname.getText());
		tag.setNickname(textNickname.getText());
		tag.setMobile(textMobile.getText());
		tag.setPhone(textPhone.getText());
		tag.setEmail(textEmail.getText());
		tag.setRemark(textRemark.getText());
		// 接受选择的zone
		int index = comboZone.getSelectionIndex();
		tag.setZoneId(index > 0 ? mZones[index].getId() : null);

		tag.offAllRoles();
		checked(treeRole.getItems());

		close();
	}

	private void checked(TreeItem[] items) {
		for (TreeItem item : items) {
			if (item.getChecked()) {
				if (item.getData() != null && item.getData() instanceof Role) {
					tag.addRole((Role) item.getData());
				}
			}
		}
	}

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
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

	private void verify() {
		if (tag != null) {
			btnAccept.setEnabled(false);
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
			btnAccept.setEnabled(true);
		} else {
			btnAccept.setEnabled(false);
			if (Tool.isEmpty(textName.getText())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FUser.VerifyTextNameIsNull.text"));
				return;
			}
			String name = textName.getText();
			Set<User> users = servo.getUsers();
			for (User user : users) {
				if (name.equals(user.getName())) {
					labelVerify.setVisible(true);
					labelVerify.setText(Lang.getString("FUser.VerifyTextNameIsExist.text"));
					return;
				}
			}
			if (Tool.isEmpty(textPassword.getText())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FUser.VerifyPasswordIsNull.text"));
				return;
			}
			if (Tool.isEmpty(textEnsurePassword.getText())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FUser.VerifyEnsurePasswordIsNull.text"));
				return;
			}
			if (!textPassword.getText().equals(textEnsurePassword.getText())) {
				labelVerify.setVisible(true);
				labelVerify.setText(Lang.getString("FUser.VerifyPasswordfaile.text"));
				return;
			}
			labelVerify.setText(Tool.EMPTY);
			labelVerify.setVisible(false);
			btnAccept.setEnabled(true);
		}

	}
}
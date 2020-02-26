package com.kiy.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.common.Role;
import com.kiy.common.User;
import com.kiy.resources.Lang;

/**
 * 查看用户或者角色的信息，只读不能修改
 * 
 * @author HLX
 *
 */
public class ACheckUserAndRoleInfo extends A<FUserAndRole> {
	private Table mTableUser;
	private Table mTableRole;

	/**
	 * 
	 * @param shell
	 * @param tableUser
	 *            Table 用户表格
	 * @param tableRole
	 *            Table 角色表格
	 */
	public ACheckUserAndRoleInfo(FUserAndRole m, Table tableUser, Table tableRole) {
		super(m);
		mTableUser = tableUser;
		mTableRole = tableRole;
	}

	@Override
	protected void run() {
		User selectedUser = null;
		Role selectedRole = null;

		if (mTableUser != null && mTableUser.isFocusControl()) {
			int selectionUserIndex = mTableUser.getSelectionIndex();
			if (selectionUserIndex != -1/* 有选中项 */) {
				TableItem item = mTableUser.getItem(selectionUserIndex);
				Object data = item.getData();
				if (data != null) {
					if (data instanceof User) {
						selectedUser = (User) data;
					} else {
						// item的数据无法转化为User实体对象
					}
				} else {
					// item里的数据为空
				}
			} else {
				// User表没有选择项
			}
		}

		if (mTableRole != null && mTableRole.isFocusControl()) {
			int selectionRoleIndex = mTableRole.getSelectionIndex();
			if (selectionRoleIndex != -1/* 有选中项 */) {
				TableItem item = mTableRole.getItem(selectionRoleIndex);
				Object data = item.getData();
				if (data != null) {
					if (data instanceof Role) {
						selectedRole = (Role) data;
					} else {
						// item的数据无法转化为Role实体对象
					}
				} else {
					// item里的数据为空
				}
			} else {
				// User表没有选择项
			}
		}

		if (selectedUser == null && selectedRole == null) {
			F.mbInformation(main.getShell(), Lang.getString("Property.text"), Lang.getString("Property.tip"));
			return;
		}

		if (selectedUser != null) {
			// 打开用户界面
			FUserDetails f = new FUserDetails(main.getShell(), selectedUser);
			f.open();
		}

		if (selectedRole != null) {
			// 打开角色界面
			FRoleDetails f = new FRoleDetails(main.getShell(), selectedRole);
			f.open();
		}
	}

}

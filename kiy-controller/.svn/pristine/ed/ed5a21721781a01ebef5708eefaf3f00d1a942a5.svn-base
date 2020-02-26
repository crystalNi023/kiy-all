package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.DeleteRole;
import com.kiy.protocol.Messages.DeleteUser;
import com.kiy.protocol.Messages.Message;
import com.kiy.resources.Lang;

/**
 * 删除用户或者角色
 * 
 * @author HLX
 *
 */
public class ADeleteUserOrRole extends A<FUserAndRole> {

	private Table mTableUser;
	private Table mTableRole;
	private Servo servo;

	/**
	 * 
	 * @param m
	 * @param tableUser
	 * @param tableRole
	 */
	public ADeleteUserOrRole(FUserAndRole m, Servo s, Table tableUser, Table tableRole) {
		super(m);
		servo = s;
		mTableUser = tableUser;
		mTableRole = tableRole;
	}

	@Override
	protected void run() {
		User selectionUser = null;
		Role selectionRole = null;

		if (servo == null) {
			throw new NullPointerException();
		}

		if (mTableUser != null && mTableUser.isFocusControl()) {
			int selectionUserIndex = mTableUser.getSelectionIndex();
			if (selectionUserIndex != -1) {
				TableItem item = mTableUser.getItem(selectionUserIndex);
				Object data = item.getData();
				if (data != null) {
					if (data instanceof User) {
						selectionUser = (User) data;
					}
				}
			} else {
				// User表没有选中项
			}
		}

		if (mTableRole != null && mTableRole.isFocusControl()) {
			int selectionRoleIndex = mTableRole.getSelectionIndex();
			if (selectionRoleIndex != -1) {
				TableItem item = mTableRole.getItem(selectionRoleIndex);
				Object data = item.getData();
				if (data != null) {
					if (data instanceof Role) {
						selectionRole = (Role) data;
					} else {
						// data 不能强转为 Role
					}
				}
			} else {
				// Role表没有选中项
			}
		}

		if (selectionUser == null && selectionRole == null) {
			F.mbInformation(main.getShell(), Lang.getString("FUserAndRole.deleteItem.text"), Lang.getString("ADeleteUserAndRole.MessageBoxContent.text"));
			return;
		}

		if (selectionUser != null) {
			buildDeleteUser(selectionUser, servo.getClient());
		}

		if (selectionRole != null) {
			buildDeleteRole(selectionRole, servo.getClient());
		}
	}

	/**
	 * 发送删除用户参数
	 * 
	 * @param user User 需要删除的用户对象
	 * @param client CtrClient 客户端对象
	 */
	private void buildDeleteUser(User user, CtrClient client) {

		if (user == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteUser.TipDeleteUserTitle.text"), String.format(Lang.getString("ADeleteUser.MessageBoxDeleteEnsureContent.text"), user.getName()));

		if (open == SWT.OK) {
			Message.Builder b_m = Message.newBuilder();
			DeleteUser.Builder deleteUser = DeleteUser.newBuilder();
			deleteUser.setId(user.getId());
			b_m.setDeleteUser(deleteUser);
			b_m.setUserId(servo.getUser().getId());
			b_m.setKey(CtrClient.getKey());
			client.send(b_m.build());
		} else {
			// 取消删除
		}
	}

	/**
	 * 发送删除角色参数
	 * 
	 * @param role Role 需要删除的角色对象
	 * @param client CtrClient 客户端对象
	 */
	private void buildDeleteRole(Role role, CtrClient client) {

		if (role == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteRole.TipDeleteRoleTitle.text"),String.format(Lang.getString("ADeleteRole.MessageBoxDeleteEnsureContent.text"), role.getName()));

		if (open == SWT.OK) {
			Message.Builder b_m = Message.newBuilder();
			DeleteRole.Builder deleteRole = DeleteRole.newBuilder();
			deleteRole.setId(role.getId());
			b_m.setDeleteRole(deleteRole);
			b_m.setUserId(servo.getUser().getId());
			b_m.setKey(CtrClient.getKey());
			client.send(b_m.build());
		} else {
			// 取消删除
		}
	}
}

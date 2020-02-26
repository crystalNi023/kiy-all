package com.kiy.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateRole;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Units.MRole;
import com.kiy.protocol.Units.MUser.Builder;
import com.kiy.resources.Lang;

/**
 * 修改用户角色
 * 
 * @author hlx
 *
 */
public class AModifyUserAndRole extends A<FUserAndRole> {

	private Servo servo;
	private Table mTableUser;
	private Table mTableRole;

	/**
	 * 
	 * @param m FUserAndRole
	 * @param servo Servo
	 * @param tableUser Table
	 * @param tableRole Table
	 */
	public AModifyUserAndRole(FUserAndRole m, Servo servo, Table tableUser, Table tableRole) {
		super(m);
		this.servo = servo;
		mTableUser = tableUser;
		mTableRole = tableRole;
	}

	@Override
	protected void run() {
		User selectedUser = null;
		Role selectedRole = null;

		if (servo == null) {
			throw new NullPointerException();
		}

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
			F.mbInformation(main.getShell(), Lang.getString("FUserAndRole.editItem.text"), Lang.getString("AModifyUserAndRole.MessageBoxContent.text"));
		}

		if (selectedUser != null) {
			buildUpdateUser(selectedUser, servo.getClient());
		}

		if (selectedRole != null) {
			buildUpdateRole(selectedRole, servo.getClient());
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user User 需要修改的用户对象
	 * @param client CtrClient 客户端对象
	 */
	private void buildUpdateUser(User user, CtrClient client) {
		if (user == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		FUser fUser = new FUser(main.getShell());
		User userUpdate = fUser.open(servo, user, null);
		if (fUser.isUpdate) {
			if (userUpdate != null) {
				Message.Builder b_m = Message.newBuilder();
				b_m.setKey(CtrClient.getKey());
				b_m.setUserId(servo.getUser().getId());
				UpdateUser.Builder updateUser = UpdateUser.newBuilder();
				Builder builder = updateUser.getItemBuilder();
				builder.setId(userUpdate.getId());
				builder.setName(userUpdate.getName());
				builder.setRealname(userUpdate.getRealname());
				builder.setMobile(userUpdate.getMobile());
				builder.setPhone(userUpdate.getPhone());
				builder.setEmail(userUpdate.getEmail());
				builder.setEnable(userUpdate.getEnable());
				builder.setRemark(userUpdate.getRemark());
				builder.setPassword(user.getPassword());
				// 设置更新zoneId
				if (userUpdate.getZoneId() != null) {
					builder.setZoneId(userUpdate.getZoneId());
				}
				if (user.getRoles() != null) {
					for (Role role : userUpdate.getRoles()) {
						builder.addRoles(role.getId());
					}
				}
				b_m.setUpdateUser(updateUser);
				client.send(b_m.build());
			}
		} else {
			// 不更新
		}

	}

	/**
	 * 更新角色
	 * 
	 * @param role Role 需要修改的角色对象
	 * @param client CtrClient 客户端对象
	 */
	private void buildUpdateRole(Role role, CtrClient client) {
		if (role == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		FRole fRole = new FRole(main.getShell(), servo);
		Role roleUpdate = fRole.open(role);
		if (fRole.isUpdate) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			b_m.setUserId(servo.getUser().getId());
			UpdateRole.Builder updateRole = UpdateRole.newBuilder();
			MRole.Builder builder = updateRole.getItemBuilder();
			builder.setId(roleUpdate.getId());
			builder.setName(roleUpdate.getName());
			builder.setRemark(roleUpdate.getRemark());
			if (roleUpdate.getCommands() != null) {
				for (Integer command : roleUpdate.getCommands()) {
					builder.addCommands(command);
				}
			}
			b_m.setUpdateRole(updateRole);
			client.send(b_m.build());

		} else {
			// 不更新
		}

	}
}

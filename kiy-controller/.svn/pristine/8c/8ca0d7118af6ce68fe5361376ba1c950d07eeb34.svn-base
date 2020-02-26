package com.kiy.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateRole;
import com.kiy.protocol.Units.MRole.Builder;
import com.kiy.resources.Lang;

/**
 * 更新角色
 * 
 * @author HLX
 *
 */
public class ARoleUpdate extends A<FUserAndRole> {

	private Servo mServo;
	private Table mTable;
	private Role mRole;

	/**
	 * 其它更新
	 * 
	 * @param role Role
	 * @param servo Servo
	 */
	public ARoleUpdate(FUserAndRole m, Role role) {
		super(m);
		mRole = role;
		mServo = role.getServo();
	}

	/**
	 * 表格选中更新
	 * 
	 * @param m FUserAndRole
	 * @param servo Servo
	 * @param table Table
	 */
	public ARoleUpdate(FUserAndRole m, Servo servo, Table table) {
		super(m);
		mServo = servo;
		mTable = table;
	}

	@Override
	protected void run() {
		if (mServo == null) {
			throw new NullPointerException();
		}

		if (mTable != null) {
			int listHaveChoose = mTable.getSelectionIndex();
			if (listHaveChoose != -1/* 未选中 */) {
				TableItem ti = mTable.getItem(listHaveChoose);
				Object obj = ti.getData();
				if (obj != null) {
					if (obj instanceof Role) {
						Role role = (Role) obj;
						CtrClient client = mServo.getClient();
						buildUpdateRole(role, client);
					}
				}
			} else {
				F.mbInformation(main.getShell(), Lang.getString("AUpdateRole.MessageBoxUpdateRoleTitle.text"), Lang.getString("AUpdateRole.MessageBoxUpdateRoleContent.text"));
				return;
			}
		} else {
			if (mRole != null) {
				CtrClient client = mServo.getClient();
				buildUpdateRole(mRole, client);
			}
		}
	}

	/**
	 * 更新角色
	 * 
	 * @param role Role
	 * @param client CtrClient
	 */
	private void buildUpdateRole(Role role, CtrClient client) {
		if (role == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		FRole fRole = new FRole(main.getShell(), mServo);
		Role roleUpdate = fRole.open(role);
		if (fRole.isUpdate) {
			if (roleUpdate != null) {
				Message.Builder b_m = Message.newBuilder();
				b_m.setKey(CtrClient.getKey());
				b_m.setUserId(mServo.getUser().getId());
				UpdateRole.Builder updateRole = UpdateRole.newBuilder();
				Builder builder = updateRole.getItemBuilder();
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
			}
		} else {
			// 不更新
		}

	}
}

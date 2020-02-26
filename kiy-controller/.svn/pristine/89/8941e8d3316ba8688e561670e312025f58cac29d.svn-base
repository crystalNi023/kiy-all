package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.protocol.Messages.DeleteRole;
import com.kiy.protocol.Messages.Message;
import com.kiy.resources.Lang;

/**
 * 删除角色
 * 
 * @author HLX 2017.3.29
 *
 */
public class ADeleteRole extends A<FUserAndRole> {

	private Table table;
	private Role role;
	private Servo servo;

	/**
	 * delete role not choose in table
	 * 
	 * @param m
	 *            FUserAndRole
	 * @param r
	 *            Role
	 */
	public ADeleteRole(FUserAndRole m, Role r) {
		super(m);
		role = r;
		servo = r.getServo();
	}

	/**
	 * 在表格中选中删除角色
	 * 
	 * @param m
	 *            FUserAndRole
	 * @param t
	 *            Table
	 */
	public ADeleteRole(FUserAndRole m, Table t) {
		super(m);
		table = t;
	}

	@Override
	protected void run() {

		if (table != null&&table.isFocusControl()) {
			int listHaveChoose = table.getSelectionIndex();
			if (listHaveChoose != -1/* 未选中 */) {
				TableItem ti = table.getItem(listHaveChoose);
				Object obj = ti.getData();
				if (obj != null) {
					if (obj instanceof Role) {
						Role role = (Role) obj;
						servo = role.getServo();
						if (servo == null) {
							throw new NullPointerException("从角色里取得的伺服器对象为空");
						}

						buildDeleteRole(role, servo.getClient());
					}
				}
			} else {
				F.mbInformation(main.getShell(), Lang.getString("ADeleteRole.TipDeleteRoleTitle.text"), Lang.getString("ADeleteRole.TipDeleteRoleContent.text"));
				return;
			}
		} else {
			if (role != null) {
				Servo servo2 = role.getServo();
				if (servo2 == null) {
					throw new NullPointerException("从角色里取得的伺服器对象为空");
				}
				buildDeleteRole(role, servo2.getClient());
			}
		}
	}

	/**
	 * 发送删除角色参数
	 * 
	 * @param role
	 *            Role 需要删除的角色
	 * @param client
	 *            CtrClient
	 */
	private void buildDeleteRole(Role role, CtrClient client) {
		if (role == null) {
			return;
		}

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);

		if (judgeClientActive) {
			return;
		}

		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteRole.TipDeleteRoleTitle.text"), String.format(Lang.getString("ADeleteRole.MessageBoxDeleteEnsureContent.text"), role.getName()));

		if (open == SWT.OK) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			b_m.setUserId(servo.getUser().getId());
			DeleteRole.Builder deleteRole = DeleteRole.newBuilder();
			deleteRole.setId(role.getId());
			b_m.setDeleteRole(deleteRole);
			client.send(b_m.build());
		} else {
			// 取消删除
		}

	}
}

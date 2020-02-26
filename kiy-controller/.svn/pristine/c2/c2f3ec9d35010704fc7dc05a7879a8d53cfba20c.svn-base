package com.kiy.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.protocol.Messages.CreateUser;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MUser.Builder;

/**
 * 创建用户
 * 
 * @author HLX 2017.3.22
 *
 */
public class ACreateUser extends A<FUserAndRole> {
	private Servo mServo;
	private Table mTable;
	private Role selectionRole;

	/**
	 * 用于没有普通创建
	 * 
	 * @param servo Serco
	 * @param shell Shell
	 */
	public ACreateUser(FUserAndRole m, Servo servo) {
		super(m);
		mServo = servo;
	}

	/**
	 * 用于在角色表格中选中角色的创建动作
	 * 
	 * @param m FUserAndRole
	 * @param servo Servo
	 * @param table Table
	 */
	public ACreateUser(FUserAndRole m, Servo servo, Table table) {
		super(m);
		mServo = servo;
		mTable = table;
	}

	private Role getSelectionRole() {
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
						return role;
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		}

		return null;
	}

	@Override
	protected void run() {
		selectionRole = getSelectionRole();

		CtrClient client = mServo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		FUser f = new FUser(main.getShell());
		User user = f.open(mServo, null, selectionRole);
		if (user != null) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			b_m.setUserId(mServo.getUser().getId());
			CreateUser.Builder createUser = CreateUser.newBuilder();
			Builder builder = createUser.getItemBuilder();
			
			builder.setId(user.getId());
			builder.setEnable(user.getEnable());
			builder.setName(user.getName());
			builder.setPassword(Tool.MD5(user.getPassword()));
			builder.setRealname(user.getRealname());
			builder.setNickname(user.getNickname());
			builder.setMobile(user.getMobile());
			builder.setPhone(user.getPhone());
			builder.setEmail(user.getEmail());
			builder.setRemark(user.getRemark());
			if (!Tool.isEmpty(user.getZoneId()))
				builder.setZoneId(user.getZoneId());
			if (user.getRoleId() != null) {
				for (String roleId : user.getRoleId()) {
					builder.addRoles(roleId);
				}
			}
			b_m.setCreateUser(createUser);
			client.send(b_m.build());
		}
	}
}

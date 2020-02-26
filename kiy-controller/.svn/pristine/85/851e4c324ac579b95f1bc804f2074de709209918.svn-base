package com.kiy.controller;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Units.MUser.Builder;
import com.kiy.resources.Lang;

/**
 * 更新用户
 * @author 35210
 *
 */
public class AUpdateUser extends A<FUserAndRole> {

	private Table mTable;
	private User mUser;
	private Servo servo;

	/**
	 * 用于不是在表格中选中的更新用户操作
	 * @param m FUserAndRole
	 * @param user User
	 * @param s Servo
	 */
	public AUpdateUser(FUserAndRole m,User user, Servo s) {
		super(m);
		mUser = user;
		servo = s;
	}

	/**
	 * 用于在表格中选中的更新用户操作
	 * @param m FUserAndRole
	 * @param s Servo
	 * @param table Table
	 */
	public AUpdateUser(FUserAndRole m,Servo s, Table table) {
		super(m);
		mTable = table;
		servo = s;
	}

	@Override
	protected void run() {
		if(servo == null){
			throw new NullPointerException();
		}
		
		CtrClient client = servo.getClient();
		
		if (mTable != null) {
			int listHaveChoose = mTable.getSelectionIndex();
			if (listHaveChoose != -1/* 未选中 */) {
				TableItem ti = mTable.getItem(listHaveChoose);
				Object obj = ti.getData();
				if (obj != null) {
					if (obj instanceof User) {
						User user = (User) obj;
						buildUpdateUser(user, client);
					}
				}
			} else {
				F.mbInformation(main.getShell(), Lang.getString("AUpdateUser.MessageBoxUpdateUserTitle.text"), Lang.getString("AUpdateUser.MessageBoxUpdateUserContent.text"));
				return;
			}
		} else {
			if (mUser != null) {
				buildUpdateUser(mUser, client);
			}
		}
	}

	private void buildUpdateUser(User user, CtrClient client) {
		if(user == null){
			return;
		}
		
		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if(judgeClientActive){
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
					builder.setNickname(userUpdate.getNickname());
					builder.setRealname(userUpdate.getRealname());
					builder.setMobile(userUpdate.getMobile());
					builder.setPhone(userUpdate.getPhone());
					builder.setEmail(userUpdate.getEmail());
					builder.setEnable(userUpdate.getEnable());
					builder.setRemark(userUpdate.getRemark());
					builder.setPassword(userUpdate.getPassword());
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
}

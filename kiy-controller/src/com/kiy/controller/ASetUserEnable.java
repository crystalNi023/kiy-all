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
 * 设置用户状态
 * 
 * @author HLX
 *
 */
public class ASetUserEnable extends A<FUserAndRole> {

	private Table mTable;
	private Servo servo;

	public ASetUserEnable(FUserAndRole m, Table table, Servo ser) {
		super(m);
		servo = ser;
		mTable = table;
	}

	@Override
	protected void run() {
		if (servo != null) {
			CtrClient client = servo.getClient();

			boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
			if(judgeClientActive){
				return;
			}

			if (mTable != null) {
				int listHaveChoose = mTable.getSelectionIndex();
				if (listHaveChoose != -1/* 未选中 */) {
					TableItem ti = mTable.getItem(listHaveChoose);
					Object obj = ti.getData();
					if (obj != null) {
						if (obj instanceof User) {
							User user = (User) obj;
							if (user != null) {
								Message.Builder b_m = Message.newBuilder();
								b_m.setUserId(servo.getUser().getId());
								b_m.setKey(CtrClient.getKey());
								UpdateUser.Builder updateUser = UpdateUser.newBuilder();
								Builder builder = updateUser.getItemBuilder();
								
								
								builder.setId(user.getId());
								builder.setName(user.getName());
								builder.setRealname(user.getRealname());
								builder.setMobile(user.getMobile());
								builder.setPhone(user.getPhone());
								builder.setEmail(user.getEmail());
								builder.setEnable(!user.getEnable());
								builder.setRemark(user.getRemark());
								builder.setPassword(user.getPassword());
								// 设置更新zoneId
								if (user.getZoneId() != null) {
									builder.setZoneId(user.getZoneId());
								}

								if (user.getRoles() != null) {
									for (Role role : user.getRoles()) {
										builder.addRoles(role.getId());
									}
								}
								b_m.setUpdateUser(updateUser);
								client.send(b_m.build());

							}
						}
					}
				} else {
					F.mbInformation(main.getShell(), Lang.getString("ASetUserEnable.tipTitle"), Lang.getString("ASetUserEnable.tipContent"));
					return;
				}
			}
		}
	}

}

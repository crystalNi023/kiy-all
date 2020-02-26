package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Units.MUser.Builder;

/**
 * 2017.6.27
 * 
 * @author HLX
 *
 */
public class AUpdatePersonalInfo extends A<FMain> {

	public AUpdatePersonalInfo(FMain m) {
		super(m);
	}

	@Override
	protected void run() {

		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		FUpdatePersonalInfo f = new FUpdatePersonalInfo(main.getShell());
		User user = servo.getUser();
		if (user == null) {
			return;
		}

		User userUpdate = f.open(user, servo);

		if (f.isUpdate) {
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
				builder.setPassword(userUpdate.getPassword());
				// 设置更新zoneId
				if (userUpdate.getZoneId() != null) {
					builder.setZoneId(userUpdate.getZoneId());
				}

				if (userUpdate.getRoles() != null) {
					for (Role role : userUpdate.getRoles()) {
						builder.addRoles(role.getId());
					}
				}
				b_m.setUpdateUser(updateUser);
				client.send(b_m.build());
			} else {
				//返回的tag为空
			}
		}
	}

}

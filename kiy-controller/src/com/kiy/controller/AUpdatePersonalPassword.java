package com.kiy.controller;


import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Units.MUser.Builder;
import com.kiy.resources.Lang;

public class AUpdatePersonalPassword extends A<FMain>{
	
	public AUpdatePersonalPassword(FMain m) {
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
		if(judgeClientActive){
			return;
		}
		
		User userUpdate = servo.getUser();
		
		FUpdatePersonalPassword f = new FUpdatePersonalPassword(main.getShell());
		String newPassword = f.open();
		
		if(f.isUpdate){
			String userOldPassword = f.getUserOldPassword();
			String password = userUpdate.getPassword();
			if(password.equals(Tool.MD5(userOldPassword))){
				//输入原始密码一致可以修改
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
				builder.setPassword(Tool.MD5(newPassword));
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
			}else{
				F.mbInformation(main.getShell(), Lang.getString("AUpdatePersonalPassword.titel"), Lang.getString("AUpdatePersonalPassword.tip"));
			}
		}
		
	}

}

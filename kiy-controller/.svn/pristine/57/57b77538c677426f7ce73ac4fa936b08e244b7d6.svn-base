package com.kiy.controller;

import com.kiy.client.CtrClient;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.protocol.Messages.CreateRole;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MRole.Builder;

/**
 * 创建角色
 * 
 * @author HLX 2017.3.22
 *
 */
public class ACreateRole extends A<FUserAndRole> {
	private Servo servo;

	/**
	 * 
	 * @param m FUserAndRole
	 * @param s Servo
	 */
	public ACreateRole(FUserAndRole m, Servo s) {
		super(m);
		servo = s;
	}

	@Override
	protected void run() {
		if (servo == null) {
			throw new NullPointerException();
		}

		FRole dialog = new FRole(main.getShell(), servo);

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		Role role = dialog.open(null);

		if (role != null) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			b_m.setUserId(servo.getUser().getId());
			CreateRole.Builder createRole = CreateRole.newBuilder();
			Builder builder = createRole.getItemBuilder();
			
			builder.setId(role.getId());
			builder.setName(role.getName());
			builder.setRemark(role.getRemark());
			if (role.getCommands() != null) {
				for (Integer command : role.getCommands()) {
					builder.addCommands(command);
				}
			}
			b_m.setCreateRole(createRole);
			client.send(b_m.build());
		}
	}
}

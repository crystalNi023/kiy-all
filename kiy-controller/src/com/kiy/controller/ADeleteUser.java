package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.DeleteUser;
import com.kiy.protocol.Messages.Message;
import com.kiy.resources.Lang;

/**
 * 删除用户动作
 * 
 * @author HLX 2017.3.29
 *
 */
public class ADeleteUser extends A<FUserAndRole> {

	private Table table;
	private User user;
	private Servo servo;

	/**
	 * 不是通过表格选中来删除用户
	 * 
	 * @param m
	 * @param u
	 */
	public ADeleteUser(FUserAndRole m, User u) {
		super(m);
		user = u;
		servo = u.getServo();
	}

	/**
	 * 通过表格选中删除
	 * 
	 * @param m
	 * @param t
	 */
	public ADeleteUser(FUserAndRole m, Table t) {
		super(m);
		table = t;
	}

	@Override
	protected void run() {
		if (table != null && table.isFocusControl()) {
			int listHaveChoose = table.getSelectionIndex();
			if (listHaveChoose != -1) {
				TableItem ti = table.getItem(listHaveChoose);
				Object obj = ti.getData();
				if (obj != null) {
					if (obj instanceof User) {
						User user = (User) obj;
						servo = user.getServo();
						if (servo == null) {
							throw new NullPointerException();
						}

						buildDeleteUser(user, servo.getClient());
					}
				}
			} else {
				F.mbInformation(main.getShell(), Lang.getString("ADeleteUser.TipDeleteUserTitle.text"), Lang.getString("ADeleteUser.TipDeleteUserContent.text"));
				return;
			}
		} else {
			if (user != null) {
				Servo servo2 = user.getServo();
				if (servo2 == null) {
					throw new NullPointerException();
				}
				buildDeleteUser(user, servo2.getClient());
			}
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

		int open = F.mbQuestionCancel(main.getShell(), Lang.getString("ADeleteUser.TipDeleteUserTitle.text"),String.format(Lang.getString("ADeleteUser.MessageBoxDeleteEnsureContent.text"), user.getName()));

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
}

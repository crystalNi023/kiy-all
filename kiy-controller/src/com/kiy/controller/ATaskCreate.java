/**
 * 2017年1月20日
 */
package com.kiy.controller;

import java.util.Date;

import org.eclipse.swt.widgets.Shell;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.protocol.Messages.CreateTask;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Units.MTask.Builder;

/**
 * 创建计划任务
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class ATaskCreate extends A<FMain> {

	/**
	 * @param m FMain
	 */
	public ATaskCreate(FMain m) {
		super(m);
	}

	public ATaskCreate() {
	}

	@Override
	protected void run() {
		// 判断伺服器节点是否存在
		Servo servo = main.getCurrentServo();
		if (servo == null) {
			throw new NullPointerException();
		}

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(main.getShell(), client);
		if (judgeClientActive) {
			return;
		}

		sendMessage(servo, client, main.getShell());
	}

	public void sendMessage(Servo servo, CtrClient client, Shell parent) {
		FTask f = new FTask(parent);
		Task task = f.open(servo, null);
		if (task != null) {
			Message.Builder mb = Message.newBuilder();
			mb.setUserId(servo.getUser().getId());
			mb.setKey(CtrClient.getKey());
			CreateTask.Builder createTask = CreateTask.newBuilder();
			Builder builder = createTask.getItemBuilder();

			builder.setId(task.getId());
			builder.setName(task.getName());
			builder.setStart(task.getStart().getTime());
			builder.setStop(task.getStop().getTime());
			builder.setMonth(task.getMonth());
			builder.setWeek(task.getWeek());
			builder.setDay(task.getDay());
			builder.setInterval(task.getInterval());
			builder.setRepeat(task.getRepeat());
			if(task.getReadModel()!=null)
				builder.setReadModel(task.getReadModel().getValue());
			if(task.getWriteModel()!=null)
				builder.setWriteModel(task.getWriteModel().getValue());
			builder.setReadFeature(task.getReadFeature());
			builder.setWriteFeature(task.getWriteFeature());
			builder.setLimitLower(task.getLimitLower());
			builder.setLimitUpper(task.getLimitUpper());
			builder.setFeed(task.getFeed());
			builder.setFeedLower(task.getFeedLower());
			builder.setFeedUpper(task.getFeedUpper());
			if(task.getReadDeviceIds()!=null) {
				for (String deviceId : task.getReadDeviceIds()) {
					builder.addReads(deviceId);
				}
			}
			if(task.getWriteDeviceIds()!=null) {
				for (String deviceId : task.getWriteDeviceIds()) {
					builder.addWrites(deviceId);
				}
			}
			if (!Tool.isEmpty(task.getRoleId())) {
				builder.setRoleId(task.getRoleId());
			}
			builder.setEnable(task.getEnable());
			builder.setRemark(task.getRemark());
			builder.setCreated(new Date().getTime());
			mb.setCreateTask(createTask);

			client.send(mb.build());

		}
	}
}
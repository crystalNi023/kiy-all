/**
 * 2017年3月6日
 */
package com.kiy.servo.test;

import java.util.Date;
import java.util.Map;

import com.kiy.client.CtrClient;
import com.kiy.client.CtrClientHandler;
import com.kiy.common.Device;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Vendor;
import com.kiy.common.Unit;
import com.kiy.common.User;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.Login;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.SelectDeletes;
import com.kiy.protocol.Messages.SelectDevices;
import com.kiy.protocol.Messages.SelectRoles;
import com.kiy.protocol.Messages.SelectTasks;
import com.kiy.protocol.Messages.SelectUsers;
import com.kiy.protocol.Messages.SelectZones;
import com.kiy.protocol.Units.MDevice;
import com.kiy.protocol.Units.MRole;
import com.kiy.protocol.Units.MTask;
import com.kiy.protocol.Units.MUser;
import com.kiy.protocol.Units.MZone;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestDeviceMessages {

	private static int select = 0;

	public static void main(String[] args) {
		final Servo servo = new Servo();
		servo.setIp("127.0.0.1");
		servo.setPort(1230);
		CtrClient client = new CtrClient(servo);
		servo.setClient(client);

		client.setHandler(new CtrClientHandler() {

			@Override
			public void connected(CtrClient client) {
				System.out.println("CONNECTED");
				Message msg = Message.newBuilder().setLogin(Login.newBuilder().setUsername("test").setPassword(Tool.MD5("abc"))).build();
				client.send(msg);
			}

			@Override
			public void received(CtrClient client, Message msg,Map<Message, Unit> units) {
				System.out.println("RECEIVED");
				System.out.println(msg);

				if (msg.getActionCase() == ActionCase.LOGIN) {
					// 更新数据
					client.send(Message.newBuilder().setSelectZones(SelectZones.newBuilder().setTick(0)).build());
					client.send(Message.newBuilder().setSelectDevices(SelectDevices.newBuilder().setTick(0)).build());
					client.send(Message.newBuilder().setSelectTasks(SelectTasks.newBuilder().setTick(0)).build());
					client.send(Message.newBuilder().setSelectUsers(SelectUsers.newBuilder().setTick(0)).build());
					client.send(Message.newBuilder().setSelectRoles(SelectRoles.newBuilder().setTick(0)).build());
				}
				if (msg.getActionCase() == ActionCase.SELECT_ZONES) {
					SelectZones a = msg.getSelectZones();
					for (MZone m : a.getItemsList()) {
						Zone zone = new Zone();
						zone.setId(m.getId());
						zone.setName(m.getName());
						zone.setParentId(m.getParent());
						zone.setRemark(m.getRemark());
						zone.setCreated(new Date(m.getCreated()));
						zone.setUpdated(new Date(m.getUpdated()));
						servo.add(zone);
					}
					select++;
				}
				if (msg.getActionCase() == ActionCase.SELECT_DEVICES) {
					SelectDevices a = msg.getSelectDevices();
					for (MDevice m : a.getItemsList()) {
						Device device = Device.instance(Vendor.valueOf(m.getVendor()), Kind.valueOf(m.getKind()),Model.valueOf(m.getModel()));
						device.setId(m.getId());
						device.setZoneId(m.getZoneId());
						device.setRelayId(m.getRelayId());
						device.setOwnerId(m.getOwnerId());
						device.setVendor(Vendor.valueOf(m.getVendor()));
						device.setKind(Kind.valueOf(m.getKind()));
						device.setLink(Link.valueOf(m.getLink()));
						device.setSn(m.getSn());
						device.setName(m.getName());
						device.setNumber(m.getNumber());
						device.setPassword(m.getPassword());
						device.setNetworkIp(m.getNetworkIp());
						device.setNetworkPort(m.getNetworkPort());
						device.setSerialPort(m.getSerialPort());
						device.setSerialBaudRate(m.getSerialBaudRate());
						device.setLoad(m.getLoad());
						device.setPower(m.getPower());
						device.setMutual(m.getMutual());
						device.setDelay(m.getDelay());
						device.setAddress(m.getAddress());
						device.setRemark(m.getRemark());
						device.setCreated(new Date(m.getCreated()));
						device.setUpdated(new Date(m.getUpdated()));
						servo.add(device);
					}
					select++;
				}
				if (msg.getActionCase() == ActionCase.SELECT_TASKS) {
					SelectTasks a = msg.getSelectTasks();
					for (MTask m : a.getItemsList()) {
						Task task = new Task();
						task.setId(m.getId());
						task.setName(m.getName());
						task.setStart(new Date(m.getStart()));
						task.setStop(new Date(m.getStop()));
						task.setMonth(m.getMonth());
						task.setWeek(m.getWeek());
						task.setDay(m.getDay());
						task.setInterval(m.getInterval());
						task.setRepeat(m.getRepeat());
						task.setReadFeature(m.getReadFeature());
						task.setWriteFeature(m.getReadFeature());
						task.setLimitUpper(m.getLimitUpper());
						task.setLimitLower(m.getLimitLower());
						task.setFeed(m.getFeed());
						task.setFeedUpper(m.getFeedUpper());
						task.setFeedLower(m.getFeedLower());
						task.setReadModel(Model.valueOf(m.getReadModel()));
						task.setWriteModel(Model.valueOf(m.getWriteModel()));
						task.setRoleId(m.getRoleId());
						task.setRemark(m.getRemark());
						task.setCreated(new Date(m.getCreated()));
						task.setUpdated(new Date(m.getUpdated()));
						servo.add(task);
					}
					select++;
				}
				if (msg.getActionCase() == ActionCase.SELECT_USERS) {
					SelectUsers a = msg.getSelectUsers();
					for (MUser m : a.getItemsList()) {
						User user = new User();
						user.setId(m.getId());
						user.setName(m.getName());
						user.setZoneId(m.getZoneId());
						user.setEnable(m.getEnable());
						user.setPassword(m.getPassword());
						user.setRealname(m.getRealname());
						user.setMobile(m.getMobile());
						user.setPhone(m.getPhone());
						user.setEmail(m.getEmail());
						user.setRemark(m.getRemark());
						user.setCreated(new Date(m.getCreated()));
						user.setUpdated(new Date(m.getUpdated()));
						for (String r : m.getRolesList())
							user.addRole(r);
						servo.add(user);
					}
					select++;
				}
				if (msg.getActionCase() == ActionCase.SELECT_ROLES) {
					SelectRoles a = msg.getSelectRoles();
					for (MRole m : a.getItemsList()) {
						Role role = new Role();
						role.setId(m.getId());
						role.setName(m.getName());
						role.setRemark(m.getRemark());
						role.setCreated(new Date(m.getCreated()));
						role.setUpdated(new Date(m.getUpdated()));
						for (int c : m.getCommandsList())
							role.addCommand(c);
						servo.add(role);
					}
					select++;
				}

				if (select == 5) {
					if (msg.getActionCase() == ActionCase.SELECT_DELETES) {
						SelectDeletes a = msg.getSelectDeletes();
						for (String id : a.getItemsList()) {
							servo.remove(id);
						}
						select++;
					} else {
						client.send(Message.newBuilder().setSelectDeletes(SelectDeletes.newBuilder().setTick(0)));
					}
				}

				//
				if (select == 6) {
					//for (Device d : servo.getDevices()) {
					//	client.send(Message.newBuilder().setCommand(Command.GET_DEVICE_DATE).setGetDeviceDate(GetDeviceDate.newBuilder().setId(d.getId())).build());
					//}
					select++;
				}
				if (select == 7) {
					//for (Device d : servo.getDevices()) {
					//	client.send(Message.newBuilder().setCommand(Command.GET_DEVICE_STATUS).setGetDeviceStatus(GetDeviceStatus.newBuilder().setId(d.getId())).build());
					//}
					select++;
				}
			}

			@Override
			public void disconnected(CtrClient client) {
				System.out.println("DISCONNECTED");
			}

			@Override
			public void excepted(CtrClient client, Throwable cause) {
				System.out.println("EXCEPTED");
				cause.printStackTrace();
			}

			@Override
			public void sent(CtrClient client, Message msg) {
				
			}

		});
		client.start();
		// client.start("192.168.0.100", 1230);

		synchronized (client) {
			try {
				client.wait();
			} catch (

			InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
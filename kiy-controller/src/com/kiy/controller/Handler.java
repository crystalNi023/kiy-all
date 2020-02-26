/**
 * 2017年1月17日
 */
package com.kiy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Display;

import com.google.protobuf.ProtocolStringList;
import com.kiy.client.CtrClient;
import com.kiy.client.CtrClientHandler;
import com.kiy.common.Device;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Role;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Status;
import com.kiy.common.Types.Use;
import com.kiy.common.Types.Vendor;
import com.kiy.common.Unit;
import com.kiy.common.User;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.Connect;
import com.kiy.protocol.Messages.CreateDevice;
import com.kiy.protocol.Messages.CreateLinkage;
import com.kiy.protocol.Messages.CreateRole;
import com.kiy.protocol.Messages.CreateScene;
import com.kiy.protocol.Messages.CreateTask;
import com.kiy.protocol.Messages.CreateUser;
import com.kiy.protocol.Messages.CreateZone;
import com.kiy.protocol.Messages.Login;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.MovePosition;
import com.kiy.protocol.Messages.ReadDeviceStatus;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.SelectDeletes;
import com.kiy.protocol.Messages.SelectDevices;
import com.kiy.protocol.Messages.SelectLinkages;
import com.kiy.protocol.Messages.SelectRoles;
import com.kiy.protocol.Messages.SelectScenes;
import com.kiy.protocol.Messages.SelectTasks;
import com.kiy.protocol.Messages.SelectUsers;
import com.kiy.protocol.Messages.SelectZones;
import com.kiy.protocol.Messages.SpeechRecognition;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.protocol.Messages.UpdateLinkage;
import com.kiy.protocol.Messages.UpdateLinkageStatus;
import com.kiy.protocol.Messages.UpdateRole;
import com.kiy.protocol.Messages.UpdateScene;
import com.kiy.protocol.Messages.UpdateSceneStatus;
import com.kiy.protocol.Messages.UpdateTask;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Messages.UpdateZone;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDevice;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.protocol.Units.MLinkage;
import com.kiy.protocol.Units.MLinkageDevice;
import com.kiy.protocol.Units.MRole;
import com.kiy.protocol.Units.MScene;
import com.kiy.protocol.Units.MSceneDevice;
import com.kiy.protocol.Units.MTask;
import com.kiy.protocol.Units.MUser;
import com.kiy.protocol.Units.MZone;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class Handler implements CtrClientHandler {
	/**
	 * 禁止将更新窗口显示的方法写在此类,在此类添加成员变量和方法时请事先商讨.
	 */
	private FMain main;
	private ServoManager manager;

	public Handler(FMain fm, ServoManager sm) {
		main = fm;
		manager = sm;
	}

	@Override
	public void connected(CtrClient client) {
		Servo servo = client.getServo();
		// 先判断是否时云端登录
		if (servo.isCloudLogin()) {
			// 发送登录请求
			Message.Builder msg = Message.newBuilder();
			msg.setKey(CtrClient.getKey());
			Connect.Builder connect = Connect.newBuilder();
			connect.setKey(servo.getLoginKey());
			connect.setName("SMART_CITY_CONTROLLER");
			msg.setConnect(connect);
			client.send(msg.build());
		} else {
			// 登录
			Message msg = Message.newBuilder().setKey(CtrClient.getKey()).setLogin(Login.newBuilder().setUsername(servo.getUsername()).setPassword(servo.getPassword())).build();
			client.send(msg);
		}

	}

	@Override
	public void received(CtrClient client, Message msg, Map<Message, Unit> units) {
		if (units == null)
			units = new HashMap<>();
		Servo servo = client.getServo();
		/**
		 * 此方法接收来自服务器消息,仅处理与实体对象有关的更新操作; 与窗口显示及消息提示相关操作只能在窗口处理,禁止在此处处理
		 */
		switch (msg.getActionCase()) {
				
			case CONNECT: {
				if (msg.getResult() == Result.SUCCESS) {
					Connect connect = msg.getConnect();
					servo.setName(connect.getName());
					// 连接成功再登录
					if (servo.isCloudLogin()) {
						// 登录
						Message msg1 = Message.newBuilder().setKey(CtrClient.getKey()).setLogin(Login.newBuilder().setUsername(servo.getUsername()).setPassword(servo.getPassword())).build();
						client.send(msg1);
					}
				} else {
					// 连接请求失败 无处理
				}
				break;
			}
			case LOGIN: {
				Login action = msg.getLogin();
				if (msg.getResult() == Result.SUCCESS) {
					User user = servo.getUser(action.getId());
					if (user == null) {
						user = new User();
						user.setId(action.getId());
						servo.add(user);
					}
					user.setName(action.getUsername());
					user.setRealname(action.getRealname());
					user.setNickname(action.getNickname());
					user.setMobile(action.getMobile());
					user.setPhone(action.getPhone());
					user.setEmail(action.getEmail());
					user.setRemark(action.getRemark());
					user.setCreated(new Date(action.getCreated()));
					user.setUpdated(new Date(action.getUpdated()));
					servo.setUser(user);
					// 更新数据
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectZones(SelectZones.newBuilder().setTick(servo.getTickLong(Servo.ZONE))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectDevices(SelectDevices.newBuilder().setTick(servo.getTickLong(Servo.DEVICE))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectTasks(SelectTasks.newBuilder().setTick(servo.getTickLong(Servo.TASK))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectUsers(SelectUsers.newBuilder().setTick(servo.getTickLong(Servo.USER))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectRoles(SelectRoles.newBuilder().setTick(servo.getTickLong(Servo.ROLE))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectScenes(SelectScenes.newBuilder().setTick(servo.getTickLong(Servo.SCENE))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectLinkages(SelectLinkages.newBuilder().setTick(servo.getTickLong(Servo.LINKAGE))).build());
					client.send(Message.newBuilder().setUserId(user.getId()).setKey(CtrClient.getKey()).setSelectDeletes(SelectDeletes.newBuilder().setTick(servo.getTickLong())).build());
				}
				break;
			}
			case SELECT_ZONES: {
				SelectZones action = msg.getSelectZones();
				for (MZone z : action.getItemsList()) {
					Zone zone = servo.getZone(z.getId());
					if (zone == null) {
						zone = new Zone();
						zone.setId(z.getId());
						zone.setUpdated(new Date(z.getUpdated()));
						servo.add(zone);
					}
					if (!Tool.isEmpty(z.getParent()))
						zone.setParentId(z.getParent());
					zone.setName(z.getName());
					zone.setRemark(z.getRemark());
					zone.setUpdated(new Date(z.getUpdated()));
					zone.setCreated(new Date(z.getCreated()));
				}
				break;
			}
			case SELECT_DEVICES: {
				SelectDevices action = msg.getSelectDevices();
				for (MDevice d : action.getItemsList()) {
					Device device = servo.getDevice(d.getId());
					
					if (device == null) {
						device = Device.instance(Vendor.valueOf(d.getVendor()), Kind.valueOf(d.getKind()), Model.valueOf(d.getModel()));
						device.setId(d.getId());
						device.setUpdated(new Date(d.getUpdated()));
						servo.add(device);
					}
					if (!Tool.isEmpty(d.getZoneId()))
						device.setZoneId(d.getZoneId());
					if (!Tool.isEmpty(d.getRelayId()))
						device.setRelayId(d.getRelayId());
					device.setSn(d.getSn());
					device.setMutual(d.getMutual());
					device.setDelay(d.getDelay());
					device.setAddress(d.getAddress());
					device.setName(d.getName());
					device.setVendor(Vendor.valueOf(d.getVendor()));
					device.setKind(Kind.valueOf(d.getKind()));
					device.setModel(Model.valueOf(d.getModel()));
					device.setLink(Link.valueOf(d.getLink()));
					device.setUse(Use.valueOf(d.getUse()));
					device.setNumber(d.getNumber());
					device.setUsername(d.getUsername());
					device.setPassword(d.getPassword());
					device.setNetworkIp(d.getNetworkIp());
					device.setNetworkPort(d.getNetworkPort());
					device.setSerialPort(d.getSerialPort());
					device.setSerialBaudRate(d.getSerialBaudRate());
					device.setRemark(d.getRemark());
					device.setPower(d.getPower());
					device.setLoad(d.getLoad());
					device.setInstalled(new Date(d.getInstalled()));
					device.setProduced(new Date(d.getProduced()));
					device.setAltitude(d.getAltitude());
					device.setLatitude(d.getLatitude());
					device.setLongitude(d.getLongitude());
					device.setPhaseCheck(d.getPhaseCheck());
					device.setPhasePower(d.getPhasePower());
					device.setNotice(d.getNotice());
					device.setDetect(d.getDetect());
					device.setCreated(new Date(d.getCreated()));
					device.setUpdated(new Date(d.getUpdated()));
				}
				break;
			}
			case SELECT_TASKS: {
				SelectTasks action = msg.getSelectTasks();
				for (MTask t : action.getItemsList()) {
					Task task = servo.getTask(t.getId());
					if (task == null) {
						task = new Task();
						task.setUpdated(new Date(t.getUpdated()));
						task.setId(t.getId());
						servo.add(task);
					}
					task.setName(t.getName());
					task.setStart(new Date(t.getStart()));
					task.setStop(new Date(t.getStop()));
					task.setMonth(t.getMonth());
					task.setWeek(t.getWeek());
					task.setDay(t.getDay());
					task.setInterval(t.getInterval());
					task.setRepeat(t.getRepeat());
					task.setReadModel(Model.valueOf(t.getReadModel()));
					task.setWriteModel(Model.valueOf(t.getWriteModel()));
					task.setReadFeature(t.getReadFeature());
					task.setWriteFeature(t.getWriteFeature());
					task.setLimitLower(t.getLimitLower());
					task.setLimitUpper(t.getLimitUpper());
					task.setFeed(t.getFeed());
					task.setFeedLower(t.getFeedLower());
					task.setFeedUpper(t.getFeedUpper());
					ProtocolStringList readsList = t.getReadsList();
					for (String id : readsList) {
						task.addReadDeviceById(id);
					}
					ProtocolStringList writesList = t.getWritesList();
					for (String id : writesList) {
						task.addWriteDeviceById(id);
					}
					if (!Tool.isEmpty(t.getRoleId()))
						task.setRoleId(t.getRoleId());
					task.setEnable(t.getEnable());
					task.setRemark(t.getRemark());
					task.setUpdated(new Date(t.getUpdated()));
					task.setCreated(new Date(t.getCreated()));
				}
				break;
			}
			case SELECT_ROLES: {
				SelectRoles action = msg.getSelectRoles();
				for (MRole r : action.getItemsList()) {
					Role role = servo.getRole(r.getId());
					if (role == null) {
						role = new Role();
						role.setUpdated(new Date(r.getUpdated()));
						role.setId(r.getId());
						servo.add(role);
					}
					role.setName(r.getName());
					role.setRemark(r.getRemark());
					for (Integer value : r.getCommandsList()) {
						role.addCommand(value);
					}
					role.setUpdated(new Date(r.getUpdated()));
					role.setCreated(new Date(r.getCreated()));
				}
				break;
			}
			case SELECT_USERS: {
				SelectUsers action = msg.getSelectUsers();
				for (MUser u : action.getItemsList()) {
					User user = servo.getUser(u.getId());
					if (user == null) {
						user = new User();
						user.setId(u.getId());
						user.setUpdated(new Date(u.getUpdated()));
						servo.add(user);
					}
					if (!Tool.isEmpty(u.getZoneId()))
						user.setZoneId(u.getZoneId());
					user.setMobile(u.getMobile());
					user.setName(u.getName());
					user.setPassword(u.getPassword());
					user.setPhone(u.getPhone());
					user.setEnable(u.getEnable());
					user.setEmail(u.getEmail());
					user.setRealname(u.getRealname());
					user.setNickname(u.getNickname());
					user.setRemark(u.getRemark());
					for (String roleId : u.getRolesList()) {
						user.addRole(roleId);
					}
					user.setUpdated(new Date(u.getUpdated()));
					user.setCreated(new Date(u.getCreated()));
				}
				break;
			}
			case SELECT_SCENES: {
				SelectScenes action = msg.getSelectScenes();
				for (MScene s : action.getItemsList()) {
					Scene scene = servo.getScene(s.getId());
					if (scene == null) {
						scene = new Scene();
						scene.setId(s.getId());
						scene.setUpdated(new Date(s.getUpdated()));
						servo.add(scene);
					}
					scene.setName(s.getName());
					scene.setRemark(s.getRemark());
					scene.setSwitchStatu(s.getSwitch());
					List<MSceneDevice> openDevicesList = s.getOpenDevicesList();
					for (MSceneDevice openDevice : openDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(openDevice.getDeviceId());
						sceneDevice.setSwitchStatus(openDevice.getSwitch());
						sceneDevice.setFeatureIndex(openDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(openDevice.getFeatureValue());
						scene.addOpenSceneDevice(sceneDevice);
					}
					List<MSceneDevice> offDevicesList = s.getOffDevicesList();
					for (MSceneDevice offDevice : offDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(offDevice.getDeviceId());
						sceneDevice.setSwitchStatus(offDevice.getSwitch());
						sceneDevice.setFeatureIndex(offDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(offDevice.getFeatureValue());
						scene.addOffSceneDevice(sceneDevice);
					}
					scene.setCreated(new Date(s.getCreated()));
					scene.setUpdated(new Date(s.getUpdated()));
				}
				break;
			}
			case SELECT_LINKAGES: {
				SelectLinkages action = msg.getSelectLinkages();
				for (MLinkage l : action.getItemsList()) {
					Linkage linkage = servo.getLinkage(l.getId());
					if (linkage == null) {
						linkage = new Linkage();
						linkage.setId(l.getId());
						servo.add(linkage);
					}
					linkage.setName(l.getName());
					linkage.setEnable(l.getEnable());
					linkage.setRemark(l.getRemark());
					for (MLinkageDevice mLinkageDevice : l.getLinkageDeviceList()) {
						LinkageDevice linkageDevice = new LinkageDevice();
						linkageDevice.setId(mLinkageDevice.getId());
						linkageDevice.setLinkageId(mLinkageDevice.getLinkageId());
						linkageDevice.setDeviceId(mLinkageDevice.getDeviceId());
						linkageDevice.setPriority(mLinkageDevice.getPriority());
						linkageDevice.setFeatureIndex(mLinkageDevice.getFeatureIndex());
						linkageDevice.setFeatureValue(mLinkageDevice.getFeatureValue());
						linkageDevice.setSecs(mLinkageDevice.getSecs());
						linkage.addLinkageDevice(linkageDevice);
					}
				}
				break;
			}
			case SELECT_DELETES: {
				SelectDeletes action = msg.getSelectDeletes();
				for (String id : action.getItemsList()) {
					servo.remove(id);
				}
				break;
			}
			case READ_DEVICE_STATUS: {
				ReadDeviceStatus readDeviceStatus = msg.getReadDeviceStatus();
				MDeviceStatus deviceStatus = readDeviceStatus.getItem();
				Device device = servo.getDevice(deviceStatus.getDeviceId());
				if (device != null) {
					device.setStatus(Status.valueOf(deviceStatus.getStatus()));
					device.setTickStatus(deviceStatus.getCreated());
					if (device.getFeatures() == null) {
						// 无处理
					} else {
						Map<Integer, Integer> map = deviceStatus.getItemsMap();
						for (Integer index : map.keySet()) {
							device.getFeature(index).setValue(map.get(index));
						}
					}
				} else {
				}
				units.put(msg, device);
				break;
			}
			case WRITE_DEVICE_STATUS: {
				WriteDeviceStatus writeDeviceStatus = msg.getWriteDeviceStatus();
				MDeviceStatus deviceStatus = writeDeviceStatus.getItem();
				Device device = servo.getDevice(deviceStatus.getDeviceId());
				if (device != null) {
					device.setStatus(Status.valueOf(deviceStatus.getStatus()));
					device.setTickStatus(deviceStatus.getCreated());
					Map<Integer, Integer> map = deviceStatus.getItemsMap();
					for (Integer index : map.keySet()) {
						device.getFeature(index).setValue(map.get(index));
					}
					units.put(msg, device);
				}
				break;
			}
			case CREATE_ZONE: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateZone createZone = msg.getCreateZone();
					Zone zone = new Zone();
					MZone mZone = createZone.getItem();
					zone.setId(mZone.getId());
					if (!Tool.isEmpty(mZone.getParent()))
						zone.setParentId(mZone.getParent());
					zone.setName(mZone.getName());
					zone.setRemark(mZone.getRemark());
					zone.setCreated(new Date(mZone.getCreated()));
					zone.setUpdated(zone.getCreated());
					servo.add(zone);
					units.put(msg, zone);
				}
				break;
			}
			case CREATE_TASK: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateTask createTask = msg.getCreateTask();
					MTask mTask = createTask.getItem();
					Task task = new Task();
					task.setId(mTask.getId()); 
					task.setName(mTask.getName());
					task.setStart(new Date(mTask.getStart()));
					task.setStop(new Date(mTask.getStop()));
					task.setMonth(mTask.getMonth());
					task.setWeek(mTask.getWeek());
					task.setDay(mTask.getDay());
					task.setInterval(mTask.getInterval());
					task.setRepeat(mTask.getRepeat());
					task.setReadModel(Model.valueOf(mTask.getReadModel()));
					task.setWriteModel(Model.valueOf(mTask.getWriteModel()));
					task.setReadFeature(mTask.getReadFeature());
					task.setWriteFeature(mTask.getWriteFeature());
					task.setLimitLower(mTask.getLimitLower());
					task.setLimitUpper(mTask.getLimitUpper());
					task.setFeed(mTask.getFeed());
					task.setFeedLower(mTask.getFeedLower());
					task.setFeedUpper(mTask.getFeedUpper());
					if (mTask.getReadsList() != null) {
						for (String deviceId : mTask.getReadsList()) {
							task.addReadDeviceById(deviceId);
						}
					}
					if (mTask.getWritesList() != null) {
						for (String deviceId : mTask.getWritesList()) {
							task.addWriteDeviceById(deviceId);
						}
					}
					task.setEnable(mTask.getEnable());
					task.setRoleId(mTask.getRoleId());
					task.setRemark(mTask.getRemark());
					task.setCreated(new Date(mTask.getCreated()));
					task.setUpdated(task.getCreated());
					servo.add(task);
					units.put(msg, task);
				}
				break;
			}
			case CREATE_SCENE: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateScene createScene = msg.getCreateScene();
					MScene mScene = createScene.getItem();
					Scene scene = new Scene();
					scene.setId(mScene.getId());
					scene.setName(mScene.getName());
					scene.setSwitchStatu(mScene.getSwitch());
					scene.setRemark(mScene.getRemark());
					scene.setCreated(new Date(mScene.getCreated()));
					scene.setUpdated(scene.getCreated());
					List<MSceneDevice> offDevicesList = mScene.getOffDevicesList();
					for (MSceneDevice mSceneDevice : offDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(mSceneDevice.getDeviceId());
						sceneDevice.setSwitchStatus(mSceneDevice.getSwitch());
						sceneDevice.setFeatureIndex(mSceneDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(mSceneDevice.getFeatureValue());
						scene.addOffSceneDevice(sceneDevice);
					}
					List<MSceneDevice> openDevicesList = mScene.getOpenDevicesList();
					for (MSceneDevice mSceneDevice : openDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(mSceneDevice.getDeviceId());
						sceneDevice.setSwitchStatus(mSceneDevice.getSwitch());
						sceneDevice.setFeatureIndex(mSceneDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(mSceneDevice.getFeatureValue());
						scene.addOpenSceneDevice(sceneDevice);
					}
					servo.add(scene);
					units.put(msg, scene);
				}
				break;
			}
			case CREATE_LINKAGE: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateLinkage createLinkage = msg.getCreateLinkage();
					MLinkage mLinkage = createLinkage.getItem();
					Linkage linkage = new Linkage();
					linkage.setId(mLinkage.getId());
					linkage.setName(mLinkage.getName());
					linkage.setEnable(mLinkage.getEnable());
					linkage.setRemark(mLinkage.getRemark());
					List<MLinkageDevice> linkageDeviceList = mLinkage.getLinkageDeviceList();
					for (MLinkageDevice m : linkageDeviceList) {
						LinkageDevice linkageDevice = new LinkageDevice();
						linkageDevice.setId(m.getId());
						linkageDevice.setLinkageId(m.getLinkageId());
						linkageDevice.setDeviceId(m.getDeviceId());
						linkageDevice.setPriority(m.getPriority());
						linkageDevice.setFeatureIndex(m.getFeatureIndex());
						linkageDevice.setFeatureValue(m.getFeatureValue());
						linkageDevice.setSecs(m.getSecs());
						linkage.addLinkageDevice(linkageDevice);
					}
					servo.add(linkage);
					units.put(msg, linkage);
				}
				break;
			}
			case CREATE_DEVICE: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateDevice createDevice = msg.getCreateDevice();
					MDevice mDevice = createDevice.getItem();
					Device device = Device.instance(Vendor.valueOf(mDevice.getVendor()), Kind.valueOf(mDevice.getKind()), Model.valueOf(mDevice.getModel()));
					device.setId(mDevice.getId());
					if (!Tool.isEmpty(mDevice.getZoneId()))
						device.setZoneId(mDevice.getZoneId());
					if (!Tool.isEmpty(mDevice.getRelayId()))
						device.setRelayId(mDevice.getRelayId());
					device.setSn(mDevice.getSn());
					device.setMutual(mDevice.getMutual());
					device.setDelay(mDevice.getDelay());
					device.setAddress(mDevice.getAddress());
					device.setName(mDevice.getName());
					device.setLink(Link.valueOf(mDevice.getLink()));
					device.setUse(Use.valueOf(mDevice.getUse()));
					device.setNumber(mDevice.getNumber());
					device.setUsername(mDevice.getUsername());
					device.setPassword(mDevice.getPassword());
					device.setNetworkIp(mDevice.getNetworkIp());
					device.setNetworkPort(mDevice.getNetworkPort());
					device.setSerialPort(mDevice.getSerialPort());
					device.setSerialBaudRate(mDevice.getSerialBaudRate());
					device.setRemark(mDevice.getRemark());
					device.setPower(mDevice.getPower());
					device.setLoad(mDevice.getLoad());
					device.setCreated(new Date(mDevice.getCreated()));
					device.setInstalled(new Date(mDevice.getInstalled()));
					device.setProduced(new Date(mDevice.getProduced()));
					device.setUpdated(device.getCreated());
					device.setNotice(mDevice.getNotice());
					device.setDetect(mDevice.getDetect());
					device.setPhaseCheck(mDevice.getPhaseCheck());
					device.setPhasePower(mDevice.getPhasePower());

					servo.add(device);
					units.put(msg, device);
				}
				break;
			}
			case CREATE_USER: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateUser createUser = msg.getCreateUser();
					MUser mUser = createUser.getItem();
					User user = new User();
					user.setId(mUser.getId());
					user.setEmail(mUser.getEmail());
					user.setMobile(mUser.getMobile());
					user.setName(mUser.getName());
					user.setPassword(mUser.getPassword());
					user.setPhone(mUser.getPhone());
					user.setEnable(mUser.getEnable());
					if (!Tool.isEmpty(mUser.getZoneId()))
						user.setZoneId(mUser.getZoneId());
					user.setRealname(mUser.getRealname());
					user.setNickname(mUser.getNickname());
					user.setRemark(mUser.getRemark());
					user.setCreated(new Date(mUser.getCreated()));
					user.setUpdated(user.getCreated());
					for (String roleId : mUser.getRolesList()) {
						user.addRole(roleId);
					}
					servo.add(user);
					units.put(msg, user);
				}
				break;
			}
			case CREATE_ROLE: {
				if (msg.getResult() == Result.SUCCESS) {
					CreateRole createRole = msg.getCreateRole();
					MRole mRole = createRole.getItem();
					Role role = new Role();
					role.setId(mRole.getId());
					role.setName(mRole.getName());
					role.setRemark(mRole.getRemark());
					role.setCreated(new Date(mRole.getCreated()));
					role.setUpdated(role.getCreated());
					for (Integer value : mRole.getCommandsList()) {
						ActionCase c = ActionCase.forNumber(value);
						if (c != null)
							role.addCommand(c.getNumber());
					}
					servo.add(role);
					units.put(msg, role);
				}
				break;
			}
			case UPDATE_USER: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateUser updateUser = msg.getUpdateUser();
					MUser mUser = updateUser.getItem();
					User user = servo.getUser(mUser.getId());

					if (user == null) {
						System.out.println("this user dose not belong to this servo");
					}

					if (!Tool.isEmpty(mUser.getZoneId()))
						user.setZoneId(mUser.getZoneId());
					user.setEmail(mUser.getEmail());
					user.setMobile(mUser.getMobile());
					user.setName(mUser.getName());
					user.setPassword(mUser.getPassword());
					user.setPhone(mUser.getPhone());
					user.setEnable(mUser.getEnable());
					user.setRealname(mUser.getRealname());
					user.setNickname(mUser.getNickname());
					user.setRemark(mUser.getRemark());
					user.setUpdated(user.getCreated());
					user.offAllRoles();
					for (String roleId : mUser.getRolesList()) {
						user.addRole(roleId);
					}
					servo.updateTick(user);
					units.put(msg, user);
				}
				break;
			}
			case UPDATE_ROLE: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateRole updateRole = msg.getUpdateRole();
					MRole mRole = updateRole.getItem();
					Role role = servo.getRole(mRole.getId());
					if (role == null) {
						System.out.println("this role dose not belong to this servo");
					}
					role.setName(mRole.getName());
					role.setRemark(mRole.getRemark());
					role.setUpdated(role.getCreated());
					role.offAllCommands();
					for (Integer value : mRole.getCommandsList()) {
						role.addCommand(value);
					}

					servo.updateTick(role);
					units.put(msg, role);
				}
				break;
			}
			case MOVE_POSITION: {
				if (msg.getResult() == Result.SUCCESS) {
					MovePosition movePosition = msg.getMovePosition();
					Device device = servo.getDevice(movePosition.getId());

					if (device == null) {
						System.out.println("this device dose not belong to this servo");
					}
					device.setLongitude(movePosition.getLongitude());
					device.setLatitude(movePosition.getLatitude());
					device.setUpdated(new Date(movePosition.getUpdated()));
					units.put(msg, device);
				}
				break;
			}

			case UPDATE_ZONE: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateZone updateZone = msg.getUpdateZone();
					MZone mZone = updateZone.getItem();
					Zone zone = servo.getZone(mZone.getId());
					if (zone == null) {
						System.out.println("this zone dose not belong to this servo");
					}
					if (!Tool.isEmpty(mZone.getParent()))
						zone.setParentId(mZone.getParent());
					zone.setName(mZone.getName());
					zone.setRemark(mZone.getRemark());
					zone.setUpdated(zone.getUpdated());
					servo.updateTick(zone);
					units.put(msg, zone);
				}
				break;
			}
			case UPDATE_TASK: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateTask updateTask = msg.getUpdateTask();
					MTask mTask = updateTask.getItem();
					Task task = servo.getTask(mTask.getId());

					if (task == null) {
						System.out.println("this task dose not belong to this servo");
					}

					task.setName(mTask.getName());
					task.setStart(new Date(mTask.getStart()));
					task.setStop(new Date(mTask.getStop()));
					task.setMonth(mTask.getMonth());
					task.setWeek(mTask.getWeek());
					task.setDay(mTask.getDay());
					task.setInterval(mTask.getInterval());
					task.setRepeat(mTask.getRepeat());
					task.setReadModel(Model.valueOf(mTask.getReadModel()));
					task.setWriteModel(Model.valueOf(mTask.getWriteModel()));
					task.setReadFeature(mTask.getReadFeature());
					task.setWriteFeature(mTask.getWriteFeature());
					task.setLimitLower(mTask.getLimitLower());
					task.setLimitUpper(mTask.getLimitUpper());
					task.setFeed(mTask.getFeed());
					task.setFeedLower(mTask.getFeedLower());
					task.setFeedUpper(mTask.getFeedUpper());
					task.removeAllReadDevices();
					task.removeAllWriteDevices();

					ProtocolStringList readsList = mTask.getReadsList();
					for (String id : readsList) {
						task.addReadDeviceById(id);
					}

					ProtocolStringList writesList = mTask.getWritesList();
					for (String id : writesList) {
						task.addWriteDeviceById(id);
					}
					if (!Tool.isEmpty(mTask.getRoleId()))
						task.setRoleId(mTask.getRoleId());
					task.setEnable(mTask.getEnable());
					task.setRemark(mTask.getRemark());
					task.setUpdated(new Date(mTask.getUpdated()));
					servo.updateTick(task);
					units.put(msg, task);
				}
				break;
			}
			case UPDATE_SCENE: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateScene updateScene = msg.getUpdateScene();
					MScene mScene = updateScene.getItem();
					Scene scene = servo.getScene(mScene.getId());
					if (scene == null)
						System.out.println("this scene dose not belong to this servo");
					scene.setName(mScene.getName());
					scene.setSwitchStatu(mScene.getSwitch());
					scene.setUpdated(new Date(mScene.getUpdated()));
					scene.setRemark(mScene.getRemark());
					scene.removeAllOffSceneDevice();
					scene.removeAllOpenSceneDevice();
					List<MSceneDevice> offDevicesList = mScene.getOffDevicesList();
					for (MSceneDevice mSceneDevice : offDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(mSceneDevice.getDeviceId());
						sceneDevice.setSwitchStatus(mSceneDevice.getSwitch());
						sceneDevice.setFeatureIndex(mSceneDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(mSceneDevice.getFeatureValue());
						scene.addOffSceneDevice(sceneDevice);
					}
					List<MSceneDevice> openDevicesList = mScene.getOpenDevicesList();
					for (MSceneDevice mSceneDevice : openDevicesList) {
						SceneDevice sceneDevice = new SceneDevice();
						sceneDevice.setDeviceId(mSceneDevice.getDeviceId());
						sceneDevice.setSwitchStatus(mSceneDevice.getSwitch());
						sceneDevice.setFeatureIndex(mSceneDevice.getFeatureIndex());
						sceneDevice.setFeatureValue(mSceneDevice.getFeatureValue());
						scene.addOpenSceneDevice(sceneDevice);
					}
					servo.updateTick(scene);
					units.put(msg, scene);
				}
				break;
			}
			case UPDATE_LINKAGE:{
				if (msg.getResult() == Result.SUCCESS) {
					UpdateLinkage updateLinkage = msg.getUpdateLinkage();
					MLinkage mLinkage = updateLinkage.getItem();
					Linkage linkage = servo.getLinkage(mLinkage.getId());
					if (linkage!=null)
						System.out.println("this linkage dose not belong to this servo");
					linkage.setName(mLinkage.getName());
					linkage.setUpdated(new Date(mLinkage.getUpdated()));
					linkage.setRemark(mLinkage.getRemark());
					linkage.removeAllLinkageDevice();
					List<MLinkageDevice> linkageDevices = mLinkage.getLinkageDeviceList();
					for (MLinkageDevice mLinkageDevice : linkageDevices) {
						LinkageDevice linkageDevice = new LinkageDevice();
						linkageDevice.setDeviceId(mLinkageDevice.getDeviceId());
						linkageDevice.setLinkageId(mLinkageDevice.getLinkageId());
						linkageDevice.setPriority(mLinkageDevice.getPriority());
						linkageDevice.setFeatureIndex(mLinkageDevice.getFeatureIndex());
						linkageDevice.setFeatureValue(mLinkageDevice.getFeatureValue());
						linkageDevice.setSecs(mLinkageDevice.getSecs());
						linkage.addLinkageDevice(linkageDevice);
					}
					servo.updateTick(linkage);
					units.put(msg, linkage);
				}
				break;
			}
			case UPDATE_LINKAGE_STATUS:{
				if (msg.getResult() == Result.SUCCESS) {
					UpdateLinkageStatus updateLinkageStatus = msg.getUpdateLinkageStatus();
					Linkage linkage = servo.getLinkage(updateLinkageStatus.getId());
					linkage.setEnable(updateLinkageStatus.getSwitchStatus());
					servo.updateTick(linkage);
					units.put(msg, linkage);
				}
				break;
			}
			case UPDATE_SCENE_STATUS: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateSceneStatus updateSceneStatus = msg.getUpdateSceneStatus();
					Scene scene = servo.getScene(updateSceneStatus.getId());
					scene.setSwitchStatu(updateSceneStatus.getSwitchStatus());
					servo.updateTick(scene);
					units.put(msg, scene);
				}
				break;
			}
			case UPDATE_DEVICE: {
				if (msg.getResult() == Result.SUCCESS) {
					UpdateDevice updateDevice = msg.getUpdateDevice();
					MDevice mDevice = updateDevice.getItem();
					Device device = servo.getDevice(mDevice.getId());
					if (device == null) {
						System.out.println("this device dose not belong to this servo");
					}

					if (!Tool.isEmpty(mDevice.getZoneId()))
						device.setZoneId(mDevice.getZoneId());
					if (!Tool.isEmpty(mDevice.getRelayId()))
						device.setRelayId(mDevice.getRelayId());
					device.setSn(mDevice.getSn());
					device.setMutual(mDevice.getMutual());
					device.setDelay(mDevice.getDelay());
					device.setAddress(mDevice.getAddress());
					device.setName(mDevice.getName());
					device.setVendor(Vendor.valueOf(mDevice.getVendor()));
					device.setKind(Kind.valueOf(mDevice.getKind()));
					device.setModel(Model.valueOf(mDevice.getModel()));
					device.setLink(Link.valueOf(mDevice.getLink()));
					device.setUse(Use.valueOf(mDevice.getUse()));
					device.setNumber(mDevice.getNumber());
					device.setUsername(mDevice.getUsername());
					device.setPassword(mDevice.getPassword());
					device.setNetworkIp(mDevice.getNetworkIp());
					device.setNetworkPort(mDevice.getNetworkPort());
					device.setSerialPort(mDevice.getSerialPort());
					device.setSerialBaudRate(mDevice.getSerialBaudRate());
					device.setRemark(mDevice.getRemark());
					device.setUpdated(device.getCreated());
					device.setPower(mDevice.getPower());
					device.setLoad(mDevice.getLoad());
					device.setInstalled(new Date(mDevice.getInstalled()));
					device.setProduced(new Date(mDevice.getProduced()));
					device.setPhasePower(mDevice.getPhasePower());
					device.setPhaseCheck(mDevice.getPhaseCheck());
					device.setNotice(mDevice.getNotice());
					device.setDetect(mDevice.getDetect());
					servo.updateTick(device);
					units.put(msg, device);
				}
				break;
			}
			case DELETE_ZONE: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (Zone) servo.remove(msg.getDeleteZone().getId()));
				}
				break;
			}
			case DELETE_DEVICE: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (Device) servo.remove(msg.getDeleteDevice().getId()));
				}
				break;
			}
			case DELETE_TASK: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (Task) servo.remove(msg.getDeleteTask().getId()));
				}
				break;
			}
			case DELETE_SCENE: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (Scene) servo.remove(msg.getDeleteScene().getId()));
				}
				break;
			}
			case DELETE_USER: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (User) servo.remove(msg.getDeleteUser().getId()));
				}
				break;
			}
			case DELETE_ROLE: {
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, (Role) servo.remove(msg.getDeleteRole().getId()));
				}
				break;
			}
			case DELETE_LINKAGE:{
				if (msg.getResult() == Result.SUCCESS) {
					units.put(msg, servo.remove(msg.getDeleteLinkage().getId()));
				}
				break;
			}
			case FILE_LIST: {
				break;
			}
			case FILE_UPLOAD: {
				break;
			}
			case FILE_DOWNLOAD: {
				break;
			}
			case NOTICE: {
//				MNotice notice = msg.getNotice();
//				com.kiy.servo.Log.debug("notice:   "+notice.getContent());
//				units.put(msg, null);
				break;
			}
//			case OFFLINE_NOTICE_ACTIVE:{
//				System.err.println("收到OFFLINE_NOTICE_ACTIVE反馈消息");
//				break;
//			}
			case CREATE_MAINTAIN: {
				break;
			}
			case UPDATE_MAINTAIN: {
				break;
			}
			case SPEECH_RECOGNITION: {
				String result = msg.getSpeechRecognition().getResult();
				System.err.println("收到语音反馈:" + result);
				break;
			}
			default:
				break;
		}
		manager.received(servo, msg, units);
		main.addMessage(servo, msg, units);
	}

	@Override
	public void disconnected(final CtrClient client) {
		client.stop();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				main.getTreeZoneView().update(client.getServo());
				main.getTreeRelayView().update(client.getServo());
			}
		});
	}

	@Override
	public void excepted(CtrClient client, Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void sent(CtrClient client, Message msg) {
		Servo servo = client.getServo();
		main.addMessage(servo, msg, null);

//		System.out.println("发送 : " + msg.toString());

		if (msg.getActionCase() == ActionCase.SPEECH_RECOGNITION) {
			SpeechRecognition speechRecognition = msg.getSpeechRecognition();
			System.err.println("发送语音:" + speechRecognition);
		}
	}
}
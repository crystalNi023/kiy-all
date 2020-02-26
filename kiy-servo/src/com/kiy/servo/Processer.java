/**
 * 2017年8月6日
 */
package com.kiy.servo;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.internal.ConcurrentSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.ByteString;
import com.kiy.common.Device;
import com.kiy.common.DeviceLogin;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Maintain;
import com.kiy.common.Notice;
import com.kiy.common.Push;
import com.kiy.common.Question;
import com.kiy.common.Role;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Origin;
import com.kiy.common.Types.Repair;
import com.kiy.common.Types.Status;
import com.kiy.common.Types.Use;
import com.kiy.common.Types.Vendor;
import com.kiy.common.ULog;
import com.kiy.common.User;
import com.kiy.common.Weather;
import com.kiy.common.XMCamera;
import com.kiy.common.XMDevice;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.ChangePhone;
import com.kiy.protocol.Messages.CreateDevice;
import com.kiy.protocol.Messages.CreateLinkage;
import com.kiy.protocol.Messages.CreateMaintain;
import com.kiy.protocol.Messages.CreateRole;
import com.kiy.protocol.Messages.CreateScene;
import com.kiy.protocol.Messages.CreateTask;
import com.kiy.protocol.Messages.CreateUser;
import com.kiy.protocol.Messages.CreateXMDevice;
import com.kiy.protocol.Messages.CreateZone;
import com.kiy.protocol.Messages.DeleteDevice;
import com.kiy.protocol.Messages.DeleteLinkage;
import com.kiy.protocol.Messages.DeleteMaintain;
import com.kiy.protocol.Messages.DeleteRole;
import com.kiy.protocol.Messages.DeleteScene;
import com.kiy.protocol.Messages.DeleteTask;
import com.kiy.protocol.Messages.DeleteUser;
import com.kiy.protocol.Messages.DeleteXMDevice;
import com.kiy.protocol.Messages.DeleteZone;
import com.kiy.protocol.Messages.EnsureMessage;
import com.kiy.protocol.Messages.FileDownload;
import com.kiy.protocol.Messages.FileList;
import com.kiy.protocol.Messages.FileList_File;
import com.kiy.protocol.Messages.FileUpload;
import com.kiy.protocol.Messages.GetCameraConfig;
import com.kiy.protocol.Messages.GetMessage;
import com.kiy.protocol.Messages.GetQuestions;
import com.kiy.protocol.Messages.GetServoConfig;
import com.kiy.protocol.Messages.GetWeatherData;
import com.kiy.protocol.Messages.LinuxCommand;
import com.kiy.protocol.Messages.Login;
import com.kiy.protocol.Messages.MainAccount;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.ModifyPassword;
import com.kiy.protocol.Messages.ModifyPasswordByOld;
import com.kiy.protocol.Messages.ModifyXMDeviceName;
import com.kiy.protocol.Messages.MoveDevice;
import com.kiy.protocol.Messages.MovePosition;
import com.kiy.protocol.Messages.MoveZone;
import com.kiy.protocol.Messages.NoticeCloud;
import com.kiy.protocol.Messages.OfflineNoticeActive;
import com.kiy.protocol.Messages.OfflineNoticeReceive;
import com.kiy.protocol.Messages.QueryDeviceMaintain;
import com.kiy.protocol.Messages.QueryDeviceStatus;
import com.kiy.protocol.Messages.QueryLog;
import com.kiy.protocol.Messages.ReadAllDeviceStatus;
import com.kiy.protocol.Messages.ReadDeviceStatus;
import com.kiy.protocol.Messages.ReportLoss;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.SelectDeletes;
import com.kiy.protocol.Messages.SelectDevices;
import com.kiy.protocol.Messages.SelectLinkages;
import com.kiy.protocol.Messages.SelectRoles;
import com.kiy.protocol.Messages.SelectScenes;
import com.kiy.protocol.Messages.SelectTasks;
import com.kiy.protocol.Messages.SelectUsers;
import com.kiy.protocol.Messages.SelectXMDevice;
import com.kiy.protocol.Messages.SelectZones;
import com.kiy.protocol.Messages.SetServoConfig;
import com.kiy.protocol.Messages.SpeechRecognition;
import com.kiy.protocol.Messages.SyncUserIcon;
import com.kiy.protocol.Messages.UpdateCameraList;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.protocol.Messages.UpdateLinkage;
import com.kiy.protocol.Messages.UpdateLinkageStatus;
import com.kiy.protocol.Messages.UpdateMaintain;
import com.kiy.protocol.Messages.UpdateRole;
import com.kiy.protocol.Messages.UpdateScene;
import com.kiy.protocol.Messages.UpdateSceneStatus;
import com.kiy.protocol.Messages.UpdateTask;
import com.kiy.protocol.Messages.UpdateUser;
import com.kiy.protocol.Messages.UpdateZone;
import com.kiy.protocol.Messages.UploadCameraConfig;
import com.kiy.protocol.Messages.UserExist;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDHCamera;
import com.kiy.protocol.Units.MDevice;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.protocol.Units.MLinkage;
import com.kiy.protocol.Units.MLinkageDevice;
import com.kiy.protocol.Units.MLog;
import com.kiy.protocol.Units.MMaintain;
import com.kiy.protocol.Units.MNotice;
import com.kiy.protocol.Units.MNotice.Builder;
import com.kiy.protocol.Units.MPush;
import com.kiy.protocol.Units.MQuestion;
import com.kiy.protocol.Units.MRole;
import com.kiy.protocol.Units.MScene;
import com.kiy.protocol.Units.MSceneDevice;
import com.kiy.protocol.Units.MTask;
import com.kiy.protocol.Units.MUser;
import com.kiy.protocol.Units.MXMCamera;
import com.kiy.protocol.Units.MXMDevice;
import com.kiy.protocol.Units.MZone;
import com.kiy.servo.cloud.Cloud;
import com.kiy.servo.data.Data;
import com.kiy.servo.driver.Driver;
import com.kiy.servo.driver.DriverAdpater;
import com.kiy.servo.driver.SerialPortAdpater;
import com.kiy.servo.job.Linkager;
import com.kiy.servo.job.Strategy;
import com.kiy.servo.master.Master;
import com.kiy.servo.master.MatcherLogon;
import com.kiy.servo.messageque.MessageDevice;
import com.kiy.servo.messageque.MessageDeviceDeque;
import com.kiy.servo.notice.SMS;
import com.kiy.servo.notice.WeatherManager;
import com.kiy.servo.recognize.Recognize;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 * @param <E>
 *
 */
public final class Processer<E> {

	public static final AttributeKey<User> USER = AttributeKey.valueOf("USER");
	/**
	 * 0 无反馈消息
	 */
	public static final int NONE = 0;
	/**
	 * 1 有反馈消息仅当前用户
	 */
	public static final int SINGLE = 1;
	/**
	 * 2 有反馈消息所有登录用户
	 */
	public static final int MULTIPLE = 2;
	/**
	 * 3稍后重试消息
	 */
	public static final int RETRY = 3;

	/**
	 * 是否服务器模式
	 */
	private final boolean isServer;

	public Processer(boolean m) {
		isServer = m;
	}

	/**
	 * 处理Message消息
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @return int 0无反馈消息,1有反馈消息仅当前用户,2有反馈消息所有登录用户
	 */
	public int handler(Channel channel, User user, Message request, Message.Builder response) {
		System.err.println(request.toString());
		switch (request.getActionCase()) {
		
			case CONNECT: {
				if (request.getResult() == Result.SUCCESS) {
					return NONE;
				}
				return RETRY;
			}
			case DISCONNECT: {
				channel.disconnect();
				channel.close();
				return NONE;
			}
			case HEARTBEAT: {
				return isServer ? SINGLE : NONE;
			}
		case LOGIN: {
			Login a = request.getLogin();
			String username = a.getUsername();
			String password = a.getPassword();

			if (Tool.isEmpty(username) || Tool.isEmpty(password)) {
				response.setResult(Result.ILLEGAL_ARGUMENT);
				response.setError("用户名或密码不能为空！");
				return SINGLE;
			}
			if(Data.getServo().getUserForName(username) == null){
				response.setResult(Result.ERROR);
				response.setError("登录用户不存在！");
				return SINGLE;
			}
			user = Data.Login(username, password);
			if (user == null) {
				response.setResult(Result.DENIED);
				response.setError("登录密码错误！");
				return SINGLE;
			}
			if (!user.getEnable()) {
//				response.setResult(Result.DENIED);
//				response.setError("DISABLED");
				response.setResult(Result.ERROR);
				response.setError("该账户已被禁用！如需解禁请联系售后！");
				return SINGLE;
			}else{
				Login.Builder b = response.getLoginBuilder();
				b.setId(user.getId());
				if (!Tool.isEmpty(user.getZoneId()))
					b.setZoneId(user.getZoneId());
				b.setUsername(user.getName());
				b.setPassword(user.getPassword());
				b.setEnable(user.getEnable());
				b.setRealname(user.getRealname());
				b.setNickname(user.getNickname());
				b.setMobile(user.getMobile());
				b.setPhone(user.getPhone());
				b.setEmail(user.getEmail());
				if (!Tool.isEmpty(user.getIconUrl())) {
					b.setUserIcon(user.getIconUrl());
				}
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					b.addRoles(role.getId());
				}
				b.setRemark(Tool.getString(Config.PHONE));
				b.setCreated(user.getCreated().getTime());
				b.setUpdated(user.getUpdated().getTime());
				
				MPush pushItem = a.getPushItem();
				if (!Tool.isEmpty(pushItem.getDeviceTokens())) {
					// 从push里面根据userId取到数据(如果有就修改，如果没有就新增)
					if (Data.SelectPush(user.getId()) != null) {
						Push selectPush = Data.SelectPush(user.getId());
						selectPush.setDeviceTokens(pushItem.getDeviceTokens());
						selectPush.setDeviceType(pushItem.getDeviceType());
						selectPush.setUpdated(new Date());
						if (Data.UpdatePush(selectPush)==null) {
							response.setResult(Result.ERROR);
							return SINGLE;
						}
					} else {
						Push push = new Push();
						push.setId(UUID.randomUUID().toString());
						push.setUserId(user.getId());
						push.setDeviceTokens(pushItem.getDeviceTokens());
						push.setDeviceType(pushItem.getDeviceType());
						push.setCreated(new Date());
						push.setUpdated(new Date());
						if (Data.CreatePush(push)==null) {
							response.setResult(Result.ERROR);
							response.setError("创建推送失败！");
							return SINGLE;
						}
					}
					
					//记录登录信息
					Set<DeviceLogin> deviceLogins = Data.SelectDeviceLogins(user.getId());
					boolean exist = false;//判断该用户登录此设备的记录是否存在;
					String deviceId = pushItem.getDeviceId();
					for (DeviceLogin deviceLogin : deviceLogins) {
						if(deviceLogin.getDeviceId().equals(deviceId)){
							exist = true;
						}
					}
					if (!exist) {
						//创建该用户登录此设备的记录;
						DeviceLogin deviceLogin = new DeviceLogin();
						deviceLogin.setId(UUID.randomUUID().toString());
						deviceLogin.setUserId(user.getId());
						deviceLogin.setDeviceId(deviceId);
						deviceLogin.setDeviceType(pushItem.getDeviceType());
						deviceLogin.setStatus(1);
						deviceLogin.setCreated(new Date());
						deviceLogin.setUpdated(deviceLogin.getCreated());
						if (Data.CreateDeviceLogin(deviceLogin)==null) {
							response.setResult(Result.ERROR);
							response.setError("创建设备登录信息失败！");
							return SINGLE;
						}
					}
						//进行判断，进行下线通知;
						for (DeviceLogin deviceLogin : deviceLogins) {
							if(deviceLogin.getDeviceId().equals(deviceId)){//判断登录是否相等
								if (deviceLogin.getStatus() != 1) {
									deviceLogin.setStatus(1);
									if (Data.UpdateDeviceLogin(deviceLogin) == null) {
										response.setResult(Result.ERROR);
										response.setError("更改设备登录信息失败！");
										return SINGLE;
									}
								}
							}else {
								if (deviceLogin.getStatus() != 0) {
									deviceLogin.setStatus(0);
									if (Data.UpdateDeviceLogin(deviceLogin) != null) {
										//发送下线通知消息
										Message.Builder msg = Message.newBuilder();
										OfflineNoticeReceive.Builder offlineNoticeReceive = msg.getOfflineNoticeReceiveBuilder();
										offlineNoticeReceive.setDeviceId(deviceLogin.getDeviceId());
										String type = pushItem.getDeviceType()==2?"苹果手机":pushItem.getDeviceType()==1?"安卓手机":"电脑"; 
										String content = "你的账号于"+Util.getDate()+"在另一台"+type+"登录了。如非本人操作，则密码可能已泄露，建议修改密码或紧急修改账号。";
										offlineNoticeReceive.setContent(content);
										Message m = msg.build();
										Cloud.send(m);
										Data.addOfflineNotice(deviceLogin.getDeviceId(), content);
										Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
								} else {
									response.setResult(Result.ERROR);
									return SINGLE;
								}
							}
						}
					}
				}
				
				//返回摄像头的信息
				if (Data.selectCamera()!=null) {
					for (XMCamera xmCamera : Data.selectCamera()) {
						MXMCamera.Builder item = MXMCamera.newBuilder();
						item.setId(xmCamera.getId());
						item.setName(xmCamera.getName());
						item.setPassword(xmCamera.getPassword());
						item.setDeviceType(xmCamera.getDeviceType());
						item.setRemark(xmCamera.getRemark());
						item.setCreated(xmCamera.getCreated().getTime());
						item.setUpdated(xmCamera.getUpdated().getTime());
						b.setCameraItem(item);
					}
				}
				channel.attr(USER).set(user);
				response.setResult(Result.SUCCESS);
			} 
			return SINGLE;
		}
			case LOGOUT: {
				if (channel.hasAttr(USER))
					channel.attr(USER).set(null);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}

			case SELECT_ZONES: {
				SelectZones a = request.getSelectZones();
				Set<Zone> set = Data.SelectZones(a.getTick());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectZones.Builder b = response.getSelectZonesBuilder();
					for (Zone z : set) {
						MZone.Builder mb = MZone.newBuilder();
						mb.setId(z.getId());
						mb.setName(z.getName());
						if (!Tool.isEmpty(z.getParentId()))
							mb.setParent(z.getParentId());
						mb.setRemark(z.getRemark());
						mb.setCreated(z.getCreated().getTime());
						mb.setUpdated(z.getUpdated().getTime());
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_DEVICES: {
				SelectDevices a = request.getSelectDevices();
				Set<Device> set = Data.SelectDevices(a.getTick());
				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					ConcurrentSet<Device> treeSet = new ConcurrentSet<Device>();
					for (Device device : set) {
						treeSet.add(device);
					}
					SelectDevices.Builder b = response.getSelectDevicesBuilder();
					for (Device d : treeSet) {
						MDevice.Builder mb = MDevice.newBuilder();
						mb.setId(d.getId());
						if (!Tool.isEmpty(d.getZoneId()))
							mb.setZoneId(d.getZoneId());
						if (!Tool.isEmpty(d.getRelayId()))
							mb.setRelayId(d.getRelayId());
						if (!Tool.isEmpty(d.getOwnerId()))
							mb.setOwnerId(d.getOwnerId());
						mb.setVendor(d.getVendor().getValue());
						mb.setKind(d.getKind().getValue());
						mb.setLink(d.getLink().getValue());
						mb.setModel(d.getModel().getValue());
						mb.setUse(d.getUse().getValue());
						mb.setSn(Tool.getString(d.getSn()));
						mb.setName(d.getName());
						mb.setNumber(d.getNumber());
						mb.setUsername(Tool.getString(d.getUsername()));
						mb.setPassword(Tool.getString(d.getPassword()));
						mb.setNetworkIp(Tool.getString(d.getNetworkIp()));
						mb.setNetworkPort(d.getNetworkPort());
						mb.setSerialPort(Tool.getString(d.getSerialPort()));
						mb.setSerialBaudRate(d.getSerialBaudRate());
						mb.setLoad(d.getLoad());
						mb.setPower(d.getPower());
						mb.setMutual(d.getMutual());
						mb.setDelay(d.getDelay());
						mb.setAddress(Tool.getString(d.getAddress()));
						if (d.getInstalled() != null)
							mb.setInstalled(d.getInstalled().getTime());
						if (d.getProduced() != null)
							mb.setProduced(d.getProduced().getTime());
						mb.setLongitude(d.getLongitude());
						mb.setLatitude(d.getLatitude());
						mb.setAltitude(d.getAltitude());
						mb.setPhaseCheck(d.getPhaseCheck());
						mb.setPhasePower(d.getPhasePower());
						mb.setNotice(d.getNotice());
						mb.setDetect(d.getDetect());
						mb.setRemark(d.getRemark());
						mb.setCreated(d.getCreated().getTime());
						mb.setUpdated(d.getUpdated().getTime());
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_TASKS: {
				SelectTasks a = request.getSelectTasks();
				Set<Task> set = Data.SelectTasks(a.getTick());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectTasks.Builder b = response.getSelectTasksBuilder();
					for (Task t : set) {
						MTask.Builder mb = MTask.newBuilder();
						mb.setId(t.getId());
						mb.setName(t.getName());
						mb.setStart(t.getStart().getTime());
						mb.setStop(t.getStop().getTime());
						mb.setMonth(t.getMonth());
						mb.setWeek(t.getWeek());
						mb.setDay(t.getDay());
						mb.setInterval(t.getInterval());
						mb.setRepeat(t.getRepeat());
						mb.setReadModel(t.getReadModel().getValue());
						mb.setWriteModel(t.getWriteModel().getValue());
						if (!Tool.isEmpty(t.getRoleId()))
							mb.setRoleId(t.getRoleId());
						mb.setRemark(t.getRemark());
						mb.setCreated(t.getCreated().getTime());
						mb.setUpdated(t.getUpdated().getTime());
						mb.setReadFeature(t.getReadFeature());
						mb.setWriteFeature(t.getWriteFeature());
						mb.setLimitLower(t.getLimitLower());
						mb.setLimitUpper(t.getLimitUpper());
						mb.setFeed(t.getFeed());
						mb.setFeedLower(t.getFeedLower());
						mb.setFeedUpper(t.getFeedUpper());
						if (t.getReadDeviceIds() != null) {
							for (String deviceId : t.getReadDeviceIds()) {
								mb.addReads(deviceId);
							}
						}
						if (t.getWriteDeviceIds() != null) {
							for (String deviceId : t.getWriteDeviceIds()) {
								mb.addWrites(deviceId);
							}
						}
						mb.setEnable(t.getEnable());
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_USERS: {
				SelectUsers a = request.getSelectUsers();
				Set<User> set = Data.SelectUsers(a.getTick());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectUsers.Builder b = response.getSelectUsersBuilder();
					for (User u : set) {
						MUser.Builder mb = MUser.newBuilder();
						mb.setId(u.getId());
						if (!Tool.isEmpty(u.getZoneId()))
							mb.setZoneId(u.getZoneId());
						mb.setName(u.getName());
						mb.setEnable(u.getEnable());
						mb.setPassword(u.getPassword());
						mb.setRealname(u.getRealname());
						mb.setNickname(u.getNickname());
						mb.setMobile(u.getMobile());
						mb.setPhone(u.getPhone());
						mb.setEmail(u.getEmail());
						mb.setType(u.getType());
						mb.setRemark(u.getRemark());
						mb.setCreated(u.getCreated().getTime());
						mb.setUpdated(u.getUpdated().getTime());
						for (String r : u.getRoleId())
							mb.addRoles(r);
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_ROLES: {
				SelectRoles a = request.getSelectRoles();
				Set<Role> set = Data.SelectRoles(a.getTick());
				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectRoles.Builder b = response.getSelectRolesBuilder();
					for (Role r : set) {
						MRole.Builder mb = MRole.newBuilder();
						mb.setId(r.getId());
						mb.setName(r.getName());
						mb.setRemark(r.getRemark());
						mb.setCreated(r.getCreated().getTime());
						mb.setUpdated(r.getUpdated().getTime());
						for (Integer c : r.getCommands()) {
							if (c != null)
								mb.addCommands(c);
						}
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_SCENES: {
				SelectScenes a = request.getSelectScenes();
				Set<Scene> set = Data.SelectScenes(a.getTick());
				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectScenes.Builder b = response.getSelectScenesBuilder();
					for (Scene scene : set) {
						MScene.Builder mb = MScene.newBuilder();
						mb.setId(scene.getId());
						mb.setName(scene.getName());
						mb.setRemark(scene.getRemark());
						mb.setImage(scene.getImage());
						mb.setUpdated(scene.getUpdated().getTime());
						mb.setCreated(scene.getCreated().getTime());
						mb.setSwitch(scene.getSwitchStatu());
						List<SceneDevice> openDevices = scene.getOpenDevices();
						for (SceneDevice openDevice : openDevices) {
							MSceneDevice.Builder mSceneDevice = MSceneDevice.newBuilder();
							mSceneDevice.setDeviceId(openDevice.getDeviceId());
							mSceneDevice.setFeatureIndex(openDevice.getFeatureIndex());
							mSceneDevice.setFeatureValue(openDevice.getFeatureValue());
							mSceneDevice.setSwitch(openDevice.getSwitchStatus());
							mb.addOpenDevices(mSceneDevice.build());
						}
						List<SceneDevice> offDevices = scene.getOffDevices();
						for (SceneDevice offDevice : offDevices) {
							MSceneDevice.Builder mSceneDevice = MSceneDevice.newBuilder();
							mSceneDevice.setDeviceId(offDevice.getDeviceId());
							mSceneDevice.setFeatureIndex(offDevice.getFeatureIndex());
							mSceneDevice.setFeatureValue(offDevice.getFeatureValue());
							mSceneDevice.setSwitch(offDevice.getSwitchStatus());
							mb.addOffDevices(mSceneDevice.build());
						}
						mb.build();
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_LINKAGES: {
				SelectLinkages l = request.getSelectLinkages();
				Set<Linkage> set = Data.SelectLinkage(l.getTick());
				if (set == null) {
					response.setResult(Result.ERROR);
				}else {
					SelectLinkages.Builder b = response.getSelectLinkagesBuilder();
					for (Linkage linkage : set) {
						MLinkage.Builder mb = MLinkage.newBuilder();
						mb.setId(linkage.getId());
						mb.setName(linkage.getName());
						mb.setEnable(linkage.isEnable());
						mb.setRemark(linkage.getRemark());
						mb.setCreated(linkage.getCreated().getTime());
						mb.setUpdated(linkage.getUpdated().getTime());
						//获取关联设备
						List<LinkageDevice> linkageDevice = linkage.getLinkageDevice();
						for (LinkageDevice ld : linkageDevice) {
							MLinkageDevice.Builder mBuilder = MLinkageDevice.newBuilder();
							mBuilder.setId(ld.getId());
							mBuilder.setLinkageId(ld.getLinkageId());
							mBuilder.setDeviceId(ld.getDeviceId());
							mBuilder.setPriority(ld.getPriority());
							mBuilder.setFeatureIndex(ld.getFeatureIndex());
							mBuilder.setFeatureValue(ld.getFeatureValue());
							mBuilder.setSecs(ld.getSecs());
							mBuilder.setCreated(ld.getCreated().getTime());
							mBuilder.setUpdated(ld.getUpdated().getTime());
							mb.addLinkageDevice(mBuilder.build());
						}
						mb.build();
						b.addItems(mb);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case SELECT_DELETES: {
				SelectDeletes a = request.getSelectDeletes();
				Set<String> set = Data.SelectDeletes(a.getTick());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					SelectDeletes.Builder b = response.getSelectDeletesBuilder();
					for (String id : set) {
						b.addItems(id);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case QUERY_DEVICE_STATUS: {
				QueryDeviceStatus a = request.getQueryDeviceStatus();
				Device d = Data.getServo().getDevice(a.getId());
				List<Device> set = Data.QueryDeviceStatus(d, a.getBegin(), a.getEnd());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					QueryDeviceStatus.Builder b = response.getQueryDeviceStatusBuilder();
					for (Device status : set) {

						MDeviceStatus.Builder s = MDeviceStatus.newBuilder();
						s.setId(status.getId());
						s.setStatus(status.getStatus().getValue());
						s.setOrigin(status.getDelay());
						s.setIndicate(status.getIndicate());
						for (int index = 0; index < status.getFeatureCount(); index++) {
							s.putItems(index, status.getFeature(index).getValue());
						}
						s.setCreated(status.getCreated().getTime());
						b.addItems(s);
					}
					channel.attr(USER).set(user);
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case QUERY_DEVICE_MAINTAIN: {
				QueryDeviceMaintain a = request.getQueryDeviceMaintain();
				List<Maintain> set = Data.QueryDeviceMaintain(a.getId(), a.getBegin(), a.getEnd());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					QueryDeviceMaintain.Builder b = response.getQueryDeviceMaintainBuilder();
					for (Maintain t : set) {
						MMaintain.Builder m = MMaintain.newBuilder();
						m.setId(t.getId());
						m.setDeviceId(t.getDeviceId());
						m.setRepair(t.getRepair().getValue());
						m.setName(t.getName());
						m.setSn(t.getSn());
						m.setLoad(t.getLoad());
						m.setPower(t.getPower());
						m.setMutual(t.getMutual());
						m.setRadix(t.getRadix());
						m.setEnergyBalance(t.getEnergyBalance());
						m.setRemark(t.getRemark());
						m.setUpdated(t.getUpdated().getTime());
						b.addItems(m);
					}
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case QUERY_LOG: {
				QueryLog a = request.getQueryLog();
				List<ULog> set = Data.QueryLog(a.getId(), a.getBegin(), a.getEnd());

				if (set == null) {
					response.setResult(Result.ERROR);
				} else {
					QueryLog.Builder b = response.getQueryLogBuilder();
					for (ULog l : set) {
						MLog.Builder m = MLog.newBuilder();
						m.setId(l.getId());
						if (!Tool.isEmpty(l.getUserId()))
							m.setUserId(l.getUserId());
						m.setCommand(l.getCommand());
						m.setResult(l.getStatus());
						m.setParameter(l.getParameter());
						m.setTime(l.getTime());
						m.setRemark(l.getRemark());
						m.setCreated(l.getCreated().getTime());
						b.addItems(m);
					}
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case READ_DEVICE_STATUS: /* 读取设备状态 */{
				ReadDeviceStatus readDeviceStatus = request.getReadDeviceStatus();
				MDeviceStatus mDeviceStatus = readDeviceStatus.getItem();
				Device device = Data.getServo().getDevice(mDeviceStatus.getDeviceId());
				ReadDeviceStatus.Builder rds_b = ReadDeviceStatus.newBuilder();
				MDeviceStatus.Builder builder = MDeviceStatus.newBuilder();
				if (device == null) {
					builder.setStatus(Status.C_DRIVER.getValue());/* 设备不存在 */
				} else {
					builder.setDeviceId(device.getId());
					builder.setCreated(device.getTickStatus());
					DriverAdpater adpater = Driver.getAdpater(device);
					if (adpater == null) {
						/*
						 * 通讯适配器( 数据集中器不存在)
						 */
						builder.setStatus(Status.C_RELAY.getValue());
						
						//摄像头没有集中器
						if (device.getKind() == Kind.CAMERA) {
							builder.setStatus(Status.NONE.getValue());
						}
					} else {
						if (!adpater.isActive()) {
							builder.setStatus(Status.OFFLINE.getValue());/* 通讯适配器离线 */
						} else {
							if (!device.getDetect()) {
								builder.setStatus(Status.OFFLINE.getValue());/* 设备已关闭检测功能 */
							} else {
								//2018.08.16改 ni
//								if (device.getKind() == Kind.AIR_CONDITIONER) {
//									for (int index = 0; index < device.getFeatureCount(); index++) {
//										builder.putItems(index, device.getFeature(index).getValue());
//									}
//								}else {
									/* 访问设备取到设备状态 */
									MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, false));
									// adpater.getStatus(device);
									builder.setStatus(device.getStatus().getValue());
									for (int index = 0; index < device.getFeatureCount(); index++) {
										builder.putItems(index, device.getFeature(index).getValue());
//									}
								}
							}
						}
					}
				}
				rds_b.setItem(builder.build());
				response.setReadDeviceStatus(rds_b);
				response.setResult(Result.SUCCESS);
				Data.CreateDeviceStatus(device, Origin.USER);/* 记录设备状态记录 */
				//Linkager.executeLinkage(device);//设备上报触发联动
				return MULTIPLE;
			}
			case WRITE_DEVICE_STATUS: /* 控制设备 */{
				System.err.println(request.toString());
				WriteDeviceStatus writeDeviceStatus = request.getWriteDeviceStatus();
				MDeviceStatus mDeviceStatus = writeDeviceStatus.getItem();
				Device device = Data.getServo().getDevice(mDeviceStatus.getDeviceId());
				WriteDeviceStatus.Builder wds_b = WriteDeviceStatus.newBuilder();
				MDeviceStatus.Builder builder = MDeviceStatus.newBuilder();
				if (device == null) {
					builder.setStatus(Status.C_DRIVER.getValue());/* 设备不存在 */
				} else {
					builder.setCreated(device.getTickStatus());
					builder.setDeviceId(device.getId());
					DriverAdpater adpater = Driver.getAdpater(device);
					if (adpater == null) {
						/*
						 * 通讯适配器( 数据集中器不存在)
						 */
						builder.setStatus(Status.C_RELAY.getValue());
					} else {
						if (!adpater.isActive()) {
							builder.setStatus(Status.OFFLINE.getValue());/* 通讯适配器离线 */
						} else {
							if (!device.getDetect()) {
								builder.setStatus(Status.OFFLINE.getValue());/* 设备已关闭检测功能 */
							} else {
								Map<Integer, Integer> map = mDeviceStatus.getItemsMap();
								for (Integer index : map.keySet()) {
									device.getFeature(index).setValue(map.get(index));
								}
								MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, true));
								// adpater.setStatus(device);/*发送控制指令给设备*/
								builder.setStatus(device.getStatus().getValue());
								for (int index = 0; index < device.getFeatureCount(); index++) {
									builder.putItems(index, device.getFeature(index).getValue());
								}
								/* 电源总控属于安防设备如果被控制需要发送通知给用户 */
								if (device.getNotice()) {
									if (device.getKind() == Kind.SWITCH && device.getUse() == Use.PWOER) {
										Message.Builder msg = Message.newBuilder();
										Builder noticeBuilder = msg.getNoticeBuilder();
										noticeBuilder.setDeviceId(device.getId());

										noticeBuilder.setContent(Data.getServo().getUser(request.getUserId()).getName() + "控制了您的电源总控开关");
										Message m = msg.build();
										Cloud.send(m);
										Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);

										Notice n = new Notice();
										n.setDeviceId(device.getId());
										n.setContent("Control");
										n.setUserId(request.getUserId());
										Data.CreateNotice(n);
									}
								}
							}
						}
					}
				}
				wds_b.setItem(builder.build());
				response.setWriteDeviceStatus(wds_b);
				response.setResult(Result.SUCCESS);
				Data.CreateDeviceStatus(device, Origin.USER);
				Linkager.executeLinkage(device);
				return MULTIPLE;
			}
			case READ_DEVICE_CONFIG: {
				response.setResult(Result.ERROR);
				response.setError("UNSUPPORT");
				return SINGLE;
			}
			case WRITE_DEVICE_CONFIG: {
				response.setResult(Result.ERROR);
				response.setError("UNSUPPORT");
				return SINGLE;
			}
			case CREATE_USER: {
				CreateUser createUser = request.getCreateUser();
				MUser a = createUser.getItem();
				User u = Data.getServo().getUserForName(a.getName());
				if (u!=null) {
					response.setResult(Result.ERROR);
					response.setError("用户已存在！");
					return SINGLE;
				}
				u = new User();
				u.setId(a.getId());
				if (!Tool.isEmpty(a.getZoneId()))
					u.setZoneId(a.getZoneId());
				u.setName(a.getName());
				u.setPassword(a.getPassword());
				u.setEnable(a.getEnable());
				u.setRealname(a.getRealname());
				u.setNickname(a.getNickname());
				u.setMobile(a.getMobile());
				u.setPhone(a.getPhone());
				u.setEmail(a.getEmail());
				u.setRemark(a.getRemark());
				u.setType(a.getType());
				u.setCreated(new Date());
				u.setUpdated(u.getCreated());
				for (String id : a.getRolesList()) {
					u.addRole(id);
				}
				if (a.getRolesList().size()==0) {//app没有下拉选择角色，所以默认赋于所有角色权限
					for (Role role : Data.getServo().getRoles()) {
						u.addRole(role.getId());
					}
				}
				u = Data.CreateUser(u);
				if (u == null) {
					response.setResult(Result.ERROR);
					response.setError("新增用户失败!");
					return SINGLE;
				} else {
					List<MQuestion> questionsList = createUser.getQuestionsList();
					for (MQuestion mQuestion : questionsList) {
						Question question = new Question();
						question.setId(mQuestion.getId());
						question.setUserId(u.getId());
						question.setQuestionNumber(mQuestion.getQuestionNumber());
						question.setQuestionAnswer(mQuestion.getQuestionAnswer());
						question.setRemark(mQuestion.getRemark());
						question.setCreated(new Date());
						question.setUpdated(u.getCreated());
						Question q = Data.CreateQuestion(question);
						if (q == null) {
							response.setResult(Result.ERROR);
							response.setError("设置密保问题失败!");
							return SINGLE;
						} 
					}
					CreateUser.Builder b = response.getCreateUserBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return SINGLE;
				}
			}
			case GET_QUESTIONS:{
				Set<User> users = Data.getServo().getUsers();
				GetQuestions.Builder newBuilder = GetQuestions.newBuilder();
				for (User user2 : users) {
					if(user2.getType()==1){
						for (Question question : Data.selectQuestion(user2.getId())) {
							MQuestion.Builder mq = MQuestion.newBuilder();
							mq.setId(question.getId());
							mq.setUserId(question.getUserId());
							mq.setQuestionNumber(question.getQuestionNumber());
							mq.setQuestionAnswer(question.getQuestionAnswer());
							mq.setRemark(question.getRemark());
							mq.setCreated(question.getCreated().getTime());
							mq.setUpdated(question.getUpdated().getTime());
							newBuilder.addQuestions(mq);
						}
						response.setGetQuestions(newBuilder.build());
						response.setResult(Result.SUCCESS);
						return SINGLE;
					}
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case CREATE_ROLE: {
				CreateRole createRole = request.getCreateRole();
				MRole a = createRole.getItem();
				Role r = new Role();
				r.setId(a.getId());
				r.setName(a.getName());
				r.setRemark(a.getRemark());
				r.setCreated(new Date());
				r.setUpdated(r.getCreated());
				for (Integer c : a.getCommandsList()) {
					if (c != null)
						r.addCommand(c);
				}

				r = Data.CreateRole(r);

				if (r == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateRole.Builder b = response.getCreateRoleBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case CREATE_ZONE: {
				CreateZone createZone = request.getCreateZone();
				MZone a = createZone.getItem();
				Zone z = new Zone();
				z.setId(a.getId());
				if (!Tool.isEmpty(a.getParent()))
					z.setParentId(a.getParent());
				z.setName(a.getName());
				z.setRemark(a.getRemark());
				z.setCreated(new Date());
				z.setUpdated(z.getCreated());
				z = Data.CreateZone(z);
				if (z == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateZone.Builder b = response.getCreateZoneBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case CREATE_TASK: {
				CreateTask createTask = request.getCreateTask();
				MTask a = createTask.getItem();

				Task t = new Task();
				t.setId(a.getId());
				t.setName(a.getName());
				t.setStart(new Date(a.getStart()));
				t.setStop(new Date(a.getStop()));
				t.setMonth(a.getMonth());
				t.setWeek(a.getWeek());
				t.setDay(a.getDay());
				t.setInterval(a.getInterval());
				t.setRepeat(a.getRepeat());
				t.setReadModel(Model.valueOf(a.getReadModel()));
				t.setWriteModel(Model.valueOf(a.getWriteModel()));
				t.setReadFeature(a.getReadFeature());
				t.setWriteFeature(a.getWriteFeature());
				t.setLimitUpper(a.getLimitUpper());
				t.setLimitLower(a.getLimitLower());
				t.setFeed(a.getFeed());
				t.setFeedUpper(a.getFeedUpper());
				t.setFeedLower(a.getFeedLower());
				for (String deviceId : a.getReadsList()) {
					t.addReadDeviceById(deviceId);
				}
				for (String deviceId : a.getWritesList()) {
					t.addWriteDeviceById(deviceId);
				}
				if (!Tool.isEmpty(a.getRoleId()))
					t.setRoleId(a.getRoleId());
				t.setEnable(a.getEnable());
				t.setRemark(a.getRemark());
				t.setCreated(new Date());
				t.setUpdated(t.getCreated());
				t = Data.CreateTask(t);
				if (t == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateTask.Builder b = response.getCreateTaskBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					Strategy.create(t, true);
					return MULTIPLE;
				}
			}
			case CREATE_DEVICE: {
				CreateDevice createDevice = request.getCreateDevice();
				MDevice a = createDevice.getItem();
				Device device = Device.instance(Vendor.valueOf(a.getVendor()), Kind.valueOf(a.getKind()), Model.valueOf(a.getModel()));
				device.setId(a.getId());
				device.setName(a.getName());
				if (!Tool.isEmpty(a.getRelayId()))
					device.setRelayId(a.getRelayId());
				if (!Tool.isEmpty(a.getZoneId()))
					device.setZoneId(a.getZoneId());
				if (!Tool.isEmpty(a.getOwnerId()))
					device.setOwnerId(a.getOwnerId());
				device.setVendor(Vendor.valueOf(a.getVendor()));
				device.setKind(Kind.valueOf(a.getKind()));
				device.setModel(Model.valueOf(a.getModel()));
				device.setLink(Link.valueOf(a.getLink()));
				device.setUse(Use.valueOf(a.getUse()));
				device.setNumber(a.getNumber());
				device.setSn(a.getSn());
				device.setLoad(a.getLoad());
				device.setPower(a.getPower());
				device.setMutual(a.getMutual());
				device.setDelay(a.getDelay());
				device.setUsername(a.getUsername());
				device.setPassword(a.getPassword());
				device.setNetworkIp(a.getNetworkIp());
				device.setNetworkPort(a.getNetworkPort());
				device.setSerialPort(a.getSerialPort());
				device.setSerialBaudRate(a.getSerialBaudRate());
				device.setAddress(a.getAddress());
				device.setInstalled(new Date(a.getInstalled()));
				device.setProduced(new Date(a.getProduced()));
				device.setLongitude(a.getLongitude());
				device.setLatitude(a.getLatitude());
				device.setAltitude(a.getAltitude());
				device.setPhaseCheck(a.getPhaseCheck());
				device.setNotice(a.getNotice());
				device.setDetect(a.getDetect());
				device.setPhasePower(a.getPhasePower());
				device.setRemark(a.getRemark());
				device.setCreated(new Date());
				device.setUpdated(device.getCreated());
				device = Data.CreateDevice(device);
				if (device == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateDevice.Builder b = response.getCreateDeviceBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					Driver.create(device, true);
					return MULTIPLE;
				}
			}
			case CREATE_MAINTAIN: {
				CreateMaintain createMaintain = request.getCreateMaintain();
				MMaintain a = createMaintain.getItem();
				Maintain m = new Maintain();
				m.setId(a.getId());
				m.setDeviceId(a.getDeviceId());
				m.setRepair(Repair.valueOf(a.getRepair()));
				m.setSn(a.getSn());
				m.setLoad(a.getLoad());
				m.setPower(a.getPower());
				m.setMutual(a.getMutual());
				m.setRadix(a.getRadix());
				m.setEnergyBalance(a.getEnergyBalance());
				m.setName(a.getName());
				m.setRemark(a.getRemark());
				m.setCreated(new Date(a.getCreated()));
				m.setUpdated(m.getCreated());

				m = Data.CreateMaintain(m);
				if (m == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateMaintain.Builder b = response.getCreateMaintainBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case MOVE_ZONE: {
				MoveZone a = request.getMoveZone();
				Zone z = Data.MoveZone(a.getId(), a.getParent());
				if (z == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					MoveZone.Builder b = response.getMoveZoneBuilder();
					b.setUpdated(z.getUpdated().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case MOVE_DEVICE: {
				MoveDevice a = request.getMoveDevice();
				Device d = Data.MoveDevice(a.getId(), a.getZone(), a.getRelay());
				if (d == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					MoveDevice.Builder b = response.getMoveDeviceBuilder();
					b.setUpdated(d.getCreated().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case MOVE_POSITION: {
				MovePosition a = request.getMovePosition();
				Device d = Data.MovePosition(a.getId(), a.getLongitude(), a.getLatitude(), a.getAltitude());
				if (d == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					MovePosition.Builder b = response.getMovePositionBuilder();
					b.setUpdated(d.getCreated().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_USER: {
				UpdateUser updateUser = request.getUpdateUser();
				MUser a = updateUser.getItem();
				User u = new User();
				u.setId(a.getId());
				if (Tool.isEmpty(a.getZoneId()))
					u.setZoneId(null);
				else
					u.setZoneId(a.getZoneId());
				u.setName(a.getName());
				u.setPassword(a.getPassword());
				u.setEnable(a.getEnable());
				u.setRealname(a.getRealname());
				u.setNickname(a.getNickname());
				u.setMobile(a.getMobile());
				u.setPhone(a.getPhone());
				u.setEmail(a.getEmail());
				u.setRemark(a.getRemark());
				for (String id : a.getRolesList()) {
					u.addRole(id);
				}
				u = Data.UpdateUser(u);
				if (u == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateUser.Builder b = response.getUpdateUserBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_ROLE: {
				UpdateRole updateRole = request.getUpdateRole();
				MRole a = updateRole.getItem();
				Role r = new Role();
				r.setId(a.getId());
				r.setName(a.getName());
				r.setRemark(a.getRemark());

				for (Integer c : a.getCommandsList()) {
					if (c != null)
						r.addCommand(c);
				}
				r = Data.UpdateRole(r);
				if (r == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateRole.Builder b = response.getUpdateRoleBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_ZONE: {
				UpdateZone updateZone = request.getUpdateZone();
				MZone a = updateZone.getItem();
				Zone z = new Zone();
				z.setId(a.getId());
				if (Tool.isEmpty(a.getParent()))
					z.setParentId(null);
				else
					z.setParentId(a.getParent());
				z.setName(a.getName());
				z.setRemark(a.getRemark());
				z = Data.UpdateZone(z);
				if (z == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateZone.Builder b = response.getUpdateZoneBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_TASK: {
				UpdateTask updateTask = request.getUpdateTask();
				MTask a = updateTask.getItem();
				Task t = new Task();
				t.setId(a.getId());
				t.setName(a.getName());
				t.setStart(new Date(a.getStart()));
				t.setStop(new Date(a.getStop()));
				t.setMonth(a.getMonth());
				t.setWeek(a.getWeek());
				t.setDay(a.getDay());
				t.setInterval(a.getInterval());
				t.setRepeat(a.getRepeat());
				t.setReadModel(Model.valueOf(a.getReadModel()));
				t.setWriteModel(Model.valueOf(a.getWriteModel()));
				t.setFeed(a.getFeed());
				t.setFeedLower(a.getFeedLower());
				t.setFeedUpper(a.getFeedUpper());
				t.setLimitLower(a.getLimitLower());
				t.setLimitUpper(a.getLimitUpper());
				t.setReadFeature(a.getReadFeature());
				t.setWriteFeature(a.getWriteFeature());
				if (Tool.isEmpty(a.getRoleId()))
					t.setRoleId(null);
				else
					t.setRoleId(a.getRoleId());
				t.removeAllReadDevices();
				for (String deviceId : a.getReadsList()) {
					t.addReadDeviceById(deviceId);
				}
				t.removeAllWriteDevices();
				for (String deviceId : a.getWritesList()) {
					t.addWriteDeviceById(deviceId);
				}
				t.setEnable(a.getEnable());
				t.setRemark(a.getRemark());
				t.setUpdated(new Date(a.getUpdated()));
				t = Data.UpdateTask(t);
				if (t == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateTask.Builder b = response.getUpdateTaskBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					Strategy.update(t);
					return MULTIPLE;
				}
			}
			case UPDATE_DEVICE: {
				UpdateDevice updateDevice = request.getUpdateDevice();
				MDevice a = updateDevice.getItem();
				Device d = Device.instance(Vendor.valueOf(a.getVendor()), Kind.valueOf(a.getKind()), Model.valueOf(a.getModel()));
				d.setId(a.getId());
				if (Tool.isEmpty(a.getRelayId()))
					d.setRelayId(null);
				else
					d.setRelayId(a.getRelayId());
				if (Tool.isEmpty(a.getZoneId()))
					d.setZoneId(null);
				else
					d.setZoneId(a.getZoneId());
				if (Tool.isEmpty(a.getOwnerId()))
					d.setOwnerId(null);
				else
					d.setOwnerId(a.getOwnerId());
				d.setName(a.getName());
				d.setVendor(Vendor.valueOf(a.getVendor()));
				d.setKind(Kind.valueOf(a.getKind()));
				d.setModel(Model.valueOf(a.getModel()));
				d.setLink(Link.valueOf(a.getLink()));
				d.setUse(Use.valueOf(a.getUse()));
				d.setNumber(a.getNumber());
				d.setSn(a.getSn());
				d.setLoad(a.getLoad());
				d.setPower(a.getPower());
				d.setMutual(a.getMutual());
				d.setDelay(a.getDelay());
				d.setUsername(a.getUsername());
				d.setPassword(a.getPassword());
				d.setNetworkIp(a.getNetworkIp());
				d.setNetworkPort(a.getNetworkPort());
				d.setSerialPort(a.getSerialPort());
				d.setSerialBaudRate(a.getSerialBaudRate());
				d.setAddress(a.getAddress());
				d.setInstalled(new Date(a.getInstalled()));
				d.setProduced(new Date(a.getProduced()));
				d.setLongitude(a.getLongitude());
				d.setLatitude(a.getLatitude());
				d.setAltitude(a.getAltitude());
				d.setPhaseCheck(a.getPhaseCheck());
				d.setPhasePower(a.getPhasePower());
				d.setDetect(a.getDetect());
				d.setNotice(a.getNotice());
				d.setRemark(a.getRemark());
				d = Data.UpdateDevice(d);
				if (d == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateDevice.Builder b = response.getUpdateDeviceBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					Driver.update(d);
					Recognize.getInstance().refresh();
					return MULTIPLE;
				}
			}
			case UPDATE_MAINTAIN: {
				UpdateMaintain updateMaintain = request.getUpdateMaintain();
				MMaintain a = updateMaintain.getItem();
				Maintain m = new Maintain();
				m.setId(a.getId());
				m.setDeviceId(a.getDeviceId());
				m.setRepair(Repair.valueOf(a.getRepair()));
				m.setSn(a.getSn());
				m.setLoad(a.getLoad());
				m.setPower(a.getPower());
				m.setMutual(a.getMutual());
				m.setRadix(a.getRadix());
				m.setEnergyBalance(a.getEnergyBalance());
				m.setName(a.getName());
				m.setRemark(a.getRemark());
				m.setUpdated(new Date(a.getUpdated()));
				m = Data.UpdateMaintain(m);
				if (m == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateMaintain.Builder b = response.getUpdateMaintainBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_SCENE: {
				UpdateScene updateScene = request.getUpdateScene();
				MScene a = updateScene.getItem();
				Scene scene = new Scene();
				scene.setId(a.getId());
				scene.setName(a.getName());
				scene.setRemark(a.getRemark());
				scene.setImage(a.getImage());
				scene.setSwitchStatu(a.getSwitch());
				scene.setCreated(new Date(a.getCreated()));
				scene.setUpdated(new Date(a.getUpdated()));
				List<MSceneDevice> offDevicesList = a.getOffDevicesList();
				for (MSceneDevice offDevice : offDevicesList) {
					SceneDevice sceneDevice = new SceneDevice();
					sceneDevice.setDeviceId(offDevice.getDeviceId());
					sceneDevice.setSwitchStatus(offDevice.getSwitch());
					sceneDevice.setFeatureIndex(offDevice.getFeatureIndex());
					sceneDevice.setFeatureValue(offDevice.getFeatureValue());
					scene.addOffSceneDevice(sceneDevice);
				}
				List<MSceneDevice> openDevicesList = a.getOpenDevicesList();
				for (MSceneDevice openDevice : openDevicesList) {
					SceneDevice sceneDevice = new SceneDevice();
					sceneDevice.setDeviceId(openDevice.getDeviceId());
					sceneDevice.setSwitchStatus(openDevice.getSwitch());
					sceneDevice.setFeatureIndex(openDevice.getFeatureIndex());
					sceneDevice.setFeatureValue(openDevice.getFeatureValue());
					scene.addOpenSceneDevice(sceneDevice);
				}
				scene = Data.UpdateScene(scene);
				if (scene == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					UpdateScene.Builder b = response.getUpdateSceneBuilder();
					b.getItemBuilder().setUpdated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_SCENE_STATUS: {
				UpdateSceneStatus updateSceneStatus = request.getUpdateSceneStatus();
				String id = updateSceneStatus.getId();
				boolean switchStatus = updateSceneStatus.getSwitchStatus();
				Scene scene = Data.UpdateSceneStatusScenes(id, switchStatus);
				if (scene == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					if (switchStatus) {
						/* 开启场景 */
						controlSceneDevice(scene.getOpenDevices(), request);
					} else {
						/* 关闭场景 */
						controlSceneDevice(scene.getOffDevices(), request);
					}
					
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_LINKAGE: {
				UpdateLinkage updateLinkage = request.getUpdateLinkage();
				MLinkage item = updateLinkage.getItem();
				Linkage linkage = new Linkage();
				linkage.setId(item.getId());
				linkage.setName(item.getName());
				linkage.setEnable(item.getEnable());
				linkage.setRemark(item.getRemark());
				for (MLinkageDevice mLinkageDevice : item.getLinkageDeviceList()) {
					LinkageDevice linkageDevice = new LinkageDevice();
					linkageDevice.setId(mLinkageDevice.getId());
					linkageDevice.setLinkageId(mLinkageDevice.getLinkageId());
					linkageDevice.setDeviceId(mLinkageDevice.getDeviceId());
					linkageDevice.setPriority(mLinkageDevice.getPriority());
					linkageDevice.setFeatureIndex(mLinkageDevice.getFeatureIndex());
					linkageDevice.setFeatureValue(mLinkageDevice.getFeatureValue());
					linkageDevice.setSecs(mLinkageDevice.getSecs());
					linkageDevice.setCreated(new Date());
					linkageDevice.setUpdated(new Date());
					linkage.addLinkageDevice(linkageDevice);
				}
				linkage = Data.UpdateLinkage(linkage);
				if (linkage == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case UPDATE_LINKAGE_STATUS: {
				UpdateLinkageStatus updateLinkageStatus = request.getUpdateLinkageStatus();
				String id = updateLinkageStatus.getId();
				boolean linkageStatus = updateLinkageStatus.getSwitchStatus();
				Linkage linkage = Data.UpdateLinkageStatus(id, linkageStatus);
				if (linkage == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					// TODO
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_USER: {
				DeleteUser a = request.getDeleteUser();
				User u = Data.DeleteUser(a.getId());
				if (u == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					Data.getServo().remove(u.getId()); // 删除缓存中的用户；
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_ROLE: {
				DeleteRole a = request.getDeleteRole();
				Role u = Data.DeleteRole(a.getId());
				if (u == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_ZONE: {
				DeleteZone a = request.getDeleteZone();
				Zone u = Data.DeleteZone(a.getId());
				if (u == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_TASK: {
				DeleteTask a = request.getDeleteTask();
				Task t = Data.DeleteTask(a.getId());
				if (t == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					Strategy.delete(t);
					return MULTIPLE;
				}
			}
			case DELETE_SCENE: {
				DeleteScene a = request.getDeleteScene();
				Scene scene = Data.DeleteScene(a.getId());
				if (scene == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_LINKAGE: {										
				DeleteLinkage d = request.getDeleteLinkage();
				Linkage linkage = Data.DeleteLinkage(d.getId());
				if (linkage == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case DELETE_DEVICE: {
				DeleteDevice a = request.getDeleteDevice();
				Device device = Data.DeleteDevice(a.getId());
				if (device == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					Driver.delete(device);
					return MULTIPLE;
				}
			}
			case DELETE_MAINTAIN: {
				DeleteMaintain a = request.getDeleteMaintain();
				if (Data.DeleteMaintain(a.getId())) {
					response.setResult(Result.SUCCESS);
				} else {
					response.setResult(Result.ERROR);
				}
				return SINGLE;
			}
			case FILE_LIST: {
				File dir = new File(Config.getWorkPath() + File.separatorChar + Config.FILE_DIRECTORY + File.separatorChar + Config.FILE_DIRECTORY_MAP);
				if (dir.exists() && dir.isDirectory()) {
					FileList.Builder b = response.getFileListBuilder();
					File[] children = dir.listFiles();
					for (int index = 0; index < children.length; index++) {
						if (children[index].getName().endsWith(".temp"))
							continue;
						FileList_File.Builder fBuilder = FileList_File.newBuilder();
						fBuilder.setName(children[index].getName());
						fBuilder.setDate(children[index].lastModified());
						fBuilder.setLength(children[index].length());
						b.addFile(fBuilder);
					}
					response.setResult(Result.SUCCESS);
				} else {
					response.setResult(Result.ERROR);
				}
				return SINGLE;
			}
			case FILE_UPLOAD: {
				FileUpload a = request.getFileUpload();
				File file = new File(Config.getWorkPath() + File.separatorChar + Config.FILE_DIRECTORY + File.separatorChar + a.getName() + ".temp");

				ByteString bytes = a.getData();
				if (bytes != null && !bytes.isEmpty()) {
					try {
						if (!file.exists()) {
							file.createNewFile();
						}
						FileOutputStream out = new FileOutputStream(file, true);
						bytes.writeTo(out);
						out.flush();
						out.close();
						// 判断剩余待传字节
						if (a.getLength() == 0) {
							File dest = new File(Config.getWorkPath() + File.separatorChar + Config.FILE_DIRECTORY + File.separatorChar + Config.FILE_DIRECTORY_MAP + File.separatorChar + a.getName());
							if (dest.exists())
								dest.delete();
							file.renameTo(dest);
							file = dest;
						}
					} catch (IOException ex) {
						response.setResult(Result.ERROR);
					}
				}
				response.getFileUploadBuilder().clearData().setLength(file.length());
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case FILE_DOWNLOAD: {
				FileDownload a = request.getFileDownload();
				File file = new File(Config.getWorkPath() + File.separatorChar + Config.FILE_DIRECTORY + File.separatorChar + Config.FILE_DIRECTORY_MAP + File.separatorChar + a.getName());
				// 已收字节
				long length = a.getLength();
				if (file.exists() && file.isFile()) {
					try {
						FileInputStream in = new FileInputStream(file);
						in.skip(length);
						length = 1024L * 2;
						ByteString.Output out = ByteString.newOutput((int) length);
						int value = 0;
						while (length > 0 && (value = in.read()) >= 0) {
							out.write(value);
							length--;
						}
						in.close();
						length = file.length() - a.getLength() - out.size();
						// 剩余字节
						response.getFileDownloadBuilder().setData(out.toByteString()).setLength(length);
						response.setResult(Result.SUCCESS);
					} catch (IOException ex) {
						response.setResult(Result.ERROR);
					}
				} else {
					response.setResult(Result.ERROR);
				}
				return SINGLE;
			}
			case GET_SERVO: {
				response.getGetServoBuilder().setName(Config.NAME).addAllCom(SerialPortAdpater.getAllPort());
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case GET_CAMERA_CONFIG:{
				GetCameraConfig.Builder builder = response.getGetCameraConfigBuilder();
				builder.setAppid(Config.CAMERA_APP_ID);
				builder.setAppsecret(Config.CAMERA_APP_SECRET);
				builder.setHost(Config.CAMERA_HOST_ADRESS);
				builder.setPhone(Config.PHONE);
				response.setGetCameraConfig(builder);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case GET_SERVO_CONFIG: {
				GetServoConfig.Builder a = response.getGetServoConfigBuilder();

				a.setName(Config.NAME + "(" + Tool.getLinuxLocalIp() + ")");
				a.setThread(Config.THREAD);
				a.setDebug(Config.DEBUG);

				a.setDriver(Config.DRIVER);
				a.setDriverRestart(Config.DRIVER_RESTART);
				a.setDriverHeartbeat(Config.DRIVER_HEARTBEAT);
				a.setDriverInterval(Config.DRIVER_INTERVAL);
				a.setDriverTimeout(Config.DRIVER_TIMEOUT);
				a.setDriverDelay(Config.DRIVER_DELAY);

				a.setMaster(Config.MASTER);
				a.setMasterIp(Config.MASTER_IP);
				a.setMasterPort(Config.MASTER_PORT);

				a.setCruise(Config.CRUISE);
				a.setCruiseDelay(Config.CRUISE_DELAY);

				a.setStrategy(Config.STRATEGY);

				a.setMqtt(Config.MQTT);
				a.setMqttHost(Config.MQTT_HOST);
				a.setMqttUsername(Config.MQTT_USERNAME);
				a.setMqttPassword(Config.MQTT_PASSWORD);
				a.setMqttSubScriptionTopic(Config.MQTT_SUBSCRIPTION_TOPIC);

				a.setDb(Config.DB);
				a.setDbMax(Config.DB_MAX);
				a.setDbType(Config.DB_TYPE);
				a.setDbUrl(Config.DB_URL);
				a.setDbUser(Config.DB_USER);
				a.setDbPassword(Config.DB_PASSWORD);

				a.setCloud(Config.CLOUD);
				a.setCloudHost(Config.CLOUD_HOST);
				a.setCloudPort(Config.CLOUD_PORT);
				a.setCloudReset(Config.CLOUD_RESET);
				a.setCloudId(Config.CLOUD_ID);

				a.setWeatherWeaid(Config.WEATHER_WEAID);
				a.setAqiWeaid(Config.AQI_WEAID);

				a.setSms(Config.SMS);
				a.setPhone(Config.PHONE);
				a.setCameraAppId(Config.CAMERA_APP_ID);
				a.setCameraAppSecret(Config.CAMERA_APP_SECRET);
				a.setCameraHostAdress(Config.CAMERA_HOST_ADRESS);


				response.setGetServoConfig(a);
				response.setResult(Result.SUCCESS);

				return SINGLE;
			}
			case SET_SERVO_CONFIG: {
				SetServoConfig a = request.getSetServoConfig();
				String name = a.getName();
				String string = name.substring(0, name.indexOf("("));
				Config.NAME = string;
				Config.THREAD = a.getThread();
				Config.DEBUG = a.getDebug();

				Config.DRIVER = a.getDriver();
				Config.DRIVER_RESTART = a.getDriverRestart();
				Config.DRIVER_HEARTBEAT = a.getDriverHeartbeat();
				Config.DRIVER_INTERVAL = a.getDriverInterval();
				Config.DRIVER_TIMEOUT = a.getDriverTimeout();
				Config.DRIVER_DELAY = a.getDriverDelay();

				Config.MASTER = a.getMaster();
				Config.MASTER_IP = a.getMasterIp();
				Config.MASTER_PORT = a.getMasterPort();

				Config.CRUISE = a.getCruise();
				Config.CRUISE_DELAY = a.getCruiseDelay();

				Config.STRATEGY = a.getStrategy();

				Config.MQTT = a.getMqtt();
				Config.MQTT_HOST = a.getMqttHost();
				Config.MQTT_USERNAME = a.getMqttUsername();
				Config.MQTT_PASSWORD = a.getMqttPassword();
				Config.MQTT_SUBSCRIPTION_TOPIC = a.getMqttSubScriptionTopic();

				Config.DB = a.getDb();
				Config.DB_MAX = a.getDbMax();
				Config.DB_TYPE = a.getDbType();
				Config.DB_URL = a.getDbUrl();
				Config.DB_USER = a.getDbUser();
				Config.DB_PASSWORD = a.getDbPassword();

				Config.CLOUD = a.getCloud();
				Config.CLOUD_HOST = a.getCloudHost();
				Config.CLOUD_PORT = a.getCloudPort();
				Config.CLOUD_RESET = a.getCloudReset();
				Config.CLOUD_ID = a.getCloudId();

				Config.WEATHER_WEAID = a.getWeatherWeaid();
				Config.AQI_WEAID = a.getAqiWeaid();

				Config.SMS = a.getSms();
				
				Config.PHONE = a.getPhone();
				Config.CAMERA_APP_ID = a.getCameraAppId();
				Config.CAMERA_APP_SECRET = a.getCameraAppSecret();
				Config.CAMERA_HOST_ADRESS = a.getCameraHostAdress();

				try {
					Config.save();
				} catch (IOException ex) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}

				response.getSetServoConfigBuilder().clear();
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case CREATE_SCENE: {
				CreateScene createScene = request.getCreateScene();
				MScene a = createScene.getItem();

				Scene scene = new Scene();
				scene.setId(a.getId());
				scene.setName(a.getName());
				scene.setSwitchStatu(a.getSwitch());
				scene.setImage(a.getImage());
				scene.setRemark(a.getRemark());
				scene.setCreated(new Date());
				scene.setUpdated(new Date());
				List<MSceneDevice> openDevicesList = a.getOpenDevicesList();
				for (MSceneDevice openDevice : openDevicesList) {
					SceneDevice sceneDevice = new SceneDevice();
					sceneDevice.setDeviceId(openDevice.getDeviceId());
					sceneDevice.setSwitchStatus(openDevice.getSwitch());
					sceneDevice.setFeatureIndex(openDevice.getFeatureIndex());
					sceneDevice.setFeatureValue(openDevice.getFeatureValue());
					scene.addOpenSceneDevice(sceneDevice);
				}
				List<MSceneDevice> offDevicesList = a.getOffDevicesList();
				for (MSceneDevice offDevice : offDevicesList) {
					SceneDevice sceneDevice = new SceneDevice();
					sceneDevice.setDeviceId(offDevice.getDeviceId());
					sceneDevice.setSwitchStatus(offDevice.getSwitch());
					sceneDevice.setFeatureIndex(offDevice.getFeatureIndex());
					sceneDevice.setFeatureValue(offDevice.getFeatureValue());
					scene.addOffSceneDevice(sceneDevice);
				}
				scene.setImage(1);
				scene = Data.CreateScene(scene);

				if (scene == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateScene.Builder b = response.getCreateSceneBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case CREATE_LINKAGE: {
				CreateLinkage createLinkage = request.getCreateLinkage();
				MLinkage item = createLinkage.getItem();
				Linkage linkage = new Linkage();
				linkage.setId(item.getId());
				linkage.setName(item.getName());
				linkage.setEnable(item.getEnable());
				linkage.setRemark(item.getRemark());
				linkage.setCreated(new Date());
				linkage.setUpdated(new Date());
				List<MLinkageDevice> linkageDeviceList = item.getLinkageDeviceList();
				for (MLinkageDevice m : linkageDeviceList) {
					LinkageDevice linkageDevice = new LinkageDevice();
						linkageDevice.setId(m.getId());
						linkageDevice.setLinkageId(m.getLinkageId());
						linkageDevice.setDeviceId(m.getDeviceId());
						linkageDevice.setPriority(m.getPriority());
						linkageDevice.setFeatureIndex(m.getFeatureIndex());
						linkageDevice.setFeatureValue(m.getFeatureValue());
						linkageDevice.setSecs(m.getSecs());
						linkageDevice.setCreated(new Date());
						linkageDevice.setUpdated(new Date());
						linkage.addLinkageDevice(linkageDevice);
				}
				linkage = Data.CreateLinkage(linkage);
				if (linkage == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					CreateLinkage.Builder b = response.getCreateLinkageBuilder();
					b.getItemBuilder().setCreated(new Date().getTime());
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
				}
			}
			case GET_WEATHER_DATA: {
				Weather weather = WeatherManager.getWeather();
				GetWeatherData.Builder builder = response.getGetWeatherDataBuilder();
				if (weather!=null){
					builder.setWeaid(weather.getResult().getWeaid());
					builder.setDays(weather.getResult().getDays());
					builder.setWeek(weather.getResult().getWeek());
					builder.setCityno(weather.getResult().getCityno());
					builder.setCitynm(weather.getResult().getCitynm());
					builder.setCityid(weather.getResult().getCityid());
					builder.setTemperature(weather.getResult().getTemperature());
					builder.setTemperatureCurr(weather.getResult().getTemperature_curr());
					builder.setHumidity(weather.getResult().getHumidity());
					builder.setAqi(weather.getResult().getAqi());
					builder.setAqiText(weather.getResult().getAQIString());
					builder.setWeather(weather.getResult().getWeather());
					builder.setWeatherIcon(weather.getResult().getWeather_icon());
					builder.setWeatherIcon1(weather.getResult().getWeather_icon1());
					builder.setWind(weather.getResult().getWind());
					builder.setWinp(weather.getResult().getWinp());
					builder.setTempHigh(weather.getResult().getTemp_high());
					builder.setTempLow(weather.getResult().getTemp_low());
					builder.setHumiHigh(weather.getResult().getHumi_high());
					builder.setHumiLow(weather.getResult().getHumi_low());
					builder.setWeatid(weather.getResult().getWeatid());
					builder.setWeatid1(weather.getResult().getWeatid1());
					builder.setWindid(weather.getResult().getWindid());
					builder.setWinpid(weather.getResult().getWinpid());
					builder.setWeatherIconid(weather.getResult().getWeather_iconid());
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case GET_MESSAGE: {
				GetMessage getMessage = request.getGetMessage();
				String phone = getMessage.getPhone();
				String sendSMSVerify = SMS.sendSMSVerify(phone);
				if(sendSMSVerify==null){
				response.setResult(Result.SUCCESS);
				}else{
					response.setResult(Result.ERROR);
					response.setError(sendSMSVerify);
				}
				return SINGLE;
			}

			case MODIFY_PASSWORD: {
				ModifyPassword modifyPassword = request.getModifyPassword();
				String phone = modifyPassword.getPhone();
				String password = modifyPassword.getPassword();
				if (Tool.isEmpty(phone)) {
					response.setResult(Result.ERROR);
					response.setError("电话号码不能为空！");
					return SINGLE;
				}
				if (Tool.isEmpty(password)) {
					response.setResult(Result.ERROR);
					response.setError("密码不能为空！");
					return SINGLE;
				}
				User u = Data.getServo().getUserForName(phone);
				if (u == null) {
					response.setResult(Result.ERROR);
					response.setError("用户不存在！");
					return SINGLE;
				}
				u.setPassword(password);
				Data.UpdateUser(u);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case MODIFY_PASSWORD_BY_OLD: {
				ModifyPasswordByOld modifyPasswordByOld = request.getModifyPasswordByOld();
				String phone = modifyPasswordByOld.getPhone();
				String passwordOld = modifyPasswordByOld.getPasswordOld();
				String passwordNew = modifyPasswordByOld.getPasswordNew();
				if (Tool.isEmpty(phone)) {
					response.setResult(Result.ERROR);
					response.setError("电话号码不能为空！");
					return SINGLE;
				}
				User u = Data.getServo().getUserForName(phone);
				if (u == null) {
					response.setResult(Result.ERROR);
					response.setError("用户不存在！");
					return SINGLE;
				}
				if (Tool.isEmpty(passwordOld)) {
					response.setResult(Result.ERROR);
					response.setError("原密码不能为空！");
					return SINGLE;
				}else if (!u.getPassword().equals(passwordOld)) {
					response.setResult(Result.ERROR);
					response.setError("原密码错误！");
					return SINGLE;
				}
				if (Tool.isEmpty(passwordNew)) {
					response.setResult(Result.ERROR);
					response.setError("新密码不能为空！");
					return SINGLE;
				}
				u.setPassword(passwordNew);
				Data.UpdateUser(u);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case CHANGE_PHONE: {
				ChangePhone changePhone = request.getChangePhone();
				String phoneOld = changePhone.getPhoneOld();
				String phoneNew = changePhone.getPhoneNew();
				if (Tool.isEmpty(phoneOld)) {
					response.setResult(Result.ERROR);
					response.setError("原手机号不能为空！");
					return SINGLE;
				}
				if (Tool.isEmpty(phoneNew)) {
					response.setResult(Result.ERROR);
					response.setError("新手机号不能为空！");
					return SINGLE;
				}
				User u = Data.getServo().getUserForName(phoneOld);
				if (u == null) {
					response.setResult(Result.ERROR);
					response.setError("该用户不存在！");
					return SINGLE;
				}
				u.setName(phoneNew);
				u.setPhone(phoneNew);
				Data.UpdateUser(u);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case SYNC_USER_ICON: {
				SyncUserIcon syncUserIcon = request.getSyncUserIcon();
				String iconUrl = syncUserIcon.getIconUrl();
				String userId = syncUserIcon.getUserId();
				User u = Data.getServo().getUser(userId);
				if (u == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				u.setIconUrl(iconUrl);
				Data.UploadUserIcon(u);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case ENSURE_MESSAGE: {
				EnsureMessage ensureMessage = request.getEnsureMessage();
				String phone = ensureMessage.getPhone();
				String passCode = ensureMessage.getPassCode();
				if (Tool.isEmpty(passCode)) {
					response.setResult(Result.ERROR);
					response.setError("验证码不能为空！");
					return SINGLE;
				}
				if (SMS.ensureVerificationCode(phone, passCode)) {
					response.setResult(Result.SUCCESS);
					return SINGLE;
				}
				response.setResult(Result.ERROR);
				response.setError("验证码有误！");
				return SINGLE;
			}
			case URGENT_MESSAGE: {
				String name = request.getUrgentMessage().getName();
				if (name == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				// 发送紧急推送给家庭其他成员（除呼叫者外）；
				User userForName = Data.getServo().getUserForName(name);
				Set<Push> pushs = Data.getServo().getPushs();
				String urgentString = "用户："+name+"发送了紧急呼叫。";
				if(Cloud.getChannel().isActive()){
					for (Push push : pushs) {
						if (push.getUserId().equals(userForName.getId())) 
							continue;
						//给其它用户发推送
						Message.Builder msg2 = Message.newBuilder();
						NoticeCloud.Builder nc = msg2.getNoticeCloudBuilder();
						nc.setType(push.getDeviceType());
						nc.setDeviceToken(push.getDeviceTokens());
						nc.setNoticeContent(urgentString);
						Message message = msg2.build();
						Cloud.send(message);
						}
				}else{
					Message.Builder msg = Message.newBuilder();
					MNotice.Builder noticeBuilder = msg.getNoticeBuilder();
					noticeBuilder.setDeviceId(null);
					noticeBuilder.setContent(urgentString);
					Message m = msg.build();
					Cloud.send(m);
					Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
					}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case SPEECH_RECOGNITION: {
				SpeechRecognition speechRecognition = request.getSpeechRecognition();
				String speech = speechRecognition.getSpeech();
				SpeechRecognition.Builder builder = response.getSpeechRecognitionBuilder();
				if (Tool.isEmpty(speech)) {
					builder.setResult("未识别到语音");
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				String recognizeString = Recognize.getInstance().recognizeString(speech);
				builder.setResult(recognizeString);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case UPDATE_CAMERA_LIST: {
				UpdateCameraList updateCameraList = request.getUpdateCameraList();
				List<MDHCamera> itemsList = updateCameraList.getItemsList();
				/**
				 * 1.服务器没有，上传的有创建
				 */
				for (MDHCamera mdhCamera : itemsList) {
					boolean findCameraForCache = isCameraInUpload(mdhCamera);
					if (!findCameraForCache) {// 不存在则创建摄像头
						createCameraDevice(mdhCamera);
					}
				}
				/**
				 * 2.服务器有，上传的没有则删除
				 */
				Set<Device> safeCameraDevices = Data.getServo().getSafeCameraDevices();
				
				for (Device device : safeCameraDevices) {
					boolean deviceNotInUpload = isDeviceNotInUpload(device,itemsList);
					if (deviceNotInUpload) { // 上传列表无该设备 删除缓存
						deleteCamera(device);
					}
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case READ_ALL_DEVICE_STATUS:{
				ReadAllDeviceStatus.Builder readAllDeviceStatusBuilder = response.getReadAllDeviceStatusBuilder();
				
				Set<Device> devices = Data.getServo().getDevices();
				for (Device device : devices) {
					MDeviceStatus.Builder builder = MDeviceStatus.newBuilder();
					builder.setDeviceId(device.getId());
					DriverAdpater adpater = Driver.getAdpater(device);
					if (adpater == null) {
						/*
						 * 通讯适配器( 数据集中器不存在)
						 */
						builder.setStatus(Status.C_RELAY.getValue());
						if (device.getKind() == Kind.CAMERA) {
							builder.setStatus(Status.NONE.getValue());
						}
					} else {
						if (!adpater.isActive()) {
							builder.setStatus(Status.OFFLINE.getValue());/* 通讯适配器离线 */
						} else {
							// 直接访问设备 获取设备状态
							if (!device.getDetect()) {
								builder.setStatus(Status.OFFLINE.getValue());/* 设备已关闭检测功能 */
							} 
							else {
//								if (device.getKind() == Kind.AIR_CONDITIONER) {
////									for (int index = 0; index < device.getFeatureCount(); index++) {
////										builder.putItems(index, device.getFeature(index).getValue());
////									}
//									/* 访问设备取到设备状态 */
//									MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, false));
//									builder.setStatus(device.getStatus().getValue());
//									for (int index = 0; index < device.getFeatureCount(); index++) {
//										builder.putItems(index, device.getFeature(index).getValue());
//									}
//								}
//								else {
									/* 访问设备取到设备状态 */
									MessageDeviceDeque.getInstance().add(new MessageDevice(adpater, device, true, false));
									builder.setStatus(device.getStatus().getValue());
									for (int index = 0; index < device.getFeatureCount(); index++) {
										builder.putItems(index, device.getFeature(index).getValue());
//									}
								}
							}
						}
					}
					readAllDeviceStatusBuilder.addItem(builder.build());
					response.setResult(Result.SUCCESS);
				}
				return SINGLE;
			}
			case UPLOAD_CAMERA_CONFIG:{
				if (Data.selectCamera()==null) {
					UploadCameraConfig uploadCameraConfig = response.getUploadCameraConfig();
					MXMCamera item = uploadCameraConfig.getItem();
						XMCamera xmCamera = new XMCamera();
						xmCamera.setId(UUID.randomUUID().toString());
						xmCamera.setName(item.getName());
						xmCamera.setPassword(item.getPassword());
						xmCamera.setDeviceType(item.getDeviceType());
						xmCamera.setRemark(item.getRemark());
						xmCamera.setCreated(new Date());
						xmCamera.setUpdated(new Date());
						if (Data.CreateCamera(xmCamera)) {
							response.setResult(Result.SUCCESS);
							return SINGLE;
						}else{
							response.setResult(Result.ERROR);
							return SINGLE;
						}
				}else {
					response.setResult(Result.CONFLICT);
					return SINGLE;
				}
				}
			case MAIN_ACCOUNT:{
				Set<User> users = Data.getServo().getUsers();
				MainAccount.Builder mb = response.getMainAccountBuilder();
				for (User user2 : users) {
					if(user2.getType()==1){
						mb.setExist(true);
						mb.setPhone(user2.getName());
					}
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case USER_EXIST:{
				UserExist.Builder userExist = response.getUserExistBuilder();
				User userForName = Data.getServo().getUserForName(userExist.getUserName());
				if (userForName == null) {
					response.setResult(Result.ERROR);
					response.setError("该用户不存在");
					return SINGLE;
				}
				if (userForName.getType()==1) 
					userExist.setMainAccount(true);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case OFFLINE_NOTICE_ACTIVE:{
				OfflineNoticeActive.Builder offlineNoticeActive = response.getOfflineNoticeActiveBuilder();
				String deviceId = offlineNoticeActive.getDeviceId();
				if (deviceId == null) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				if (offlineNoticeActive.getType() == 1) {//APP已读，清除缓存消息
					Data.deleteOfflineNotice(deviceId);
					response.setResult(Result.SUCCESS);
					return MULTIPLE;
					
				} 
				if (Data.getOfflineNotice(deviceId) != null){
					offlineNoticeActive.setContent(Data.getOfflineNotice(deviceId));
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			// 挂失
			case REPORT_LOSS:{
				ReportLoss reportLoss = response.getReportLoss();
				String username = reportLoss.getUsername();
				String password = reportLoss.getPassword();
				if(username == null || password == null){
					response.setResult(Result.ERROR);
					response.setError("用户或密码不能为空！");
					return SINGLE;
				}
				if(Data.getServo().getUserForName(username) == null){
					response.setResult(Result.ERROR);
					response.setError("用户不存在！");
					return SINGLE;
				}
				user = Data.Login(username, password);
				if (user == null) {
					response.setResult(Result.ERROR);
					response.setError("密码错误！");
					return SINGLE;
				}
				user.setEnable(false);
				if(Data.UpdateUser(user)==null){
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case LINUX_COMMAND:{
				LinuxCommand linuxCommand = response.getLinuxCommand();
				try {
					Shell.exec(linuxCommand.getCommand());
					response.setResult(Result.SUCCESS);
					return SINGLE;
				} catch (InterruptedException e) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				
			}
			case CREATE_XM_DEVICE:{
				CreateXMDevice createXmDevice = request.getCreateXmDevice();
				MXMDevice item = createXmDevice.getItem();
				XMDevice xmDevice = new XMDevice();
				xmDevice.setId(UUID.randomUUID().toString());
				xmDevice.setUserId(item.getUserId());
				// 序列号和设备名称不能为空
				if (Tool.isEmpty(item.getDeviceMac())) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				if (Tool.isEmpty(item.getLoginName())) {
					response.setResult(Result.ERROR);
					return SINGLE;
				}
				xmDevice.setDeviceMac(item.getDeviceMac());
				xmDevice.setDeviceName(item.getDeviceName());
				xmDevice.setLoginName(item.getLoginName());
				xmDevice.setLoginPsw(item.getLoginPsw());
				xmDevice.setDeviceIp(item.getDeviceIp());
				xmDevice.setState(item.getState());
				xmDevice.setNPort(item.getNPort());
				xmDevice.setNType(item.getNType());
				xmDevice.setNId(item.getNId());
				xmDevice.setRemark(item.getRemark());
				if (!Data.CreateXMDevice(xmDevice)) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return SINGLE;
				}
			}
			case SELECT_XM_DEVICE:{
				if (Data.selectXMDevice()!=null) {
					SelectXMDevice.Builder b = response.getSelectXmDeviceBuilder();
					for (XMDevice xmDevice : Data.selectXMDevice()) {
						MXMDevice.Builder mxmDevice = MXMDevice.newBuilder();
						mxmDevice.setId(xmDevice.getId());
						mxmDevice.setUserId(xmDevice.getUserId());
						mxmDevice.setDeviceMac(xmDevice.getDeviceMac());
						mxmDevice.setDeviceName(xmDevice.getDeviceName());
						mxmDevice.setLoginName(xmDevice.getLoginName());
						mxmDevice.setLoginPsw(xmDevice.getLoginPsw());
						mxmDevice.setDeviceIp(xmDevice.getDeviceIp());
						mxmDevice.setState(xmDevice.getState());
						mxmDevice.setNPort(xmDevice.getNPort());
						mxmDevice.setNType(xmDevice.getNType());
						mxmDevice.setNId(xmDevice.getNId());
						mxmDevice.setRemark(xmDevice.getRemark());
						mxmDevice.setCreated(xmDevice.getCreated().getTime());
						mxmDevice.setUpdated(xmDevice.getUpdated().getTime());
						b.addItems(mxmDevice);
					}
				}
				channel.attr(USER).set(user);
				response.setResult(Result.SUCCESS);
				return SINGLE;
			}
			case MODIFY_XM_DEVICE_NAME:{
				ModifyXMDeviceName modify = request.getModifyXmDeviceName();
				XMDevice xmDevice = new XMDevice();
				xmDevice.setDeviceMac(modify.getDeviceMac());
				xmDevice.setDeviceName(modify.getDeviceName());
				if (!Data.UpdateXMDevice(xmDevice)) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return SINGLE;
				}
			}
			case DELETE_XM_DEVICE:{
				DeleteXMDevice deleteXmDevice = request.getDeleteXmDevice();
				XMDevice xmDevice = new XMDevice();
				xmDevice.setDeviceMac(deleteXmDevice.getDeviceMac());
				channel.attr(USER).set(user);
				if (!Data.deleteXMDevice(xmDevice)) {
					response.setResult(Result.ERROR);
					return SINGLE;
				} else {
					response.setResult(Result.SUCCESS);
					return SINGLE;
				}
			}
			case NOTICE_CLOUD:{
				return NONE;
			}
			case ACTION_NOT_SET:
			default:
				if(request.getResult().equals(Result.UNSUPPORTED))
					return NONE;
				response.setResult(Result.UNSUPPORTED);
				return SINGLE;
				
		}
	}

	private void sendSetStatus(Device d) {
		// 记录日志
		Data.CreateDeviceStatus(d, Origin.SCENE);

		Message.Builder msg = Message.newBuilder();
		WriteDeviceStatus.Builder wrsb = msg.getWriteDeviceStatusBuilder();
		MDeviceStatus.Builder itemBuilder = wrsb.getItemBuilder();

		itemBuilder.setDeviceId(d.getId());
		itemBuilder.setStatus(d.getStatus().getValue());
		itemBuilder.setCreated(d.getTickStatus());
		for (int index = 0; index < d.getFeatureCount(); index++) {
			itemBuilder.putItems(index, d.getFeature(index).getValue());
		}
		// 发送
		wrsb.setItem(itemBuilder);
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}

	private void sendControlNotice(Device device, Message request) {
		/* 电源总控属于安防设备如果被控制需要发送通知给用户 */
		if (device.getNotice()) {
			if (device.getKind() == Kind.SWITCH && device.getUse() == Use.PWOER) {
				Message.Builder msg = Message.newBuilder();
				Builder noticeBuilder = msg.getNoticeBuilder();
				noticeBuilder.setDeviceId(device.getId());

				noticeBuilder.setContent(Data.getServo().getUser(request.getUserId()).getName() + "关闭了您的电源总控开关");
				Message m = msg.build();
				Cloud.send(m);
				Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);

				Notice n = new Notice();
				n.setDeviceId(device.getId());
				n.setContent("Control");
				n.setUserId(request.getUserId());
				Data.CreateNotice(n);
			}
		}
	}

	/**
	 * 发送场景设备控制指令
	 * 
	 * @param sceneDevice
	 */
	private synchronized void controlSceneDevice(List<SceneDevice> sceneDevices, Message request) {
		ConcurrentHashMap<String,Device> deviceMap = new ConcurrentHashMap<>();
		for (SceneDevice sceneDevice : sceneDevices) {
			Device device = Data.getServo().getDevice(sceneDevice.getDeviceId());
			if (device == null) {
				/* 设备不存在 */
			} else {
				if (deviceMap.get(device.getId())!=null) {
					Device device2 = deviceMap.get(device.getId());
					device.getFeature(sceneDevice.getFeatureIndex()).setValue(sceneDevice.getFeatureValue());
					deviceMap.put(device.getId(), device2);
				}else{
					DriverAdpater adpater = Driver.getAdpater(device);
					if (adpater == null) {
						device.setStatus(Status.C_RELAY);
					} else {
						if (!adpater.isActive()) {
							device.setStatus(Status.OFFLINE);
						} else {
							if (!device.getDetect()) {
								device.setStatus(Status.OFFLINE);
							} else {
								Tool.setCombinedEquipment(device);
								device.getFeature(sceneDevice.getFeatureIndex()).setValue(sceneDevice.getFeatureValue());
								deviceMap.put(device.getId(), device);
							}
						}
					}
				}
			}
		}
			for (Entry<String, Device> d : deviceMap.entrySet()) {
				Device device2 = d.getValue();
				MessageDeviceDeque.getInstance().add(new MessageDevice(Driver.getAdpater(device2), device2, true, true));
				sendSetStatus(device2);
				sendControlNotice(device2, request);
		}
	}

	/**
	 * 查看服务器是否存在可以匹配的摄像头
	 * 
	 * @param mdCamera
	 * @return
	 */
	private boolean isCameraInUpload(MDHCamera mdCamera) {
		Set<Device> devices = Data.getServo().getSafeCameraDevices();
		for (Device device : devices) {
			if (device.getNumber().equals(mdCamera.getCameraId()) && Integer.valueOf(device.getUsername())==mdCamera.getChannelId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查看服务器是否存在匹配摄像头
	 */
	private boolean isDeviceNotInUpload(Device device,List<MDHCamera> itemsList){
		for (MDHCamera mdhCamera : itemsList) {
			if (device.getNumber().equals(mdhCamera.getCameraId()) && Integer.valueOf(device.getUsername())==mdhCamera.getChannelId()) {
				return false;
			}
		}
		return true;
	} 
	
	
	
	private String getCameraZoneId(){
		Set<Zone> zones = Data.getServo().getZones();
		for (Zone zone : zones) {
			if (zone.getRemark().equals("摄像头")) {
				return zone.getId();
			}
		}
		return null;
	}

	/**
	 * 创建摄像头
	 * 
	 * @param mdCamera
	 * @return
	 */
	private void createCameraDevice(MDHCamera mdCamera) {
		Device createDevice = Device.instance(Vendor.DAHUA, Kind.CAMERA, Model.DH_0001);
		createDevice.newId();
		createDevice.setZoneId(getCameraZoneId());
		createDevice.setLink(Link.ETHERNET);
		createDevice.setUse(Use.NONE);
		createDevice.setName(mdCamera.getCameraId());
		createDevice.setNumber(mdCamera.getCameraId());
		createDevice.setUsername(String.valueOf(mdCamera.getChannelId()));
		createDevice.setCreated(new Date(System.currentTimeMillis()));
		createDevice.setUpdated(new Date(System.currentTimeMillis()));
		
		
		Message.Builder msg = Message.newBuilder();
		CreateDevice.Builder createDeviceBuilder = msg.getCreateDeviceBuilder();
		MDevice.Builder newBuilder = createDeviceBuilder.getItemBuilder();
		newBuilder.setId(createDevice.getId());
		newBuilder.setZoneId(createDevice.getZoneId());
		newBuilder.setVendor(createDevice.getVendor().getValue());
		newBuilder.setKind(createDevice.getKind().getValue());
		newBuilder.setModel(createDevice.getModel().getValue());
		newBuilder.setLink(createDevice.getLink().getValue());
		newBuilder.setName(createDevice.getName());
		newBuilder.setNumber(createDevice.getNumber());
		newBuilder.setCreated(System.currentTimeMillis());
		newBuilder.setUpdated(System.currentTimeMillis());
		newBuilder.setUsername(createDevice.getUsername());
		createDeviceBuilder.setItem(newBuilder.build());
		
		msg.setCreateDevice(createDeviceBuilder.build());
		
		createDevice = Data.CreateDevice(createDevice);
		Message m = msg.build();
		
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}
	
	
	private void deleteCamera(Device device){
		
		Message.Builder msg = Message.newBuilder();
		DeleteDevice.Builder deleteDeviceBuilder = msg.getDeleteDeviceBuilder();
		deleteDeviceBuilder.setId(device.getId());
		msg.setDeleteDevice(deleteDeviceBuilder.build());
		Message m = msg.build();

		Data.DeleteDevice(device.getId());
		
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}
}

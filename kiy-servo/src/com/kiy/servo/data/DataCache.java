/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kiy.common.Device;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Push;
import com.kiy.common.Role;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.common.Zone;

/**
 * 基于内存的数据维护类
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class DataCache {

	private DataFile DF;
	protected Servo servo;

	public DataCache() {
		DF = new DataFile();
	}

	public void load() {
		Object o = DF.load();
		if (o != null && o instanceof Servo) {
			servo = (Servo) o;
		} else {
			servo = null;
		}
	}

	public void save() {
		DF.save(servo);
	}

	// 以下为数据处理方法///////////////////////////

	public User Login(String username, String password) {
		User user = null;
		Enumeration<User> e = servo.eUsers();
		while (e.hasMoreElements()) {
			user = e.nextElement();
			if (username.equalsIgnoreCase(user.getName())) {
				if (password.equals(user.getPassword())) {
					return user;
				}
				break;
			}
		}
		return null;
	}

	public Set<Device> SelectDevices(long date) {
		HashSet<Device> set = new HashSet<Device>();
		Enumeration<Device> e = servo.eDevices();
		Device d = null;
		while (e.hasMoreElements()) {
			d = e.nextElement();
			if (d.getUpdated().getTime() > date)
				set.add(d);
		}
		return set;
	}

	public Set<Task> SelectTasks(long date) {
		HashSet<Task> set = new HashSet<Task>();
		Enumeration<Task> e = servo.eTasks();
		Task d = null;
		while (e.hasMoreElements()) {
			d = e.nextElement();
			if (d.getUpdated().getTime() > date)
				set.add(d);
		}
		return set;
	}

	public Set<Zone> SelectZones(long date) {
		HashSet<Zone> set = new HashSet<Zone>();
		Enumeration<Zone> e = servo.eZones();
		Zone d = null;
		while (e.hasMoreElements()) {
			d = e.nextElement();
			if (d.getUpdated().getTime() > date)
				set.add(d);
		}
		return set;
	}

	public Set<User> SelectUsers(long date) {
		HashSet<User> set = new HashSet<User>();
		Enumeration<User> e = servo.eUsers();
		User d = null;
		while (e.hasMoreElements()) {
			d = e.nextElement();
			if (d.getUpdated().getTime() > date)
				set.add(d);
		}
		return set;
	}

	public Set<Role> SelectRoles(long date) {
		HashSet<Role> set = new HashSet<Role>();
		Enumeration<Role> e = servo.eRoles();
		Role d = null;
		while (e.hasMoreElements()) {
			d = e.nextElement();
			if (d.getUpdated().getTime() > date)
				set.add(d);
		}
		return set;
	}
	
	public Set<Scene> SelectScenes(long date){
		HashSet<Scene> set = new HashSet<Scene>();
		Enumeration<Scene> e = servo.eScenes();
		Scene s = null;
		while (e.hasMoreElements()) {
			s = e.nextElement();
			if (s.getUpdated().getTime() > date)
				set.add(s);
		}
		return set;
	}
	
	public Set<Linkage> SelectLinkage(long date){
		HashSet<Linkage> set = new HashSet<>();
		Enumeration<Linkage> e = servo.eLinkages();
		Linkage l = null;
		while (e.hasMoreElements()) {
			l = e.nextElement();
			if (l.getUpdated().getTime() > date)
				set.add(l);
		}
		return set;
	}

	public User CreateUser(User user) {
		servo.add(user);
		return user;
	}

	public Role CreateRole(Role role) {
		servo.add(role);
		return role;
	}

	public Zone CreateZone(Zone zone) {
		servo.add(zone);
		return zone;
	}

	public Task CreateTask(Task task) {
		servo.add(task);
		return task;
	}

	public Device CreateDevice(Device device) {
		servo.add(device);
		return device;
	}
	
	public Scene CreateScene(Scene scene){
		servo.add(scene);
		return scene;
	}
	
	public Linkage CreateLinkage(Linkage linkage){
		servo.add(linkage);
		return linkage;
	}
	public Push CreatePush(Push push){
		servo.add(push);
		return push;
	}

	public Zone MoveZone(String id, String parent) {
		Zone zone = servo.getZone(id);
		if (zone == null)
			return null;

		if (Tool.isEmpty(parent))
			zone.setParentId(null);
		else
			zone.setParentId(parent);
		zone.setUpdated(new Date());

		return zone;
	}

	public Device MoveDevice(String id, String zone, String relay) {
		Device device = servo.getDevice(id);
		if (device == null)
			return null;

		if (Tool.isEmpty(relay))
			device.setRelayId(null);
		else
			device.setRelayId(relay);

		if (Tool.isEmpty(zone))
			device.setZoneId(null);
		else
			device.setZoneId(zone);

		device.setUpdated(new Date());

		return device;
	}

	public Device MovePosition(String id, double longitude, double latitude, float altitude) {
		Device device = servo.getDevice(id);
		if (device == null)
			return null;

		device.setLongitude(longitude);
		device.setLatitude(latitude);
		device.setAltitude(altitude);

		device.setUpdated(new Date());

		return device;
	}

	public User UpdateUser(User n) {
		User user = servo.getUser(n.getId());
		if (user == null)
			return null;

		user.setZoneId(n.getZoneId());
		user.setName(n.getName());
		user.setPassword(n.getPassword());
		user.setEnable(n.getEnable());
		user.setRealname(n.getRealname());
		user.setNickname(n.getNickname());
		user.setMobile(n.getMobile());
		user.setPhone(n.getPhone());
		user.setEmail(n.getEmail());
		user.setRemark(n.getRemark());
		user.offAllRoles();
		for (String id : n.getRoleId()) {
			user.addRole(id);
		}
		user.setUpdated(new Date());

		return user;
	}

	public Role UpdateRole(Role a) {
		Role role = servo.getRole(a.getId());
		if (role == null)
			return null;

		role.setName(a.getName());
		role.setRemark(a.getRemark());
		role.offAllCommands();
		for (Integer command : a.getCommands()) {
			if (command != null)
				role.addCommand(command);
		}
		role.setUpdated(new Date());

		return role;
	}

	public Zone UpdateZone(Zone a) {
		Zone zone = servo.getZone(a.getId());
		if (zone == null)
			return null;

		zone.setParentId(a.getParentId());
		zone.setName(a.getName());
		zone.setRemark(a.getRemark());
		zone.setUpdated(new Date());

		return zone;
	}

	public Task UpdateTask(Task a) {
		Task task = servo.getTask(a.getId());
		if (task == null)
			return null;
		task.setRoleId(a.getRoleId());
		task.setName(a.getName());
		task.setStart(a.getStart());
		task.setStop(a.getStop());
		task.setMonth(a.getMonth());
		task.setWeek(a.getWeek());
		task.setDay(a.getDay());
		task.setInterval(a.getInterval());
		task.setRepeat(a.getRepeat());
		task.setReadModel(a.getReadModel());
		task.setWriteModel(a.getWriteModel());
		task.setReadFeature(a.getReadFeature());
		task.setWriteFeature(a.getWriteFeature());
		task.setLimitLower(a.getLimitLower());
		task.setLimitUpper(a.getLimitUpper());
		task.setFeed(a.getFeed());
		task.setFeedLower(a.getFeedLower());
		task.setFeedUpper(a.getFeedUpper());
		Set<String> readDeviceIds = a.getReadDeviceIds();
		task.removeAllReadDevices();
		if(readDeviceIds!=null) {
			for (String string : readDeviceIds) {
				task.addReadDeviceById(string);
			}
		}
		
		task.removeAllWriteDevices();
		Set<String> writeDeviceIds = a.getWriteDeviceIds();
		if(writeDeviceIds!=null) {
			for (String string : writeDeviceIds) {
				task.addWriteDeviceById(string);
			}
		}
		task.setEnable(a.getEnable());
		task.setRemark(a.getRemark());
		task.setUpdated(new Date());
		return task;
	}
	
	public Scene UpdateScene(Scene s) {
		Scene scene = servo.getScene(s.getId());
		if (scene == null)
			return null;
		scene.setSwitchStatu(s.getSwitchStatu());
		scene.setName(s.getName());
		scene.setImage(s.getImage());
		scene.setRemark(s.getRemark());
		scene.setUpdated(new Date());
		List<SceneDevice> openDevices = s.getOpenDevices();
		scene.removeAllOpenSceneDevice();
		for (SceneDevice openDevice : openDevices) {
			scene.addOpenSceneDevice(openDevice);
		}
		List<SceneDevice> offDevices = s.getOffDevices();
		scene.removeAllOffSceneDevice();
		for (SceneDevice offDevice : offDevices) {
			scene.addOffSceneDevice(offDevice);
		}
		return scene;
	}
	
	public Scene UpdateSceneStatus(String id,boolean switchStatus){
		Scene scene = servo.getScene(id);
		if (scene == null)
			return null;
		scene.setSwitchStatu(switchStatus);
		return scene;
	}
	
	public Linkage UpdateLinkage(Linkage l) {
		Linkage linkage = servo.getLinkage(l.getId());
		if (linkage == null)
			return null;
		linkage.setName(l.getName());
		linkage.setEnable(l.isEnable());
		linkage.setRemark(l.getRemark());

		linkage.removeAllLinkageDevice();
		for (LinkageDevice linkageDevice2 : l.getLinkageDevice()) {
			linkage.addLinkageDevice(linkageDevice2);
		}
		return linkage;
	}
	public Push UpdatePush(Push p) {
		Push push = servo.getPush(p.getId());
		if (push == null)
			return null;
		push.setDeviceTokens(p.getDeviceTokens());
		push.setDeviceType(p.getDeviceType());
		push.setUpdated(p.getUpdated());
		return push;
	}
	
	public Linkage UpdateLinkageStatus(String id,boolean linkageStatus){
		Linkage linkage = servo.getLinkage(id);
		if (linkage == null)
			return null;
		linkage.setEnable(linkageStatus);
		return linkage;
	}

	public Device UpdateDevice(Device a) {
		Device device = servo.getDevice(a.getId());
		if (device == null)
			return null;

		if (Tool.isEmpty(a.getRelayId()))
			device.setRelayId(null);
		else
			device.setRelayId(a.getRelayId());
		if (Tool.isEmpty(a.getZoneId()))
			device.setZoneId(null);
		else
			device.setZoneId(a.getZoneId());
		if (Tool.isEmpty(a.getOwnerId()))
			device.setOwnerId(null);
		else
			device.setOwnerId(a.getOwnerId());

		device.setName(a.getName());
		device.setVendor(a.getVendor());
		device.setKind(a.getKind());
		device.setModel(a.getModel());
		device.setLink(a.getLink());
		device.setUse(a.getUse());
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
		device.setInstalled(a.getInstalled());
		device.setProduced(a.getProduced());
		device.setAddress(a.getAddress());
		device.setLatitude(a.getLongitude());
		device.setLatitude(a.getLatitude());
		device.setAltitude(a.getAltitude());
		device.setPhaseCheck(a.getPhaseCheck());
		device.setPhasePower(a.getPhasePower());
		device.setNotice(a.getNotice());
		device.setDetect(a.getDetect());
		device.setRemark(a.getRemark());
		device.setUpdated(new Date());

		return device;
	}

	public User DeleteUser(String id) {
		return (User) servo.remove(id);
	}

	public Role DeleteRole(String id) {
		return (Role) servo.remove(id);
	}

	public Zone DeleteZone(String id) {
		return (Zone) servo.remove(id);
	}

	public Task DeleteTask(String id) {
		return (Task) servo.remove(id);
	}

	public Device DeleteDevice(String id) {
		return (Device) servo.remove(id);
	}
	
	public Scene DeleteScene(String id){
		return (Scene) servo.remove(id);
	}
	public Linkage DeleteLinkage(String id){
		return (Linkage) servo.remove(id);
	}
	public void addOfflineNotice(String deviceId,String content){
		servo.addOfflineNotice(deviceId, content);
	}
	public String getOfflineNotice(String deviceId){
		return servo.getOfflineNotice(deviceId);
	}
	public void deleteOfflineNotice(String deviceId){
		servo.deleteOfflineNotice(deviceId);
	}
}
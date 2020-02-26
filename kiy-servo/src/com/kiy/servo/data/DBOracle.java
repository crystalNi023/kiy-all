/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kiy.common.DeviceLogin;
import com.kiy.common.Question;
import com.kiy.common.XMCamera;
import com.kiy.common.Device;
import com.kiy.common.Linkage;
import com.kiy.common.Maintain;
import com.kiy.common.Notice;
import com.kiy.common.Push;
import com.kiy.common.Role;
import com.kiy.common.Scene;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Types.Origin;
import com.kiy.common.ULog;
import com.kiy.common.User;
import com.kiy.common.XMDevice;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.Message;

/**
 * Oracle数据库操作
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class DBOracle extends DataBank {

	/**
	 * @param c
	 */
	public DBOracle(DBConnection c) {
		super(c);
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#refresh(com.kiy.common.Servo)
	 */
	@Override
	public void refresh(Servo s) {
		//
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#SelectDeletes(java.util.Date)
	 */
	@Override
	public Set<String> SelectDeletes(Date d) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#QueryDeviceStatus(com.kiy.common.Device, java.util.Date, java.util.Date)
	 */
	@Override
	public List<Device> QueryDeviceStatus(Device u, Date begin, Date end) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#QueryDeviceMaintain(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<Maintain> QueryDeviceMaintain(String id, Date begin, Date end) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#QueryLog(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<ULog> QueryLog(String id, Date begin, Date end) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#MoveZone(com.kiy.common.Zone)
	 */
	@Override
	public boolean MoveZone(Zone u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#MoveDevice(com.kiy.common.Device)
	 */
	@Override
	public boolean MoveDevice(Device u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#MovePosition(com.kiy.common.Device)
	 */
	@Override
	public boolean MovePosition(Device u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateUser(com.kiy.common.User)
	 */
	@Override
	public boolean CreateUser(User u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateRole(com.kiy.common.Role)
	 */
	@Override
	public boolean CreateRole(Role u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateZone(com.kiy.common.Zone)
	 */
	@Override
	public boolean CreateZone(Zone u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateTask(com.kiy.common.Task)
	 */
	@Override
	public boolean CreateTask(Task u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateDevice(com.kiy.common.Device)
	 */
	@Override
	public boolean CreateDevice(Device u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateDeviceStatus(com.kiy.common.Device, com.kiy.common.Types.Origin)
	 */
	@Override
	public boolean CreateDeviceStatus(Device u, Origin o) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateMaintain(com.kiy.common.Maintain)
	 */
	@Override
	public boolean CreateMaintain(Maintain u) {
		return false;
	}



	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateUser(com.kiy.common.User)
	 */
	@Override
	public boolean UpdateUser(User u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateRole(com.kiy.common.Role)
	 */
	@Override
	public boolean UpdateRole(Role u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateZone(com.kiy.common.Zone)
	 */
	@Override
	public boolean UpdateZone(Zone u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateTask(com.kiy.common.Task)
	 */
	@Override
	public boolean UpdateTask(Task u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateDevice(com.kiy.common.Device)
	 */
	@Override
	public boolean UpdateDevice(Device u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#UpdateMaintain(com.kiy.common.Maintain)
	 */
	@Override
	public boolean UpdateMaintain(Maintain u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteUser(com.kiy.common.User)
	 */
	@Override
	public boolean DeleteUser(User u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteRole(com.kiy.common.Role)
	 */
	@Override
	public boolean DeleteRole(Role u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteZone(com.kiy.common.Zone)
	 */
	@Override
	public boolean DeleteZone(Zone u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteTask(com.kiy.common.Task)
	 */
	@Override
	public boolean DeleteTask(Task u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteDevice(com.kiy.common.Device)
	 */
	@Override
	public boolean DeleteDevice(Device u) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#DeleteMaintain(java.lang.String)
	 */
	@Override
	public boolean DeleteMaintain(String id) {
		return false;
	}


	/* (non-Javadoc)
	 * @see com.kiy.servo.data.DBOperation#CreateLog(com.kiy.common.User, com.kiy.protocol.Messages.Message, com.kiy.protocol.Messages.Message, long)
	 */
	@Override
	public boolean CreateLog(User u, Message req, Message rsp, long time) {
		return false;
	}

	@Override
	public boolean CreateNotice(Notice u) {
		return false;
	}

	@Override
	public boolean UploadUserIcon(User u) {
		return false;
	}

	@Override
	public String QueryUserIcon(String id) {
		return null;
	}

	@Override
	public boolean CreateScene(Scene s) {
		return false;
	}

	@Override
	public boolean UpdateScene(Scene s) {
		return false;
	}

	@Override
	public boolean UpdateSceneStatus(String id,boolean sceneStatus) {
		return false;
	}

	@Override
	public boolean DeleteScene(Scene s) {
		return false;
	}

	@Override
	public boolean CreateLinkage(Linkage l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateLinkage(Linkage l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteLinkage(Linkage l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateLinkageStatus(String id, boolean linkageStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int CountLinkageDevicePriority(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean CreatePush(Push push) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Push SelectPush(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean UpdatePush(Push push) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Push> SelectPushes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<XMCamera> SelectCamera() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CreateCamera(XMCamera camera) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Question> SelectQuestion(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CreateQuestion(Question question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CreateDeviceLogin(DeviceLogin deviceLogin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<DeviceLogin> SelectDeviceLogins(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean UpdateDeviceLogin(DeviceLogin deviceLogin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<XMDevice> SelectXMDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CreateXMDevice(XMDevice xmDevice) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateXMDevice(XMDevice xmDevice) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteXMDevice(XMDevice xmDevice) {
		// TODO Auto-generated method stub
		return false;
	}

}
/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.kiy.common.Device;
import com.kiy.common.DeviceLogin;
import com.kiy.common.Feature;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Maintain;
import com.kiy.common.Notice;
import com.kiy.common.Push;
import com.kiy.common.Question;
import com.kiy.common.Role;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Servo;
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
import com.kiy.common.XMCamera;
import com.kiy.common.XMDevice;
import com.kiy.common.Zone;
import com.kiy.protocol.Messages.Message;
import com.kiy.servo.Log;

/**
 * MySql数据库操作
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class DBMySQL extends DataBank {

	public DBMySQL(DBConnection c) {
		super(c);
	}

	private static final String SELECT_DEVICES = "SELECT * FROM `devices` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_TASKS = "SELECT * FROM `tasks` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_TASK_DEVICES = "SELECT * FROM `task_devices` WHERE `task`=?task AND `rw`=?rw";
	private static final String SELECT_ZONES = "SELECT * FROM `zones` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_USERS = "SELECT * FROM `users` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_USER_ROLES = "SELECT * FROM `user_roles` WHERE `user`=?user";
	private static final String SELECT_ROLES = "SELECT * FROM `roles` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_ROLE_POWERS = "SELECT * FROM `role_powers` WHERE `role`=?role";
	private static final String SELECT_DELETES = "SELECT `id` FROM `deletes` WHERE ?created IS NULL OR `created`>?created";
	private static final String SELECT_SCENE = "SELECT * FROM `scenes` WHERE ?updated IS NULL OR `updated`>?updated";
	private static final String SELECT_SCENE_DEVICE = "SELECT * FROM `scene_devices` WHERE `scene`=?scene";
	private static final String SELECT_LINKAGE = "SELECT * FROM `linkages`";
	private static final String SELECT_LINKAGE_DEVICES = "SELECT * FROM `linkage_devices` WHERE `linkage_id`=?linkage_id order by `priority` asc";

	private static final String COUNT_LINKAGE_DEVICE_PRIORITY="SELECT COUNT(l.priority) `count` from `linkage_devices` l WHERE `device_id`=?device_id";
	private static final String SELECT_LINKAGEID_BY_DEVICEID="SELECT linkage_id  FROM  linkage_devices WHERE device_id =?device_id";
	
	private static final String QUERY_DEVICE_STATUS = "SELECT * FROM `device_status` WHERE `device`=?device AND `created`>=?begin AND `created`<=?end ORDER BY `created`";
	private static final String QUERY_DEVICE_MAINTAIN = "SELECT * FROM `maintains` WHERE `device`=?device AND `created`>=?begin AND `created`<=?end ORDER BY `created`";
	private static final String QUERY_LOG = "SELECT * FROM `logs` WHERE (?user IS NULL OR `user`=?user) AND `created`>=?begin AND `created`<=?end ORDER BY `created`";
	private static final String QUERY_USER_ICON = "SELECT `icon_url` FROM `users` WHERE `id`=?id";

	private static final String MOVE_ZONE = "UPDATE `zones` SET `parent`=?parent,`updated`=?updated WHERE `id`=?id";
	private static final String MOVE_DEVICE = "UPDATE `devices` SET `zone`=?zone,`relay`=?relay,`updated`=?updated WHERE `id`=?id";
	private static final String MOVE_POSITION = "UPDATE `devices` SET `longitude`=?longitude,`latitude`=?latitude,`altitude`=?altitude,`updated`=?updated WHERE `id`=?id";

	private static final String CREATE_USER = "INSERT INTO `users` (`id`,`zone`,`name`,`password`,`enable`,`realname`,`nickname`,`mobile`,`phone`,`email`,`remark`,`type`,`created`,`updated`) VALUES (?id,?zone,?name,?password,?enable,?realname,?nickname,?mobile,?phone,?email,?remark,?type,?created,?created)";
	private static final String CREATE_USER_ROLES = "INSERT INTO `user_roles` (`role`,`user`,`created`) VALUES (?role,?user,?created)";
	private static final String CREATE_ROLE = "INSERT INTO `roles` (`id`,`name`,`remark`,`created`,`updated`) VALUES (?id,?name,?remark,?created,?created)";
	private static final String CREATE_ROLE_POWER = "INSERT INTO `role_powers` (`role`,`command`,`created`) VALUES (?role,?command,?created)";
	private static final String CREATE_ZONE = "INSERT INTO `zones` (`id`,`name`,`parent`,`remark`,`created`,`updated`) VALUES (?id,?name,?parent,?remark,?created,?created)";
	private static final String CREATE_TASK = "INSERT INTO `tasks`(`id`,`name`,`start`,`stop`,`month`,`week`,`day`,`interval`,`repeat`,`read_model`,`write_model`,`read_feature`,`write_feature`,`limit_lower`,`limit_upper`,`feed`,`feed_lower`,`feed_upper`,`role`,`enable`,`remark`,`created`,`updated`) VALUES (?id,?name,?start,?stop,?month,?week,?day,?interval,?repeat,?read_model,?write_model,?read_feature,?write_feature,?limit_lower,?limit_upper,?feed,?feed_lower,?feed_upper,?role,?enable,?remark,?created,?created)";
	private static final String CREATE_TASK_DEVICES = "INSERT INTO `task_devices`(`task`,`device`,`rw`,`created`) VALUES(?task,?device,?rw,?created)";
	private static final String CREATE_DEVICE = "INSERT INTO `devices` (`id`,`zone`,`relay`,`owner`,`vendor`,`kind`,`model`,`link`,`use`,`sn`,`name`,`number`,`username`,`password`,`network_ip`,`network_port`,`serial_port`,`serial_baud_rate`,`load`,`power`,`mutual`,`delay`,`address`,`installed`,`produced`,`longitude`,`latitude`,`altitude`,`phase_check`,`phase_power`,`notice`,`detect`,`remark`,`created`,`updated`) VALUES (?id,?zone,?relay,?owner,?vendor,?kind,?model,?link,?use,?sn,?name,?number,?username,?password,?network_ip,?network_port,?serial_port,?serial_baud_rate,?load,?power,?mutual,?delay,?address,?installed,?produced,?longitude,?latitude,?altitude,?phase_check,?phase_power,?notice,?detect,?remark,?created,?created)";
	private static final String CREATE_DEVICE_STATUS = "INSERT INTO `device_status`(`id`,`device`,`status`,`origin`,`s01`,`s02`,`s03`,`s04`,`s05`,`s06`,`s07`,`s08`,`s09`,`s10`,`s11`,`s12`,`s13`,`s14`,`s15`,`s16`,`s17`,`s18`,`s19`,`s20`,`s21`,`s22`,`s23`,`s24`,`s25`,`s26`,`s27`,`s28`,`s29`,`s30`,`s31`,`s32`,`s33`,`s34`,`s35`,`s36`,`s37`,`s38`,`s39`,`s40`,`s41`,`s42`,`s43`,`s44`,`s45`,`s46`,`s47`,`s48`,`s49`,`s50`,`s51`,`s52`,`s53`,`s54`,`s55`,`s56`,`s57`,`s58`,`s59`,`s60`,`s61`,`s62`,`s63`,`s64`,`created`)VALUES(?id,?device,?status,?origin,?s01,?s02,?s03,?s04,?s05,?s06,?s07,?s08,?s09,?s10,?s11,?s12,?s13,?s14,?s15,?s16,?s17,?s18,?s19,?s20,?s21,?s22,?s23,?s24,?s25,?s26,?s27,?s28,?s29,?s30,?s31,?s32,?s33,?s34,?s35,?s36,?s37,?s38,?s39,?s40,?s41,?s42,?s43,?s44,?s45,?s46,?s47,?s48,?s49,?s50,?s51,?s52,?s53,?s54,?s55,?s56,?s57,?s58,?s59,?s60,?s61,?s62,?s63,?s64,NOW())";
	private static final String CREATE_MAINTAIN = "INSERT INTO `maintains` (`id`,`device`,`repair`,`sn`,`load`,`power`,`radix`,`energy_balance`,`mutual`,`remark`,`created`,`updated`) VALUES (?id,?device,?repair,?sn,?load,?power,?radix,?energy_balance,?mutual,?remark,?created,?created)";
	private static final String CREATE_LOG = "INSERT INTO `logs` (`id`,`user`,`name`,`command`,`status`,`time`,`parameter`,`remark`,`created`) VALUES (UUID(),?user,?name,?command,?status,?time,?parameter,?remark,NOW())";
	private static final String CREATE_NOTICE = "INSERT INTO `notice` (`id`,`device`,`user`,`content`) VALUES(UUID(),?device,?user,?content)";
	private static final String CREATE_SCENE = "INSERT INTO `scenes` (`id`,`name`,`image`,`remark`,`created`,`updated`) VALUES (?id,?name,?image,?remark,?created,?created)";
	private static final String CREATE_SCENE_DEVICE = "INSERT INTO `scene_devices` (`scene`,`device`,`switch_status`,`feature_index`,`feature_value`) VALUES (?scene,?device,?switch_status,?feature_index,?feature_value)";
	private static final String CREATE_LINKAGE = "INSERT INTO `linkages` (`id`,`name`,`enable`,`remark`) VALUES (?id,?name,?enable,?remark)";
	private static final String CREATE_LINKAGE_DEVICES = "INSERT INTO `linkage_devices` (`id`,`linkage_id`,`device_id`,`priority`,`feature_index`,`feature_value`,`secs`,`created`,`updated`) VALUES (?id,?linkage_id,?device_id,?priority,?feature_index,?feature_value,?secs,?created,?created)";
	
	private static final String UPDATE_USER = "UPDATE `users` SET `zone`=?zone,`name`=?name,`password`=?password,`enable`=?enable,`realname`=?realname,`nickname`=?nickname,`mobile`=?mobile,`phone`=?phone,`email`=?email,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String DELETE_USER_ROLES = "DELETE FROM `user_roles` WHERE `user`=?user";
	private static final String UPDATE_ROLE = "UPDATE `roles` SET `name`=?name,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String DELETE_ROLE_POWER = "DELETE FROM `role_powers` WHERE `role`=?role";
	private static final String UPDATE_ZONE = "UPDATE `zones` SET `name`=?name,`parent`=?parent,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String UPDATE_TASK = "UPDATE `tasks` SET `name`=?name,`start`=?start,`stop`=?stop,`month`=?month,`week`=?week,`day`=?day,`interval`=?interval,`repeat`=?repeat,`read_model`=?read_model,`write_model`=?write_model,`read_feature`=?read_feature,`write_feature`=?write_feature,`limit_lower`=?limit_lower,`limit_upper`=?limit_upper,`feed`=?feed,`feed_lower`=?feed_lower,`feed_upper`=?feed_upper,`role`=?role,`enable`=?enable,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String DELETE_TASK_DEVICES = "DELETE FROM `task_devices` WHERE `task`=?task";
	private static final String UPDATE_DEVICE = "UPDATE `devices` SET `zone`=?zone,`relay`=?relay,`owner`=?owner,`vendor`=?vendor,`kind`=?kind,`model`=?model,`link`=?link,`use`=?use,`sn`=?sn,`name`=?name,`number`=?number,`username`=?username,`password`=?password,`network_ip`=?network_ip,`network_port`=?network_port,`serial_port`=?serial_port,`serial_baud_rate`=?serial_baud_rate,`load`=?load,`power`=?power,`mutual`=?mutual,`delay`=?delay,`address`=?address,`installed`=?installed,`produced`=?produced,`longitude`=?longitude,`latitude`=?latitude,`altitude`=?altitude,`phase_check`=?phase_check,`phase_power`=?phase_power,`notice`=?notice,`detect`=?detect,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String UPDATE_MAINTAIN = "UPDATE `maintains` SET `device`=?device,`repair`=?repair,`sn`=?sn,`load`=?load,`power`=?power,`radix`=?radix,`energy_balance`=?energy_balance,`mutual`=?mutual,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String UPLOAD_USER_ICON = "UPDATE `users` SET `icon_url`=?icon_url WHERE `id`=?id";
	private static final String UPDATE_SCENE = "UPDATE `scenes` SET `name`=?name,`switch_status`=?switch_status,`image`=?image,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	private static final String DELETE_SCENE_DEVICES = "DELETE FROM `scene_devices` WHERE `scene`=?scene";
	private static final String UPDATE_SCENE_STATUS = "UPDATE `scenes` SET `switch_status`=?swicth_status WHERE `id`=?id";
	private static final String UPDATE_LINKAGE = "UPDATE `linkages` SET `name`=?name,`enable`=?enable,`remark`=?remark WHERE `id`=?id";
	private static final String DELETE_LINKAGE_DEVICES = "DELETE FROM `linkage_devices` WHERE `linkage_id`=?linkage_id";
	private static final String UPDATE_LINKAGE_STATUS = "UPDATE `linkages` SET `enable`=?enable WHERE `id`=?id";

	private static final String DELETE_USER = "DELETE FROM `users` WHERE `id`=?id";
	private static final String DELETE_ROLE = "DELETE FROM `roles` WHERE `id`=?id";
	private static final String DELETE_ZONE = "DELETE FROM `zones` WHERE `id`=?id";
	private static final String DELETE_TASK = "DELETE FROM `tasks` WHERE `id`=?id";
	private static final String DELETE_DEVICE = "DELETE FROM `devices` WHERE `id`=?id";
	private static final String DELETE_MAINTAIN = "DELETE FROM `maintains` WHERE `id`=?id";
	private static final String DELETE_SCENE = "DELETE FROM `scenes` WHERE `id`=?id";
	private static final String DELETE_LINKAGE = "DELETE FROM `linkages` WHERE `id`=?id"; 
	
	private static final String SELECT_PUSHES = "SELECT * FROM `push`";
	private static final String SELECT_PUSH = "SELECT * FROM `push` WHERE `user_id`=?user_id";
	private static final String CREATE_PUSH = "INSERT INTO `push` (`id`,`user_id`,`device_tokens`,`device_type`,`remark`,`created`,`updated`) VALUES (?id,?user_id,?device_tokens,?device_type,?remark,?created,?updated)";
	private static final String UPDATE_PUSH = "UPDATE `push` SET `device_tokens`=?device_tokens,`device_type`=?device_type,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
//	private static final String DELETE_PUSH = "DELETE FROM `push` WHERE `id`=?id";
	
	private static final String SELECT_DEVICE_LOGINS = "SELECT * FROM `device_login` WHERE `user_id`=?user_id";
	private static final String CREATE_DEVICE_LOGIN = "INSERT INTO `device_login` (`id`,`user_id`,`device_id`,`device_type`,`status`,`remark`,`created`,`updated`) VALUES (?id,?user_id,?device_id,?device_type,?status,?remark,?created,?updated)";
	private static final String UPDATE_DEVICE_LOGIN = "UPDATE `device_login` SET `user_id`=?user_id,`device_id`=?device_id,`device_type`=?device_type, `status`=?status,`remark`=?remark,`updated`=?updated WHERE `id`=?id";
	
	private static final String SELECT_CAMERAS = "SELECT * FROM `camera`";
	private static final String CREATE_CAMERA = "INSERT INTO `camera` (`id`,`name`,`password`,`device_type`,`remark`,`created`,`updated`) VALUES (?id,?name,?password,?device_type,?remark,?created,?updated)";
	
	private static final String SELECT_XM_DEVICE = "SELECT * FROM `xm_device`";
	private static final String CREATE_XM_DEVICE = "INSERT INTO `xm_device` (`id`,`user_id`,`device_mac`,`device_name`,`login_name`,`login_psw`,`device_ip`,`state`,`n_port`,`n_type`,`n_id`,`remark`) VALUES (?id,?userId,?deviceMac,?deviceName,?loginName,?loginPsw,?deviceIp,?state,?nPort,?nType,?nId,?remark)";
	private static final String MODIFY_XM_DEVICE_NAME = "UPDATE `xm_device` SET `device_name`=?deviceName WHERE `device_mac`=?deviceMac";
	private static final String DELETE_XM_DEVICE = "DELETE FROM `xm_device` WHERE `device_mac`=?deviceMac";
	
	private static final String SELECT_QUESTIONS = "SELECT * FROM `question` WHERE `user_id`=?user_id";
	private static final String CREATE_QUESTION = "INSERT INTO `question` (`id`,`user_id`,`question_number`,`question_answer`,`remark`,`created`,`updated`) VALUES (?id,?user_id,?question_number,?question_answer,?remark,?created,?updated)";

	public void refresh(Servo s) {
		NSQL sql1 = NSQL.get(SELECT_DEVICES);
		NSQL sql2 = NSQL.get(SELECT_TASKS);
		NSQL sql3 = NSQL.get(SELECT_ZONES);
		NSQL sql4 = NSQL.get(SELECT_USERS);
		NSQL sql5 = NSQL.get(SELECT_USER_ROLES);
		NSQL sql6 = NSQL.get(SELECT_ROLES);
		NSQL sql7 = NSQL.get(SELECT_ROLE_POWERS);
		NSQL sql8 = NSQL.get(SELECT_TASK_DEVICES);
		NSQL sql9 = NSQL.get(SELECT_SCENE);
		NSQL sql10 = NSQL.get(SELECT_SCENE_DEVICE);
		NSQL sql11 = NSQL.get(SELECT_LINKAGE);
		NSQL sql12 = NSQL.get(SELECT_LINKAGE_DEVICES);
		NSQL sql13 = NSQL.get(SELECT_PUSHES);

		Connection connection = dbc.get();
		// DEVICE
		ResultSet rs14 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql1.getSql())) {
			sql1.setParameter(ps, "updated", null);
			rs14 = ps.executeQuery();
			while (rs14.next()) {
				Device device = Device.instance(Vendor.valueOf(rs14.getInt("vendor")), Kind.valueOf(rs14.getInt("kind")),Model.valueOf(rs14.getInt("model")));
				device.setId(rs14.getString("id"));
				device.setZoneId(rs14.getString("zone"));
				device.setRelayId(rs14.getString("relay"));
				device.setOwnerId(rs14.getString("owner"));
				device.setVendor(Vendor.valueOf(rs14.getInt("vendor")));
				device.setKind(Kind.valueOf(rs14.getInt("kind")));
				device.setModel(Model.valueOf(rs14.getInt("model")));
				device.setLink(Link.valueOf(rs14.getInt("link")));
				device.setUse(Use.valueOf(rs14.getInt("use")));
				device.setSn(rs14.getString("sn"));
				device.setName(rs14.getString("name"));
				device.setNumber(rs14.getString("number"));
				device.setUsername(rs14.getString("username"));
				device.setPassword(rs14.getString("password"));
				device.setNetworkIp(rs14.getString("network_ip"));
				device.setNetworkPort(rs14.getInt("network_port"));
				device.setSerialPort(rs14.getString("serial_port"));
				device.setSerialBaudRate(rs14.getInt("serial_baud_rate"));
				device.setLoad(rs14.getInt("load"));
				device.setPower(rs14.getInt("power"));
				device.setMutual(rs14.getFloat("mutual"));
				device.setDelay(rs14.getInt("delay"));
				device.setAddress(rs14.getString("address"));
				device.setInstalled(rs14.getTimestamp("installed"));
				device.setProduced(rs14.getTimestamp("produced"));
				device.setLongitude(rs14.getDouble("longitude"));
				device.setLatitude(rs14.getDouble("latitude"));
				device.setAltitude(rs14.getFloat("altitude"));
				device.setPhaseCheck(rs14.getInt("phase_check"));
				device.setPhasePower(rs14.getInt("phase_power"));
				device.setNotice(rs14.getBoolean("notice"));
				device.setDetect(rs14.getBoolean("detect"));
				device.setRemark(rs14.getString("remark"));
				device.setCreated(rs14.getTimestamp("created"));
				device.setUpdated(rs14.getTimestamp("updated"));
				s.add(device);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs14);
		}
		// TASK
		ResultSet rs10 = null;
		ResultSet rs11 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql2.getSql());
			PreparedStatement ps2 = connection.prepareStatement(sql8.getSql());
			PreparedStatement ps3 = connection.prepareStatement(sql8.getSql());) {
			sql2.setParameter(ps, "updated", null);
			rs10 = ps.executeQuery();
			while (rs10.next()) {
				Task task = new Task();
				task.setId(rs10.getString("id"));
				task.setName(rs10.getString("name"));
				task.setStart(rs10.getTimestamp("start"));
				task.setStop(rs10.getTimestamp("stop"));
				task.setMonth(rs10.getInt("month"));
				task.setWeek(rs10.getInt("week"));
				task.setDay(rs10.getInt("day"));
				task.setInterval(rs10.getInt("interval"));
				task.setRepeat(rs10.getInt("repeat"));
				task.setReadModel(Model.valueOf(rs10.getInt("read_model")));
				task.setWriteModel(Model.valueOf(rs10.getInt("write_model")));
				task.setReadFeature(rs10.getInt("read_feature"));
				task.setWriteFeature(rs10.getInt("write_feature"));
				task.setLimitLower(rs10.getInt("limit_lower"));
				task.setLimitUpper(rs10.getInt("limit_upper"));
				task.setFeed(rs10.getInt("feed"));
				task.setFeedLower(rs10.getInt("feed_lower"));
				task.setFeedUpper(rs10.getInt("feed_upper"));
				task.setRoleId(rs10.getString("role"));
				task.setEnable(rs10.getBoolean("enable"));
				task.setRemark(rs10.getString("remark"));
				task.setCreated(rs10.getTimestamp("created"));
				task.setUpdated(rs10.getTimestamp("updated"));
				// 添加检测设备
				sql8.setParameter(ps2, "task", task.getId());
				sql8.setParameter(ps2, "rw", 0);
				rs11 = ps2.executeQuery();
				while (rs11.next()) {
					task.addReadDeviceById(rs11.getString("device"));
				}
				// 添加控制设备
				sql8.setParameter(ps3, "task", task.getId());
				sql8.setParameter(ps3, "rw", 1);
				ResultSet rs3 = ps3.executeQuery();
				while (rs3.next()) {
					task.addWriteDeviceById(rs3.getString("device"));
				}
				s.add(task);
			}

		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs10);
			dbc.closeRS(rs11);
		}
		// ZONE
		ResultSet rs = null;
		try (PreparedStatement ps = connection.prepareStatement(sql3.getSql())) {
			sql3.setParameter(ps, "updated", null);
			rs = ps.executeQuery();
			while (rs.next()) {
				Zone zone = new Zone();
				zone.setId(rs.getString("id"));
				zone.setName(rs.getString("name"));
				zone.setParentId(rs.getString("parent"));
				zone.setRemark(rs.getString("remark"));
				zone.setCreated(rs.getTimestamp("created"));
				zone.setUpdated(rs.getTimestamp("updated"));
				s.add(zone);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs);
		}
		// USER
		ResultSet rs9 = null;
		ResultSet rs8 = null;
		try (PreparedStatement ps1 = connection.prepareStatement(sql4.getSql());
			PreparedStatement ps2 = connection.prepareStatement(sql5.getSql());) {

			sql4.setParameter(ps1, "updated", null);
			rs9 = ps1.executeQuery();
			while (rs9.next()) {
				User user = new User();
				user.setId(rs9.getString("id"));
				user.setZoneId(rs9.getString("zone"));
				user.setName(rs9.getString("name"));
				user.setEnable(rs9.getBoolean("enable"));
				user.setPassword(rs9.getString("password"));
				user.setRealname(rs9.getString("realname"));
				user.setNickname(rs9.getString("nickname"));
				user.setMobile(rs9.getString("mobile"));
				user.setPhone(rs9.getString("phone"));
				user.setEmail(rs9.getString("email"));
				user.setIconUrl(rs9.getString("icon_url"));
				user.setType(rs9.getInt("type"));
				user.setRemark(rs9.getString("remark"));
				user.setCreated(rs9.getTimestamp("created"));
				user.setUpdated(rs9.getTimestamp("updated"));
				
				sql5.setParameter(ps2, "user", user.getId());
				rs8 = ps2.executeQuery();
				while (rs8.next()) {
					user.addRole(rs8.getString("role"));
				}
				s.add(user);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs8);
			dbc.closeRS(rs9);
		}
		// ROLE
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		try (PreparedStatement ps1 = connection.prepareStatement(sql6.getSql());
			PreparedStatement ps2 = connection.prepareStatement(sql7.getSql());) {

			sql6.setParameter(ps1, "updated", null);
			rs6 = ps1.executeQuery();
			while (rs6.next()) {
				Role role = new Role();
				role.setId(rs6.getString("id"));
				role.setName(rs6.getString("name"));
				role.setRemark(rs6.getString("remark"));
				role.setCreated(rs6.getTimestamp("created"));
				role.setUpdated(rs6.getTimestamp("updated"));

				sql7.setParameter(ps2, "role", role.getId());
				rs7 = ps2.executeQuery();
				while (rs7.next()) {
					Integer c = rs7.getInt("command");
					if (c != null)
						role.addCommand(c);
				}
				rs7.close();
				s.add(role);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs6);
			dbc.closeRS(rs7);
		}

		// SCENE
		ResultSet rs0 = null;
		ResultSet rs1 = null;
		try (PreparedStatement ps1 = connection.prepareStatement(sql9.getSql());
			PreparedStatement ps2 = connection.prepareStatement(sql10.getSql());) {

			sql6.setParameter(ps1, "updated", null);
			rs0 = ps1.executeQuery();
			while (rs0.next()) {
				Scene scene = new Scene();
				scene.setId(rs0.getString("id"));
				scene.setName(rs0.getString("name"));
				scene.setRemark(rs0.getString("remark"));
				scene.setImage(rs0.getInt("image"));
				scene.setCreated(rs0.getTimestamp("created"));
				scene.setUpdated(rs0.getTimestamp("updated"));
				scene.setSwitchStatu(rs0.getBoolean("switch_status"));
				sql10.setParameter(ps2, "scene", scene.getId());
				rs1 = ps2.executeQuery();
				while (rs1.next()) {
					SceneDevice sceneDevice = new SceneDevice();
					sceneDevice.setDeviceId(rs1.getString("device"));
					sceneDevice.setSwitchStatus(rs1.getBoolean("switch_status"));
					sceneDevice.setFeatureIndex(rs1.getInt("feature_index"));
					sceneDevice.setFeatureValue(rs1.getInt("feature_value"));
					if (sceneDevice.getSwitchStatus()) {
						scene.addOpenSceneDevice(sceneDevice);
					} else {
						scene.addOffSceneDevice(sceneDevice);
					}
					
				}
				rs1.close();
				s.add(scene);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs1);
			dbc.closeRS(rs0);
			dbc.put(connection);
		}

		// LINKAGE
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try (PreparedStatement ps1 = connection
				.prepareStatement(sql11.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql12
						.getSql());) {

			rs2 = ps1.executeQuery();
			while (rs2.next()) {
				Linkage linkage = new Linkage();
				linkage.setId(rs2.getString("id"));
				linkage.setName(rs2.getString("name"));
				linkage.setEnable(rs2.getBoolean("enable"));
				linkage.setRemark(rs2.getString("remark"));
				linkage.setCreated(rs2.getTimestamp("created"));
				linkage.setUpdated(rs2.getTimestamp("updated"));
				sql12.setParameter(ps2, "linkage_id", linkage.getId());
				rs3 = ps2.executeQuery();
				while (rs3.next()) {
					LinkageDevice linkageDevice = new LinkageDevice();
					linkageDevice.setId(rs3.getString("id"));
					linkageDevice.setLinkageId(rs3.getString("linkage_id"));
					linkageDevice.setDeviceId(rs3.getString("device_id"));
					linkageDevice.setPriority(rs3.getInt("priority"));
					linkageDevice.setFeatureIndex(rs3.getInt("feature_index"));
					linkageDevice.setFeatureValue(rs3.getInt("feature_value"));
					linkageDevice.setSecs(rs3.getInt("secs"));
					linkageDevice.setCreated(rs3.getDate("created"));
					linkageDevice.setUpdated(rs3.getDate("updated"));
					linkage.addLinkageDevice(linkageDevice);
				}
				rs3.close();
				s.add(linkage);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs3);
			dbc.closeRS(rs2);
			dbc.put(connection);
		}

		// PUSH
		ResultSet rs13 = null;
		try (PreparedStatement ps13 = connection.prepareStatement(sql13
				.getSql())) {
			rs13 = ps13.executeQuery();
			while (rs13.next()) {
				Push push = new Push();
				push.setId(rs13.getString("id"));
				push.setUserId(rs13.getString("user_id"));
				push.setDeviceTokens(rs13.getString("device_tokens"));
				push.setDeviceType(Integer.parseInt(rs13
						.getString("device_type")));
				push.setRemark(rs13.getString("remark"));
				push.setCreated(rs13.getTimestamp("created"));
				push.setUpdated(rs13.getTimestamp("updated"));
				s.add(push);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs13);
			dbc.put(connection);
		}

		dbc.put(connection);
	}
	

	@Override
	public Set<String> SelectDeletes(Date d) {
		NSQL sql = NSQL.get(SELECT_DELETES);

		Set<String> items = new HashSet<>();
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "created", d);
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				items.add(rs5.getString("id"));
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return items;
	}

	@Override
	public List<Device> QueryDeviceStatus(Device device, Date begin, Date end) {
		NSQL sql = NSQL.get(QUERY_DEVICE_STATUS);

		List<Device> items = new ArrayList<>();
		Connection connection = dbc.get();
		ResultSet rs4 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "device", device.getId());
			sql.setParameter(ps, "begin", begin);
			sql.setParameter(ps, "end", end);
			rs4 = ps.executeQuery();
			while (rs4.next()) {
				Device d = Device.instance(device.getVendor(), device.getKind(),device.getModel());
				d.setId(rs4.getString("device"));
				// 借用delay暂存origin
				d.setDelay(rs4.getInt("origin"));
				d.setStatus(Status.valueOf(rs4.getInt("status")));
				Feature[] fs = d.getFeatures();
				for (int index = 0; index < fs.length; index++) {
					fs[index].setValue(rs4.getInt(String.format("s%02d", index + 1)));
				}
				d.setCreated(rs4.getTimestamp("created"));
				items.add(d);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs4);
			dbc.put(connection);
		}
		return items;
	}

	@Override
	public List<Maintain> QueryDeviceMaintain(String id, Date begin, Date end) {
		NSQL sql = NSQL.get(QUERY_DEVICE_MAINTAIN);

		List<Maintain> items = new ArrayList<>();
		Connection connection = dbc.get();
		ResultSet rs = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "device", id);
			sql.setParameter(ps, "begin", begin);
			sql.setParameter(ps, "end", end);
			rs = ps.executeQuery();
			while (rs.next()) {
				Maintain s = new Maintain();
				s.setId(rs.getString("id"));
				s.setDeviceId(rs.getString("device"));
				s.setRepair(Repair.valueOf(rs.getInt("repair")));
				s.setSn(rs.getString("sn"));
				s.setLoad(rs.getInt("load"));
				s.setPower(rs.getInt("power"));
				s.setRadix(rs.getInt("radix"));
				s.setEnergyBalance(rs.getInt("energy_balance"));
				s.setMutual(rs.getInt("mutual"));
				s.setRemark(rs.getString("remark"));
				s.setUpdated(rs.getTimestamp("updated"));
				s.setCreated(rs.getTimestamp("created"));
				items.add(s);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs);
			dbc.put(connection);
		}
		return items;
	}

	@Override
	public List<ULog> QueryLog(String id, Date begin, Date end) {
		NSQL sql = NSQL.get(QUERY_LOG);

		List<ULog> items = new ArrayList<>();
		Connection connection = dbc.get();
		ResultSet rs3 = null; 
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			if (Tool.isEmpty(id))
				sql.setParameter(ps, "user", null);
			else
				sql.setParameter(ps, "user", id);
			sql.setParameter(ps, "begin", begin);
			sql.setParameter(ps, "end", end);
			rs3 = ps.executeQuery();
			while (rs3.next()) {
				ULog s = new ULog();
				s.setId(rs3.getString("id"));
				s.setUserId(rs3.getString("user"));
				s.setName(rs3.getString("name"));
				s.setCommand(rs3.getInt("command"));
				s.setStatus(rs3.getInt("status"));
				s.setParameter(rs3.getString("parameter"));
				s.setRemark(rs3.getString("remark"));
				s.setCreated(rs3.getTimestamp("created"));
				items.add(s);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs3);
			dbc.put(connection);
		}
		return items;
	}

	@Override
	public boolean MoveZone(Zone z) {
		NSQL sql1 = NSQL.get(MOVE_ZONE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", z.getId());
			sql1.setParameter(s1, "parent", z.getParentId());
			sql1.setParameter(s1, "updated", z.getUpdated());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean MoveDevice(Device d) {
		NSQL sql1 = NSQL.get(MOVE_DEVICE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", d.getId());
			sql1.setParameter(s1, "zone", d.getZoneId());
			sql1.setParameter(s1, "relay", d.getRelayId());
			sql1.setParameter(s1, "updated", d.getUpdated());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean MovePosition(Device d) {
		NSQL sql1 = NSQL.get(MOVE_POSITION);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", d.getId());
			sql1.setParameter(s1, "longitude", d.getLongitude());
			sql1.setParameter(s1, "latitude", d.getLatitude());
			sql1.setParameter(s1, "altitude", d.getAltitude());
			sql1.setParameter(s1, "updated", d.getUpdated());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateUser(User u) {
		NSQL sql1 = NSQL.get(CREATE_USER);
		NSQL sql2 = NSQL.get(CREATE_USER_ROLES);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());
				PreparedStatement s2 = connection.prepareStatement(sql2.getSql());) {
				sql1.setParameter(s1, "id", u.getId());
				sql1.setParameter(s1, "zone", u.getZoneId());
				sql1.setParameter(s1, "name", u.getName());
				sql1.setParameter(s1, "password", u.getPassword());
				sql1.setParameter(s1, "enable", u.getEnable());
				sql1.setParameter(s1, "nickname", u.getNickname());
				sql1.setParameter(s1, "realname", u.getRealname());
				sql1.setParameter(s1, "mobile", u.getMobile());
				sql1.setParameter(s1, "phone", u.getPhone());
				sql1.setParameter(s1, "email", u.getEmail());
				sql1.setParameter(s1, "type", u.getType());
				sql1.setParameter(s1, "remark", u.getRemark());
				sql1.setParameter(s1, "created", u.getCreated());
				if (s1.executeUpdate() == 1) {
					for (String role_id : u.getRoleId()) {
						sql2.setParameter(s2, "user", u.getId());
						sql2.setParameter(s2, "role", role_id);
						sql2.setParameter(s2, "created", u.getCreated());
						s2.executeUpdate();
					}
					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateRole(Role r) {
		NSQL sql1 = NSQL.get(CREATE_ROLE);
		NSQL sql2 = NSQL.get(CREATE_ROLE_POWER);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());
				PreparedStatement s2 = connection.prepareStatement(sql2.getSql())) {
				sql1.setParameter(s1, "id", r.getId());
				sql1.setParameter(s1, "name", r.getName());
				sql1.setParameter(s1, "remark", r.getRemark());
				sql1.setParameter(s1, "created", r.getCreated());
				if (s1.executeUpdate() == 1) {
					for (Integer command : r.getCommands()) {
						if (command != null) {
							sql2.setParameter(s2, "role", r.getId());
							sql2.setParameter(s2, "command", command);
							sql2.setParameter(s2, "created", r.getCreated());
							s2.executeUpdate();
						}
					}
					connection.commit();
					return true;
				}
				return false;
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateZone(Zone z) {
		NSQL sql = NSQL.get(CREATE_ZONE);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", z.getId());
			sql.setParameter(ps, "parent", z.getParentId());
			sql.setParameter(ps, "name", z.getName());
			sql.setParameter(ps, "remark", z.getRemark());
			sql.setParameter(ps, "created", z.getCreated());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateTask(Task t) {
		NSQL sql = NSQL.get(CREATE_TASK);
		NSQL sql2 = NSQL.get(CREATE_TASK_DEVICES);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql2.getSql());) {
				sql.setParameter(ps, "id", t.getId());
				sql.setParameter(ps, "name", t.getName());
				sql.setParameter(ps, "start", t.getStart());
				sql.setParameter(ps, "stop", t.getStop());
				sql.setParameter(ps, "month", t.getMonth());
				sql.setParameter(ps, "week", t.getWeek());
				sql.setParameter(ps, "day", t.getDay());
				sql.setParameter(ps, "interval", t.getInterval());
				sql.setParameter(ps, "repeat", t.getRepeat());
				if (t.getReadModel() != null)
					sql.setParameter(ps, "read_model", t.getReadModel().getValue());
				if (t.getWriteModel() != null)
					sql.setParameter(ps, "write_model", t.getWriteModel().getValue());
				sql.setParameter(ps, "read_feature", t.getReadFeature());
				sql.setParameter(ps, "write_feature", t.getWriteFeature());
				sql.setParameter(ps, "limit_lower", t.getLimitLower());
				sql.setParameter(ps, "limit_upper", t.getLimitUpper());
				sql.setParameter(ps, "feed", t.getFeed());
				sql.setParameter(ps, "feed_lower", t.getFeedLower());
				sql.setParameter(ps, "feed_upper", t.getFeedUpper());
				sql.setParameter(ps, "role", t.getRoleId());
				sql.setParameter(ps, "enable", t.getEnable());
				sql.setParameter(ps, "remark", t.getRemark());
				sql.setParameter(ps, "created", t.getCreated());
				sql.setParameter(ps, "updated", t.getUpdated());

				if (ps.executeUpdate() == 1) {
					if (t.getReadDeviceIds() != null) {
						for (String deviceId : t.getReadDeviceIds()) {
							sql2.setParameter(ps2, "task", t.getId());
							sql2.setParameter(ps2, "device", deviceId);
							sql2.setParameter(ps2, "rw", 0);
							sql2.setParameter(ps2, "created", new Date());
							ps2.executeUpdate();
						}
					}

					if (t.getWriteDeviceIds() != null) {
						for (String deviceId : t.getWriteDeviceIds()) {
							sql2.setParameter(ps2, "task", t.getId());
							sql2.setParameter(ps2, "device", deviceId);
							sql2.setParameter(ps2, "rw", 1);
							sql2.setParameter(ps2, "created", new Date());
							ps2.executeUpdate();
						}
					}
					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}

	@Override
	public boolean CreateDevice(Device d) {
		NSQL sql = NSQL.get(CREATE_DEVICE);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "id", d.getId());
			sql.setParameter(ps, "zone", d.getZoneId());
			sql.setParameter(ps, "relay", d.getRelayId());
			sql.setParameter(ps, "owner", d.getOwnerId());
			sql.setParameter(ps, "vendor", d.getVendor().getValue());
			sql.setParameter(ps, "kind", d.getKind().getValue());
			sql.setParameter(ps, "model", d.getModel().getValue());
			sql.setParameter(ps, "link", d.getLink().getValue());
			sql.setParameter(ps, "use", d.getUse().getValue());
			sql.setParameter(ps, "sn", d.getSn());
			sql.setParameter(ps, "name", d.getName());
			sql.setParameter(ps, "number", d.getNumber());
			sql.setParameter(ps, "username", d.getUsername());
			sql.setParameter(ps, "password", d.getPassword());
			sql.setParameter(ps, "network_ip", d.getNetworkIp());
			sql.setParameter(ps, "network_port", d.getNetworkPort());
			sql.setParameter(ps, "serial_port", d.getSerialPort());
			sql.setParameter(ps, "serial_baud_rate", d.getSerialBaudRate());
			sql.setParameter(ps, "load", d.getLoad());
			sql.setParameter(ps, "power", d.getPower());
			sql.setParameter(ps, "mutual", d.getMutual());
			sql.setParameter(ps, "delay", d.getDelay());
			sql.setParameter(ps, "address", d.getAddress());
			sql.setParameter(ps, "installed", d.getInstalled());
			sql.setParameter(ps, "produced", d.getProduced());
			sql.setParameter(ps, "longitude", d.getLongitude());
			sql.setParameter(ps, "latitude", d.getLatitude());
			sql.setParameter(ps, "altitude", d.getAltitude());
			sql.setParameter(ps, "phase_check", d.getPhaseCheck());
			sql.setParameter(ps, "phase_power", d.getPhasePower());
			sql.setParameter(ps, "notice", d.getNotice());
			sql.setParameter(ps, "detect", d.getDetect());
			sql.setParameter(ps, "remark", d.getRemark());
			sql.setParameter(ps, "created", d.getCreated());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateDeviceStatus(Device d, Origin o) {
		NSQL sql = NSQL.get(CREATE_DEVICE_STATUS);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "id", UUID.randomUUID().toString());
			sql.setParameter(ps, "device", d.getId());
			sql.setParameter(ps, "status", d.getStatus().getValue());
			sql.setParameter(ps, "origin", o.getValue());
			int value = 0;
			for (int index = 0; index < 64; index++) {
				if (index < d.getFeatureCount())
					value = d.getFeature(index).getValue();
				else
					value = 0;
				sql.setParameter(ps, String.format("s%02d", index + 1), value);
			}
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateMaintain(Maintain m) {
		NSQL sql = NSQL.get(CREATE_MAINTAIN);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "id", m.getId());
			sql.setParameter(ps, "device", m.getDeviceId());
			sql.setParameter(ps, "repair", m.getRepair().getValue());
			sql.setParameter(ps, "sn", m.getSn());
			sql.setParameter(ps, "load", m.getLoad());
			sql.setParameter(ps, "power", m.getPower());
			sql.setParameter(ps, "radix", m.getRadix());
			sql.setParameter(ps, "energy_balance", m.getEnergyBalance());
			sql.setParameter(ps, "mutual", m.getMutual());
			sql.setParameter(ps, "remark", m.getRemark());
			sql.setParameter(ps, "created", m.getCreated());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateLog(User u, Message q, Message p, long time) {
		NSQL sql = NSQL.get(CREATE_LOG);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			if (u == null)
				sql.setParameter(ps, "user", null);
			else
				sql.setParameter(ps, "user", u.getId());
			sql.setParameter(ps, "name", "");
			sql.setParameter(ps, "command", q.getActionCase().getNumber());
			if (p != null)
				sql.setParameter(ps, "status", p.getResultValue());
			sql.setParameter(ps, "time", time);
			if (q.toString().length() > 1000) {
				sql.setParameter(ps, "parameter", "data is too long");
			} else {
				sql.setParameter(ps, "parameter", q.toString());
			}
			sql.setParameter(ps, "remark", q.getError());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean CreateNotice(Notice u) {
		NSQL sql = NSQL.get(CREATE_NOTICE);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "device", u.getDeviceId());
			sql.setParameter(ps, "user", u.getUserId());
			sql.setParameter(ps, "content", u.getContent());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateUser(User u) {
		NSQL sql1 = NSQL.get(UPDATE_USER);
		NSQL sql2 = NSQL.get(DELETE_USER_ROLES);
		NSQL sql3 = NSQL.get(CREATE_USER_ROLES);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());
				PreparedStatement s2 = connection.prepareStatement(sql2.getSql());
				PreparedStatement s3 = connection.prepareStatement(sql3.getSql());) {
				sql1.setParameter(s1, "id", u.getId());
				sql1.setParameter(s1, "zone", u.getZoneId());
				sql1.setParameter(s1, "name", u.getName());
				sql1.setParameter(s1, "password", u.getPassword());
				sql1.setParameter(s1, "enable", u.getEnable());
				sql1.setParameter(s1, "realname", u.getRealname());
				sql1.setParameter(s1, "nickname", u.getNickname());
				sql1.setParameter(s1, "mobile", u.getMobile());
				sql1.setParameter(s1, "phone", u.getPhone());
				sql1.setParameter(s1, "type", u.getType());
				sql1.setParameter(s1, "email", u.getEmail());
				sql1.setParameter(s1, "remark", u.getRemark());
				sql1.setParameter(s1, "updated", u.getUpdated());
				if (s1.executeUpdate() == 1) {
					sql2.setParameter(s2, "user", u.getId());
					if (s2.executeUpdate() >= 0) {
						for (String role_id : u.getRoleId()) {
							sql3.setParameter(s3, "user", u.getId());
							sql3.setParameter(s3, "role", role_id);
							sql3.setParameter(s3, "created", u.getUpdated());
							s3.executeUpdate();
						}
						connection.commit();
						return true;
					}
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateRole(Role r) {
		NSQL sql1 = NSQL.get(UPDATE_ROLE);
		NSQL sql2 = NSQL.get(DELETE_ROLE_POWER);
		NSQL sql3 = NSQL.get(CREATE_ROLE_POWER);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());
				PreparedStatement s2 = connection.prepareStatement(sql2.getSql());
				PreparedStatement s3 = connection.prepareStatement(sql3.getSql());) {
				sql1.setParameter(s1, "id", r.getId());
				sql1.setParameter(s1, "name", r.getName());
				sql1.setParameter(s1, "remark", r.getRemark());
				sql1.setParameter(s1, "updated", r.getUpdated());
				if (s1.executeUpdate() == 1) {
					sql2.setParameter(s2, "role", r.getId());
					if (s2.executeUpdate() >= 0) {
						for (Integer command : r.getCommands()) {
							sql3.setParameter(s3, "role", r.getId());
							sql3.setParameter(s3, "command", command);
							sql3.setParameter(s3, "created", r.getUpdated());
							s3.executeUpdate();
						}
						connection.commit();
						return true;
					}
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateZone(Zone z) {
		NSQL sql1 = NSQL.get(UPDATE_ZONE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", z.getId());
			sql1.setParameter(s1, "name", z.getName());
			sql1.setParameter(s1, "parent", z.getParentId());
			sql1.setParameter(s1, "remark", z.getRemark());
			sql1.setParameter(s1, "updated", z.getUpdated());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateTask(Task t) {
		NSQL sql1 = NSQL.get(UPDATE_TASK);
		NSQL sql2 = NSQL.get(DELETE_TASK_DEVICES);
		NSQL sql3 = NSQL.get(CREATE_TASK_DEVICES);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());
				PreparedStatement s2 = connection.prepareStatement(sql2.getSql());
				PreparedStatement s3 = connection.prepareStatement(sql3.getSql());) {
				sql1.setParameter(s1, "id", t.getId());
				sql1.setParameter(s1, "name", t.getName());
				sql1.setParameter(s1, "start", t.getStart());
				sql1.setParameter(s1, "stop", t.getStop());
				sql1.setParameter(s1, "month", t.getMonth());
				sql1.setParameter(s1, "week", t.getWeek());
				sql1.setParameter(s1, "day", t.getDay());
				sql1.setParameter(s1, "interval", t.getInterval());
				sql1.setParameter(s1, "repeat", t.getRepeat());
				sql1.setParameter(s1, "read_model", t.getReadModel().getValue());
				sql1.setParameter(s1, "write_model", t.getWriteModel().getValue());
				sql1.setParameter(s1, "read_feature", t.getReadFeature());
				sql1.setParameter(s1, "write_feature", t.getWriteFeature());
				sql1.setParameter(s1, "limit_lower", t.getLimitLower());
				sql1.setParameter(s1, "limit_upper", t.getLimitUpper());
				sql1.setParameter(s1, "feed", t.getFeed());
				sql1.setParameter(s1, "feed_upper", t.getFeedUpper());
				sql1.setParameter(s1, "feed_lower", t.getFeedLower());
				sql1.setParameter(s1, "role", t.getRoleId());
				sql1.setParameter(s1, "enable", t.getEnable());
				sql1.setParameter(s1, "remark", t.getRemark());
				sql1.setParameter(s1, "updated", t.getUpdated());
				if (s1.executeUpdate() == 1) {
					sql2.setParameter(s2, "task", t.getId());
					if (s2.executeUpdate() >= 0) {
						if (t.getReadDeviceIds() != null) {
							for (String deviceId : t.getReadDeviceIds()) {
								sql3.setParameter(s3, "task", t.getId());
								sql3.setParameter(s3, "device", deviceId);
								sql3.setParameter(s3, "rw", 0);
								sql3.setParameter(s3, "created", new Date());
								s3.executeUpdate();
							}
						}

						if (t.getWriteDeviceIds() != null) {
							for (String deviceId : t.getWriteDeviceIds()) {
								sql3.setParameter(s3, "task", t.getId());
								sql3.setParameter(s3, "device", deviceId);
								sql3.setParameter(s3, "rw", 1);
								sql3.setParameter(s3, "created", new Date());
								s3.executeUpdate();
							}
						}
					}
					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateDevice(Device d) {
		NSQL sql = NSQL.get(UPDATE_DEVICE);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", d.getId());
			sql.setParameter(ps, "zone", d.getZoneId());
			sql.setParameter(ps, "relay", d.getRelayId());
			sql.setParameter(ps, "owner", d.getOwnerId());
			sql.setParameter(ps, "vendor", d.getVendor().getValue());
			sql.setParameter(ps, "kind", d.getKind().getValue());
			sql.setParameter(ps, "model", d.getModel().getValue());
			sql.setParameter(ps, "link", d.getLink().getValue());
			sql.setParameter(ps, "use", d.getUse().getValue());
			sql.setParameter(ps, "sn", d.getSn());
			sql.setParameter(ps, "name", d.getName());
			sql.setParameter(ps, "number", d.getNumber());
			sql.setParameter(ps, "username", d.getUsername());
			sql.setParameter(ps, "password", d.getPassword());
			sql.setParameter(ps, "network_ip", d.getNetworkIp());
			sql.setParameter(ps, "network_port", d.getNetworkPort());
			sql.setParameter(ps, "serial_port", d.getSerialPort());
			sql.setParameter(ps, "serial_baud_rate", d.getSerialBaudRate());
			sql.setParameter(ps, "load", d.getLoad());
			sql.setParameter(ps, "power", d.getPower());
			sql.setParameter(ps, "mutual", d.getMutual());
			sql.setParameter(ps, "delay", d.getDelay());
			sql.setParameter(ps, "address", d.getAddress());
			sql.setParameter(ps, "installed", d.getInstalled());
			sql.setParameter(ps, "produced", d.getProduced());
			sql.setParameter(ps, "longitude", d.getLongitude());
			sql.setParameter(ps, "latitude", d.getLatitude());
			sql.setParameter(ps, "altitude", d.getAltitude());
			sql.setParameter(ps, "phase_check", d.getPhaseCheck());
			sql.setParameter(ps, "phase_power", d.getPhasePower());
			sql.setParameter(ps, "notice", d.getNotice());
			sql.setParameter(ps, "detect", d.getDetect());
			sql.setParameter(ps, "remark", d.getRemark());
			sql.setParameter(ps, "updated", d.getUpdated());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UpdateMaintain(Maintain m) {
		NSQL sql = NSQL.get(UPDATE_MAINTAIN);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", m.getId());
			sql.setParameter(ps, "device", m.getDeviceId());
			sql.setParameter(ps, "repair", m.getRepair().getValue());
			sql.setParameter(ps, "sn", m.getSn());
			sql.setParameter(ps, "load", m.getLoad());
			sql.setParameter(ps, "power", m.getPower());
			sql.setParameter(ps, "radix", m.getRadix());
			sql.setParameter(ps, "energy_balance", m.getEnergyBalance());
			sql.setParameter(ps, "mutual", m.getMutual());
			sql.setParameter(ps, "remark", m.getRemark());
			sql.setParameter(ps, "updated", m.getUpdated());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteUser(User u) {
		NSQL sql1 = NSQL.get(DELETE_USER);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", u.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteRole(Role r) {
		NSQL sql1 = NSQL.get(DELETE_ROLE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", r.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteZone(Zone z) {
		NSQL sql1 = NSQL.get(DELETE_ZONE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", z.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteTask(Task t) {
		NSQL sql1 = NSQL.get(DELETE_TASK);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", t.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteMaintain(String id) {
		NSQL sql = NSQL.get(DELETE_MAINTAIN);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", id);
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteDevice(Device u) {
		NSQL sql = NSQL.get(DELETE_DEVICE);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", u.getId());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean UploadUserIcon(User u) {
		NSQL sql = NSQL.get(UPLOAD_USER_ICON);

		Connection connection = dbc.get();
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", u.getId());
			sql.setParameter(ps, "icon_url", u.getIconUrl());
			return ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public String QueryUserIcon(String id) {

		NSQL sql = NSQL.get(QUERY_USER_ICON);

		Connection connection = dbc.get();
		ResultSet rs13 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql());) {
			sql.setParameter(ps, "id", id);
			rs13 = ps.executeQuery();
			if (rs13.next()) {
				return rs13.getString("icon_url");
			}
		} catch (SQLException ex) {
			Log.error(ex);
			return "";
		} finally {
			dbc.closeRS(rs13);
			dbc.put(connection);
		}
		return "";
	}

	@Override
	public boolean CreateLinkage(Linkage l) {
		NSQL sql = NSQL.get(CREATE_LINKAGE);
		NSQL sql2 = NSQL.get(CREATE_LINKAGE_DEVICES);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql2.getSql());) {
				sql.setParameter(ps, "id", l.getId());
				sql.setParameter(ps, "name", l.getName());
				sql.setParameter(ps, "enable", l.isEnable());
				sql.setParameter(ps, "remark", l.getRemark());
				if (ps.executeUpdate() == 1) {
					if (l.getLinkageDevice() != null) {
						for (LinkageDevice linkageDevice : l.getLinkageDevice()) {
							//判断是否为联动主设备
							if (linkageDevice.getPriority() == 1) {
								//判断是否已被设为联动主设备
								if (CountLinkageDevicePriority(linkageDevice.getDeviceId()) > 0) {
									connection.rollback();
									return false;
								}
							}
							sql2.setParameter(ps2, "id", linkageDevice.getId());
							sql2.setParameter(ps2, "linkage_id", linkageDevice.getLinkageId());
							sql2.setParameter(ps2, "device_id", linkageDevice.getDeviceId());
							sql2.setParameter(ps2, "priority", linkageDevice.getPriority());
							sql2.setParameter(ps2, "feature_index", linkageDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", linkageDevice.getFeatureValue());
							sql2.setParameter(ps2, "secs", linkageDevice.getSecs());
							sql2.setParameter(ps2, "created", linkageDevice.getCreated());
							sql2.setParameter(ps2, "updated", linkageDevice.getUpdated());
							ps2.executeUpdate();
						}
					}
					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}

	@Override
	public boolean DeleteLinkage(Linkage l) {
		NSQL sql1 = NSQL.get(DELETE_LINKAGE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", l.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	
	@Override
	public boolean CreateScene(Scene s) {
		NSQL sql = NSQL.get(CREATE_SCENE);
		NSQL sql2 = NSQL.get(CREATE_SCENE_DEVICE);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql2.getSql());) {
				sql.setParameter(ps, "id", s.getId());
				sql.setParameter(ps, "name", s.getName());
				sql.setParameter(ps, "image", s.getImage());
				sql.setParameter(ps, "remark", s.getRemark());
				sql.setParameter(ps, "created", s.getCreated());
				if (ps.executeUpdate() == 1) {
					if (s.getOpenDevices() != null) {
						for (SceneDevice openDevice : s.getOpenDevices()) {
							sql2.setParameter(ps2, "scene", s.getId());
							sql2.setParameter(ps2, "device", openDevice.getDeviceId());
							sql2.setParameter(ps2, "switch_status", openDevice.getSwitchStatus());
							sql2.setParameter(ps2, "feature_index", openDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", openDevice.getFeatureValue());
							ps2.executeUpdate();
						}
					}
					if (s.getOffDevices() != null) {
						for (SceneDevice offDevice : s.getOffDevices()) {
							sql2.setParameter(ps2, "scene", s.getId());
							sql2.setParameter(ps2, "device", offDevice.getDeviceId());
							sql2.setParameter(ps2, "switch_status", offDevice.getSwitchStatus());
							sql2.setParameter(ps2, "feature_index", offDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", offDevice.getFeatureValue());
							ps2.executeUpdate();
						}
					}

					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}

	@Override
	public boolean UpdateScene(Scene s) {
		NSQL sql = NSQL.get(UPDATE_SCENE);
		NSQL sql3 = NSQL.get(DELETE_SCENE_DEVICES);
		NSQL sql2 = NSQL.get(CREATE_SCENE_DEVICE);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql2.getSql());
				PreparedStatement ps3 = connection.prepareStatement(sql3.getSql());) {
				sql.setParameter(ps, "id", s.getId());
				sql.setParameter(ps, "name", s.getName());
				sql.setParameter(ps, "switch_status", s.getSwitchStatu());
				sql.setParameter(ps, "image", s.getImage());
				sql.setParameter(ps, "remark", s.getRemark());
				sql.setParameter(ps, "updated", s.getUpdated());
				if (ps.executeUpdate() == 1) {
					/* 删除已有的 */
					sql3.setParameter(ps3, "scene", s.getId());
					ps3.executeUpdate();
					if (s.getOpenDevices() != null) {
						for (SceneDevice openDevice : s.getOpenDevices()) {
							sql2.setParameter(ps2, "scene", s.getId());
							sql2.setParameter(ps2, "device", openDevice.getDeviceId());
							sql2.setParameter(ps2, "switch_status", openDevice.getSwitchStatus());
							sql2.setParameter(ps2, "feature_index", openDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", openDevice.getFeatureValue());
							ps2.executeUpdate();
						}
					}
					if (s.getOffDevices() != null) {
						for (SceneDevice offDevice : s.getOffDevices()) {
							sql2.setParameter(ps2, "scene", s.getId());
							sql2.setParameter(ps2, "device", offDevice.getDeviceId());
							sql2.setParameter(ps2, "switch_status", offDevice.getSwitchStatus());
							sql2.setParameter(ps2, "feature_index", offDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", offDevice.getFeatureValue());
							ps2.executeUpdate();
						}
					}

					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}

	@Override
	public boolean UpdateLinkage(Linkage l) {
		NSQL sql  = NSQL.get(UPDATE_LINKAGE);
		NSQL sql2 = NSQL.get(CREATE_LINKAGE_DEVICES);
		NSQL sql3 = NSQL.get(DELETE_LINKAGE_DEVICES);
		NSQL sql4 = NSQL.get(SELECT_LINKAGEID_BY_DEVICEID);

		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());
				PreparedStatement ps2 = connection.prepareStatement(sql2.getSql());
				PreparedStatement ps3 = connection.prepareStatement(sql3.getSql());
				PreparedStatement ps4 = connection.prepareStatement(sql4.getSql());) {
				sql.setParameter(ps, "id", l.getId());
				sql.setParameter(ps, "name", l.getName());
				sql.setParameter(ps, "enable", l.isEnable());
				sql.setParameter(ps, "remark", l.getRemark());
				if (ps.executeUpdate() == 1) {
					/* 删除已有的 */
					sql3.setParameter(ps3, "linkage_id", l.getId());
					ps3.executeUpdate();
					if (l.getLinkageDevice()!=null)
						for (LinkageDevice linkageDevice : l.getLinkageDevice()) {
							//判断是否为联动主设备
							if (linkageDevice.getPriority() == 1) {
								//判断是否已被设为联动主设备
								if (CountLinkageDevicePriority(linkageDevice.getDeviceId()) > 0) {
									sql4.setParameter(ps4, "device_id", linkageDevice.getDeviceId());
									ResultSet rs = ps4.executeQuery();     
									if (rs.next()) {
										connection.rollback();
										return false;
									}
								}
							}
							sql2.setParameter(ps2, "id", UUID.randomUUID().toString());
							sql2.setParameter(ps2, "linkage_id", l.getId());
							sql2.setParameter(ps2, "device_id", linkageDevice.getDeviceId());
							sql2.setParameter(ps2, "priority", linkageDevice.getPriority());
							sql2.setParameter(ps2, "feature_index", linkageDevice.getFeatureIndex());
							sql2.setParameter(ps2, "feature_value", linkageDevice.getFeatureValue());
							sql2.setParameter(ps2, "secs", linkageDevice.getSecs());
							sql2.setParameter(ps2, "created", linkageDevice.getCreated());
							sql2.setParameter(ps2, "updated", linkageDevice.getUpdated());
							ps2.executeUpdate();
					}
					connection.commit();
					return true;
				}
				connection.rollback();
				return false;
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}

	@Override
	public boolean UpdateLinkageStatus(String id, boolean linkageStatus) {
		NSQL sql1 = NSQL.get(UPDATE_LINKAGE_STATUS);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql())) {
			sql1.setParameter(s1, "enable", linkageStatus);
			sql1.setParameter(s1, "id", id);
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	
	@Override
	public boolean UpdateSceneStatus(String id, boolean sceneStatus) {
		NSQL sql1 = NSQL.get(UPDATE_SCENE_STATUS);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "swicth_status", sceneStatus);
			sql1.setParameter(s1, "id", id);
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}

	@Override
	public boolean DeleteScene(Scene s) {
		NSQL sql1 = NSQL.get(DELETE_SCENE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "id", s.getId());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	
	@Override
	public int CountLinkageDevicePriority(String id) {
		NSQL sql1 = NSQL.get(COUNT_LINKAGE_DEVICE_PRIORITY);
		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql())){
			sql1.setParameter(s1, "device_id", id);
			ResultSet rs = s1.executeQuery();
			while (rs.next()) {
				return rs.getInt("count");
			}
			return 2;
		} catch (SQLException e) {
			Log.error(e);
			return 2;
		} finally {
			dbc.put(connection);
		}
	}
	
	@Override
	public Push SelectPush(String userId) {
		NSQL sql = NSQL.get(SELECT_PUSH);

		Push push = null;
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "user_id", userId);
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				push = new Push();
				push.setId(rs5.getString("id"));
				push.setUserId(rs5.getString("user_id"));
				push.setDeviceTokens((rs5.getString("device_tokens")));
				push.setDeviceType(Integer.parseInt((rs5.getString("device_type"))));
				push.setRemark((rs5.getString("remark")));
				push.setCreated(rs5.getTimestamp("created"));
				push.setUpdated(rs5.getTimestamp("updated"));
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return push;
	}

	@Override
	public boolean CreatePush(Push push) {
		NSQL sql = NSQL.get(CREATE_PUSH);
		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());){
				sql.setParameter(ps, "id", push.getId());
				sql.setParameter(ps, "user_id", push.getUserId());
				sql.setParameter(ps, "device_tokens", push.getDeviceTokens());
				sql.setParameter(ps, "device_type", push.getDeviceType());
				sql.setParameter(ps, "remark", push.getRemark());
				sql.setParameter(ps, "created", push.getCreated());
				sql.setParameter(ps, "updated", push.getUpdated());
				if (ps.executeUpdate() == 1) {
					connection.commit();
					return true;
				}else {
					connection.rollback();
					return false;
				}
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}
	@Override
	public boolean CreateDeviceLogin(DeviceLogin deviceLogin) {
		NSQL sql = NSQL.get(CREATE_DEVICE_LOGIN);
		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());){
				sql.setParameter(ps, "id", deviceLogin.getId());
				sql.setParameter(ps, "user_id", deviceLogin.getUserId());
				sql.setParameter(ps, "device_id", deviceLogin.getDeviceId());
				sql.setParameter(ps, "device_type", deviceLogin.getDeviceType());
				sql.setParameter(ps, "status", deviceLogin.getStatus());
				sql.setParameter(ps, "remark", deviceLogin.getRemark());
				sql.setParameter(ps, "created", deviceLogin.getCreated());
				sql.setParameter(ps, "updated", deviceLogin.getUpdated());
				if (ps.executeUpdate() == 1) {
					connection.commit();
					return true;
				}else {
					connection.rollback();
					return false;
				}
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}
	
	@Override
	public boolean UpdateDeviceLogin(DeviceLogin deviceLogin) {
		NSQL sql1 = NSQL.get(UPDATE_DEVICE_LOGIN);
		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql())) {
			sql1.setParameter(s1, "id",deviceLogin.getId());
			sql1.setParameter(s1, "user_id",deviceLogin.getUserId());
			sql1.setParameter(s1, "device_id",deviceLogin.getDeviceId());
			sql1.setParameter(s1, "device_type", deviceLogin.getDeviceType());
			sql1.setParameter(s1, "status", deviceLogin.getStatus());
			sql1.setParameter(s1, "remark", deviceLogin.getRemark());
			sql1.setParameter(s1, "updated", new Date());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	
	@Override
	public Set<DeviceLogin> SelectDeviceLogins(String userId) {
		NSQL sql = NSQL.get(SELECT_DEVICE_LOGINS);

		Set<DeviceLogin> deviceLogins = new HashSet<>();
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "user_id", userId);
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				DeviceLogin deviceLogin = new DeviceLogin();
				deviceLogin.setId(rs5.getString("id"));
				deviceLogin.setUserId(rs5.getString("user_id"));
				deviceLogin.setDeviceId(rs5.getString("device_id"));
				deviceLogin.setDeviceType(rs5.getInt("device_type"));
				deviceLogin.setStatus(rs5.getInt("status"));
				deviceLogin.setRemark(rs5.getString("remark"));
				deviceLogin.setCreated(rs5.getTimestamp("created"));
				deviceLogin.setUpdated(rs5.getTimestamp("updated"));
				deviceLogins.add(deviceLogin);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return deviceLogins;
	}
	
	@Override
	public boolean UpdatePush(Push push) {
		NSQL sql1 = NSQL.get(UPDATE_PUSH);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql())) {
			sql1.setParameter(s1, "id",push.getId());
			sql1.setParameter(s1, "device_tokens",push.getDeviceTokens());
			sql1.setParameter(s1, "device_type", push.getDeviceType());
			sql1.setParameter(s1, "remark", push.getRemark());
			sql1.setParameter(s1, "updated", push.getUpdated());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}


	@Override
	public Set<Push> SelectPushes() {
		NSQL sql = NSQL.get(SELECT_PUSHES);
		Set<Push> pushes = new HashSet<>();
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				Push push = new Push();
				push.setId(rs5.getString("id"));
				push.setUserId(rs5.getString("user_id"));
				push.setDeviceTokens(rs5.getString("device_tokens"));
				push.setDeviceType(Integer.parseInt(rs5.getString("device_type")));
				push.setRemark(rs5.getString("remark"));
				push.setCreated(rs5.getTimestamp("created"));
				push.setUpdated(rs5.getTimestamp("updated"));
				pushes.add(push);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return pushes;
	}
	
	@Override
	public Set<XMCamera> SelectCamera() {
		NSQL sql = NSQL.get(SELECT_CAMERAS);
		Set<XMCamera> cameras = null;
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				cameras = new HashSet<>();
				XMCamera camera = new XMCamera();
				camera.setId(rs5.getString("id"));
				camera.setName(rs5.getString("name"));
				camera.setPassword(rs5.getString("password"));
				camera.setDeviceType(Integer.parseInt(rs5.getString("device_type")));
				camera.setRemark(rs5.getString("remark"));
				camera.setCreated(rs5.getTimestamp("created"));
				camera.setUpdated(rs5.getTimestamp("updated"));
				cameras.add(camera);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return cameras;
	}
	
	@Override
	public boolean CreateCamera(XMCamera camera) {
		NSQL sql = NSQL.get(CREATE_CAMERA);
		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());){
				sql.setParameter(ps, "id", camera.getId());
				sql.setParameter(ps, "name", camera.getName());
				sql.setParameter(ps, "password", camera.getPassword());
				sql.setParameter(ps, "device_type", camera.getDeviceType());
				sql.setParameter(ps, "remark", camera.getRemark());
				sql.setParameter(ps, "created", camera.getCreated());
				sql.setParameter(ps, "updated", camera.getUpdated());
				if (ps.executeUpdate() == 1) {
					connection.commit();
					return true;
				}else {
					connection.rollback();
					return false;
				}
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}
	@Override
	public Set<XMDevice> SelectXMDevice() {
		NSQL sql = NSQL.get(SELECT_XM_DEVICE);
		Set<XMDevice> xmDevices = null;
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			rs5 = ps.executeQuery();
			xmDevices = new HashSet<>();
			while (rs5.next()) {
				XMDevice xmDevice = new XMDevice();
				xmDevice.setId(rs5.getString("id"));
				xmDevice.setUserId(rs5.getString("user_id"));
				xmDevice.setDeviceMac(rs5.getString("device_mac"));
				xmDevice.setDeviceName(rs5.getString("device_name"));
				xmDevice.setLoginName(rs5.getString("login_name"));
				xmDevice.setLoginPsw(rs5.getString("login_psw"));
				xmDevice.setDeviceIp(rs5.getString("device_ip"));
				xmDevice.setState(rs5.getInt("state"));
				xmDevice.setNPort(rs5.getInt("n_port"));
				xmDevice.setNType(rs5.getInt("n_type"));
				xmDevice.setNId(rs5.getInt("n_id"));
				xmDevice.setRemark(rs5.getString("remark"));
				xmDevice.setCreated(rs5.getTimestamp("created"));
				xmDevice.setUpdated(rs5.getTimestamp("updated"));
				xmDevices.add(xmDevice);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return xmDevices;
	}
	
	@Override
	public boolean CreateXMDevice(XMDevice xmDevice) {
		NSQL sql = NSQL.get(CREATE_XM_DEVICE);
		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());){
				sql.setParameter(ps, "id", xmDevice.getId());
				sql.setParameter(ps, "userId", xmDevice.getUserId());
				sql.setParameter(ps, "deviceMac", xmDevice.getDeviceMac());
				sql.setParameter(ps, "deviceName", xmDevice.getDeviceName());
				sql.setParameter(ps, "loginName", xmDevice.getLoginName());
				sql.setParameter(ps, "loginPsw", xmDevice.getLoginPsw());
				sql.setParameter(ps, "deviceIp", xmDevice.getDeviceIp());
				sql.setParameter(ps, "state", xmDevice.getState());
				sql.setParameter(ps, "nPort", xmDevice.getNPort());
				sql.setParameter(ps, "nType", xmDevice.getNType());
				sql.setParameter(ps, "nId", xmDevice.getNId());
				sql.setParameter(ps, "remark", xmDevice.getRemark());
				sql.setParameter(ps, "created", xmDevice.getCreated());
				sql.setParameter(ps, "updated", xmDevice.getUpdated());
				if (ps.executeUpdate() == 1) {
					connection.commit();
					return true;
				}else {
					connection.rollback();
					return false;
				}
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}
	@Override
	public boolean UpdateXMDevice(XMDevice xmDevice) {
		NSQL sql1 = NSQL.get(MODIFY_XM_DEVICE_NAME);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "deviceMac", xmDevice.getDeviceMac());
			sql1.setParameter(s1, "deviceName", xmDevice.getDeviceName());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	@Override
	public boolean DeleteXMDevice(XMDevice xmDevice) {
		NSQL sql1 = NSQL.get(DELETE_XM_DEVICE);

		Connection connection = dbc.get();
		try (PreparedStatement s1 = connection.prepareStatement(sql1.getSql());) {
			sql1.setParameter(s1, "deviceMac", xmDevice.getDeviceMac());
			return s1.executeUpdate() == 1;
		} catch (SQLException ex) {
			Log.error(ex);
			return false;
		} finally {
			dbc.put(connection);
		}
	}
	@Override
	public Set<Question> SelectQuestion(String userId) {
		NSQL sql = NSQL.get(SELECT_QUESTIONS);

		Set<Question> questions = new HashSet<>();
		Connection connection = dbc.get();
		ResultSet rs5 = null;
		try (PreparedStatement ps = connection.prepareStatement(sql.getSql())) {
			sql.setParameter(ps, "user_id", userId);
			rs5 = ps.executeQuery();
			while (rs5.next()) {
				Question question = new Question();
				question.setId(rs5.getString("id"));
				question.setUserId(rs5.getString("user_id"));
				question.setQuestionNumber(rs5.getInt("question_number"));
				question.setQuestionAnswer(rs5.getString("question_answer"));
				question.setRemark(rs5.getString("remark"));
				question.setCreated(rs5.getTimestamp("created"));
				question.setUpdated(rs5.getTimestamp("updated"));
				questions.add(question);
			}
		} catch (SQLException ex) {
			Log.error(ex);
		} finally {
			dbc.closeRS(rs5);
			dbc.put(connection);
		}
		return questions;
	}
	
	@Override
	public boolean CreateQuestion(Question question) {
		NSQL sql = NSQL.get(CREATE_QUESTION);
		Connection connection = dbc.get();
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps = connection.prepareStatement(sql.getSql());){
				sql.setParameter(ps, "id", question.getId());
				sql.setParameter(ps, "user_id", question.getUserId());
				sql.setParameter(ps, "question_number", question.getQuestionNumber());
				sql.setParameter(ps, "question_answer", question.getQuestionAnswer());
				sql.setParameter(ps, "remark", question.getRemark());
				sql.setParameter(ps, "created", question.getCreated());
				sql.setParameter(ps, "updated", question.getUpdated());
				if (ps.executeUpdate() == 1) {
					connection.commit();
					return true;
				}else {
					connection.rollback();
					return false;
				}
			} catch (SQLException ex) {
				Log.error(ex);
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			Log.error(e);
		} finally {
			dbc.put(connection);
		}
		return false;
	}
}

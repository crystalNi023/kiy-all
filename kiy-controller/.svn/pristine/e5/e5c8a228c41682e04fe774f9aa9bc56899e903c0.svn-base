package com.kiy.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Units.MLog;
import com.kiy.resources.Lang;

/**
 * 用于搜索log信息的类
 * 
 * @author HLX
 *
 */
public class SearchLog {
	private List<MLog> searchLogs;
	private Servo servo;

	private Set<MLog> resultLogs;

	public SearchLog(List<MLog> logs, Servo s1) {
		searchLogs = logs;
		servo = s1;
	}

	/**
	 * 默认搜索结果顺序 指令 用户 状态 备注 同一搜索结果不显示多次
	 * 
	 * @param str
	 *            搜索内容
	 * @return
	 */
	private Set<MLog> searchLogs(String str) {
		resultLogs = new HashSet<>();

		if (Tool.isEmpty(str)) {
			resultLogs.addAll(searchLogs);
			return resultLogs;
		}

		if (searchLogs != null && !searchLogs.isEmpty()) {

			/**
			 * 搜索指令
			 */
			for (MLog mlog : searchLogs) {
				int command = mlog.getCommand();
				ActionCase forNumber = ActionCase.forNumber(command);
				if (Lang.getString("Command." + forNumber.name()).contains(str)) {
					resultLogs.add(mlog);
				}
			}

			/**
			 * 搜索用户名
			 */
			for (MLog mlog : searchLogs) {
				String userId = mlog.getUserId();
				User user = servo.getUser(userId);
				if (user != null) {
					if (user.getName().contains(str)) {
						resultLogs.add(mlog);
					}
				}
			}

			/**
			 * 搜索状态
			 */
			for (MLog mlog : searchLogs) {
				int status = mlog.getResult();
				Result forNumber = Result.forNumber(status);
				if (Lang.getString("Status." + forNumber.name()).contains(str)) {
					resultLogs.add(mlog);
				}
			}

			/**
			 * 搜索备注
			 */
			for (MLog mlog : searchLogs) {
				String remark = mlog.getRemark();
				if (remark.contains(str)) {
					resultLogs.add(mlog);
				}
			}
		}
		return resultLogs;
	}

	/**
	 * 搜索日志
	 * 
	 * @return
	 */
	public Set<MLog> search(String searchText) {
		return searchLogs(searchText);
	}
}

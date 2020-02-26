package com.dec.pro.service.project;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.entity.project.Progress;
import com.dec.pro.entity.project.ProgressRecord;
import com.dec.pro.mapper.project.ProgressRecordMapper;
import com.dec.pro.service.BaseService;
@Service
public class ProgressRecordService extends BaseService<ProgressRecord>{
	private static Logger logger=Logger.getLogger(ProgressRecordService.class);
	@Autowired
	ProgressRecordMapper progressRecordMapper;
	@Autowired
	ProgressService progressService;
	@Autowired
	ProcedureService procedureService;
	
	public ProgressRecord getOneByProgId(String progId) throws Exception {
		ProgressRecord progressRecord;
		try {
			progressRecord = progressRecordMapper.getOneByProgId(progId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return progressRecord;
	}
	
	public int updateProgressRecord(ProgressRecord progressRecord) throws Exception {
		int timeAssess = progressRecord.getTimeAssess();
		int count = super.update(progressRecord);
		if (progressRecord.getStatus() == 2) { // 确认通过
			// 有延期提前的需修改项目流程
			Progress progress = progressService.getOne(progressRecord.getProgId());
			Procedure procedure = procedureService.getOne(progress.getProcId());// 获取项目流程

			// 进度为100时Status改为已完成
			if (progress.getSchedule() == 100) {
				System.err.println("设置为100");
				procedure.setProcess(progress.getSchedule());// 设置流程进度
				procedure.setTimeAssess(timeAssess);// 计划整体时间
				procedure.setStatus(3);
			}else if (progressRecord.getTimeAssess() != 0) {
				int orgTimeAssess = procedure.getTimeAssess();
				procedure.setTimeAssess(orgTimeAssess + timeAssess);
			}
			procedure.setNotice(1); // 设置为新通知
			count = procedureService.update(procedure);
		}else if (progressRecord.getStatus() == 3) { // 未通过
			Progress progress;
			Procedure procedure;
			progress = progressService.getOne(progressRecord.getProgId());
			procedure = procedureService.getOne(progress.getProcId());// 获取项目流程
			procedure.setNotice(1); // 设置为新通知
			count = procedureService.update(procedure);
		}
		return count;
	}
}

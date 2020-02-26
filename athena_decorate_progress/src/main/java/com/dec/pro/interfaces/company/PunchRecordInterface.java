package com.dec.pro.interfaces.company;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.company.Position;
import com.dec.pro.entity.company.PunchRecord;
import com.dec.pro.service.company.PositionService;
import com.dec.pro.service.company.PunchRecordService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class PunchRecordInterface {
	private static Logger logger=Logger.getLogger(PunchRecordInterface.class);
	@Autowired
	private PunchRecordService punchRecordService;
	@Autowired
	private PositionService pService;
	
	@RequestMapping(value = "app/punchRecord/increase", name = "录入打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> increasePunchRecord(PunchRecord punchRecord,Position position){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			GetField.getOTM(" APP上班打卡:",punchRecord);
			GetField.getOTM(" APP上班打卡定位:",position);
			position.setId(UUid.getUUid());
			position.setType(2);//类型  (1.轨迹 2.上班打卡位置 3.下班打卡位置 )
			punchRecord.setId(UUid.getUUid());
			punchRecord = punchRecordService.addPunchRecord(punchRecord,position);
			
			code="0000";
			msg = "成功";
			resultMap.put("result", punchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/remove", name = "移除打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> removePunchRecord(String id){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			punchRecordService.deletePunchRecord(id);
			code="0000";
			msg = "成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/modify", name = "下班打卡", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> removePunchRecord(PunchRecord punchRecord,Position position){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			//类型  (1.轨迹 2.上班打卡位置 3.下班打卡位置 )
			position.setType(3);
			GetField.getOTM(" APP下班打卡:",punchRecord);
			GetField.getOTM(" APP下班打卡定位:",position);
			punchRecordService.updatePunchRecord(punchRecord,position);
			code="0000";
			msg = "成功";
			resultMap.put("result", punchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/reportWorkToday", name = "上报今日工作", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> reportWorkToday(PunchRecord punchRecord){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			GetField.getOTM(" APP上报今日工作:",punchRecord);
			// 判断是否打卡（未打卡就新增打卡记录）
			PunchRecord onePunchRecord = punchRecordService.getOnePunchRecord(punchRecord.getConstructorId(), new Date());
			if (onePunchRecord==null) {
				punchRecord.setId(UUid.getUUid());
				punchRecordService.add(punchRecord);
			}else {
				onePunchRecord.setWorkToday(punchRecord.getWorkToday());
				punchRecordService.update(onePunchRecord);
			}
			code="0000";
			msg = "成功";
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	
	@RequestMapping(value = "app/punchRecord/queryPageList", name = "分页查询打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryPageListPunchRecord(PunchRecord punchRecord){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		Page<PunchRecord> pageList;
		String code;
		String msg;
		try {
			pageList = punchRecordService.getPageListPunchRecord(punchRecord);
			code="0000";
			msg = "成功";
			resultMap.put("result", pageList);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/queryOne", name = "查询单个施工员打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryOnePunchRecord(String constructorId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		PunchRecord punchRecord;
		String code;
		String msg;
		try {
			logger.error("查询constructorId："+constructorId);
			logger.error("查询date："+date);
			punchRecord = punchRecordService.getOnePunchRecord(constructorId,date);
			if(punchRecord!=null){
				logger.error("打卡时间id："+punchRecord.getId());
				logger.error("打卡时间WorkingTime："+punchRecord.getWorkingTime());
				logger.error("打卡时间WorkingTime："+punchRecord);
				logger.error("打卡时间系统时间："+new Date());
			}
			code="0000";
			msg = "成功";
			resultMap.put("result", punchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	
	@RequestMapping(value = "app/punchRecord/queryListStaff", name = "主管查询员工打开记录", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryListStaffPunchRecord(String groId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> list;
		String code;
		String msg;
		try {
			list = punchRecordService.getListStaffPunchRecord(groId,date);
			code="0000";
			msg = "成功";
			resultMap.put("result", list);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	
	@RequestMapping(value = "app/punchRecord/queryPeriodByConstructorId", name = "查询单个员工某一时间段打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryPunchRecordPeriodByConstructorId(String constructorId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> listPunchRecord;
		String code;
		String msg;
		try {
			listPunchRecord = punchRecordService.getPunchRecordPeriodByConstructorId(constructorId,startDate,endDate);
			code="0000";
			msg = "成功";
			resultMap.put("result", listPunchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	//新增
	@RequestMapping(value = "app/punchRecord/getListByCompany", name = "查询某天公司下施工员打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getListByCompany(String companyId,String userId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> listPunchRecord;
		String code;
		String msg;
		try {
			listPunchRecord = punchRecordService.getListByCompany(companyId,userId,date);
			code="0000";
			msg = "成功";
			resultMap.put("result", listPunchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/getListCountByCompany", name = "查询某天公司下施工员打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getListCountByCompany(String companyId,String userId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> listPunchRecord;
		String code;
		String msg;
		try {
			listPunchRecord = punchRecordService.getListCountByCompany(companyId,userId,date);
			code="0000";
			msg = "成功";
			resultMap.put("result", listPunchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/getStatusListByCompany", name = "查询某天公司下施工员打卡信息（根据打卡状态）", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getStatusListByCompany(String companyId,int status,String userId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> listPunchRecord;
		String code;
		String msg;
		try {
			listPunchRecord = punchRecordService.getStatusListByCompany(companyId,status,userId,date);
			code="0000";
			msg = "成功";
			resultMap.put("result", listPunchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/punchRecord/getPunchRecordPeriodByConstructorId", name = "查询单个施工员某一时间段打卡信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getPunchRecordPeriodByConstructorId(String constructorId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<PunchRecord> listPunchRecord;
		String code;
		String msg;
		try {
			listPunchRecord = punchRecordService.getPunchRecordPeriodByConstructorId(constructorId, startDate, endDate);
			code="0000";
			msg = "成功";
			resultMap.put("result", listPunchRecord);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
}

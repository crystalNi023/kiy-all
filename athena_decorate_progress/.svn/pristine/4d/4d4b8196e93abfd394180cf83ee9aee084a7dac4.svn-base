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

import com.alibaba.fastjson.JSON;
import com.dec.pro.entity.company.Position;
import com.dec.pro.service.company.PositionService;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller
public class PositionInterface {
	private static Logger logger=Logger.getLogger(PositionInterface.class);
	@Autowired
	private PositionService positionService;
	
	@RequestMapping(value = "app/position/increaseBatch", name = "批量录入定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> increaseBatchPosition(String listPosition){
		List<Position> listP = JSON.parseArray(listPosition, Position.class);//json数组转list对象
		if(listP!=null){
			for(int i=0;i<listP.size();i++){
				listP.get(i).setId(UUid.getUUid());//设置id
				listP.get(i).setType(1);//设置类型  (1.轨迹 2.上班打卡位置3.下班打卡位置)
				logger.error("上传批量位置："+listP.get(i).getId()+"  "+listP.get(i).getPositionTime());
			}
		}
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		int count=0;
		try {
			count = positionService.addBatch(listP);
			code="0000";
			msg = "成功";
			resultMap.put("result", count);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	@RequestMapping(value = "app/position/remove", name = "移除定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> removePosition(String id){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			positionService.deletePosition(id);
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
	@RequestMapping(value = "app/position/modify", name = "修改定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> removePosition(Position position){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		String code;
		String msg;
		try {
			positionService.updatePosition(position);
			code="0000";
			msg = "成功";
			resultMap.put("result", position);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	
	@RequestMapping(value = "app/position/queryPageList", name = "分页查询定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryPageListPosition(Position position){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		Page<Position> pageList;
		String code;
		String msg;
		try {
			pageList = positionService.getPageListPosition(position);
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
	@RequestMapping(value = "app/position/queryOne", name = "查询单个定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> queryOnePosition(String id){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		Position position;
		String code;
		String msg;
		try {
			position = positionService.getOnePosition(id);
			code="0000";
			msg = "成功";
			resultMap.put("result", position);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
	//已用
	@RequestMapping(value = "app/position/queryConstructorIdOneDay", name = "查询施工员某日定位信息", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object>  queryConstructorIdOneDayPosition(String constructorId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date date){
		Map<String,Object> resultMap =new HashMap<String,Object>();
		List<Position> listPosition;
		String code;
		String msg;
		try {
			listPosition = positionService.getConstructorIdOneDayPosition(constructorId,date);
			logger.error("查询日期："+date);
			if(listPosition!=null && listPosition.size()>0){
				for (Position positionEntity : listPosition) {
					logger.error("返回id："+positionEntity.getId());
					logger.error("返回记录日期："+positionEntity.getPositionTime());
					logger.error("返回更新日期："+positionEntity.getUpdated());
				}
			}
			code="0000";
			msg = "成功";
			resultMap.put("result", listPosition);
		} catch (Exception e) {
			code="0001";
			msg=e.getMessage();
		}
		resultMap.put("code",code);
		resultMap.put("msg",msg);
		return resultMap; 
	}
}

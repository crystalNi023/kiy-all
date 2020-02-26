package com.dec.pro.service.areaView;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.areaView.AreaView;
import com.dec.pro.mapper.areaView.AreaViewMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.user.UserService;
@Service
public class AreaViewService extends BaseService<AreaView>{
	private static Logger logger=Logger.getLogger(AreaViewService.class);
	@Autowired
	AreaViewMapper areaViewMapper;
	@Autowired
	UserService userService;
	
	public List<AreaView> getListByProId(String proId) throws Exception {
		List<AreaView> listAreaView;
		try {
			listAreaView = areaViewMapper.getListByProId(proId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return listAreaView;
	}
	
	public List<AreaView> getListByUsername(String userName) throws Exception {
		List<AreaView> listAreaView;
		try {
			listAreaView = areaViewMapper.getListByUserName(userName);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		
		return listAreaView;
	}

}

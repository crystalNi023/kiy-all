package com.dec.pro.service.project;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.ImageFlow;
import com.dec.pro.mapper.project.ImageFlowMapper;
import com.dec.pro.service.BaseService;
@Service
public class ImageFlowService extends BaseService<ImageFlow>{
	private static Logger logger=Logger.getLogger(ImageResourceService.class);
	@Autowired
	ImageFlowMapper imageFlowMapper; 
	
	/**
	 * 根据cusId获取图片流量限制
	 * @param id
	 * @return
	 */
	public ImageFlow getOneByCusId(String cusId) throws Exception {
		ImageFlow imageFlow ;
		try {
			imageFlow = imageFlowMapper.getOneByCusId(cusId);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return imageFlow;
	}
}


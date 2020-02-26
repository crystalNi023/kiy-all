package com.dec.pro.service.project;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.mapper.project.ImageResourceMapper;
import com.dec.pro.service.BaseService;
@Service
public class ImageResourceService extends BaseService<ImageResource>{
	private static Logger logger=Logger.getLogger(ImageResourceService.class);
	@Autowired
	ImageResourceMapper imageResourceMapper; 
	
	/**
	 * 根据cusId获取图片集合
	 * @param id
	 * @return
	 */
	public List<ImageResource> getImgByCustomerId(String id) throws Exception {
		List<ImageResource> listImageResource;
		try {
			listImageResource = imageResourceMapper.getImgByCustomerId(id);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return listImageResource;
	}
	
	/**
	 * 根据cusId和图片类型获取图片集合
	 * @param id
	 * @param resourceType
	 * @return
	 */
	public List<ImageResource> getResourceTypeImg(String id,int resourceType) throws Exception {
		List<ImageResource> listImageResource;
		try {
			listImageResource = imageResourceMapper.getResourceTypeImg(id,resourceType);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return listImageResource;
	}
	
}


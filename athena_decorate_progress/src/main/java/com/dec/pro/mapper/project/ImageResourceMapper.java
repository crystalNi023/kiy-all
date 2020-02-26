package com.dec.pro.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.mapper.BaseMapper;

public interface ImageResourceMapper extends BaseMapper<ImageResource>{

	public List<ImageResource> getImgByCustomerId(@Param("id") String id);
	public List<ImageResource> getResourceTypeImg(@Param("id") String id,@Param("resourceType")int resourceType);
}

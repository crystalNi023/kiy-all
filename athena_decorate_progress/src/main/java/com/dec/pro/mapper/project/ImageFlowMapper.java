package com.dec.pro.mapper.project;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.ImageFlow;
import com.dec.pro.mapper.BaseMapper;

public interface ImageFlowMapper extends BaseMapper<ImageFlow>{
	public ImageFlow getOneByCusId(@Param("cusId") String cusId);
}

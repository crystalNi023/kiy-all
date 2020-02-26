package com.dec.pro.mapper.areaView;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.areaView.AreaView;
import com.dec.pro.mapper.BaseMapper;

public interface AreaViewMapper extends BaseMapper<AreaView>{
	
	public List<AreaView> getListByProId(@Param("proId")String proId);
	public List<AreaView> getListByUserName(@Param("userName")String userName);

}

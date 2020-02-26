package com.dec.pro.mapper.project;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.ProgressRecord;
import com.dec.pro.mapper.BaseMapper;

public interface ProgressRecordMapper extends BaseMapper<ProgressRecord>{
	
	public ProgressRecord getOneByProgId(@Param("progId")String progId);

}

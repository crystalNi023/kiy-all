package com.dec.pro.mapper.project;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.mapper.BaseMapper;

public interface ProcedureMapper extends BaseMapper<Procedure>{
	/**
	 * 根据项目id获取流程新消息条数
	 * @param proId
	 * @return
	 */
	public int getNews(@Param("proId") String proId);
}

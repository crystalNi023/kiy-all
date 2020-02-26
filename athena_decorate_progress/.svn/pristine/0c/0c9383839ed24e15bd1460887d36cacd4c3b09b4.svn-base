package com.dec.pro.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.ChangeRecord;
import com.dec.pro.mapper.BaseMapper;

public interface ChangeRecordMapper extends BaseMapper<ChangeRecord>{

	/**
	 * 根据流程获取需求变更
	 * @param procId
	 * @return
	 */
	public List<ChangeRecord> getListByProcId(@Param("procId")String procId);
	/**
	 * 根据项目id获取需求变更新消息条数
	 * @param proId
	 * @return
	 */
	public int getNews(@Param("proId") String proId);
	
}

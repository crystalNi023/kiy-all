package com.dec.pro.mapper.project;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.project.Project;
import com.dec.pro.entity.push.Push;
import com.dec.pro.mapper.BaseMapper;

public interface ProjectMapper extends BaseMapper<Project>{
	
	public Project getProjectListByCusId(@Param("cusId") String cusId);
	
	public int addProjectTecoration(@Param("proId")String proId,@Param("tecId")String tecId);
	
	public Project getTimeAssessProcessByProId(@Param("proId")String proId);
	
    public Push getPushByProId(@Param("proId")String proId);
    
    public int getCountForCamera(Map<String, Object> map);
    
    public List<Project> getListForCamera(Map<String, Object> map);
    
	public int getCountByUserId(Map<String, Object> map);
 
	public List<Project> getListByUserId(Map<String, Object> map);
}

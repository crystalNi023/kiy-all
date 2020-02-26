package com.dec.pro.mapper.company;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.company.DecorationTeam;
import com.dec.pro.mapper.BaseMapper;

public interface DecorationTeamMapper extends BaseMapper<DecorationTeam>{

	public List<DecorationTeam> getListByComId(@Param("comId")String comId);
	
	public DecorationTeam getOneByPhone(@Param("phone")String phone);
	
	public DecorationTeam getOneByUserId(@Param("userId")String userId);
	
	public List<DecorationTeam> getListByProId(@Param("proId")String proId);

	public Integer getRelation(@Param("projectId")String projectId,@Param("decorationTeamId")String decorationTeamId);
	
	public int relationProject(@Param("projectId")String projectId,@Param("decorationTeamId")String decorationTeamId);
	
	public int deleteRelation(@Param("projectId")String projectId,@Param("decorationTeamId")String decorationTeamId);
}

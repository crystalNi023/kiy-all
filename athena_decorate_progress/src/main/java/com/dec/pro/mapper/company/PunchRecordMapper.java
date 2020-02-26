package com.dec.pro.mapper.company;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.company.PunchRecord;
import com.dec.pro.mapper.BaseMapper;

public interface PunchRecordMapper extends BaseMapper<PunchRecord>{
	public PunchRecord getOnePunchRecord(@Param("constructorId")String constructorId,@Param("date")Date date );
	public List<PunchRecord> getListStaffPunchRecord(@Param("groId")String groId,@Param("date")Date date );
	public List<PunchRecord> getStaffWorkingStatus(@Param("groId")String groId,@Param("date")Date date );
	public List<PunchRecord> getPunchRecordPeriodByConstructorId(@Param("constructorId")String constructorId,@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	//已用
	public List<PunchRecord> getListByCompany(@Param("companyId")String companyId,@Param("date")Date date);
	public List<PunchRecord> getStatusListByCompany(@Param("companyId")String companyId,@Param("status")int status,@Param("date")Date date);
	public List<PunchRecord> getListByCompanyManager(@Param("companyId")String companyId,@Param("userId")String userId,@Param("date")Date date);
	public List<PunchRecord> getStatusListByCompanyManager(@Param("companyId")String companyId,@Param("userId")String userId,@Param("status")int status,@Param("date")Date date);
}

package com.dec.pro.mapper.company;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.company.DeviceAllocation;
import com.dec.pro.mapper.BaseMapper;

public interface DeviceAllocationMapper extends BaseMapper<DeviceAllocation>{
	public List<DeviceAllocation> getListByComId(@Param("comId") String comId);
	public List<DeviceAllocation> getListByComIdAndProId(@Param("comId") String comId,@Param("proId") String proId);
	public List<DeviceAllocation> getListBySerialNumber(@Param("serialNumber") String serialNumber);
	public int deleteBySerialNumber(@Param("serialNumber") String serialNumber);
	public int updateProIdBySerialNumber(@Param("serialNumber") String serialNumber);
	public int updateDeviceAllocationNameById(@Param("id") String id, @Param("name") String name);
	public DeviceAllocation getOneBySerialNumber(@Param("serialNumber") String serialNumber);
}

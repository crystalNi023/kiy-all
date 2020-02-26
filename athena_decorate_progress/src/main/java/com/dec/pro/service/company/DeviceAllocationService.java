package com.dec.pro.service.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.company.DeviceAllocation;
import com.dec.pro.mapper.company.DeviceAllocationMapper;
import com.dec.pro.service.BaseService;
@Service
public class DeviceAllocationService extends BaseService<DeviceAllocation>{
	@Autowired
	private DeviceAllocationMapper deviceAllocationMapper;
	
	/**
	 * 分配设备到公司
	 * @param da
	 * @return
	 * @throws Exception
	 */
	public int addDeviceAllocation(DeviceAllocation da) throws Exception{
		int count=0;
		List<DeviceAllocation> list = null;
		String serialNumber = da.getSerialNumber();
		//检查设备是否被分配
		if(serialNumber==null || "".equals(serialNumber)){
			throw new RuntimeException("设备序列号不能为空！");
		}else{
			list = getListBySerialNumber(serialNumber);
		}
		//绑定设备
		if(list==null || list.isEmpty()){
			count=add(da);
		}else{
			throw new RuntimeException("该设备已被分配！");
		}
		return count;
	}
	/**
	 * 公司分配设备到项目
	 * @param da
	 * @return
	 * @throws Exception
	 */
	public int updateDeviceAllocation(DeviceAllocation da) throws Exception{
		int count=0;
		DeviceAllocation queryDa = null;
		String serialNumber = da.getSerialNumber();
		//检查设备是否被分配
		if(serialNumber==null || "".equals(serialNumber)){
			throw new RuntimeException("设备序列号不能为空！");
		}else{
			queryDa = deviceAllocationMapper.getOneBySerialNumber(serialNumber);
		}
		if(queryDa==null || "".equals(queryDa)){
			throw new RuntimeException("该设备未分配给贵公司！");
		}
		String proId = queryDa.getProId();
		//绑定设备
		if(proId==null || "".equals(proId)){
			count=update(da);
		}else{
			throw new RuntimeException("该设备已被分配！");
		}
		return count;
	}
	/**
	 * 与公司设备解绑
	 * @param da
	 * @return
	 * @throws Exception
	 */
	public int removeDeviceAllocation(String serialNumber) throws Exception{
		return deviceAllocationMapper.deleteBySerialNumber(serialNumber);
	}
	/**
	 * 公司回收项目设备
	 * @param da
	 * @return
	 * @throws Exception
	 */
	public int removeDeviceAllocationProId(String serialNumber) throws Exception{
		return deviceAllocationMapper.updateProIdBySerialNumber(serialNumber);
	}
	
	/**
	 * 根据公司id查询设备
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<DeviceAllocation> getDeviceAllocationList(String comId) throws Exception {
		return deviceAllocationMapper.getListByComId(comId);
	}
	/**
	 * 根据公司id和项目id查询设备
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<DeviceAllocation> getDeviceAllocationList(String comId,String proId) throws Exception {
		return deviceAllocationMapper.getListByComIdAndProId(comId,proId);
	}
	/**
	 * 根据设备序列号查询设备绑定信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<DeviceAllocation> getListBySerialNumber(String serialNumber) throws Exception {
		List<DeviceAllocation>  list = deviceAllocationMapper.getListBySerialNumber(serialNumber);
		return list;
	}
	/**
	 * 根据编号修改设备名称
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public int updateDeviceAllocationNameById(String id , String name) throws Exception {
		return deviceAllocationMapper.updateDeviceAllocationNameById(id,name);
	}
}

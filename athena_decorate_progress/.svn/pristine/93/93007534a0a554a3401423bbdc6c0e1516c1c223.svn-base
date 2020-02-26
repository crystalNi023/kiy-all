package com.dec.pro.interfaces.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.company.DeviceAllocation;
import com.dec.pro.service.company.DeviceAllocationService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.UUid;

@Controller(value="设备分配")
public class DeviceAllocationInterface {
	@Autowired
	private DeviceAllocationService deviceAllocationService;
	private String code="0000";
	private String msg="成功";
	
	@RequestMapping(value="app/deviceAllocation/increase",name="设备与公司绑定",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> increaseDeviceAllocation(DeviceAllocation deviceAllocation){
		Map<String, Object> map=new HashMap<>();
		deviceAllocation.setId(UUid.getUUid());
		try {
			GetField.getOTM(" APP分配设备到公司:",deviceAllocation);
			if(deviceAllocationService.addDeviceAllocation(deviceAllocation)==1){
				code = "0000";
				msg = "分配成功";
			}else{
				code = "0001";
				msg = "分配失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/modifyById",name="设备与公司项目绑定",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> modifyDeviceAllocationById(DeviceAllocation deviceAllocation){
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP分配设备到公司项目:",deviceAllocation);
			if(deviceAllocationService.updateDeviceAllocation(deviceAllocation)==1){
				code = "0000";
				msg = "分配成功";
			}else{
				code = "0001";
				msg = "分配失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/removeProId",name="设备与公司项目解绑",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> removeProIdDeviceAllocation(String serialNumber){
		Map<String, Object> map=new HashMap<>();
		try {
			if(deviceAllocationService.removeDeviceAllocationProId(serialNumber)==1){
				code = "0000";
				msg = "解绑成功";
			}else{
				code = "0001";
				msg = "解绑失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/remove",name="设备与公司解绑",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> removeDeviceAllocation(String serialNumber){
		Map<String, Object> map=new HashMap<>();
		try {
			if(deviceAllocationService.removeDeviceAllocation(serialNumber)==1){
				code = "0000";
				msg = "解绑成功";
			}else{
				code = "0001";
				msg = "解绑失败";
			}
			
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/queryComId",name="查询公司设备",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> queryDeviceAllocationByComId(String comId){
		Map<String, Object> map=new HashMap<>();
		List<DeviceAllocation> list=null;
		try {
			list = deviceAllocationService.getDeviceAllocationList(comId);
			code = "0000";
			msg = "查询成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result", list);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/queryComIdAndProId",name="查询公司项目设备",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> queryDeviceAllocationByComIdAndProId(String comId ,String proId){
		Map<String, Object> map=new HashMap<>();
		List<DeviceAllocation> list=null;
		try {
			list = deviceAllocationService.getDeviceAllocationList(comId, proId);
			code = "0000";
			msg = "查询成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result", list);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/deviceAllocation/modifyNameById",name="更新设备名称",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> modifyDeviceAllocationNameById(String id ,String name){
		Map<String, Object> map=new HashMap<>();
		try {
			if(deviceAllocationService.updateDeviceAllocationNameById(id, name)==1){
				code = "0000";
				msg = "修改成功";
			}else{
				code = "0001";
				msg = "修改失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	
}

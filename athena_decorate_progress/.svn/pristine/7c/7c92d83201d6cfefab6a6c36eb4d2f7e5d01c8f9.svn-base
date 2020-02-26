package com.dec.pro.interfaces.project;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.service.project.ProcedureService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

@Controller(value="项目流程")
public class ProcedureInterface {
	@Autowired
	private ProcedureService procedureService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/procedure/queryList",name="分页查询流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryProcedureList(Procedure procedure){
		Map<String, Object> map=new HashMap<>();
		Page<Procedure> pages = null;
		try {
			pages =procedureService.getProcedureList(procedure);
			code="0000";
			msg="成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result",pages);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
	@RequestMapping(value="app/procedure/modifyById",name="修改流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> modifyProcedureById(Procedure procedure){
		Map<String, Object> map=new HashMap<>();
		try {
			procedure.setNotice(1);// 设置为新通知
			GetField.getOTM(" APP修改流程:",procedure);
			if(procedureService.update(procedure)==1){
				code="0000";
				msg="修改成功";
			}else {
				code="0001";
				msg="修改失败";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
	@RequestMapping(value="app/procedure/increase",name="添加流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> increaseProcedure(Procedure procedure) {
		Map<String, Object> map=new HashMap<>();
		procedure.setId(UUid.getUUid());
		procedure.setStatus(1);//状态1.未启动 2.装修中 3.已完成
		int count = 0;
		try {
			GetField.getOTM(" APP添加流程:",procedure);
			count = procedureService.addProcedure(procedure);
			if (count==1) {
				code="0000";
				msg="成功";
			}else {
				code="0001";
				msg="添加失败";
			}
		} catch (Exception e) {
			code = "0001";
			msg = e.getMessage();
		}
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/procedure/reLaunch",name="重新发起流程",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> reLaunch(Procedure procedure){
		Map<String, Object> map=new HashMap<>();
		try {
			GetField.getOTM(" APP重新发起流程:",procedure);
			procedureService.updateProcedure(procedure);
			code="00";
			msg="成功";
		} catch (Exception e) {
			code = "01";
			msg = e.getMessage();
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	} 
}

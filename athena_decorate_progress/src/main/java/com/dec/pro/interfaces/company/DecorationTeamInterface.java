package com.dec.pro.interfaces.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dec.pro.entity.company.DecorationTeam;
import com.dec.pro.service.company.DecorationTeamService;
import com.dec.pro.util.GetField;

@Controller(value="项目团队")
public class DecorationTeamInterface {
	@Autowired
	private DecorationTeamService decorationTeamService;
	private String code="0000";
	private String msg="成功";
	@RequestMapping(value="app/decorationTeam/queryList",name="根据公司查询装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryDecorationTeamList(DecorationTeam decorationTeam){
		Map<String, Object> map=new HashMap<>();
		List<DecorationTeam> decorationTeams = null;
		try {
			GetField.getOTM(" APP根据公司查询装修团队:",decorationTeam);
			decorationTeams = decorationTeamService.getListByComId(decorationTeam.getComId());
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result",decorationTeams);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	}
	@RequestMapping(value="app/decorationTeam/queryListByProId",name="根据项目id查询装修团队",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> queryDecorationTeamListByProId(String proId){
		Map<String, Object> map=new HashMap<>();
		List<DecorationTeam> decorationTeams = null;
		try {
			GetField.getOTM(" APP根据项目id查询装修团队:",proId);
			decorationTeams =decorationTeamService.getListByProId(proId);
			code = "0000";
			msg = "获取成功";
		} catch (Exception e) {
			msg=e.getMessage();
			code="0001";
		}
		map.put("result",decorationTeams);
		map.put("code",code);
		map.put("msg",msg);
		return map;
	} 
}

package com.dec.pro.service.project;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.entity.push.Push;
import com.dec.pro.mapper.project.ProcedureMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.push.PushService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
@Service
public class ProcedureService extends BaseService<Procedure>{
	private static Logger logger=Logger.getLogger(ProcedureService.class);
	@Autowired
	private ProcedureMapper procedureMapper;
	@Autowired
	private PushService pushService;
	@Autowired
	private ProjectService projectService;
	/**
	 * 查询项目下所有流程
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Procedure> getProcedureList(Procedure p) throws Exception {
		pages.setPageNo(p.getPageNo());
		pages.setPageSize(p.getPageSize());
		Page<Procedure> resultPage;
		resultPage = getPage(pages, GetField.getOTM(" 查询项目下所有流程：",p));// 按条件查询项目
		return resultPage;
	}
	public int addProcedure(Procedure procedure) throws Exception{
		int count = this.add(procedure);//添加流程
		int pushType=4;
		String proId = procedure.getProId();
		Push push = projectService.getPushByProId(proId);//推送消息到客户
		try {
			pushService.sendOnePushMessage(push, pushType);
		} catch (Exception e) {
			logger.error("消息推送异常信息："+e.getMessage());
			throw new RuntimeException("消息推送失败！");
		}
		return count;
	}
	public int updateProcedure(Procedure procedure) throws Exception{
		int count=0;
		count = this.update(procedure);//修改流程
		int pushType=4;
		String proId = procedure.getProId();
		Push push = projectService.getPushByProId(proId);//推送消息到客户
		try {
			pushService.sendOnePushMessage(push, pushType);
		} catch (Exception e) {
			logger.error("消息推送异常信息："+e.getMessage());
			throw new RuntimeException("消息推送失败！");
		}
		return count;
	}
	/**
	 * 获取流程新消息条数
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public int getNews(String proId) throws Exception {
		int newsNumber;
		try {
			newsNumber = procedureMapper.getNews(proId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return newsNumber;
	}
}

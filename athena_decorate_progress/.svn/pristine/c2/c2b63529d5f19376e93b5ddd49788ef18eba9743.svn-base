package com.dec.pro.service.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.Procedure;
import com.dec.pro.entity.project.Project;
import com.dec.pro.entity.push.Push;
import com.dec.pro.entity.user.User;
import com.dec.pro.mapper.project.ProjectMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.company.DecorationTeamService;
import com.dec.pro.service.push.PushService;
import com.dec.pro.service.user.UserService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;
@Service
public class ProjectService extends BaseService<Project>{
	private static Logger logger=Logger.getLogger(ProjectService.class);
	@Autowired
	private UserService userService;
	@Autowired
	private ProcedureService procedureService;
	@Autowired
	private ChangeRecordService changeRecordService;
	@Autowired
	ProjectMapper projectMapper;
	@Autowired
	DecorationTeamService decorationTeamService;
	@Autowired
	private PushService pushService;
	/**
	 * 按条件分页查询(加入权限（不同职位访问的数据不同）)
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Project> getProjectList(Project p) throws Exception {
		User one = userService.getOne(p.getUserId());
		Page<Project> resultPage = null;
		pages.setPageNo(p.getPageNo());
		pages.setPageSize(p.getPageSize());
		if ((one!=null) && (one.getType()==6 || one.getType()==7)) { //项目经理和施工员：只能查看自己管理的项目;
			p.setUserId(p.getUserId());
			resultPage = getProjectListByUserId(p, p.getUserId());
		}else{
			resultPage = getPage(pages, GetField.getOTM(" 分页查询项目:",p));//按条件查询项目
		}
		return resultPage;
	}
	
	/**
	 * 按条件分页查询项目
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Project> getProjectListByUserId(Project p,String userId) throws Exception {
		Map<String, Object> reqMap=new HashMap<String, Object>();
		pages.setPageNo(p.getPageNo());
		pages.setPageSize(p.getPageSize());
		reqMap.put("startRow", pages.getStartRow());
		reqMap.put("pageSize", pages.getPageSize());
		reqMap.put("decoration", p.getDecoration());
		reqMap.put("comId", p.getComId());
		reqMap.put("userId", userId);
		reqMap.put("customerName", p.getCustomerName());
		reqMap.put("decoration", p.getDecoration());
		reqMap.put("address", p.getAddress());
		p.setUserId(userId);
		Number totalItems = (Number) projectMapper.getCountByUserId(reqMap);
	        if (totalItems != null) {
	            pages.setTotalItems(totalItems.intValue());
	            List<Project> list;
	            try {
	            	list = projectMapper.getListByUserId(reqMap);
	    		} catch (Exception e) {
	    			logger.error("数据库异常信息：" + e.getMessage());
	    			throw new RuntimeException("数据异常，请联系管理员！");
	    		}
	            pages.setResult(list);
	        }
		return pages;
	}
	
	/**
	 * 按条件分页查询项目及摄像头
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Project> getProjectListForCamera(Project p) throws Exception {
		Map<String, Object> reqMap=new HashMap<String, Object>();
		pages.setPageNo(p.getPageNo());
		pages.setPageSize(p.getPageSize());
		reqMap.put("startRow", pages.getStartRow());
		reqMap.put("pageSize", pages.getPageSize());
		reqMap.put("decoration", p.getDecoration());
		reqMap.put("comId", p.getComId());
		reqMap.put("userId", p.getUserId());
		Number totalItems = (Number) projectMapper.getCountForCamera(reqMap);
	        if (totalItems != null) {
	            pages.setTotalItems(totalItems.intValue());
	            List<Project> list;
	            try {
	            	list = projectMapper.getListForCamera(reqMap);
	            	System.err.println("获取摄像头"+p.getUserId());
	    		} catch (Exception e) {
	    			logger.error("数据库异常信息：" + e.getMessage());
	    			throw new RuntimeException("数据异常，请联系管理员！");
	    		}
	            pages.setResult(list);
	        }
		return pages;
	}
	/**
	 * 按条件分页查询(app)
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Project getProjectListByCusId(Project p) throws Exception{
		Project project;
		try {
			project = projectMapper.getProjectListByCusId(p.getCusId());
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return project;
	}
	/**
	 * 为客户添加项目及流程     伴随推送消息
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	public int addProject(Project p) throws Exception{
		int count=0;
		int pushType=4;//流程发起
		// 判断客户是否已经发起过项目
		Project projectUse;
		try {
			projectUse = projectMapper.getProjectListByCusId(p.getCusId());
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		if (projectUse != null){
			throw new RuntimeException("该客户已有项目");
		}
		count = super.add(p);
		if (count == 1) {
				// 绑定项目与相关负责人 *必须有联系电话
				if (p.getDecorationId()!=null) {
				//DecorationTeam one = decorationTeamService.getOneByPhone(p.getDecorationPhone());
				projectMapper.addProjectTecoration(p.getId(), p.getDecorationId());
			}
			if (p.getDesignId()!=null) {
				//DecorationTeam one = decorationTeamService.getOneByPhone(p.getDesignPhone());
				projectMapper.addProjectTecoration(p.getId(), p.getDesignId());
			}
			if (p.getSpotId()!=null) {
				//DecorationTeam one = decorationTeamService.getOneByPhone(p.getSpotPhone());
				projectMapper.addProjectTecoration(p.getId(), p.getSpotId());
			}
			List<Procedure>  listProcedure = p.getListProcedure();
			if(listProcedure != null && listProcedure.size()>0){
//				for (Procedure procedure : listProcedure) {
//					procedure.setId(UUid.getUUid());//设置主键
//					procedure.setProId(p.getId());
//					//procedureService.add(procedure);
//				}
				for (int i = 0; i <listProcedure.size(); i++) {
					listProcedure.get(i).setId(UUid.getUUid());
					listProcedure.get(i).setProId(p.getId());
				}
				procedureService.addBatch(listProcedure);//批量插入流程
			}else{
				//项目暂未添加流程
			}
		}else{
			//项目添加失败！
		}
		Push push=this.getPushByProId(p.getId());//消息推送
		if (push == null) {
			return count;
		}
		try {
			pushService.sendOnePushMessage(push, pushType);
		} catch (Exception e) {
			logger.error("消息推送异常信息："+e.getMessage());
			throw new RuntimeException("消息推送失败！");
		}
		return count;
	}
	public Push getPushByProId(String proId) throws Exception{
		Push push;
		try {
			push = projectMapper.getPushByProId(proId);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return push;
	}
	/**
	 * 获取项目新消息
	 * @param projectList
	 * @return
	 * @throws Exception
	 */
	public List<Project> getNews(List<String> idList) throws Exception{
		List<Project> projectList = new ArrayList<>();
		System.out.println(idList);
			for (String proId : idList) {
				Project project = new Project();
				project.setId(proId);
				int procedureNews = procedureService.getNews(proId);
				int changeRecordNews = changeRecordService.getNews(proId);
				if (changeRecordNews>0 && procedureNews>0) { //0：暂无新消息 1:进度流程消息 2：需求变更消息 3：进度流程和需求变更消息 
					project.setNewsType(3);
				}else if (changeRecordNews>0 && procedureNews<=0) {
					project.setNewsType(2);
				}else if (changeRecordNews<=0 && procedureNews>0) {
					project.setNewsType(1);
				}else {
					project.setNewsType(0);
				}
				project.setNewsNumber(changeRecordNews+procedureNews);
				projectList.add(project);
			}
			System.out.println(projectList);
		return projectList;
	}
}

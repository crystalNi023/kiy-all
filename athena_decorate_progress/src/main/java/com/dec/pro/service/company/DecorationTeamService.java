package com.dec.pro.service.company;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dec.pro.entity.company.DecorationTeam;
import com.dec.pro.entity.user.User;
import com.dec.pro.mapper.company.DecorationTeamMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.user.UserService;
import com.dec.pro.util.AES;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;
@Service
public class DecorationTeamService extends BaseService<DecorationTeam>{
	private static Logger logger=Logger.getLogger(DecorationTeamService.class);
	@Autowired
	DecorationTeamMapper decorationTeamMapper;
	@Autowired
	UserService userService;
	
	public int addDecorationTeam(DecorationTeam decorationTeam) throws Exception{
		return decorationTeamMapper.add(decorationTeam);
	}
	
	/**
	 * 按条件分页查询
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public Page<DecorationTeam> getDecorationTeamList(DecorationTeam d) throws Exception {
		pages.setPageNo(d.getPageNo());
		pages.setPageSize(d.getPageSize());
		Page<DecorationTeam> resultPage;
		resultPage = getPage(pages, GetField.getOTM(" 分页查询装修团队:",d));//按条件查询客户
		return resultPage;
	}
	
	/**
	 * 根据公司id查询
	 * @param comId
	 * @return
	 */
	public List<DecorationTeam> getListByComId(String comId) throws Exception{
		List<DecorationTeam> listDecorationTeam;
		try {
			listDecorationTeam = decorationTeamMapper.getListByComId(comId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return listDecorationTeam;
	}
	
	/**
	 * 根据电话号码获取装修团队
	 * @param phone
	 * @return
	 */
	public DecorationTeam getOneByPhone(String phone) throws Exception{
		DecorationTeam decorationTeam;
		try {
			decorationTeam = decorationTeamMapper.getOneByPhone(phone);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return decorationTeam;
	}
	
	/**
	 * 根据用户编号获取装修团队
	 * @param userId
	 * @return
	 */
	public DecorationTeam getOneByUserId(String userId) throws Exception{
		DecorationTeam decorationTeam;
		try {
			decorationTeam = decorationTeamMapper.getOneByUserId(userId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return decorationTeam;
	}
	
	/**
	 * 根据项目编号获取装修团队
	 * @param proId
	 * @return
	 */
	public List<DecorationTeam> getListByProId(String proId) throws Exception{
		List<DecorationTeam> listDecorationTeam;
		try {
			listDecorationTeam = decorationTeamMapper.getListByProId(proId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return listDecorationTeam;
	}
	
	/**
	 * 分配账号(给装修团队分配账号)
	 * @param u
	 * @param d
	 * @throws Exception
	 */
	@Transactional
	public void distributionUser(String username,String password,DecorationTeam d) throws Exception{
		User user=userService.getUserByUsername(username);//查询该用户是否已注册
		if(user==null){
			User u = new User();
			u.setId(UUid.getUUid());
			u.setComId(d.getComId());
			u.setUsername(username);
			u.setPassword(AES.encrypt(password));//密码MD5加密
			//类型（1装修公司,2客户,3系统管理员,4项目部经理,5装修设计师,6项目经理,7现场负责人）工程部经理＞项目经理
			u.setType(d.getModule()+3);
			d.setUserId(u.getId());
			try {
				userService.add(u);//插入数据库
				update(d);
			} catch (Exception e) {
				throw new RuntimeException("数据异常，请联系管理员！");
			}
		}else{
			throw new RuntimeException("该用户名已经被注册！");
		}
	}
	
	/**
	 * 关联项目和装修员工
	 * @param projectId
	 * @param decorationTeamId
	 * @return
	 * @throws Exception
	 */
	public int relationProject(String projectId,String decorationTeamId) throws Exception{
		int count = 0;
		try {
			if(decorationTeamMapper.getRelation(projectId, decorationTeamId)!=null) throw new RuntimeException("该员工与项目已关联，请不要重复提交！");
			count = decorationTeamMapper.relationProject(projectId,decorationTeamId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return count;
	}
	
	/**
	 * 解除项目和装修员工的关联关系
	 * @param projectId
	 * @param decorationTeamId
	 * @return
	 * @throws Exception
	 */
	public int deleteRelation(String projectId,String decorationTeamId) throws Exception{
		int count = 0;
		try {
			count = decorationTeamMapper.deleteRelation(projectId,decorationTeamId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}
}

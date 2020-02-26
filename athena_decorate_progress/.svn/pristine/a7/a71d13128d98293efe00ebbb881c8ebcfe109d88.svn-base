package com.dec.pro.service.user;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.company.DecorationCompany;
import com.dec.pro.entity.role.Power;
import com.dec.pro.entity.role.Role;
import com.dec.pro.entity.user.User;
import com.dec.pro.mapper.user.UserMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.company.DecorationCompanyService;
import com.dec.pro.service.company.DecorationTeamService;
import com.dec.pro.service.customer.CustomerService;
import com.dec.pro.service.project.ImageResourceService;
import com.dec.pro.util.AES;
import com.dec.pro.util.GetField;
import com.dec.pro.util.ImageUtil;
import com.dec.pro.util.Page;
import com.dec.pro.util.SMSUtil;
import com.dec.pro.util.UUid;
@Service
public class UserService extends BaseService<User>{
	private static Logger logger=Logger.getLogger(UserService.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ImageResourceService imageResourceService;
	@Autowired
	private DecorationCompanyService decorationCompanyService;
	@Autowired
	private DecorationTeamService decorationTeamService;
	/**
	 * 按条件分页查询
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<User> getUserPages(User u) throws Exception{
		pages.setPageNo(u.getPageNo());
		pages.setPageSize(u.getPageSize());
		Page<User> page = getPage(pages,GetField.getOTM(" 分页查询用户：",u));
		return page;
	}
	/**
	 * 注册用户
	 * @param u
	 * @return
	 * @throws RuntimeException
	 * @throws IOException 
	 */
	@Transactional
	public void register(User u,DecorationCompany decorationCompany,MultipartFile[] businessLicense) throws Exception{
		String username=u.getUsername();
		User user=getUserByUsername(username);//查询该用户是否已注册
		if(user==null){//注册
			String code = u.getCode().trim();
			String mobile = u.getUsername().trim();
			if(SMSUtil.checkVerify(mobile, code)){//校验验证码
				u.setPassword(AES.encrypt(u.getPassword()));//密码MD5加密
				String uUid = UUid.getUUid();
				//公司信息注册
				decorationCompany.setId(uUid);
				int add;
				try {
					add = decorationCompanyService.add(decorationCompany);
				} catch (Exception e) {
					throw new RuntimeException("数据异常，请联系管理员！");
				}
				if (add==1) {
					u.setComId(uUid);
				}
				//类型 1：装修队（注册时类型为装修队）
				u.setType(1);
				//注册时设为不可用
				u.setEnable(false);
				int count;
				try {
					count = mapper.add(u);//插入数据库
				} catch (Exception e) {
					throw new RuntimeException("数据异常，请联系管理员！");
				}
				if(count != 1)
					throw new RuntimeException("注册失败,请联系管理员！");
				if(businessLicense!=null && businessLicense.length>0){// 保存营业执照
					int type=3;//资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
					ImageUtil.saveImageResource(imageResourceService,businessLicense,decorationCompany.getId(),type);
				}else {
					throw new RuntimeException("请上传营业执照！");
				}
			}else{
				throw new RuntimeException("验证码不正确！");
			}
		}else{
			throw new RuntimeException("该用户名已经被注册！");
		}
	}
	/**
	 * 重置密码
	 * @param u
	 * @throws Exception 
	 */
	public void resetPassword(User u) throws Exception{
		String username = u.getUsername();
		String code = u.getCode();
		String mobile=username;
		User user=getUserByUsername(username);//查询该用户是否已注册
		if(user==null){
			throw new RuntimeException("用户不存在，请先注册！");
		}else{
			if(SMSUtil.checkVerify(mobile, code)){//校验验证码
				u.setPassword(AES.encrypt(u.getPassword()));//密码加密
				mapper.update(u);
			}else {
				throw new RuntimeException("验证码错误");
			}
		}
	}	
	
	
	/**
	 * 注册用户(APP接口使用)
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	public void registerAPP(User u) throws Exception{
		String username=u.getUsername();
		User user=getUserByUsername(username);//查询该用户是否已注册
		if(user==null){//注册
			String code = u.getCode().trim();
			String mobile = u.getUsername().trim();
			if(SMSUtil.checkVerify(mobile, code)){//校验验证码
				//if(true){
				u.setPassword(AES.encrypt(u.getPassword()));//密码MD5加密
				int count = mapper.add(u);//插入数据库
				if(count != 1)
					throw new RuntimeException("注册失败,请联系管理员！");
			}else{
				throw new RuntimeException("验证码不正确！");
			}
		}else{
			throw new RuntimeException("该用户名已经被注册！");
		}
	}
	
	/**
	 * 重置密码（APP接口使用）
	 * @param u
	 * @throws RuntimeException
	 */
	public void resetPasswordAPP(User u) throws Exception{
		String username = u.getUsername();
		String code = u.getCode();
		String mobile=username;
		User user=getUserByUsername(username);//查询该用户是否已注册
		if(user==null){
			throw new RuntimeException("用户不存在，请先注册！");
		}else{
			if(SMSUtil.checkVerify(mobile, code)){//校验验证码
				user.setPassword(AES.encrypt(u.getPassword()));//密码MD5加密
				mapper.update(user);
			}else{
				throw new RuntimeException("验证码不正确！");
			}
		}
	}
	
	
	/**
	 * 登录(APP接口使用)
	 * @param u
	 * @param type
	 * @return
	 * @throws RuntimeException
	 */
	public User loginAPP(User u) throws Exception{
		String username = u.getUsername();
		String password = u.getPassword();
		
		if(username.isEmpty() && password.isEmpty()){
			throw new RuntimeException("用户名和密码为空！");
		}else if(username.isEmpty()){
			throw new RuntimeException("用户名为空！");
		}else if(password.isEmpty()){
			throw new RuntimeException("用户密码为空！");
		}
		User user = getUserByUsername(username);

		if(user == null){
			throw new RuntimeException("用户不存在，请先注册！");
		}else{
			if (u.getType()==1) {//判断是PC端公司登陆
				// type：1装修公司,2客户,3系统管理员,4项目部经理,5装修设计师,6项目经理,7现场负责人,8施工员
				if (user.getType()!=1 && user.getType()!=4 && user.getType()!=6) {
					throw new RuntimeException("请使用公司账号登陆！");
				}
			}
			if(username.equals(user.getUsername()) && AES.encrypt(password).equals(user.getPassword()) && user.isEnable()){			 	
				return user;
			}else if(!username.equals(user.getUsername())){
				throw new RuntimeException("用户名错误！");
			}else if(!user.isEnable()){
				throw new RuntimeException("该用户未启用！");
			}else {
				throw new RuntimeException("用户密码错误！");
			}
		}
	}
	/**
	 * 校验验证码
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	public boolean codeCheck(User u) throws Exception {
		String username = u.getUsername();
		User user = getUserByUsername(username);// 查询该用户是否已注册
		if (user == null) {// 注册
			String code = u.getCode().trim();
			String mobile = u.getUsername().trim();
			if (SMSUtil.checkVerify(mobile, code)) {// 校验验证码
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public User getUserByUsername(String username) throws Exception{
		User user;
		try {
			user = userMapper.getUserByUsername(username);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return user;
	}
	
	public Set<Role> getRoleByUsername(String username) throws Exception{
		Set<Role> roles;
		try {
			roles = userMapper.selectRoleByUsername(username);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return roles;
	}
	
	public Set<Power> getPowerByUsername(String username) throws Exception{
		Set<Power> powers;
		try {
			powers = userMapper.selectPowerByUsername(username);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return powers;
	}
}

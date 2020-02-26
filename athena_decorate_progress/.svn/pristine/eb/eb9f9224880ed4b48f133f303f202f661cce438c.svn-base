package com.dec.pro.service.customer;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.customer.Customer;
import com.dec.pro.entity.project.ImageFlow;
import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.entity.project.Project;
import com.dec.pro.entity.user.User;
import com.dec.pro.mapper.customer.CustomerMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.company.DecorationCompanyService;
import com.dec.pro.service.project.ImageFlowService;
import com.dec.pro.service.project.ImageResourceService;
import com.dec.pro.service.project.ProjectService;
import com.dec.pro.service.user.UserService;
import com.dec.pro.util.AES;
import com.dec.pro.util.GetField;
import com.dec.pro.util.ImageUtil;
import com.dec.pro.util.Page;
import com.dec.pro.util.SMSUtil;
import com.dec.pro.util.UUid;
@Service
public class CustomerService extends BaseService<Customer>{
	private static Logger logger=Logger.getLogger(CustomerService.class);
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageFlowService imageFlowService;
	@Autowired
	private ImageResourceService imageResourceService;
	@Autowired
	private DecorationCompanyService decorationCompanyService;

	/**
	 * pc端添加用户并为用户注册账号，并上传图片
	 * @param customer
	 * @param imgs
	 * @param resourceType 图片类型（1：合同 2：设计图）
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public int addCustomer(Customer customer, String comId, MultipartFile[] contractImgs, MultipartFile[] designImgs) throws Exception {
		int count = 0;
		String userId = UUid.getUUid();// 生成用户编号
		// 判断电话号码是否已经有过
		User user = userService.getUserByUsername(customer.getPhone());
		if (user != null)
			throw new RuntimeException("该号码已添加！");
		customer.setUserId(userId);// 绑定用户
		count = super.add(customer);
		if (count == 1) {// 添加成功
			User u = new User();
			String param = null;
			String phone = customer.getPhone();
			String code = SMSUtil.getRandomString();
			u.setId(userId);
			u.setComId(comId);
			u.setUsername(phone);
			u.setPassword(AES.encrypt(code));
			u.setType(2);
			userService.add(u);// 用户注册成功
			param = phone + "," + code;// 发送短信
			SMSUtil.sendSmsOne(SMSUtil.DECORATE_ACCOUNT_NUMBER, param, phone, UUid.getUUid());

			// 关联客户图片限制
			ImageFlow imageFlow = new ImageFlow();
			imageFlow.setId(UUid.getUUid());
			imageFlow.setCusId(customer.getId());
			imageFlow.setFlowLimit(30.0);
			imageFlowService.add(imageFlow);

			// 保存图片
			if (contractImgs != null && contractImgs.length > 0) {
				int type = 1;// 资源类型 1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
//				saveImageResource(contractImgs, customer.getId(), type);
				ImageUtil.saveImageResourceByLimit(imageResourceService, imageFlowService, contractImgs, null , customer.getId(),type);
			}
			if (designImgs != null && designImgs.length > 0) {
				int type = 2;
				ImageUtil.saveImageResourceByLimit(imageResourceService, imageFlowService, designImgs, null , customer.getId(),type);
			}

		}
		return count;
	}
	/**
	 * 按条件分页查询
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Customer> getCustomerList(Customer c) throws Exception {
		User one = userService.getOne(c.getUserId());//传递进来用于条件查询的userId
		pages.setPageNo(c.getPageNo());
		pages.setPageSize(c.getPageSize());
		Page<Customer> resultPage;
		if (one.getType()!=6) {//项目经理：只能查看自己管理的项目;
			c.setUserId(null);
		}
		resultPage = getPage(pages, GetField.getOTM("分页查询客户:",c));// 按条件查询客户
		return resultPage;
	}
	/**
	 * 根据客户id获取图片(分类型)
	 * @param id
	 * @return
	 */
	public List<ImageResource> getResourceTypeImg(String id, int resourceType) throws Exception {
		List<ImageResource> imageResources;
		imageResources = imageResourceService.getResourceTypeImg(id, resourceType);
		return imageResources;
	}
	/**
	 * 根据客户id获取图片
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public List<ImageResource> getImgByCustomerId(String id) throws Exception {
		List<ImageResource> imageResources;
		imageResources = imageResourceService.getImgByCustomerId(id);
		return imageResources;
	}
	/**
	 * 上传图片
	 * @param customer
	 * @param contractImgs
	 * @param designImgs
	 * @return
	 * @throws Exception 
	 */
	public int uploadImg(Customer customer,MultipartFile[] contractImgs,MultipartFile[] designImgs) throws Exception{
		int code = 0;
		if(contractImgs!=null && contractImgs.length>0){// 保存图片
			int type=1;//资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
//			code = saveImageResource(contractImgs,customer.getId(),type);
			code = ImageUtil.saveImageResourceByLimit(imageResourceService, imageFlowService, contractImgs, null , customer.getId(),type);
		}
		if(designImgs!=null && designImgs.length>0){
			int type=2;
//			code = saveImageResource(designImgs,customer.getId(),type);
			code = ImageUtil.saveImageResourceByLimit(imageResourceService, imageFlowService, designImgs, null , customer.getId(),type);
		}
		return code;
	}
	/**
	 * 根据电话号码获取用户
	 * @param phone
	 * @return
	 */
	public Customer getCustomerByUserId(String userId){
		Customer customer;
		try {
			customer = customerMapper.getCustomerByUserId(userId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return customer;
	}

	/**
	 * 
	 * @param contractImgs
	 * @param cusId 客户id
	 * @param type 资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
//	public int saveImageResource(MultipartFile[] contractImgs,String cusId,int type) throws Exception{
//		int code = 0;
//		for (MultipartFile img : contractImgs) {
//			if (img.getSize() > 0) {
//				String path = "/imgs/" + new Date().getTime();// 本地调试路径
//				String fileName = img.getOriginalFilename();
//				if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
//					File file = new File(path);
//					try {
//						Util.checkFileIsExists(file);// 判断文件夹是否存在
//						img.transferTo(file);// 转存文件
//					} catch (Exception e) {
//						logger.error("存储图片异常信息：" + e.getMessage());
//						throw new RuntimeException("图片存储失败，请联系管理员！");
//					}
//					// 图片大小（M）
//					double fileSize = Double.parseDouble(new DecimalFormat("#.000").format((double) file.length() / (1024 * 1024)));
//					// 判断流量限制
//					ImageFlow imageFlow = imageFlowService.getOneByCusId(cusId);
//					if (checkUseAble(imageFlow, fileSize)) {
//						imageFlow.setCusId(cusId);
//						imageFlow.setFlowThisTime(fileSize);
//						imageFlowService.update(imageFlow);
//						ImageResource imageResource = new ImageResource();
//						imageResource.setId(UUid.getUUid());
//						imageResource.setResourceName(fileName);
//						imageResource.setResourceUrl(file.getPath());// 本地调试
//						imageResource.setResourceSize(fileSize);
//						imageResource.setResourceType(type);
//						imageResource.setCusId(cusId);
//						code = imageResourceService.add(imageResource);
//					}else {
//						try {
//							file.delete();
//						} catch (Exception e) {
//							logger.error("存储图片异常信息：" + e.getMessage());
//							throw new RuntimeException("图片存储失败，请联系管理员！");
//						}
//						throw new RuntimeException("图片上传大小超过限制，请联系客服购买！");
//					}
//				}
//			}
//		}
//		return code;
//	}
//	public static boolean checkUseAble(ImageFlow imageFlow,double fileSize){
//		int limit = (int)imageFlow.getFlowLimit()*1000;
//		int use = (int)imageFlow.getFlowUsed()*1000+(int)fileSize*1000;
//		return limit>=use?true:false;
//	}
}

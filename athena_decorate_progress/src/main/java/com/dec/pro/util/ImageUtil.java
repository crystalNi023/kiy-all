package com.dec.pro.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.project.ImageFlow;
import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.service.project.ImageFlowService;
import com.dec.pro.service.project.ImageResourceService;

public class ImageUtil {
	private static Logger logger=Logger.getLogger(ImageUtil.class);
	/**
	 * 直接保存图片
	 * @param contractImgs
	 * @param id 客户id
	 * @param type 资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static int saveImageResource(ImageResourceService imageResourceService,MultipartFile[] contractImgs,String id,int type) throws Exception{
		int code = 0;
		for (MultipartFile img : contractImgs) {
			if (img.getSize() > 0) {
				String path = "/imgs/" + new Date().getTime();// 本地调试路径
				String fileName = img.getOriginalFilename();
				if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
					File file = new File(path);
					try {
						Util.checkFileIsExists(file);//判断文件夹是否存在
						img.transferTo(file);
					} catch (Exception e) {
						logger.error("存储图片异常信息："+e.getMessage());
						throw new RuntimeException("图片存储失败，请联系管理员！");
					}
					ImageResource imageResource = new ImageResource();
					imageResource.setId(UUid.getUUid());
					imageResource.setResourceName(fileName);
					imageResource.setResourceUrl(file.getPath());// 本地调试
					imageResource.setResourceSize(Double.parseDouble(new DecimalFormat("#.000").format((double) file.length() / (1024 * 1024))));// 图片大小（M）
					imageResource.setResourceType(type);
					imageResource.setCusId(id);
					code = imageResourceService.add(imageResource);
				}
			}
		}
		return code;
	}
	/**
	 * 保存图片限制流量
	 * @param imageResourceService
	 * @param imageFlowService
	 * @param contractImgs
	 * @param changeRecordId 需求变更id（需求变更要判断图片大小）
	 * @param cusId 客户id
	 * @param type 资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
	 * @return
	 * @throws Exception
	 */
	public static int saveImageResourceByLimit(ImageResourceService imageResourceService,ImageFlowService imageFlowService,MultipartFile[] contractImgs,String changeRecordId,String cusId,int type) throws Exception{
		int code = 0;
		for (MultipartFile img : contractImgs) {
			if (img.getSize() > 0) {
				String path = "/imgs/" + new Date().getTime();// 本地调试路径
				String fileName = img.getOriginalFilename();
				if (fileName.endsWith("jpg") || fileName.endsWith("png")) {
					File file = new File(path);
					try {
						Util.checkFileIsExists(file);// 判断文件夹是否存在
						img.transferTo(file);// 转存文件
					} catch (Exception e) {
						logger.error("存储图片异常信息：" + e.getMessage());
						throw new RuntimeException("图片存储失败，请联系管理员！");
					}
					// 图片大小（M）
					double fileSize = Double.parseDouble(new DecimalFormat("#.000").format((double) file.length() / (1024 * 1024)));
					//如果小于0.001，就按照0.001计划
					if (fileSize<=0.001) {
						fileSize = 0.001;
					}
					// 判断流量限制
					ImageFlow imageFlow = imageFlowService.getOneByCusId(cusId);
					if (checkUseAble(imageFlow, fileSize)) {
						imageFlow.setCusId(cusId);
						imageFlow.setFlowThisTime(fileSize);
						imageFlow.setFlowUsed(imageFlow.getFlowUsed()+fileSize);
						imageFlowService.update(imageFlow);
						ImageResource imageResource = new ImageResource();
						imageResource.setId(UUid.getUUid());
						imageResource.setResourceName(fileName);
						imageResource.setResourceUrl(file.getPath());// 本地调试
						imageResource.setResourceSize(fileSize);
						imageResource.setResourceType(type);
						if (changeRecordId!=null) {
							imageResource.setCusId(changeRecordId);
						}else{
						imageResource.setCusId(cusId);
						}
						code = imageResourceService.add(imageResource);
					}else {
						try {
							file.delete();
						} catch (Exception e) {
							logger.error("存储图片异常信息：" + e.getMessage());
							throw new RuntimeException("图片存储失败，请联系管理员！");
						}
						throw new RuntimeException("图片上传大小超过限制，请联系客服购买！");
					}
				}
			}
		}
		return code;
	}
	public static boolean checkUseAble(ImageFlow imageFlow,double fileSize){
		int limit = (int)imageFlow.getFlowLimit()*1000;
		int use = (int)(imageFlow.getFlowUsed()*1000+fileSize*1000);
		return limit>=use?true:false;
	}
}

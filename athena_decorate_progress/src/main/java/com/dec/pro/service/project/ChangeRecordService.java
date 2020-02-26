package com.dec.pro.service.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.project.ChangeRecord;
import com.dec.pro.entity.project.ImageResource;
import com.dec.pro.entity.project.Procedure;
import com.dec.pro.entity.project.Project;
import com.dec.pro.mapper.project.ChangeRecordMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.ImageUtil;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;
@Service
public class ChangeRecordService extends BaseService<ChangeRecord>{
	private static Logger logger=Logger.getLogger(ChangeRecordService.class);

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProcedureService procedureService;
	@Autowired
	private ChangeRecordMapper changeRecordMapper;
	@Autowired
	private ImageResourceService imageResourceService;
	@Autowired
	private ImageFlowService imageFlowService;
	
	/**
	 * 根据项目id获取所有流程（包括需求变更）
	 * @param procedure
	 * @return
	 * @throws Exception
	 */
	public List<Procedure> getListByProId(Procedure procedure) throws Exception{
		List<Procedure> result = procedureService.getProcedureList(procedure).getResult();
		for (Procedure procedure2 : result) {
			List<ChangeRecord> changeRecordlist;
			try {
				changeRecordlist = changeRecordMapper.getListByProcId(procedure2.getId());
			} catch (Exception e) {
				logger.error("数据库异常信息：" + e.getMessage());
				throw new RuntimeException("数据异常，请联系管理员！");
			}
			for (ChangeRecord changeRecord : changeRecordlist) {
				List<String> changeRecordUrls = new ArrayList<>();
				// 获取需求变更图片
				List<ImageResource> imgByCustomerId = imageResourceService.getImgByCustomerId(changeRecord.getId());
				for (ImageResource imageResource : imgByCustomerId) {
					changeRecordUrls.add(imageResource.getResourceUrl());
				}
				changeRecord.setChangeRecordUrls(changeRecordUrls);
			}
			procedure2.setChangeRecordList(changeRecordlist);
		}
		return result;
	}
	
	/**
	 * 新增客户需求变更（可上传图片）
	 * @param changeRecord
	 * @param changeRecordImgs
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	@Transactional
	public int add(ChangeRecord changeRecord,MultipartFile[] changeRecordImgs) throws Exception{
		int code = 0;
		changeRecord.setId(UUid.getUUid());
		changeRecord.setChangeTime(new Date());// 设置客户需求变更时间
		changeRecord.setNotice(1);// 设置为新通知
		//获取客户id
		Procedure one = procedureService.getOne(changeRecord.getProcId());
		Project one2 = projectService.getOne(one.getProId());
		String cusId = one2.getCusId();
		code = super.add(changeRecord);
		if (code != 1)
			return code;
		if (changeRecordImgs != null && changeRecordImgs.length > 0) {// 保存图片
			int type = 4;//资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
			ImageUtil.saveImageResourceByLimit(imageResourceService, imageFlowService, changeRecordImgs,changeRecord.getId() , cusId, type);
		}
		return code;
	}
	
	/**
	 * 根据项目id设置计划变更为已读
	 * @param procedure
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int readByProId(Procedure procedure) throws Exception{
		int count = 0;
		try {
			Page<Procedure> procedureList = procedureService.getProcedureList(procedure);
			List<Procedure> procedures = procedureList.getResult();
			for (Procedure procedure2 : procedures) {
				List<ChangeRecord> listChangeRecord = changeRecordMapper.getListByProcId(procedure2.getId());
				for (ChangeRecord changeRecord : listChangeRecord) {
					changeRecord.setProcId(procedure2.getId());//绑定流程id
					changeRecord.setNotice(2);//把通知设为已读
					count = changeRecordMapper.update(changeRecord);
				}
			}
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}
	/**
	 * 获取需求变更新消息条数
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public int getNews(String proId) throws Exception {
		int newsNumber;
		try {
			newsNumber = changeRecordMapper.getNews(proId);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return newsNumber;
	}
	
}

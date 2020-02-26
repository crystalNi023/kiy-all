package com.dec.pro.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dec.pro.entity.feedback.Feedback;
import com.dec.pro.service.BaseService;
import com.dec.pro.service.project.ImageResourceService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.ImageUtil;
import com.dec.pro.util.Page;

@Service
public class FeedbackService extends BaseService<Feedback> {
	@Autowired
	private ImageResourceService imageResourceService;
	/**
	 * 按条件分页查询
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public Page<Feedback> getFeedbackList(Feedback f) throws Exception {
		pages.setPageNo(f.getPageNo());
		pages.setPageSize(f.getPageSize());
		Page<Feedback> page;
		page = getPage(pages, GetField.getOTM("分页查询意见反馈：",f));
		return page;
	}
	public int addFeedback(Feedback feedback,MultipartFile[] feedbackImage) throws Exception{
		int count=0;
		count = this.add(feedback);//添加记录
		if(feedbackImage!=null && feedbackImage.length>0){//上传图片--支持多张
			int type=5;//资源类型  1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈
			ImageUtil.saveImageResource(imageResourceService,feedbackImage,feedback.getId(),type);
		}else {
			//不作处理
		}
		return count;
	}
}

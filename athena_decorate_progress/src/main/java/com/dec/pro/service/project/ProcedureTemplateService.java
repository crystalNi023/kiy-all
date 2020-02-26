package com.dec.pro.service.project;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dec.pro.entity.project.ProcedureTemplate;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.GetField;
@Service
public class ProcedureTemplateService extends BaseService<ProcedureTemplate>{
	/**
	 * 查询项目下所有流程
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public List<ProcedureTemplate> getProcedureTemplateList(ProcedureTemplate pt) throws Exception {
		pages.setPageNo(pt.getPageNo());
		pages.setPageSize(pt.getPageSize());
		List<ProcedureTemplate> listPt;
		listPt = getList(GetField.getOTM(" 查询项目下所有流程：",pt));// 按条件查询项目
		return listPt;
	}
}

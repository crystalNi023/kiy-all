package com.dec.pro.service.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.company.DecorationCompany;
import com.dec.pro.mapper.company.DecorationCompanyMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
@Service
public class DecorationCompanyService extends BaseService<DecorationCompany>{
	@Autowired
	private DecorationCompanyMapper decorationCompanyMapper;
	/**
	 * 按条件分页查询
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public Page<DecorationCompany> getDecorationCompanyList(DecorationCompany dc) throws Exception{
		pages.setPageNo(dc.getPageNo());
		pages.setPageSize(dc.getPageSize());
		Page<DecorationCompany> resultPage = getPage(pages, GetField.getOTM(" 分页查询项目:",dc));//按条件查询项目
		return resultPage;
	}
	/**
	 * 修改
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public int updateDecorationCompanyById(DecorationCompany dc) throws Exception{
		return decorationCompanyMapper.updateDeviceAccount(dc);
	}
	/**
	 * 根据设备序列号获取公司信息
	 * @param dc
	 * @return
	 * @throws Exception
	 */
	public DecorationCompany getOneBySerialNumber(String serialNumber) throws Exception{
		return decorationCompanyMapper.getOneBySerialNumber(serialNumber);
	}
}

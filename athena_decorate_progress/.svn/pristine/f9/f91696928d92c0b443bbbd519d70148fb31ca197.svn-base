package com.dec.pro.service.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dec.pro.entity.sms.SMS;
import com.dec.pro.mapper.sms.SMSMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;
import com.dec.pro.util.SMSUtil;
import com.dec.pro.util.UUid;

@Service
public class SMSService extends BaseService<SMS> {
	@Autowired
	private SMSMapper smsMapper;

	/**
	 * 单发
	 * 
	 * @param sms
	 * @return
	 * @throws Exception 
	 */
	public int sendOneSMSMsg(SMS sms) throws Exception {
		// 发送短信

		// 存数据库
		sms.setCId(sms.getId());// 获取短信客户id作为外键
		sms.setId(UUid.getUUid());// 生成主键
		sms.setStatus(1);// 发送成功
		add(sms);
		return 1;// 0失败 1成功
	}

	/**
	 * 群发
	 * 
	 * @param sms
	 * @return
	 * @throws Exception 
	 */
	public int sendListSMSMsg(List<SMS> listSms) throws Exception {
		StringBuffer mobiles = new StringBuffer();
		String param ="";
		String templateId ="";
		String result = null;
		if(listSms!=null && listSms.size()>0){
			param = listSms.get(0).getContent();//获取模板填充内容
			templateId = listSms.get(0).getTemplateId();//获取模板短信模板id
		}
		String uid = UUid.getUUid();
		// 发送短信
		for (int i = 0; i < listSms.size(); i++) {
			mobiles.append(listSms.get(i).getPhone());
			if (i == (listSms.size() - 1))
				continue;
			else
				mobiles.append(",");
		}

		result = SMSUtil.sendSmsBatch(templateId, param, mobiles.toString(), uid);
		
		listSms = addResMsg(listSms, result);//整理返回信息
		// 存数据库
		int count = addBatch(listSms);// 批量存储
		return count;// 数据库成功条数
	}

	/**
	 * 添加短信客户
	 * 
	 * @param sms
	 * @return
	 */
	public int addClient(SMS sms) {
		sms.setId(UUid.getUUid());
		return smsMapper.addClient(sms);
	}

	/**
	 * 按条件分页查询
	 * @param sms
	 * @return
	 * @throws Exception
	 */
	public Page<SMS> getSMSClientList(SMS sms) throws Exception {
		pages.setPageNo(sms.getPageNo());
		pages.setPageSize(sms.getPageSize());
		return getPage(pages, GetField.getOTM(null,sms));
	}
	public List<SMS> getListSMS(SMS sms) throws Exception{
		return smsMapper.getList(GetField.getOTM(" 查询多个客户：",sms));
	}
	/**
	 * 短信发送完成后补充返回信息
	 * 
	 * @param listSms
	 * @param result
	 *            json数据（详系见http://docs.ucpaas.com/doku.php?id=%E7%9F%AD%E4%BF%
	 *            A1:sendsms_batch）
	 * @return
	 */
	private List<SMS> addResMsg(List<SMS> listSms, String result) {
		JSONObject js = (JSONObject) JSONObject.parse(result);

		JSONArray jsonArray = (JSONArray) js.get("report");
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				String str = jsonArray.getString(i);
				JSONObject s = (JSONObject) JSONObject.parse(str);
				String mobile = s.getString("mobile");
				String smsId = s.getString("smsid");
				String msg = s.getString("msg");
				int status = 0;
				if("0".equals(s.getString("code"))){
					status=1;
				}else{
					status=2;
				}
				for (SMS sms : listSms) {// 将对应电话号码信息补全
					if (sms.getPhone().equals(mobile)) {
						sms.setCId(sms.getId());// 获取短信客户id作为外键
						sms.setId(UUid.getUUid());// 生成主键
						sms.setSmsId(smsId);
						sms.setMsg(msg);
						sms.setStatus(status);
						//sms.setContent(content);//短信内容  需通过获取模板查询|前端传进来
					} else {
						// 继续遍历，直到完成
					}
				}

			}
		}
		return listSms;
	}
}

package com.dec.pro.service.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.push.Push;
import com.dec.pro.mapper.push.PushMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.PushUtil;
@Service
public class PushService extends BaseService<Push>{
	@Autowired
	private PushMapper pushMapper;
	public Push getPushByUserId(String userId){
		Push push;
		try {
			push = pushMapper.getPushByUserId(userId);
		} catch (Exception e) {
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return push;
	}
	/**
	 * 
	 * @param push 发送设备
	 * @param pushType 发送消息分类
	 * @return
	 * @throws Exception 
	 */
	public void sendOnePushMessage(Push push,int pushType) throws Exception{
		String alert="";
		switch (pushType){// 1.进度提示 2.整改/延期申请 3.验收申请
		case 1:
			alert="装修进度已更新";
			break;
		case 2:
			alert="装修发起整改/延期申请";
			break;
		case 3:
			alert="装修发起验收申请";
			break;
		case 4:
			alert="装修发起新流程";
			break;
		default:
			alert="未定义";
			break;
			
		}
		if(push.getDeviceType()==1){
			PushUtil.sendAndroidUnicast(push.getDeviceTokens(),alert);
		}else if(push.getDeviceType()==2){
			PushUtil.sendIOSUnicast(push.getDeviceTokens(),alert);
		}else{
			System.err.println("未识别的设备！"+"  用户编号为："+push.getUserId());
			//不作处理
		}
	}
	
}

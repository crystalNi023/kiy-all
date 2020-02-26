package com.kiy.cloud.data.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kiy.cloud.data.Data;
import com.kiy.cloud.data.bean.XMDeviceBean;
import com.kiy.cloud.data.dao.XmDeviceDao;

public class XmDeviceDaoImpl implements XmDeviceDao{

	@Override
	public boolean create(XMDeviceBean xmDeviceBean) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        int insert = session.insert("xmDevice.create",xmDeviceBean);
        session.commit();
        session.close();
        return insert == 1;
	}

	@Override
	public boolean deleteByDeviceMac(String deviceMac){
		 SqlSessionFactory factory = Data.getSqlSessionFactory();
	     SqlSession session = factory.openSession();
	     int delete = session.delete("xmDevice.deleteByDeviceMac",deviceMac);
	     session.commit();
	     session.close();
	     return delete == 1;
	}

	@Override
	public boolean updateDeviceName(XMDeviceBean xmDeviceBean) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int update = session.update("xmDevice.updateDeviceName",xmDeviceBean);
		session.commit();
		session.close();
		return update == 1;
	}

	@Override
	public List<XMDeviceBean> selectBySingleProductUserId(
			String singleProductUserId) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		List<XMDeviceBean> xmDeviceBean = session.selectList(
				"xmDevice.selectBySingleProductUserId", singleProductUserId);
		session.close();
		return xmDeviceBean;
	}

	@Override
	public boolean createSingleProductXMDevice(XMDeviceBean xmDeviceBean) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        int insert = session.insert("xmDevice.createSingleProductXMDevice",xmDeviceBean);
        session.commit();
        session.close();
        return insert == 1;
	}

	@Override
	public XMDeviceBean getOneByDeviceMac(String deviceMac) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		XMDeviceBean xmDeviceBean = session.selectOne(
				"xmDevice.getOneByDeviceMac", deviceMac);
		session.close();
		return xmDeviceBean;
	}

	@Override
	public boolean deleteSingleProductXMDevice(XMDeviceBean xmDeviceBean) {
		SqlSessionFactory factory = Data.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		int delete = session.delete("xmDevice.deleteSingleProductXMDevice",xmDeviceBean);
		session.commit();
		session.close();
		return delete!=0;
	}

}

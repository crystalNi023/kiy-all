package com.kiy.cloud.data.dao.impl;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kiy.cloud.data.Data;
import com.kiy.cloud.data.bean.ServoBean;
import com.kiy.cloud.data.dao.ServoDao;

public class ServoDaoImpl implements ServoDao{

	@Override
	public String findCustomerIdById(String id) {
		SqlSessionFactory factory =Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        String servoBean = session.selectOne("servos.findCustmomerById",id);         
        session.close();
        return servoBean;
	}

	@Override
	public ServoBean findServoById(String id) {
		SqlSessionFactory factory =Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        ServoBean servoBean = session.selectOne("servos.findServoById",id);         
        session.close();
        return servoBean;
	}
	
	@Override
	public boolean updateServoIp(String id, String ip){
		HashMap<String, String> map = new HashMap<>();
		map.put("ip_address", ip);
		map.put("id", id);
		SqlSessionFactory factory =Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        session.update("servos.updateServoIp", map);
        session.commit();   
        session.close();
		return false;
	}

}

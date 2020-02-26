package com.kiy.cloud.data.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.kiy.cloud.data.Data;
import com.kiy.cloud.data.bean.FeedbackBean;
import com.kiy.cloud.data.dao.FeedBackDao;

public class FeedBackDaoImpl implements FeedBackDao{

	@Override
	public boolean createFeedBack(FeedbackBean feedbackBean){
        SqlSessionFactory factory = Data.getSqlSessionFactory();
        SqlSession session = factory.openSession();
        int insert = session.insert("feedbacks.createFeedback",feedbackBean);
        session.commit();
        session.close();
        return insert == 1;
	}

}

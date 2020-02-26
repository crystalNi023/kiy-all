package com.kiy.cloud.data.dao.impl;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.kiy.cloud.Config;
import com.kiy.cloud.data.bean.UserIconBean;
import com.kiy.cloud.data.dao.UserIconDao;

public class UserIconDaoImpl implements UserIconDao {

	@Override
	public boolean createUserIcon(UserIconBean userIconBean) throws Exception {
		InputStream inputStream = Resources.getResourceAsStream(Config.MYBATIS_CONFIG_URL);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();
        int insert = session.insert("user_icons.createUserIcon",userIconBean);
        session.commit();
        session.close();
        return insert == 1;
	}

}

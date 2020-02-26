package com.kiy.test;

import java.text.ParseException;

import com.kiy.common.Config;
import com.kiy.data.Data;
import com.kiy.data.DataOperation;
import com.kiy.driver.Log;

public class TestDatabase {
	public static void main(String[] args) throws ParseException {
		Config.load();
		Data.initialize();
		
		/**
		 * 登录测试
		 */
//		User login = Data.login("hlx", "abc");
//		if(login!=null) {
//			Log.debug("id"+ " "+ login.getId());
//			Log.debug("username"+ " "+ login.getUsername());
//			Log.debug("power"+ " "+ login.getPower().name());
//			Log.debug("realname"+ " "+ login.getRealname());
//			Log.debug("begin_number"+ " "+ login.getBeginNumber());
//			Log.debug("end_number"+ " "+ login.getEndNumber());
//			Log.debug("enable"+ " "+ login.isEnable());
//		}else {
//			Log.debug("登录失败");
//		}
		
		/**
		 * 创建卡  
		 */
		
		//97601068271140864
//		Card card = new Card();
//		card.setUserId(97601068271140864L);
//		card.setNumber(3333333333333L);
//		card.setKind(Kind.ONCE);
//		card.setRemark("创建人：测试");
//		boolean createCard = Data.createCard(card);
//		if(createCard) 
//			Log.debug("创建卡成功");
//		else
//			Log.debug("创建卡失败");
		
		/**
		 * 创建制卡记录
		 */
//		Card card = new Card();
//		card.setUserId(97601068271140864L);
//		card.setNumber(3333333333333L);
//		card.setKind(Kind.ONCE);
//		card.setRemark("创建人：测试");
//		boolean createCard = Data.createWriteCard(card);
//		if(createCard) 
//			Log.debug("创建制卡记录成功");
//		else
//			Log.debug("创建制卡记录失败");
		
		/**
		 * 创建读卡记录
		 */
//		Card card = new Card();
//		card.setUserId(97601068271140864L);
//		card.setNumber(3333333333333L);
//		card.setKind(Kind.ONCE);
//		card.setRemark("创建人：测试");
//		boolean createCard = Data.createReadCard(card);
//		if(createCard) 
//			Log.debug("创建读卡记录成功");
//		else
//			Log.debug("创建读卡记录失败");
	
		/**
		 * 创建领卡记录
		 */
		boolean createGetCardRecord = DataOperation.createGetCardRecord(25546009253774407L,25546009253774407L,2, 10, "测试");
		if(createGetCardRecord) 
			Log.debug("创建领卡记录成功");
		else
			Log.debug("创建领卡记录失败");
		
//		/**
//		 * 获取所有用户
//		 */
//		DataCache dataCache = DataCache.getInstance();
//		List<User> selectUser = DataOperation.selectUser();
//		for (User user : selectUser) {
//			dataCache.addUser(user);
//		}
//		for (User user : dataCache.getUsers()) {
//			System.out.println(user.getId());
//		}
//		
//		/**
//		 * 更新用户
//		 */
//		User user = dataCache.getUser(25546009253774407l);
//		user.setRealname("TEST 用户");
//		boolean updateUser = DataOperation.UpdateUser(user);
//		if(updateUser) 
//			Log.debug("更新用户成功");
//		else
//			Log.debug("更新用户失败");
		
//		/**
//		 * 获取所有制卡记录
//		 */
//		DataCache dataCache = DataCache.getInstance();
//		List<WriteCard> selectWriteCard = DataOperation.selectWriteCard(null,Util.parse("2018-04-03 10:34:40"),Util.parse("2018-04-03 10:34:40"));
//		for (WriteCard writeCard : selectWriteCard) {
//			dataCache.addWriteCard(writeCard);
//		}
//		
//		for (WriteCard writeCard : dataCache.getWriteCard()) {
//			System.out.println(writeCard.getCreated());
//		}
//		
		/**
		 * 获取所有读卡记录
		 */
//		DataCache dataCache = DataCache.getInstance();
//		List<ReadCard> selectReadCard = DataOperation.selectReadCard(null,Util.parse("2018-04-08 10:34:40"),Util.parse("2018-04-09 10:34:40"));
//		for (ReadCard readCard : selectReadCard) {
//			dataCache.addReadCard(readCard);
//		}
//		
//		for (ReadCard readCard : dataCache.getReadCard()) {
//			System.out.println(readCard.getCreated());
//		}
		
		/**
		 * 获取所有领卡记录
		 */
//		DataCache dataCache = DataCache.getInstance();
//		List<GetCard> selectGetCard = DataOperation.selectGetCard(Util.parse("2018-04-08 10:34:40"), Util.parse("2018-04-09 16:34:40"));
//		for (GetCard getCard : selectGetCard) {
//			dataCache.addGetCard(getCard);
//		}
//		
//		for (GetCard getCard : dataCache.getGetCard()) {
//			System.out.println(getCard.getBegin());
//		}
	}
}

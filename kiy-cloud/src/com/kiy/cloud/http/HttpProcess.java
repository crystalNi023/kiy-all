package com.kiy.cloud.http;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import com.kiy.cloud.Config;
import com.kiy.cloud.Log;
import com.kiy.cloud.data.bean.FeedbackBean;
import com.kiy.cloud.data.bean.SingleProductUserBean;
import com.kiy.cloud.data.bean.UserIconBean;
import com.kiy.cloud.data.bean.XMDeviceBean;
import com.kiy.cloud.data.dao.SingleProductUserDao;
import com.kiy.cloud.data.dao.XmDeviceDao;
import com.kiy.cloud.data.dao.impl.FeedBackDaoImpl;
import com.kiy.cloud.data.dao.impl.SingleProductUserDaoImpl;
import com.kiy.cloud.data.dao.impl.UserIconDaoImpl;
import com.kiy.cloud.data.dao.impl.XmDeviceDaoImpl;
import com.kiy.cloud.master.MasterGroup;
import com.kiy.cloud.master.MasterHandler;
import com.kiy.cloud.recognize.RecognizeListener;
import com.kiy.cloud.recognize.RecognizeMessageQueue;
import com.kiy.cloud.tool.OauthServiceTool;
import com.kiy.cloud.tool.SMS;
import com.kiy.cloud.tool.WeatherManager;
import com.kiy.common.Tool;
import com.kiy.common.Weather;
import com.kiy.protocol.Messages.Login;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.ReadAllDeviceStatus;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.SelectDevices;
import com.kiy.protocol.Messages.SyncUserIcon;
import com.kiy.protocol.Messages.SyncUserIcon.Builder;

/**
 * HTTP 请求处理
 * 
 * @author HLX Tel:18996470535
 * @date 2018年7月20日 Copyright:Copyright(c) 2018
 */
public final class HttpProcess {

	public static final int CODE_JSON = 1; /* JSON 请求 */
	public static final int CODE_XML = 2; /* XML 请求 */
	public static final int CODE_HTML = 3; /* HTML 请求 */

	public static final boolean SPEECH_RECOGNIZE = true;
	public static final boolean SPEECH_SYNTHESIS = false;

	/**
	 * 此标志用于处理保存，上传不同文件保存的相应位置标识
	 */
	public static final int FEEDBACK_IMG = 3; /* 意见反馈请求 */
	public static final int USER_ICON_IMG = 4; /* 用户头像上传 */
	public static final int PHONETIC_FILE = 5; /* 语音识别文件 */

	/**
	 * 返回结果常量
	 */
	private static final String SUCCESS = "SUCCESS"; /* 成功 */
	private static final String SERVER_INTERNAL_ERROR = "Server internal error"; /* 服务器内部错误 */

	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk
	private HttpPostRequestDecoder decoder;

	/**
	 * 上传的文件的URL
	 */
	private String fileUrl;
	/**
	 * 上传的文件的真实路径
	 */
	private String fileTurePath;

	/**
	 * HTTP 请求处理入口
	 * 
	 * @param type 数据交互类型：(json/xml)
	 * @param path 请求方法
	 * @param parameters 请求参数
	 * @param writer
	 * @param request
	 * @throws IOException
	 */
	public void process(int type, String path, HttpParameters parameters, PrintWriter writer, FullHttpRequest request) throws Exception {
		switch (path) {
		// 创建意见反馈
			case "create_feedback":
				createFeedback(type, parameters, writer, request);
				break;
			// 上传用户头像
			case "upload_userIcon":
				uploadUserIcon(type, parameters, writer, request);
				break;
			// 语音识别
			case "phonetic_recognition":
				phoneticRecognition(type, writer, request);
				break;
			// 获取在线伺服器
			case "getOnlineServo": {
				MasterGroup groups = MasterHandler.getGroups();
				if (groups != null) {
					ConcurrentMap<String, Channel> channel_keys = groups.getOnlineServoKey();
					getOnlineServo(writer, type, channel_keys);
				} else {
					getOnlineServo(writer, type, null);
				}
			}
				break;
			// 获取所有设备列表
			case "select_devices": {
				selectDevices2(type, parameters, writer);
				// selectDevices(type, parameters, writer);
			}
				break;
			// 读取所有设备状态
			case "read_all_device_status": {
				readAllDeviceStatus(type, parameters, writer);
			}
			break;
			// 天猫授权页面
			case "tm_authorization": {
				parameters.getValue("redirect_uri");
				parameters.getValue("token");
				parameters.getValue("token");
				
				FileReader fileReader = new FileReader(Tool.getWorkPath() + File.separatorChar + "data" + File.separatorChar + "pages" + File.separatorChar + "authorization.html");
				System.err.println(Tool.getWorkPath() + File.separatorChar + "data" + File.separatorChar + "pages" + File.separatorChar + "authorization.html");
				BufferedReader br = new BufferedReader(fileReader);
				String s = null;
				String result = "";
				while ((s = br.readLine()) != null) {
					result = result + s;
				}
				br.close();
				fileReader.close();
				writer.println(result);
				break;
			}
			// 伺服器授权登录
			case "servo_login": {
				String username = parameters.getValue("username");
				String password = parameters.getValue("password");
				// 伺服器ID
				String servoId = parameters.getValue("servo_id");
				if (Tool.isEmpty(username) || Tool.isEmpty(password) || Tool.isEmpty(servoId)) {
					break;
				}
				MasterGroup groups = MasterHandler.getGroups();
				if (groups == null || !groups.getOnlineServoKey().containsKey(servoId)) {
					break;
				}
				// 构建protobuf协议消息
				Message.Builder msg = Message.newBuilder();
				msg.setUserId("cloud-" + servoId);
				Login.Builder loginBuilder = msg.getLoginBuilder();
				loginBuilder.setUsername(username);
				loginBuilder.setPassword(Tool.MD5(password));
				msg.setLogin(loginBuilder.build());
				Message message = sendMessageToServo(type, writer, msg.build());
				if(message.getResult()==Result.SUCCESS){
					//登录成功
					
				}
				
			}
			break;
			//发送验证码
			case "sendSms":{
				String phone = parameters.getValue("phone");
				if (Tool.isEmpty(phone)) 
					return;
				String sendSMSVerify = SMS.sendSMSVerify(phone);
				if (sendSMSVerify==null) {//成功
					success(writer, type, "成功");
					return;
				}else{
					parameterError(writer, type, sendSMSVerify);
					return;
				}
			}
			//校验验证码
			case "checkVerificationCode":{
				String phone = parameters.getValue("phone");
				String code = parameters.getValue("code");
				if (Tool.isEmpty(phone)) 
					return;
				if (SMS.ensureVerificationCode(phone, code)) {
					success(writer, type, "成功");
					return;
				}else{
					parameterError(writer, type, "验证码错误！");
					return;
				}
			}
			//查看账户是否存在
			case "singleProductUserExist":{
				String username = parameters.getValue("username");
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean selectByUsername = singleProductUserDao.selectByUsername(username);
				if (selectByUsername!=null) {
					success(writer, type, "用户已存在！");
					return;
				}else{
					parameterError(writer, type, "用户不存在！");
					return;
				}
			}
			//单品登录
			case "loginForSingleProduct":{
				String username = parameters.getValue("username");
				String password = parameters.getValue("password");
				if (Tool.isEmpty(username) || Tool.isEmpty(password)) {
					parameterError(writer, type, "用户名或密码不能为空！");
					return;
				}
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean selectByUsername = singleProductUserDao.selectByUsername(username);
				if (selectByUsername==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}
				if (!selectByUsername.isEnable()) {
					parameterError(writer, type, "用户暂未启用！");
					return;
				}
				if(!selectByUsername.getPassword().equals(Tool.MD5(password))){
					parameterError(writer, type, "密码错误！");
					return;
				}else{
					successSingleProduct(writer, type, selectByUsername, "登录成功");
					return;
				}
			}
			// 创建单品账号
			case "createSingleProductUser":{
				String username = parameters.getValue("username");
				String password = parameters.getValue("password");
				String remark = parameters.getValue("remark");
				if (Tool.isEmpty(username) || Tool.isEmpty(password)) {
					parameterError(writer, type, "用户名或密码不能为空！");
					return;
				}
				//判断用户名是否已经存在
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				if (singleProductUserDao.selectByUsername(username) != null) {
					parameterError(writer, type, "该用户已存在！");
					return;
				}
				SingleProductUserBean singleProductUserBean = new SingleProductUserBean();
				singleProductUserBean.setId(OauthServiceTool.getOauthCode());
				int type1 = 0;
				if (parameters.getValue("type")!=null) {
					type1 = Integer.parseInt(parameters.getValue("type"));
					if (type1==2) {//注册子账号，要传关联的userId
						if (parameters.getValue("singleProductUserId")!=null) {
							singleProductUserBean.setSingleProductUserId(parameters.getValue("singleProductUserId"));
						}else{
							parameterError(writer, type, "该关联主账号！");
							return;
						}
					}
					singleProductUserBean.setType(type1);
				}
				singleProductUserBean.setUsername(username);
				singleProductUserBean.setPassword(Tool.MD5(password));
				singleProductUserBean.setRemark(remark);
				if(singleProductUserDao.create(singleProductUserBean)){
					//返回成功
					success(writer, type, "添加成功");
				}else{
					parameterError(writer, type, "操作失败！");
				}
				return;
			}
			//删除单品用户
			case "deleteSingleProductUser":{
				String id = parameters.getValue("id");
				if (Tool.isEmpty(id)) {
					parameterError(writer, type, "id不能为空！");
					return;
				}
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				if (singleProductUserDao.delete(id)) {
					success(writer, type, "删除成功");
				}else{
					parameterError(writer, type, "操作失败！");
				}
				return;
			}
			//根据singleProductUserId获取单品账号集合
			case "getListBySingleProductUserId":{
				String singleProductUserId = parameters.getValue("singleProductUserId");
				if (Tool.isEmpty(singleProductUserId)) {
					parameterError(writer, type, "singleProductUserId不能为空！");
					return;
				}
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				List<SingleProductUserBean> list = singleProductUserDao.getListBySingleProductUserId(singleProductUserId);
				successSingleProductList(writer, type, list , "查询成功");
				return;
			}
			//验证单品用户账号密码
			case "checkSingleProductUserPassword":{
				String username = parameters.getValue("username");
				String password = parameters.getValue("password");
				if (Tool.isEmpty(username) || Tool.isEmpty(password)) {
					parameterError(writer, type, "用户名或密码不能为空！");
					return;
				}
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean selectByUsername = singleProductUserDao.selectByUsername(username);
				if (selectByUsername==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}
				if(!selectByUsername.getPassword().equals(Tool.MD5(password))){
					parameterError(writer, type, "密码错误！");
					return;
				}else{
					successSingleProduct(writer, type, selectByUsername, "成功");
					return;
				}
			}
			//注销单品用户账户
			case "logoutSingleProductUser":{
				String username = parameters.getValue("username");
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean selectByUsername = singleProductUserDao.selectByUsername(username);
				if (selectByUsername==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}else {
					//查找相关联的子用户
					List<SingleProductUserBean> list = singleProductUserDao.getListBySingleProductUserId(selectByUsername.getId());
					list.add(selectByUsername);
					//删除该账户和关联的子账户
					try {
						for (SingleProductUserBean singleProductUserBean : list) {
							singleProductUserDao.delete(singleProductUserBean.getId());
						}
						successSingleProduct(writer, type, selectByUsername, "成功");
					} catch (Exception e) {
						Log.debug(e);
						parameterError(writer, type, "删除失败，请联系管理员！");
					}
					return;
				}
			}
			//验证用户是否与当前登陆账号关联
			case "verifyUsernameRelationId":{
				String id = parameters.getValue("id");
				String username = parameters.getValue("username");
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean selectByUsername = singleProductUserDao.selectByUsername(username);
				if (selectByUsername==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}else {
					//用户名就是当前用户
					if (selectByUsername.getId().equals(id)) {
						successSingleProduct(writer, type, selectByUsername, "成功");
						return;
					}
					//用户名是当前用户的子账户
					if (selectByUsername.getSingleProductUserId()==null) {
						parameterError(writer, type, "用户无关联！");
						return;
					}
					if(selectByUsername.getSingleProductUserId().equals(id)){
						successSingleProduct(writer, type, selectByUsername, "成功");
					}else{
						parameterError(writer, type, "用户无关联！");
					}
					return;
				}
			}
			//上报雄迈账号
			case "relationXMDeviceAccount":{
				String username = parameters.getValue("username");
				String xmDeviceAccount = parameters.getValue("xmDeviceAccount");
				String xmDevicePassword = parameters.getValue("xmDevicePassword");
				if (Tool.isEmpty(username) || Tool.isEmpty(xmDeviceAccount) || Tool.isEmpty(xmDevicePassword)) {
					parameterError(writer, type, "账户信息不能为空！");
					return;
				}
				SingleProductUserBean singleProductUserBean = new SingleProductUserBean();
				singleProductUserBean.setUsername(username);
				singleProductUserBean.setXmDeviceAccount(xmDeviceAccount);
				singleProductUserBean.setXmDevicePassword(xmDevicePassword);
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				if(singleProductUserDao.relationXMDeviceAccount(singleProductUserBean)){
					success(writer, type , "成功");
				}else{
					parameterError(writer, type, "失败！");
				}
				return;
			}
			//创建雄迈设备
			case "createXMDevice":{
				String deviceMac = parameters.getValue("deviceMac");
				String deviceName = parameters.getValue("deviceName");
				String singleProductUserId = parameters.getValue("singleProductUserId");
				if (Tool.isEmpty(deviceMac) || Tool.isEmpty(deviceName)) {
					parameterError(writer, type, "设备序列号或名称不能为空！");
					return;
				}
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				//判断deviceMac是否已经存在
				
				if(xmDeviceDao.getOneByDeviceMac(deviceMac)!=null){
					XMDeviceBean oneByDeviceMac = xmDeviceDao.getOneByDeviceMac(deviceMac);
					//判断该设备是否已绑定单品用户
					if(oneByDeviceMac.getSingleProductUserId()!=null){
						//判断是否绑定当前用户
						if(oneByDeviceMac.getSingleProductUserId().equals(singleProductUserId)){
							parameterError(writer, type, "该设备已被当前登陆用户添加！");
						}else{
							parameterError(writer, type, "该设备已被其他用户添加！");
						}
					}else{
						//绑定关联关系在数据库
						//如果是子账号添加要把主账号的关联关系也加上
						SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
						SingleProductUserBean one = singleProductUserDao.getOne(singleProductUserId);
						if(one!=null && one.getSingleProductUserId()!=null){//有关联的主账号
							oneByDeviceMac.setSingleProductUserId(one.getSingleProductUserId());
							xmDeviceDao.createSingleProductXMDevice(oneByDeviceMac);
						}
						oneByDeviceMac.setSingleProductUserId(singleProductUserId);
						if(xmDeviceDao.createSingleProductXMDevice(oneByDeviceMac)){
							success(writer, type , "成功");
						}else{
							parameterError(writer, type, "失败！");
						}
					}
					return;
				}
				if (Tool.isEmpty(singleProductUserId)) {
					parameterError(writer, type, "关联的单品用户id不能为空！");
					return;
				}
				XMDeviceBean xmDevice = new XMDeviceBean();
				xmDevice.setId(OauthServiceTool.getOauthCode());
				xmDevice.setDeviceMac(deviceMac);
				xmDevice.setDeviceName(deviceName);
				if(parameters.getValue("loginName")!=null){
				xmDevice.setLoginName(parameters.getValue("loginName"));}
				if(parameters.getValue("loginPsw")!=null){
				xmDevice.setLoginPsw(parameters.getValue("loginPsw"));}
				if(parameters.getValue("deviceIp")!=null){
				xmDevice.setDeviceIp(parameters.getValue("deviceIp"));}
				if(parameters.getValue("state")!=null){
				xmDevice.setState(Integer.parseInt(parameters.getValue("state")));}
				if(parameters.getValue("nPort")!=null){
				xmDevice.setNPort(Integer.parseInt(parameters.getValue("nPort")));}
				if(parameters.getValue("nType")!=null){
				xmDevice.setNType(Integer.parseInt(parameters.getValue("nType")));}
				if(parameters.getValue("nId")!=null){
				xmDevice.setNId(Integer.parseInt(parameters.getValue("nId")));}
				if(parameters.getValue("remark")!=null){
				xmDevice.setRemark(parameters.getValue("remark"));}
				if(xmDeviceDao.create(xmDevice)){
					//绑定关联关系在数据库
					//如果是子账号添加要把主账号的关联关系也加上
					SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
					SingleProductUserBean one = singleProductUserDao.getOne(singleProductUserId);
					if(one!=null && one.getSingleProductUserId()!=null){//有关联的主账号
						xmDevice.setSingleProductUserId(one.getSingleProductUserId());
						xmDeviceDao.createSingleProductXMDevice(xmDevice);
					}
					xmDevice.setSingleProductUserId(singleProductUserId);
					xmDeviceDao.createSingleProductXMDevice(xmDevice);
					success(writer, type , "成功");
				}else{
					parameterError(writer, type, "失败！");
				}
				return;
			}
			//删除雄迈设备
			case "deleteXMDevice":{
				String deviceMac = parameters.getValue("deviceMac");
				if (Tool.isEmpty(deviceMac)) {
					parameterError(writer, type, "设备序列号不能为空！");
					return;
				}
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				if (xmDeviceDao.deleteByDeviceMac(deviceMac)) {
					success(writer, type, "删除成功");
				}else{
					parameterError(writer, type, "操作失败！");
				}
				return;
			}
			//修改雄迈设备名称
			case "updateXMDeviceName":{
				String deviceMac = parameters.getValue("deviceMac");
				String deviceName = parameters.getValue("deviceName");
				if (Tool.isEmpty(deviceMac) || Tool.isEmpty(deviceName)) {
					parameterError(writer, type, "设备序列号或名称不能为空！");
					return;
				}
				XMDeviceBean xmDeviceBean = new XMDeviceBean();
				xmDeviceBean.setDeviceMac(deviceMac);
				xmDeviceBean.setDeviceName(deviceName);
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				if(xmDeviceDao.updateDeviceName(xmDeviceBean)){
					success(writer, type , "成功");
				}else{
					parameterError(writer, type, "失败！");
				}
				return;
			}
			//根据单品用户id查询雄迈设备列表
			case "selectBySingleProductUserId":{
				String singleProductUserId = parameters.getValue("singleProductUserId");
				if (Tool.isEmpty(singleProductUserId)) {
					parameterError(writer, type, "关联单品id不能为空！");
					return;
				}
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				List<XMDeviceBean> list = xmDeviceDao.selectBySingleProductUserId(singleProductUserId);
				successXMDeviceList(writer, type, list, "查询成功");
				return;
			}
			//关联单品用户和雄迈设备
			case "relationSingleProductXMDevice":{
				String xmDeviceId = parameters.getValue("xmDeviceId");
				String singleProductUserId = parameters.getValue("singleProductUserId");
				if (Tool.isEmpty(singleProductUserId) || Tool.isEmpty(xmDeviceId)) {
					parameterError(writer, type, "单品用户id或雄迈设备id不能为空！");
					return;
				}
				XMDeviceBean xmDeviceBean = new XMDeviceBean();
				xmDeviceBean.setId(xmDeviceId);
				xmDeviceBean.setSingleProductUserId(singleProductUserId);
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				if(xmDeviceDao.createSingleProductXMDevice(xmDeviceBean)){
					success(writer, type, "成功");
				}else{
					parameterError(writer, type, "操作失败！");
				}
				return;
			}
			//解绑单品用户和雄迈设备
			case "deleteSingleProductXMDevice":{
				String xmDeviceId = parameters.getValue("xmDeviceId");
				String singleProductUserId = parameters.getValue("singleProductUserId");
				if (Tool.isEmpty(singleProductUserId) || Tool.isEmpty(xmDeviceId)) {
					parameterError(writer, type, "单品用户id或雄迈设备id不能为空！");
					return;
				}
				XMDeviceBean xmDeviceBean = new XMDeviceBean();
				xmDeviceBean.setId(xmDeviceId);
				xmDeviceBean.setSingleProductUserId(singleProductUserId);
				XmDeviceDao xmDeviceDao = new XmDeviceDaoImpl();
				if(xmDeviceDao.deleteSingleProductXMDevice(xmDeviceBean)){
					success(writer, type, "成功");
				}else{
					parameterError(writer, type, "操作失败！");
				}
				return;
			}
			//获取天气接口
			case "getWeather":{
				String weatherWeaid = parameters.getValue("weatherWeaid");
				String aqiWeaid = parameters.getValue("aqiWeaid");
				if (Tool.isEmpty(weatherWeaid) || Tool.isEmpty(aqiWeaid)) {
					parameterError(writer, type, "查询天气参数不能为空！");
					return;
				}
				WeatherManager weatherManager = new WeatherManager();
				Weather weather = weatherManager.getWeather(weatherWeaid, aqiWeaid);
				if(weather!=null && weather.getResult()!=null){
					successWeather(writer, type, weather, "成功");
				}else{
					parameterError(writer, type, "获取失败！");
				}
				return;
			}
			//校验二维码
			case "checkQRCode":{
				String QRCode = parameters.getValue("QRCode");
				if (Tool.isEmpty(QRCode)) {
					parameterError(writer, type, "二维码不能为空！");
					return;
				}
				if(QRCode.contains("ATHENALL")){
					success(writer, type, "成功");
				}else{
					parameterError(writer, type, "二维码验证失败！");
				}
				return;
			}
			// 根据单品id查询单品用户账号信息
			case "getSingleProductUserName":{
				String username = parameters.getValue("username");
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				if (username==null) {
					parameterError(writer, type, "用户名不能为空！");
					return;
				}
				SingleProductUserBean singleProductUser = singleProductUserDao.selectByUsername(username);
				if (singleProductUser==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}else if (!singleProductUser.isEnable()) {
					parameterError(writer, type, "用户暂未启用！");
					return;
				}else if (singleProductUser.getSingleProductUserId()!=null) {
					SingleProductUserBean one = singleProductUserDao.getOne(singleProductUser.getSingleProductUserId());
					if (one!=null) {//关联主账号是否存在
						successSingleProduct(writer, type, one, "获取成功");
						return;
					}else{
						 parameterError(writer, type, "未找到关联的主账号！");
					     return;
					}
				  }else {
					    successSingleProduct(writer, type, singleProductUser, "获取成功");
						return;
				}
				}
			// 修改单品用户账号密码
			case "modifySingleProductUserPassword":{
				String username = parameters.getValue("username");
				String password = parameters.getValue("password");
				if (Tool.isEmpty(username) || Tool.isEmpty(password)){
					parameterError(writer, type, "用户名或密码不能为空！");
					return;
				}
				SingleProductUserDao singleProductUserDao = new SingleProductUserDaoImpl();
				SingleProductUserBean singleProductUser = singleProductUserDao.selectByUsername(username);
				if (singleProductUser==null) {
					parameterError(writer, type, "用户不存在！");
					return;
				}
				if (!singleProductUser.isEnable()) {
					parameterError(writer, type, "用户暂未启用,请联系管理员！");
					return;
				}
				boolean modifyPassword = singleProductUserDao.modifyPassword(username,Tool.MD5(password));
				if (modifyPassword) {
					success(writer, type, "修改成功");
				}else{
					parameterError(writer, type, "修改失败！");
				}
				return;
			}
			default:
				parameterError(writer, type, "错误的请求！");
				break;
		}
	}

	/**
	 * 读取上传文件
	 */
	private void getUploadFile(int type, PrintWriter writer, FullHttpRequest request, int fileType) {
		HttpContent chunk = request;
		try {
			decoder.offer(chunk);
		} catch (ErrorDataDecoderException e1) {
			Log.error(e1);
			serverError(writer, type, SERVER_INTERNAL_ERROR);
			reset();
			return;
		}
		readHttpDataChunkByChunk(type, writer, fileType); // 从解码器decoder中读出数据
	}

	/**
	 * 写入文件
	 */
	private void writeHttpData(InterfaceHttpData data, int fileType) throws IOException {
		if (data.getHttpDataType() != HttpDataType.Attribute && data.getHttpDataType() != HttpDataType.InternalAttribute) {
			String uploadFileName = getUploadFileName(data);
			if (Tool.isEmpty(uploadFileName)) {
				return;
			}

			String substring = uploadFileName.substring(uploadFileName.lastIndexOf('.'), uploadFileName.length());
			FileUpload fileUpload = (FileUpload) data;
			if (fileUpload.isCompleted()) {
				String f = "";
				if (fileType == FEEDBACK_IMG) {
					f = Config.FEEDBACK_URL;
				} else if (fileType == USER_ICON_IMG) {
					f = Config.USER_ICON_URL;
				} else {
					f = Config.PHONETIC_URL;
				}
				File dir = new File(Config.getWorkPath() + f);
				if (!dir.exists()) {
					dir.mkdir();
				}
				File dest = new File(dir, System.currentTimeMillis() + UUID.randomUUID().toString() + substring);
				fileUpload.renameTo(dest);
				fileTurePath = dest.getAbsolutePath();
				if (fileType == FEEDBACK_IMG) {
					fileUrl = Config.IMG_URL + "feedback/" + dest.getName();
				} else if (fileType == USER_ICON_IMG) {
					fileUrl = Config.IMG_URL + "userIcon/" + dest.getName();
				}
			}
		}
	}

	/**
	 * chunk 从decoder中读出数据，写入临时对象，然后写入文件， 这个封装主要是为了释放临时对象
	 */
	private void readHttpDataChunkByChunk(int type, PrintWriter writer, int fileType) {
		try {
			while (decoder.hasNext()) {
				InterfaceHttpData data = decoder.next();
				if (data != null) {
					try {
						writeHttpData(data, fileType);
					} catch (IOException e) {
						Log.error(e);
						serverError(writer, type, SERVER_INTERNAL_ERROR);
						reset();
						return;
					} finally {
						data.release();
					}
				}
			}
		} catch (EndOfDataDecoderException e) {
			// 无处理
		}
	}

	/**
	 * 重置
	 */
	private void reset() {
		fileUrl = null;
		fileTurePath = null;
		if (decoder != null) {
			decoder.destroy();
			decoder = null;
		}

	}

	/**
	 * 在InterfaceHttpData中读取上传的文件名称
	 */
	private String getUploadFileName(InterfaceHttpData data) {
		String content = data.toString();
		String temp = content.substring(0, content.indexOf("\n"));
		content = temp.substring(temp.lastIndexOf("=") + 2, temp.lastIndexOf("\""));
		return content;
	}

	/**
	 * 返回成功消息
	 */
	public void success(PrintWriter writer, int type, String success) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0000", true);
			JSON.field(writer, "msg", success, false);
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0000");
			XML.element(writer, "msg", success);
		} else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * 返回单品成功消息
	 */
	public void successSingleProduct(PrintWriter writer, int type,SingleProductUserBean singleProductUserBean, String success) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0000", true);
			JSON.field(writer, "msg", success, false);
			writer.print(',');
			writer.print('\"');
			writer.print("result");
			writer.print('\"');
			writer.print(':');
			if (singleProductUserBean!=null) {
				JSON.begin(writer);
				JSON.field(writer, "id", singleProductUserBean.getId(), true);
				JSON.field(writer, "singleProductUserId", singleProductUserBean.getSingleProductUserId(), false);
				JSON.field(writer, "username", singleProductUserBean.getUsername(), false);
				JSON.field(writer, "password", singleProductUserBean.getPassword(), false);
				JSON.field(writer, "xmDeviceAccount", singleProductUserBean.getXmDeviceAccount(), false);
				JSON.field(writer, "xmDevicePassword", singleProductUserBean.getXmDevicePassword(), false);
				JSON.field(writer, "type", singleProductUserBean.getType(), false);
				JSON.field(writer, "remark", singleProductUserBean.getRemark(), false);
				JSON.field(writer, "created", singleProductUserBean.getCreated(), false);
				JSON.field(writer, "updated", singleProductUserBean.getUpdated(), false);
				JSON.end(writer);
			}
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0000");
			XML.element(writer, "msg", success);
		} else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * 返回单品成功消息集合
	 */
	public void successSingleProductList(PrintWriter writer, int type,List<SingleProductUserBean> singleProductUserBean, String success) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0000", true);
			JSON.field(writer, "msg", success, false);
			writer.print(',');
			writer.print('\"');
			writer.print("result");
			writer.print('\"');
			writer.print(':');
			writer.print('[');
			for (int i = 0; i < singleProductUserBean.size(); i++) {
				SingleProductUserBean singleProductUser = singleProductUserBean.get(i);
				JSON.begin(writer);
				JSON.field(writer, "id", singleProductUser.getId(), true);
				JSON.field(writer, "singleProductUserId", singleProductUser.getSingleProductUserId(), false);
				JSON.field(writer, "username", singleProductUser.getUsername(), false);
				JSON.field(writer, "password", singleProductUser.getPassword(), false);
				JSON.field(writer, "xmDeviceAccount", singleProductUser.getXmDeviceAccount(), false);
				JSON.field(writer, "xmDevicePassword", singleProductUser.getXmDevicePassword(), false);
				JSON.field(writer, "type", singleProductUser.getType(), false);
				JSON.field(writer, "remark", singleProductUser.getRemark(), false);
				JSON.field(writer, "created", singleProductUser.getCreated(), false);
				JSON.field(writer, "updated", singleProductUser.getUpdated(), false);
				JSON.end(writer);
				if (i!=singleProductUserBean.size()-1) {
					writer.print(',');
				}
			}
			writer.print(']');
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0000");
			XML.element(writer, "msg", success);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 返回雄迈设备成功消息集合
	 */
	public void successXMDeviceList(PrintWriter writer, int type,List<XMDeviceBean> xmDeviceBeans, String success) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0000", true);
			JSON.field(writer, "msg", success, false);
			writer.print(',');
			writer.print('\"');
			writer.print("result");
			writer.print('\"');
			writer.print(':');
			writer.print('[');
			for (int i = 0; i < xmDeviceBeans.size(); i++) {
				XMDeviceBean xmDeviceBean = xmDeviceBeans.get(i);
				JSON.begin(writer);
				JSON.field(writer, "id", xmDeviceBean.getId(), true);
				JSON.field(writer, "singleProductUserId", xmDeviceBean.getSingleProductUserId(), false);
				JSON.field(writer, "deviceMac", xmDeviceBean.getDeviceMac(), false);
				JSON.field(writer, "deviceName", xmDeviceBean.getDeviceName(), false);
				JSON.field(writer, "loginName", xmDeviceBean.getLoginName(), false);
				JSON.field(writer, "loginPsw", xmDeviceBean.getLoginPsw(), false);
				JSON.field(writer, "deviceIp", xmDeviceBean.getDeviceIp(), false);
				JSON.field(writer, "state", xmDeviceBean.getState(), false);
				JSON.field(writer, "nPort", xmDeviceBean.getNPort(), false);
				JSON.field(writer, "nType", xmDeviceBean.getNType(), false);
				JSON.field(writer, "nId", xmDeviceBean.getNId(), false);
				JSON.field(writer, "remark", xmDeviceBean.getRemark(), false);
				JSON.field(writer, "created", xmDeviceBean.getCreated(), false);
				JSON.field(writer, "updated", xmDeviceBean.getUpdated(), false);
				JSON.end(writer);
				if (i!=xmDeviceBeans.size()-1) {
					writer.print(',');
				}
			}
			writer.print(']');
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0000");
			XML.element(writer, "msg", success);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 返回查询天气成功json
	 * @param writer
	 * @param type
	 * @param weather
	 * @param success
	 */
	public void successWeather(PrintWriter writer, int type,Weather weather, String success) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0000", true);
			JSON.field(writer, "msg", success, false);
			writer.print(',');
			writer.print('\"');
			writer.print("result");
			writer.print('\"');
			writer.print(':');
			if (weather.getResult()!=null) {
				JSON.begin(writer);
				JSON.field(writer, "weaid", weather.getResult().getWeaid(), true);
				JSON.field(writer, "days", weather.getResult().getDays(), false);
				JSON.field(writer, "week", weather.getResult().getWeek(), false);
				JSON.field(writer, "cityno", weather.getResult().getCityno(), false);
				JSON.field(writer, "citynm", weather.getResult().getCitynm(), false);
				JSON.field(writer, "cityid", weather.getResult().getCityid(), false);
				JSON.field(writer, "temperature", weather.getResult().getTemperature(), false);
				JSON.field(writer, "temperature_curr", weather.getResult().getTemperature_curr(), false);
				JSON.field(writer, "humidity", weather.getResult().getHumidity(), false);
				JSON.field(writer, "aqi", weather.getResult().getAqi(), false);
				JSON.field(writer, "weather", weather.getResult().getWeather(), false);
				JSON.field(writer, "weather_curr", weather.getResult().getWeather_curr(), false);
				JSON.field(writer, "weather_icon", weather.getResult().getWeather_icon(), false);
				JSON.field(writer, "wind", weather.getResult().getWind(), false);
				JSON.field(writer, "winp", weather.getResult().getWinp(), false);
				JSON.field(writer, "temp_high", weather.getResult().getTemp_high(), false);
				JSON.field(writer, "temp_low", weather.getResult().getTemp_low(), false);
				JSON.field(writer, "humi_high", weather.getResult().getHumi_high(), false);
				JSON.field(writer, "humi_low", weather.getResult().getHumi_low(), false);
				JSON.end(writer);
			}
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0000");
			XML.element(writer, "msg", success);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 传递参数错误消息
	 */
	public void parameterError(PrintWriter writer, int type, String error) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0001", true);
			JSON.field(writer, "msg", error, false);
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0001");
			XML.element(writer, "msg", error);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 服务器错误消息
	 */
	public void serverError(PrintWriter writer, int type, String error) {
		if (CODE_JSON == type) {
			JSON.begin(writer);
			JSON.field(writer, "code", "0002", true);
			JSON.field(writer, "msg", error, false);
			JSON.end(writer);
		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.element(writer, "code", "0002");
			XML.element(writer, "msg", error);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/*************************************************/
	/**
	 * 数据处理
	 */
	/*************************************************/

	/**
	 * 创建意见反馈
	 */
	private void createFeedback(int type, HttpParameters parameters, PrintWriter writer, FullHttpRequest request) {
		decoder = new HttpPostRequestDecoder(factory, request);
		try {
			String username = parameters.getValue("username"); // 意见反馈用户名
			String servoId = parameters.getValue("servo_id"); // 服务器key
			String content = parameters.getValue("content"); // 意见反馈内容
			if (Tool.isEmpty(username) || Tool.isEmpty(servoId) || Tool.isEmpty(content)) {
				parameterError(writer, type, "username,servo_id,content can not be null!");
				reset();
				return;
			}
			String choice = parameters.getValue("choice");
			getUploadFile(type, writer, request, FEEDBACK_IMG);

			FeedbackBean feedbackBean = new FeedbackBean();
			feedbackBean.setUsername(username);
			feedbackBean.setServoId(servoId);
			feedbackBean.setImg_url(fileUrl);
			feedbackBean.setContent(content);
			if (!Tool.isEmpty(choice)) {
				feedbackBean.setChoice(Integer.valueOf(choice));
			}
			boolean createFeedBack = false;
			createFeedBack = new FeedBackDaoImpl().createFeedBack(feedbackBean);
			if (createFeedBack) {
				success(writer, type, SUCCESS);
				reset();
				return;
			} else {
				serverError(writer, type, SERVER_INTERNAL_ERROR);
				reset();
				return;
			}
		} catch (Exception e) {
			Log.error(e);
			serverError(writer, type, SERVER_INTERNAL_ERROR);
			reset();
			return;
		}
	}

	/**
	 * 上传用户头像
	 */
	private void uploadUserIcon(int type, HttpParameters parameters, PrintWriter writer, FullHttpRequest request) {
		decoder = new HttpPostRequestDecoder(factory, request);
		try {
			String userId = parameters.getValue("user_id"); // 意见反馈用户key
			String servoId = parameters.getValue("servo_id"); // 服务器key
			if (Tool.isEmpty(userId) || Tool.isEmpty(servoId)) {
				parameterError(writer, type, "user_id,servo_id can not be null");
				reset();
				return;
			}
			getUploadFile(type, writer, request, USER_ICON_IMG);
			if (Tool.isEmpty(fileUrl)) {
				parameterError(writer, type, "user_icon can not be null");
				reset();
				return;
			}
			UserIconBean userIconBean = new UserIconBean();
			userIconBean.setUserId(userId);
			userIconBean.setServoId(servoId);
			userIconBean.setUserIcon(fileUrl);
			boolean createUser = false;
			createUser = new UserIconDaoImpl().createUserIcon(userIconBean);

			if (createUser) { /* 云端本地数据同步 */
				Message.Builder msg = Message.newBuilder();
				Builder newBuilder = SyncUserIcon.newBuilder();
				newBuilder.setIconUrl(fileUrl);
				newBuilder.setUserId(userId);
				newBuilder.build();
				msg.setSyncUserIcon(newBuilder);
				MasterGroup groups = MasterHandler.getGroups();
				boolean isSend = false;
				if (groups != null) {
					isSend = groups.Send2LocalServo(msg.build(), servoId);
				}
				if (isSend) {
					if (CODE_JSON == type) {
						JSON.begin(writer);
						JSON.field(writer, "result", "0000", true);
						JSON.field(writer, "msg", SUCCESS, false);
						JSON.field(writer, "user_id", userId);
						JSON.field(writer, "user_icon_url", fileUrl);
						JSON.end(writer);
					} else if (CODE_XML == type) {
						XML.document(writer);
						XML.element(writer, "result", "0000");
						XML.element(writer, "msg", SUCCESS);
						XML.element(writer, "uset_id", userId);
						XML.element(writer, "user_icon_url", fileUrl);
					} else {
						throw new IllegalArgumentException();
					}
					reset();
					return;
				} else {
					// 上传失败，伺服器不在线
					serverError(writer, type, "本地服务器不在线");
					reset();
				}
			} else {
				serverError(writer, type, SERVER_INTERNAL_ERROR);
				reset();
				return;
			}
		} catch (Exception e) {
			Log.error(e);
			serverError(writer, type, SERVER_INTERNAL_ERROR);
			// 删除以保存的图片
			File file = new File(fileTurePath);
			if (file.exists()) {
				file.delete();
			}
			return;
		}
	}

	/**
	 * 语音识别
	 */
	private void phoneticRecognition(int type, final PrintWriter writer, FullHttpRequest request) {
		decoder = new HttpPostRequestDecoder(factory, request);
		try {
			getUploadFile(type, writer, request, PHONETIC_FILE);
			if (Tool.isEmpty(fileTurePath)) {
				parameterError(writer, type, "phonetic file can not be null");
				reset();
				return;
			}
			RecognizeMessageQueue instance = RecognizeMessageQueue.getInstance();
			instance.add(fileTurePath, null, SPEECH_RECOGNIZE);

			final StringBuilder result = new StringBuilder();
			instance.setRecognoizeListener(new RecognizeListener() {
				@Override
				public void onRecognizeSuccess(String filepath, String re) {
					if (filepath.equals(fileTurePath)) {
						result.append(re);
						File file = new File(fileTurePath);
						if (file.exists()) {
							file.delete();
						}
					}
				}
			});
			while (true) {
				Thread.sleep(1);
				if (result.length() > 0) {
					break;
				}
			}
			writer.print(result);
			reset();
		} catch (Exception e) {
			Log.error(e);
			serverError(writer, type, SERVER_INTERNAL_ERROR);
			// 删除已保存的图片
			File file = new File(fileTurePath);
			if (file.exists()) {
				file.delete();
			}
			reset();
			return;
		}
	}

	/**
	 * 获取所有在线服务器
	 * 
	 * @param writer
	 * @param type
	 * @param channels
	 */
	private void getOnlineServo(PrintWriter writer, int type, ConcurrentMap<String, Channel> channels) {
		if (CODE_JSON == type) {
			Boolean first = true;
			JSON.begin(writer);
			JSON.arrayBegin(writer, "servos", true);
			if (channels != null) {
				Set<String> keySet = channels.keySet();
				for (String cId : keySet) {
					JSON.objectBegin(writer, first);
					{
						JSON.field(writer, "id", cId, true);
					}
					JSON.objectEnd(writer);
					if (first)
						first = false;
				}
				JSON.arrayEnd(writer);
				JSON.end(writer);
			} else {
				JSON.arrayEnd(writer);
				JSON.end(writer);
			}

		} else if (CODE_XML == type) {
			XML.document(writer);
			XML.elementBegin(writer, "get_online_servos");
			if (channels != null) {
				Set<String> keySet = channels.keySet();
				for (String cId : keySet) {
					XML.elementBegin(writer, "servo");
					{
						XML.element(writer, "id", cId);
					}
					XML.elementEnd(writer, "servo");
				}
				XML.elementEnd(writer, "get_online_servos");
			} else {
				XML.elementEnd(writer, "get_online_servos");
			}

		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 获取所有设备信息
	 * 
	 * @param type 请求接口类型：json/xml
	 * @param parameters Http 请求参数
	 * @param writer
	 * @param request
	 * @throws Exception
	 */
	private void selectDevices2(int type, HttpParameters parameters, PrintWriter writer) throws Exception {
		Log.debug("进入select_devices");
		String servoId = parameters.getValue("servo_id");
		Log.debug("获取到的servo_id = " + servoId);
		Message.Builder msg = Message.newBuilder();
		msg.setUserId("cloud-" + servoId);
		SelectDevices.Builder selectDevice = SelectDevices.newBuilder();
		msg.setSelectDevices(selectDevice.build());
		sendMessageDevice2LocalServo(type, writer, msg.build());
	}

	/**
	 * 获取所有设备信息
	 * 
	 * @param writer
	 * @param type
	 * @param channels
	 */
	@Deprecated
	private void selectDevices(int type, HttpParameters parameters, PrintWriter writer) throws Exception {
		Log.debug("进入select_devices");
		final String servoId = parameters.getValue("servo_id");
		Log.debug("获取到的servo_id = " + servoId);
		MasterGroup groups = MasterHandler.getGroups();
		if (groups.getOnlineServoKey().containsKey(servoId)) {
			// 构建protobuf协议消息
			Message.Builder msg = Message.newBuilder();
			/*
			 * 长连接服务利用 此字段判断是否为云平台返回结果，加入到相应的监听中
			 */
			msg.setUserId("cloud-" + servoId);
			SelectDevices.Builder selectDevice = SelectDevices.newBuilder();
			msg.setSelectDevices(selectDevice.build());
			Log.debug("构建protobuf协议");
			final MessageContainer reciveMsg = new MessageContainer();
			CloudResponseListener cloudResponseListener = new CloudResponseListener() {
				@Override
				public void receive(Message msg) {
					if (msg.getActionCase() == ActionCase.SELECT_DEVICES && msg.getUserId().equals(("cloud-" + servoId))) {
						Log.debug("监听回调收到监听消息");
						reciveMsg.setMsg(msg);
					}
				}
			};
			Log.debug("添加监听");
			MasterHandler.addCloudResponseListener(cloudResponseListener);
			Log.debug("发送获取服务器设备列表请求");
			groups.Send2LocalServo(msg.build(), servoId);
			long begin = System.currentTimeMillis();
			while (true) {
				Thread.sleep(1);
				if (reciveMsg.getMsg() != null) {
					Log.debug("收到回复后，移除监听");
					MasterHandler.removeCloudResponseListener(cloudResponseListener);
					break;
				}
				// 判断请求超时
				if (System.currentTimeMillis() - begin >= 5000) {
					break;
				}
			}
			if (reciveMsg.getMsg() != null) {
				// 2.处理本地服务器返回的请求
				Message message = reciveMsg.getMsg();
				String string = Arrays.toString(message.toByteArray());
				writer.append(string);
			} else {
				parameterError(writer, type, "请求超时");
			}
		} else {
			parameterError(writer, type, "伺服器不在线");
		}
	}

	/**
	 * 读取所有设备状态信息
	 * 
	 * @param type
	 * @param parameters
	 * @param writer
	 * @param request
	 * @throws Exception
	 */
	private void readAllDeviceStatus(int type, HttpParameters parameters, PrintWriter writer) throws Exception {
		Log.debug("进入select_devices");
		final String servoId = parameters.getValue("servo_id");
		Log.debug("获取到的servo_id = " + servoId);
		MasterGroup groups = MasterHandler.getGroups();
		if (groups.getOnlineServoKey().containsKey(servoId)) {
			// 构建protobuf协议消息
			Message.Builder msg = Message.newBuilder();
			/*
			 * 长连接服务利用 此字段判断是否为云平台返回结果，加入到相应的监听中
			 */
			msg.setUserId("cloud-" + servoId);
			ReadAllDeviceStatus.Builder readAllDeivceStatus = ReadAllDeviceStatus.newBuilder();
			msg.setReadAllDeviceStatus(readAllDeivceStatus.build());
			Log.debug("构建protobuf协议");
			final MessageContainer reciveMsg = new MessageContainer();
			CloudResponseListener cloudResponseListener = new CloudResponseListener() {
				@Override
				public void receive(Message msg) {
					if (msg.getActionCase() == ActionCase.READ_ALL_DEVICE_STATUS && msg.getUserId().equals(("cloud-" + servoId))) {
						Log.debug("监听回调收到监听消息");
						reciveMsg.setMsg(msg);
					}
				}
			};
			Log.debug("添加监听");
			MasterHandler.addCloudResponseListener(cloudResponseListener);
			Log.debug("发送获取服务器设备列表请求");
			groups.Send2LocalServo(msg.build(), servoId);
			long begin = System.currentTimeMillis();
			while (true) {
				Thread.sleep(1);
				if (reciveMsg.getMsg() != null) {
					Log.debug("收到回复后，移除监听");
					MasterHandler.removeCloudResponseListener(cloudResponseListener);
					break;
				}
				// 判断请求超时
				if (System.currentTimeMillis() - begin >= 5000) {
					break;
				}
			}
			if (reciveMsg.getMsg() != null) {
				// 2.处理本地服务器返回的请求
				Message message = reciveMsg.getMsg();
				String string = Arrays.toString(message.toByteArray());
				writer.append(string);
			} else {
				parameterError(writer, type, "请求超时");
			}
		} else {
			parameterError(writer, type, "伺服器不在线");
		}
	}

	/**
	 * 发送Protobuf消息给伺服器，返回Message消息
	 * 
	 * @return Message Protobuf消息对象
	 * @throws InterruptedException
	 */
	private Message sendMessageToServo(int type, PrintWriter writer, Message m) throws InterruptedException {
		String servoId = m.getUserId().substring(6);
		final Message message = m;

		System.err.println("发送" + message.toString());
		System.err.println(servoId);
		MasterGroup groups = MasterHandler.getGroups();

		if (!groups.getOnlineServoKey().containsKey(servoId)) {
			return null;
		}
		final MessageContainer reciveMsg = new MessageContainer();
		CloudResponseListener cloudResponseListener = new CloudResponseListener() {
			@Override
			public void receive(Message msg) {
				Log.debug(msg.toString());
				if (msg.getActionCase() == message.getActionCase() && msg.getUserId().equals(message.getUserId())) {
					Log.debug("监听回调收到监听消息");
					reciveMsg.setMsg(msg);
				}
			}
		};
		Log.debug("添加监听");
		MasterHandler.addCloudResponseListener(cloudResponseListener);
		Log.debug("发送获取服务器设备列表请求");
		groups.Send2LocalServo(message, servoId);
		long begin = System.currentTimeMillis();
		while (true) {
			Thread.sleep(1);
			if (reciveMsg.getMsg() != null) {
				Log.debug("收到回复后，移除监听");
				MasterHandler.removeCloudResponseListener(cloudResponseListener);
				break;
			}
			// 判断请求超时
			if (System.currentTimeMillis() - begin >= 5000) {
				break;
			}
		}
		if (reciveMsg.getMsg() != null) {
			return reciveMsg.getMsg();
		}
		// 2.处理本地服务器返回的请求
		return null;
	}

	/**
	 * 将Protobuf协议消息发送给本地服务器
	 * 
	 * @param type 请求协议了类型
	 * @param writer
	 * @param m Message
	 * @throws Exception
	 */
	private void sendMessageDevice2LocalServo(int type, PrintWriter writer, Message m) throws Exception {

		String servoId = m.getUserId().substring(6);
		final Message message = m;

		System.err.println("发送" + message.toString());
		System.err.println(servoId);
		MasterGroup groups = MasterHandler.getGroups();
		if (groups.getOnlineServoKey().containsKey(servoId)) {
			final MessageContainer reciveMsg = new MessageContainer();
			CloudResponseListener cloudResponseListener = new CloudResponseListener() {
				@Override
				public void receive(Message msg) {
					Log.debug(msg.toString());
					if (msg.getActionCase() == message.getActionCase() && msg.getUserId().equals(message.getUserId())) {
						Log.debug("监听回调收到监听消息");
						reciveMsg.setMsg(msg);
					}
				}
			};
			Log.debug("添加监听");
			MasterHandler.addCloudResponseListener(cloudResponseListener);
			Log.debug("发送获取服务器设备列表请求");
			groups.Send2LocalServo(message, servoId);
			long begin = System.currentTimeMillis();
			while (true) {
				Thread.sleep(1);
				if (reciveMsg.getMsg() != null) {
					Log.debug("收到回复后，移除监听");
					MasterHandler.removeCloudResponseListener(cloudResponseListener);
					break;
				}
				// 判断请求超时
				if (System.currentTimeMillis() - begin >= 5000) {
					break;
				}
			}
			if (reciveMsg.getMsg() != null) {
				// 2.处理本地服务器返回的请求
				Message message2 = reciveMsg.getMsg();
				String string = Arrays.toString(message2.toByteArray());
				writer.append(string);
			} else {
				parameterError(writer, type, "请求超时");
			}
		} else {
			parameterError(writer, type, "伺服器不在线");
		}
	}
}
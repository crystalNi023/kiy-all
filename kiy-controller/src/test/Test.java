package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.protobuf.ByteString;
import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.controller.F;
import com.kiy.controller.FMain;
import com.kiy.protocol.Messages;
import com.kiy.protocol.Messages.ChangePhone;
import com.kiy.protocol.Messages.CreateLinkage;
import com.kiy.protocol.Messages.CreateScene;
import com.kiy.protocol.Messages.CreateUser;
import com.kiy.protocol.Messages.CreateXMDevice;
import com.kiy.protocol.Messages.DeleteScene;
import com.kiy.protocol.Messages.DeleteXMDevice;
import com.kiy.protocol.Messages.EnsureMessage;
import com.kiy.protocol.Messages.GetCameraConfig;
import com.kiy.protocol.Messages.GetMessage;
import com.kiy.protocol.Messages.GetQuestions;
import com.kiy.protocol.Messages.GetWeatherData;
import com.kiy.protocol.Messages.LinuxCommand;
import com.kiy.protocol.Messages.Login;
import com.kiy.protocol.Messages.MainAccount;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.ModifyPassword;
import com.kiy.protocol.Messages.ModifyXMDeviceName;
import com.kiy.protocol.Messages.NoticeCloud;
import com.kiy.protocol.Messages.OfflineNoticeActive;
import com.kiy.protocol.Messages.ReadAllDeviceStatus;
import com.kiy.protocol.Messages.SelectXMDevice;
import com.kiy.protocol.Messages.SpeechRecognition;
import com.kiy.protocol.Messages.SpeechRecognition.Builder;
import com.kiy.protocol.Messages.UpdateCameraList;
import com.kiy.protocol.Messages.UpdateLinkage;
import com.kiy.protocol.Messages.UpdateLinkageStatus;
import com.kiy.protocol.Messages.UpdateScene;
import com.kiy.protocol.Messages.UpdateSceneStatus;
import com.kiy.protocol.Messages.UploadCameraConfig;
import com.kiy.protocol.Messages.UrgentMessage;
import com.kiy.protocol.Messages.UserExist;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDHCamera;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.protocol.Units.MLinkage;
import com.kiy.protocol.Units.MLinkageDevice;
import com.kiy.protocol.Units.MPush;
import com.kiy.protocol.Units.MQuestion;
import com.kiy.protocol.Units.MScene;
import com.kiy.protocol.Units.MSceneDevice;
import com.kiy.protocol.Units.MUser;
import com.kiy.protocol.Units.MXMCamera;
import com.kiy.protocol.Units.MXMDevice;

public class Test {
	private Servo servo;
	public final static String MAP_FILE = "test";

	private Shell shell;

	public Test(Servo servo1,FMain f) {
		servo = servo1;
		shell = f.getShell();
		F.center(null, shell);
	}
	public void run(){
//		getCameraConfig();
//		getDevices();
//		getSMSMessage("18996470535");
//		verifyCode("15320831347", "069343");
//		ModifyPassword("18996470535", Tool.MD5("hu110112113114"));
//		TestFile();
//		testSpeechRecognize("离家");
//		testDeviceClassify();
//		testCamera();
//		testReadAllDeviceStatus();
//		testSceneUpdate();
//		testLinkageCreate();
//		testSelectLinkages();
//		testDeleteLinkage();
//		testUpdateLinkageStatus();
//		testLinkageUpdate();
//		test();
		
//		testUpdateLinkageStatus();
//		testPushLogin();
//		testOfflineNotice();
//		testMainAccount();
//		testGetQuestions();
//		getSMSMessage("15320831347");
//		uploadCaremaConfig();
//		uploadCaremaConfig();
		
//		try {
//			testVoice();
//			Thread.sleep(3000);
//			testVoice();
//			Thread.sleep(3000);
//			testVoice();
//			Thread.sleep(20000);
//			testVoice1();
//			Thread.sleep(2000);
//			testVoice1();
//			Thread.sleep(2000);
//			testVoice1();
//			Thread.sleep(10000);
//			testVoice2();
//			Thread.sleep(1000);
//			testVoice2();
//			Thread.sleep(1000);
//			testVoice2();
//			Thread.sleep(20000);
//			testVoice3();
//			Thread.sleep(2000);
//			testVoice3();
//			Thread.sleep(2000);
//			testVoice3();
//			Thread.sleep(10000);
//			testVoice4();
//			Thread.sleep(10000);
//			testVoice5(); 
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		testUrgentMessage();
//		testTreeset();
//		testCreateUser();
//		testChangePhone();
//		testLinuxCommand();
//		testNoticeCloud2();
//		testNoticeCloud();
//		testUserExit();
//		testCreateXMDevice();
//		testSelectXMDevice();
//		testUpdateXMDevice();
//		testDeleteXMDevice();
	}
	@SuppressWarnings("unused")
	private void test(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        message.setUserId(servo.getUser().getId());
        Messages.SelectLinkages.Builder builder = Messages.SelectLinkages.newBuilder();
        Date date = new Date();
        date.setTime(servo.getTickLong(Servo.LINKAGE));
        System.err.println(date.toString()+"test");
        Date date2 = new Date();
        date2.setTime(servo.getTickLong(Servo.SCENE));
        System.err.println(date2.toString()+"test");
        builder.setTick(servo.getTickLong(Servo.LINKAGE));
        message.setSelectLinkages(builder);
        if (servo.getClient() != null && servo.getClient().isConnected() &&
        		servo.getClient().isRunning()) {
        	servo.getClient().send(message.build());
        	System.out.println("发送...");
        }
	}
	
	/**
	 * 测试联动
	 */
	@SuppressWarnings("unused")
	private void testLinkageUpdate(){
		Linkage linkage = new Linkage();
		linkage.setId("d8394d5d-a8d2-490d-bf54-3be3cb35ca17");
		linkage.setName("测试联动  修改");
		linkage.setEnable(true);
		linkage.setRemark("备注  修改");
		LinkageDevice linkageDevice = new LinkageDevice();
		linkageDevice.setId(UUID.randomUUID().toString());
		linkageDevice.setLinkageId(linkage.getId());
		linkageDevice.setDeviceId("c1f71fac-22e9-4fdf-a258-4c04b5f35a88");
		linkageDevice.setPriority(1);
		linkageDevice.setFeatureIndex(0);
		linkageDevice.setFeatureValue(1);
		linkage.addLinkageDevice(linkageDevice);
		Message.Builder msg = Message.newBuilder();
		UpdateLinkage.Builder ul = UpdateLinkage.newBuilder();
		MLinkage.Builder mLinkage = MLinkage.newBuilder();
		mLinkage.setId(linkage.getId());
		mLinkage.setName(linkage.getName());
		mLinkage.setEnable(linkage.isEnable());
		mLinkage.setRemark(linkage.getRemark());
		
		for (LinkageDevice linkageDevice2 : linkage.getLinkageDevice()) {
			MLinkageDevice.Builder mlinkageDevice = MLinkageDevice.newBuilder();
			mlinkageDevice.setId(linkageDevice2.getId());
			mlinkageDevice.setLinkageId(linkageDevice2.getLinkageId());
			mlinkageDevice.setDeviceId(linkageDevice2.getDeviceId());
			mlinkageDevice.setPriority(linkageDevice2.getPriority());
			mlinkageDevice.setFeatureIndex(linkageDevice2.getFeatureIndex());
			mlinkageDevice.setFeatureValue(linkageDevice2.getFeatureValue());
			mLinkage.addLinkageDevice(mlinkageDevice);
		}
		ul.setItem(mLinkage);
		msg.setUpdateLinkage(ul.build());
		servo.getClient().send(msg.build());
	}
	/**
	 * 测试联动状态更新
	 */
	@SuppressWarnings("unused")
	private void testUpdateLinkageStatus(){
		Message.Builder msg = Message.newBuilder();
		UpdateLinkageStatus.Builder updateLinkagestatus = UpdateLinkageStatus.newBuilder();
		updateLinkagestatus.setId("7db4fe46-de78-44fe-860f-80aa88c501a4");
		updateLinkagestatus.setSwitchStatus(false);
		msg.setUpdateLinkageStatus(updateLinkagestatus.build());
		CtrClient client = servo.getClient();
		client.send(msg.build());
		System.err.println(msg.toString());
	}

	@SuppressWarnings("unused")
	private void testReadAllDeviceStatus(){
		Message.Builder msg = Message.newBuilder();
		msg.setReadAllDeviceStatus(ReadAllDeviceStatus.newBuilder().build());
		servo.getClient().send(msg.build());
	}
	
	@SuppressWarnings("unused")
	private void testCamera(){
		Message.Builder msg = Message.newBuilder();
		UpdateCameraList.Builder updateCameraListBuilder = msg.getUpdateCameraListBuilder();
		MDHCamera.Builder newBuilder = MDHCamera.newBuilder();
		newBuilder.setCameraId("4E0828CPAG01C1D");
		newBuilder.setChannelId(0);
		updateCameraListBuilder.addItems(newBuilder.build());
		
//		MDHCamera.Builder newBuilder1 = MDHCamera.newBuilder();
//		newBuilder1.setCameraId("4E0828CPAG01C2E");
//		newBuilder1.setChannelId(1);
//		updateCameraListBuilder.addItems(newBuilder1.build());
//		
//		MDHCamera.Builder newBuilder3 = MDHCamera.newBuilder();
//		newBuilder3.setCameraId("1232138CPAG01C2E");
//		newBuilder3.setChannelId(1);
//		updateCameraListBuilder.addItems(newBuilder3.build());
		
		msg.setUserId(servo.getUser().getId());
		msg.setUpdateCameraList(updateCameraListBuilder.build());
		
		servo.getClient().send(msg.build());
	}
	
	@SuppressWarnings("unused")
	private void testDeviceClassify(){
		System.err.println("所有安防设备:");
		printDeviceSet(servo.getSafeProtectionDevices());
		System.err.println("安防气体监测点:");
		printDeviceSet(servo.getSafeProtectionGasDevices());
		System.err.println("安防烟雾监测点:");
		printDeviceSet(servo.getSafeProtectionSmokeDevices());
		System.err.println("安防水浸监测点:");
		printDeviceSet(servo.getSafeProtectionSensorWaterDevices());
		System.err.println("安防入户门监测点:");
		printDeviceSet(servo.getSafeProtectionDoorDevices());
		System.err.println("安防通断监测点:");
		printDeviceSet(servo.getSafeProtectionSwitchDevices());
		System.err.println("安防入户监测点:");
		printDeviceSet(servo.getSafeProtectionHouseHoldsDevices());
		System.err.println("照明设备:");
		printDeviceSet(servo.getIlluminationDevices());
		System.err.println("空调地暖:");
		printDeviceSet(servo.getAironditioningOrHeatingFloorDevices());
		System.err.println("通风系统:");
		printDeviceSet(servo.getVentilatingSystemDevices());
		System.err.println("环境系统:");
		printDeviceSet(servo.getEnvironmentSystemDevices());
		System.err.println("鱼缸:");
		printDeviceSet(servo.getFishTankDevices());
		System.err.println("灌溉系统:");
		printDeviceSet(servo.getIrrigationSystemDevices());
	}

	private void printDeviceSet(Set<Device> devices){
		for (Device device : devices) {
			System.err.println(device.getName());
		}
	}

	@SuppressWarnings("unused")
	private void testSpeechRecognize(String string){
		Message.Builder msg = Message.newBuilder();
		Builder newBuilder = SpeechRecognition.newBuilder();
		newBuilder.setSpeech(string);
		msg.setSpeechRecognition(newBuilder.build());
		servo.getClient().send(msg.build());
	}

	@SuppressWarnings("unused")
	private void ModifyPassword(String phone,String password){
		Message.Builder msg = Message.newBuilder();
		ModifyPassword.Builder newBuilder = ModifyPassword.newBuilder();
		newBuilder.setPhone(phone);
		newBuilder.setPassword(password);
		msg.setModifyPassword(newBuilder.build()).build();
		servo.getClient().send(msg.build());
	}

	
	@SuppressWarnings("unused")
	private void verifyCode(String phone,String code){
		Message.Builder msg = Message.newBuilder();
		EnsureMessage.Builder newBuilder = EnsureMessage.newBuilder();
		newBuilder.setPhone(phone);
		newBuilder.setPassCode(code);
		msg.setEnsureMessage(newBuilder.build()).build();
		servo.getClient().send(msg.build());
	}

	@SuppressWarnings("unused")
	private void getSMSMessage(String phone){
		Message.Builder msg = Message.newBuilder();
		GetMessage.Builder newBuilder = GetMessage.newBuilder();
		newBuilder.setPhone(phone);
		msg.setGetMessage(newBuilder.build()).build();
		servo.getClient().send(msg.build());
	}


	@SuppressWarnings("unused")
	private void getWeather(){
		Message.Builder msg = Message.newBuilder();
		msg.setUserId("20a1f504-4373-11e8-8e78-480fcf64d954");
		msg.setKey(1);
		GetWeatherData.Builder newBuilder = GetWeatherData.newBuilder();
		msg.setGetWeatherData(newBuilder.build()).build();
		servo.getClient().send(msg.build());

	}

	@SuppressWarnings("unused")
	private void updateSceneStatus(){
		Message.Builder msg = Message.newBuilder();
		msg.setUserId("20a1f504-4373-11e8-8e78-480fcf64d954");
		msg.setKey(1);
		UpdateSceneStatus.Builder updateSceneStatus = UpdateSceneStatus.newBuilder();
		updateSceneStatus.setId("e2005fdb-6b42-42f0-a51d-eafb833ebe54");
		updateSceneStatus.setSwitchStatus(false);
		msg.setUpdateSceneStatus(updateSceneStatus.build());

		CtrClient client = servo.getClient();
		client.send(msg.build());

		System.err.println(msg.toString());

	}


	/**
	 * 删除场景
	 */
	@SuppressWarnings("unused")
	private void testDeleteScene(){
		Message.Builder msg = Message.newBuilder();
		msg.setUserId("20a1f504-4373-11e8-8e78-480fcf64d954");
		msg.setKey(1);
		DeleteScene.Builder deleteScene = DeleteScene.newBuilder();
		deleteScene.setId("b1ce7f77-3cbf-4f3c-960e-bad65997efbd");
		msg.setDeleteScene(deleteScene.build());

		CtrClient client = servo.getClient();
		client.send(msg.build());
	}


	/**
	 * 测试更新场景
	 */
	@SuppressWarnings("unused")
	private void testSceneUpdate(){
		Scene scene = new Scene();
		scene.setId("e6f160ad-132e-4672-b4d4-d050086fcf73");
		scene.setName("测试场景 修改后");
		scene.setSwitchStatu(false);
		scene.setUpdated(new Date());
		SceneDevice sceneDevice11 = new SceneDevice();
		sceneDevice11.setDeviceId("83cc7e02-df69-4b9f-bfa5-2110dc6f3942");
		sceneDevice11.setFeatureIndex(0);
		sceneDevice11.setFeatureValue(0);
		sceneDevice11.setSwitchStatus(true);
		scene.addOpenSceneDevice(sceneDevice11);
		SceneDevice sceneDevice111 = new SceneDevice();
		sceneDevice111.setDeviceId("83cc7e02-df69-4b9f-bfa5-2110dc6f3942");
		sceneDevice111.setFeatureIndex(0);
		sceneDevice111.setFeatureValue(0);
		sceneDevice111.setSwitchStatus(false);
		scene.addOffSceneDevice(sceneDevice111);
		SceneDevice sceneDevice1111 = new SceneDevice();
		sceneDevice1111.setDeviceId("83cc7e02-df69-4b9f-bfa5-2110dc6f3942");
		sceneDevice1111.setFeatureIndex(0);
		sceneDevice1111.setFeatureValue(0);
		sceneDevice1111.setSwitchStatus(true);
		scene.addOpenSceneDevice(sceneDevice1111);
		SceneDevice sceneDevice11111 = new SceneDevice();
		sceneDevice11111.setDeviceId("83cc7e02-df69-4b9f-bfa5-2110dc6f3942");
		sceneDevice11111.setFeatureIndex(3);
		sceneDevice11111.setFeatureValue(5);
		sceneDevice11111.setSwitchStatus(false);
		scene.addOffSceneDevice(sceneDevice11111);
		scene.setRemark("备注 修改");

		Message.Builder msg = Message.newBuilder();
		msg.setUserId("20a1f504-4373-11e8-8e78-480fcf64d954");
		msg.setKey(1);
		UpdateScene.Builder updateBuilder = UpdateScene.newBuilder();
		MScene.Builder mScene = MScene.newBuilder();
		mScene.setId(scene.getId());
		mScene.setName(scene.getName());
		mScene.setSwitch(scene.getSwitchStatu());
		mScene.setRemark(scene.getRemark());
		mScene.setUpdated(scene.getUpdated().getTime());
		List<SceneDevice> offDevices = scene.getOffDevices();
		for (SceneDevice sceneDevice2 : offDevices) {
			MSceneDevice.Builder mSBuilder = MSceneDevice.newBuilder();
			mSBuilder.setDeviceId(sceneDevice2.getDeviceId());
			mSBuilder.setSwitch(sceneDevice2.getSwitchStatus());
			mSBuilder.setFeatureIndex(sceneDevice2.getFeatureIndex());
			mSBuilder.setFeatureValue(sceneDevice2.getFeatureValue());
			mScene.addOffDevices(mSBuilder.build());
		}
		List<SceneDevice> openDevice = scene.getOpenDevices();
		for (SceneDevice sceneDevice2 : openDevice) {
			MSceneDevice.Builder mSBuilder = MSceneDevice.newBuilder();
			mSBuilder.setDeviceId(sceneDevice2.getDeviceId());
			mSBuilder.setSwitch(sceneDevice2.getSwitchStatus());
			mSBuilder.setFeatureIndex(sceneDevice2.getFeatureIndex());
			mSBuilder.setFeatureValue(sceneDevice2.getFeatureValue());
			mScene.addOpenDevices(mSBuilder.build());
		}

		updateBuilder.setItem(mScene);
		msg.setUpdateScene(updateBuilder.build());

		System.err.println(msg.toString());

		CtrClient client = servo.getClient();
		client.send(msg.build());
	}


	/**
	 * 测试创建场景
	 */
	@SuppressWarnings("unused")
	private void TestSceneCreate(){
		/**
		 * Test Scene MessageCreated
		 */
		Scene scene = new Scene();
		scene.newId();
		scene.setName("测试场景 勿改 勿删");
		scene.setSwitchStatu(false);
		scene.setCreated(new Date());
		SceneDevice sceneDevice = new SceneDevice();
		sceneDevice.setDeviceId("13b1a43d-ed41-4cdd-b0b8-9949fa4127cb");  //水晶球灯
		sceneDevice.setFeatureIndex(2);
		sceneDevice.setFeatureValue(1);
		sceneDevice.setSwitchStatus(true);
		scene.addOpenSceneDevice(sceneDevice);
		SceneDevice sceneDevice1 = new SceneDevice();
		sceneDevice1.setDeviceId("13b1a43d-ed41-4cdd-b0b8-9949fa4127cb");
		sceneDevice1.setFeatureIndex(2);
		sceneDevice1.setFeatureValue(0);
		sceneDevice1.setSwitchStatus(false);
		scene.addOffSceneDevice(sceneDevice1);
		SceneDevice sceneDevice11 = new SceneDevice();
		sceneDevice11.setDeviceId("f6bbb522-45c8-4f20-9d74-c511542add00");
		sceneDevice11.setFeatureIndex(2);
		sceneDevice11.setFeatureValue(1);
		sceneDevice11.setSwitchStatus(true);
		scene.addOpenSceneDevice(sceneDevice11);
		SceneDevice sceneDevice111 = new SceneDevice();
		sceneDevice111.setDeviceId("f6bbb522-45c8-4f20-9d74-c511542add00");
		sceneDevice111.setFeatureIndex(2);
		sceneDevice111.setFeatureValue(0);
		sceneDevice111.setSwitchStatus(false);
		scene.addOffSceneDevice(sceneDevice111);
		scene.setRemark("测试 场景 备注");



		Message.Builder msg = Message.newBuilder();
		msg.setUserId("d6231455-ac66-4e5b-a164-7e36c1e53afb");
		msg.setKey(1);
		CreateScene.Builder createScene = CreateScene.newBuilder();
		MScene.Builder mScene = createScene.getItemBuilder();
		mScene.setId(scene.getId());
		mScene.setName(scene.getName());
		mScene.setRemark(scene.getRemark());
		mScene.setSwitch(scene.getSwitchStatu());
		mScene.setCreated(scene.getCreated().getTime());
		List<SceneDevice> offDevices = scene.getOffDevices();
		for (SceneDevice sceneDevice2 : offDevices) {
			MSceneDevice.Builder mSBuilder = MSceneDevice.newBuilder();
			mSBuilder.setDeviceId(sceneDevice2.getDeviceId());
			mSBuilder.setSwitch(sceneDevice2.getSwitchStatus());
			mSBuilder.setFeatureIndex(sceneDevice2.getFeatureIndex());
			mSBuilder.setFeatureValue(sceneDevice2.getFeatureValue());
			mScene.addOffDevices(mSBuilder.build());
		}
		List<SceneDevice> openDevice = scene.getOpenDevices();
		for (SceneDevice sceneDevice2 : openDevice) {
			MSceneDevice.Builder mSBuilder = MSceneDevice.newBuilder();
			mSBuilder.setDeviceId(sceneDevice2.getDeviceId());
			mSBuilder.setSwitch(sceneDevice2.getSwitchStatus());
			mSBuilder.setFeatureIndex(sceneDevice2.getFeatureIndex());
			mSBuilder.setFeatureValue(sceneDevice2.getFeatureValue());
			mScene.addOpenDevices(mSBuilder.build());
		}
		createScene.setItem(mScene);
		msg.setCreateScene(createScene).build();

		System.err.println(msg.toString());

		CtrClient client = servo.getClient();
		client.send(msg.build());
	}

	/**
	 * 测试新增联动
	 */
	@SuppressWarnings("unused")
	private void testLinkageCreate(){
		Linkage linkage = new Linkage();
		linkage.newId();
		linkage.setName("测试联动");
		linkage.setEnable(true);
		linkage.setRemark("备注");
		
		LinkageDevice linkageDevice = new LinkageDevice();
		linkageDevice.setId(UUID.randomUUID().toString());
		linkageDevice.setLinkageId(linkage.getId());
		linkageDevice.setDeviceId("31a9e7bf-f6e3-4b46-bdd2-92d7a6a2b95f");//电灯1
		linkageDevice.setPriority(1);
		linkageDevice.setFeatureIndex(0);
		linkageDevice.setFeatureValue(1);//开
		
		LinkageDevice linkageDevice1 = new LinkageDevice();
		linkageDevice1.setId(UUID.randomUUID().toString());
		linkageDevice1.setLinkageId(linkage.getId());
		linkageDevice1.setDeviceId("51cb01cb-e56d-48f4-a507-e37150f32358");//电灯4
		linkageDevice1.setFeatureIndex(0);
		linkageDevice1.setFeatureValue(1);//开
		
		linkage.addLinkageDevice(linkageDevice);
		linkage.addLinkageDevice(linkageDevice1);
		
		Message.Builder msg = Message.newBuilder();
		msg.setUserId("f85a7b7e-a6b3-11e8-9ed9-480fcf64d954");
		msg.setKey(1);
		CreateLinkage.Builder createLinkage = CreateLinkage.newBuilder();
		MLinkage.Builder mLinkage = createLinkage.getItemBuilder();
		mLinkage.setId(linkage.getId());
		mLinkage.setName(linkage.getName());
		mLinkage.setEnable(linkage.isEnable());
		mLinkage.setRemark(linkage.getRemark());
		List<LinkageDevice> linkageDevices = linkage.getLinkageDevice();
		for (LinkageDevice linkageDevice2 : linkageDevices) {
			MLinkageDevice.Builder mLinkageDevice = MLinkageDevice.newBuilder();
			mLinkageDevice.setId(linkageDevice2.getId());
			mLinkageDevice.setLinkageId(linkageDevice2.getLinkageId());
			mLinkageDevice.setDeviceId(linkageDevice2.getDeviceId());
			mLinkageDevice.setPriority(linkageDevice2.getPriority());
			mLinkageDevice.setFeatureIndex(linkageDevice2.getFeatureIndex());
			mLinkageDevice.setFeatureValue(linkageDevice2.getFeatureValue());
			mLinkage.addLinkageDevice(mLinkageDevice.build());
		}
		createLinkage.setItem(mLinkage);
		msg.setCreateLinkage(createLinkage).build();
		System.err.println(msg.toString());
		
		CtrClient client = servo.getClient();
		client.send(msg.build());
		
	}
	
	@SuppressWarnings("unused")
	private void TestFile(){
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setFilterNames(new String[] { "All Files(*.*)" });
		String file = fd.open();

		File  fsdFile = new File(file);
		long length = fsdFile.length();
		System.out.println(length);
		String name = fsdFile.getName();
		System.out.println(name);

		FileInputStream fileInputStream;
		ByteString byteString = null;
		try {
			fileInputStream = new FileInputStream(file);
			ByteString.Output out = ByteString.newOutput();
			int value = 0;
			while ((value = fileInputStream.read()) >= 0) {
				out.write(value);
			}
			byteString = out.toByteString();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

		CtrClient client = servo.getClient();

	}
	
	@SuppressWarnings("unused")
	private void getDevices(){
		servo.getSecurityDevices();
	}
	
	@SuppressWarnings("unused")
	private void getCameraConfig(){
		Message.Builder message = Message.newBuilder();
		message.setKey(CtrClient.getKey());
        message.setUserId(servo.getUser().getId());
        GetCameraConfig.Builder builder =GetCameraConfig.newBuilder();
        message.setGetCameraConfig(builder);
        servo.getClient().send(message.build());

	}
	
	@SuppressWarnings("unused")
	private void testVoice(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
        MDeviceStatus.Builder builder = write.getItemBuilder();
        builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
        builder.putItems(0, 1);
        builder.putItems(1, 36);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	@SuppressWarnings("unused")
	private void testVoice1(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
        MDeviceStatus.Builder builder = write.getItemBuilder();
        builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
        builder.putItems(0, 1);
        builder.putItems(1, 39);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	@SuppressWarnings("unused")
	private void testVoice2(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
        MDeviceStatus.Builder builder = write.getItemBuilder();
        builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
        builder.putItems(0, 1);
        builder.putItems(1, 41);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	@SuppressWarnings("unused")
	private void testVoice3(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
        MDeviceStatus.Builder builder = write.getItemBuilder();
        builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
        builder.putItems(0, 1);
        builder.putItems(1, 40);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	@SuppressWarnings("unused")
	private void testVoice4(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
        WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
        MDeviceStatus.Builder builder = write.getItemBuilder();
        builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
        builder.putItems(0, 1);
        builder.putItems(1, 39);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	
	@SuppressWarnings("unused")
	private void testVoice5(){
		Messages.Message.Builder message = Messages.Message.newBuilder();
		WriteDeviceStatus.Builder write = WriteDeviceStatus.newBuilder();
		MDeviceStatus.Builder builder = write.getItemBuilder();
		builder.setDeviceId("6b6d73de-b01e-4d90-90ad-e474b2dfb23e");
		builder.putItems(0, 1);
		builder.putItems(1, 41);
		message.setWriteDeviceStatus(write.build());
		servo.getClient().send(message.build());
	}
	
	@SuppressWarnings("unused")
	private void testUrgentMessage(){
		Message.Builder msg = Message.newBuilder();
		UrgentMessage.Builder nb = UrgentMessage.newBuilder();
		System.err.println(servo.getUser().getName());
		nb.setName(servo.getUser().getName());
		msg.setUrgentMessage(nb.build());
		servo.getClient().send(msg.build());
	}
	
	@SuppressWarnings("unused")
	private void testTreeset(){
		Set<Device> devices = servo.getDevices();
		TreeSet<Device> treeSet = new TreeSet<>(new DeviceComparator());
		for (Device device : devices) {
			treeSet.add(device);
		}
		for (Device device : treeSet) {
			System.err.println(device.getName());
		}  
	}
	

@SuppressWarnings("unused")
private void testPushLogin(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	Login.Builder login = Login.newBuilder();
	login.setUsername("13896074124");
	login.setPassword("900150983cd24fb0d6963f7d28e17f");
	MPush.Builder newBuilder = MPush.newBuilder();
	newBuilder.setDeviceTokens("token");
	newBuilder.setDeviceType(0);//IOS
	newBuilder.setDeviceId("dsfohjwrejtfbnjx");
	login.setPushItem(newBuilder.build());
	message.setLogin(login.build());
	servo.getClient().send(message.build());
	}

@SuppressWarnings("unused")
private void uploadCaremaConfig(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	UploadCameraConfig.Builder camera = UploadCameraConfig.newBuilder();
	MXMCamera.Builder mxmCamera = MXMCamera.newBuilder();
	mxmCamera.setName("我是一个名字");
	mxmCamera.setPassword("athena_023");
	mxmCamera.setDeviceType(2);
	mxmCamera.setRemark("第一个摄像信息");
	camera.setItem(mxmCamera);
	message.setUploadCameraConfig(camera.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testMainAccount(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	MainAccount.Builder newBuilder = MainAccount.newBuilder();
//	newBuilder.setExist(false);
	message.setMainAccount(newBuilder.build());
	servo.getClient().send(message.build());
}

@SuppressWarnings("unused")
private void testGetQuestions(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	GetQuestions.Builder newBuilder = GetQuestions.newBuilder();
	message.setGetQuestions(newBuilder.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testChangePhone(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	ChangePhone.Builder newBuilder = ChangePhone.newBuilder();
	newBuilder.setPhoneOld("15320831347");
	newBuilder.setPhoneNew("15320831348");
	message.setChangePhone(newBuilder);
	servo.getClient().send(message.build());
}

/**
 * 测试新增用户（包含密保问题，主账户）
 */
@SuppressWarnings("unused")
private void testCreateUser(){
	Messages.Message.Builder msg = Messages.Message.newBuilder();
	CreateUser.Builder c = CreateUser.newBuilder();
	MUser.Builder user = MUser.newBuilder();
	user.setId(UUID.randomUUID().toString());
	user.setName("15320831347");
	user.setPassword(Tool.MD5("123"));
	user.setEnable(true);
	user.setType(2);
	c.setItem(user);
	MQuestion.Builder question = MQuestion.newBuilder();
	question.setId(UUID.randomUUID().toString());
	question.setQuestionNumber(1);
	question.setQuestionAnswer("nidada");
	question.setRemark("第一个问题");
	c.addQuestions(question);
	msg.setCreateUser(c.build());
	CtrClient client = servo.getClient();
	client.send(msg.build());
}

@SuppressWarnings("unused")
private void testLinuxCommand(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	LinuxCommand.Builder newBuilder = LinuxCommand.newBuilder();
//	String command="nohup /home/athena/frp/frpc -c /home/athena/frp/frpc.ini";//重启frp客户端命令
	newBuilder.setCommand("reboot -f");
//	newBuilder.setCommand(command);
//	System.err.println("执行重启frp客户端命令~~~");
	message.setLinuxCommand(newBuilder.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testCreateXMDevice(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	message.setUserId(servo.getUser().getId());
	CreateXMDevice.Builder newBuilder = CreateXMDevice.newBuilder();
	MXMDevice.Builder xmDevice = MXMDevice.newBuilder();
	xmDevice.setUserId("39c5b84c-ba03-46e0-a62c-f6c14fd30149");
	xmDevice.setDeviceMac("bjklhx26862");
	xmDevice.setDeviceName("测试摄像头");
	xmDevice.setLoginName("test");
	xmDevice.setLoginPsw("abc456");
	xmDevice.setDeviceIp("192.168.0.199");
	xmDevice.setState(1);
	xmDevice.setNPort(2);
	xmDevice.setNType(3);
	xmDevice.setNId(4444);
	xmDevice.setRemark("新增摄像头");
	newBuilder.setItem(xmDevice.build());
	message.setCreateXmDevice((newBuilder.build()));
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testSelectXMDevice(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	message.setUserId(servo.getUser().getId());
	SelectXMDevice.Builder newBuilder = SelectXMDevice.newBuilder();
	message.setSelectXmDevice((newBuilder.build()));
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testUpdateXMDevice(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	message.setUserId(servo.getUser().getId());
	ModifyXMDeviceName.Builder newBuilder = ModifyXMDeviceName.newBuilder();
	newBuilder.setDeviceMac("cioserf458rferrd");
	newBuilder.setDeviceName("修改摄像头名");
	message.setModifyXmDeviceName(newBuilder.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testDeleteXMDevice(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	message.setUserId(servo.getUser().getId());
	DeleteXMDevice.Builder newBuilder = DeleteXMDevice.newBuilder();
	newBuilder.setDeviceMac("");
	message.setDeleteXmDevice(newBuilder.build());
	servo.getClient().send(message.build());
}

@SuppressWarnings("unused")
private void testNoticeCloud(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	NoticeCloud.Builder newBuilder = NoticeCloud.newBuilder();
	newBuilder.setNoticeContent("12345678");
	System.err.println("执行NOTICE_CLOUD命令~~~");
	message.setNoticeCloud(newBuilder.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testNoticeCloud2(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	NoticeCloud.Builder newBuilder = NoticeCloud.newBuilder();
	System.err.println("执行NOTICE_CLOUD命令~~~");
	message.setNoticeCloud(newBuilder.build());
	servo.getClient().send(message.build());
}

@SuppressWarnings("unused")
private void testUserExit(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	UserExist.Builder newBuilder = UserExist.newBuilder();
	newBuilder.setUserName("18324114850");
	message.setUserExist(newBuilder.build());
	servo.getClient().send(message.build());
}
@SuppressWarnings("unused")
private void testOfflineNotice(){
	Messages.Message.Builder message = Messages.Message.newBuilder();
	OfflineNoticeActive.Builder newBuilder = OfflineNoticeActive.newBuilder();
	newBuilder.setDeviceId("030B4D81-27D4-4B17-933F-61E7AE3B38B2");
	message.setOfflineNoticeActive(newBuilder.build());
	servo.getClient().send(message.build());
}

class DeviceComparator implements Comparator<Device>{  
	@Override
	public int compare(Device o1, Device o2) {
		return o1.getName().compareTo(o2.getName());
	}      
}
}





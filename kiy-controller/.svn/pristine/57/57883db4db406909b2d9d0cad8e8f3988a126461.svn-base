package com.kiy.controller;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;
import com.kiy.common.Unit;
import com.kiy.protocol.Messages.GetServoConfig;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.SetServoConfig;
import com.kiy.resources.Lang;

/**
 * 
 * @author Crystal Ni (TEL:15320831347)
 *
 * @date 2018年7月4日
 */
public class FServoConfig extends Dialog implements FormMessage {

	private boolean closing;
	private Shell shell;
	private Text textName;
	private Text textMasterIP;
	private Text textCloudHost;
	private Text textCloudId;
	private Text textWeatherCity;
	private Text textAqiCity;
	private Text textPhone;
	private Text textCameraAppId;
	private Text textCameraAppSecret;
	private Text textCameraHost;
	private Text textMqttHost;
	private Text textMqttUsername;
	private Text textMqttPassword;
	private Text textMqttTopic;
	private Combo comboDbType;
	private Text textDbUrl;
	private Text textDbUser;
	private Text textDbPassword;
	private Spinner spinnerThread;
	private Spinner spinnerDriverHeartbeat;
	private Spinner spinnerDriverRestart;
	private Spinner spinnerDriverInstructions;
	private Spinner spinnerDriverTimeout;
	private Spinner spinnerDriverDelay;
	private Spinner spinnerMasterPort;
	private Spinner spinnerCloudPort;
	private Spinner spinnerCloudReset;
	private Spinner spinnerDbMax;
	private Spinner spinnerCruiseDelay;
	private Button btnCheckButtonControl;
	private Button btnCheckButtonCruise;
	private Button btnCheckButtonPlan;
	private Button btnCheckButtonMqtt;
	private Button btnCheckButtonDb;
	private Button btnCheckButtonCloud;
	private Button btnCheckButtonSms;
	private Button btnCheckButtonDevice;
	private Button checkButtonDebug;
	private Servo servo;

	public FServoConfig(Shell parent) {
		super(parent, 0);
		closing = false;
	}

	public void open(Servo s) {
		servo = s;
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.addShellListener(shellAdapter);
		shell.setSize(680, 519);
		shell.setText(Lang.getString("FServoConf.ShellTitle.text"));
		F.center(getParent(), shell);

		// 名称
		Label lblNewLabel = new Label(shell, SWT.CENTER);
		lblNewLabel.setBounds(16, 19, 80, 23);
		lblNewLabel.setAlignment(SWT.LEFT);
		lblNewLabel.setText(Lang.getString("FServoConf.LabelServoName.text"));

		textName = new Text(shell, SWT.BORDER);
		textName.setBounds(104, 16, 292, 23);

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(0, 47, 674, 393);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);

		// 线程数
		Label labelThread = new Label(composite, SWT.NONE);
		labelThread.setBounds(16, 6, 80, 23);
		labelThread.setText(Lang.getString("FServoConf.LabelNumberOfThreads.text"));

		spinnerThread = new Spinner(composite, SWT.BORDER);
		spinnerThread.setBounds(104, 6, 80, 23);

		// 调试日志
		checkButtonDebug = new Button(composite, SWT.CHECK);
		checkButtonDebug.setBounds(103, 42, 180, 23);
		checkButtonDebug.setText(Lang.getString("FServoConfig.btnCheckButton_debug.text"));

		int baseY;
		if (FMain.THIS_IS_MAC) {
			baseY = 16;
		} else {
			baseY = 26;
		}

		{ // 设备
			Group groupDevice = new Group(composite, SWT.NONE);
			groupDevice.setText(Lang.getString("FServoConfig.group.text"));
			groupDevice.setBounds(16, 66, 630, 219);

			btnCheckButtonDevice = new Button(groupDevice, SWT.CHECK);
			btnCheckButtonDevice.setBounds(124, baseY, 180, 23);
			btnCheckButtonDevice.setText(Lang.getString("FServoConfig.btnCheckButton.text"));
			btnCheckButtonDevice.addSelectionListener(deviceSelectionAdapter);

			// 中断重启间隔
			CLabel labelInterrupt = new CLabel(groupDevice, SWT.NONE);
			labelInterrupt.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 100, 23);
			labelInterrupt.setText(Lang.getString("FServoConf.LabelDeviceInterruptRestartInterval.text"));

			spinnerDriverRestart = new Spinner(groupDevice, SWT.BORDER);
			spinnerDriverRestart.setBounds(124, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			// 心跳间隔
			CLabel labelHeartbeat = new CLabel(groupDevice, SWT.NONE);
			labelHeartbeat.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 100, 23);
			labelHeartbeat.setText(Lang.getString("FServoConf.LabelEquipmentHeartbeatInterval.text"));

			spinnerDriverHeartbeat = new Spinner(groupDevice, SWT.BORDER);
			spinnerDriverHeartbeat.setBounds(124, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			// 指令间隔(秒)
			CLabel labelInstructions = new CLabel(groupDevice, SWT.NONE);
			labelInstructions.setBounds(16, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 100, 23);
			labelInstructions.setText(Lang.getString("FServoConf.labelInstructions.text"));

			spinnerDriverInstructions = new Spinner(groupDevice, SWT.BORDER);
			spinnerDriverInstructions.setBounds(124, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			// 通信超时(秒)
			CLabel labelTimeout = new CLabel(groupDevice, SWT.NONE);
			labelTimeout.setBounds(16, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 100, 23);
			labelTimeout.setText(Lang.getString("FServoConf.labelTimeout.text"));

			spinnerDriverTimeout = new Spinner(groupDevice, SWT.BORDER);
			spinnerDriverTimeout.setBounds(124, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			
			// 设备通信系数(BAUD_RATE/DELAY)
			CLabel labelDelay = new CLabel(groupDevice, SWT.NONE);
			labelDelay.setBounds(16, baseY + 5 * (F.MARGIN + F.LABEL_HEIGHT), 100, 23);
			labelDelay.setText(Lang.getString("FServoConf.labelTimeout.text"));
			
			spinnerDriverDelay = new Spinner(groupDevice, SWT.BORDER);
			spinnerDriverDelay.setBounds(124, baseY + 5 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			spinnerDriverDelay.setMaximum(1000);
		}

		{ // 主控服务
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_1.text"));
			group.setBounds(16, 264, 630, 129);

			btnCheckButtonControl = new Button(group, SWT.CHECK);
			btnCheckButtonControl.setBounds(104, baseY, 180, 23);
			btnCheckButtonControl.setText(Lang.getString("FServoConfig.btnCheckButton2.text"));
			btnCheckButtonControl.addSelectionListener(controlSelectionAdapter);

			// 地址
			CLabel labelAdd = new CLabel(group, SWT.NONE);
			labelAdd.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelAdd.setText(Lang.getString("FServoConf.LabelMasterServiceAddress.text"));

			textMasterIP = new Text(group, SWT.BORDER);
			textMasterIP.setBounds(104, baseY + (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 端口
			CLabel labelPort = new CLabel(group, SWT.NONE);
			labelPort.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelPort.setText(Lang.getString("FServoConf.LabelMasterServicePort.text"));

			spinnerMasterPort = new Spinner(group, SWT.BORDER);
			spinnerMasterPort.setBounds(104, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			spinnerMasterPort.setMaximum(10000);
		}

		{ // 设备巡游
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_2.text"));
			group.setBounds(16, 404, 630, 99);

			btnCheckButtonCruise = new Button(group, SWT.CHECK);
			btnCheckButtonCruise.setBounds(104, baseY, 180, 23);
			btnCheckButtonCruise.setText(Lang.getString("FServoConfig.btnCheckButton3.text"));
			btnCheckButtonCruise.addSelectionListener(cruiseSelectionAdapter);

			// 时间间隔
			CLabel labelCruiseDelay = new CLabel(group, SWT.NONE);
			labelCruiseDelay.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelCruiseDelay.setText(Lang.getString("FServoConf.LabelCruiseDelay.text"));

			spinnerCruiseDelay = new Spinner(group, SWT.BORDER);
			spinnerCruiseDelay.setBounds(104, baseY + (F.MARGIN + F.LABEL_HEIGHT), 180, 23);
		}

		{// 计划策略
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_4.text"));
			group.setBounds(16, 514, 630, 60);

			btnCheckButtonPlan = new Button(group, SWT.CHECK);
			btnCheckButtonPlan.setBounds(104, baseY, 180, 23);
			btnCheckButtonPlan.setText(Lang.getString("FServoConfig.group_4.text_1"));

		}

		{// MQTT服务
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_5.text"));
			group.setBounds(16, 585, 630, 205);

			btnCheckButtonMqtt = new Button(group, SWT.CHECK);
			btnCheckButtonMqtt.setBounds(104, baseY, 180, 23);
			btnCheckButtonMqtt.setText(Lang.getString("FServoConfig.group_5.text_1"));
			btnCheckButtonMqtt.addSelectionListener(mqttSelectionAdapter);

			// HOST地址
			CLabel labelMqttHost = new CLabel(group, SWT.NONE);
			labelMqttHost.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelMqttHost.setText(Lang.getString("FServoConf.labelMqttHost.text"));

			textMqttHost = new Text(group, SWT.BORDER);
			textMqttHost.setBounds(104, baseY + (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 用户名
			CLabel labelMqttUsername = new CLabel(group, SWT.NONE);
			labelMqttUsername.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelMqttUsername.setText(Lang.getString("FServoConf.labelMqttUsername.text"));

			textMqttUsername = new Text(group, SWT.BORDER);
			textMqttUsername.setBounds(104, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 密码
			CLabel labelMqttPassword = new CLabel(group, SWT.NONE);
			labelMqttPassword.setBounds(16, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelMqttPassword.setText(Lang.getString("FServoConf.labelMqttPassword.text"));

			textMqttPassword = new Text(group, SWT.BORDER);
			textMqttPassword.setBounds(104, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 订阅主题
			CLabel labelMqttTopic = new CLabel(group, SWT.NONE);
			labelMqttTopic.setBounds(16, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelMqttTopic.setText(Lang.getString("FServoConf.labelMqttTopic.text"));

			textMqttTopic = new Text(group, SWT.BORDER);
			textMqttTopic.setBounds(104, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

		}

		{ // 数据库
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_3.text"));
			group.setBounds(16, 801, 630, 214);

			btnCheckButtonDb = new Button(group, SWT.CHECK);
			btnCheckButtonDb.setBounds(102, baseY, 180, 23);
			btnCheckButtonDb.setText(Lang.getString("FServoConfig.btnCheckButton_3.text"));
			btnCheckButtonDb.addSelectionListener(dbSelectionAdapter);

			// 最大连接数
			CLabel labelMax = new CLabel(group, SWT.NONE);
			labelMax.setText(Lang.getString("FServoConf.LabelMaximumNumberOfDatabaseConnections.text"));
			labelMax.setBounds(14, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			spinnerDbMax = new Spinner(group, SWT.BORDER);
			spinnerDbMax.setBounds(102, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			// 数据库类型
			CLabel labelType = new CLabel(group, SWT.NONE);
			labelType.setText(Lang.getString("FServoConf.LabelDatabaseType.text"));
			labelType.setBounds(14, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			comboDbType = new Combo(group, SWT.READ_ONLY);
			comboDbType.setBounds(102, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);
			comboDbType.add("MySql");
			comboDbType.add("Oracle");

			// 连接字符串
			CLabel labelStr = new CLabel(group, SWT.NONE);
			labelStr.setText(Lang.getString("FServoConf.LabelDatabaseConnectionString.text"));
			labelStr.setBounds(14, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			textDbUrl = new Text(group, SWT.BORDER);
			textDbUrl.setBounds(102, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 用户名
			CLabel labelUsername = new CLabel(group, SWT.NONE);
			labelUsername.setText(Lang.getString("FServoConf.LabelDatabaseUsername.text"));
			labelUsername.setBounds(14, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			textDbUser = new Text(group, SWT.BORDER);
			textDbUser.setBounds(102, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 密码
			CLabel labelPsw = new CLabel(group, SWT.NONE);
			labelPsw.setText(Lang.getString("FServoConf.LabelDatabasePassword.text"));
			labelPsw.setBounds(14, baseY + 5 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			textDbPassword = new Text(group, SWT.BORDER);
			textDbPassword.setBounds(102, baseY + 5 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);
		}

		{ // 云服务
			Group group = new Group(composite, SWT.NONE);
			group.setText(Lang.getString("FServoConfig.group_7.text"));
			group.setBounds(16, 1026, 630, 182);

			btnCheckButtonCloud = new Button(group, SWT.CHECK);
			btnCheckButtonCloud.setBounds(104, baseY, 180, 23);
			btnCheckButtonCloud.setText(Lang.getString("FServoConfig.btnCheckButtonCloud.text"));
			btnCheckButtonCloud.addSelectionListener(cloudSelectionAdapter);

			// 地址
			CLabel labelCloudHost = new CLabel(group, SWT.NONE);
			labelCloudHost.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelCloudHost.setText(Lang.getString("FServoConf.LabelCloudServiceAddress.text"));

			textCloudHost = new Text(group, SWT.BORDER);
			textCloudHost.setBounds(104, baseY + (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 端口
			CLabel labelCloudPort = new CLabel(group, SWT.NONE);
			labelCloudPort.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelCloudPort.setText(Lang.getString("FServoConf.LabelCloudServicePort.text"));

			spinnerCloudPort = new Spinner(group, SWT.BORDER);
			spinnerCloudPort.setBounds(104, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			spinnerCloudPort.setMaximum(10000);

			// 重置间隔
			CLabel labelCloudReset = new CLabel(group, SWT.NONE);
			labelCloudReset.setBounds(16, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelCloudReset.setText(Lang.getString("FServoConf.LabelCloudResetTime.text"));

			spinnerCloudReset = new Spinner(group, SWT.BORDER);
			spinnerCloudReset.setBounds(104, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);

			// 云服务标识
			CLabel labelCloudId = new CLabel(group, SWT.NONE);
			labelCloudId.setBounds(16, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelCloudId.setText(Lang.getString("FServoConf.LabelCloudId.text"));

			textCloudId = new Text(group, SWT.BORDER);
			textCloudId.setBounds(104, baseY + 4 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);
		}

		{
			Group group = new Group(composite, SWT.NONE);
			group.setBounds(16, 1218, 630, 129);

			// 天气城市ID
			CLabel labelWeatherCity = new CLabel(group, SWT.NONE);
			labelWeatherCity.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelWeatherCity.setText(Lang.getString("FServoConf.labelWeatherCity.text"));

			textWeatherCity = new Text(group, SWT.BORDER);
			textWeatherCity.setBounds(104, baseY + (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// PM城市ID
			CLabel labelPmCity = new CLabel(group, SWT.NONE);
			labelPmCity.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 80, 23);
			labelPmCity.setText(Lang.getString("FServoConf.labelPmCity.text"));

			textAqiCity = new Text(group, SWT.BORDER);
			textAqiCity.setBounds(104, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 180, 23);

			// 短信服务
			btnCheckButtonSms = new Button(group, SWT.CHECK);
			btnCheckButtonSms.setBounds(104, baseY, 180, 23);
			btnCheckButtonSms.setText(Lang.getString("FServoConfig.btnCheckButtonShortMessage.text"));
		}
		
		{
			Group group = new Group(composite, SWT.NONE);
			group.setBounds(16, 1354, 630, 182);

			// 业主手机号码
			CLabel labelPhone = new CLabel(group, SWT.NONE);
			labelPhone.setBounds(16, baseY , 130, 23);
			labelPhone.setText(Lang.getString("FServoConfig.btnCheckButton_5.text"));
			
			textPhone = new Text(group, SWT.BORDER);
			textPhone.setBounds(150, baseY , 250, 23);
			
			// 摄像头APP_ID
			CLabel labelAppId = new CLabel(group, SWT.NONE);
			labelAppId.setBounds(16, baseY + (F.MARGIN + F.LABEL_HEIGHT), 130, 23);
			labelAppId.setText(Lang.getString("FServoConfig.btnCheckButton_6.text"));
			
			textCameraAppId = new Text(group, SWT.BORDER);
			textCameraAppId.setBounds(150, baseY + (F.MARGIN + F.LABEL_HEIGHT), 250, 23);
			
			// 摄像头APP_SECRET
			CLabel labelAppSecret = new CLabel(group, SWT.NONE);
			labelAppSecret.setBounds(16, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 130, 23);
			labelAppSecret.setText(Lang.getString("FServoConfig.btnCheckButton_7.text"));
			
			textCameraAppSecret = new Text(group, SWT.BORDER);
			textCameraAppSecret.setBounds(150, baseY + 2 * (F.MARGIN + F.LABEL_HEIGHT), 250, 23);
			
			// 摄像头HOST地址
			CLabel labelCameraHost = new CLabel(group, SWT.NONE);
			labelCameraHost.setBounds(16, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 130, 23);
			labelCameraHost.setText(Lang.getString("FServoConfig.btnCheckButton_8.text"));
			
			textCameraHost = new Text(group, SWT.BORDER);
			textCameraHost.setBounds(150, baseY + 3 * (F.MARGIN + F.LABEL_HEIGHT), 250, 23);
			
		}

		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// 获取服务器配置
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(357, 448, 125, 27);
		btnNewButton.setText(Lang.getString("FServoConf.ButtonGetServoConfiguration.text"));
		btnNewButton.addSelectionListener(serverSelectionAdapter);

		// 确定
		Button btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(490, 448, 80, 27);
		btnAccept.addSelectionListener(acceptSelectionAdapter);
		btnAccept.setText(Lang.getString("Ensure.text"));

		// 取消
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(578, 448, 80, 27);
		btnCancel.addSelectionListener(cancelSelectionAdapter);
		btnCancel.setText(Lang.getString("Cancel.text"));

	}

	// 置灰设备组件(设备)
	private void setDeviceWidget() {
		spinnerDriverRestart.setEnabled(false);
		spinnerDriverHeartbeat.setEnabled(false);
		spinnerDriverInstructions.setEnabled(false);
		spinnerDriverTimeout.setEnabled(false);
		spinnerDriverDelay.setEnabled(false);
	}

	// 取消置灰(设备)
	private void cancleSetDeviceWidget() {
		spinnerDriverRestart.setEnabled(true);
		spinnerDriverHeartbeat.setEnabled(true);
		spinnerDriverInstructions.setEnabled(true);
		spinnerDriverTimeout.setEnabled(true);
		spinnerDriverDelay.setEnabled(true);
	}

	// 置灰控制组件(主控服务)
	private void setControlWidget() {
		textMasterIP.setEnabled(false);
		spinnerMasterPort.setEnabled(false);
	}

	// 取消置灰(主控服务)
	private void cancleSetControlWidget() {
		textMasterIP.setEnabled(true);
		spinnerMasterPort.setEnabled(true);
	}

	// 置灰控制组件(设备巡游)
	private void setCruiseWidget() {
		spinnerCruiseDelay.setEnabled(false);
	}

	// 取消置灰(设备巡游)
	private void cancleSetCruiseWidget() {
		spinnerCruiseDelay.setEnabled(true);
	}

	// 置灰控制组件(MQTT)
	private void setMqttWidget() {
		textMqttHost.setEnabled(false);
		textMqttUsername.setEnabled(false);
		textMqttPassword.setEnabled(false);
		textMqttTopic.setEnabled(false);
	}

	// 取消置灰(MQTT)
	private void cancleSetMqttWidget() {
		textMqttHost.setEnabled(true);
		textMqttUsername.setEnabled(true);
		textMqttPassword.setEnabled(true);
		textMqttTopic.setEnabled(true);
	}

	// 置灰组件(数据库)
	private void setDbWidget() {
		spinnerDbMax.setEnabled(false);
		comboDbType.setEnabled(false);
		textDbUrl.setEnabled(false);
		textDbPassword.setEnabled(false);
		textDbUser.setEnabled(false);
	}

	// 取消置灰(数据库)
	private void cancleSetDbWidget() {
		spinnerDbMax.setEnabled(true);
		comboDbType.setEnabled(true);
		textDbUrl.setEnabled(true);
		textDbPassword.setEnabled(true);
		textDbUser.setEnabled(true);
	}

	// 置灰控制组件(云服务)
	private void setCloudWidget() {
		textCloudHost.setEnabled(false);
		spinnerCloudPort.setEnabled(false);
		spinnerCloudReset.setEnabled(false);
		textCloudId.setEnabled(false);
	}

	// 取消置灰(云服务)
	private void cancleSetCloudWidget() {
		textCloudHost.setEnabled(true);
		spinnerCloudPort.setEnabled(true);
		spinnerCloudReset.setEnabled(true);
		textCloudId.setEnabled(true);
	}

	private void accept() {
		Message.Builder bm = Message.newBuilder();
		bm.setKey(CtrClient.getKey());
		bm.setUserId(servo.getUser().getId());

		SetServoConfig.Builder setServoConfig = SetServoConfig.newBuilder();

		if (btnCheckButtonDevice.getSelection()) {
			setServoConfig.setDriver(true);
		}
		if (btnCheckButtonControl.getSelection()) {
			setServoConfig.setMaster(true);
		}
		if (btnCheckButtonCruise.getSelection()) {
			setServoConfig.setCruise(true);
		}
		if (btnCheckButtonPlan.getSelection()) {
			setServoConfig.setStrategy(true);
		}
		if (btnCheckButtonMqtt.getSelection()) {
			setServoConfig.setMqtt(true);
		}
		if (btnCheckButtonDb.getSelection()) {
			setServoConfig.setDb(true);
		}
		if (btnCheckButtonCloud.getSelection()) {
			setServoConfig.setCloud(true);
		}
		setServoConfig.setName(textName.getText());
		setServoConfig.setThread(spinnerThread.getSelection());
		setServoConfig.setDebug(checkButtonDebug.getSelection());

		setServoConfig.setDriverRestart(spinnerDriverRestart.getSelection());
		setServoConfig.setDriverHeartbeat(spinnerDriverHeartbeat.getSelection());
		setServoConfig.setDriverInterval(spinnerDriverInstructions.getSelection());
		setServoConfig.setDriverTimeout(spinnerDriverTimeout.getSelection());
		setServoConfig.setDriverDelay(spinnerDriverDelay.getSelection());

		setServoConfig.setMasterIp(textMasterIP.getText());
		setServoConfig.setMasterPort(spinnerMasterPort.getSelection());

		setServoConfig.setCruiseDelay(spinnerCruiseDelay.getSelection());

		setServoConfig.setMqttHost(textMqttHost.getText());
		setServoConfig.setMqttUsername(textMqttUsername.getText());
		setServoConfig.setMqttPassword(textMqttPassword.getText());
		setServoConfig.setMqttSubScriptionTopic(textMqttTopic.getText());

		setServoConfig.setDbMax(spinnerDbMax.getSelection());
		setServoConfig.setDbType(comboDbType.getText());
		setServoConfig.setDbUrl(textDbUrl.getText());
		setServoConfig.setDbUser(textDbUser.getText());
		setServoConfig.setDbPassword(textDbPassword.getText());

		setServoConfig.setCloudHost(textCloudHost.getText());
		setServoConfig.setCloudPort(spinnerCloudPort.getSelection());
		setServoConfig.setCloudReset(spinnerDriverRestart.getSelection());
		setServoConfig.setCloudId(textCloudId.getText());

		setServoConfig.setWeatherWeaid(textWeatherCity.getText());
		setServoConfig.setAqiWeaid(textAqiCity.getText());
		setServoConfig.setSms(btnCheckButtonSms.getSelection());
		
		setServoConfig.setPhone(textPhone.getText());
		setServoConfig.setCameraAppId(textCameraAppId.getText());
		setServoConfig.setCameraAppSecret(textCameraAppSecret.getText());
		setServoConfig.setCameraHostAdress(textCameraHost.getText());

		bm.setSetServoConfig(setServoConfig);
		servo.getClient().send(bm.build());
		close();
	}

	private void sendMess(CtrClient client) {
		Message.Builder m = Message.newBuilder();
		m.setKey(CtrClient.getKey());
		m.setUserId(servo.getUser().getId());
		GetServoConfig.Builder gb = GetServoConfig.newBuilder();
		m.setGetServoConfig(gb);
		client.send(m.build());
	}

	public void setValue(GetServoConfig getServoConfig) {
		textName.setText(getServoConfig.getName());
		spinnerThread.setSelection(getServoConfig.getThread());
		checkButtonDebug.setSelection(getServoConfig.getDebug());
		btnCheckButtonSms.setSelection(getServoConfig.getSms());

		if (!getServoConfig.getDriver()) {
			setDeviceWidget();
		} else {
			cancleSetDeviceWidget();
			btnCheckButtonDevice.setSelection(true);
		}

		if (!getServoConfig.getMaster()) {
			setControlWidget();
		} else {
			cancleSetControlWidget();
			btnCheckButtonControl.setSelection(true);
		}

		if (!getServoConfig.getCruise()) {
			setCruiseWidget();
		} else {
			cancleSetCruiseWidget();
			btnCheckButtonCruise.setSelection(true);
		}

		if (!getServoConfig.getDb()) {
			setDbWidget();
		} else {
			cancleSetDbWidget();
			btnCheckButtonDb.setSelection(true);
		}

		if (getServoConfig.getStrategy()) {
			btnCheckButtonPlan.setSelection(true);
		}

		if (!getServoConfig.getMqtt()) {
			setMqttWidget();
		} else {
			cancleSetMqttWidget();
			btnCheckButtonMqtt.setSelection(true);
		}

		if (!getServoConfig.getCloud()) {
			setCloudWidget();
		} else {
			cancleSetCloudWidget();
			btnCheckButtonCloud.setSelection(true);
		}

		spinnerDriverRestart.setSelection(getServoConfig.getDriverRestart());
		spinnerDriverHeartbeat.setSelection(getServoConfig.getDriverHeartbeat());
		spinnerDriverInstructions.setSelection(getServoConfig.getDriverInterval());
		spinnerDriverTimeout.setSelection(getServoConfig.getDriverTimeout());
		spinnerDriverDelay.setSelection(getServoConfig.getDriverDelay());

		textMasterIP.setText(String.valueOf(getServoConfig.getMasterIp()));
		spinnerMasterPort.setSelection(getServoConfig.getMasterPort());

		spinnerCruiseDelay.setSelection(getServoConfig.getCruiseDelay());

		textMqttHost.setText(getServoConfig.getMqttHost());
		textMqttUsername.setText(getServoConfig.getMqttUsername());
		textMqttPassword.setText(getServoConfig.getMqttPassword());
		textMqttTopic.setText(getServoConfig.getMqttSubScriptionTopic());

		spinnerDbMax.setSelection(getServoConfig.getDbMax());
		comboDbType.setText(String.valueOf(getServoConfig.getDbType()));
		textDbUrl.setText(String.valueOf(getServoConfig.getDbUrl()));
		textDbUser.setText(String.valueOf(getServoConfig.getDbUser()));
		textDbPassword.setText(String.valueOf(getServoConfig.getDbPassword()));

		textCloudHost.setText(getServoConfig.getCloudHost());
		spinnerCloudPort.setSelection(getServoConfig.getCloudPort());
		spinnerCloudReset.setSelection(getServoConfig.getCloudReset());
		textCloudId.setText(getServoConfig.getCloudId());

		textWeatherCity.setText(getServoConfig.getWeatherWeaid());
		textAqiCity.setText(getServoConfig.getAqiWeaid());

		textPhone.setText(getServoConfig.getPhone());
		textCameraAppId.setText(getServoConfig.getCameraAppId());
		textCameraAppSecret.setText(getServoConfig.getCameraAppSecret());
		textCameraHost.setText(getServoConfig.getCameraHostAdress());
	}

	@Override
	public void received(Servo s, Message m, Map<Message, Unit> units) {
		if (m.getActionCase() == ActionCase.GET_SERVO_CONFIG) {
			final GetServoConfig gsc = m.getGetServoConfig();
			if (m.getResult() == Result.SUCCESS && gsc != null) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						setValue(gsc);
					}
				});
			} else {
				F.mbWaring(shell, Lang.getString("FServoConf.ShellTitle.text"), Lang.getString("FServoConf.MessageBoxContent.text"));
			}
		}

	}

	private ShellAdapter shellAdapter = new ShellAdapter() {
		@Override
		public void shellClosed(ShellEvent e) {
			closing = true;
			close();
		}
	};

	// 启用设备服务
	private SelectionAdapter deviceSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonDevice.getSelection()) {
				cancleSetDeviceWidget();
			} else {
				setDeviceWidget();
			}
		}
	};

	// 启用主控服务
	private SelectionAdapter controlSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonControl.getSelection()) {
				cancleSetControlWidget();
			} else {
				setControlWidget();
			}
		}
	};

	// 启用设备巡游服务
	private SelectionAdapter cruiseSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonCruise.getSelection()) {
				cancleSetCruiseWidget();
			} else {
				setCruiseWidget();
			}
		}
	};

	// 启用MQTT服务
	private SelectionAdapter mqttSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonMqtt.getSelection()) {
				cancleSetMqttWidget();
			} else {
				setMqttWidget();
			}
		}
	};

	// 启用数据库服务
	private SelectionAdapter dbSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonDb.getSelection()) {
				cancleSetDbWidget();
			} else {
				setDbWidget();
			}
		}
	};

	// 启用云服务
	private SelectionAdapter cloudSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (btnCheckButtonCloud.getSelection()) {
				cancleSetCloudWidget();
			} else {
				setCloudWidget();
			}
		}
	};

	// 获取服务器配置
	private SelectionListener serverSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			sendMess(servo.getClient());
		}
	};

	// 确定按钮
	private SelectionListener acceptSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			accept();
		}
	};

	// 取消按钮
	private SelectionListener cancelSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			close();
		}
	};

	@Override
	public void close() {
		if (!closing) {
			shell.close();
		}
	}
}
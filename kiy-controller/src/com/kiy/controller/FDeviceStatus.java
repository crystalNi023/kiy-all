package com.kiy.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Types.Status;
import com.kiy.common.Unit;
import com.kiy.common.User;
import com.kiy.controller.view.CustomSwitchView;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.ReadDeviceStatus;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Messages.WriteDeviceStatus.Builder;
import com.kiy.protocol.Units.MDevice;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

public class FDeviceStatus extends Dialog implements FormMessage {
	private Shell shell;
	private Device device;
	private Label deviceName;
	private Label deviceType;
	private Label deviceFirm;
	private Label deviceZone;
	private Label deviceRelay;
	private Label deviceConnect;
	private Label deviceNumber;
	private Label deviceSerialNumber;
	private Label deviceInstallAddress;
	private Label textProduct;
	private Label textInstall;
	private Button btnEexcute;

	private Button btnNotice;
	private Button btnDetect;

	private ScrolledComposite scrolledComposite;
	private Composite composite;
	private Servo servo;
	private List<Feature> unRedaOnlyfeatures;

	public FDeviceStatus(Shell arg0) {
		super(arg0);
	}

	public void open(Device d, Servo s) {
		device = d;
		servo = s;
		unRedaOnlyfeatures = new ArrayList<>();
		createContents();
		F.center(getParent(), shell);

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setText(Lang.getString("FDeviceStatus.ShellName.text") + device.getName());
		shell.setSize(546, 533);
		shell.addShellListener(shellAdapter);

		Group groupDeviceInfo = new Group(shell, SWT.NONE);
		groupDeviceInfo.setText(Lang.getString("FDeviceStatus.group_1.text")); //$NON-NLS-1$
		groupDeviceInfo.setBounds(16, 16, 508, 232);

		int baseY ;
		if (FMain.THIS_IS_MAC) {
			baseY = 16;
		} else {
			baseY = 26;
		}

		{
			Label lblNewLabel7 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel7.setText(Lang.getString("FDeviceStatus.lblNewLabel_7.text_1"));
			lblNewLabel7.setBounds(16, baseY, 76, 20);

			// 设备名称
			deviceName = new Label(groupDeviceInfo, SWT.NONE);
			deviceName.setBounds(100, baseY, 150, 20);

			Label lblNewLabel11 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel11.setBounds(258, baseY, 76, 20);
			lblNewLabel11.setText(Lang.getString("FDeviceStatus.lblNewLabel_11.text_1"));

			//
			deviceType = new Label(groupDeviceInfo, SWT.NONE);
			deviceType.setBounds(342, baseY, 150, 20);

			Label lblNewLabel12 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel12.setBounds(16, baseY + (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel12.setText(Lang.getString("FDeviceStatus.lblNewLabel_14.text")); //$NON-NLS-1$

			deviceFirm = new Label(groupDeviceInfo, SWT.NONE);
			deviceFirm.setBounds(100, baseY + (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label lblNewLabel13 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel13.setBounds(258, baseY + (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel13.setText(Lang.getString("FDeviceStatus.lblNewLabel_16.text")); //$NON-NLS-1$

			deviceZone = new Label(groupDeviceInfo, SWT.NONE);
			deviceZone.setBounds(342, baseY + (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label lblNewLabel9 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel9.setBounds(16, baseY + 2 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel9.setText(Lang.getString("FDeviceStatus.lblNewLabel_9.text")); //$NON-NLS-1$

			deviceRelay = new Label(groupDeviceInfo, SWT.NONE);
			deviceRelay.setBounds(100, baseY + 2 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label label2 = new Label(groupDeviceInfo, SWT.NONE);
			label2.setBounds(258, baseY + 2 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			label2.setText(Lang.getString("FDeviceStatus.label_2.text")); //$NON-NLS-1$

			deviceConnect = new Label(groupDeviceInfo, SWT.NONE);
			deviceConnect.setBounds(342, baseY + 2 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label label3 = new Label(groupDeviceInfo, SWT.NONE);
			label3.setBounds(16, baseY + 3 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			label3.setText(Lang.getString("FDeviceStatus.label_3.text")); //$NON-NLS-1$

			deviceNumber = new Label(groupDeviceInfo, SWT.NONE);
			deviceNumber.setBounds(100, baseY + 3 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label label4 = new Label(groupDeviceInfo, SWT.NONE);
			label4.setBounds(258, baseY + 3 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			label4.setText(Lang.getString("FDeviceStatus.label_4.text")); //$NON-NLS-1$

			deviceSerialNumber = new Label(groupDeviceInfo, SWT.NONE);
			deviceSerialNumber.setBounds(342, baseY + 3 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 150, 20);

			Label lblNewLabel8 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel8.setBounds(16, baseY + 4 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel8.setText(Lang.getString("FDeviceStatus.lblNewLabel_8.text")); //$NON-NLS-1$

			deviceInstallAddress = new Label(groupDeviceInfo, SWT.NONE);
			deviceInstallAddress.setBounds(100, baseY + 4 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 392, 20);

			Label lblNewLabel = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel.setBounds(16, baseY + 5 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel.setText(Lang.getString("FDeviceStatus.lblNewLabel.text")); //$NON-NLS-1$

			textProduct = new Label(groupDeviceInfo, SWT.NONE);
			textProduct.setBounds(100, baseY + 5 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 392, 20);

			Label lblNewLabel1 = new Label(groupDeviceInfo, SWT.NONE);
			lblNewLabel1.setBounds(16, baseY + 6 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 76, 20);
			lblNewLabel1.setText(Lang.getString("FDeviceStatus.lblNewLabel_1.text_1")); //$NON-NLS-1$

			textInstall = new Label(groupDeviceInfo, SWT.NONE);
			textInstall.setBounds(100, baseY + 6 * (F.MARGIN + F.SMALL_LABEL_HEIGHT), 392, 20);

			scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
			scrolledComposite.setBounds(16, 256, 508, 201);
			scrolledComposite.setExpandVertical(true);
			scrolledComposite.setExpandHorizontal(true);

			Button btnCancel = new Button(shell, SWT.NONE);
			btnCancel.setBounds(426, 465, 98, 30);
			btnCancel.setText(Lang.getString("FDeviceStatus2.btnNewButton.text")); //$NON-NLS-1$
			btnCancel.addSelectionListener(btnCancelSelectionAdapter);

			btnEexcute = new Button(shell, SWT.NONE);
			btnEexcute.setBounds(320, 465, 98, 30);
			btnEexcute.setText(Lang.getString("FDeviceStatus2.btnNewButton_1.text")); //$NON-NLS-1$
			btnEexcute.addSelectionListener(btnExecuteSelectionAdapter);
			btnEexcute.setVisible(false);
			btnEexcute.setEnabled(false);

			composite = new Composite(scrolledComposite, SWT.NONE);
			createContentsForFeature(device);
			scrolledComposite.setContent(composite);
			scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		}
	}

	private void createContentsForFeature(Device device) {

		int y = 56;

		// Label Key
		int labelHeight = 20;

		int scaleValueHeigth = 48;

		Label labelAlarm = new Label(composite, SWT.NONE);
		labelAlarm.setBounds(16, 16, 32, 32);

		CLabel labelWaring = new CLabel(composite, SWT.NONE);
		labelWaring.setBounds(98, 16, 100, 32);

		// 设置报警状态
		Status alarm = device.getStatus();
		if (alarm.equals(Status.NONE)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_threshold_no.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelNoWaring.text"));
		} else if (alarm.equals(Status.OFFLINE)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_offline.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelOffOnLine.text"));
		} else if (alarm.equals(Status.THRESHOLD)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_threshold_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelThresholdValueWaring.text"));
		} else if (alarm.equals(Status.WORK)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_work_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelWorkExceptionWaring.text"));
		} else if (alarm.equals(Status.FAULT)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_fault_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelEquipmentFailure.text"));
		} else if (alarm.equals(Status.C_RELAY)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_network_no_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelRoutingError/Unreachable.text"));
		} else if (alarm.equals(Status.C_BUSY)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_busy_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelDeviceBusy.text"));
		} else {
			// 未定义报警
		}

		btnNotice = new Button(composite, SWT.NONE);
		btnNotice.setBounds(258, 20, 80, 27);
		btnNotice.setText(device.getNotice() ? "关闭通知" : "打开通知");
		btnNotice.addSelectionListener(btnNoticeSelectionAdaper);

		btnDetect = new Button(composite, SWT.NONE);
		btnDetect.setBounds(346, 20, 80, 27);
		btnDetect.setText(device.getDetect() ? "关闭检测" : "打开检测");
		btnDetect.addSelectionListener(btnDetectSelectionAdaper);

		Feature[] features = device.getFeatures();
		if (features == null || features.length <= 0) {
			return;
		}

		for (int i = 0; i < features.length; i++) {
			if (features[i].PRIMARY) {
				if (features[i].READ_ONLY) {
					CustomSwitchView customSwitchView = new CustomSwitchView(features[i], composite, y , device , servo);
					customSwitchView.layout();
					y = y + 8 + labelHeight;
				} else {
					btnEexcute.setEnabled(true);
					btnEexcute.setVisible(true);
					CustomSwitchView customSwitchView = new CustomSwitchView(features[i], composite, y ,device , servo);
					customSwitchView.layout();
					unRedaOnlyfeatures.add(customSwitchView.getFeature());
					y = y + 8 + scaleValueHeigth;
				}

			} else {
				continue;
			}
		}

	}

	private void make() {
		if (servo == null) {
			btnEexcute.setEnabled(false);
		} else {
			User user = servo.getUser();
			if (user == null) {
				btnEexcute.setEnabled(false);
			} else {
				if (user.allow(ActionCase.WRITE_DEVICE_STATUS.getNumber())) {
					btnEexcute.setEnabled(true);
				} else {
					btnEexcute.setEnabled(false);
				}

			}
		}

		if (device != null) {
			deviceName.setText(device.getName());
			deviceType.setText(Lang.getString("Kind." + device.getKind().name()));
			deviceFirm.setText(Lang.getString("Vendor." + device.getVendor().name()));
			System.err.println("device.getLink().name()" + device.getLink().name());
			if (device.getZone() != null) {
				deviceZone.setText(device.getZone().getName());
			}
			if (device.getRelay() != null) {
				deviceRelay.setText(device.getRelay().getName());
			}
			deviceConnect.setText(Lang.getString("Link." + device.getLink().name()));
			deviceNumber.setText(device.getNumber());
			deviceSerialNumber.setText(device.getSn());
			deviceInstallAddress.setText(device.getAddress());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (device.getProduced() != null) {
				textProduct.setText(formatter.format(device.getProduced()));
			}
			if (device.getInstalled() != null) {
				textInstall.setText(formatter.format(device.getInstalled()));
			}
		}
	}

	private ShellAdapter shellAdapter = new ShellAdapter() {
		@Override
		public void shellActivated(ShellEvent arg0) {
			make();
		}
	};

	private SelectionAdapter btnExecuteSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			setDeviceStatus(servo);
		}

	};

	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			close();
		}

	};

	@Override
	public void received(Servo s, Message m, Map<Message, Unit> units) {
		ActionCase actionCase = m.getActionCase();
		Result status = m.getResult();
		if (actionCase == ActionCase.READ_DEVICE_STATUS && status == Result.SUCCESS) {
			ReadDeviceStatus readDeviceStatus = m.getReadDeviceStatus();
			MDeviceStatus deviceStatus = readDeviceStatus.getItem();
			String id = deviceStatus.getId();
			Device device3 = s.getDevice(id);
			if (device.getId().equals(id)) {
				createContentsForFeature(device3);
			}
		}

		if (actionCase == ActionCase.UPDATE_DEVICE && status == Result.SUCCESS) {
			UpdateDevice updateDevice = m.getUpdateDevice();
			MDevice mDevice = updateDevice.getItem();
			btnNotice.setText(mDevice.getNotice() ? "关闭通知" : "打开通知");
			btnNotice.setEnabled(true);
			btnDetect.setText(mDevice.getDetect() ? "关闭检测" : "打开检测");
			btnDetect.setEnabled(true);

		}

	}

	@Override
	public void close() {
		shell.close();
	}

	//执行
	private void setDeviceStatus(Servo servo) {
		if (servo == null) {
			return;
		}

		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(shell, client);

		if (judgeClientActive) {
			return;
		}

		Message.Builder m = Message.newBuilder();
		m.setKey(CtrClient.getKey());
		m.setUserId(servo.getUser().getId());
		Builder writeDeviceStatus = WriteDeviceStatus.newBuilder();
		MDeviceStatus.Builder builder = writeDeviceStatus.getItemBuilder();

		builder.setDeviceId(device.getId());
		for (int index = 0; index < unRedaOnlyfeatures.size(); index++) {
			builder.putItems(unRedaOnlyfeatures.get(index).INDEX, unRedaOnlyfeatures.get(index).getValue());
		}

		m.setWriteDeviceStatus(writeDeviceStatus);
		client.send(m.build());
		
	}

	private void setDeviceNotice(Servo servo) {
		if (servo == null) {
			return;
		}
		CtrClient client = servo.getClient();
		boolean judgeClientActive = F.judgeClientActive(shell, client);
		if (judgeClientActive) {
			return;
		}
		Message.Builder m = Message.newBuilder();
		m.setKey(CtrClient.getKey());
		m.setUserId(servo.getUser().getId());
		UpdateDevice.Builder updateDevice = UpdateDevice.newBuilder();
		com.kiy.protocol.Units.MDevice.Builder builder = updateDevice.getItemBuilder();
		builder.setId(device.getId());
		builder.setVendor(device.getVendor().getValue());
		builder.setKind(device.getKind().getValue());
		builder.setLink(device.getLink().getValue());
		builder.setModel(device.getModel().getValue());
		builder.setSn(device.getSn());
		builder.setName(device.getName());
		builder.setNumber(device.getNumber());
		builder.setZoneId(device.getZoneId());
		if (device.getRelayId() != null)
			builder.setRelayId(device.getRelayId());
		builder.setNetworkPort(device.getNetworkPort());
		builder.setNetworkIp(device.getNetworkIp());
		builder.setSerialPort(device.getSerialPort());
		builder.setSerialBaudRate(device.getSerialBaudRate());
		builder.setRemark(device.getRemark());
		builder.setLoad(device.getLoad());
		builder.setPower(device.getPower());
		builder.setUse(device.getUse() == null ? 0 : device.getUse().getValue());
		builder.setAddress(device.getAddress());
		builder.setDelay(device.getDelay());
		builder.setMutual(device.getMutual());
		builder.setUsername(device.getUsername());
		builder.setPassword(device.getPassword());
		builder.setProduced(device.getProduced().getTime());
		builder.setInstalled(device.getInstalled().getTime());
		builder.setNotice(!device.getNotice());
		builder.setDetect(device.getDetect());
		builder.setPhasePower(device.getPhasePower());
		builder.setPhaseCheck(device.getPhaseCheck());

		m.setUpdateDevice(updateDevice);
		client.send(m.build());
	}

	private void setDeviceDetect(Servo servo) {
		if (servo == null) {
			return;
		}
		CtrClient client = servo.getClient();
		boolean judgeClientActive = F.judgeClientActive(shell, client);
		if (judgeClientActive) {
			return;
		}
		Message.Builder m = Message.newBuilder();
		m.setKey(CtrClient.getKey());
		m.setUserId(servo.getUser().getId());
		UpdateDevice.Builder updateDevice = UpdateDevice.newBuilder();
		com.kiy.protocol.Units.MDevice.Builder builder = updateDevice.getItemBuilder();
		builder.setId(device.getId());
		builder.setVendor(device.getVendor().getValue());
		builder.setKind(device.getKind().getValue());
		builder.setLink(device.getLink().getValue());
		builder.setModel(device.getModel().getValue());
		builder.setSn(device.getSn());
		builder.setName(device.getName());
		builder.setNumber(device.getNumber());
		builder.setZoneId(device.getZoneId());
		if (device.getRelayId() != null)
			builder.setRelayId(device.getRelayId());
		builder.setNetworkPort(device.getNetworkPort());
		builder.setNetworkIp(device.getNetworkIp());
		builder.setSerialPort(device.getSerialPort());
		builder.setSerialBaudRate(device.getSerialBaudRate());
		builder.setRemark(device.getRemark());
		builder.setLoad(device.getLoad());
		builder.setPower(device.getPower());
		builder.setUse(device.getUse() == null ? 0 : device.getUse().getValue());
		builder.setAddress(device.getAddress());
		builder.setDelay(device.getDelay());
		builder.setMutual(device.getMutual());
		builder.setUsername(device.getUsername());
		builder.setPassword(device.getPassword());
		builder.setProduced(device.getProduced().getTime());
		builder.setInstalled(device.getInstalled().getTime());
		builder.setNotice(device.getNotice());
		builder.setDetect(!device.getDetect());
		builder.setPhasePower(device.getPhasePower());
		builder.setPhaseCheck(device.getPhaseCheck());

		m.setUpdateDevice(updateDevice);
		client.send(m.build());
	}

	private SelectionAdapter btnNoticeSelectionAdaper = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			setDeviceNotice(servo);
			btnNotice.setEnabled(false);
		}

	};

	private SelectionAdapter btnDetectSelectionAdaper = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			setDeviceDetect(servo);
			btnDetect.setEnabled(false);
		}

	};

	
}

package com.kiy.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Tool;
import com.kiy.common.Types.Status;
import com.kiy.controller.view.CustomLabelView;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

import org.eclipse.swt.custom.ScrolledComposite;

public class FDeviceDetails extends Dialog {
	private Label textVendor;
	private Label textName;
	private Label textKind;
	private Label textZone;
	private Label textMutual;
	private Label textPassword;
	private Label textSerialPort;
	private Label textSerialBaudRate;
	private Label textNetworkPort;
	private Label textNetworkIp;
	private Label textRelay;
	private Label textNumber;
	private Label textRemark;
	private Label textAddress;
	private Label textSn;
	private Label textLoad;
	private Label textDelay;
	private Label textPower;
	private Label labelImage;
	private Label textProduct;
	private Label textInstall;
	private Label labelCheckPhasePosition;
	private Label labelReadPhasePosition;

	private Shell shell;
	private Device mDevice;
	private ScrolledComposite scrolledComposite;
	private Composite composite;

	private Device deviceHistory;
	private Label textLink;

	public FDeviceDetails(Shell arg0) {
		super(arg0);
	}

	public void open(Device d, Device history) {
		mDevice = d;
		deviceHistory = history;
		createContents();

		F.center(getParent(), shell);
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(16, 604, 659, 2);

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
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.SYSTEM_MODAL);
		shell.setSize(697, 681);
		shell.setText(Lang.getString("FDevice.device"));

		labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(16, 16, 32, 32);

		Label lblNewLabel = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel.setBounds(16, 56, 659, 2);

		int baseY ;
		if (FMain.THIS_IS_MAC) {
			baseY = 16;
		} else {
			baseY = 26;
		}

		Group group = new Group(shell, SWT.NONE);
		group.setText(Lang.getString("FDevice.basicInformation.text"));
		group.setBounds(16, 66, 660, 167);

		{

			Label lblNewLabel_1 = new Label(group, SWT.NONE);
			lblNewLabel_1.setBounds(16, baseY, 76, 20);
			lblNewLabel_1.setText(Lang.getString("FDevice.company"));

			textVendor = new Label(group, SWT.NONE);
			textVendor.setBounds(100, 26, 120, 20);

			Label label = new Label(group, SWT.NONE);
			label.setBounds(228, 26, 76, 20);
			label.setText(Lang.getString("FDevice.type"));

			textKind = new Label(group, SWT.NONE);
			textKind.setBounds(312, 26, 120, 20);

			Label lblNewLabel_2 = new Label(group, SWT.NONE);
			lblNewLabel_2.setBounds(440, 26, 76, 20);
			lblNewLabel_2.setText(Lang.getString("FDevice.conn"));

			Label label_1 = new Label(group, SWT.NONE);
			label_1.setBounds(16, 54, 76, 20);
			label_1.setText(Lang.getString("FDevice.zone"));

			textZone = new Label(group, SWT.NONE);
			textZone.setBounds(100, 54, 120, 20);

			Label label_2 = new Label(group, SWT.NONE);
			label_2.setText(Lang.getString("FDevice.nameDetaisl"));
			label_2.setBounds(228, 54, 76, 20);

			textName = new Label(group, SWT.NONE);
			textName.setBounds(312, 54, 120, 20);

			Label label_3 = new Label(group, SWT.NONE);
			label_3.setBounds(440, 54, 76, 20);
			label_3.setText(Lang.getString("FDevice.sequence"));

			textSn = new Label(group, SWT.NONE);
			textSn.setBounds(524, 54, 120, 20);

			Label label_4 = new Label(group, SWT.NONE);
			label_4.setBounds(16, 110, 76, 20);
			label_4.setText(Lang.getString("FDevice.address"));

			Label lblNewLabel_3 = new Label(group, SWT.NONE);
			lblNewLabel_3.setBounds(16, 138, 76, 20);
			lblNewLabel_3.setText(Lang.getString("FDevice.describe"));

			textRemark = new Label(group, SWT.NONE);
			textRemark.setBounds(100, 138, 544, 20);

			Label lblNewLabel_10 = new Label(group, SWT.NONE);
			lblNewLabel_10.setBounds(16, 82, 76, 20);
			lblNewLabel_10.setText(Lang.getString("FDevice.produceDate.text"));

			textProduct = new Label(group, SWT.NONE);
			textProduct.setBounds(100, 82, 120, 20);

			Label lblNewLabel_11 = new Label(group, SWT.NONE);
			lblNewLabel_11.setBounds(228, 82, 76, 20);
			lblNewLabel_11.setText(Lang.getString("FDevice.installDate.text"));

			textInstall = new Label(group, SWT.NONE);
			textInstall.setBounds(312, 82, 120, 20);

		}

		textAddress = new Label(group, SWT.NONE);
		textAddress.setBounds(100, 110, 544, 20);
		
		textLink = new Label(group, SWT.NONE);
		textLink.setBounds(524, 26, 120, 20);

		Group group1 = new Group(shell, SWT.NONE);
		group1.setText(Lang.getString("FDeviceDetails.status"));
		group1.setBounds(16, 241, 660, 140);

		scrolledComposite = new ScrolledComposite(group1, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(1, 26, 658, 112);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Group group11 = new Group(shell, SWT.NONE);
		group11.setText(Lang.getString("FDevice.commnunication.text"));
		group11.setBounds(15, 389, 660, 115);

		{
			Label lblNewLabel_4 = new Label(group11, SWT.NONE);
			lblNewLabel_4.setBounds(16, baseY, 76, 20);
			lblNewLabel_4.setText(Lang.getString("FDevice.relay"));

			textRelay = new Label(group11, SWT.NONE);
			textRelay.setBounds(100, 26, 120, 20);

			Label lblNewLabel_6 = new Label(group11, SWT.NONE);
			lblNewLabel_6.setBounds(228, 26, 76, 20);
			lblNewLabel_6.setText(Lang.getString("FDevice.deviceNumDetails"));

			textNumber = new Label(group11, SWT.NONE);
			textNumber.setBounds(312, 26, 120, 20);

			Label lblNewLabel_5 = new Label(group11, SWT.NONE);
			lblNewLabel_5.setBounds(440, 26, 76, 20);
			lblNewLabel_5.setText(Lang.getString("FDevice.netIp"));
			
			textNetworkIp = new Label(group11, SWT.NONE);
			textNetworkIp.setBounds(524, 26, 120, 20);

			Label lblNewLabel_7 = new Label(group11, SWT.NONE);
			lblNewLabel_7.setBounds(16, 54, 76, 20);
			lblNewLabel_7.setText(Lang.getString("FDevice.netPort"));

			textNetworkPort = new Label(group11, SWT.NONE);
			textNetworkPort.setBounds(100, 54, 120, 20);

			Label lblNewLabel_8 = new Label(group11, SWT.NONE);
			lblNewLabel_8.setBounds(228, 54, 76, 20);
			lblNewLabel_8.setText(Lang.getString("FDevice.serialNum"));

			textSerialPort = new Label(group11, SWT.NONE);
			textSerialPort.setBounds(312, 54, 120, 20);

			Label label1 = new Label(group11, SWT.NONE);
			label1.setBounds(440, 54, 76, 20);
			label1.setText(Lang.getString("FDevice.serialBautRate"));

			textSerialBaudRate = new Label(group11, SWT.NONE);
			textSerialBaudRate.setBounds(524, 54, 120, 20);

			Label lblNewLabel_9 = new Label(group11, SWT.NONE);
			lblNewLabel_9.setBounds(16, 82, 76, 20);
			lblNewLabel_9.setText(Lang.getString("FDevice.devicepwd"));

			textPassword = new Label(group11, SWT.NONE);
			textPassword.setBounds(100, 82, 544, 20);
//			
//			Label lblNewLabel_10 = new Label(group11,SWT.NONE);
//			lblNewLabel_10.setBounds(arg0);
		}
		
		

		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setText(Lang.getString("FDevice.other.text"));
		group_1.setBounds(15, 512, 660, 84);

		{
			Label label_11 = new Label(group_1, SWT.NONE);
			label_11.setBounds(16, baseY, 76, 20);
			label_11.setText(Lang.getString("FDevice.deviceMutual"));

			textMutual = new Label(group_1, SWT.NONE);
			textMutual.setBounds(100, 26, 120, 20);

			Label label_21 = new Label(group_1, SWT.NONE);
			label_21.setBounds(228, 26, 76, 20);
			label_21.setText(Lang.getString("FDevice.delayTime"));

			textDelay = new Label(group_1, SWT.NONE);
			textDelay.setBounds(312, 26, 120, 20);

			Label label_31 = new Label(group_1, SWT.NONE);
			label_31.setBounds(440, 26, 76, 20);
			label_31.setText(Lang.getString("FDevice.ownpower"));

			textLoad = new Label(group_1, SWT.NONE);
			textLoad.setBounds(524, 26, 120, 20);

			Label label_41 = new Label(group_1, SWT.NONE);
			label_41.setBounds(16, 54, 76, 20);
			label_41.setText(Lang.getString("FDevice.loadpower"));

			textPower = new Label(group_1, SWT.NONE);
			textPower.setBounds(100, 54, 120, 20);

			Label label = new Label(group_1, SWT.NONE);
			label.setText(Lang.getString("FDevice.CheckPhasePosition.text"));
			label.setBounds(228, 54, 76, 20);

			labelCheckPhasePosition = new Label(group_1, SWT.NONE);
			labelCheckPhasePosition.setBounds(312, 54, 120, 20);

			Label label_1 = new Label(group_1, SWT.NONE);
			label_1.setText(Lang.getString("FDevice.ReadPhasePosition.text"));
			label_1.setBounds(440, 54, 76, 20);

			labelReadPhasePosition = new Label(group_1, SWT.NONE);
			labelReadPhasePosition.setBounds(524, 54, 120, 20);
		}

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(578, 614, 98, 30);
		btnNewButton.setText(Lang.getString("FDeviceCurrentStatus.ButtonClose.text"));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		creteContentsForFeature();
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		make();
	}

	private void creteContentsForFeature() {
		Device d = null;
		if (deviceHistory != null) {
			d = deviceHistory;
		} else {
			d = mDevice;
		}

		composite = new Composite(scrolledComposite, SWT.NONE);

		int y = 40;

		// Label Key
		int labelHeight = 20;

		Label labelAlarm = new Label(composite, SWT.NONE);
		labelAlarm.setBounds(16, 0, 32, 32);

		Label labelWaring = new Label(composite, SWT.NONE);
		labelWaring.setBounds(100, 6, 185, 20);

		// 设置报警状态
		Status alarm = d.getStatus();
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
		} else if (alarm.equals(Status.C_BUSY)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_busy_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelDeviceBusy.text"));
		} else if (alarm.equals(Status.C_RELAY)) {
			labelAlarm.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_network_no_32.png"));
			labelWaring.setText(Lang.getString("FDeviceCurrentStatus.LabelRoutingError/Unreachable.text"));
		} else {
			// 未定义报警
		}

		Feature[] features = d.getFeatures();
		if (features == null || features.length <= 0) {
			return;
		}

		int featureCount = d.getFeatureCount();
		int totalColumnNumber = 3;
		int startIndex = 0;
		
		if (featureCount > 0) {
			for (int i = 0; i < d.getFeatures().length; i++) {
				CustomLabelView customLabelView = new CustomLabelView(features[i], composite, y, startIndex);
				customLabelView.layout();
				
				if (startIndex == totalColumnNumber - 1) {
					y = y + 8 + labelHeight;
					startIndex = 0;
				} else {
					startIndex = startIndex + 1;
				}
//				if (isSecond == false) {
//					y = y + 8 + labelHeight;
//				}
			}
		}

	}

	private void make() {
		if (mDevice != null) {
			// 图片显示
			labelImage.setImage(Resource.getImage(FMain.class, String.format("/com/kiy/resources/device_%s_32.png", Tool.toLowerCase(mDevice.getKind()))));
			
			textVendor.setText(Lang.getString("Vendor." + mDevice.getVendor().name()));
			textKind.setText(Lang.getString("Kind." + mDevice.getKind().name()));
			textLink.setText(Lang.getString("Link." + mDevice.getLink().name()));
			textZone.setText(mDevice.getZone() == null ? Lang.getString("FDevice.rootZone") : mDevice.getZone().getName());
			textName.setText(mDevice.getName());
			textSn.setText(mDevice.getSn());
			textAddress.setText(mDevice.getAddress());
			textRemark.setText(mDevice.getRemark());

			textRelay.setText(mDevice.getRelay() == null ? Lang.getString("FDevice.rootRelay") : mDevice.getRelay().getName());
			textNumber.setText(mDevice.getNumber());
			textNetworkIp.setText(mDevice.getNetworkIp());
			textNetworkPort.setText(String.valueOf(mDevice.getNetworkPort()));
			textSerialPort.setText(mDevice.getSerialPort());
			textSerialBaudRate.setText(String.valueOf(mDevice.getSerialBaudRate()));
			textPassword.setText(mDevice.getPassword());

			textMutual.setText(String.valueOf(mDevice.getMutual()));
			textDelay.setText(String.valueOf(mDevice.getDelay())+"s");
			textLoad.setText(String.valueOf(mDevice.getLoad())+"w");
			textPower.setText(String.valueOf(mDevice.getPower())+"w");
			labelCheckPhasePosition.setText(String.valueOf(mDevice.getPhaseCheck()));
			labelReadPhasePosition.setText(String.valueOf(mDevice.getPhasePower()));

			Date produced = mDevice.getProduced();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (produced != null)

				textProduct.setText(formatter.format(produced));
			Date installed = mDevice.getInstalled();
			if (installed != null) {
				textInstall.setText(formatter.format(installed));
			}
		}
	}
}

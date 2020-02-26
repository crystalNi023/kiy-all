package com.kiy.controller.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateDevice;
import com.kiy.resources.Lang;

/**
 * 自定义Feature开关类显示组合控件
 * @author 35210
 *
 */
public class CustomSwitchView {
	private int baseY;
	private Device device;
	private Servo servo;
	// Label Key
	private final int labelX = 16;
	private final int labelWidth = 92;
	private final int labelHeight = 20;

	// Label Value
	private final int labelValueX = 116;
	private final int labelValueWidth = 96;
	private final int labelValueHeight = 20;

	// Label Off
	private final int labelOffX = 235;
	private final int labelOffWidth = 20;
	private final int labelOffHeight = 20;

	// Scale Value
	
	private final int scaleValueX = 268;
	private final int scaleValueWidth = 108;
	private final int scaleValueHeigth = 48;

	// Label Open
	private final int labelOpenX = 394;
	private final int labelOpenWidth = 20;
	private final int labelOpenHeight = 20;

	private Feature feature;
	private Composite composite;
	
	private Scale scale;
	private Label labelValue;
	private Text text;
	

	/**
	 * 
	 * @param feature Feature
	 * @param c Composite
	 * @param y y轴坐标
	 */
	public CustomSwitchView(Feature feature,Composite c,int y,Device device,Servo servo) {
		this.baseY = y;
		this.device = device;
		this.servo = servo;
		this.feature = feature;
		this.composite = c;
	}

	/**
	 * 自定义控件布局
	 */
	public Device layout() {
		if(feature == null){
			return null;
		}
		
		if(feature.READ_ONLY){ // 只读属性
			// Feature 名字
			CLabel labelKey1 = new CLabel(composite, SWT.NONE);
			labelKey1.setText(feature.getName());
			labelKey1.setBounds(labelX, baseY , labelWidth, labelHeight);

			// Feature 读数
			Label labelValue1 = new Label(composite, SWT.NONE);
			labelValue1.setText(feature.getText());
			labelValue1.setBounds(labelValueX, baseY, labelValueWidth+200, labelValueHeight);
		}else{ // 可控属性
			// Feature 名字
			CLabel labelKey = new CLabel(composite, SWT.NONE);
			labelKey.setText(feature.getName());
			labelKey.setBounds(labelX, baseY + 16, labelWidth, labelHeight);
			
			// Feature 读数
			labelValue = new Label(composite,SWT.NONE);
			labelValue.setText(feature.getText());
			labelValue.setBounds(labelValueX, baseY+16, labelValueWidth, labelValueHeight);

			// Feature 关 有点没有该属性
			Label LabelOff = new Label(composite, SWT.NONE);
			LabelOff.setText(Lang.getString("FDetectWrite.off"));
			LabelOff.setBounds(labelOffX, baseY + 16, labelOffWidth, labelOffHeight);
			if (feature.MAXIMUM!=1&&feature.MAXIMUM!=100){
				LabelOff.setVisible(false);
			}else {
				LabelOff.setVisible(true);
			}
			
			// 根据范围设置开关范围
			if (feature.MAXIMUM<100000000) {
				scale = new Scale(composite, SWT.NONE);
				scale.setBounds(scaleValueX, baseY, scaleValueWidth, scaleValueHeigth);
				scale.setSelection(feature.getValue());
				scale.setMaximum(feature.MAXIMUM);
				scale.setMinimum(feature.MINIMUM);
				scale.setPageIncrement(feature.STEP);
				scale.addSelectionListener(scaleSelectionAdapter);
			}else {
				text = new Text(composite,SWT.NONE);
				text.setBounds(scaleValueX, baseY, scaleValueWidth, scaleValueHeigth);
				text.setTextLimit(6);
				text.addVerifyListener(new VerifyListener() {
					@Override
					public void verifyText(VerifyEvent e) {
						 boolean b = ("0123456789abcdef".indexOf(e.text)>=0);     
			              e.doit = b;    
					}
				});
				text.addModifyListener(textSelectionAdapter);
			}
			
			Label LabelOpen = new Label(composite, SWT.NONE);
			LabelOpen.setText(Lang.getString("FDetectWrite.open"));
			LabelOpen.setBounds(labelOpenX, baseY + 16, labelOpenWidth, labelOpenHeight);
			if (feature.MAXIMUM!=1&&feature.MAXIMUM!=100){
				LabelOpen.setVisible(false);
			}else {
				LabelOpen.setVisible(true);
			}
		}
		return device;
		
	}
	
	
	/**
	 * 开关点击监听
	 */
	private SelectionAdapter scaleSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//根据当前选中开关量设置到feature中
			feature.setValue(scale.getSelection()); 
			//设置选中的状态
			labelValue.setText(feature.getText());
		}
		
	};
	
	/**
	 * 文本框点击监听textSelectionAdapter
	 */
	private ModifyListener textSelectionAdapter = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent arg0) {
			// 根据当前选中值设置到feature中
			if (text.getCharCount()==6) {
				feature.setValue(Tool.RGB2int(text.getText()));
				labelValue.setText(feature.getText());
				//因为调色是RGB类型，转换为int存储feature会出问题，所以存在设备sn属性中
				device.setSn(text.getText());
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
				servo.getClient().send(m.build());
			}
		}
	};
	

	/**
	 * 获取Feature
	 * @return
	 */
	public Feature getFeature() {
		return feature;
	}
	
	
}

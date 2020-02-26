package com.kiy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Linkage;
import com.kiy.common.LinkageDevice;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.protocol.Messages.CreateLinkage;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateLinkage;
import com.kiy.protocol.Units.MLinkage;
import com.kiy.protocol.Units.MLinkageDevice;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

public class FLinkage extends Dialog {
	private Shell shell;

	private CLabel labelText; // 输入验证
	private Text nameText;// 联动名称
	private Text remarkText; // 联动描述
	private Composite compositeOpen; // 开启联动面板
	private Button buttonAdd; // 添加按钮
	private Button buttonRemove; // 移除设备按钮
	private Button buttonModify; // 修改feature按钮
	private Button btnAccept; // 确认按钮
	private Button btnCancel; // 取消按钮
	private Table tableOpen; // 开启联动设备table
	private Table table; // 显示设备table
	private TableColumn tableColumn;
	private TableColumn tableColumn2;
	private TableColumn tableColumn3;
	private TableColumn tblclmnFea;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_3;
	private TableItem item;

	public List<Device> openDevicesTemp = new ArrayList<>();
	public List<Device> closeDevicesTemp = new ArrayList<>();
	private List<Device> devicesList;
	private Servo servo;
	private Linkage linkage;
	private LinkedHashSet<LinkageDevice> linkageDevices = new LinkedHashSet<LinkageDevice>();
	//去重

	public boolean isUpdate = false;
	private boolean closing;

	public FLinkage(Shell arg0) {
		super(arg0);
	}

	public Linkage open(Servo s, Linkage l) {
		if (s == null) {
			throw new NullPointerException("Servo can not be null!");
		}
		servo = s;
		linkage = l;

		createContent();
		F.center(getParent(), shell);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return linkage;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setSize(665, 429);
		shell.setText(Lang.getString("FLinkageRecord.ShellName.text"));
		shell.addFocusListener(focusAdapter);
		shell.addShellListener(shellAdapter);

		labelText = new CLabel(shell, SWT.NONE);
		labelText.setBounds(16, 16, 517, 32);
		labelText.setVisible(false);
		labelText.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/verify.png"));

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(541, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_scheduling_32.png"));

		Label labelTop = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		labelTop.setBounds(0, 62, 665, 2);

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(16, 70, 626, 287);

		TabItem tabConvention = new TabItem(tabFolder, SWT.NONE);
		tabConvention.setText(Lang.getString("FScene.TableItemConventional.text"));

		Composite compositeConvention = new Composite(tabFolder, SWT.NONE);
		tabConvention.setControl(compositeConvention);
		// 常规
		{
			CLabel lblNewLabel = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel.setBounds(16, 28, 76, 23);
			lblNewLabel.setText(Lang.getString("FScene.TableItemName.text"));

			// 联动名称
			nameText = new Text(compositeConvention, SWT.BORDER);
			nameText.setBounds(100, 28, 433, 23);
			nameText.setTextLimit(32);
			nameText.addModifyListener(modifyListener);
			nameText.addFocusListener(focusAdapter);

			CLabel lblNewLabel_1 = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel_1.setBounds(16, 92, 76, 23);
			lblNewLabel_1.setText(Lang.getString("FScene.TableItemDescription.text"));
			// 联动描述
			remarkText = new Text(compositeConvention, SWT.BORDER | SWT.WRAP);
			remarkText.setBounds(100, 87, 433, 78);
			remarkText.setTextLimit(128);

			// 取消按钮
			btnCancel = new Button(shell, SWT.NONE);
			btnCancel.setBounds(493, 365, 80, 27);
			btnCancel.setText(Lang.getString("Cancel.text"));
			btnCancel.addSelectionListener(btnCancelSelectionAdapter);

			// 确定按钮
			btnAccept = new Button(shell, SWT.NONE);
			btnAccept.setBounds(405, 365, 80, 27);
			btnAccept.setText(Lang.getString("Ensure.text"));
			btnAccept.addSelectionListener(btnAcceptSelectionAdapter);

			TabItem tabItem = new TabItem(tabFolder, 0);
			tabItem.setText(Lang.getString("FLinkageRecord.tabItem.text"));

			compositeOpen = new Composite(tabFolder, SWT.NONE);
			tabItem.setControl(compositeOpen);

			// 添加设备按钮
			buttonAdd = new Button(compositeOpen, SWT.NONE);
			buttonAdd.setText(Lang.getString("FScene2.btnAddWrite.text"));
			buttonAdd.setBounds(253, 84, 41, 23);
			buttonAdd.addSelectionListener(btnAddOpenSelectionAdapter);

			// 移除设备按钮
			buttonRemove = new Button(compositeOpen, SWT.NONE);
			buttonRemove.setText(Lang.getString("FScene2.button.text"));
			buttonRemove.setBounds(253, 130, 41, 23);
			buttonRemove.addSelectionListener(btnRemoveOpenSelectionAdapter);

			// 修改feature按钮
			buttonModify = new Button(compositeOpen, SWT.NONE);
			buttonModify.setText(Lang.getString("FScene2.buttonModify.text")  );
			buttonModify.setBounds(253, 161, 41, 23);
			buttonModify.addSelectionListener(btnModifyOpenSelectionAdapter);

			// 显示设备table
			table = new Table(compositeOpen, SWT.BORDER | SWT.FULL_SELECTION);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setBounds(10, 20, 238, 221);

			// 开启联动设备table
			tableOpen = new Table(compositeOpen, SWT.BORDER | SWT.FULL_SELECTION);
			tableOpen.setBounds(302, 20, 307, 221);
			tableOpen.setLinesVisible(true);
			tableOpen.setHeaderVisible(true);

			tableColumn_2 = new TableColumn(table, SWT.NONE);
			tableColumn_2.setWidth(115);
			tableColumn_2.setText(Lang.getString("FScene.TableItemName.text"));

			tableColumn_3 = new TableColumn(table, SWT.NONE);
			tableColumn_3.setWidth(118);
			tableColumn_3.setText(Lang.getString("FScene.TableItemArea"));

			devicesList = new ArrayList<>(servo.getCanControlDevices());
			for (Device device : devicesList) {
				item = new TableItem(table, SWT.NONE);
				item.setText(0, device.getName());
				item.setImage(0, Resource.getImage(FMain.class, String.format("/com/kiy/resources/device_%s_none.png", Tool.toLowerCase(device.getKind()))));
				item.setText(1, device.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : device.getZone().getName());
				item.setData(device);
			}

			tableColumn = new TableColumn(tableOpen, SWT.NONE);
			tableColumn.setWidth(79);
			tableColumn.setText(Lang.getString("FScene.TableItemName.text"));

			// 特性名称
			tblclmnFea = new TableColumn(tableOpen, SWT.NONE);
			tblclmnFea.setWidth(72);
			tblclmnFea.setText(Lang.getString("FScene2.tblclmnFea.text"));

			// 开关状态
			tableColumn2 = new TableColumn(tableOpen, SWT.NONE);
			tableColumn2.setWidth(89);
			tableColumn2.setText(Lang.getString("FScene2.tblclmnStatus.text"));
			
			//延时启动
			tableColumn3 = new TableColumn(tableOpen, SWT.NONE);
			tableColumn3.setWidth(89);
			tableColumn3.setText("延时启动");
			make();
		}
	}

	private void make() {
		if (linkage != null) {
			nameText.setText(linkage.getName());
			remarkText.setText(linkage.getRemark());
			// 联动集合
			if (linkage.getLinkageDevice() != null) {
				// 表格显示
				linkageDevices.addAll(linkage.getLinkageDevice());
				showTable();
			}
		}
	}

	/**
	 * 联动设备表格显示
	 */
	public void showTable() {
		tableOpen.removeAll();
	//	LinkageDevice[] devices = sortLinkDeviceByPriority(linkageDevices);
		for (LinkageDevice linkageDevice : linkageDevices) {
			Device device = servo.getDevice(linkageDevice.getDeviceId());
			Device d = device;
			Feature feature = d.getFeature(linkageDevice.getFeatureIndex());
			feature.setValue(linkageDevice.getFeatureValue());
			TableItem tableItem = new TableItem(tableOpen, SWT.NONE);
			tableItem.setText(0, device.getName());
			tableItem.setText(1, feature.getName());
			tableItem.setText(2, feature.getText());
			tableItem.setText(3, linkageDevice.getSecs() + "s");
			tableItem.setData(linkageDevice);
		}
	}

	/**
	 * 设备联动排序（优先级 从高到低 值越小优先级越高）
	 * 
	 * @return
	 */
//	private LinkageDevice[] sortLinkDeviceByPriority(List<LinkageDevice> linkageDevices) {
/*	private LinkageDevice[] sortLinkDeviceByPriority(LinkedHashSet<LinkageDevice> linkageDevices) {
		LinkageDevice[] devices = new LinkageDevice[linkageDevices.size()];
		Iterator<LinkageDevice> iterator = linkageDevices.iterator();
		for (int i = 0; i < devices.length; i++) {
			if (iterator.hasNext()) {
				devices[i] = iterator.next();
			}
		}
		//冒泡排序
		for (int i = 0; i < devices.length-1; i++) {
			for (int j = 0; j < devices.length-i-1; j++) {
				if(devices[j].getPriority()>devices[j+1].getPriority()){
					LinkageDevice device = devices[j];
					devices[j]=devices[i+1];
					devices[j+1]=device;
				}
			}
		}
		//打印排序后结果
		for (LinkageDevice linkageDevice : devices) {
			System.out.println("排序后联动设备优先级："+linkageDevice.getPriority());
		}
		return devices;
	}*/

	/*
	 * TODO 添加验证
	 */
	private FocusAdapter focusAdapter = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent arg0) {
			verify();
		}
	};

	private ModifyListener modifyListener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent arg0) {
			verify();
		}
	};

	private ShellAdapter shellAdapter = new ShellAdapter() {
		@Override
		public void shellClosed(ShellEvent arg0) {
			closing = true;
			close();
		}
	};

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}

	/**
	 * 验证输入
	 */
	private void verify() {
		btnAccept.setEnabled(false);
		labelText.setVisible(true);

		if (Tool.isEmpty(nameText.getText())) {
			labelText.setText(Lang.getString("FScene.verifynameText.text"));
			return;
		} else {
			labelText.setText(Tool.EMPTY);
			labelText.setVisible(false);
			btnAccept.setEnabled(true);
		}
	}

	/**
	 * 添加联动设备
	 */
	private SelectionAdapter btnAddOpenSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {

			TableItem[] selection = table.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.add.text"), Lang.getString("FScene.addRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof Device) {
				Device device = (Device) data;
				FLinkageSelectDeviceFeature f = new FLinkageSelectDeviceFeature(shell);
				LinkageDevice linkageDevice = f.open(device);
				if (linkageDevice != null&&!linkageDevices.contains(linkageDevice)) {
					linkageDevices.add(linkageDevice);
				}
				
				showTable();
			}
		}
	};

	/**
	 * 移除联动设备
	 */
	private SelectionAdapter btnRemoveOpenSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			TableItem[] selection = tableOpen.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.del.text"), Lang.getString("FScene.delRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof LinkageDevice) {
				linkageDevices.remove(data);
				showTable();
			}
		}
	};

	/**
	 * 修改开启联动feature
	 */
	private SelectionAdapter btnModifyOpenSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {

			TableItem[] selection = tableOpen.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.modify.text"), Lang.getString("FScene.modifyRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof LinkageDevice) {
				LinkageDevice linkageDevice = (LinkageDevice) data;
				FLinkageSelectDeviceFeature f = new FLinkageSelectDeviceFeature(shell);
				LinkageDevice result = f.open(servo.getDevice(linkageDevice.getDeviceId()));
				if (result != null) {
					linkageDevice.setId(result.getId());
					linkageDevice.setFeatureIndex(result.getFeatureIndex());
					linkageDevice.setFeatureValue(result.getFeatureValue());
					linkageDevice.setSecs(result.getSecs());
				}
				showTable();
			}
		}
	};

	/*
	 * 确认
	 */
	private void accept() {
		if (linkage == null) {
			linkage = new Linkage();
			linkage.newId();
			linkage.setCreated(new Date());
			// 创建默认生效
			linkage.setEnable(true);
			// 名称
			linkage.setName(nameText.getText());
			// 描述
			linkage.setRemark(remarkText.getText());

			/**
			 * 联动
			 */
			if (tableOpen.getItemCount() > 0) {
				TableItem[] items = tableOpen.getItems();
				LinkageDevice linkageDevice;
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof LinkageDevice) {
						linkageDevice = (LinkageDevice) data;
						linkage.addLinkageDevice(linkageDevice);
					}
				}
			}

			Message.Builder msg = Message.newBuilder();
			msg.setUserId(servo.getUser().getId());
			msg.setKey(CtrClient.getKey());

			CreateLinkage.Builder createLinkage = CreateLinkage.newBuilder();
			MLinkage.Builder mBuilder = createLinkage.getItemBuilder();
			mBuilder.setId(linkage.getId());
			mBuilder.setName(linkage.getName());
			mBuilder.setRemark(linkage.getRemark());
			mBuilder.setEnable(linkage.isEnable());
			for (int i = 0; i < linkage.getLinkageDevice().size(); i++) {
				LinkageDevice linkageDevice = linkage.getLinkageDevice().get(i);
				MLinkageDevice.Builder mlBuilder = MLinkageDevice.newBuilder();
				mlBuilder.setId(UUID.randomUUID().toString());
				mlBuilder.setDeviceId(linkageDevice.getDeviceId());
				mlBuilder.setLinkageId(linkage.getId());
				// 设置优先级 从1开始 1为主控设备
				mlBuilder.setPriority(i + 1);
				mlBuilder.setFeatureIndex(linkageDevice.getFeatureIndex());
				mlBuilder.setFeatureValue(linkageDevice.getFeatureValue());
				mlBuilder.setSecs(linkageDevice.getSecs());
				mBuilder.addLinkageDevice(mlBuilder.build());
			}

			createLinkage.setItem(mBuilder);
			msg.setCreateLinkage(createLinkage);
			CtrClient client = servo.getClient();
			client.send(msg.build());

		} else {
			// 名称
			linkage.setName(nameText.getText());
			// 描述
			linkage.setRemark(remarkText.getText());

			/**
			 * 联动
			 */
			if (tableOpen.getItemCount() > 0) {
				TableItem[] items = tableOpen.getItems();
				LinkageDevice linkageDevice;
				linkage.removeAllLinkageDevice();
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof LinkageDevice) {
						linkageDevice = (LinkageDevice) data;
						linkage.addLinkageDevice(linkageDevice);
					}
				}
			}

			Message.Builder msg = Message.newBuilder();
			msg.setUserId(servo.getUser().getId());
			msg.setKey(CtrClient.getKey());

			UpdateLinkage.Builder updateLinkage = UpdateLinkage.newBuilder();
			MLinkage.Builder mBuilder = updateLinkage.getItemBuilder();
			mBuilder.setId(linkage.getId());
			mBuilder.setName(linkage.getName());
			mBuilder.setRemark(linkage.getRemark());
			mBuilder.setEnable(linkage.isEnable());
			for (int i = 0; i < linkage.getLinkageDevice().size(); i++) {
				LinkageDevice linkageDevice = linkage.getLinkageDevice().get(i);
				// 设置优先级 从1开始 1为主控设备
				linkageDevice.setPriority(i + 1);
				MLinkageDevice.Builder mlBuilder = MLinkageDevice.newBuilder();
				mlBuilder.setId(UUID.randomUUID().toString());
				mlBuilder.setDeviceId(linkageDevice.getDeviceId());
				mlBuilder.setLinkageId(linkage.getId());
				mlBuilder.setPriority(linkageDevice.getPriority());
				mlBuilder.setFeatureIndex(linkageDevice.getFeatureIndex());
				mlBuilder.setFeatureValue(linkageDevice.getFeatureValue());
				mlBuilder.setSecs(linkageDevice.getSecs());
				mBuilder.addLinkageDevice(mlBuilder.build());
			}
			updateLinkage.setItem(mBuilder);
			msg.setUpdateLinkage(updateLinkage);
			CtrClient client = servo.getClient();
			client.send(msg.build());
		}
	}

	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			isUpdate = true;
			accept();
			close();
		}
	};

	private void cancel() {
		linkage = null;
		close();
	}

	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			cancel();
		}
	};

}

package com.kiy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.kiy.common.Scene;
import com.kiy.common.SceneDevice;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.protocol.Messages.CreateScene;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.UpdateScene;
import com.kiy.protocol.Units.MScene;
import com.kiy.protocol.Units.MSceneDevice;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

public class FScene extends Dialog {
	private Shell shell;

	private CLabel labelText; // 输入验证
	private Text nameText;// 场景名称
	private Text remarkText; // 场景描述
	private Composite compositeOpen; // 开启场景面板
	private Composite compositeClose; // 关闭场景面板
	private Button buttonAdd; // 添加按钮
	private Button buttonAdd2; // 添加按钮2
	private Button buttonRemove; // 移除设备按钮
	private Button buttonRemove2; // 移除设备按钮2
	private Button buttonModify; // 修改feature按钮
	private Button buttonModify2; // 修改feature按钮2
	private Button btnAccept; // 确认按钮
	private Button btnCancel; // 取消按钮
	private Table tableOpen; // 开启场景设备table
	private Table tableClose; // 关闭场景设备table
	private Table table; // 显示设备table
	private Table table2; // 显示设备table2
	private TableColumn tableColumn;
	private TableColumn tableColumn2;
	private TableColumn tableColumn3;
	private TableColumn tableColumn4;
	private TableColumn tblclmnFea;
	private TableColumn tblclmnFea2;
	private TableColumn tableColumn_2;
	private TableColumn tableColumn_2_2;
	private TableColumn tableColumn_3;
	private TableColumn tableColumn_3_2;
	private TableItem item;

	public List<Device> openDevicesTemp = new ArrayList<>();
	public List<Device> closeDevicesTemp = new ArrayList<>();
	private List<Device> devicesList;
	private Servo servo;
	private Scene scene;
	private Set<SceneDevice> sceneOpenDevicesSet = new HashSet<>();
	private Set<SceneDevice> sceneCloseDevicesSet = new HashSet<>();

	public boolean isUpdate = false;
	private boolean closing;

	public FScene(Shell arg0) {
		super(arg0);
	}

	public Scene open(Servo s, Scene e) {
		if (s == null) {
			throw new NullPointerException("Servo can not be null!");
		}
		servo = s;
		scene = e;

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
		return scene;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setSize(595, 429);
		shell.setText(Lang.getString("FScene.ShellName.text"));
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
		tabFolder.setBounds(16, 70, 557, 287);

		TabItem tabConvention = new TabItem(tabFolder, SWT.NONE);
		tabConvention.setText(Lang.getString("FScene.TableItemConventional.text"));

		Composite compositeConvention = new Composite(tabFolder, SWT.NONE);
		tabConvention.setControl(compositeConvention);
		// 常规
		{
			CLabel lblNewLabel = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel.setBounds(16, 28, 76, 23);
			lblNewLabel.setText(Lang.getString("FScene.TableItemName.text"));

			// 场景名称
			nameText = new Text(compositeConvention, SWT.BORDER);
			nameText.setBounds(100, 28, 433, 23);
			nameText.setTextLimit(32);
			nameText.addModifyListener(modifyListener);
			nameText.addFocusListener(focusAdapter);

			CLabel lblNewLabel_1 = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel_1.setBounds(16, 92, 76, 23);
			lblNewLabel_1.setText(Lang.getString("FScene.TableItemDescription.text"));
			// 场景描述
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
			tabItem.setText(Lang.getString("FScene.tabItem.text"));

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
			buttonModify.setText(Lang.getString("FScene2.buttonModify.text")); 
			buttonModify.setBounds(253, 161, 41, 23);
			buttonModify.addSelectionListener(btnModifyOpenSelectionAdapter);

			// 显示设备table
			table = new Table(compositeOpen, SWT.BORDER | SWT.FULL_SELECTION);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			table.setBounds(10, 20, 238, 221);

			// 开启场景设备table
			tableOpen = new Table(compositeOpen, SWT.BORDER | SWT.FULL_SELECTION);
			tableOpen.setBounds(302, 20, 247, 221);
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

			//特性名称
			tblclmnFea = new TableColumn(tableOpen, SWT.NONE);
			tblclmnFea.setWidth(72);
			tblclmnFea.setText(Lang.getString("FScene2.tblclmnFea.text")); 

			//开关状态
			tableColumn2 = new TableColumn(tableOpen, SWT.NONE);
			tableColumn2.setWidth(89);
			tableColumn2.setText(Lang.getString("FScene2.tblclmnStatus.text"));

			TabItem tabItem1 = new TabItem(tabFolder, 0);
			tabItem1.setText(Lang.getString("FScene.tabItem.text2"));

			// 关闭场景面板
			compositeClose = new Composite(tabFolder, SWT.NONE);
			tabItem1.setControl(compositeClose);

			// 添加设备按钮2
			buttonAdd2 = new Button(compositeClose, SWT.NONE);
			buttonAdd2.setText(Lang.getString("FScene2.btnAddWrite.text")); 
			buttonAdd2.setBounds(253, 84, 41, 23);
			buttonAdd2.addSelectionListener(btnAddCloseSelectionAdapter);

			// 移除设备按钮2
			buttonRemove2 = new Button(compositeClose, SWT.NONE);
			buttonRemove2.setText(Lang.getString("FScene2.button.text")); 
			buttonRemove2.setBounds(253, 130, 41, 23);
			buttonRemove2.addSelectionListener(btnRemoveCloseSelectionAdapter);

			// 修改feature按钮2
			buttonModify2 = new Button(compositeClose, SWT.NONE);
			buttonModify2.setText(Lang.getString("FScene2.buttonModify.text")); 
			buttonModify2.setBounds(253, 161, 41, 23);
			buttonModify2.addSelectionListener(btnModifyCloseSelectionAdapter);

			// 显示设备table2
			table2 = new Table(compositeClose, SWT.BORDER | SWT.FULL_SELECTION);
			table2.setLinesVisible(true);
			table2.setHeaderVisible(true);
			table2.setBounds(10, 20, 238, 221);

			// 开启场景设备table
			tableClose = new Table(compositeClose, SWT.BORDER | SWT.FULL_SELECTION);
			tableClose.setBounds(302, 20, 247, 221);
			tableClose.setLinesVisible(true);
			tableClose.setHeaderVisible(true);

			tableColumn_2_2 = new TableColumn(table2, SWT.NONE);
			tableColumn_2_2.setWidth(115);
			tableColumn_2_2.setText(Lang.getString("FScene.TableItemName.text"));

			tableColumn_3_2 = new TableColumn(table2, SWT.NONE);
			tableColumn_3_2.setWidth(118);
			tableColumn_3_2.setText(Lang.getString("FScene.TableItemArea"));

			devicesList = new ArrayList<>(servo.getCanControlDevices());
			for (Device device : devicesList) {
				item = new TableItem(table2, SWT.NONE);
				item.setText(0, device.getName());
				item.setImage(0, Resource.getImage(FMain.class, String.format("/com/kiy/resources/device_%s_none.png", Tool.toLowerCase(device.getKind()))));
				item.setText(1, device.getZone() == null ? Lang.getString("FAddDeviceToMap.NoZone.text") : device.getZone().getName());
				item.setData(device);
			}

			tableColumn3 = new TableColumn(tableClose, SWT.NONE);
			tableColumn3.setWidth(79);
			tableColumn3.setText(Lang.getString("FScene.TableItemName.text"));

			tblclmnFea2 = new TableColumn(tableClose, SWT.NONE);
			tblclmnFea2.setWidth(72);
			tblclmnFea2.setText(Lang.getString("FScene2.tblclmnFea.text")); 

			tableColumn4 = new TableColumn(tableClose, SWT.NONE);
			tableColumn4.setWidth(89);
			tableColumn4.setText(Lang.getString("FScene2.tblclmnStatus.text"));

			make();
		}
	}

	private void make() {
		if (scene != null) {
			nameText.setText(scene.getName());
			remarkText.setText(scene.getRemark());
			// 开启场景
			if (scene.getOpenDevices() != null) {
				// 1.表格显示
				List<SceneDevice> openDevices = scene.getOpenDevices();
				for (SceneDevice sceneDevice : openDevices) {
					sceneOpenDevicesSet.add(sceneDevice);
					showOpenTable();
				}
			}

			// 关闭场景
			if (scene.getOffDevices() != null) {
				// 1.表格显示
				List<SceneDevice> closeDevices = scene.getOffDevices();
				for (SceneDevice sceneDevice : closeDevices) {
					sceneCloseDevicesSet.add(sceneDevice);
					showCloseTable();
				}
			}

		}
		// 添加设备
		if (sceneOpenDevicesSet.size() >= 0) {
			tableOpen.removeAll();
			showOpenTable();
		}
	}

	/**
	 * 开启场景设备表格显示
	 */
	public void showOpenTable() {
		tableOpen.removeAll();
		for (SceneDevice sceneDevice : sceneOpenDevicesSet) {
			Device device = servo.getDevice(sceneDevice.getDeviceId());
			Device d = device;
			Feature feature = d.getFeature(sceneDevice.getFeatureIndex());
			feature.setValue(sceneDevice.getFeatureValue());
			TableItem tableItem = new TableItem(tableOpen, SWT.NONE);
			tableItem.setText(0, device.getName());
			tableItem.setText(1, feature.getName());
			tableItem.setText(2, feature.getText()+feature.getName());
			tableItem.setData(sceneDevice);
		}
	}

	/**
	 * 关闭场景设备表格显示
	 */
	public void showCloseTable() {
		tableClose.removeAll();
		for (SceneDevice sceneDevice : sceneCloseDevicesSet) {
					Device device = servo.getDevice(sceneDevice.getDeviceId());
					Device d = device;
					Feature feature = d.getFeature(sceneDevice.getFeatureIndex());
					feature.setValue(sceneDevice.getFeatureValue());
					TableItem tableItem = new TableItem(tableClose, SWT.NONE);
					tableItem.setText(0, device.getName());
					tableItem.setText(1, feature.getName());
					tableItem.setText(2, feature.getText());
					tableItem.setData(sceneDevice);
			}
	}

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
	 * 添加开始场景设备
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
				FSelectDeviceFeature f = new FSelectDeviceFeature(shell);
				SceneDevice sceneDevice = f.open(device);
				if (sceneDevice != null) {
					sceneDevice.setSwitchStatus(true);
					sceneOpenDevicesSet.add(sceneDevice);
				}
				showOpenTable();
			}
		}
	};

	/**
	 * 添加关闭场景设备
	 */
	private SelectionAdapter btnAddCloseSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			TableItem[] selection = table2.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.add.text"), Lang.getString("FScene.addRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof Device) {
				Device device = (Device) data;
				FSelectDeviceFeature f = new FSelectDeviceFeature(shell);
				SceneDevice sceneDevice = f.open(device);
				if (sceneDevice != null) {
					sceneCloseDevicesSet.add(sceneDevice);
				}
				showCloseTable();
			}
		}
	};

	/**
	 * 移除开启场景设备
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
			if (data != null && data instanceof SceneDevice) {
				sceneOpenDevicesSet.remove(data);
				showOpenTable();
			}
		}
	};

	/**
	 * 移除关闭场景设备
	 */
	private SelectionAdapter btnRemoveCloseSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			TableItem[] selection = tableClose.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.del.text"), Lang.getString("FScene.delRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof SceneDevice) {
				sceneCloseDevicesSet.remove(data);
				showCloseTable();
			}
		}
	};

	/**
	 * 修改开启场景feature
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
			if (data != null && data instanceof SceneDevice) {
				SceneDevice sceneDevice = (SceneDevice) data;
				FSelectDeviceFeature f = new FSelectDeviceFeature(shell);
				SceneDevice sceneDevice2 = f.open(servo.getDevice(sceneDevice.getDeviceId()));
				if (sceneDevice2!=null) {
					sceneDevice.setFeatureIndex(sceneDevice2.getFeatureIndex());
					sceneDevice.setFeatureValue(sceneDevice2.getFeatureValue());
					sceneOpenDevicesSet.remove(sceneDevice);
					sceneOpenDevicesSet.add(sceneDevice2);
				}
				showOpenTable();
			}
		}
	};

	/**
	 * 修改关闭场景feature
	 */
	private SelectionAdapter btnModifyCloseSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {

			TableItem[] selection = tableClose.getSelection();
			if (selection == null || selection.length <= 0) {
				F.mbInformation(shell, Lang.getString("FScene.modify.text"), Lang.getString("FScene.modifyRemind.text"));
				return;
			}
			Object data = selection[0].getData();
			if (data != null && data instanceof SceneDevice) {
				SceneDevice sceneDevice = (SceneDevice) data;
				FSelectDeviceFeature f = new FSelectDeviceFeature(shell);
				SceneDevice sceneDevice2 = f.open(servo.getDevice(sceneDevice.getDeviceId()));
				if (sceneDevice2!=null) {
					sceneDevice.setFeatureIndex(sceneDevice2.getFeatureIndex());
					sceneDevice.setFeatureValue(sceneDevice2.getFeatureValue());
					sceneCloseDevicesSet.remove(sceneDevice);
					sceneCloseDevicesSet.add(sceneDevice2);
				}
				showCloseTable();
			}
		}
	};

	/*
	 * 确认
	 */
	private void accept() {
		if (scene == null) {
			scene = new Scene();
			scene.newId();
			scene.setSwitchStatu(false);
			scene.setCreated(new Date());
			// 名称
			scene.setName(nameText.getText());
			// 描述
			scene.setRemark(remarkText.getText());

			/**
			 * 开启场景
			 */
			if (tableOpen.getItemCount() > 0) {
				TableItem[] items = tableOpen.getItems();
				SceneDevice scenedevice;
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof SceneDevice) {
						scenedevice = (SceneDevice) data;
						scene.addOpenSceneDevice(scenedevice);
					}
				}
			}

			/**
			 * 关闭场景
			 */
			if (tableClose.getItemCount() > 0) {
				TableItem[] items = tableClose.getItems();
				SceneDevice scenedevice;
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof SceneDevice) {
						scenedevice = (SceneDevice) data;
						scene.addOffSceneDevice(scenedevice);
					}
				}
			}
			Message.Builder msg = Message.newBuilder();
			msg.setUserId(servo.getUser().getId());
			msg.setKey(CtrClient.getKey());
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
		} else {
			// 名称
			scene.setName(nameText.getText());
			// 描述
			scene.setRemark(remarkText.getText());
			scene.setUpdated(new Date());

			/**
			 * 开启场景
			 */
			if (tableOpen.getItemCount() >= 0) {
				TableItem[] items = tableOpen.getItems();
				SceneDevice scenedevice;
				scene.removeAllOpenSceneDevice();
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof SceneDevice) {
						scenedevice = (SceneDevice) data;
						scene.addOpenSceneDevice(scenedevice);
					}
				}
			}

			/**
			 * 关闭场景
			 */
			if (tableClose.getItemCount() >= 0) {
				TableItem[] items = tableClose.getItems();
				SceneDevice scenedevice;
				scene.removeAllOffSceneDevice();
				for (int i = 0; i < items.length; i++) {
					Object data = items[i].getData();
					if (data != null && data instanceof SceneDevice) {
						scenedevice = (SceneDevice) data;
						scene.addOffSceneDevice(scenedevice);
					}
				}
			}
			Message.Builder msg = Message.newBuilder();
			msg.setUserId(servo.getUser().getId());
			msg.setKey(CtrClient.getKey());
			UpdateScene.Builder updateScene = UpdateScene.newBuilder();
			MScene.Builder mScene = updateScene.getItemBuilder();
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
			updateScene.setItem(mScene);
			msg.setUpdateScene(updateScene).build();
			System.err.println(msg.toString());
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
		scene = null;
		close();
	}

	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			cancel();
		}
	};

}

package com.kiy.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.Role;
import com.kiy.common.Servo;
import com.kiy.common.Task;
import com.kiy.common.Tool;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;
import com.kiy.common.Types.Day;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Month;
import com.kiy.common.Types.Week;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FTask extends Dialog {
	private Shell shell;
	private Servo servo;
	private Task task;

	private CLabel labelText; // 输入验证
	private Text nameText;// 计划名称
	private Text remarkText; // 计划描述
	private Group groupOneTime;// 一次
	private Group groupDay;// 每天
	private Group groupWeek;// 每周
	private Group groupMonth;// 每月
	private Button buttonOneTime; // 一次button
	private Button buttonPerDay; // 每天button
	private Button buttonPerWeek;// 每周button
	private Button buttonPerMonth;// 每月button
	private DateTime startDate; // 执行开始日期
	private DateTime endDate; // 执行结束日期
	private DateTime startTime; // 执行开始时间
	private DateTime endTime; // 执行结束时间
	private Spinner spinnerInterval; // 执行间隔时间
	private Text dayIntervalText; // 每天间隔
	private Text weekIntervalText; // 每周间隔
	private Button btnSunday; // 星期日
	private Button btnMonday; // 星期一
	private Button btnTuesday; // 星期二
	private Button btuWednesday; // 星期三
	private Button btuThursday; // 星期四
	private Button btuFriday; // 星期五
	private Button btuSaturday; // 星期六
	private Text textSelectMonth; // 选择的月
	private Button btnSelectMonth; // 选择月按钮
	private Text textSelectDay; // 选择天
	private Button btnSelectDay; // 选择天按钮
	private CCombo comboDetectRole; // 通知报警的角色

	private List<Role> waringRole = new ArrayList<>(); // 保存的告警角色
	private Text textLimitLower;// 阈值下限
	private Text textLimitUpper;// 阈值上限
	private Table tableRead;// 检测设备Table
	private Table tableWrite;// 控制设备Table
	private CCombo comboReadFeature;// 检测设备Feature
	private Button btnAddRead;// 添加检测设备Button·
	private CCombo comboWriteFeature;// 控制设备Feature
	private CLabel lblFeedLowerPer;// 下限动作百分比
	private Scale scaleFeedLower;// 下限动作
	private CLabel lblFeedPer;// 默认动作百分比
	private Scale scaleFeed;// 默认动作
	private CLabel lblFeedUpperPer;// 上限动作百分比
	private Scale scaleFeedUpper;// 上限动作
	private Button btnAddWrite;// 添加控制设备

	private List<Month> monthValue = new ArrayList<>();// 聚合月
	private List<Day> dayValue = new ArrayList<Day>();// 聚合日
	private List<Object> weekValue = new ArrayList<>();// 聚合周
	public List<Device> readDevicesTemp = new ArrayList<>();
	public List<Device> writeDevicesTemp = new ArrayList<>();

	public boolean isUpdate = false;
	private boolean closing;

	public FTask(Shell arg0) {
		super(arg0);
	}

	public Task open(Servo s, Task t) {
		if (s == null) {
			throw new NullPointerException("Servo can not be null!");
		}

		servo = s;
		task = t;

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
		return task;
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setSize(595, 429);
		shell.setText(Lang.getString("FTask.ShellName.text"));
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
		tabConvention.setText(Lang.getString("FTask.TableItemConventional.text"));

		Composite compositeConvention = new Composite(tabFolder, SWT.NONE);
		tabConvention.setControl(compositeConvention);
		// 常规
		{
			CLabel lblNewLabel = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel.setBounds(16, 28, 76, 23);
			lblNewLabel.setText(Lang.getString("FTask.TableItemName.text"));

			// 任务名称
			nameText = new Text(compositeConvention, SWT.BORDER);
			nameText.setBounds(100, 28, 433, 23);
			nameText.setTextLimit(32);
			nameText.addModifyListener(modifyListener);
			nameText.addFocusListener(focusAdapter);

			CLabel lblNewLabel_1 = new CLabel(compositeConvention, SWT.NONE);
			lblNewLabel_1.setBounds(16, 59, 76, 23);
			lblNewLabel_1.setText(Lang.getString("FTask.TableItemDescription.text"));
			// 任务描述
			remarkText = new Text(compositeConvention, SWT.BORDER | SWT.WRAP);
			remarkText.setBounds(100, 59, 433, 47);
			remarkText.setTextLimit(128);
			remarkText.addModifyListener(modifyListener);
			remarkText.addFocusListener(focusAdapter);
		}

		CLabel label_4 = new CLabel(compositeConvention, SWT.NONE);
		label_4.setText(Lang.getString("FTask.lblNewLabel_13"));
		label_4.setBounds(16, 117, 78, 23);

		// 告警角色
		comboDetectRole = new CCombo(compositeConvention, SWT.BORDER | SWT.READ_ONLY);
		comboDetectRole.setBounds(100, 117, 160, 23);
		waringRole.clear();
		waringRole.addAll(servo.getRoles());
		for (int i = 0; i < servo.getRoles().size(); i++) {
			comboDetectRole.add(waringRole.get(i).getName());
			comboDetectRole.setData(String.valueOf(i), waringRole.get(i));
		}
		comboDetectRole.addFocusListener(focusAdapter);

		TabItem tabTrigger = new TabItem(tabFolder, SWT.NONE);
		tabTrigger.setText(Lang.getString("FTask.TableItemTrigger.text"));

		Composite compositeTrigger = new Composite(tabFolder, SWT.NONE);
		tabTrigger.setControl(compositeTrigger);
		// 触发器
		{
			// 一次按钮
			buttonOneTime = new Button(compositeTrigger, SWT.RADIO);
			buttonOneTime.setText(Lang.getString("FTask.btnRadioButton.text"));
			buttonOneTime.setBounds(16, 33, 66, 23);
			buttonOneTime.addSelectionListener(buttonOneTimeSelectionAdapter);
			buttonOneTime.addFocusListener(focusAdapter);

			// 每天
			buttonPerDay = new Button(compositeTrigger, SWT.RADIO);
			buttonPerDay.setText(Lang.getString("FTaskNew.button_2.text")); //$NON-NLS-1$
			buttonPerDay.setBounds(16, 64, 66, 23);
			buttonPerDay.addSelectionListener(buttonPerDaySelectionAdapter);
			buttonPerDay.addFocusListener(focusAdapter);

			// 每周
			buttonPerWeek = new Button(compositeTrigger, SWT.RADIO);
			buttonPerWeek.setText(Lang.getString("FTaskNew.buttonPerWeek.text"));
			buttonPerWeek.setBounds(16, 95, 66, 23);
			buttonPerWeek.addSelectionListener(buttonPerWeekSelectionAdapter);
			buttonPerWeek.addFocusListener(focusAdapter);

			// 每月
			buttonPerMonth = new Button(compositeTrigger, SWT.RADIO);
			buttonPerMonth.setText(Lang.getString("FTaskNew.buttonPerMonth.text"));
			buttonPerMonth.setBounds(16, 126, 66, 23);
			buttonPerMonth.addSelectionListener(buttonPerMonthSelectionAdapter);
			buttonPerMonth.addFocusListener(focusAdapter);

			Label lblNewLabel_6 = new Label(compositeTrigger, SWT.SEPARATOR);
			lblNewLabel_6.setBounds(100, 31, 5, 174);

			{
				CLabel lblNewLabel_2 = new CLabel(compositeTrigger, SWT.NONE);
				lblNewLabel_2.setBounds(132, 33, 76, 23);
				lblNewLabel_2.setText(Lang.getString("FTask.Data")); // 执行日期:

				startDate = new DateTime(compositeTrigger, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				startDate.setBounds(216, 33, 110, 23);
				startDate.addFocusListener(focusAdapter);
				startDate.addSelectionListener(verifySelectionAdapter);

				endDate = new DateTime(compositeTrigger, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
				endDate.setBounds(418, 33, 110, 23);
				endDate.addFocusListener(focusAdapter);
				endDate.addSelectionListener(verifySelectionAdapter);

				CLabel lblNewLabel_3 = new CLabel(compositeTrigger, SWT.NONE);
				lblNewLabel_3.setBounds(132, 64, 76, 20);
				lblNewLabel_3.setText(Lang.getString("FTask.Time")); // 执行时间:

				startTime = new DateTime(compositeTrigger, SWT.BORDER | SWT.TIME);
				startTime.setBounds(216, 64, 110, 23);
				startTime.addFocusListener(focusAdapter);
				startTime.addSelectionListener(verifySelectionAdapter);

				endTime = new DateTime(compositeTrigger, SWT.BORDER | SWT.TIME);
				endTime.setBounds(418, 64, 110, 23);
				endTime.addFocusListener(focusAdapter);
				endTime.addSelectionListener(verifySelectionAdapter);

				Label lblNewLabel_4 = new Label(compositeTrigger, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblNewLabel_4.setBounds(334, 33, 76, 23);

				Label lblNewLabel_5 = new Label(compositeTrigger, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblNewLabel_5.setBounds(334, 64, 76, 23);

				CLabel lblNewLabel_7 = new CLabel(compositeTrigger, SWT.NONE);
				lblNewLabel_7.setBounds(16, 223, 66, 23);
				lblNewLabel_7.setText(Lang.getString("FTask.delay")); // 执行间隔:

				spinnerInterval = new Spinner(compositeTrigger, SWT.BORDER);
				spinnerInterval.setBounds(100, 223, 110, 23);
				spinnerInterval.setMaximum(43200);
				spinnerInterval.setTextLimit(5);
				spinnerInterval.addFocusListener(focusAdapter);
			}

			/**
			 * 每次
			 */
			groupOneTime = new Group(compositeTrigger, SWT.NONE);
			groupOneTime.setBounds(132, 92, 396, 113);
			groupOneTime.setVisible(true);

			/**
			 * 每天
			 */
			groupDay = new Group(compositeTrigger, SWT.NONE);
			groupDay.setBounds(132, 92, 396, 113);
			groupDay.setVisible(false);

			{
				Label label = new Label(groupDay, SWT.NONE);
				label.setText(Lang.getString("FTask.LabelEach.text"));
				label.setBounds(16, 22, 15, 17);

				dayIntervalText = new Text(groupDay, SWT.BORDER);
				dayIntervalText.setText("1");
				dayIntervalText.setTextLimit(1);
				dayIntervalText.setBounds(39, 19, 42, 23);
				dayIntervalText.addVerifyListener(new VerifyListener() {
					@Override
					public void verifyText(VerifyEvent arg0) {
						F.numberCheck1(arg0, dayIntervalText);
					}
				});

				Label label_1 = new Label(groupDay, SWT.NONE);
				label_1.setText(Lang.getString("FTask.lblNewLabel_5.text"));
				label_1.setBounds(89, 22, 216, 17);
			}

			/**
			 * 每周
			 */
			groupWeek = new Group(compositeTrigger, SWT.NONE);
			groupWeek.setBounds(132, 92, 396, 113);
			groupWeek.setVisible(false);

			{
				Label label = new Label(groupWeek, SWT.NONE);
				label.setText(Lang.getString("FTask.LabelEach.text"));
				label.setBounds(16, 19, 15, 17);

				weekIntervalText = new Text(groupWeek, SWT.BORDER);
				weekIntervalText.setText("1");
				weekIntervalText.setTextLimit(1);
				weekIntervalText.setBounds(39, 16, 38, 23);
				weekIntervalText.addVerifyListener(new VerifyListener() {
					@Override
					public void verifyText(VerifyEvent arg0) {
						F.numberCheck1(arg0, weekIntervalText);
					}
				});

				Label label_1 = new Label(groupWeek, SWT.NONE);
				label_1.setText(Lang.getString("FTask.lblNewLabel_6.text"));
				label_1.setBounds(85, 19, 183, 17);

				btnSunday = new Button(groupWeek, SWT.CHECK);
				btnSunday.setText(Lang.getString("FTask.BtnSunday.text"));
				btnSunday.setData(Week.SUNDAY);
				btnSunday.setBounds(16, 44, 70, 17);
				btnSunday.addSelectionListener(btnListener);
				btnSunday.addFocusListener(focusAdapter);

				btnMonday = new Button(groupWeek, SWT.CHECK);
				btnMonday.setText(Lang.getString("FTask.BtnMonday.text"));
				btnMonday.setData(Week.MONDAY);
				btnMonday.setBounds(90, 44, 70, 17);
				btnMonday.addSelectionListener(btnListener);
				btnMonday.addFocusListener(focusAdapter);

				btnTuesday = new Button(groupWeek, SWT.CHECK);
				btnTuesday.setText(Lang.getString("FTask.BtnTuesday.text"));
				btnTuesday.setData(Week.TUESDAY);
				btnTuesday.setBounds(164, 44, 70, 17);
				btnTuesday.addSelectionListener(btnListener);
				btnTuesday.addFocusListener(focusAdapter);

				btuWednesday = new Button(groupWeek, SWT.CHECK);
				btuWednesday.setText(Lang.getString("FTask.BtnWednesday.Text"));
				btuWednesday.setData(Week.WEDNESDAY);
				btuWednesday.setBounds(238, 44, 70, 17);
				btuWednesday.addSelectionListener(btnListener);
				btuWednesday.addFocusListener(focusAdapter);

				btuThursday = new Button(groupWeek, SWT.CHECK);
				btuThursday.setText(Lang.getString("FTask.BtnThursday.text"));
				btuThursday.setData(Week.THURSDAY);
				btuThursday.setBounds(16, 69, 70, 17);
				btuThursday.addSelectionListener(btnListener);
				btuThursday.addFocusListener(focusAdapter);

				btuFriday = new Button(groupWeek, SWT.CHECK);
				btuFriday.setText(Lang.getString("FTask.BtnFriday.text"));
				btuFriday.setData(Week.FRIDAY);
				btuFriday.setBounds(90, 69, 70, 17);
				btuFriday.addSelectionListener(btnListener);
				btuFriday.addFocusListener(focusAdapter);

				btuSaturday = new Button(groupWeek, SWT.CHECK);
				btuSaturday.setText(Lang.getString("FTask.BtnSaturday.text"));
				btuSaturday.setData(Week.SATURDAY);
				btuSaturday.setBounds(164, 69, 70, 17);
				btuSaturday.addSelectionListener(btnListener);
				btuSaturday.addFocusListener(focusAdapter);
			}

			/**
			 * 每月
			 */
			groupMonth = new Group(compositeTrigger, SWT.NONE);
			groupMonth.setBounds(132, 92, 396, 113);
			groupMonth.setVisible(false);

			{
				Label label = new Label(groupMonth, SWT.NONE);
				label.setText(Lang.getString("FTask.month"));
				label.setBounds(16, 20, 40, 17);

				textSelectMonth = new Text(groupMonth, SWT.BORDER);
				textSelectMonth.setEditable(false);
				textSelectMonth.setBounds(64, 17, 278, 23);

				btnSelectMonth = new Button(groupMonth, SWT.NONE);
				btnSelectMonth.setText("...");
				btnSelectMonth.setBounds(350, 17, 33, 23);
				btnSelectMonth.addSelectionListener(btnSelectMonthSelectionAdapter);

				Label label_1 = new Label(groupMonth, SWT.NONE);
				label_1.setText(Lang.getString("FTask.day"));
				label_1.setBounds(16, 50, 40, 17);

				textSelectDay = new Text(groupMonth, SWT.BORDER);
				textSelectDay.setEditable(false);
				textSelectDay.setBounds(64, 47, 278, 23);

				btnSelectDay = new Button(groupMonth, SWT.NONE);
				btnSelectDay.setText("...");
				btnSelectDay.setBounds(350, 47, 33, 23);

				CLabel label_2 = new CLabel(compositeTrigger, SWT.NONE);
				label_2.setText(Lang.getString("FTask.second"));
				label_2.setBounds(216, 223, 66, 23);
				btnSelectDay.addSelectionListener(btnSelectDaySelectionAdapter);

			}

		}

		TabItem tabRead = new TabItem(tabFolder, SWT.NONE);
		tabRead.setText("检测");

		Composite compositeRead = new Composite(tabFolder, SWT.NONE);
		tabRead.setControl(compositeRead);

		{
			/**
			 * 检测
			 */
			CLabel label_1 = new CLabel(compositeRead, SWT.NONE);
			label_1.setText("检测设备：");
			label_1.setBounds(16, 28, 67, 23);

			btnAddRead = new Button(compositeRead, SWT.NONE);
			btnAddRead.setBounds(16, 59, 67, 23);
			btnAddRead.setText(Lang.getString("Edit.text"));
			btnAddRead.addSelectionListener(btnAddReadSelectionAdapter);
			btnAddRead.addFocusListener(focusAdapter);

			tableRead = new Table(compositeRead, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
			tableRead.setBounds(91, 28, 242, 221);
			tableRead.setHeaderVisible(true);
			tableRead.setLinesVisible(true);
			tableRead.addFocusListener(focusAdapter);

			TableColumn tblclmnReadName = new TableColumn(tableRead, SWT.NONE);
			tblclmnReadName.setWidth(115);
			tblclmnReadName.setText("名称");

			TableColumn tblclmnReadZone = new TableColumn(tableRead, SWT.NONE);
			tblclmnReadZone.setWidth(115);
			tblclmnReadZone.setText("区域");

			CLabel lblNewLabel_1 = new CLabel(compositeRead, SWT.NONE);
			lblNewLabel_1.setBounds(341, 28, 67, 23);
			lblNewLabel_1.setText("检测项：");

			comboReadFeature = new CCombo(compositeRead, SWT.BORDER);
			comboReadFeature.setBounds(416, 28, 125, 23);
			comboReadFeature.addFocusListener(focusAdapter);

			CLabel label = new CLabel(compositeRead, SWT.NONE);
			label.setText("阈值上限：");
			label.setBounds(341, 59, 67, 23);

			textLimitUpper = new Text(compositeRead, SWT.BORDER);
			textLimitUpper.setBounds(416, 58, 125, 24);
			textLimitUpper.addModifyListener(modifyListener);
			textLimitUpper.addVerifyListener(new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent arg0) {
					F.numberThreeCheck(arg0, textLimitUpper);
				}
			});

			CLabel label_3 = new CLabel(compositeRead, SWT.NONE);
			label_3.setText(Lang.getString("FTaskNew.label_3.text"));
			label_3.setBounds(341, 90, 67, 23);

			textLimitLower = new Text(compositeRead, SWT.BORDER);
			textLimitLower.setBounds(416, 90, 125, 23);
			textLimitLower.addModifyListener(modifyListener);
			textLimitLower.addVerifyListener(new VerifyListener() {
				@Override
				public void verifyText(VerifyEvent arg0) {
					F.numberThreeCheck(arg0, textLimitLower);
				}
			});
		}

		{
			/**
			 * 控制
			 */
			TabItem tabWrite = new TabItem(tabFolder, SWT.NONE);
			tabWrite.setText(Lang.getString("FTaskNew.tbtmNewItem.text_1"));

			Composite compositeWrite = new Composite(tabFolder, SWT.NONE);
			tabWrite.setControl(compositeWrite);

			CLabel label_9 = new CLabel(compositeWrite, SWT.NONE);
			label_9.setText(Lang.getString("FTaskNew.label_9.text"));
			label_9.setBounds(16, 28, 67, 23);

			btnAddWrite = new Button(compositeWrite, SWT.NONE);
			btnAddWrite.setText(Lang.getString("Edit.text"));
			btnAddWrite.setBounds(16, 59, 67, 23);
			btnAddWrite.addSelectionListener(btnAddWriteSelectionAdapter);

			tableWrite = new Table(compositeWrite, SWT.BORDER | SWT.FULL_SELECTION);
			tableWrite.setLinesVisible(true);
			tableWrite.setHeaderVisible(true);
			tableWrite.setBounds(91, 28, 242, 221);

			TableColumn tblclmnWriteName = new TableColumn(tableWrite, SWT.NONE);
			tblclmnWriteName.setWidth(115);
			tblclmnWriteName.setText("名称");

			TableColumn tblclmnWriteZone = new TableColumn(tableWrite, SWT.NONE);
			tblclmnWriteZone.setWidth(115);
			tblclmnWriteZone.setText("区域");

			CLabel label_5 = new CLabel(compositeWrite, SWT.NONE);
			label_5.setText("控制项：");
			label_5.setBounds(341, 28, 67, 23);

			comboWriteFeature = new CCombo(compositeWrite, SWT.BORDER);
			comboWriteFeature.setBounds(416, 28, 125, 23);
			comboWriteFeature.addSelectionListener(comboWriteFeatureSelectionAdater);
			
						CLabel label_8 = new CLabel(compositeWrite, SWT.NONE);
						label_8.setText(Lang.getString("FTaskNew.label_8.text"));
						label_8.setBounds(341, 59, 67, 42);
			
						lblFeedUpperPer = new CLabel(compositeWrite, SWT.NONE);
						lblFeedUpperPer.setText("0%");
						lblFeedUpperPer.setBounds(416, 59, 44, 42);
			
						scaleFeedUpper = new Scale(compositeWrite, SWT.NONE);
						scaleFeedUpper.setBounds(463, 59, 78, 42);
						scaleFeedUpper.setSelection(0);
						scaleFeedUpper.setMinimum(0);
						scaleFeedUpper.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								int maximum = scaleFeedUpper.getMaximum();
								if (maximum == 100) {
									String s = String.valueOf((scaleFeedUpper.getSelection()) + "%");
									lblFeedUpperPer.setText(s);
								} else {
									String s = String.valueOf((scaleFeedUpper.getSelection()) * 100 + "%");
									lblFeedUpperPer.setText(s);
								}
							}
						});
			
						scaleFeedLower = new Scale(compositeWrite, SWT.NONE);
						scaleFeedLower.setBounds(463, 159, 78, 42);
						scaleFeedLower.setSelection(0);
						scaleFeedLower.setMinimum(0);
						scaleFeedLower.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								int maximum = scaleFeedLower.getMaximum();
								if (maximum == 100) {
									String s = String.valueOf((scaleFeedLower.getSelection()) + "%");
									lblFeedLowerPer.setText(s);
								} else {
									String s = String.valueOf((scaleFeedLower.getSelection()) * 100 + "%");
									lblFeedLowerPer.setText(s);
								}
							}
						});
			
						CLabel label_7 = new CLabel(compositeWrite, SWT.NONE);
						label_7.setText(Lang.getString("FTaskNew.label_7.text"));
						label_7.setBounds(341, 109, 67, 42);
			
						lblFeedPer = new CLabel(compositeWrite, SWT.NONE);
						lblFeedPer.setText("0%");
						lblFeedPer.setBounds(416, 109, 44, 42);

			CLabel label_6 = new CLabel(compositeWrite, SWT.NONE);
			label_6.setText("下限动作：");
			label_6.setBounds(341, 159, 67, 42);

			lblFeedLowerPer = new CLabel(compositeWrite, SWT.NONE);
			lblFeedLowerPer.setBounds(416, 159, 44, 42);
			lblFeedLowerPer.setText("0%");

			scaleFeed = new Scale(compositeWrite, SWT.NONE);
			scaleFeed.setBounds(463, 109, 78, 42);
			scaleFeed.setSelection(0);
			scaleFeed.setMinimum(0);
			scaleFeed.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int maximum = scaleFeed.getMaximum();
					if (maximum == 100) {
						String s = String.valueOf((scaleFeed.getSelection()) + "%");
						lblFeedPer.setText(s);
					} else {
						String s = String.valueOf((scaleFeed.getSelection()) * 100 + "%");
						lblFeedPer.setText(s);
					}
				}
			});
		}

		// 取消按钮
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(493, 365, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));
		btnCancel.addSelectionListener(btnCancelSelectionAdapter);

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.setBounds(405, 365, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.addSelectionListener(btnAcceptSelectionAdapter);

		make();
	}

	private void make() {
		if (task == null) {
			buttonOneTime.setSelection(true);
			return;
		}

		// 常规
		{
			nameText.setText(task.getName());
			remarkText.setText(task.getRemark());
			Role role = task.getRole();
			if (role != null) {
				if (waringRole.contains(role)) {
					int indexOfWaringRole = waringRole.indexOf(role);
					comboDetectRole.select(indexOfWaringRole);
				} else {
					waringRole.add(role);
					comboDetectRole.add(role.getName());
					comboDetectRole.setData(String.valueOf(waringRole.size() - 1), role);
					comboDetectRole.select(waringRole.size() - 1);
				}
			}
		}

		// 触发器
		{
			// 面板显示
			Calendar calendarStart = Calendar.getInstance();
			Calendar calendarEnd = Calendar.getInstance();
			// 设置开始时间
			if (task.getStart() != null) {
				calendarStart.setTime(task.getStart());
				startDate.setDate(calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
						calendarStart.get(Calendar.DATE));
				startTime.setTime(calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE),
						calendarStart.get(Calendar.SECOND));
			}
			// 设置结束时间
			if (task.getStop() != null) {
				calendarEnd.setTime(task.getStop());
				endDate.setDate(calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
						calendarEnd.get(Calendar.DATE));
				endTime.setTime(calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE),
						calendarEnd.get(Calendar.SECOND));
			}

			if (task.getMonth() != 0) {
				buttonPerMonth.setSelection(true);
				groupOneTime.setVisible(false);
				groupDay.setVisible(false);
				groupWeek.setVisible(false);
				groupMonth.setVisible(true);
			} else if (task.getWeek() != 0) {
				buttonPerWeek.setSelection(true);
				groupOneTime.setVisible(false);
				groupDay.setVisible(false);
				groupWeek.setVisible(true);
				groupMonth.setVisible(false);
			} else if (task.getInterval() >= 1) {
				buttonPerDay.setSelection(true);
				groupOneTime.setVisible(false);
				groupDay.setVisible(true);
				groupWeek.setVisible(false);
				groupMonth.setVisible(false);
			} else {
				if (calendarStart.get(Calendar.YEAR) == calendarEnd.get(Calendar.YEAR)
						&& calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH)
						&& calendarStart.get(Calendar.DATE) == calendarEnd.get(Calendar.DATE)) {
					// 日期相等为一次
					buttonOneTime.setSelection(true);
					groupOneTime.setVisible(true);
					groupDay.setVisible(false);
					groupWeek.setVisible(false);
					groupMonth.setVisible(false);
				} else {
					// 不一致为每天
					buttonPerDay.setSelection(true);
					groupOneTime.setVisible(false);
					groupDay.setVisible(true);
					groupWeek.setVisible(false);
					groupMonth.setVisible(false);
				}

			}

			// 设置周天间隔
			if (task.getInterval() % 7 == 0 && task.getInterval() != 0) {
				weekIntervalText.setText(String.valueOf((task.getInterval()) / 7 + 1));
			} else if (task.getInterval() != 0) {
				dayIntervalText.setText(String.valueOf(task.getInterval() + 1));
			} else {
				weekIntervalText.setText("1");
				dayIntervalText.setText("1");
			}
			// 设置一天内重复间隔
			spinnerInterval.setSelection(task.getRepeat());
			// 设置月
			StringBuilder sb = new StringBuilder();
			for (Month month : Month.values()) {
				if (task.containsMonth(month)) {
					monthValue.add(month);
					sb.append(Lang.getString("Month." + month.name()) + ",");
				}
			}

			String s = sb.toString();
			int index = s.lastIndexOf(",");
			if (index != -1) {
				s = s.substring(0, index);
			}
			textSelectMonth.setText(s);
			// 设置周
			if (task.containsWeek(Week.MONDAY)) {
				btnMonday.setSelection(true);
			}
			if (task.containsWeek(Week.TUESDAY)) {
				btnTuesday.setSelection(true);
			}
			if (task.containsWeek(Week.WEDNESDAY)) {
				btuWednesday.setSelection(true);
			}
			if (task.containsWeek(Week.THURSDAY)) {
				btuThursday.setSelection(true);
			}
			if (task.containsWeek(Week.FRIDAY)) {
				btuFriday.setSelection(true);
			}
			if (task.containsWeek(Week.SATURDAY)) {
				btuSaturday.setSelection(true);
			}
			if (task.containsWeek(Week.SUNDAY)) {
				btnSunday.setSelection(true);
			}
			// 设置天
			StringBuilder sb2 = new StringBuilder();
			for (Day day : Day.values()) {
				if (task.containsDay(day)) {
					dayValue.add(day);
					sb2.append(Lang.getString("Day." + day.name()) + ",");
				}
			}
			String s2 = sb2.toString();
			int index2 = s2.lastIndexOf(",");
			if (index2 != -1) {
				s2 = s2.substring(0, index2);
			}
			textSelectDay.setText(s2);
		}

		{
			/**
			 * 检测(检测类型与检测项不能更换)
			 */
			// 没有检测设备就不会填数据
			if (task.getReadDevices() != null) {
				// 1.表格显示
				Set<Device> readDevices = task.getReadDevices();
				readDevicesTemp.addAll(readDevices);
				for (Device device : readDevicesTemp) {
					TableItem tb = new TableItem(tableRead, SWT.NONE);
					tb.setText(0, device.getName());
					tb.setText(1, device.getZone() == null ? "" : device.getZone().getName());
					tb.setData(device);
				}
				// 2.检测项
				Feature feature = readDevicesTemp.get(0).getFeature(task.getReadFeature());
				comboReadFeature.add(feature.getName());
				comboReadFeature.select(0);
				comboReadFeature.setData(feature.getName(), feature.INDEX);
			
				// 3.阈值下限（可修改）
				int limitLower = task.getLimitLower();  
				double lower =limitLower/(feature.OMITTED_VALUE*1.0);
				textLimitLower.setText(String.valueOf(lower));
				
				// 4.阈值上限（可修改）
				int limitUpper = task.getLimitUpper();  
				double upper =limitUpper/(feature.OMITTED_VALUE*1.0);
				textLimitUpper.setText(String.valueOf(upper));

			}
		}

		{
			/**
			 * 控制
			 */
			// 没有控制设备就不回填数据
			if (task.getWriteDevices() != null) {
				// 1.表格显示
				Set<Device> writeDevices = task.getWriteDevices();
				writeDevicesTemp.addAll(writeDevices);
				for (Device device : writeDevicesTemp) {
					TableItem tb = new TableItem(tableWrite, SWT.NONE);
					tb.setText(0, device.getName());
					tb.setText(1, device.getZone() == null ? "" : device.getZone().getName());
					tb.setData(device);
				}
				// 2.控制项
				Feature feature = writeDevicesTemp.get(0).getFeature(task.getWriteFeature());
				comboWriteFeature.add(feature.getName());
				comboWriteFeature.select(0);
				comboWriteFeature.setData(feature.getName(), feature.INDEX);

				int step = feature.STEP;
				scaleFeed.setMaximum(feature.MAXIMUM);
				scaleFeedLower.setMaximum(feature.MAXIMUM);
				scaleFeedUpper.setMaximum(feature.MAXIMUM);
				if (step > 1) {
					scaleFeed.setPageIncrement(step);
					scaleFeedLower.setPageIncrement(step);
					scaleFeedUpper.setPageIncrement(step);
				} else {
					scaleFeed.setPageIncrement(100);
					scaleFeedLower.setPageIncrement(100);
					scaleFeedUpper.setPageIncrement(100);
				}
				
				// 2.设置默认动作
				scaleFeed.setSelection(task.getFeed());
				// 3.设置上限动作
				scaleFeedUpper.setSelection(task.getFeedUpper());
				// 4.设置下限动作
				scaleFeedLower.setSelection(task.getFeedLower());
//
				int maximum = scaleFeed.getMaximum();
//				if (feature.STEP > 1) {
//					// 2.设置默认动作
//					scaleFeed.setSelection(task.getFeed());
//					// 3.设置上限动作
//					scaleFeedUpper.setSelection(task.getFeedUpper());
//					// 4.设置下限动作
//					scaleFeedLower.setSelection(task.getFeedLower());
//				} else {
//					// 2.设置默认动作
//					scaleFeed.setSelection(task.getFeed() > 50 ? 100 : 0);
//					// 3.设置上限动作
//					scaleFeedUpper.setSelection(task.getFeedUpper() > 50 ? 100 : 0);
//					// 4.设置下限动作
//					scaleFeedLower.setSelection(task.getFeedLower() > 50 ? 100 : 0);
//				}
				if (maximum == 100) {
					lblFeedUpperPer.setText(String.valueOf(task.getFeedUpper() + "%"));
					lblFeedPer.setText(String.valueOf(task.getFeed() + "%"));
					lblFeedLowerPer.setText(String.valueOf(task.getFeedLower() + "%"));
				} else {
					lblFeedUpperPer.setText(String.valueOf(task.getFeedUpper() * 100 + "%"));
					lblFeedPer.setText(String.valueOf(task.getFeed() * 100 + "%"));
					lblFeedLowerPer.setText(String.valueOf(task.getFeedLower() * 100 + "%"));
				}
			}
		}
	}

	private void accept() {
		if (task == null) {
			task = new Task();
			task.newId();
		}

		task.setEnable(true);
		// 常规
		{
			// 名称
			task.setName(nameText.getText());
			// 描述
			task.setRemark(remarkText.getText());
			// 告警角色
			int selectionIndex = comboDetectRole.getSelectionIndex();
			if (selectionIndex != -1) {
				Role role = waringRole.get(selectionIndex);
				task.setRoleId(role.getId());
			} else {
				task.setRoleId(null);
			}
		}

		// 触发器
		{
			// 设置年月日
			int sYear = startDate.getYear();
			int sMonth = startDate.getMonth();
			int sDay = startDate.getDay();
			// 设置时分秒
			int sHours = startTime.getHours();
			int sMinutes = startTime.getMinutes();
			int sSeconds = startTime.getSeconds();
			// 设置年月日
			int eYear = endDate.getYear();
			int eMonth = endDate.getMonth();
			int eDay = endDate.getDay();
			// 设置时分秒
			int eHours = endTime.getHours();
			int eMinutes = endTime.getMinutes();
			int eSeconds = endTime.getSeconds();

			// 开始时间
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(sYear, sMonth, sDay, sHours, sMinutes, sSeconds);
			Date start = startCalendar.getTime();
			task.setStart(start);

			// 结束时间
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.set(eYear, eMonth, eDay, eHours, eMinutes, eSeconds);
			Date end = endCalendar.getTime();
			task.setStop(end);

			// 设置间隔时间
			// 设置间隔时间
			// 判断所取值是否为空
			String dayInterval = dayIntervalText.getText().trim();
			if (dayInterval.isEmpty()) {
				dayInterval = "1";
			}

			String weekInterval = weekIntervalText.getText().trim();
			if (weekInterval.isEmpty()) {
				weekInterval = "1";
			}

			int intervalValue = 0;
			if (!dayInterval.equals("1")) {
				intervalValue = Integer.valueOf(dayInterval) - 1;
			} else if (!weekInterval.equals("1")) {
				intervalValue = (Integer.valueOf(weekInterval) - 1) * 7;
			} else {
				intervalValue = 0;
			}
			task.setInterval(intervalValue);
			task.setRepeat(spinnerInterval.getSelection());

			if (buttonOneTime.getSelection()) {
				task.setDay(0);
				task.setWeek(0);
				task.setMonth(0);
			}
			//
			if (buttonPerDay.getSelection()) {
				task.setDay(0);
				task.setWeek(0);
				task.setMonth(0);
				// 设置日聚合
				if (dayValue != null) {
					for (Object day : dayValue) {
						task.margeDay((Day) day);
					}
				}

			}

			if (buttonPerWeek.getSelection()) {
				task.setDay(0);
				task.setWeek(0);
				task.setMonth(0);
				if (weekValue != null) {
					for (Object week : weekValue) {
						task.margeWeek((Week) week);
					}
				}
			}

			if (buttonPerMonth.getSelection()) {
				task.setWeek(0);
				task.setMonth(0);
				if (monthValue != null) {
					for (Object month : monthValue) {
						task.margeMonth((Month) month);
					}
				}
				// 设置日聚合
				if (dayValue != null) {
					for (Object day : dayValue) {
						task.margeDay((Day) day);
					}
				}
			}
		}

		{
			if (tableRead.getItemCount() > 0) {
				/**
				 * 检测
				 */
				int index = 0;
				Feature feature = null;

				// 2.设置检测项
				Object data = comboReadFeature.getData(comboReadFeature.getText());
				if (data != null && data instanceof Integer) {
					Integer featureIndex = (Integer) data;
					index = featureIndex;
					task.setReadFeature(featureIndex);
				}

				TableItem item = tableRead.getItem(0);
				Object data1 = item.getData();
				if (data1 != null && data1 instanceof Device) {
					Device device = (Device) data1;
					feature = device.getFeature(index);
					task.setReadModel(device.getModel());
				}

				task.removeAllReadDevices();
				
				// 1.设置检测设备
				TableItem[] items = tableRead.getItems();
				for (TableItem tableItem : items) {
					Object data2 = tableItem.getData();
					if (data2 != null && data2 instanceof Device) {
						Device device = (Device) data2;
						task.addReadDevice(device);
					}
				}

				// 3.设置阈值上限
				String text2 = textLimitLower.getText();
				if (!Tool.isEmpty(text2)) {
					// 乘以省略值后去掉小数部分
					BigDecimal b1 = new BigDecimal(text2);
					BigDecimal b2 = new BigDecimal(feature.OMITTED_VALUE);
					BigDecimal multiply = b1.multiply(b2);
					double a = multiply.doubleValue();
					int upper = (int) Math.floor(a);
					task.setLimitLower(upper);
				}

				// 4.设置阈值下限
				String text3 = textLimitUpper.getText();
				if (!Tool.isEmpty(text3)) {
					// 乘以省略值后去掉小数部分
					BigDecimal b1 = new BigDecimal(text3);
					BigDecimal b2 = new BigDecimal(feature.OMITTED_VALUE);
					BigDecimal multiply = b1.multiply(b2);
					double a = multiply.doubleValue();
					int upper = (int) Math.floor(a);
					task.setLimitUpper(upper);
				}
			}

		}

		{

			if (tableWrite.getItemCount() > 0) {
				/**
				 * 控制
				 */
				// 1.设置控制项
				Object data = comboWriteFeature.getData(comboWriteFeature.getText());
				if (data != null && data instanceof Integer) {
					Integer featureIndex = (Integer) data;
					task.setWriteFeature(featureIndex);
				}

				TableItem item = tableWrite.getItem(0);
				Object data1 = item.getData();
				if (data1 != null && data1 instanceof Device) {
					Device device = (Device) data1;
					task.setWriteModel(device.getModel());
				}

				task.removeAllWriteDevices();
				// 2.设置控制设备
				TableItem[] items = tableWrite.getItems();
				for (TableItem tableItem : items) {
					Object data2 = tableItem.getData();
					if (data2 != null && data2 instanceof Device) {
						Device device = (Device) data2;
						task.addWriteDevice(device);
					}
				}

				// 设置默认动作
				task.setFeed(scaleFeed.getSelection());
				// 设置上限动作
				task.setFeedUpper(scaleFeedUpper.getSelection());
				// 设置下限动作
				task.setFeedLower(scaleFeedLower.getSelection());
				
			}

		}
	}

	/**
	 * 选择一次点击事件
	 */
	private SelectionAdapter buttonOneTimeSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (buttonOneTime.getSelection()) {
				groupOneTime.setVisible(true);
				groupDay.setVisible(false);
				groupWeek.setVisible(false);
				groupMonth.setVisible(false);
			}
		}

	};

	/**
	 * 选择每天点击事件
	 */
	private SelectionAdapter buttonPerDaySelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (buttonPerDay.getSelection()) {
				groupOneTime.setVisible(false);
				groupDay.setVisible(true);
				groupWeek.setVisible(false);
				groupMonth.setVisible(false);
			}
		}

	};

	/**
	 * 选择每周点击事件
	 */
	private SelectionAdapter buttonPerWeekSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (buttonPerWeek.getSelection()) {
				groupOneTime.setVisible(false);
				groupDay.setVisible(false);
				groupWeek.setVisible(true);
				groupMonth.setVisible(false);
			}
		}

	};

	/**
	 * 选择每月的点击事件
	 */
	private SelectionAdapter buttonPerMonthSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (buttonPerMonth.getSelection()) {
				groupOneTime.setVisible(false);
				groupDay.setVisible(false);
				groupWeek.setVisible(false);
				groupMonth.setVisible(true);
			}
		}

	};

	private SelectionAdapter btnListener = new SelectionAdapter() {
		// 视图相关菜单和工具栏事件
		@Override
		public void widgetSelected(SelectionEvent e) {
			// verify();

			if (btnSunday.getSelection()) {
				weekValue.add(btnSunday.getData());
			} else {
				weekValue.remove(btnSunday.getData());
			}

			if (btnMonday.getSelection()) {
				weekValue.add(btnMonday.getData());
			} else {
				weekValue.remove(btnMonday.getData());
			}

			if (btnTuesday.getSelection()) {
				weekValue.add(btnTuesday.getData());
			} else {
				weekValue.remove(btnTuesday.getData());
			}

			if (btuWednesday.getSelection()) {
				weekValue.add(btuWednesday.getData());
			} else {
				weekValue.remove(btuWednesday.getData());
			}

			if (btuThursday.getSelection()) {
				weekValue.add(btuThursday.getData());
			} else {
				weekValue.remove(btuThursday.getData());
			}

			if (btuFriday.getSelection()) {
				weekValue.add(btuFriday.getData());
			} else {
				weekValue.remove(btuFriday.getData());
			}

			if (btuSaturday.getSelection()) {
				weekValue.add(btuSaturday.getData());
			} else {
				weekValue.remove(btuSaturday.getData());
			}

		}
	};

	private SelectionAdapter btnSelectMonthSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FSelectMonth dialog = new FSelectMonth(shell);
			dialog.open(shell, monthValue);
			monthValue = dialog.getSelectMonth();

			StringBuffer sb = new StringBuffer();
			for (Month month : monthValue) {
				String string = Lang.getString("Month." + month.name());
				sb.append(string + ",");
			}
			String s = sb.toString();
			int index = s.lastIndexOf(",");
			if (index != -1) {
				s = s.substring(0, index);
			}
			textSelectMonth.setText(s == null ? "" : s);

		}

	};

	private SelectionAdapter btnSelectDaySelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FSelectDay dialog = new FSelectDay(shell);
			dialog.open(shell, dayValue);
			if (dialog.isEnsure) {
				dayValue = dialog.getSelectDay();
				StringBuffer sb = new StringBuffer();
				for (Day day : dayValue) {
					String string = Lang.getString("Day." + day.name());
					sb.append(string + ",");
				}
				String s = sb.toString();
				int index = s.lastIndexOf(",");
				if (index != -1) {
					s = s.substring(0, index);
				}
				textSelectDay.setText(s == null ? "" : s);
			}
		}

	};

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

	private SelectionAdapter verifySelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			 verify();
		}

	};

	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			cancel();
		}

	};

	private SelectionAdapter btnAcceptSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			isUpdate = true;
			accept();
			close();
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

	private void cancel() {
		task = null;
		close();
	}

	/**
	 * TODO 编辑检测设备 编辑检测设备
	 */
	private SelectionAdapter btnAddReadSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FSelectDevice f = new FSelectDevice(shell);
			TableItem[] items = tableRead.getItems();
			Set<Device> devices = new HashSet<>();
			for (TableItem item : items) {
				Device d = (Device) item.getData();
				devices.add(d);
			}

			Set<Device> selectDevices = f.open(servo, devices);
			tableRead.removeAll();

			for (Device device : selectDevices) {
				TableItem tableItem = new TableItem(tableRead, SWT.NONE);
				tableItem.setText(0, device.getName());
				tableItem.setText(1, device.getZone() == null ? "" : device.getZone().getName());
				tableItem.setData(device);
			}

			Object data = comboReadFeature.getData("device");
			if (data == null) {
				if (selectDevices.iterator().hasNext()) {
					Device next = selectDevices.iterator().next();
					Feature[] features = next.getFeatures();
					comboReadFeature.removeAll();
					comboReadFeature.setData("device", next.getKind());
					for (Feature feature : features) {
						if (feature.PRIMARY != true || feature.READ_ONLY == false) {
							continue;
						}
						comboReadFeature.add(feature.getName());
						comboReadFeature.setData(feature.getName(), feature.INDEX);
					}
					
				}
			} else if (selectDevices.iterator().hasNext() && selectDevices.iterator().next().getKind() != (Kind) data) {
				if (selectDevices.iterator().hasNext()) {
					Device next = selectDevices.iterator().next();
					Feature[] features = next.getFeatures();
					comboReadFeature.removeAll();
					comboReadFeature.setData("device", next.getKind());
					for (Feature feature : features) {
						if (feature.PRIMARY != true || feature.READ_ONLY == false) {
							continue;
						}
						comboReadFeature.add(feature.getName());
						comboReadFeature.setData(feature.getName(), feature.INDEX);
					}
				}
			}
			
		}
	};

	/**
	 * TODO 编辑控制设备 编辑控制设备
	 */
	private SelectionAdapter btnAddWriteSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FSelectDevice f = new FSelectDevice(shell);
			TableItem[] items = tableWrite.getItems();
			Set<Device> devices = new HashSet<>();
			for (TableItem item : items) {
				Device d = (Device) item.getData();
				devices.add(d);
			}

			Set<Device> selectDevices = f.open(servo, devices);
			tableWrite.removeAll();

			for (Device device : selectDevices) {
				TableItem tableItem = new TableItem(tableWrite, SWT.NONE);
				tableItem.setText(0, device.getName());
				tableItem.setText(1, device.getZone() == null ? "" : device.getZone().getName());
				tableItem.setData(device);
			}

			Object data = comboWriteFeature.getData("device");
			if (data == null) {
				if (selectDevices.iterator().hasNext()) {
					Device next = selectDevices.iterator().next();
					Feature[] features = next.getFeatures();
					comboWriteFeature.removeAll();
					comboWriteFeature.setData("device", next.getKind());
					for (Feature feature : features) {
						if (feature.PRIMARY != true || feature.READ_ONLY == true) {
							continue;
						}
						comboWriteFeature.add(feature.getName());
						comboWriteFeature.setData(feature.getName(), feature.INDEX);
					}
					
					scaleFeed.setSelection(0);
					scaleFeedLower.setSelection(0);
					scaleFeedUpper.setSelection(0);
					lblFeedPer.setText("0%");
					lblFeedLowerPer.setText("0%");
					lblFeedUpperPer.setText("0%");
				}
			} else if (selectDevices.iterator().hasNext() && selectDevices.iterator().next().getKind() != (Kind) data) {
				if (selectDevices.iterator().hasNext()) {
					Device next = selectDevices.iterator().next();
					Feature[] features = next.getFeatures();
					comboWriteFeature.removeAll();
					comboWriteFeature.setData("device", next.getKind());
					for (Feature feature : features) {
						if (feature.PRIMARY != true || feature.READ_ONLY == true) {
							continue;
						}
						comboWriteFeature.add(feature.getName());
						comboWriteFeature.setData(feature.getName(), feature.INDEX);
					}
					scaleFeed.setSelection(0);
					scaleFeedLower.setSelection(0);
					scaleFeedUpper.setSelection(0);
					lblFeedPer.setText("0%");
					lblFeedLowerPer.setText("0%");
					lblFeedUpperPer.setText("0%");
				}
			}
		}
	};

	private SelectionAdapter comboWriteFeatureSelectionAdater = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			String text = comboWriteFeature.getText();
			if(!text.isEmpty()) {
				Object data = comboWriteFeature.getData(text);
				if(data==null||!(data instanceof Integer) ) {
					return;
				}
				//1.取到feature下标
				int index = (Integer)data;
				//2.取到设备对象
				if(tableWrite.getItemCount()<=0) {
					return;
				}
				Device device =(Device) tableWrite.getItem(0).getData();
				//3.取得feature对象
				Feature feature = device.getFeature(index);
				int max = feature.MAXIMUM;
				scaleFeed.setMaximum(max);
				scaleFeedLower.setMaximum(max);
				scaleFeedUpper.setMaximum(max);
				
				int step = feature.STEP;
				if (step > 1) {
					scaleFeed.setPageIncrement(step);
					scaleFeedLower.setPageIncrement(step);
					scaleFeedUpper.setPageIncrement(step);
				} else {
					scaleFeed.setPageIncrement(100);
					scaleFeedLower.setPageIncrement(100);
					scaleFeedUpper.setPageIncrement(100);
				}
			}
		}
		
	};
	private Button btnAccept;
	private Button btnCancel;
	
	/**
	 * 验证输入
	 */
	private void verify() {
		btnAccept.setEnabled(false);
		labelText.setVisible(true);

		if (Tool.isEmpty(nameText.getText())) {
			labelText.setText(Lang.getString("FTask.verifynameText.text"));
			return;
		}

		int sYear = startDate.getYear();
		int sMonth = startDate.getMonth();
		int sDay = startDate.getDay();
		int sHours = startTime.getHours();
		int sMinutes = startTime.getMinutes();
		int sSeconds = startTime.getSeconds();

		int eYear = endDate.getYear();
		int eMonth = endDate.getMonth();
		int eDay = endDate.getDay();
		int eHours = endTime.getHours();
		int eMinutes = endTime.getMinutes();
		int eSeconds = endTime.getSeconds();
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(sYear, sMonth, sDay,sHours,sMinutes,sSeconds);
		Date start = startCalendar.getTime();

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(eYear, eMonth, eDay,eHours,eMinutes,eSeconds);
		Date end = endCalendar.getTime();

		if (Integer.valueOf(spinnerInterval.getText() == "" ? "0" : spinnerInterval.getText()) > 43200) {
			labelText.setText("您不能设定间隔时间大于43200秒");
			return;
		}

		if (dayIntervalText.getText().equals("0")) {
			labelText.setText(Lang.getString("FTask.verifyDayIntervalText.text"));
			return;
		}

		if (weekIntervalText.getText().equals("0")) {
			labelText.setText(Lang.getString("FTask.verifyWeekIntervalText.text"));
			return;
		}

		if (start.getTime() > end.getTime()) {
			labelText.setText(Lang.getString("FTask.verifyTime.text"));
			return;
		}
		
		String text = textLimitLower.getText();
		String text2 = textLimitUpper.getText();
		
		if (text.equals("-") || text2.equals("-")) {
			labelText.setText(Lang.getString("FTask.tip"));
			return;
		}

		if (Double.valueOf(textLimitLower.getText() == "" ? "0" : textLimitLower.getText()) > Double.valueOf(textLimitUpper.getText() == "" ? "0" : textLimitUpper.getText())) {
			labelText.setText(Lang.getString("FTask.verifyMinThresholdText.text"));
			return;
		}

		labelText.setText(Tool.EMPTY);
		labelText.setVisible(false);
		btnAccept.setEnabled(true);
	}
}

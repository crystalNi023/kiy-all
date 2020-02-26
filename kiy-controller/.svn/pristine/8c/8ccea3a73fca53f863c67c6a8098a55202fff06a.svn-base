package com.kiy.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Maintain;
import com.kiy.common.Servo;
import com.kiy.common.Types.Repair;
import com.kiy.common.Unit;
import com.kiy.protocol.Messages.CreateMaintain;
import com.kiy.protocol.Messages.DeleteMaintain;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.QueryDeviceMaintain;
import com.kiy.protocol.Messages.Result;
import com.kiy.protocol.Messages.UpdateMaintain;
import com.kiy.protocol.Units.MMaintain;
import com.kiy.protocol.Units.MMaintain.Builder;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * @author (KeDaiQin TEL:18302367318)
 *
 */
public class FMaintainRecord extends Dialog implements FormMessage {

	private boolean closing;
	private Shell shell;
	private Table table;
	private DateTime startSecond;
	private DateTime endSecond;
	private Date start;
	private Date end;
	private ViewForm viewForm;
	private Composite composite0;
	private Composite composite;
	private ServoManager servoManager;
	private Map<String, TableItem> maintainItems; // 用于存放维护记录tableItem
	private List<Maintain> lists;

	private Device device;
	private Servo servo;

	public FMaintainRecord(Shell parent) {
		super(parent, 0);
		lists = new ArrayList<>();
		closing = false;
		maintainItems = new HashMap<>();
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public void open(Device d, ServoManager servoManager) {
		device = d;
		if (device == null) {
			throw new NullPointerException("device can not be null");
		}
		servo = d.getServo();
		if (servo == null) {
			throw new NullPointerException("servo can not be null");
		}

		createContents(device);
		this.servoManager = servoManager;
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public Shell getShell() {
		return shell;
	}

	/**
	 * 绘制设备维护列表记录窗口
	 */
	private void createContents(final Device d) {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE | SWT.SYSTEM_MODAL);
		shell.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/controller.png"));
		shell.addShellListener(shellAdapter);
		shell.setSize(900, 500);
		shell.setText(Lang.getString("FMaintainRecord.ShellTitle.text") + "-" + d.getName());
		shell.setLayout(new FillLayout());
		F.center(getParent(), shell);

		viewForm = new ViewForm(shell, SWT.NONE);

		GridLayout gl = new GridLayout();
		gl.numColumns = 7;
		composite0 = new Composite(viewForm, SWT.NONE);
		composite0.setLayout(gl);
		viewForm.setContent(composite0);

		Label label = new Label(composite0, SWT.NONE);
		label.setBounds(16, 18, 70, 17);
		label.setText(Lang.getString("FMaintainRecord.LabelStartTime.text"));

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -1);

		// 设置当前选中时间为当前时间前一周
		long timeInMillis = calendar.getTimeInMillis();

		DateTime startDay = new DateTime(composite0, SWT.BORDER | SWT.DROP_DOWN);
		startDay.setBounds(94, 16, 110, 24);
		startDay.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

		startSecond = new DateTime(composite0, SWT.BORDER | SWT.TIME);
		GridData gd_startSecond = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_startSecond.widthHint = 110;
		startSecond.setLayoutData(gd_startSecond);
		startSecond.setBounds(212, 16, 110, 24);
		startSecond.setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

		Label label_1 = new Label(composite0, SWT.NONE);
		label_1.setBounds(338, 18, 70, 17);
		label_1.setText(Lang.getString("FMaintainRecord.LabelEndTime.text"));

		final DateTime endDay = new DateTime(composite0, SWT.BORDER | SWT.DROP_DOWN);
		endDay.setBounds(416, 16, 110, 24);

		endSecond = new DateTime(composite0, SWT.BORDER | SWT.TIME);
		GridData gd_endSecond = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_endSecond.widthHint = 110;
		endSecond.setLayoutData(gd_endSecond);
		endSecond.setBounds(534, 16, 110, 24);

		final CtrClient client = d.getServo().getClient();
		if (client != null && client.isConnected() && client.isRunning()) {
			requestMaintainRecords(timeInMillis, System.currentTimeMillis(), client, d);
		}

		Button button = new Button(composite0, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 80;
		button.setLayoutData(gd_button);
		button.setBounds(660, 13, 80, 27);
		button.setText(Lang.getString("FMaintainRecord.ButtonInquery.text"));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				int sYear = startDay.getYear();
				int sMonth = startDay.getMonth() + 1;
				int sDay = startDay.getDay();
				int sHours = startSecond.getHours();
				int sMinutes = startSecond.getMinutes();
				int sSeconds = startSecond.getSeconds();

				int eYear = endDay.getYear();
				int eMonth = endDay.getMonth() + 1;
				int eDay = endDay.getDay();
				int eHours = endSecond.getHours();
				int eMinutes = endSecond.getMinutes();
				int eSeconds = endSecond.getSeconds();
				// 聚合年月日时分秒
				String startDate = sYear + "-" + sMonth + "-" + sDay + " " + sHours + ":" + sMinutes + ":" + sSeconds;
				String endDate = eYear + "-" + eMonth + "-" + eDay + " " + eHours + ":" + eMinutes + ":" + eSeconds;
				SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					start = form.parse(startDate);
					end = form.parse(endDate);
				} catch (ParseException e1) {
					return;
				}

				if (client != null && client.isConnected() && client.isRunning()) {
					requestMaintainRecords(start.getTime(), end.getTime(), client, d);
				}
			}
		});

		viewForm.setTopLeft(composite0);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite = new Composite(viewForm, SWT.NONE);
		composite.setLayout(gridLayout);
		viewForm.setContent(composite);

		// 表格布局
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = SWT.FILL;

		table = new Table(composite, SWT.VIRTUAL | SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(gridData);// 设置表格布局

		// 虚拟化监听
		table.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				show(event);
			}
		});

		// 排序监听
		Listener columnListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				sort((TableColumn) e.widget);
			}
		};

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText(Lang.getString("FMaintainRecord.TableItemMaintenanceType.text"));
		tableColumn.setResizable(true);
		tableColumn.setWidth(130);
		tableColumn.setMoveable(true);
		tableColumn.addListener(SWT.Selection, columnListener);

		TableColumn tableColumn1 = new TableColumn(table, SWT.NONE);
		tableColumn1.setText(Lang.getString("FMaintainRecord.TableItemSerialNumber.text"));
		tableColumn1.setResizable(true);
		tableColumn1.setWidth(90);
		tableColumn1.setMoveable(true);
		tableColumn1.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn11 = new TableColumn(table, SWT.NONE);
		tableColumn11.setText(Lang.getString("FMaintainRecord.TableItemLoadPower.text"));
		tableColumn11.setResizable(true);
		tableColumn11.setWidth(90);
		tableColumn11.setMoveable(true);
		tableColumn11.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn111 = new TableColumn(table, SWT.NONE);
		tableColumn111.setText(Lang.getString("FMaintainRecord.TableItemOwnPower.text"));
		tableColumn111.setResizable(true);
		tableColumn111.setWidth(90);
		tableColumn111.setMoveable(true);
		tableColumn111.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn1111 = new TableColumn(table, SWT.NONE);
		tableColumn1111.setText(Lang.getString("FMaintainRecord.TableItemMutualInductanceRatio.text"));
		tableColumn1111.setResizable(true);
		tableColumn1111.setWidth(90);
		tableColumn1111.setMoveable(true);
		tableColumn1111.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn11111 = new TableColumn(table, SWT.NONE);
		tableColumn11111.setText(Lang.getString("FMaintainRecord.TableItemEndOfTable.text"));
		tableColumn11111.setResizable(true);
		tableColumn11111.setWidth(90);
		tableColumn11111.setMoveable(true);
		tableColumn11111.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn111111 = new TableColumn(table, SWT.NONE);
		tableColumn111111.setText(Lang.getString("FMaintainRecord.TableItemTableBalance.text"));
		tableColumn111111.setResizable(true);
		tableColumn111111.setWidth(90);
		tableColumn111111.setMoveable(true);
		tableColumn111111.addListener(SWT.Selection, columnListener);
		
		TableColumn tableColumn1111111 = new TableColumn(table, SWT.NONE);
		tableColumn1111111.setText("时间");
		tableColumn1111111.setResizable(true);
		tableColumn1111111.setWidth(200);
		tableColumn1111111.setMoveable(true);
		tableColumn1111111.addListener(SWT.Selection, columnListener);
		
		Menu menu = new Menu(shell, SWT.BAR);

		MenuItem createItem = new MenuItem(menu, SWT.PUSH);
		createItem.setText(Lang.getString("FTaskRecord.MenuCreate.text"));
		createItem.addSelectionListener(createMaintainSelectionAdapter);
		createItem.setData(new ActionCase[] { ActionCase.CREATE_MAINTAIN });

		MenuItem editItem = new MenuItem(menu, SWT.PUSH);
		editItem.setText(Lang.getString("FTaskRecord.MenuEdit.text"));
		editItem.addSelectionListener(updateMaintainSelectionAdapter);
		editItem.setData(new ActionCase[] { ActionCase.UPDATE_MAINTAIN });

		MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
		deleteItem.setText(Lang.getString("FTaskRecord.MenuDelete.text"));
		deleteItem.addSelectionListener(deleteMaintainSelectionAdapter);
		deleteItem.setData(new ActionCase[] { ActionCase.DELETE_MAINTAIN });

		shell.setMenuBar(menu);
		
		MenuItem[] items = menu.getItems();
		MenuCommandUtil mcu = new MenuCommandUtil(servo);
		mcu.LoadMenuForCommand(items);
		
		Menu menu1 = new Menu(shell, SWT.POP_UP);

		MenuItem createItem1 = new MenuItem(menu1, SWT.PUSH);
		createItem1.setText(Lang.getString("FTaskRecord.MenuCreate.text"));
		createItem1.addSelectionListener(createMaintainSelectionAdapter);
		createItem1.setData(new ActionCase[] { ActionCase.CREATE_MAINTAIN });


		MenuItem editItem1 = new MenuItem(menu1, SWT.PUSH);
		editItem1.setText(Lang.getString("FTaskRecord.MenuEdit.text"));
		editItem1.addSelectionListener(updateMaintainSelectionAdapter);
		editItem1.setData(new ActionCase[] { ActionCase.UPDATE_MAINTAIN });

		MenuItem deleteItem1 = new MenuItem(menu1, SWT.PUSH);
		deleteItem1.setText(Lang.getString("FTaskRecord.MenuDelete.text"));
		deleteItem1.addSelectionListener(deleteMaintainSelectionAdapter);
		deleteItem1.setData(new ActionCase[] { ActionCase.DELETE_MAINTAIN });
		
		table.setMenu(menu1);
		
		menu1.addMenuListener(menuAdapter);
		make();
	}

	private void show(Event e) {
		TableItem item = (TableItem) e.item;
		Maintain maintain = lists.get(e.index);
		maintainItems.put(maintain.getId(), item);
		item.setData(maintain);
		update(item, maintain);
	}

	private void sort(TableColumn c) {
		TableColumn sortColumn = table.getSortColumn();
		if (c == null && sortColumn == null)
			return;

		int dir = table.getSortDirection();
		if (c == null) {
			c = sortColumn;
		} else if (sortColumn == c) {
			dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		} else {
			table.setSortColumn(c);
			dir = SWT.UP;
		}

		final int direction = dir;
		switch (table.indexOf(c)) {
			case 0:
				Collections.sort(lists, new Comparator<Maintain>() {
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getRepair().getValue(), b.getRepair().getValue());
						else
							return Integer.compare(b.getRepair().getValue(), a.getRepair().getValue());
					}
				});
				break;
			case 1:
				Collections.sort(lists, new Comparator<Maintain>() {
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return String.CASE_INSENSITIVE_ORDER.compare(a.getSn(), b.getSn());
						else
							return String.CASE_INSENSITIVE_ORDER.compare(b.getSn(), a.getSn());
					}
				});
				break;
			case 2:
				Collections.sort(lists, new Comparator<Maintain>() {
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getLoad(), b.getLoad());
						else
							return Float.compare(b.getLoad(), a.getLoad());
					}
				});
				break;
			case 3:
				Collections.sort(lists, new Comparator<Maintain>() {
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getPower(), b.getPower());
						else
							return Integer.compare(b.getPower(), a.getPower());
					}
				});
				break;
			case 4:
				Collections.sort(lists, new Comparator<Maintain>() {
					@Override
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Float.compare(a.getMutual(), b.getMutual());
						else
							return Float.compare(b.getMutual(), a.getMutual());
					}
				});
				break;
			case 5:
				Collections.sort(lists, new Comparator<Maintain>() {
					@Override
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getRadix(), b.getRadix());
						else
							return Integer.compare(b.getRadix(), a.getRadix());
					}
				});
				break;
			case 6:
				Collections.sort(lists, new Comparator<Maintain>() {
					@Override
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Integer.compare(a.getEnergyBalance(), b.getEnergyBalance());
						else
							return Integer.compare(b.getEnergyBalance(), a.getEnergyBalance());
					}
				});
				break;
			case 7:
				Collections.sort(lists, new Comparator<Maintain>(){

					@Override
					public int compare(Maintain a, Maintain b) {
						if (SWT.UP == direction)
							return Long.compare(a.getUpdated().getTime(), b.getUpdated().getTime());
						else
							return Long.compare(b.getUpdated().getTime(), a.getUpdated().getTime());
					}
					
				});
				break;
			default:
				break;
				
		}
		table.setSortDirection(dir);
		table.clearAll();
	}

	/**
	 * 更新
	 * 
	 * @param d
	 */
	public void update(TableItem item, Maintain maintain) {
		if (maintain == null)
			return;
		table.setItemCount(lists.size());
		// 维修类型
		item.setText(0, Lang.getString("Miantain." + maintain.getRepair().getValue()));
		// 序列号
		item.setText(1, String.valueOf(maintain.getSn()));
		// 负载功率
		item.setText(2, String.valueOf(maintain.getLoad()));
		// 自身功率
		item.setText(3, String.valueOf(maintain.getPower()));
		// 互感比
		item.setText(4, String.valueOf(maintain.getMutual()));
		// 标底
		item.setText(5, String.valueOf(maintain.getRadix()));
		// 表余量
		item.setText(6, String.valueOf(maintain.getEnergyBalance()));
		//时间
		item.setText(7,F.format(maintain.getUpdated()));
	}

	/**
	 * 请求设备维护记录
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	private void requestMaintainRecords(Long start, Long end, CtrClient client, Device d) {
		Message.Builder bm = Message.newBuilder();
		bm.setKey(CtrClient.getKey());
		bm.setUserId(client.getServo().getUser().getId());
		QueryDeviceMaintain.Builder qdm = QueryDeviceMaintain.newBuilder();
		qdm.setBegin(start);
		qdm.setEnd(end);
		qdm.setId(d.getId());
		bm.setQueryDeviceMaintain(qdm);
		client.send(bm.build());
	}

	private void make() {
	}

	@Override
	public void received(Servo s, Message m,Map<Message, Unit> units) {
		if(shell.isDisposed()){
			return;
		}
		
		if (m.getActionCase() == ActionCase.QUERY_DEVICE_MAINTAIN) {
			if (m.getResult() == Result.SUCCESS) {
				QueryDeviceMaintain qdm = m.getQueryDeviceMaintain();
				List<MMaintain> mlist = qdm.getItemsList();
				if (mlist.size() > 0) {
					lists = new ArrayList<>();
					for (MMaintain mmaintain : mlist) {
						Maintain maintain = new Maintain();
						maintain.setId(mmaintain.getId());
						maintain.setDeviceId(mmaintain.getDeviceId());
						maintain.setRepair(Repair.valueOf(mmaintain.getRepair()));
						maintain.setSn(mmaintain.getSn());
						maintain.setLoad(mmaintain.getLoad());
						maintain.setPower(mmaintain.getPower());
						maintain.setMutual(mmaintain.getMutual());
						maintain.setRadix(mmaintain.getRadix());
						maintain.setEnergyBalance(mmaintain.getEnergyBalance());
						maintain.setUpdated(new Date(mmaintain.getUpdated()));
						lists.add(maintain);
					}

					table.setItemCount(lists.size());
				} else {
					table.removeAll();
				}
			} else {
				F.mbWaring(getShell(), Lang.getString("FMaintainRecord.MessageBoxTitle.text"), Lang.getString("FMaintainRecord.MessageBoxContent.text"));
				return;
			}
		} else if (m.getActionCase() == ActionCase.CREATE_MAINTAIN) {
			if (m.getResult() == Result.SUCCESS) {
				CreateMaintain createMaintain = m.getCreateMaintain();
				MMaintain mmaintain = createMaintain.getItem();
				Maintain maintain = new Maintain();
				
				maintain.setId(mmaintain.getId());
				maintain.setDeviceId(mmaintain.getDeviceId());
				maintain.setRepair(Repair.valueOf(mmaintain.getRepair()));
				maintain.setSn(mmaintain.getSn());
				maintain.setLoad(mmaintain.getLoad());
				maintain.setPower(mmaintain.getPower());
				maintain.setMutual(mmaintain.getMutual());
				maintain.setRadix(mmaintain.getRadix());
				maintain.setEnergyBalance(mmaintain.getEnergyBalance());
				maintain.setUpdated(new Date(mmaintain.getCreated()));

				insert(maintain);

			} else {
				// 创建维护记录失败
				F.mbWaring(getShell(), Lang.getString("FMaintainRecord.create"), Lang.getString("FMiantainRecord.create.tip"));
				return;
			}
		} else if (m.getActionCase() == ActionCase.UPDATE_MAINTAIN) {
			if (m.getResult() == Result.SUCCESS) {
				UpdateMaintain updateMaintain = m.getUpdateMaintain();
				MMaintain mMaintain = updateMaintain.getItem();
				Maintain maintain = new Maintain();
				maintain.setId(mMaintain.getId());
				maintain.setDeviceId(mMaintain.getDeviceId());
				maintain.setRepair(Repair.valueOf(mMaintain.getRepair()));
				maintain.setSn(mMaintain.getSn());
				maintain.setLoad(mMaintain.getLoad());
				maintain.setPower(mMaintain.getPower());
				maintain.setMutual(mMaintain.getMutual());
				maintain.setRadix(mMaintain.getRadix());
				maintain.setEnergyBalance(mMaintain.getEnergyBalance());
				maintain.setUpdated(new Date(mMaintain.getUpdated()));
				updateMaintain(maintain);
			} else {
				// 更新维护记录失败
				F.mbWaring(getShell(), Lang.getString("FMaintainRecord.update"), Lang.getString("FMaintainRecord.update.tip"));
				return;
			}
		} else if (m.getActionCase() == ActionCase.DELETE_MAINTAIN) {
			if (m.getResult() == Result.SUCCESS) {
				DeleteMaintain mmaintain = m.getDeleteMaintain();
				String id = mmaintain.getId();
				Maintain mm = null ;
				for (Maintain maintain : lists) {
					if (maintain.getId().equals(id)) {
						mm = maintain;
					}
				}
				
				if(mm!=null){
					remove(mm);
				}
			} else {
				// 删除维护记录失败
				F.mbWaring(getShell(), Lang.getString("FMaintainRecord.delete"), Lang.getString("FMaintainRecord.delete.tip"));
			}
		}
	}

	@Override
	public void close() {
		servoManager.off(this);
		if (closing) {
			return;
		}
		shell.close();
	}

	private ShellAdapter shellAdapter = new ShellAdapter() {

		@Override
		public void shellClosed(ShellEvent arg0) {
			closing = true;
			close();
		}

	};

	/**
	 * 维修记录
	 * 
	 * @param user
	 */
	private void insert(Maintain maintain) {
		if (table.isDisposed()) {
			return;
		}

		if (maintain == null) {
			return;
		}

		lists.add(maintain);
		table.setItemCount(lists.size());
		table.clearAll();
	}

	/**
	 * 删除维护记录
	 * 
	 * @param user
	 */
	private void remove(Maintain maintain) {
		if (table.isDisposed()) {
			return;
		}

		if (maintain == null) {
			return;
		}

		if (lists.remove(maintain)) {
			table.setItemCount(lists.size());
			table.clearAll();
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	private void updateMaintain(Maintain maintain) {
		if (table.isDisposed()) {
			return;
		}

		if (maintain == null)
			return;

		if (lists.contains(maintain)) {
			TableItem item = maintainItems.get(maintain.getId());
			if (item == null) {
				return;
			}

			table.setItemCount(lists.size());

			table.setItemCount(lists.size());
			// 维修类型
			item.setText(0, Lang.getString("Miantain." + maintain.getRepair().getValue()));
			// 序列号
			item.setText(1, String.valueOf(maintain.getSn()));
			// 负载功率
			item.setText(2, String.valueOf(maintain.getLoad()));
			// 自身功率
			item.setText(3, String.valueOf(maintain.getPower()));
			// 互感比
			item.setText(4, String.valueOf(maintain.getMutual()));
			// 标底
			item.setText(5, String.valueOf(maintain.getRadix()));
			// 表余量
			item.setText(6, String.valueOf(maintain.getEnergyBalance()));
			//时间
			item.setText(7,F.format(maintain.getUpdated()));
		} else {
		}

	}

	private SelectionAdapter createMaintainSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			createMaintain();
		}

	};

	private SelectionAdapter updateMaintainSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			updateMaintain();
		}

	};

	private SelectionAdapter deleteMaintainSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			deleteMaintain();
		}

	};

	private void createMaintain() {
		CtrClient client = servo.getClient();

		boolean judgeClientActive = F.judgeClientActive(shell, client);
		if (judgeClientActive) {
			return;
		}

		FMaintain form = new FMaintain(shell);
		Maintain maintain = form.open(device, null);
		if (maintain != null) {
			Message.Builder b_m = Message.newBuilder();
			b_m.setKey(CtrClient.getKey());
			b_m.setUserId(servo.getUser().getId());
			CreateMaintain.Builder createMaintain = CreateMaintain.newBuilder();
			
			Builder builder = createMaintain.getItemBuilder();
			builder.setId(maintain.getId());
			builder.setDeviceId(maintain.getDeviceId());
			builder.setRepair(maintain.getRepair().getValue());
			builder.setSn(maintain.getSn());
			builder.setLoad(maintain.getLoad());
			builder.setPower(maintain.getPower());
			builder.setMutual(maintain.getMutual());
			builder.setRadix(maintain.getRadix());
			builder.setEnergyBalance(maintain.getEnergyBalance());
			builder.setCreated(new Date().getTime());
			b_m.setCreateMaintain(createMaintain);
			client.send(b_m.build());
		}
	}

	private void updateMaintain() {
		int selectionIndex = table.getSelectionIndex();
		if (selectionIndex != -1) {
			TableItem item = table.getItem(selectionIndex);
			Object data = item.getData();
			if (data != null && data instanceof Maintain) {
				Maintain maintain1 = (Maintain) data;

				CtrClient client = servo.getClient();

				boolean judgeClientActive = F.judgeClientActive(shell, client);
				if (judgeClientActive) {
					return;
				}

				FMaintain form = new FMaintain(shell);
				Maintain maintain = form.open(null, maintain1);
				if (form.isUpdate) {
					Message.Builder b_m = Message.newBuilder();
					b_m.setKey(CtrClient.getKey());
					b_m.setUserId(servo.getUser().getId());
					UpdateMaintain.Builder updateMaintain = UpdateMaintain.newBuilder();
					Builder builder = updateMaintain.getItemBuilder();
					
					builder.setId(maintain.getId());
					builder.setDeviceId(maintain.getDeviceId());
					builder.setRepair(maintain.getRepair().getValue());
					builder.setSn(maintain.getSn());
					builder.setLoad(maintain.getLoad());
					builder.setPower(maintain.getPower());
					builder.setMutual(maintain.getMutual());
					builder.setRadix(maintain.getRadix());
					builder.setEnergyBalance(maintain.getEnergyBalance());
					builder.setUpdated(new Date().getTime());
					b_m.setUpdateMaintain(updateMaintain);
					client.send(b_m.build());
				}

			}
		} else {
			// 没有选择maintain，提示
			F.mbInformation(shell, Lang.getString("FMaintainRecord.edit"), Lang.getString("FMaintainRecord.edit.Tip"));
		}
	}

	private void deleteMaintain() {
		int selectionIndex = table.getSelectionIndex();
		if (selectionIndex != -1) {
			TableItem item = table.getItem(selectionIndex);
			Object data = item.getData();
			if (data != null && data instanceof Maintain) {
				Maintain maintain1 = (Maintain) data;

				CtrClient client = servo.getClient();

				boolean judgeClientActive = F.judgeClientActive(shell, client);
				if (judgeClientActive) {
					return;
				}
				
				int open = F.mbQuestionCancel(getShell(), Lang.getString("FMaintainRecord.delete"), Lang.getString("FMaintainrecord.delete.question"));
				
				if(open == SWT.OK){
					Message.Builder b_m = Message.newBuilder();
					b_m.setKey(CtrClient.getKey());
					b_m.setUserId(servo.getUser().getId());
					DeleteMaintain.Builder delete = DeleteMaintain.newBuilder();
					delete.setId(maintain1.getId());
					b_m.setDeleteMaintain(delete);
					client.send(b_m.build());
				}
			}
		} else {
			// 没有选择maintain，提示
			F.mbInformation(shell, Lang.getString("FMaintainRecord.delete"), Lang.getString("FMaintainRecord.delete.tip"));
		}
	}
	
	private MenuAdapter menuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu menu = (Menu) arg0.widget;
			MenuItem[] items = menu.getItems();
			MenuCommandUtil m = new MenuCommandUtil(servo);
			m.LoadMenuForCommand(items);
		}

	};

}

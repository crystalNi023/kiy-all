/**
FF * 2017年1月18日
 */
package com.kiy.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.geotools.swt.SwtMapPane;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Status;
import com.kiy.common.Types.Vendor;
import com.kiy.common.Unit;
import com.kiy.common.User;
import com.kiy.common.Zone;
import com.kiy.controller.view.MapView;
import com.kiy.controller.view.TreeRelayView;
import com.kiy.controller.view.TreeView;
import com.kiy.controller.view.TreeZoneView;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.protocol.Messages.Result;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FMain implements FormMessage {

	private Shell shell;

	private Menu menu;
	private MenuItem menuViewTree;
	private Menu menuViews;
	private MenuItem menuViewState;
	private MenuItem menuViewMessage;
	private MenuItem menuBarTools;
	private MenuItem menuBarStatus;
	private Menu menuServos;
	private Menu menuDevices;
	private MenuItem menuViewFullscreen;
	private MenuItem menuServoConnect;
	private MenuItem menuServoDisconnect;
	private MenuItem menuTreeZonesServoConnect;
	private MenuItem menuTreeZonesServoDisconnect;
	private Menu menuCreates;
	private Tree treeZones;
	private Tree treeRelays;
	private CoolBar tools;
	private ToolBar toolSearch;
	private SashForm formHR;
	private CTabFolder tabFolderTrees;
	private CTabItem tabTreeZones;
	private CTabItem tabTreeRelays;
	private SashForm formVR;
	private CTabFolder tabFolderStates;
	private SwtMapPane mapPane;
	private CTabItem tabViewPlane;
	private CTabFolder tabFolderMessages;
	private CTabItem tabMessage;
	private ToolBar toolServo;
	private ToolBar toolViews;
	private ToolItem toolViewTree;
	private ToolItem toolViewState;
	private ToolItem toolViewMessage;
	private Table tableMessage;
	private Table tableNotice;
	private CLabel labelStatusServo;
	private CLabel labelStatusUser;
	private CLabel labelStatusSelect;
	private Composite status;
	private ToolItem toolSearchDeviceVendor;
	private Menu menuDeviceVendor;
	private Text text;
	private MenuItem menuVendorAll;
	private ToolItem toolSearchDeviceType;
	private Menu menuDeviceType;
	private MenuItem menuTypeAll;
	private ToolItem toolSearchDeviceLink;
	private Menu menuDeviceLink;
	private MenuItem menuLinkAll;
	private ToolItem toolMapLayers;
	private MenuItem menuView;
	private ToolItem toolServoConnect;
	private Menu menuReports;

	// 辅助视图
	// private TableView tableView;
	private TreeRelayView treeRelayView;
	private TreeZoneView treeZoneView;
	private ServoManager servoManager;
	private MapView mapView;

	// 新状态列表视图 成员变量
	private TreeView treeView;
	private CTabItem tabViewTree;
	private Tree treeDeviceDetails;

	public static final boolean THIS_IS_MAC = SWT.getPlatform().equals("cocoa"); // 判断是否为Mac OS系统

	public static boolean isOpenCammera;

	public FMain() {
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FMain window = new FMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display.setAppName(Lang.getString("NAME"));
		Display display = Display.getDefault();
		createContents();

		shell.setToolTipText(Lang.getString("NAME"));
		shell.open();
		shell.layout();

		load();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setText(Lang.getString("NAME"));
		shell.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/controller.png"));
		shell.setSize(1100, 700);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		

		shell.setLayout(new FormLayout());
		{

			menu = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menu);

			{
				// 主菜单-伺服器Item
				MenuItem menuServo = new MenuItem(menu, SWT.CASCADE);
				menuServo.setText(Lang.getString("FMain.menuServoCreate2.text"));
				// 主菜单-伺服器
				menuServos = new Menu(menuServo);
				menuServo.setMenu(menuServos);

				// 主菜单-伺服器-新建item
				MenuItem menuCreate = new MenuItem(menuServos, SWT.CASCADE);
				menuCreate.setText(Lang.getString("FMain.mntmNewSubmenu.text"));

				// 主菜单-伺服器-新建
				menuCreates = new Menu(menuCreate);
				menuCreate.setMenu(menuCreates);

				// 主菜单-伺服器-新建-新建伺服器
				MenuItem menuCreateServo = new MenuItem(menuCreates, SWT.CASCADE);
				menuCreateServo.addSelectionListener(new AServoCreate(this));
				menuCreateServo.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));
				menuCreateServo.setText(Lang.getString("FMain.menuServoCreate.text") + "\tAlt+Shift+S"); //$NON-NLS-1$
				menuCreateServo.setAccelerator(SWT.ALT + SWT.SHIFT + 'S');

				// 主菜单-伺服器-新建-新建设备
				MenuItem menuCreateDevice = new MenuItem(menuCreates, SWT.CASCADE);
				menuCreateDevice.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_create.png"));
				menuCreateDevice.setText(Lang.getString("FMain.menuCreateDevice.text"));
//				menuCreateDevice.setData(new ActionCase[] { ActionCase.CREATE_DEVICE });

				// 主菜单-伺服器-新建-新建设备
				Menu menuCreatesDevice = new Menu(menuCreateDevice);
				menuCreateDevice.setMenu(menuCreatesDevice);
				
				F.createDeviceMenu(menuCreatesDevice, this);

				// 主菜单-伺服器-新建-新建地区
				MenuItem menuCreateZone = new MenuItem(menuCreates, SWT.CASCADE);
				menuCreateZone.addSelectionListener(new ACreateZone(this));
				menuCreateZone.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));
				menuCreateZone.setText(Lang.getString("FMain.menuCreateZone.text") + "\tAlt+Shift+Z"); //$NON-NLS-1$
				menuCreateZone.setAccelerator(SWT.ALT + SWT.SHIFT + 'Z');
				menuCreateZone.setData(new ActionCase[] { ActionCase.CREATE_ZONE });

				// 主菜单-伺服器-新建-新建设备维护
				MenuItem menuCreateMaintain = new MenuItem(menuCreates, SWT.CASCADE);
				menuCreateMaintain.addSelectionListener(new AMaintainCreate(this));
				menuCreateMaintain.setText(Lang.getString("FMain.menuMainTain.text") + "\tAlt+Shift+M");
				menuCreateMaintain.setAccelerator(SWT.ALT + SWT.SHIFT + 'M');
				menuCreateMaintain.setData(new ActionCase[] { ActionCase.CREATE_MAINTAIN });

				new MenuItem(menuServos, SWT.SEPARATOR);

				// 主菜单-伺服器-连接
				menuServoConnect = new MenuItem(menuServos, SWT.CASCADE);
				menuServoConnect.addSelectionListener(new AServoConnect(this));
				menuServoConnect.setText(Lang.getString("FMain.menuServoConnect.text"));
				menuServoConnect.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));

				// 主菜单-伺服器-断开
				menuServoDisconnect = new MenuItem(menuServos, SWT.CASCADE);
				menuServoDisconnect.addSelectionListener(new AServoDisconnect(this));
				menuServoDisconnect.setText(Lang.getString("FMain.menuServoDisconnect.text")); //$NON-NLS-1$
				menuServoDisconnect
						.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_disconnected.png"));

				new MenuItem(menuServos, SWT.SEPARATOR);

				// 主菜单-伺服器-计划策略
				MenuItem menuTasks = new MenuItem(menuServos, SWT.NONE);
				menuTasks.setText(Lang.getString("FMain.menuItem.text")); //$NON-NLS-1$
				menuTasks.addSelectionListener(new ATaskRecord(this));
				menuTasks.setData(
						new ActionCase[] { ActionCase.CREATE_TASK, ActionCase.UPDATE_TASK, ActionCase.DELETE_TASK });

				// 主菜单-伺服器-场景
				MenuItem menuScenes = new MenuItem(menuServos, SWT.NONE);
				menuScenes.setText(Lang.getString("FMain.menuItem1.text")); //$NON-NLS-1$
				menuScenes.addSelectionListener(new ASceneRecord(this));
				menuScenes.setData(
						new ActionCase[] { ActionCase.CREATE_SCENE, ActionCase.UPDATE_SCENE, ActionCase.DELETE_SCENE });
				
				// 主菜单-伺服器-联动
				MenuItem menuLinkage = new MenuItem(menuServos, SWT.NONE);
				menuLinkage.setText(Lang.getString("FMain.menuItemLinkage.text")); //$NON-NLS-1$
				menuLinkage.addSelectionListener(new ALinkageRecord(this));
				menuLinkage.setData(
						new ActionCase[] { ActionCase.CREATE_LINKAGE, ActionCase.UPDATE_SCENE, ActionCase.DELETE_LINKAGE });
				
				// 主菜单-伺服器-用户角色
				MenuItem menuUserRole = new MenuItem(menuServos, SWT.NONE);
				menuUserRole.setText(Lang.getString("FMain.menuItemUserRole.text")); //$NON-NLS-1$
				menuUserRole.addSelectionListener(new AUserAndRole(this));
				// 设置用户角色菜单权限
				menuUserRole.setData(new ActionCase[] { ActionCase.CREATE_USER, ActionCase.CREATE_ROLE,
						ActionCase.UPDATE_USER, ActionCase.UPDATE_ROLE, ActionCase.DELETE_USER, ActionCase.DELETE_ROLE,
						ActionCase.QUERY_LOG });

				// 主菜单-伺服器-查询日志
				MenuItem menuLogs = new MenuItem(menuServos, SWT.NONE);
				menuLogs.setText(Lang.getString("FMain.menuItemQueryLog.text"));
				menuLogs.addSelectionListener(new AQueryLog(this));
				// 设置日志查询权限
				menuLogs.setData(new ActionCase[] { ActionCase.QUERY_LOG });

				new MenuItem(menuServos, SWT.SEPARATOR);

				// 主菜单-伺服器-伺服器配置
				MenuItem menuServoConfig = new MenuItem(menuServos, SWT.PUSH);
				menuServoConfig.setText(Lang.getString("FMain.menuServoConfig.text"));
				menuServoConfig.addSelectionListener(new AServoConfig(this));
				// 设置伺服器配置菜单权限
				menuServoConfig.setData(new ActionCase[] { ActionCase.GET_SERVO_CONFIG, ActionCase.SET_SERVO_CONFIG });

				// 主菜单-伺服器-维护记录
				MenuItem menuMaintainRecords = new MenuItem(menuServos, SWT.PUSH);
				menuMaintainRecords.setText(Lang.getString("FMain.menuMainTainRecords.text"));
				menuMaintainRecords.addSelectionListener(new AMainTainRecords(this));
				// 设置设备维护菜单权限
				menuMaintainRecords.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_MAINTAIN });

				// 主菜单-伺服器-历史状态
				MenuItem menuDeviceHistoryStatus = new MenuItem(menuServos, SWT.PUSH);
				menuDeviceHistoryStatus.setText(Lang.getString("FMain.menuHistoryStatus.text"));
				menuDeviceHistoryStatus.addSelectionListener(new AHistoryDeviceStatus(this));
				// 设置历史状态菜单权限
				menuDeviceHistoryStatus.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_STATUS });

				MenuItem menuMapManager = new MenuItem(menuServos, SWT.PUSH);
				menuMapManager.setText(Lang.getString("FMain.mapManager"));
				menuMapManager.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						FMapFileManager f = new FMapFileManager(getShell());
						servoManager.add(f, getCurrentServo());
						f.open(getCurrentServo(), servoManager);

					}

				});
				menuMapManager.setData(
						new ActionCase[] { ActionCase.FILE_LIST, ActionCase.FILE_UPLOAD, ActionCase.FILE_DOWNLOAD });

				new MenuItem(menuServos, SWT.SEPARATOR);

				MenuItem menuServoRefresh = new MenuItem(menuServos, SWT.CASCADE);
				menuServoRefresh.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						refreshTree();
					}

				});
				menuServoRefresh.setText(Lang.getString("refresh") + "\tF5");
				menuServoRefresh.setAccelerator(SWT.F5);

				// 主菜单-伺服器-删除
				MenuItem menuServoDelete = new MenuItem(menuServos, SWT.CASCADE);
				menuServoDelete.addSelectionListener(new ADeleteUnit(this));
				menuServoDelete.setText(Lang.getString("FMain.menuServoDelete.text") + "\tDelete");
				menuServoDelete.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/delete.png"));
				menuServoDelete.setAccelerator(SWT.DEL);
				menuServoDelete.setData("tag", "delete");

				// 主菜单-伺服器-设置
				MenuItem menuServoModify = new MenuItem(menuServos, SWT.CASCADE);
				menuServoModify.addSelectionListener(new AUpdateUnit(this));
				menuServoModify.setText(Lang.getString("FMain.menuServoModify.text_1")); //$NON-NLS-1$
				menuServoModify.setAccelerator(SWT.ALT + SWT.CR);
				menuServoModify.setData("tag", "property");

				MenuItem menuUnitDetails = new MenuItem(menuServos, SWT.CASCADE);
				menuUnitDetails.addSelectionListener(new ACheckProperty(this));
				menuUnitDetails.setText(Lang.getString("Proprety")); //$NON-NLS-1$
				menuUnitDetails.setAccelerator(SWT.ALT + SWT.CR);

				new MenuItem(menuServos, SWT.SEPARATOR);

				// 主菜单-伺服器-退出
				MenuItem menuExit = new MenuItem(menuServos, SWT.CASCADE);
				menuExit.setText(Lang.getString("FMain.menuLogout.text"));
				menuExit.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/singout.png"));
				menuExit.addSelectionListener(new AExit(this));

				// 主菜单-控制item
				MenuItem menuDevice = new MenuItem(menu, SWT.CASCADE);
				menuDevice.setText(Lang.getString("FMain.menuDevice.text")); //$NON-NLS-1$

				// 主菜单-控制
				menuDevices = new Menu(menuDevice);
				menuDevice.setMenu(menuDevices);

				// 主菜单-控制-获取状态
				MenuItem menuDeviceGetStatus = new MenuItem(menuDevices, SWT.CASCADE);
				menuDeviceGetStatus.setText(Lang.getString("FMain.menuGetStatus.text"));
				menuDeviceGetStatus.addSelectionListener(new AGetDeviceStatus(this));
				// 设置获取状态菜单权限
				menuDeviceGetStatus.setData(new ActionCase[] { ActionCase.READ_DEVICE_STATUS });

				// 主菜单-控制-设置状态
				MenuItem menuDeviceSetStatus = new MenuItem(menuDevices, SWT.CASCADE);
				menuDeviceSetStatus.setText(Lang.getString("FMain.menuSetStatus.text"));
				menuDeviceSetStatus.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/setStatu.png"));
				menuDeviceSetStatus.addSelectionListener(new ASetDeviceStatus(this));
				// 设置设置状态菜单权限
				menuDeviceSetStatus.setData(new ActionCase[] { ActionCase.WRITE_DEVICE_STATUS });
				new MenuItem(menuDevices, SWT.SEPARATOR);

				MenuItem selectAll = new MenuItem(menuDevices, SWT.CALENDAR);
				selectAll.setText(Lang.getString("AllSelctet") + "\tCtrl+A");
				selectAll.setAccelerator(SWT.CTRL + 'A');
				selectAll.addSelectionListener(selectAllSelectionAdapter);

				// 主菜单-控制-属性
				MenuItem menuDeviceProperties = new MenuItem(menuDevices, SWT.CASCADE);
				menuDeviceProperties.setText(Lang.getString("FMain.menuServoModify.text") + "\tAlt+Enter");
				menuDeviceProperties.addSelectionListener(new AUpdateUnit(this));
				menuDeviceProperties.setAccelerator(SWT.ALT + SWT.CR);
				menuDeviceProperties.setData("tag", "property");

				// 主菜单-视图item
				menuView = new MenuItem(menu, SWT.CASCADE);
				menuView.setText(Lang.getString("FMain.menuView.text"));

				// 主菜单-视图
				menuViews = new Menu(menuView);
				menuView.setMenu(menuViews);

				// 主菜单-视图-结构视图
				menuViewTree = new MenuItem(menuViews, SWT.CHECK);
				menuViewTree.addSelectionListener(viewsListener);
				menuViewTree.setText(Lang.getString("FMain.menuStructureView.text") + "\tCtrl+Shift+C");
				menuViewTree.setAccelerator(SWT.CTRL + SWT.SHIFT + 'C');
				menuViewTree.setSelection(true);

				// 主菜单-视图-状态视图
				menuViewState = new MenuItem(menuViews, SWT.CHECK);
				menuViewState.addSelectionListener(viewsListener);
				menuViewState.setText(Lang.getString("FMain.menuStatuView.text") + "\tCtrl+Shift+S");
				menuViewState.setAccelerator(SWT.CTRL + SWT.SHIFT + 'S');
				menuViewState.setSelection(true);

				// 主菜单-视图-消息视图
				menuViewMessage = new MenuItem(menuViews, SWT.CHECK);
				menuViewMessage.addSelectionListener(viewsListener);
				menuViewMessage.setText(Lang.getString("FMain.menuMessageView.text") + "\tCtrl+Shift+I");
				menuViewMessage.setAccelerator(SWT.CTRL + SWT.SHIFT + 'I');
				menuViewMessage.setSelection(true);

				new MenuItem(menuViews, SWT.SEPARATOR);

				// 主菜单-视图-工具栏
				menuBarTools = new MenuItem(menuViews, SWT.CHECK);
				menuBarTools.addSelectionListener(menuBarToolsSelectionAdapter);
				menuBarTools.setText(Lang.getString("FMain.menuToolBar.text") + "\tAlt+B");
				menuBarTools.setAccelerator(SWT.ALT + 'B');
				menuBarTools.setSelection(true);

				// 主菜单-视图-状态栏
				menuBarStatus = new MenuItem(menuViews, SWT.CHECK);
				menuBarStatus.addSelectionListener(menuBarStatusSelectionAdapter);
				menuBarStatus.setText(Lang.getString("FMain.menuStatuBar.text") + "\tAlt+S");
				menuBarStatus.setAccelerator(SWT.ALT + 'S');
				menuBarStatus.setSelection(true);

				new MenuItem(menuViews, SWT.SEPARATOR);

				// 主菜单-视图-重置所有布局
				MenuItem menuViewReset = new MenuItem(menuViews, SWT.CASCADE);
				menuViewReset.addSelectionListener(menuViewResetSelectionAdapter);
				menuViewReset.setText(Lang.getString("FMain.menuResetAllStructure.text") + "\tAlt+A");
				menuViewReset.setAccelerator(SWT.ALT + 'A');

				// 主菜单-视图-全屏
				menuViewFullscreen = new MenuItem(menuViews, SWT.CHECK);
				menuViewFullscreen.setText(Lang.getString("FMain.menuFullScreen.text"));
				menuViewFullscreen.addSelectionListener(menuViewFullscreenSelectionAdapter);

				MenuItem menuReport = new MenuItem(menu, SWT.CASCADE);
				menuReport.setText(Lang.getString("FMain.mntmNewSubmenu.text_1")); //$NON-NLS-1$

				menuReports = new Menu(menuReport);
				menuReport.setMenu(menuReports);

				ExecutorService es = CtrClient.getES();
				es.execute(new Runnable() {

					@Override
					public void run() {
						loadReportMenu();
					}
				});

				// 主菜单-我的item
				MenuItem menuPersonal = new MenuItem(menu, SWT.CASCADE);
				menuPersonal.setText(Lang.getString("FMain.menuUser.text"));

				// 主菜单-我的
				Menu menuPersonals = new Menu(menuPersonal);
				menuPersonal.setMenu(menuPersonals);

				// 主菜单-我的-个人资料
				MenuItem menuPersonalInfo = new MenuItem(menuPersonals, SWT.CASCADE);
				menuPersonalInfo.addSelectionListener(new ACheckUserInfo(this));
				menuPersonalInfo.setText(Lang.getString("FMain.menuPersonalInfo.text"));

				new MenuItem(menuPersonals, SWT.SEPARATOR);

				// 主菜单-我的-修改资料
				MenuItem menuEditPersonal = new MenuItem(menuPersonals, SWT.CASCADE);
				menuEditPersonal.setText(Lang.getString("FMain.menuEditPersonal.text"));
				menuEditPersonal.addSelectionListener(new AUpdatePersonalInfo(this));

				// 主菜单-我的-修改密码
				MenuItem menuEditPassword = new MenuItem(menuPersonals, SWT.CASCADE);
				menuEditPassword.setText(Lang.getString("FMain.menuEditPassword.text"));
				menuEditPassword.addSelectionListener(new AUpdatePersonalPassword(this));

				new MenuItem(menuPersonals, SWT.SEPARATOR);

				// 主菜单-我的-查看个人日志
				MenuItem menuQueryPersonalLog = new MenuItem(menuPersonals, SWT.CASCADE);
				menuQueryPersonalLog.setText(Lang.getString("FMain.menuQueryPersonalLog.text"));
				menuQueryPersonalLog.addSelectionListener(new AQueryPersonalLog(this));

				menuPersonals.addMenuListener(menuPersonalsMenuAdapter);

				// 主菜单-帮助item
				MenuItem menuHelp = new MenuItem(menu, SWT.CASCADE);
				menuHelp.setText(Lang.getString("FMain.menuHelp.text"));

				// 主菜单-帮助
				Menu menuHelps = new Menu(menuHelp);
				menuHelp.setMenu(menuHelps);

				// 主菜单-帮助-使用说明
				MenuItem menuGuide = new MenuItem(menuHelps, SWT.CASCADE);
				menuGuide.addSelectionListener(new AGuide(this));
				menuGuide.setText(Lang.getString("FMain.menuGuide.text"));

				// 主菜单-帮助-系统信息
				MenuItem menuSystem = new MenuItem(menuHelps, SWT.CASCADE);
				menuSystem.addSelectionListener(new ASystem(this));
				menuSystem.setText(Lang.getString("FMain.menuSystemInfo.text"));

				new MenuItem(menuHelps, SWT.SEPARATOR);

				// 主菜单-帮助-关于
				MenuItem menuAbout = new MenuItem(menuHelps, SWT.CASCADE);
				menuAbout.setText(Lang.getString("FMain.menuAbout.text"));
			}
			tools = new CoolBar(shell, SWT.FLAT);
			FormData fd_tools = new FormData();
			fd_tools.top = new FormAttachment(0);
			fd_tools.left = new FormAttachment(0);
			fd_tools.right = new FormAttachment(100, 0);
			tools.setLayoutData(fd_tools);
			{
				CoolItem coolServo = new CoolItem(tools, SWT.NONE);
				coolServo.setSize(new Point(100, 0));

				toolServo = new ToolBar(tools, SWT.FLAT | SWT.RIGHT);
				coolServo.setControl(toolServo);

				// 工具栏-连接伺服器
				toolServoConnect = new ToolItem(toolServo, SWT.NONE);
				toolServoConnect.setToolTipText(Lang.getString("FMain.toolConnServo.text"));
				toolServoConnect.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));
				toolServoConnect.addSelectionListener(new AServoConnect(this));

				// 工具栏-断开伺服器
				ToolItem toolServoDisconnect = new ToolItem(toolServo, SWT.NONE);
				toolServoDisconnect.setToolTipText(Lang.getString("FMain.toolDisConnServo.text"));
				toolServoDisconnect
						.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_disconnected.png"));
				toolServoDisconnect.addSelectionListener(new AServoDisconnect(this));

				CoolItem coolViews = new CoolItem(tools, SWT.NONE);
				coolViews.setSize(new Point(100, 22));

				toolViews = new ToolBar(tools, SWT.FLAT | SWT.RIGHT);
				coolViews.setControl(toolViews);

				// 工具栏-结构视图
				toolViewTree = new ToolItem(toolViews, SWT.CHECK);
				toolViewTree.addSelectionListener(viewsListener);
				toolViewTree.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/structure_view.png"));
				toolViewTree.setToolTipText(Lang.getString("FMain.menuStructureView.text"));
				toolViewTree.setSelection(true);

				// 工具栏-状态视图
				toolViewState = new ToolItem(toolViews, SWT.CHECK);
				toolViewState.addSelectionListener(viewsListener);
				toolViewState.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/state_view.png"));
				toolViewState.setToolTipText(Lang.getString("FMain.menuStatuView.text"));
				toolViewState.setSelection(true);

				// 工具栏-消息视图
				toolViewMessage = new ToolItem(toolViews, SWT.CHECK);
				toolViewMessage.addSelectionListener(viewsListener);
				toolViewMessage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/information_view.png"));
				toolViewMessage.setToolTipText(Lang.getString("FMain.menuMessageView.text"));
				toolViewMessage.setSelection(true);
			}

			// 地图控制工具栏
			CoolItem coolMap = new CoolItem(tools, SWT.NONE);
			coolMap.setSize(new Point(100, 22));

			ToolBar toolBar = new ToolBar(tools, SWT.FLAT | SWT.RIGHT);
			coolMap.setControl(toolBar);

			{
				// 鼠标
				ToolItem toolMapNormal = new ToolItem(toolBar, SWT.NONE);
				toolMapNormal.addSelectionListener(toolMapNormalSelectionAdapter);
				toolMapNormal.setToolTipText(Lang.getString("FMain.toolMapNormal.text"));
				toolMapNormal.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mouse_pointer.png"));

				// 移动
				ToolItem toolMapPan = new ToolItem(toolBar, SWT.NONE);
				toolMapPan.addSelectionListener(toolMapPanSelectionAdapter);
				toolMapPan.setToolTipText(Lang.getString("FMain.toolMapPan.text"));
				toolMapPan.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mapview_pan.png"));

				// 放大
				ToolItem toolMapIn = new ToolItem(toolBar, SWT.NONE);
				toolMapIn.addSelectionListener(toolMapPanSelectionAdapter);
				toolMapIn.setToolTipText(Lang.getString("FMain.toolMapIn.text"));
				toolMapIn.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mapview_in.png"));

				// 缩小
				ToolItem toolMapOut = new ToolItem(toolBar, SWT.NONE);
				toolMapOut.addSelectionListener(toolMapOutSelectionAdapter);
				toolMapOut.setToolTipText(Lang.getString("FMain.toolMapOut.text"));
				toolMapOut.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mapview_out.png"));

				// 旋转
				ToolItem toolMapRotate = new ToolItem(toolBar, SWT.NONE);
				toolMapRotate.addSelectionListener(toolMapRotateSelectionAdapter);
				toolMapRotate.setToolTipText(Lang.getString("FMain.toolMapRotate.text"));
				toolMapRotate.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mapview_rotate.png"));

				// 重置
				ToolItem toolMapReset = new ToolItem(toolBar, SWT.NONE);
				toolMapReset.setToolTipText(Lang.getString("FMain.toolMapReset.text"));
				toolMapReset.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/mapview_reset.png"));
				toolMapReset.addSelectionListener(toolMapResetSelectionAdapter);

				// 添加设备
				ToolItem toolMapAddDevice = new ToolItem(toolBar, SWT.NONE);
				toolMapAddDevice.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_add.png"));
				toolMapAddDevice.setToolTipText(Lang.getString("FMain.addDevice.text"));
				toolMapAddDevice.addSelectionListener(toolMapAddDeviceSelectionAdapter);

				// 图层
				toolMapLayers = new ToolItem(toolBar, SWT.DROP_DOWN);
				toolMapLayers.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/layer.png"));
				toolMapLayers.setToolTipText(Lang.getString("FMain.toolMapLayers.text"));
				toolMapLayers.addListener(SWT.Selection, toolMapLayersListener);

			}

			CoolItem coolSearch = new CoolItem(tools, SWT.NONE);

			toolSearch = new ToolBar(tools, SWT.FLAT | SWT.RIGHT);
			coolSearch.setControl(toolSearch);

			// 设备厂商
			toolSearchDeviceVendor = new ToolItem(toolSearch, SWT.DROP_DOWN);
			toolSearchDeviceVendor
					.setText(Lang.getString("FMain.tltmNewItem.text_5") + "(" + Lang.getString("FMain.All") + ")"); //$NON-NLS-1$
			toolSearchDeviceVendor.setToolTipText(Lang.getString("FMain.All"));

			// 设备厂商选择菜单
			menuDeviceVendor = new Menu(shell, SWT.POP_UP);

			menuVendorAll = new MenuItem(menuDeviceVendor, SWT.CHECK);
			menuVendorAll.setText(Lang.getString("FMain.All"));
			menuVendorAll.setSelection(true);

			new MenuItem(menuDeviceVendor, SWT.SEPARATOR);

			for (Vendor vendor : Vendor.values()) {
				MenuItem menuItem = new MenuItem(menuDeviceVendor, SWT.CHECK);
				menuItem.setText(Lang.getString("Vendor." + vendor.name()));
				menuItem.setData(vendor);
				menuItem.setSelection(true);
			}

			menuVendorAll.addSelectionListener(menuVendorAllSelectionAdapter);

			toolSearchDeviceVendor.addListener(SWT.Selection, toolSearchDeviceVendorListener);

			// 设备类型
			toolSearchDeviceType = new ToolItem(toolSearch, SWT.DROP_DOWN);
			toolSearchDeviceType
					.setText(Lang.getString("FMain.tltmNewItem.text_6") + "(" + Lang.getString("FMain.All") + ")");
			toolSearchDeviceType.setToolTipText(Lang.getString("FMain.All"));

			menuDeviceType = new Menu(shell, SWT.POP_UP);

			menuTypeAll = new MenuItem(menuDeviceType, SWT.CHECK);
			menuTypeAll.setText(Lang.getString("FMain.All"));
			menuTypeAll.setSelection(true);

			new MenuItem(menuDeviceType, SWT.SEPARATOR);

			for (Kind kind : Kind.values()) {
				MenuItem menuItem = new MenuItem(menuDeviceType, SWT.CHECK);
				menuItem.setText(Lang.getString("Kind." + kind.name()));
				menuItem.setData(kind);
				menuItem.setSelection(true);
			}

			menuTypeAll.addSelectionListener(menuTypeAllSelectionAdapter);

			toolSearchDeviceType.addListener(SWT.Selection, toolSearchDeviceTypeListener);

			// 设备连接方式
			toolSearchDeviceLink = new ToolItem(toolSearch, SWT.DROP_DOWN);
			toolSearchDeviceLink
					.setText(Lang.getString("FMain.tltmNewItem.text_7") + "(" + Lang.getString("FMain.All") + ")"); //$NON-NLS-1$
			toolSearchDeviceLink.setToolTipText(Lang.getString("FMain.All"));

			// 设备连接方式选择菜单
			menuDeviceLink = new Menu(shell, SWT.POP_UP);

			menuLinkAll = new MenuItem(menuDeviceLink, SWT.CHECK);
			menuLinkAll.setText(Lang.getString("FMain.All"));
			menuLinkAll.setSelection(true);

			new MenuItem(menuDeviceLink, SWT.SEPARATOR);

			for (Link link : Link.values()) {
				MenuItem menuItem = new MenuItem(menuDeviceLink, SWT.CHECK);
				menuItem.setText(Lang.getString("Link." + link.name()));
				menuItem.setData(link);
				menuItem.setSelection(true);
			}

			menuLinkAll.addSelectionListener(menuLinkAllSelectionAdapter);

			toolSearchDeviceLink.addListener(SWT.Selection, toolSearchDeviceLinkListener);

			MenuItem[] itemsVendor = menuDeviceVendor.getItems();
			for (MenuItem item : itemsVendor) {
				item.addSelectionListener(itemsVendorSelectionAdapter);
			}

			MenuItem[] itemsType = menuDeviceType.getItems();
			for (MenuItem item : itemsType) {
				item.addSelectionListener(itemsTypeSelectionAdapter);
			}

			MenuItem[] itemsLink = menuDeviceLink.getItems();
			for (MenuItem item : itemsLink) {
				item.addSelectionListener(itemsLinkSelectionAdapter);
			}

			CoolItem coolSearchText = new CoolItem(tools, SWT.NONE);

			ToolBar toolSearchText = new ToolBar(tools, SWT.FLAT | SWT.RIGHT);

			coolSearchText.setControl(toolSearchText);

			ToolItem textItem = new ToolItem(toolSearchText, SWT.SEPARATOR);
			text = new Text(toolSearchText, SWT.SINGLE | SWT.BORDER);
			text.pack();
			textItem.setWidth(200);
			text.setMessage(Lang.getString("FMain.Search"));
			textItem.setControl(text);

			/**
			 * search device by text
			 */
			text.addModifyListener(textSearchModifyListener);

			toolResize(tools);

			formHR = new SashForm(shell, SWT.SMOOTH);
			fd_tools.bottom = new FormAttachment(formHR);
			FormData fd_formHR = new FormData();
			if (THIS_IS_MAC) {
				fd_formHR.top = new FormAttachment(0, 24);
			} else {
				fd_formHR.top = new FormAttachment(0, 32);
			}

			fd_formHR.left = new FormAttachment(0, 3);
			fd_formHR.right = new FormAttachment(100, -3);
			formHR.setLayoutData(fd_formHR);
			{
				tabFolderTrees = new CTabFolder(formHR, SWT.BORDER);
				tabFolderTrees.addCTabFolder2Listener(ctabFollderListener);
				tabFolderTrees.setMinimizeVisible(true);
				tabFolderTrees.setMaximizeVisible(true);

				// 结构视图选项卡-设备区域视图
				tabTreeZones = new CTabItem(tabFolderTrees, SWT.NONE);
				tabTreeZones.setText(Lang.getString("FMain.menuDeviceZoneView.text"));
				tabTreeZones.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/area.png"));

				// 区域视图树
				treeZones = new Tree(tabFolderTrees, SWT.VIRTUAL);

				Menu menu_1 = new Menu(treeZones);
				treeZones.setMenu(menu_1);
				treeZones.addListener(SWT.Selection, treeZonesListener);
				treeZones.addListener(SWT.MouseDoubleClick, treeZonesDoubleClickListener);
				tabTreeZones.setControl(treeZones);
				treeZoneView = new TreeZoneView(treeZones);

				{
					// 区域视图右键菜单-连接
					menuTreeZonesServoConnect = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZonesServoConnect.setText(Lang.getString("FMain.menuConnect.text"));
					menuTreeZonesServoConnect
							.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));
					menuTreeZonesServoConnect.addSelectionListener(new AServoConnect(this));

					// 区域视图右键菜单-断开
					menuTreeZonesServoDisconnect = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZonesServoDisconnect.setText(Lang.getString("FMain.menuServoDisconnect.text")); //$NON-NLS-1$
					menuTreeZonesServoDisconnect
							.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_disconnected.png"));
					menuTreeZonesServoDisconnect.addSelectionListener(new AServoDisconnect(this));

					// missing
					MenuItem menuItem = new MenuItem(menu_1, SWT.SEPARATOR);
					menuItem.setText(Lang.getString("FMain.other.text"));

					// 区域视图右键菜单-新建item
					MenuItem menuTreeZoneCreate = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZoneCreate.setText(Lang.getString("FMain.mntmNewSubmenu.text")); //$NON-NLS-1$

					// 区域视图右键菜单-新建
					Menu menu_2 = new Menu(menuTreeZoneCreate);
					menuTreeZoneCreate.setMenu(menu_2);

					// 区域视图右键菜单-新建-伺服器
					MenuItem menuTreeZoneCreateServo = new MenuItem(menu_2, SWT.CALENDAR);
					menuTreeZoneCreateServo.setText(Lang.getString("FMain.menuServoCreate.text") + "\tAlt+Shift+S");
					menuTreeZoneCreateServo
							.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/servo_connected.png"));
					menuTreeZoneCreateServo.setAccelerator(SWT.ALT + SWT.SHIFT + 'S');
					menuTreeZoneCreateServo.addSelectionListener(new AServoCreate(this));

					// 区域视图右键菜单-新建-设备
					MenuItem menuTreeZoneCreateDevice = new MenuItem(menu_2, SWT.CASCADE);
					menuTreeZoneCreateDevice.setText(Lang.getString("FMain.menuCreateDevice.text")); //$NON-NLS-1$ 璁惧...
					menuTreeZoneCreateDevice
							.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_create.png"));
					menuTreeZoneCreateDevice.setData(new ActionCase[] { ActionCase.CREATE_DEVICE });

					// 主菜单-伺服器-新建-新建设备
					Menu menuCreatesDevice = new Menu(menuTreeZoneCreateDevice);
					menuTreeZoneCreateDevice.setMenu(menuCreatesDevice);
					
					F.createDeviceMenu(menuCreatesDevice, this);

					// 区域视图右键菜单-新建-区域
					MenuItem menuTreeZoneCreateZone = new MenuItem(menu_2, SWT.CASCADE);
					menuTreeZoneCreateZone.setText(Lang.getString("FMain.menuCreateZone.text") + "\tAlt+Shift+Z");
					menuTreeZoneCreateZone.addSelectionListener(new ACreateZone(this));
					menuTreeZoneCreateZone.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/zone.png"));
					menuTreeZoneCreateZone.setAccelerator(SWT.ALT + SWT.SHIFT + 'Z');
					menuTreeZoneCreateZone.setData(new ActionCase[] { ActionCase.CREATE_ZONE });

					// 区域视图右键菜单-新建-设备维护
					MenuItem menuTreeZoneCreateMaintain = new MenuItem(menu_2, SWT.PUSH);
					menuTreeZoneCreateMaintain.setText(Lang.getString("FMain.menuMainTain.text") + "\tAlt+shift+M");
					menuTreeZoneCreateMaintain.addSelectionListener(new AMaintainCreate(this));
					menuTreeZoneCreateMaintain.setAccelerator(SWT.ALT + SWT.SHIFT + 'M');
					menuTreeZoneCreateMaintain.setData(new ActionCase[] { ActionCase.CREATE_MAINTAIN });

					// 区域视图右键菜单-删除
					MenuItem menuTreeZoneDelete = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZoneDelete.setText(Lang.getString("FMain.menuServoDelete.text") + "\tDelete");
					menuTreeZoneDelete.addSelectionListener(new ADeleteUnit(this));
					menuTreeZoneDelete.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/delete.png"));
					menuTreeZoneDelete.setAccelerator(SWT.DEL);
					menuTreeZoneDelete.setData("tag", "delete");

					// 区域视图右键菜单-伺服器配置
					MenuItem menuTreeZoneServoConfig = new MenuItem(menu_1, SWT.PUSH);
					menuTreeZoneServoConfig.setText(Lang.getString("FMain.menuServoConfig.text"));
					menuTreeZoneServoConfig.addSelectionListener(new AServoConfig(this));
					menuTreeZoneServoConfig
							.setData(new ActionCase[] { ActionCase.GET_SERVO_CONFIG, ActionCase.SET_SERVO_CONFIG });

					// 区域视图右键菜单-维护记录
					MenuItem menuTreeZoneMaintainRecords = new MenuItem(menu_1, SWT.PUSH);
					menuTreeZoneMaintainRecords.setText(Lang.getString("FMain.menuMainTainRecords.text"));
					menuTreeZoneMaintainRecords.addSelectionListener(new AMainTainRecords(this));
					menuTreeZoneMaintainRecords.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_MAINTAIN });

					// 区域视图右键菜单-历史状态
					MenuItem menuTreeZoneHistoryStatus = new MenuItem(menu_1, SWT.PUSH);
					menuTreeZoneHistoryStatus.setText(Lang.getString("FMain.menuHistoryStatus.text"));
					menuTreeZoneHistoryStatus.addSelectionListener(new AHistoryDeviceStatus(this));
					menuTreeZoneHistoryStatus.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_STATUS });

					// 区域视图右键菜单-设置
					MenuItem menuTreeZoneSetting = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZoneSetting.setText(Lang.getString("FMain.menuServoProperty.text"));
					menuTreeZoneSetting.addSelectionListener(new AUpdateUnit(this));
					menuTreeZoneSetting.setAccelerator(SWT.ALT + SWT.CR);
					menuTreeZoneSetting.setData("tag", "property");

					// 区域视图右键菜单-属性
					MenuItem menuTreeZoneProperty = new MenuItem(menu_1, SWT.CASCADE);
					menuTreeZoneProperty.setText(Lang.getString("FMain.menuServoModify.text"));
					menuTreeZoneProperty.addSelectionListener(new ACheckProperty(this));

					menu_1.addMenuListener(zoneViewMenuAdapter);
					menu_2.addMenuListener(zoneViewCreateMenuAdapter);
				}

				// 结构视图选项卡-设备中继视图
				tabTreeRelays = new CTabItem(tabFolderTrees, SWT.NONE);
				tabTreeRelays.setText(Lang.getString("FMain.menuDeviceRelayView.text"));
				tabTreeRelays.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/repeaters_view.png"));

				treeRelays = new Tree(tabFolderTrees, SWT.VIRTUAL | SWT.MULTI);
				tabTreeRelays.setControl(treeRelays);
				Menu relayRMenu = menu_1;
				treeRelays.setMenu(relayRMenu);
				treeRelays.addListener(SWT.Selection, treeRelaysListener);
				treeRelayView = new TreeRelayView(treeRelays);

				formVR = new SashForm(formHR, SWT.SMOOTH | SWT.VERTICAL);
				{
					tabFolderStates = new CTabFolder(formVR, SWT.BORDER);
					tabFolderStates.addCTabFolder2Listener(ctabFollderListener);
					tabFolderStates.setMinimizeVisible(true);
					tabFolderStates.setMaximizeVisible(true);

					// 状态树形视图
					tabViewTree = new CTabItem(tabFolderStates, SWT.NONE);
					tabViewTree.setText(Lang.getString("FMain.TreeViewPlane.text"));
					tabViewTree.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/view_tree.png"));

					treeDeviceDetails = new Tree(tabFolderStates, SWT.VIRTUAL | SWT.FULL_SELECTION | SWT.MULTI);
					treeView = new TreeView(treeDeviceDetails);
					tabViewTree.setControl(treeDeviceDetails);
					treeDeviceDetails.addListener(SWT.MouseDoubleClick, setDeviceStatusTreeListDoubleClick);
					treeDeviceDetails.addSelectionListener(tableListSelectionAdapter);

					{
						Menu menutreeDeviceDetails = new Menu(shell, SWT.POP_UP);

						// 状态树形视图右键菜单-获取状态
						MenuItem menuRDeviceGetStatus1 = new MenuItem(menutreeDeviceDetails, SWT.NONE);
						menuRDeviceGetStatus1.setText(Lang.getString("FMain.mntmNewItem_8.text"));
						menuRDeviceGetStatus1.addSelectionListener(new AGetDeviceStatus(this));
						menuRDeviceGetStatus1.setData(new ActionCase[] { ActionCase.READ_DEVICE_STATUS });

						// 状态树形视图右键菜单-设置状态
						MenuItem menuRDeviceSetStatus1 = new MenuItem(menutreeDeviceDetails, SWT.PUSH);
						menuRDeviceSetStatus1.setText(Lang.getString("FMain.menuStatus.text"));
						menuRDeviceSetStatus1.addSelectionListener(new ASetDeviceStatus(this));
						menuRDeviceSetStatus1.setData(new ActionCase[] { ActionCase.WRITE_DEVICE_STATUS });

						new MenuItem(menutreeDeviceDetails, SWT.SEPARATOR);

						// 状态树形视图右键菜单-维护记录
						MenuItem mainTainRecordItem1 = new MenuItem(menutreeDeviceDetails, SWT.PUSH);
						mainTainRecordItem1.setText(Lang.getString("FMain.menuMainTainRecords.text"));
						mainTainRecordItem1.addSelectionListener(new AMainTainRecords(this));
						mainTainRecordItem1.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_MAINTAIN });

						// 状态树形视图右键菜单-历史状态
						MenuItem historyStatuItem1 = new MenuItem(menutreeDeviceDetails, SWT.PUSH);
						historyStatuItem1.setText(Lang.getString("FMain.menuHistoryStatus.text"));
						historyStatuItem1.addSelectionListener(new AHistoryDeviceStatus(this));
						historyStatuItem1.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_STATUS });

						new MenuItem(menutreeDeviceDetails, SWT.SEPARATOR);

						// 状态树形视图右键菜单-属性
						MenuItem deviceModifyItem1 = new MenuItem(menutreeDeviceDetails, SWT.PUSH);
						deviceModifyItem1.setText(Lang.getString("FMain.menuServoProperty.text"));
						deviceModifyItem1.addSelectionListener(new AUpdateUnit(this));
						deviceModifyItem1.setData("tag", "property");

						MenuItem deviceRoperty1 = new MenuItem(menutreeDeviceDetails, SWT.CASCADE);
						deviceRoperty1.setText(Lang.getString("FMain.menuServoModify.text")); //$NON-NLS-1$ 灞炴��...
						deviceRoperty1.addSelectionListener(new ACheckProperty(this));

						MenuItem devicedelete1 = new MenuItem(menutreeDeviceDetails, SWT.CASCADE);
						devicedelete1.setText(Lang.getString("FMain.menuServoDelete.text"));
						devicedelete1.addSelectionListener(new ADeleteUnit(this));
						devicedelete1.setData(new ActionCase[] { ActionCase.DELETE_DEVICE });

						new MenuItem(menutreeDeviceDetails, SWT.SEPARATOR);

						MenuItem expandAll = new MenuItem(menutreeDeviceDetails, SWT.CASCADE);
						expandAll.setText(Lang.getString("FMain.menuExpandAll.text"));
						expandAll.addSelectionListener(expandAllSelectionAdapter);

						MenuItem packUpAll = new MenuItem(menutreeDeviceDetails, SWT.CASCADE);
						packUpAll.setText(Lang.getString("FMain.menuPackUpAll.text"));
						packUpAll.addSelectionListener(packUpAllSelectionAdapter);

						menutreeDeviceDetails.addMenuListener(menutreeDeviceDetailsMenuAdapter);
						treeDeviceDetails.setMenu(menutreeDeviceDetails);

					}

					// 平面视图
					tabViewPlane = new CTabItem(tabFolderStates, SWT.NONE);
					tabViewPlane.setText(Lang.getString("FMain.tabViewPlane.text"));
					tabViewPlane.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/view_plan.png"));

					mapPane = new SwtMapPane(tabFolderStates, SWT.NO_BACKGROUND);
					mapView = new MapView(mapPane, this);
					mapView.show(getSelectionServo());
					tabViewPlane.setControl(mapPane);

					// 消息视图
					tabFolderMessages = new CTabFolder(formVR, SWT.BORDER);
					tabFolderMessages.addCTabFolder2Listener(ctabFollderListener);
					tabFolderMessages.setMinimizeVisible(true);
					tabFolderMessages.setMaximizeVisible(true);

					Menu clearMenu = new Menu(shell, SWT.POP_UP);
					MenuItem clearItem = new MenuItem(clearMenu, SWT.PUSH);

					// 消息视图右键-查看详情
					MenuItem openDetailsItem = new MenuItem(clearMenu, SWT.PUSH);
					openDetailsItem.setText(Lang.getString("FMain.tableNotice.getDetails.text"));
					openDetailsItem.setData(new ActionCase[] { ActionCase.WRITE_DEVICE_STATUS });

					// 消息视图右键-清空
					clearItem.setText(Lang.getString("FMain.tblClearAll.text"));
					clearItem.addSelectionListener(clearItemSelectionAdapter);
					clearMenu.addMenuListener(clearMenuAdapter);

					// 消息视图通知选项卡
					CTabItem tbtmNotice = new CTabItem(tabFolderMessages, SWT.NONE);
					tbtmNotice.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/notice.png"));

					// 消息视图通知表格
					tableNotice = new Table(tabFolderMessages, SWT.FULL_SELECTION);
					// 双击查看通知详情
					tableNotice.addListener(SWT.MouseDoubleClick, setDeviceStatusTableNoticeDoubleClick);

					tbtmNotice.setText(Lang.getString("FMain.tblNotice.text")); //$NON-NLS-1$
					tbtmNotice.setControl(tableNotice);
					tableNotice.setHeaderVisible(true);
					tableNotice.setLinesVisible(true);
					tableNotice.setMenu(clearMenu);

					// 消息视图通知表格-设备
					TableColumn tblclmnNewColumn = new TableColumn(tableNotice, SWT.NONE);
					tblclmnNewColumn.setWidth(130);
					tblclmnNewColumn.setText(Lang.getString("FMain.tblDevice.text")); //$NON-NLS-1$

					// 消息视图通知表格-时间
					TableColumn tblclmnNewTime = new TableColumn(tableNotice, SWT.NONE);
					tblclmnNewTime.setWidth(155);
					tblclmnNewTime.setText(Lang.getString("FMain.tblTime.text")); //$NON-NLS-1$

					// 消息视图通知表格-状态
					TableColumn tblclmnNewColumn_2 = new TableColumn(tableNotice, SWT.NONE);
					tblclmnNewColumn_2.setWidth(130);
					tblclmnNewColumn_2.setText(Lang.getString("FMain.tblStatus.text")); // 鐘舵��

					// 消息视图通知表格-消息
					tabMessage = new CTabItem(tabFolderMessages, SWT.NONE);
					tabMessage.setText(Lang.getString("FMain.tblMessage.text"));
					tabMessage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/message.png"));

					// 消息视图消息表格
					tableMessage = new Table(tabFolderMessages, SWT.FULL_SELECTION);

					// 双击打开设备状态
					tableMessage.addListener(SWT.MouseDoubleClick, setDeviceStatusTableMessageDoubleClick);
					tabMessage.setControl(tableMessage);
					tableMessage.setHeaderVisible(true);
					tableMessage.setLinesVisible(true);
					tableMessage.setMenu(clearMenu);

					openDetailsItem
							.addSelectionListener(new ASetDeviceStatusOnMessageTable(this, tableNotice, tableMessage));

					// 消息视图消息表格-消息源
					TableColumn tblclmnServo = new TableColumn(tableMessage, SWT.NONE);
					tblclmnServo.setWidth(100);
					tblclmnServo.setText(Lang.getString("FMain.tblResource.text"));

					// 消息视图消息表格-实体
					TableColumn tblclmnUnit = new TableColumn(tableMessage, SWT.NONE);
					tblclmnUnit.setWidth(100);
					tblclmnUnit.setText(Lang.getString("FMain.tblUnit.text"));

					// 消息视图消息表格-时间
					TableColumn tblclmnTime = new TableColumn(tableMessage, SWT.NONE);
					tblclmnTime.setWidth(155);
					tblclmnTime.setText(Lang.getString("FMain.tblTime.text"));

					// 消息视图消息表格-指令
					TableColumn tblclmnCommand = new TableColumn(tableMessage, SWT.NONE);
					tblclmnCommand.setWidth(120);
					tblclmnCommand.setText(Lang.getString("FMain.tblCommand.text"));

					// 消息视图消息表格-结果
					TableColumn tblclmnStatus = new TableColumn(tableMessage, SWT.NONE);
					tblclmnStatus.setWidth(120);
					tblclmnStatus.setText(Lang.getString("FMain.tblResult.text"));

					// 消息视图消息表格-详情
					TableColumn tblclmnError = new TableColumn(tableMessage, SWT.NONE);
					tblclmnError.setWidth(150);
					tblclmnError.setText(Lang.getString("FMain.tblDetail.text"));
				}
				formVR.setWeights(new int[] { 72, 28 });
			}

			status = new Composite(shell, SWT.NONE);
			fd_formHR.bottom = new FormAttachment(status);
			formHR.setWeights(new int[] { 255, 520 });
			RowLayout rl_status = new RowLayout(SWT.HORIZONTAL);
			rl_status.fill = true;
			status.setLayout(rl_status);
			FormData fd_status = new FormData();
			fd_status.bottom = new FormAttachment(100);
			fd_status.right = new FormAttachment(100, 0);
			fd_status.left = new FormAttachment(0);
			status.setLayoutData(fd_status);
			{
				labelStatusServo = new CLabel(status, SWT.NONE);
				labelStatusServo.setLayoutData(new RowData(115, SWT.DEFAULT));

				labelStatusUser = new CLabel(status, SWT.NONE);
				labelStatusUser.setLayoutData(new RowData(111, SWT.DEFAULT));

				labelStatusSelect = new CLabel(status, SWT.NONE);
				labelStatusSelect.setLayoutData(new RowData(200, SWT.DEFAULT));
			}
		}

		shell.addShellListener(shellAdapter);

		menuServos.addMenuListener(menuServosMenuAdapter);

		menuCreates.addMenuListener(menuCreatesMenuAdapter);

		menuDevices.addMenuListener(menuDevicesMenuAdapter);

	}

	/**
	 * 修订coolBar位置误差
	 * 
	 * @param coolBar
	 */
	private void toolResize(CoolBar coolBar) {
		// 修订CoolBar位置误差
		CoolItem[] coolItems = coolBar.getItems();
		for (int i = 0; i < coolItems.length; i++) {
			CoolItem coolItem = coolItems[i];
			Control control = coolItem.getControl();

			Point controlSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point coolItemSize = coolItem.computeSize(controlSize.x, controlSize.y);

			if (control instanceof ToolBar) {
				ToolBar toolBar = (ToolBar) control;
				if (toolBar.getItemCount() > 0) {
					controlSize.x = toolBar.getItem(0).getWidth();
				}
			}
			coolItem.setMinimumSize(controlSize);
			coolItem.setPreferredSize(coolItemSize);
			coolItem.setSize(coolItemSize);
		}
	}

	/**
	 * 加载
	 */
	private void load() {
		shell.setEnabled(false);
		servoManager = new ServoManager(this);
		final FLoading loading = new FLoading(shell);

		CtrClient.getES().execute(new Runnable() {
			@Override
			public void run() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						loading.open();
					}
				});
				try {
					servoManager.load();
					mapView.load();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						refreshTree();

						tabFolderTrees.setSelection(tabTreeZones);
						tabFolderStates.setSelection(tabViewTree);
						tabFolderMessages.setSelection(tabMessage);

						mapView.show(getSelectionServo());
						shell.setEnabled(true);

						loading.close();
					}
				});
				// 自动连接
				for (Servo servo : servoManager.getAll()) {
					if (servo.isAutoConnect()) {
						CtrClient client = new CtrClient(servo);
						servo.setClient(client);
						client.setHandler(servoManager.getHandler());
						client.start();
					}
				}
			}
		});

	}

	/**
	 * 状态栏还原
	 */
	private void resetViews() {
		// 三个视图的显示还原

		tabFolderTrees.setMaximized(false);
		tabFolderTrees.setMinimized(false);
		tabFolderStates.setMaximized(false);
		tabFolderStates.setMinimized(false);
		tabFolderMessages.setMaximized(false);
		tabFolderMessages.setMinimized(false);
		formHR.setMaximizedControl(null);
		formVR.setMaximizedControl(null);
		// 选项卡的默认还原
		tabFolderTrees.setSelection(tabTreeZones);
		tabFolderStates.setSelection(tabViewTree);
		tabFolderMessages.setSelection(tabMessage);
		// 栏的还原
		tools.setVisible(true);
		status.setVisible(true);
		// 菜单栏还原
		menuViewTree.setSelection(true);
		menuViewState.setSelection(true);
		menuViewMessage.setSelection(true);

		menuBarTools.setSelection(true);
		menuBarStatus.setSelection(true);

		toolViewTree.setSelection(true);
		toolViewState.setSelection(true);
		toolViewMessage.setSelection(true);

	}

	private CTabFolder2Adapter ctabFollderListener = new CTabFolder2Adapter() {
		// CTabFolder相关事件
		@Override
		public void maximize(CTabFolderEvent e) {
			CTabFolder folder = (CTabFolder) e.getSource();
			folder.setMaximized(true);
			folder.setMinimized(false);
			viewsListener.widgetSelected(null);
		}

		@Override
		public void minimize(CTabFolderEvent e) {
			CTabFolder folder = (CTabFolder) e.getSource();
			folder.setMaximized(false);
			folder.setMinimized(true);
			viewsListener.widgetSelected(null);
		}

		@Override
		public void restore(CTabFolderEvent e) {
			CTabFolder folder = (CTabFolder) e.getSource();
			folder.setMaximized(false);
			folder.setMinimized(false);
			viewsListener.widgetSelected(null);
		}

		@Override
		public void close(CTabFolderEvent e) {
		}
	};

	private SelectionAdapter viewsListener = new SelectionAdapter() {
		// 视图相关菜单和工具栏事件
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e == null) {
				menuViewTree.setSelection(!tabFolderTrees.getMinimized());
				menuViewState.setSelection(!tabFolderStates.getMinimized());
				menuViewMessage.setSelection(!tabFolderMessages.getMinimized());

				toolViewTree.setSelection(menuViewTree.getSelection());
				toolViewState.setSelection(menuViewState.getSelection());
				toolViewMessage.setSelection(menuViewMessage.getSelection());
			} else {
				Object source = e.getSource();
				if (source != null) {
					if (source instanceof ToolItem) {
						menuViewTree.setSelection(toolViewTree.getSelection());
						menuViewState.setSelection(toolViewState.getSelection());
						menuViewMessage.setSelection(toolViewMessage.getSelection());
					}
					if (source instanceof MenuItem) {
						toolViewTree.setSelection(menuViewTree.getSelection());
						toolViewState.setSelection(menuViewState.getSelection());
						toolViewMessage.setSelection(menuViewMessage.getSelection());
					}

					if (menuViewTree.getSelection()) {
						tabFolderTrees.setMinimized(false);
					} else {
						tabFolderTrees.setMinimized(true);
					}
					if (menuViewState.getSelection()) {
						tabFolderStates.setMinimized(false);
					} else {
						tabFolderStates.setMinimized(true);
					}
					if (menuViewMessage.getSelection()) {
						tabFolderMessages.setMinimized(false);
					} else {
						tabFolderMessages.setMinimized(true);
					}
				}
			}

			if (tabFolderTrees.getMaximized()) {
				formHR.setMaximizedControl(tabFolderTrees);
			} else if (tabFolderStates.getMaximized()) {
				formHR.setMaximizedControl(formVR);
				formVR.setMaximizedControl(tabFolderStates);
			} else if (tabFolderMessages.getMaximized()) {
				formHR.setMaximizedControl(formVR);
				formVR.setMaximizedControl(tabFolderMessages);
			} else {
				formHR.setMaximizedControl(null);
				formVR.setMaximizedControl(null);
			}

			if (tabFolderTrees.getMinimized()) {
				formHR.setMaximizedControl(formVR);
			}
			if (tabFolderStates.getMinimized()) {
				formVR.setMaximizedControl(tabFolderMessages);
			}
			if (tabFolderMessages.getMinimized()) {
				formVR.setMaximizedControl(tabFolderStates);
			}

		}
	};

	/**
	 * 添加通知
	 */
	public void addNotice(Device d) {
		if (d == null)
			return;

		if (d.getStatus() == Status.NONE)
			return;

		TableItem item = new TableItem(tableNotice, SWT.NONE, 0);
		item.setText(0, d.getName());// 设备
		item.setText(1, F.format(new Date()));// 时间
		item.setText(2, d.getName() + Lang.getString("AlarmType." + d.getStatus().toString().toUpperCase()));// 状态
		item.setData(d);

		item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));

		Sound.play("/com/kiy/resources/alarm.wav");

		// 移除多余的消息
		if (tableNotice.getItemCount() > 1000)
			tableNotice.remove(999, tableNotice.getItemCount() - 1);
	}

	/**
	 * 添加消息列表
	 * 
	 * @param s
	 *            伺服器
	 * @param m
	 *            消息
	 */
	public void addMessage(final Servo s, final Message m, final Map<Message, Unit> units) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {

				Unit unit = null;
				if (units != null) {
					unit = units.get(m);
				}

				if (m.getActionCase() == ActionCase.FILE_DOWNLOAD || m.getActionCase() == ActionCase.FILE_UPLOAD) {
					return;
				}

				if (m.getKey() == 0) {
					if (m.getError()!=null&&!Tool.isEmpty(m.getError().toString())) {
						F.mbInformation(shell, "提示信息", m.getError().toString());
					}
					return;
				}

				if (tableMessage.getItemCount() > 1000) {
					tableMessage.remove(999, tableMessage.getColumnCount() - 1);
				}

				User user = null;

				if (getCurrentServo() != null) {
					String userId = m.getUserId();
					user = getCurrentServo().getUser(userId);
				}

				// if (m.getActionCase() == ActionCase.DEVICE_SET_STATUS && unit != null) {
				//
				//
				// }

				if (user == null) {
					TableItem[] items = tableMessage.getItems();
					for (int i = 0; i < items.length; i++) {
						Object data = items[i].getData("key");
						if (data != null) {
							if (m.getKey() == (int) data) {
								items[i].setText(1, unit == null ? Tool.EMPTY : units.get(m).getName());// 实体
								items[i].setText(4, Lang.getString("Status." + m.getResult().toString()));
								items[i].setText(5,Tool.getString(m.getError()));
								switch (m.getResult()) {
								case SUCCESS:
									items[i].setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/ok.png"));
									break;
								case DENIED:
									items[i].setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/deny.png"));
									break;
								case ERROR:
								case ILLEGAL_ARGUMENT:
								case UNRECOGNIZED:
								case UNSUPPORTED:
									items[i].setImage(0,
											Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));
									break;
								default:
									break;
								}
								return;
							}
						}

					}
					TableItem item = new TableItem(tableMessage, SWT.NONE, 0);
					item.setText(0, Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());// 源
					item.setText(1, unit == null ? Tool.EMPTY : unit.getName());// 实体
					item.setText(2, F.format(new Date()));// 时间
					item.setText(3, Lang.getString("Command." + m.getActionCase().toString()));// 指令
					item.setText(5, m.getError());// 详情
					item.setData(unit);
					item.setText(4, Lang.getString("FMain.request"));
					item.setData("key", m.getKey());
					switch (m.getResult()) {
					case SUCCESS:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/ok.png"));
						break;
					case DENIED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/deny.png"));
						break;
					case ERROR:
					case ILLEGAL_ARGUMENT:
					case UNRECOGNIZED:
					case UNSUPPORTED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));
						break;
					default:
						break;
					}

				} else if (user == s.getUser()) {
					// 自己发出的消息
					TableItem[] items = tableMessage.getItems();
					for (int i = 0; i < items.length; i++) {
						Object data = items[i].getData("key");
						if (data != null) {
							if (m.getKey() == (int) data) {
								items[i].setText(1, unit == null ? Tool.EMPTY : unit.getName());// 实体
								items[i].setText(4, Lang.getString("Status." + m.getResult().toString()));
								switch (m.getResult()) {
								case SUCCESS:
									items[i].setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/ok.png"));
									break;
								case DENIED:
									items[i].setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/deny.png"));
									break;
								case ERROR:
								case ILLEGAL_ARGUMENT:
								case UNRECOGNIZED:
								case UNSUPPORTED:
									items[i].setImage(0,
											Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));
									break;
								default:
									break;
								}
								return;
							}
						}

					}
					TableItem item = new TableItem(tableMessage, SWT.NONE, 0);
					item.setText(0, Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());// 源
					item.setText(1, unit == null ? Tool.EMPTY : unit.getName());// 实体
					item.setText(2, F.format(new Date()));// 时间
					item.setText(3, Lang.getString("Command." + m.getActionCase().toString()));// 指令
					item.setText(5, m.getError());// 详情
					item.setData(unit);
					item.setText(4, Lang.getString("FMain.request"));
					item.setData("key", m.getKey());
					switch (m.getResult()) {
					case SUCCESS:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/ok.png"));
						break;
					case DENIED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/deny.png"));
						break;
					case ERROR:
					case ILLEGAL_ARGUMENT:
					case UNRECOGNIZED:
					case UNSUPPORTED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));
						break;
					default:
						break;
					}

				} else {

					// 别人发出的消息
					TableItem item = new TableItem(tableMessage, SWT.NONE, 0);
					item.setText(0, Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());// 源
					item.setText(1, unit == null ? Tool.EMPTY : unit.getName());// 实体
					item.setText(2, F.format(new Date()));// 时间
					item.setText(3, Lang.getString("Command." + m.getActionCase().toString()));// 指令
					item.setText(4, Lang.getString("Status." + m.getResult().toString()));// 状态
					item.setText(5, m.getError());// 详情
					item.setData(unit);
					switch (m.getResult()) {
					case SUCCESS:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/ok.png"));
						break;
					case DENIED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/deny.png"));
						break;
					case ERROR:
					case ILLEGAL_ARGUMENT:
					case UNRECOGNIZED:
					case UNSUPPORTED:
						item.setImage(0, Resource.getImage(FMain.class, "/com/kiy/resources/warning.png"));
						break;
					default:
						break;
					}
				}
			}
		});
	}

	/**
	 * 状态栏更新
	 * 
	 */
	public void status() {
		Servo s = getCurrentServo();
		if (s == null) {
			labelStatusServo.setText(Tool.EMPTY);
		} else {
			labelStatusServo.setText(Tool.isEmpty(s.getName()) ? s.getIp() : s.getName());
			CtrClient client = s.getClient();
			if (client == null) {
				labelStatusUser.setText(Lang.getString("FMain.labelStatusUserNotConnect.text"));
			} else {
				if (client.isConnected()) {
					User u = s.getUser();
					if (u == null) {
						labelStatusUser.setText(Lang.getString("FMain.labelStatusUserNotLogin.text"));
					} else {
						labelStatusUser.setText(u.getName());
					}
				} else {
					labelStatusUser.setText(Lang.getString("FMain.labelStatusUserNotConnect.text"));
				}
			}
		}

	}

	/**
	 * 接收Message
	 */
	@Override
	public void received(Servo s, Message m, Map<Message, Unit> units) {
		switch (m.getActionCase()) {
		case CONNECT:
		case DISCONNECT:
		case HEARTBEAT:
			break;
		case LOGIN: {
			if (m.getResult() == Result.SUCCESS) {

			} else if (m.getResult() == Result.DENIED) {
				int open = F.mbWaringCancel(shell, Lang.getString("FMain.MessageBoxLoginTitle.text"),
						String.format(Lang.getString("FMain.MessageBoxLoginContent.text"), s));
				if (open == SWT.OK) {
					FLogin f = new FLogin(shell);
					if (f.open(s) == null) {
						s.getClient().stop();
					} else {
						servoManager.getHandler().connected(s.getClient());
					}
				}
			} else if (m.getResult() == Result.CONFLICT) {
				int open = F.mbWaringCancel(shell, Lang.getString("FMain.MessageBoxLoginTitle.text"),
						Lang.getString("FMain.MessageBoxLoginContent.confict"));
				if (open == SWT.OK) {
					FLogin f = new FLogin(shell);
					if (f.open(s) == null) {
						s.getClient().stop();
					} else {
						servoManager.getHandler().connected(s.getClient());
					}
				}
			} else if (m.getResult() == Result.NON_EXISTENT) {
				int open = F.mbWaringCancel(shell, Lang.getString("FMain.MessageBoxLoginTitle.text"),
						Lang.getString("FMain.MessageBoxLoginWrong.a"));
				if (open == SWT.OK) {
					FLogin f = new FLogin(shell);
					if (f.open(s) == null) {
						s.getClient().stop();
					} else {
						servoManager.getHandler().connected(s.getClient());
					}
				}
			} else if (m.getResult() == Result.ILLEGAL_ARGUMENT) {
				int open = F.mbWaringCancel(shell, Lang.getString("FMain.MessageBoxLoginTitle.text"),
						Lang.getString("FMain.MessageBoxLoginWrong.text"));
				if (open == SWT.OK) {
					FLogin f = new FLogin(shell);
					if (f.open(s) == null) {
						s.getClient().stop();
					} else {
						servoManager.getHandler().connected(s.getClient());
					}
				}
			} else {
				int open = F.mbWaringCancel(shell, Lang.getString("FMain.MessageBoxLoginTitle.text"),
						Lang.getString("FMain.MessageBoxLoginContent.error"));
				if (open == SWT.OK) {
					FLogin f = new FLogin(shell);
					if (f.open(s) == null) {
						s.getClient().stop();
					} else {
						servoManager.getHandler().connected(s.getClient());
					}
				}
			}
			break;
		}
		case LOGOUT:
			break;
		case SELECT_ROLES:
			refreshTree();
			break;
		case READ_DEVICE_STATUS: {
			status();
			Device d = (Device) units.get(m);

			treeZoneView.update(d);
			treeRelayView.update(d);
			treeView.update(d);
			mapView.update(d, getCurrentServo());
			addNotice(d);
			break;
		}
		case WRITE_DEVICE_STATUS: {
			Device d = (Device) units.get(m);
			status();
			treeView.update(d);
			treeZoneView.update(d);
			treeRelayView.update(d);
			mapView.update(d, getCurrentServo());
			addNotice(d);
			break;
		}
		case CREATE_DEVICE: {
			if (m.getResult() == Result.SUCCESS) {
				status();
				// Device d = (Device) m.getUnit();
				Device d = (Device) units.get(m);
				treeView.insert(d);
				treeZoneView.insert(d);
				treeRelayView.insert(d);
			} else {
				F.mbWaring(getShell(), Lang.getString("FMain.MessageBoxCreateDeviceTitle.text"),
						Lang.getString("Message.createDeviceField.text"));
			}
			break;
		}
		case CREATE_ZONE: {
			if (m.getResult() == Result.SUCCESS) {
				status();
				Zone zone = (Zone) units.get(m);
				treeZoneView.insert(zone);
			} else {
				F.mbWaring(getShell(), Lang.getString("FMain.MessageBoxCreateZoneTitle.text"),
						Lang.getString("Message.createZoneField.text"));
			}
			break;
		}
		case MOVE_DEVICE: {
			if (m.getResult() == Result.SUCCESS) {
				// Device d = (Device) m.getUnit();
				Device d = (Device) units.get(m);
				mapView.update(d, getCurrentServo());
			} else {
				F.mbWaring(getShell(), Lang.getString("FMain.MessageBoxCreateDeviceTitle.text"),
						Lang.getString("Message.createDeviceField.text"));
			}
			break;
		}
		case MOVE_ZONE: {
			if (m.getResult() == Result.SUCCESS) {
				// treeZoneView.update((Zone) m.getUnit());
				treeZoneView.update((Device) units.get(m));
			} else {
				F.mbWaring(getShell(), Lang.getString("FMain.MessageBoxMoveZoneTitle.text"),
						Lang.getString("FMain.MessageBoxMoveZoneContent.text"));
			}
			break;
		}
		case UPDATE_DEVICE: {
			if (m.getResult() == Result.SUCCESS) {
				status();
				// Device d = (Device) m.getUnit();
				Device d = (Device) units.get(m);
				treeView.update(d);
				mapView.update(d, getCurrentServo());
				treeRelayView.refresh(servoManager.getAll());
			} else {
				treeZoneView.refresh(servoManager.getAll());
				F.mbWaring(getShell(), Lang.getString("Message.modifyDeviceTitle.text"),
						Lang.getString("Message.modifyDeviceField.text"));
			}
			break;
		}
		case UPDATE_ZONE: {
			if (m.getResult() == Result.SUCCESS) {
				status();
				treeZoneView.refresh(servoManager.getAll());
			} else {
				F.mbWaring(getShell(), Lang.getString("Message.modifyZoneTitle.text"),
						Lang.getString("Message.modifyZoneField.text"));
			}
			break;
		}
		case DELETE_DEVICE: {
			if (m.getResult() == Result.SUCCESS) {
				status();
				// Device d = (Device) m.getUnit();
				Device d = (Device) units.get(m);
				treeZoneView.remove(s, d);
				treeRelayView.remove(s, d);
				treeView.remove(d);
				mapView.deleteDeviceOnMap(getCurrentServo(), d);
			} else {
				F.mbWaring(getShell(), Lang.getString("Message.delDeviceTitle.text"),
						Lang.getString("Message.delDeviceFidld.text"));
			}
			break;
		}
		case DELETE_ZONE: {
			if (m.getResult() == Result.SUCCESS) {
				// treeZoneView.remove(s, (Zone) m.getUnit());
				treeZoneView.remove(s, (Zone) units.get(m));
			} else {
				F.mbWaring(getShell(), Lang.getString("Message.delZoneTile.text"),
						Lang.getString("Message.delZoneMessage.text"));
			}
			break;
		}
		case SET_SERVO_CONFIG: {
			if (m.getResult() == Result.SUCCESS) {
				// 无处理
			} else {
				F.mbWaring(getShell(), Lang.getString("FMian.MessageBoxServoConfigTitle.text"),
						Lang.getString("FMian.MessageBoxServoConfigContent.text"));
			}
		}
		case NOTICE: {
//			MNotice notice = m.getNotice();
//			String deviceId = notice.getDeviceId();
//			String userId = notice.getUserId();
//			User user = s.getUser(userId);
//			Device d = s.getDevice(deviceId);
//			String content = notice.getContent();
//			FTip f = new FTip(user == null ? null : user.getName(), d == null ? null : d.getName(), content);
//			f.open();
			break;
		}

		default:
			break;
		}
	}

	/**
	 * 获取shell
	 * 
	 * @return
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * 获取ServerManager
	 * 
	 * @return
	 */
	public ServoManager getServerManager() {
		return servoManager;
	}

	/**
	 * 获取TreeZoneView
	 * 
	 * @return
	 */
	public TreeZoneView getTreeZoneView() {
		return treeZoneView;
	}

	/**
	 * 获取TreeRelayView
	 * 
	 * @return
	 */
	public TreeRelayView getTreeRelayView() {
		return treeRelayView;
	}

	/**
	 * 刷新树视图
	 */
	public void refreshTree() {
		treeZoneView.refresh(servoManager.getAll());
		treeRelayView.refresh(servoManager.getAll());
	}

	public void moveDeviceOnTree(Device d, Zone newZone) {
		treeZoneView.moveDevice(d, newZone);
	}

	/**
	 * 获取当前操作的伺服器
	 * 
	 * @return
	 */
	public Servo getCurrentServo() {
		CTabItem item = tabFolderTrees.getSelection();
		if (item == tabTreeZones) {
			if (treeZones.getSelectionCount() > 0) {
				TreeItem[] items = treeZones.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Servo)
							return (Servo) o;
						else if (o instanceof Zone)
							return ((Zone) o).getServo();
						else if (o instanceof Device)
							return ((Device) o).getServo();
					}
				}
			}
		}
		if (item == tabTreeRelays) {
			if (treeRelays.getSelectionCount() > 0) {
				TreeItem[] items = treeRelays.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Servo)
							return (Servo) o;
						else if (o instanceof Device)
							return ((Device) o).getServo();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前操作/选择的伺服器
	 * 
	 * @return
	 */
	public Servo getSelectionServo() {
		if (treeZones.isFocusControl()) {
			if (treeZones.getSelectionCount() > 0) {
				TreeItem[] items = treeZones.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Servo)
							return (Servo) o;
					}
				}
			}
		}
		if (treeRelays.isFocusControl()) {
			if (treeRelays.getSelectionCount() > 0) {
				TreeItem[] items = treeRelays.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Servo)
							return (Servo) o;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前操作/选择的区域
	 * 
	 * @return
	 */
	public Zone getSelectionZone() {
		if (treeZones.isFocusControl()) {
			if (treeZones.getSelectionCount() > 0) {
				TreeItem[] items = treeZones.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Zone)
							return ((Zone) o);
						else if (o instanceof Device)
							return ((Device) o).getZone();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前操作/选择的设备
	 * 
	 * @return
	 */
	public Set<Device> getSelectionDevices() {
		Set<Device> devices = new HashSet<Device>();
		if (treeZones.isFocusControl()) {
			if (treeZones.getSelectionCount() > 0) {
				for (TreeItem item : treeZones.getSelection()) {
					Object o = item.getData();
					if (o != null) {
						if (o instanceof Device)
							devices.add((Device) o);
					}
				}
			}
		}
		if (treeRelays.isFocusControl()) {
			if (treeRelays.getSelectionCount() > 0) {
				for (TreeItem item : treeRelays.getSelection()) {
					Object o = item.getData();
					if (o != null) {
						if (o instanceof Device)
							devices.add((Device) o);
					}
				}
			}
		}
		if (treeDeviceDetails.isFocusControl()) {
			if (treeDeviceDetails.getSelectionCount() > 0) {
				for (TreeItem item : treeDeviceDetails.getSelection()) {
					Object o = item.getData();
					if (o != null) {
						if (o instanceof Device)
							devices.add((Device) o);
					}
				}
			}
		}

		return devices;
	}

	/**
	 * 
	 * 获取当前操作/选择设备
	 * 
	 * 
	 * 
	 * @return
	 */
	public Device getSelectionDevice() {
		if (treeZones.isFocusControl()) {
			if (treeZones.getSelectionCount() > 0) {
				TreeItem[] items = treeZones.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Device)
							return (Device) o;
					}
				}
			}
		}
		if (treeRelays.isFocusControl()) {
			if (treeRelays.getSelectionCount() > 0) {
				TreeItem[] items = treeRelays.getSelection();
				if (items != null && items.length > 0) {
					Object o = items[0].getData();
					if (o != null) {
						if (o instanceof Device)
							return (Device) o;
					}
				}
			}
		}
		if (treeDeviceDetails.isFocusControl()) {
			if (treeDeviceDetails.getSelectionCount() > 0) {
				for (TreeItem item : treeDeviceDetails.getSelection()) {
					Object o = item.getData();
					if (o != null) {
						if (o instanceof Device)
							return (Device) o;
					}
				}
			}
		}

		return null;
	}

	/**
	 * 关闭主窗口
	 */
	@Override
	public void close() {
		for (Servo servo : servoManager.getAll()) {
			CtrClient ctrClient = servo.getClient();
			if (ctrClient != null) {
				ctrClient.stop();
				ctrClient.close();
			}
		}
	}

	/**
	 * 菜单栏选择设备厂商
	 */
	private void selectVendor() {
		boolean isAllCheck = true;
		int selectCount = 0;
		String select = null;
		for (MenuItem item_1 : menuDeviceVendor.getItems()) {
			if (item_1.getData() != null) {
				if (!item_1.getSelection()) {
					isAllCheck = false;
				} else {
					selectCount++;
					if (item_1.getData() instanceof Vendor) {
						Vendor v = (Vendor) item_1.getData();
						select = Lang.getString("Vendor." + v.name());
					}
				}
			}
		}

		if (isAllCheck) {
			toolSearchDeviceVendor
					.setText(Lang.getString("FMain.tltmNewItem.text_5") + "(" + Lang.getString("FMain.All") + ")");
			toolSearchDeviceVendor.setToolTipText(Lang.getString("FMain.All"));
			menuVendorAll.setSelection(true);
		} else {
			if (selectCount > 1) {
				toolSearchDeviceVendor.setText(Lang.getString("FMain.tltmNewItem.text_5") + "(" + selectCount + ")");
				toolSearchDeviceVendor.setToolTipText(Lang.getString("FMain.Multi"));
			} else if (selectCount == 1) {
				toolSearchDeviceVendor.setText(Lang.getString("FMain.tltmNewItem.text_5") + "(" + selectCount + ")");
				toolSearchDeviceVendor.setToolTipText(select);
			} else {
				toolSearchDeviceVendor
						.setText(Lang.getString("FMain.tltmNewItem.text_5") + "(" + Lang.getString("FMain.None") + ")");
				toolSearchDeviceVendor.setToolTipText(Lang.getString("FMain.None"));
			}
			menuVendorAll.setSelection(false);
		}
		filterDevice();
	}

	/**
	 * 菜单栏选择设备类型
	 */
	private void selectKind() {
		boolean isAllCheck = true;
		int selectCount = 0;
		String select = null;
		for (MenuItem item_1 : menuDeviceType.getItems()) {
			if (item_1.getData() != null) {
				if (!item_1.getSelection()) {
					isAllCheck = false;
				} else {
					selectCount++;
					if (item_1.getData() instanceof Kind) {
						Kind k = (Kind) item_1.getData();
						select = Lang.getString("Kind." + k.name());
					}
				}
			}
		}

		if (isAllCheck) {
			toolSearchDeviceType
					.setText(Lang.getString("FMain.tltmNewItem.text_6") + "(" + Lang.getString("FMain.All") + ")");
			toolSearchDeviceType.setToolTipText(Lang.getString("FMain.All"));
			menuTypeAll.setSelection(true);
		} else {
			if (selectCount > 1) {
				toolSearchDeviceType.setText(Lang.getString("FMain.tltmNewItem.text_6") + "(" + selectCount + ")");
				toolSearchDeviceType.setToolTipText(Lang.getString("FMain.Multi"));
			} else if (selectCount == 1) {
				toolSearchDeviceType.setText(Lang.getString("FMain.tltmNewItem.text_6") + "(" + selectCount + ")");
				toolSearchDeviceType.setToolTipText(select);
			} else {
				toolSearchDeviceType
						.setText(Lang.getString("FMain.tltmNewItem.text_6") + "(" + Lang.getString("FMain.None") + ")");
				toolSearchDeviceType.setToolTipText(Lang.getString("FMain.None"));
			}
			menuTypeAll.setSelection(false);
		}

		filterDevice();
	}

	/**
	 * 菜单栏选择设备连接方式
	 */
	private void selectLink() {
		boolean isAllCheck = true;
		int selectCount = 0;
		String select = null;
		for (MenuItem item_1 : menuDeviceLink.getItems()) {
			if (item_1.getData() != null) {
				if (!item_1.getSelection()) {
					isAllCheck = false;
				} else {
					selectCount++;
					if (item_1.getData() instanceof Link) {
						Link l = (Link) item_1.getData();
						select = Lang.getString("Link." + l.name());
					}
				}
			}
		}

		if (isAllCheck) {
			toolSearchDeviceLink.setToolTipText(Lang.getString("FMain.All"));
			toolSearchDeviceLink
					.setText(Lang.getString("FMain.tltmNewItem.text_7") + "(" + Lang.getString("FMain.All") + ")");
			menuLinkAll.setSelection(true);
		} else {
			if (selectCount > 1) {
				toolSearchDeviceLink.setToolTipText(Lang.getString("FMain.Multi"));
				toolSearchDeviceLink.setText(Lang.getString("FMain.tltmNewItem.text_7") + "(" + selectCount + ")");
			} else if (selectCount == 1) {
				toolSearchDeviceLink.setToolTipText(select);
				toolSearchDeviceLink.setText(Lang.getString("FMain.tltmNewItem.text_7") + "(" + selectCount + ")");
			} else {
				toolSearchDeviceLink.setToolTipText(Lang.getString("FMain.None"));
				toolSearchDeviceLink
						.setText(Lang.getString("FMain.tltmNewItem.text_7") + "(" + Lang.getString("FMain.None") + ")");
			}
			menuLinkAll.setSelection(false);
		}

		filterDevice();
	}

	/**
	 * 筛选设备并在显示
	 */
	private void filterDevice() {
		Set<Device> filterDevices = new DeviceFiliter(menuDeviceVendor, menuDeviceType, menuDeviceLink,
				getCurrentServo()).getFilterDevices();

		treeView.search(filterDevices);
		mapView.search(filterDevices, getSelectionServo());

	}

	/**
	 * menuBarTools点击事件
	 */
	private SelectionAdapter menuBarToolsSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			tools.setVisible(menuBarTools.getSelection());
		}

	};

	/**
	 * 状态树形视图双击打开设备状态窗口
	 */
	private Listener setDeviceStatusTreeListDoubleClick = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			Servo currentServo = getCurrentServo();
			if (currentServo == null) {
				F.mbInformation(getShell(), Lang.getString("FMain.setDeviceStatus.text"),
						Lang.getString("AGetStatus.TipNotConnectServo.text"));
				return;
			}

			User user = currentServo.getUser();
			if (user == null) {
				F.mbInformation(getShell(), Lang.getString("FMain.setDeviceStatus.text"),
						Lang.getString("AGetStatus.TipNotConnectServo.text"));
				return;
			}

			if (!user.allow(ActionCase.WRITE_DEVICE_STATUS.getNumber())) {
				F.mbInformation(getShell(), Lang.getString("FMain.setDeviceStatus.text"),
						Lang.getString("FMain.userNotHaveSetDeviceStatusCommand.text"));
				return;
			}

			TreeItem[] selection = treeDeviceDetails.getSelection();
			if (selection != null && selection.length == 1) {
				Object data = selection[0].getData();
				if (data != null && data instanceof Device) {
					Device d = (Device) data;
					FDeviceStatus f = new FDeviceStatus(shell);
					getServerManager().add(f, getCurrentServo());
					f.open(d, getCurrentServo());
					getServerManager().off(f);
				}
			}
		}

	};

	/**
	 * 消息列表视图双击打开设置设备状态窗口notice
	 */
	private Listener setDeviceStatusTableNoticeDoubleClick = new Listener() {

		@Override
		public void handleEvent(Event arg0) {
			int selectionIndex = tableNotice.getSelectionIndex();
			if (selectionIndex != -1/*-1表格未选中*/) {
				TableItem item = tableNotice.getItem(selectionIndex);
				Object data = item.getData();
				if (data != null) {
					if (data instanceof Device) {
						Device d = (Device) data;
						if (d != null) {
							FDeviceStatus f = new FDeviceStatus(shell);
							servoManager.add(f, getCurrentServo());
							f.open(d, getCurrentServo());
							servoManager.off(f);
						}
					} else {
						// 表格里的数据不是设备而是其他的未知
					}
				} else {
					// tableItem 为空
				}
			} else {
				// 表格没有选择项
			}
		}
	};

	/**
	 * 消息列表视图双击打开设置设备状态窗口message
	 */
	private Listener setDeviceStatusTableMessageDoubleClick = new Listener() {

		@Override
		public void handleEvent(Event arg0) {
			Servo currentServo = getCurrentServo();
			if (currentServo != null) {
				User user = currentServo.getUser();
				if (user != null) {
					if (user.allow(ActionCase.WRITE_DEVICE_STATUS.getNumber())) {
						int selectionIndex = tableMessage.getSelectionIndex();
						if (selectionIndex != -1/*-1表格未选中*/) {
							TableItem item = tableMessage.getItem(selectionIndex);
							Object data = item.getData();
							if (data != null) {
								if (data instanceof Device) {
									Device d = (Device) data;

									FDeviceStatus f = new FDeviceStatus(shell);
									getServerManager().add(f, getCurrentServo());
									f.open(d, getCurrentServo());
									getServerManager().off(f);
								} else {
									// 不能强转成Device
								}
							} else {
								// 表格data为空
							}
						} else {
							// 表格没有选中项
						}
					} else {
						// 没有权限打开
					}
				} else {
					// 用户为空
				}
			} else {
				// 伺服器为空
			}
		}
	};

	/**
	 * menuBarStatus SelectionAdapter
	 */
	private SelectionAdapter menuBarStatusSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			status.setVisible(menuBarStatus.getSelection());
		}
	};

	/**
	 * menuViewReset SelectionAdapter
	 */
	private SelectionAdapter menuViewResetSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			resetViews();
		}
	};

	/**
	 * menuViewFullscreen SelectionAdapter
	 */
	private SelectionAdapter menuViewFullscreenSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent event) {
			if (menuViewFullscreen.getSelection()) {
				shell.setFullScreen(true);
				shell.setMaximized(true);
			} else {
				shell.setFullScreen(false);
			}
		}
	};

	/**
	 * menuPersonals MenuAdapter
	 */
	private MenuAdapter menuPersonalsMenuAdapter = new MenuAdapter() {
		@Override
		public void menuShown(MenuEvent arg0) {
			Menu menu = (Menu) arg0.widget;
			MenuItem[] items = menu.getItems();
			if (getCurrentServo() == null) {
				for (MenuItem menuItem : items) {
					menuItem.setEnabled(false);
				}
			} else {
				CtrClient client = getCurrentServo().getClient();
				if (client != null && client.isConnected() && client.isRunning()) {
					for (MenuItem menuItem : items) {
						menuItem.setEnabled(true);
					}
				}
			}
		}
	};

	/**
	 * toolMapNormal SelectionAdapter
	 */
	private SelectionAdapter toolMapNormalSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
//			Display.getDefault().asyncExec(new Runnable() {
//				@Override
//				public void run() {
//					mapView.select(getCurrentServo());
//				}
//			});
			test.Test test = new test.Test(getSelectionServo(), FMain.this);
			test.run();

		}
	};

	/**
	 * toolMapPan SelectionAdapter
	 */
	private SelectionAdapter toolMapPanSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					mapView.pan();
				}
			});

		}
	};

	/**
	 * toolMapOut SelectionAdapter
	 */
	private SelectionAdapter toolMapOutSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					mapView.zoomOut();
				}
			});
		}

	};

	/**
	 * toolMapRotate SelectionAdapter
	 */
	private SelectionAdapter toolMapRotateSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					mapView.rotate();
				}
			});
		}

	};

	/**
	 * toolMapReset SelectionAdapter
	 */
	private SelectionAdapter toolMapResetSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					mapView.reset(getSelectionServo());
				}
			});
		}

	};

	/**
	 * toolMapLayers Listener
	 */
	private Listener toolMapLayersListener = new Listener() {

		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.ARROW) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						mapView.layers(toolMapLayers);
					}
				});

			}

		}
	};

	/**
	 * treeZones Listener
	 */
	private Listener treeZonesListener = new Listener() {

		@Override
		public void handleEvent(Event e) {
			TreeItem item = (TreeItem) e.item;
			Object o = item.getData();
			if (o instanceof Servo) {
				// tableView.refresh((Servo) o);
				treeView.refresh((Servo) o);
			}

			if (o instanceof Zone) {
				// tableView.refresh((Zone) o);
				treeView.refresh((Zone) o);
			}
			if (o instanceof Device)
				mapView.setDisplayByDevice((Device) o);
			status();
		}
	};

	/**
	 * treeZones Listener DoubleClick
	 */
	private Listener treeZonesDoubleClickListener = new Listener() {

		@Override
		public void handleEvent(Event arg0) {
			Servo servo = getSelectionServo();
			Device device = getSelectionDevice();
			Zone zone = getSelectionZone();

			// 判断选择的伺服器
			if (servo != null) {
				// 打开伺服器属性窗口
				FServoDetails f = new FServoDetails(shell, servo);
				f.open();
			}
			// 判断删除设备
			else if (device != null) {
				// 打开设备属性窗口
				FDeviceDetails f = new FDeviceDetails(shell);
				f.open(device, null);
			}
			// 判断选择的区域
			else if (zone != null) {
				FZoneDetails f = new FZoneDetails(shell, zone);
				f.open();
			}

		}
	};

	/**
	 * Zone View Right-Click MenuAdapter
	 */
	private MenuAdapter zoneViewMenuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu menu = (Menu) arg0.widget;
			MenuItem[] items = menu.getItems();
			for (MenuItem item : items) {
				Object data = item.getData("tag");
				if (data != null) {
					Servo currentServo = getCurrentServo();
					if (currentServo != null) {
						User user = currentServo.getUser();
						if (user != null) {
							switch ((String) data) {
							case "delete":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时显示
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.DELETE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.DELETE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								}
								break;
							case "property":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.UPDATE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.UPDATE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else {
								}
								break;
							default:
								break;
							}
						}
					}
				} else {
					// 没有标志位的菜单
				}

			}

			// 控制伺服器菜单断开连接
			Servo currentServo = getCurrentServo();
			if (currentServo != null) {
				CtrClient client = currentServo.getClient();

				if (client == null) {
					menuTreeZonesServoConnect.setEnabled(true);
					menuTreeZonesServoDisconnect.setEnabled(false);
				} else {
					if (client.isConnected() && client.isRunning()) {
						menuTreeZonesServoConnect.setEnabled(false);
						menuTreeZonesServoDisconnect.setEnabled(true);
					} else {
						menuTreeZonesServoConnect.setEnabled(true);
						menuTreeZonesServoDisconnect.setEnabled(false);
					}
				}
			} else {
				menuTreeZonesServoConnect.setEnabled(false);
				menuTreeZonesServoDisconnect.setEnabled(false);
			}

			MenuCommandUtil m = new MenuCommandUtil(FMain.this);
			m.LoadMenuForCommand(items);
		}

	};

	/**
	 * ZoneView CreateMenu Right-Click MenuAdapter
	 */
	private MenuAdapter zoneViewCreateMenuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu menu = (Menu) arg0.widget;
			MenuItem[] items = menu.getItems();
			MenuCommandUtil m = new MenuCommandUtil(FMain.this);
			m.LoadMenuForCommand(items);
		}

	};

	/**
	 * treeRelays Selection Listener
	 */
	private Listener treeRelaysListener = new Listener() {

		@Override
		public void handleEvent(Event arg0) {
			TreeItem item = (TreeItem) arg0.item;
			Object o = item.getData();
			if (o instanceof Servo) {
				treeView.refresh((Servo) o);
			}
			if (o instanceof Device) {
				mapView.setDisplayByDevice((Device) o);
				treeView.refresh((Device) o);
			}
			status();
		}
	};

	/**
	 * tableList SelectionAdapter
	 */
	private SelectionAdapter tableListSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			status();
		}

	};

	/**
	 * toolMapAddDevice SelectionAdapter
	 */
	private SelectionAdapter toolMapAddDeviceSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FAddDeviceOnMap f = new FAddDeviceOnMap(shell);
			Set<Device> exitsDevices = mapView.getExitsDevices();
			f.open(getCurrentServo(), exitsDevices, mapPane, mapView);
		}

	};

	/**
	 * menutreeDeviceDetails MenuAdapter
	 */
	private MenuAdapter menutreeDeviceDetailsMenuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu m = (Menu) arg0.widget;
			MenuItem[] items = m.getItems();
			for (MenuItem item : items) {
				Object data = item.getData("tag");
				if (data != null) {
					Servo currentServo = getCurrentServo();
					if (currentServo != null) {
						User user = currentServo.getUser();
						if (user != null) {
							switch ((String) data) {
							case "property":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.UPDATE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.UPDATE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else {
								}
								break;
							default:
								break;
							}
						}
					}
				} else {
					// 没有标志位的菜单
				}

			}

			MenuCommandUtil m1 = new MenuCommandUtil(FMain.this);
			m1.LoadMenuForCommand(items);
		}

	};

	/**
	 * clearItem SelectionAdapter
	 */
	private SelectionAdapter clearItemSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (tableMessage.isFocusControl())
				tableMessage.removeAll();
			if (tableNotice.isFocusControl())
				tableNotice.removeAll();
		}

	};

	/**
	 * clearMenu MenuAdapter
	 */
	private MenuAdapter clearMenuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu m = (Menu) arg0.widget;
			MenuItem[] items = m.getItems();

			MenuCommandUtil mcu = new MenuCommandUtil(FMain.this);
			mcu.LoadMenuForCommand(items);

		}

	};

	/**
	 * shell ShellAdapter
	 */
	private ShellAdapter shellAdapter = new ShellAdapter() {
		@Override
		public void shellClosed(ShellEvent e) {
			// 关闭
			try {
				servoManager.save();
				servoManager.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * menuServos MenuAdapter
	 */
	private MenuAdapter menuServosMenuAdapter = new MenuAdapter() {
		@Override
		public void menuShown(MenuEvent arg0) {
			// 获取到当前Menu下的所有子菜单
			Menu currentMenu = (Menu) arg0.widget;
			MenuItem[] items = currentMenu.getItems();
			for (MenuItem item : items) {
				Object data = item.getData("tag");
				if (data != null) {
					Servo currentServo = getCurrentServo();
					if (currentServo != null) {
						User user = currentServo.getUser();
						if (user != null) {
							switch ((String) data) {
							case "delete":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时显示
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.DELETE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.DELETE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								}
								break;
							case "property":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.UPDATE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.UPDATE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else {
								}
								break;
							default:
							}
						}
					}
				} else {
				}

			}

			Servo currentServo = getCurrentServo();
			if (currentServo != null) {
				CtrClient client = currentServo.getClient();
				if (client == null) {
					menuServoConnect.setEnabled(true);
					menuServoDisconnect.setEnabled(false);
					return;
				} else {
					if (client.isConnected() && client.isRunning()) {
						menuServoConnect.setEnabled(false);
						menuServoDisconnect.setEnabled(true);
					} else {
						menuServoConnect.setEnabled(true);
						menuServoDisconnect.setEnabled(false);
					}
				}

			} else {
				menuServoConnect.setEnabled(false);
				menuServoDisconnect.setEnabled(false);
			}
			MenuCommandUtil m = new MenuCommandUtil(FMain.this);
			m.LoadMenuForCommand(items);
		}
	};

	/**
	 * menuCreates MenuAdapter
	 */
	private MenuAdapter menuCreatesMenuAdapter = new MenuAdapter() {
		@Override
		public void menuShown(MenuEvent arg0) {
			Menu currentMenu = (Menu) arg0.widget;
			MenuItem[] items = currentMenu.getItems();
			MenuCommandUtil m = new MenuCommandUtil(FMain.this);
			m.LoadMenuForCommand(items);
		}

	};

	/**
	 * menuDevices MenuAdapter
	 */
	private MenuAdapter menuDevicesMenuAdapter = new MenuAdapter() {
		@Override
		public void menuShown(MenuEvent arg0) {
			Menu currentMenu = (Menu) arg0.widget;
			MenuItem[] items = currentMenu.getItems();
			for (MenuItem item : items) {
				Object data = item.getData("tag");
				if (data != null) {
					Servo currentServo = getCurrentServo();
					if (currentServo != null) {
						User user = currentServo.getUser();
						if (user != null) {
							switch ((String) data) {
							case "property":
								if (getSelectionServo() != null) {
									// 1.当树上选中的是伺服器时
									item.setEnabled(true);
								} else if (getSelectionDevice() != null) {
									// 2.当树上选中的是设备时判断显示
									if (user.allow(ActionCase.UPDATE_DEVICE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else if (getSelectionZone() != null) {
									// 2.当树上选中的是区域时判断显示
									if (user.allow(ActionCase.UPDATE_ZONE.getNumber())) {
										item.setEnabled(true);
									} else {
										item.setEnabled(false);
									}
								} else {
								}
								break;
							default:
								break;
							}

						} else {
						}
					} else {
					}

				} else {
				}
			}
			MenuCommandUtil m = new MenuCommandUtil(FMain.this);
			m.LoadMenuForCommand(items);
		}
	};

	/**
	 * menuVendorAll SelectionAdapter
	 */
	private SelectionAdapter menuVendorAllSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (menuVendorAll.getSelection()) {
				MenuItem[] items = menuDeviceVendor.getItems();
				for (MenuItem item : items) {
					item.setSelection(true);
				}
			}
		}
	};

	/**
	 * toolSearchDeviceVendor SelectionAdapter
	 */
	private Listener toolSearchDeviceVendorListener = new Listener() {

		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.ARROW) {
				Rectangle rect = toolSearchDeviceVendor.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = toolSearch.toDisplay(pt);
				menuDeviceVendor.setLocation(pt.x, pt.y);
				menuDeviceVendor.setVisible(true);
			}

			if (event.detail == SWT.NONE) {
				Set<Vendor> vendors = new HashSet<>();
				MenuItem[] items = menuDeviceVendor.getItems();
				if (items.length > 0) {
					for (MenuItem item : items) {
						if (item.getSelection()) {
							Object data = item.getData();
							if (data != null && data instanceof Vendor) {
								Vendor v = (Vendor) data;
								vendors.add(v);
							}
						}
					}
				}

				FSelect f = new FSelect(shell, "Vendor");
				f.setSelectedVendors(vendors);
				f.open();
				if (f.ensure) {
					Set<Vendor> selectVendor = f.getSelectVendor();
					for (MenuItem item : items) {
						Object data = item.getData();
						if (data != null && data instanceof Vendor) {
							Vendor vendor1 = (Vendor) data;
							if (selectVendor.contains(vendor1)) {
								item.setSelection(true);
							} else {
								item.setSelection(false);
							}
						} else {
						}
					}
				}

				selectVendor();

			}

		}

	};

	/**
	 * menuTypeAll SelectionAdapter
	 */
	private SelectionAdapter menuTypeAllSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (menuTypeAll.getSelection()) {
				MenuItem[] items = menuDeviceType.getItems();
				for (MenuItem item : items) {
					item.setSelection(true);
				}
			}
		}
	};

	/**
	 * toolSearchDeviceType Listener
	 */
	private Listener toolSearchDeviceTypeListener = new Listener() {

		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.ARROW) {
				Rectangle rect = toolSearchDeviceType.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = toolSearch.toDisplay(pt);
				menuDeviceType.setLocation(pt.x, pt.y);
				menuDeviceType.setVisible(true);
			}

			if (event.detail == SWT.NONE) {
				Set<Kind> kinds = new HashSet<>();
				MenuItem[] items = menuDeviceType.getItems();
				if (items.length > 0) {
					for (MenuItem item : items) {
						if (item.getSelection()) {
							Object data = item.getData();
							if (data != null && data instanceof Kind) {
								Kind k = (Kind) data;
								kinds.add(k);
							}
						}
					}
				}

				FSelect f = new FSelect(shell, "Kind");
				f.setSelectedKinds(kinds);
				f.open();
				if (f.ensure) {
					Set<Kind> selectKind = f.getSelectKind();
					for (MenuItem item : items) {
						Object data = item.getData();
						if (data != null && data instanceof Kind) {
							Kind kind1 = (Kind) data;
							if (selectKind.contains(kind1)) {
								item.setSelection(true);
							} else {
								item.setSelection(false);
							}
						} else {
						}
					}
				}

				selectKind();

			}
		}
	};

	/**
	 * menuLinkAll SelectionAdapter
	 */
	private SelectionAdapter menuLinkAllSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (menuTypeAll.getSelection()) {
				MenuItem[] items = menuDeviceLink.getItems();
				for (MenuItem item : items) {
					item.setSelection(true);
				}
			}
		}
	};

	/**
	 * toolSearchDeviceLink Listener
	 */
	private Listener toolSearchDeviceLinkListener = new Listener() {

		@Override
		public void handleEvent(Event event) {
			if (event.detail == SWT.ARROW) {
				Rectangle rect = toolSearchDeviceLink.getBounds();
				Point pt = new Point(rect.x, rect.y + rect.height);
				pt = toolSearch.toDisplay(pt);
				menuDeviceLink.setLocation(pt.x, pt.y);
				menuDeviceLink.setVisible(true);
			}

			if (event.detail == SWT.NONE) {
				Set<Link> links = new HashSet<>();
				MenuItem[] items = menuDeviceLink.getItems();
				if (items.length > 0) {
					for (MenuItem item : items) {
						if (item.getSelection()) {
							Object data = item.getData();
							if (data != null && data instanceof Link) {
								Link link = (Link) data;
								links.add(link);
							}
						}
					}
				}

				FSelect f = new FSelect(shell, "Link");
				f.setSelectedLinks(links);
				f.open();
				if (f.ensure) {
					Set<Link> selectLink = f.getSelectLink();
					for (MenuItem item : items) {
						Object data = item.getData();
						if (data != null && data instanceof Link) {
							Link link1 = (Link) data;
							if (selectLink.contains(link1)) {
								item.setSelection(true);
							} else {
								item.setSelection(false);
							}
						} else {
							// 不能强转为Vendor
						}
					}
				}

				selectLink();
			}
		}
	};

	/**
	 * itemsVendor SelectionAdapter
	 */
	private SelectionAdapter itemsVendorSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			selectVendor();
		}
	};

	/**
	 * itemsType SelectionAdapter
	 */
	private SelectionAdapter itemsTypeSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			selectKind();
		}
	};

	/**
	 * itemsLink SelectionAdapter
	 */
	private SelectionAdapter itemsLinkSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			selectLink();
		}
	};

	/**
	 * TextSearch ModifyListener
	 */
	private ModifyListener textSearchModifyListener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent arg0) {
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					String text2 = text.getText();
					SearchDevice s = new SearchDevice(getCurrentServo());
					Set<Device> search = s.search(text2);
					treeView.search(search);
				}
			});

		}
	};

	private SelectionAdapter expandAllSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			treeView.expandAll();
		}

	};

	private SelectionAdapter selectAllSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			treeView.selectAll();
		}

	};

	private SelectionAdapter packUpAllSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			treeView.packUpAll();
		}

	};

	/**
	 * 报表
	 */
	private void loadReportMenu() {
		String path = Tool.getWorkPath();
		File folder = new File(path + File.separatorChar + "report");

		if (folder.exists()) {
			File[] files = folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isFile()) {
						return f.getName().endsWith(".html");
					}
					return false;
				}
			});

			for (final File file : files) {
				final String title = getTitle(file);
				menuReports.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						//
						MenuItem menuReportA = new MenuItem(menuReports, SWT.NONE);
						menuReportA.addSelectionListener(new SelectionAdapter() {
							/**
							 * @param arg0
							 */
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								if (getSelectionServo() == null) {
									F.mbInformation(getShell(), Lang.getString("FMain.report"),
											Lang.getString("FMain.report.tip"));
									return;
								}

								FReport f = new FReport(shell);
								servoManager.add(f, getSelectionServo());
								f.open(FMain.this.getCurrentServo(), String.format("/report/%s", file.getName()));
								servoManager.off(f);

							}
						});
						menuReportA.setText(title);

					}
				});
			}
		}

	}

	private String getTitle(File file) {
		// 从HTML文件中读取<title>标签中的内容
		try (Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8")) {
			int value = 0;
			char[] chars = new char[5];
			StringBuilder sb = new StringBuilder();
			w: while ((value = reader.read()) >= 0) {
				if (value == '<') {
					// title
					for (int index = 0; (value = reader.read()) >= 0 && index < chars.length; index++) {
						if (value == '>')
							continue w;
						chars[index] = (char) value;
					}
					if ("title".equalsIgnoreCase(new String(chars))) {
						sb.setLength(0);
						while ((value = reader.read()) >= 0) {
							if (value == '<')
								break;
							sb.append((char) value);
						}
						return sb.toString();
					}
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public TreeView getTreeView() {
		return treeView;
	}

}
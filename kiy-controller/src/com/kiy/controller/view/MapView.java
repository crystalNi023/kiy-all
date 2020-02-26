/**
 * 2017年5月3日
 */
package com.kiy.controller.view;

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolItem;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultDerivedCRS;
import org.geotools.referencing.operation.transform.ProjectiveTransform;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.styling.TextSymbolizer;
import org.geotools.swt.SwtMapFrame;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.event.MapMouseEvent;
import org.geotools.swt.event.MapMouseListener;
import org.geotools.swt.event.MapPaneEvent;
import org.geotools.swt.event.MapPaneListener;
import org.geotools.swt.tool.PanTool;
import org.geotools.swt.tool.ZoomInTool;
import org.geotools.swt.tool.ZoomOutTool;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.identity.FeatureId;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.style.ExternalGraphic;

import com.kiy.client.CtrClient;
import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.controller.ACheckDevicePropertyForMap;
import com.kiy.controller.ADeleteDeviceForMap;
import com.kiy.controller.ADeleteDeviceOnMapForMap;
import com.kiy.controller.AGetDeviceStatusForMap;
import com.kiy.controller.AHistoryDeviceStatusForMap;
import com.kiy.controller.AMainTainRecordsForMap;
import com.kiy.controller.AMoveDeviceForMap;
import com.kiy.controller.ASetDeviceStatusForMap;
import com.kiy.controller.AUpdateDeviceForMap;
import com.kiy.controller.F;
import com.kiy.controller.FMain;
import com.kiy.controller.MenuCommandUtil;
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.MovePosition;
import com.kiy.protocol.Messages.Message.ActionCase;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class MapView {

	private SwtMapPane pane;
	private MapContent content;
	private GTRenderer renderer;
	private List<Layer> layers;
	private Menu menus;
	private Servo servo;
	private Set<Device> exitsDevices;
	private CoordinateReferenceSystem baseCRS; // 基本坐标系，用于将地图重置
	private SimpleFeatureType TYPE;
	private Coordinate coordinate;
	private StyleFactory styleFactory;
	private FeatureTypeStyle fts;
	private Style style;

	private MenuItem mapGetStatus;// 获取状态
	private MenuItem mapSetStatus;// 设置状态
	private MenuItem mapMainTain;// 维护记录
	private MenuItem mapHistory;// 历史状态
	private MenuItem mapUpdateDevice;// 设置
	private MenuItem mapProperty;// 属性
	private MenuItem mapDeleteDeviceOnMap;// 移除设备
	private MenuItem mapDeleteDevice;// 删除设备
	private MenuItem mapMoveDevice;// 移动设备

	List<SimpleFeature> list = new ArrayList<>();
	public final static String DEVICE_LAYER = "设备分布";
	public static final double DEFAULT_DISTANCE_FRACTION = 0.02d;
	public static DirectPosition2D mapPosition; // 点击后设置设备的坐标

	private Layer deviceLayer;

	public void setServo(Servo s) {
		servo = s;
	}

	public DirectPosition2D getMapPosition() {
		return mapPosition;
	}

	public Set<Device> getExitsDevices() {
		return exitsDevices;
	}

	private FMain main;

	public MapView(SwtMapPane p, FMain m) {
		pane = p;
		main = m;

		styleFactory = CommonFactoryFinder.getStyleFactory();
		fts = styleFactory.createFeatureTypeStyle();
		style = styleFactory.createStyle();
		exitsDevices = new HashSet<>();

		/**
		 * 拖放接收 // TODO Auto-generated method stub
		 */
		DropTarget target = new DropTarget(pane, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT);
		// 设置目标对象可传输的数据类型
		target.setTransfer(new Transfer[] { TextTransfer.getInstance() });

		target.addDropListener(new DropTargetListener() {
			@Override
			public void dropAccept(DropTargetEvent arg0) {
			}

			@Override
			public void drop(DropTargetEvent arg0) {
				String deviceId = (String) arg0.data;

				if (coordinate == null) {
					return;
				}

				addDevice(deviceId, coordinate, servo, true);
				coordinate = null;
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SELECT;
			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}

			@Override
			public void dragLeave(DropTargetEvent arg0) {
			}

			@Override
			public void dragEnter(DropTargetEvent arg0) {
				if (arg0.detail == DND.DROP_DEFAULT)
					arg0.detail = DND.DROP_COPY;
			}
		});

		createMapContent();
	}

	/**
	 * createMapContent
	 */
	private void createMapContent() {
		try {
			TYPE = DataUtilities.createType("device", "location:Point:srid=4326,id:String,type:int");
		} catch (SchemaException e1) {
			e1.printStackTrace();
		}

		renderer = new StreamingRenderer();
		layers = new ArrayList<Layer>();
		content = new MapContent();
		pane.setMapContent(content);
		pane.setRenderer(renderer);

		/**
		 * 初始化地图右键菜单
		 */
		Menu menuMap = new Menu(pane.getShell(), SWT.POP_UP);
		mapGetStatus = new MenuItem(menuMap, SWT.PUSH);
		mapSetStatus = new MenuItem(menuMap, SWT.PUSH);
		new MenuItem(menuMap, SWT.SEPARATOR);
		mapMainTain = new MenuItem(menuMap, SWT.PUSH);
		mapHistory = new MenuItem(menuMap, SWT.PUSH);
		new MenuItem(menuMap, SWT.SEPARATOR);
		mapUpdateDevice = new MenuItem(menuMap, SWT.PUSH);
		mapProperty = new MenuItem(menuMap, SWT.PUSH);
		new MenuItem(menuMap, SWT.SEPARATOR);
		mapDeleteDeviceOnMap = new MenuItem(menuMap, SWT.PUSH);
		mapDeleteDevice = new MenuItem(menuMap, SWT.PUSH);
		mapMoveDevice = new MenuItem(menuMap, SWT.PUSH);

		mapGetStatus.setText(Lang.getString("FMain.mntmNewItem_8.text"));
		mapSetStatus.setText(Lang.getString("FMain.menuStatus.text"));
		mapMainTain.setText(Lang.getString("FMain.menuMainTainRecords.text"));
		mapHistory.setText(Lang.getString("FMain.menuHistoryStatus.text"));
		mapUpdateDevice.setText(Lang.getString("FMain.menuServoProperty.text"));
		mapProperty.setText(Lang.getString("FMain.menuServoModify.text"));
		mapDeleteDeviceOnMap.setText(Lang.getString("MapView.removeDevice"));
		mapDeleteDevice.setText(Lang.getString("MapView.deleteDevice"));
		mapMoveDevice.setText(Lang.getString("MapView.moveDevice"));

		pane.setMenu(menuMap);

		
		menuMap.addMenuListener(menuAdapter);
		/**
		 * 添加右键菜单点击功能
		 */
		mapSetStatus.addSelectionListener(new ASetDeviceStatusForMap(main, this));
		mapGetStatus.addSelectionListener(new AGetDeviceStatusForMap(main, this));
		mapMainTain.addSelectionListener(new AMainTainRecordsForMap(main, this));
		mapHistory.addSelectionListener(new AHistoryDeviceStatusForMap(main, this));
		mapUpdateDevice.addSelectionListener(new AUpdateDeviceForMap(main, this));
		mapProperty.addSelectionListener(new ACheckDevicePropertyForMap(main, this));
		mapDeleteDeviceOnMap.addSelectionListener(new ADeleteDeviceOnMapForMap(main, this));
		mapDeleteDevice.addSelectionListener(new ADeleteDeviceForMap(main, this));
		mapMoveDevice.addSelectionListener(new AMoveDeviceForMap(main, this));

		/**
		 * 添加右键菜单权限
		 */
		mapSetStatus.setData(new ActionCase[] { ActionCase.WRITE_DEVICE_STATUS });
		mapGetStatus.setData(new ActionCase[] { ActionCase.READ_DEVICE_STATUS});
		mapMainTain.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_MAINTAIN});
		mapHistory.setData(new ActionCase[] { ActionCase.QUERY_DEVICE_STATUS});
		mapUpdateDevice.setData(new ActionCase[] { ActionCase.UPDATE_DEVICE});
		mapDeleteDevice.setData(new ActionCase[] {ActionCase.DELETE_DEVICE});
		mapMoveDevice.setData(new ActionCase[]{ActionCase.MOVE_POSITION});
		
		/**
		 * 设置地图鼠标事件监听
		 */
		pane.addMouseListener(new MapMouseListener() {

			@Override
			public void onMouseClicked(MapMouseEvent e) {
//				System.out.println(e.getMapPosition().getX());
//				System.out.println(e.getMapPosition().getY());
//				System.out.println(e.getPoint().x);
//				System.out.println(e.getPoint().y);

				mapPosition = e.getMapPosition();// 获取到点击的位置

			}

			@Override
			public void onMouseDragged(MapMouseEvent e) {
			}

			@Override
			public void onMouseEntered(MapMouseEvent e) {
			}

			@Override
			public void onMouseExited(MapMouseEvent e) {
			}

			@Override
			public void onMouseMoved(MapMouseEvent e) {

			}

			@Override
			public void onMousePressed(MapMouseEvent e) {
			}

			@Override
			public void onMouseReleased(MapMouseEvent e) {
				DirectPosition2D mapPosition = e.getMapPosition();
				coordinate = new Coordinate(mapPosition.getX(), mapPosition.getY());
			}

			/**
			 * 鼠标滚轮滚动缩放地图，上滑放大，下滑缩小
			 */
			@Override
			public void onMouseWheelMoved(MapMouseEvent e) {
				/**
				 * 鼠标向上滑动时
				 */
				if (e.getWheelAmount() > 0) {
					// 获取swtMapPane的大小信息
					Rectangle paneArea = pane.getBounds();
					// 获取当前鼠标指向的坐标位置
					DirectPosition2D mapPos = e.getMapPosition();

					// 设置缩放比
					double scale = pane.getWorldToScreenTransform().getScaleX();
					double newScale = scale * 1.1;

					// 角点位置
					DirectPosition2D corner = new DirectPosition2D(mapPos.getX() - 0.5d * paneArea.width / newScale, mapPos.getY() + 0.5d * paneArea.height / newScale);

					// 设置缩放后显示的地图区域
					Envelope2D newMapArea = new Envelope2D();
					newMapArea.setFrameFromCenter(mapPos, corner);
					pane.setDisplayArea(newMapArea);
				} else {
					/**
					 * 鼠标向下滑动
					 */
					Rectangle paneArea = pane.getBounds();
					DirectPosition2D mapPos = e.getMapPosition();

					double scale = pane.getWorldToScreenTransform().getScaleX();
					double newScale = scale / 1.1;

					DirectPosition2D corner = new DirectPosition2D(mapPos.getX() - 0.5d * paneArea.width / newScale, mapPos.getY() + 0.5d * paneArea.height / newScale);

					Envelope2D newMapArea = new Envelope2D();
					newMapArea.setFrameFromCenter(mapPos, corner);
					pane.setDisplayArea(newMapArea);
				}
			}
		});

		/**
		 * 设置pane监听
		 */
		pane.addMapPaneListener(new MapPaneListener() {

			@Override
			public void onDisplayAreaChanged(MapPaneEvent e) {
			}

			@Override
			public void onNewContext(MapPaneEvent e) {
			}

			@Override
			public void onNewRenderer(MapPaneEvent e) {
			}

			@Override
			public void onRenderingProgress(MapPaneEvent e) {
			}

			@Override
			public void onRenderingStarted(MapPaneEvent e) {
			}

			@Override
			public void onRenderingStopped(MapPaneEvent e) {
			}

			@Override
			public void onResized(MapPaneEvent e) {
			}
		});

	}

	/**
	 * 加载地图
	 * 
	 * @throws IOException
	 */
	public void load() throws IOException {
		// File folder = new File(TestMapURL);

		String path = Tool.getWorkPath();
		File folder = new File(path + File.separatorChar + "map");
		if (folder.exists()) {
			// ShapeFile 最少需要 .shp, .dbf, .shx 三个文件 ,
			File[] files = folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isFile()) {
						return f.getName().endsWith(".shp");
					}
					return false;
				}
			});
			if (files != null && files.length > 0) {
				// MapContent content = new MapContent();
				for (File shp : files) {
					FileDataStore store = FileDataStoreFinder.getDataStore(shp);
					if (store != null) {
						((ShapefileDataStore) store).setCharset(Charset.forName("GBK"));
						SimpleFeatureSource source = store.getFeatureSource();

						Style style = null;
						String s = shp.getAbsolutePath();
						s = s.substring(0, s.length() - 4) + ".sld";
						File sld = new File(s);
						if (sld.exists()) {
							SLDParser stylereader = new SLDParser(CommonFactoryFinder.getStyleFactory(), sld.toURI().toURL());
							Style[] styles = stylereader.readXML();
							if (styles != null && styles.length > 0)
								style = styles[0];
						} else {
							style = SLD.createSimpleStyle(source.getSchema());
						}

						Layer layer = new FeatureLayer(source, style);
						layers.add(layer);

					}
				}
			}
		}
	}

	/**
	 * 显示地图
	 */
	public void show(Servo s) {
		if(layers.isEmpty()){
			return;
		}
		content.addLayers(layers);
		pane.setCursorTool(new PanTool());
		pane.setVisible(true);
		baseCRS = pane.getMapContent().getCoordinateReferenceSystem();
		showDeviceOnMap(s);
	}

	/**
	 * 重置地图位置
	 */
	public void reset(Servo s) {
		if(layers.isEmpty()){
			return;
		}
		pane.reset();
		if (baseCRS != null) {
			pane.getMapContent().getViewport().setCoordinateReferenceSystem(baseCRS);
		}
		showDeviceOnMap(s);
	}

	/**
	 * 设置光标为移动光标
	 */
	public void pan() {
		pane.setCursorTool(new PanTool());
	}

	/**
	 * 设置光标为放大光标
	 */
	public void zoomIn() {
		pane.setCursorTool(new ZoomInTool());
	}

	/**
	 * 设置光标为缩小光标
	 */
	public void zoomOut() {
		pane.setCursorTool(new ZoomOutTool());
	}

	/**
	 * 旋转地图 地图没有坐标系无法旋转
	 */
	public void rotate() {
		CoordinateReferenceSystem currentCRS = pane.getMapContent().getCoordinateReferenceSystem();
		if (currentCRS == null) {
			return;
		}
		CoordinateReferenceSystem transformedCRS = currentCRS;
		AffineTransform rotation = new AffineTransform();
		rotation.rotate(Math.PI / 36);
		MathTransform transform = ProjectiveTransform.create(rotation);
		transformedCRS = new DefaultDerivedCRS("rotate", currentCRS, transform, currentCRS.getCoordinateSystem());
		pane.getMapContent().getViewport().setCoordinateReferenceSystem(transformedCRS);
	}

	/**
	 * 将地图转换为图片
	 * 
	 * @param f
	 */
	public void image(File f) {
		Image image = new Image(null, pane.getBounds());
		GC gc = new GC(image);
		pane.print(gc);

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };
		loader.save(f.getAbsolutePath(), SWT.IMAGE_PNG);
	}

	/**
	 * 图层工具栏中
	 * 
	 * @param i
	 */
	public void layers(ToolItem i) {
		if(layers.isEmpty()){
			return;
		}
		
		if (menus == null) {
			menus = new Menu(i.getDisplay().getActiveShell(), SWT.POP_UP);
			for (Layer layer : layers) {
				MenuItem menu = new MenuItem(menus, SWT.CHECK);
				String name = layer.getTitle();
				menu.setText(name == null ? "no name" : name);
				menu.setSelection(layer.isVisible());
				menu.setData(layer);
				menu.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						MenuItem menu = (MenuItem) e.widget;
						Layer layer = (Layer) menu.getData();
						layer.setVisible(menu.getSelection());
					}
				});
			}
		} else {
			menus = null;
			menus = new Menu(i.getDisplay().getActiveShell(), SWT.POP_UP);
			for (Layer layer : layers) {
				MenuItem menu = new MenuItem(menus, SWT.CHECK);
				String name = layer.getTitle();
				menu.setText(name == null ? "no name" : name);
				menu.setSelection(layer.isVisible());
				menu.setData(layer);
				menu.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						MenuItem menu = (MenuItem) e.widget;
						Layer layer = (Layer) menu.getData();
						layer.setVisible(menu.getSelection());
					}
				});
			}
		}

		// 设置图层下拉菜单显示位置
		org.eclipse.swt.graphics.Rectangle rect = i.getBounds();
		Point pt = new Point(rect.x, rect.y + rect.height);
		pt = i.getParent().toDisplay(pt);
		menus.setLocation(pt.x, pt.y);
		menus.setVisible(true);
	}

	public void select(Servo s) {
		pane.setCursorTool(new NormalMouseTool(pane, s));
	}

	public void frame() {
		if(layers.isEmpty()){
			return;
		}
		SwtMapFrame.showMap(content);
	}

	/**
	 * 在地图上添加设备
	 * 
	 * @param deviceId
	 * @param deviceCoordinate
	 * @param s
	 */
	public void addDevice(String deviceId, Coordinate deviceCoordinate, Servo s, boolean isFristAdd) {
		if (pane.isDisposed()) {
			return;
		}

		this.servo = s;

		if (servo == null) {
			return;
		}

		if (isFristAdd) {
			CtrClient client = servo.getClient();
			if (client.isConnected() && client.isRunning()) {
				Message.Builder b_m = Message.newBuilder();
				MovePosition.Builder movePosition = MovePosition.newBuilder();
				movePosition.setId(deviceId);
				movePosition.setLongitude(deviceCoordinate.x);
				movePosition.setLatitude(deviceCoordinate.y);
				movePosition.setUpdated(new Date().getTime());
				b_m.setMovePosition(movePosition);
				client.send(b_m.build());
			} else {
				MessageBox mServoNotConnect = new MessageBox(main.getShell(), SWT.ICON_INFORMATION | SWT.OK);
				mServoNotConnect.setText("移动设备");
				mServoNotConnect.setMessage("伺服器断开连接");
				mServoNotConnect.open();
			}
		}

		Device device = servo.getDevice(deviceId);

		// 新建设备的Feature
		SimpleFeatureBuilder build = new SimpleFeatureBuilder(TYPE);
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		com.vividsolutions.jts.geom.Point point = null;
		if (deviceCoordinate != null) {
			point = geometryFactory.createPoint(deviceCoordinate);
		} else {
			double longitude = device.getLongitude();// 设备的经度
			double latitude = device.getLatitude();// 设备的纬度
			Coordinate c = new Coordinate(longitude, latitude);
			point = geometryFactory.createPoint(c);
		}

		build.add(point);
		build.add(deviceId);
		build.add(device.getKind().getValue());

		// 创建Feature并设定id为设备类型
		SimpleFeature buildFeature = build.buildFeature(device.getId());
		list.add(buildFeature);

		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, list);

		// 存放的是Feature的集合
		FeatureSource<?, ?> source = new CollectionFeatureSource(collection);

		StyleBuilder sb = new StyleBuilder();
		Graphic graphic = sb.createGraphic();
		URL resource2 = null;

		TextSymbolizer text = styleFactory.createTextSymbolizer();
		FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
		text.setFont(styleFactory.createFont(filterFactory.literal("@微软雅黑"), filterFactory.literal("normal"), filterFactory.literal("bold"), filterFactory.literal("14")));

		resource2 = Resource.class.getResource(F.getDeviceImageURL(device));
		

		String indication = device.getIndicate();
		if (Tool.isEmpty(indication)) {
			text.setLabel(filterFactory.literal("   " + device.getName()));
		} else {
			text.setLabel(filterFactory.literal("   " + device.getName() + "/" + indication));
		}

		ExternalGraphic external = sb.createExternalGraphic(resource2, "image/png");
		graphic.graphicalSymbols().clear();
		graphic.graphicalSymbols().add(external);
		graphic.setSize(filterFactory.literal(20));
		// 创建点的样式，第一个参数是graphic图形，第二个参数是样式名字
		PointSymbolizer pointSymbolizer = styleFactory.createPointSymbolizer(graphic, null);
		pointSymbolizer.getOptions().put("maxDisplacement", "150");

		// 创建rule
		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(pointSymbolizer);
		rule.symbolizers().add(text);
		rule.setName(deviceId);

		Set<FeatureId> Ids = new HashSet<>();
		Ids.add(buildFeature.getIdentifier());
		rule.setFilter(filterFactory.id(Ids));
		fts.rules().add(rule);

		style.featureTypeStyles().add(fts);
		// *****************************

		if (layers.size() <= 0) {
			return;
		}

		// 先移除已有图层再添加新的图层
		content.removeLayer(deviceLayer);
		layers.remove(deviceLayer);
		deviceLayer = new FeatureLayer(source, style);
		deviceLayer.setTitle(DEVICE_LAYER);
		layers.add(deviceLayer);
		content.addLayer(deviceLayer);

		exitsDevices.add(device);
	}

	/**
	 * 在地图上添加设备
	 * 
	 * @param deviceId
	 * @param deviceCoordinate
	 * @param s
	 */
	public void showDevice(Set<Device> devices, Coordinate deviceCoordinate) {

		if (pane.isDisposed()) {
			return;
		}

		list.clear();

		for (Device device : devices) {
			String deviceId = device.getId();

			// 新建设备的Feature
			SimpleFeatureBuilder build = new SimpleFeatureBuilder(TYPE);
			GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
			com.vividsolutions.jts.geom.Point point = null;
			if (deviceCoordinate != null) {
				point = geometryFactory.createPoint(deviceCoordinate);
			} else {
				double longitude = device.getLongitude();// 设备的经度
				double latitude = device.getLatitude();// 设备的纬度
				Coordinate c = new Coordinate(longitude, latitude);
				point = geometryFactory.createPoint(c);
			}

			build.add(point);
			build.add(deviceId);
			build.add(device.getKind().getValue());

			// 创建Feature并设定id为设备类型
			SimpleFeature buildFeature = build.buildFeature(device.getId());
			list.add(buildFeature);

			// *************************************************************************

			StyleBuilder sb = new StyleBuilder();
			Graphic graphic = sb.createGraphic();
			URL resource2 = null;

			TextSymbolizer text = styleFactory.createTextSymbolizer();
			FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
			text.setFont(styleFactory.createFont(filterFactory.literal("@微软雅黑"), filterFactory.literal("normal"), filterFactory.literal("bold"), filterFactory.literal("14")));

			resource2 = Resource.class.getResource(F.getDeviceImageURL(device));
			
			String indication = device.getIndicate();
			if (Tool.isEmpty(indication)) {
				text.setLabel(filterFactory.literal("   " + device.getName()));
			} else {
				text.setLabel(filterFactory.literal("   " + device.getName() + "/" + indication));
			}

			if (resource2 == null) {
				return;
			}

			ExternalGraphic external = sb.createExternalGraphic(resource2, "image/png");
			graphic.graphicalSymbols().clear();
			graphic.graphicalSymbols().add(external);
			graphic.setSize(filterFactory.literal(20));
			// 创建点的样式，第一个参数是graphic图形，第二个参数是样式名字
			PointSymbolizer pointSymbolizer = styleFactory.createPointSymbolizer(graphic, null);
			pointSymbolizer.getOptions().put("maxDisplacement", "150");

			// 创建rule
			Rule rule = styleFactory.createRule();
			rule.symbolizers().add(pointSymbolizer);
			rule.symbolizers().add(text);
			rule.setName(deviceId);

			Set<FeatureId> Ids = new HashSet<>();
			Ids.add(buildFeature.getIdentifier());
			rule.setFilter(filterFactory.id(Ids));
			fts.rules().add(rule);

			style.featureTypeStyles().add(fts);

			exitsDevices.add(device);
		}

		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, list);

		// 存放的是Feature的集合
		FeatureSource<?, ?> source = new CollectionFeatureSource(collection);
		// *****************************

		if (layers.size() <= 0) {
			return;
		}

		// 先移除已有图层再添加新的图层
		content.removeLayer(deviceLayer);
		layers.remove(deviceLayer);
		deviceLayer = new FeatureLayer(source, style);
		deviceLayer.setTitle(DEVICE_LAYER);
		layers.add(deviceLayer);
		content.addLayer(deviceLayer);

	}

	/**
	 * 在地图上更新设备状态
	 * 
	 * @param device
	 * @param s
	 */
	public void update(Device device, Servo s) {
		servo = s;
		if (pane.isDisposed()) {
			return;
		}

		if (device == null) {
			return;
		}

		String id = device.getId();// 更新的设备id
		// DeviceStatus status = device.getStatus();// 拿到最新的设备状态，用于修改设备图片

		if (deviceLayer == null) {
			return;
		}

		SimpleFeature changeFeature = getFeatureByDeviceID(id, deviceLayer);
		
		if(changeFeature == null){
			return;
		}

		List<FeatureTypeStyle> featureTypeStyles = style.featureTypeStyles();
		if (featureTypeStyles.size() == 0) {
			return;
		}

		FeatureTypeStyle featureTypeStyle = featureTypeStyles.get(0);
		List<Rule> rules = featureTypeStyle.rules();

		Rule changeRule = null;
		for (Rule rule : rules) {
			if (rule.getName().equals(id)) {
				if (servo == null) {
				}

				changeRule = rule;
				break;
			}
		}

		if (changeRule == null) {
			return;
		}

		// 从已有的rule集合中移除需要修改的设备style rule
		rules.remove(changeRule);

		// 重新设定新的rule，并添加到style中
		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, list);

		// 存放的是Feature的集合
		FeatureSource<?, ?> source = new CollectionFeatureSource(collection);

		StyleBuilder sb = new StyleBuilder();
		Graphic graphic = sb.createGraphic();
		URL resource2 = null;

		TextSymbolizer text = styleFactory.createTextSymbolizer();
		FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
		text.setFont(styleFactory.createFont(filterFactory.literal("@微软雅黑"), filterFactory.literal("normal"), filterFactory.literal("bold"), filterFactory.literal("14")));

		resource2 = Resource.class.getResource(F.getDeviceImageURL(device));
		
		String indication = device.getIndicate();
		if (Tool.isEmpty(indication)) {
			text.setLabel(filterFactory.literal("   " + device.getName()));
		} else {
			text.setLabel(filterFactory.literal("   " + device.getName() + "/" + indication));
		}

		ExternalGraphic external = sb.createExternalGraphic(resource2, "image/png");
		graphic.graphicalSymbols().clear();
		graphic.graphicalSymbols().add(external);
		graphic.setSize(filterFactory.literal(20));
		// 创建点的样式，第一个参数是graphic图形，第二个参数是样式名字
		PointSymbolizer pointSymbolizer = styleFactory.createPointSymbolizer(graphic, null);
		pointSymbolizer.getOptions().put("maxDisplacement", "150");

		// 创建rule
		Rule rule = styleFactory.createRule();
		rule.symbolizers().add(pointSymbolizer);
		rule.symbolizers().add(text);
		rule.setName(id);

		Set<FeatureId> Ids = new HashSet<>();

		Ids.add(changeFeature.getIdentifier());
		rule.setFilter(filterFactory.id(Ids));
		fts.rules().add(rule);

		style.featureTypeStyles().add(fts);
		/**
		 * SLD -风格化图层描述器（Styled Layer Descriptor）
		 * SLD描述了如何在WMS规范的基础上进行扩展使之支持用户对要素数据进行自定义的符号化显示。
		 */

		Style createStyle = styleFactory.createStyle();
		createStyle.featureTypeStyles().add(fts);
		if (layers.size() <= 0) {
			return;
		}

		// UserLayer layer = styleFactory.createUserLayer();

		content.removeLayer(deviceLayer);
		layers.remove(deviceLayer);
		deviceLayer = new FeatureLayer(source, style);
		deviceLayer.setTitle(DEVICE_LAYER);

		layers.add(deviceLayer);
		content.addLayer(deviceLayer);
	}

	/**
	 * 将所有设备加到地图上
	 */
	public void showDeviceOnMap(Servo s) {

		if (s == null) {
			return;
		}


		Set<Device> devices = s.getDevices();
		Set<Device> showDevices = new HashSet<>();
		for (Device device : devices) {
			// String id = device.getId();
			double longitude = device.getLongitude();
			double latitude = device.getLatitude();
			if (longitude != 0.0 || latitude != 0.0) {
				// 设备已经有了设定好的经纬度
				showDevices.add(device);
				// addDevice(id, null, s, false);
			}
		}

		showDevice(showDevices, null);
	}

	/**
	 * 从地图中移除已有的设备（并不是删除而是移除）
	 * 
	 * @param s
	 * @param device
	 */
	public void deleteDeviceOnMap(Servo s, Device device) {
		if (pane.isDisposed()) {
			return;
		}

		if (s == null) {
			return;
		}

		if (device == null) {
			return;
		}

		if (deviceLayer == null) {
		}
		String id = device.getId();// 移除设备的id
		SimpleFeature feature = getFeatureByDeviceID(id, deviceLayer); // 获取到需要删除的feature
		list.remove(feature);// 从保存已有feature的集合中移除需要删除的feature
		// 重新设定新的rule，并添加到style中
		SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, list);
		// 存放的是Feature的集合
		FeatureSource<?, ?> source = new CollectionFeatureSource(collection);

		if (layers.size() <= 0) {
			return;
		}

		// UserLayer layer = styleFactory.createUserLayer();

		content.removeLayer(deviceLayer);
		layers.remove(deviceLayer);
		deviceLayer = new FeatureLayer(source, style);
		deviceLayer.setTitle(DEVICE_LAYER);

		layers.add(deviceLayer);
		content.addLayer(deviceLayer);

		exitsDevices.remove(device);
	}

	/**
	 * 通过设备id获取Feature
	 * 
	 * @param deviceId
	 * @param deviceLayer
	 * @return
	 */
	private SimpleFeature getFeatureByDeviceID(String deviceId, Layer deviceLayer) {
		// 从设备图层中取出所有的feature
		if (deviceLayer == null) {
			return null;
		}

		if (Tool.isEmpty(deviceId)) {
			return null;
		}

		FeatureSource<?, ?> featureSource = deviceLayer.getFeatureSource();
		FeatureCollection<?, ?> features = null;
		try {
			features = featureSource.getFeatures();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (features == null) {
			return null;
		}

		FeatureIterator<?> itertor = features.features();

		SimpleFeature changeFeature = null;// 用来保存需要更改，利用设备id来进行判断

		while (itertor.hasNext()) {
			SimpleFeature feature = (SimpleFeature) itertor.next();
			String checkId = (String) feature.getAttribute("id");
			if (checkId.equals(deviceId)) {
				changeFeature = feature;
				break;
			}
		}

		if (changeFeature == null) {
			return null;
		}

		return changeFeature;
	}

	/**
	 * 移动地图上已有的设备
	 * 
	 * @param deviceId
	 * @param coordinate
	 * @param s
	 */
	public void moveDevice(String deviceId, Servo s) {

		if (deviceId == null || Tool.isEmpty(deviceId)) {
			return;
		}

		Device device = s.getDevice(deviceId);

		if (device == null) {
			throw new IllegalStateException("device 不能为空");
		}

		// 1.移除需要移动的设备
		deleteDeviceOnMap(s, device);

		// 2.设置添加设备光标
		pane.setCursorTool(new AddDeviceTool(deviceId, this, s));

	}

	/**
	 * 菜单栏筛选或者搜索显示 tip：只显示拥有坐标系地址的设备
	 * 
	 * @param d
	 */
	public void search(Set<Device> d, Servo s) {

		if (pane.isDisposed()) {
			return;
		}

		if (s == null) {
			return;
		}

		// // 2.将筛选出的设备进一步筛选，获取已有坐标系的设备
		Set<Device> devices = new HashSet<>();// 该集合是最终需要显示在地图上的设备
		for (Device device : d) {
			if (device.getLongitude() != 0.0 && device.getLatitude() != 0.0) {
				devices.add(device);
			}
		}


		// 1.清空地图上的所有设备
		content.removeLayer(deviceLayer);
		layers.remove(deviceLayer);

		// // 3.将设备显示在地图上
		// for (Device device1 : devices) {
		// String id = device1.getId();
		// addDevice(id, null, s, false);
		// }

		showDevice(devices, null);

	}

	/**
	 * 树形菜单点击设备后显示
	 * 
	 * @param d
	 * @param s
	 */
	public void setDisplayByDevice(Device d) {
		if (pane.isDisposed()) {
			return;
		}


		if (d == null) {
			return;
		}


		double longitude = d.getLongitude();// 经度
		double latitude = d.getLatitude();// 纬度
		
		// 当地图上没有改设备时不放大
		if (longitude == 0.0 && latitude == 0.0) {
			return;
		}
		

		// 将给设备的坐标设为放大后地图的中点
		DirectPosition2D startDragPos = new DirectPosition2D();
		startDragPos.setLocation(longitude, latitude);

		// 获取到当前设备可见的大小
		Rectangle paneArea = pane.getVisibleRect();

		if (pane.getWorldToScreenTransform() == null) {
			return;
		}

		double scale = pane.getWorldToScreenTransform().getScaleX();
		double newScale = scale * 1.1;

		// 设置放大区域的角点
		DirectPosition2D corner = new DirectPosition2D(startDragPos.getX() - 0.5d * paneArea.width / newScale, startDragPos.getY() + 0.5d * paneArea.height / newScale);

		Envelope2D newMapArea = new Envelope2D();
		newMapArea.setFrameFromCenter(startDragPos, corner);
		// 显示放大后的区域
		pane.setDisplayArea(newMapArea);
	}

	public SwtMapPane getPane() {
		return pane;
	}


	private MenuAdapter menuAdapter = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent arg0) {
			Menu menu = (Menu) arg0.widget;
			MenuItem[] items = menu.getItems();
			MenuCommandUtil m = new MenuCommandUtil(main);
			m.LoadMenuForCommand(items);
		}

	};

}

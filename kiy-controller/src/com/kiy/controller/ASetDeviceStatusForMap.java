package com.kiy.controller;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.swt.SwtMapPane;
import org.geotools.swt.tool.InfoToolHelper;
import org.geotools.swt.tool.VectorLayerHelper;
import org.geotools.swt.utils.Utils;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;
import org.opengis.feature.type.Name;

import com.kiy.common.Device;
import com.kiy.common.Servo;
import com.kiy.controller.view.MapView;

/**
 * 在地图上设置设备状态
 * 
 * @author HLX
 *
 */
public class ASetDeviceStatusForMap extends A<FMain> {

	private MapView mv; // 用来获取设备坐标信息

	/**
	 * 
	 * @param m FMain
	 * @param mv MapView
	 */
	public ASetDeviceStatusForMap(FMain m, MapView mv) {
		super(m);
		this.mv = mv;
	}

	@Override
	protected void run() {
		SwtMapPane pane = mv.getPane();
		if (pane == null) {
			return;
		}

		if (pane.isDisposed()) {
			return;
		}

		WeakHashMap<Layer, InfoToolHelper<?>> helperTable = new WeakHashMap<Layer, InfoToolHelper<?>>();
		DirectPosition2D mapPosition = mv.getMapPosition();
		MapContent content = pane.getMapContent();
		Servo servo = main.getCurrentServo();

		if (servo == null) {
			throw new NullPointerException(getClass() + "servo 为空");
		}

		if (mapPosition == null) {
			return;
		}

		String deviceId = null;
		// 遍历所有图层
		for (Layer layer : content.layers()) {
			if (layer == null) {
				continue;
			}
			if (layer.isSelected()) {
				InfoToolHelper<?> helper = null;
				// 找到设备图层
				if (layer != null && layer.getTitle() != null && layer.getTitle().equals(MapView.DEVICE_LAYER)) {

					// 查找图层信息
					helper = helperTable.get(layer);

					if (helper == null) {
						if (Utils.isGridLayer(layer)) {
							try {
								Class<?> clazz = Class.forName("org.geotools.swt.tool.GridLayerHelper");
								Constructor<?> ctor = clazz.getConstructor(MapContent.class, Layer.class);
								helper = (InfoToolHelper<?>) ctor.newInstance(content, layer);
								helperTable.put(layer, helper);

							} catch (Exception ex) {
								throw new IllegalStateException("Failed to create InfoToolHelper for grid layer", ex);
							}

						} else {
							try {
								Class<?> clazz = Class.forName("org.geotools.swt.tool.VectorLayerHelper");
								Constructor<?> ctor = clazz.getConstructor(MapContent.class, Layer.class);
								helper = (InfoToolHelper<?>) ctor.newInstance(content, layer);
								helperTable.put(layer, helper);

							} catch (Exception ex) {
								throw new IllegalStateException("Failed to create InfoToolHelper for vector layer", ex);
							}
						}
					}

					Object info = null;

					if (helper instanceof VectorLayerHelper) {
						ReferencedEnvelope mapEnv = pane.getDisplayArea();
						double searchWidth = MapView.DEFAULT_DISTANCE_FRACTION * (mapEnv.getWidth() + mapEnv.getHeight()) / 2;
						try {
							info = helper.getInfo(mapPosition, Double.valueOf(searchWidth));
						} catch (Exception ex) {
							throw new IllegalStateException(ex);
						}

						if (info != null) {
							FeatureIterator<? extends Feature> iter = null;
							SimpleFeatureCollection selectedFeatures = (SimpleFeatureCollection) info;
							try {
								iter = selectedFeatures.features();
								// 遍历点击区域内所有的feature
								while (iter.hasNext()) {
									Feature next = iter.next();
									// 提取feature内的所有信息
									Collection<Property> properties = next.getProperties();

									for (Property property : properties) {

										Name name = property.getName();
										// id为添加设备时给设备id设置的key
										if (name.toString().equals("id")) {
											Object value = property.getValue();
											// 点击的设备id
											deviceId = value.toString();
										}
									}

								}

							} catch (Exception ex) {
								throw new IllegalStateException(ex);

							} finally {
								if (iter != null) {
									iter.close();
								}
							}
						}

					} else {
						try {
							info = helper.getInfo(mapPosition);
						} catch (Exception ex) {
							throw new IllegalStateException(ex);
						}

						if (info != null) {
							@SuppressWarnings("unchecked")
							List<String> bandValues = (List<String>) info;
							if (!bandValues.isEmpty()) {
							}
						}
					}
				}
			}
		}

		if (deviceId == null) {
			return;
		}

		// 打开设置设备状态窗口
		Device d = servo.getDevice(deviceId);

		if (d != null) {
			ServoManager serverManager = main.getServerManager();
			FDeviceStatus f = new FDeviceStatus(main.getShell());
			serverManager.add(f, servo);
			f.open(d, servo);
			serverManager.off(f);
		}
	}
}

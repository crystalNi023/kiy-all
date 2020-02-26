package com.kiy.servo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kiy.common.Device;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Vendor;
import com.kiy.common.devices.ESAirES0014;
import com.kiy.common.devices.ESCurtainES0013;
import com.kiy.common.devices.ESCurtainES0013_01;
import com.kiy.common.devices.ESDimmingES0011;
import com.kiy.common.devices.ESDimmingES0019_01;
import com.kiy.common.devices.ESDimmingES0019_02;
import com.kiy.common.devices.ESInfraredTransponderES0026;
import com.kiy.common.devices.ESNetworkedThermostatES0024;
import com.kiy.common.devices.ESSmartLockES0016;
import com.kiy.common.devices.ESSwitchES0004;
import com.kiy.common.devices.ESSwitchES0010;
import com.kiy.common.devices.ESSwitchFourES0003;
import com.kiy.common.devices.ESSwitchFourES0003_01;
import com.kiy.common.devices.ESSwitchFourES0003_02;
import com.kiy.common.devices.ESSwitchFourES0003_03;
import com.kiy.common.devices.ESSwitchFourES0003_04;
import com.kiy.common.devices.ESSwitchFourES0020;
import com.kiy.common.devices.ESSwitchFourES0020_01;
import com.kiy.common.devices.ESSwitchFourES0020_02;
import com.kiy.common.devices.ESSwitchFourES0020_03;
import com.kiy.common.devices.ESSwitchFourES0020_04;
import com.kiy.common.devices.ESSwitchTouchTwoES0006;
import com.kiy.common.devices.ESSwitchTouchTwoES0006_01;
import com.kiy.common.devices.ESSwitchTouchTwoES0006_02;
import com.kiy.common.devices.ESSwitchTwoES0005;
import com.kiy.common.devices.ESSwitchTwoES0005_01;
import com.kiy.common.devices.ESSwitchTwoES0005_02;
import com.kiy.common.devices.ESToningES0012;
import com.kiy.common.devices.ESTvES0015;
import com.kiy.common.devices.ESWindowPusherES0025;
import com.kiy.servo.Config;
import com.kiy.servo.driver.rs485.CRC16M;

/**
 * MQTT数据处理
 * 
 * @author HLX Tel:18996470535
 * @date 2018年6月25日 Copyright:Copyright(c) 2018
 */
public class MQTTCoder {
	/**
	 * 读取数据
	 */
	public static final int READ = 1;
	/**
	 * 解析数据
	 */
	public static final int WRITE = 2;

	public static final String BIAS = "/";

	/**
	 * 编辑发布数据
	 * 
	 * @param device
	 * @param action
	 * @throws MqttException
	 * @throws MqttPersistenceException
	 */
	public void encode(String GatewayKey, Device device, MQTTServer client, int action) throws MqttPersistenceException, MqttException {
		System.err.println("进来几次，encode");
		Vendor vendor = device.getVendor();
		Model model = device.getModel();
		String number = device.getNumber();
		String sn = device.getSn();
		MqttMessage message = new MqttMessage();
		message.setQos(1);
		message.setRetained(true);
		StringBuilder sb = new StringBuilder();
		StringBuilder topic = sb.append(Config.MQTT_SUBSCRIPTION_TOPIC).append(GatewayKey).append(BIAS).append(number).append(BIAS).append("command");
		switch (action) {
			case READ:
				if (vendor == Vendor.EASTSOFT) {
					switch (model) {
						case ES_0001:
						case ES_0002: {
							String string = "{\"cmd\":\"read\"}";
							message.setPayload(string.getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
//						case ES_0022_01:
//						case ES_0003_01: 
//						case ES_0022_02:
//							case ES_0003_02: 
//							case ES_0022_03:
//								case ES_0003_03: 
//								case ES_0003_04: 
						case ES_0022://单火线开关（3个）发送消息同四路开关
						case ES_0003: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch1", true);
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("switch_ch2", true);
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("switch_ch3", true);
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("switch_ch4", true);
							jsonArray.add(channel1);
							jsonArray.add(channel2);
							jsonArray.add(channel3);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0022_01:
						case ES_0003_01: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel = new JsonObject();
							channel.addProperty("switch_ch1", true);
							jsonArray.add(channel);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0022_02:
						case ES_0003_02: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel = new JsonObject();
							channel.addProperty("switch_ch2", true);
							jsonArray.add(channel);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0022_03:
						case ES_0003_03: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel = new JsonObject();
							channel.addProperty("switch_ch3", true);
							jsonArray.add(channel);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0003_04: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel = new JsonObject();
							channel.addProperty("switch_ch4", true);
							jsonArray.add(channel);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0004: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005:
						case ES_0006: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject2.addProperty("switch_ch2", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005_01: 
						case ES_0006_01: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005_02: 
						case ES_0006_02: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch2", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005_03: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch3", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0007: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("messageString", "01040000000271CB");
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0008: {
						}
							break;
						case ES_0009: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("humidness", 0);
							jsonObject2.addProperty("temperature", 0);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0010: {
							/**
							 * 1.请求开关
							 */
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
							/**
							 * 2.请求电力读数
							 */
							JsonObject jsonObject3 = new JsonObject();
							jsonObject3.addProperty("deviceKey", number);
							jsonObject3.addProperty("cmd", "read");
							JsonObject jsonObject4 = new JsonObject();
							jsonObject4.addProperty("electricity", 0);
							jsonObject4.addProperty("current", 0);
							jsonObject4.addProperty("power", 0);
							jsonObject4.addProperty("voltage", 0);
							jsonObject3.add("function", jsonObject4);
							message.setPayload(jsonObject3.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0011: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("switch_ch1", true);
							jsonObject1.addProperty("diming", 0);
							jsonObject.add("function", jsonObject1);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0012: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject2.addProperty("diming", 0);
							jsonObject2.addProperty("color", 0);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0013: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("curtain_percent", 0);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0013_01: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("curtain_percent", 0);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0014: {
							// 请求温湿度
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("temperature", 0);
							jsonObject22.addProperty("humidness", 0);
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0015: {
							// 请求温湿度
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("temperature", 0);
							jsonObject22.addProperty("humidness", 0);
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0016:{
							
						}
						break;
						case ES_0017: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("messageString", "020300040001C5F8");
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0018: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("messageString", "01030007000275CA");
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0019:{
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject2.addProperty("switch_ch2", true);
							jsonObject2.addProperty("diming_current_ch1", 0);
							jsonObject2.addProperty("diming_current_ch2", 0);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0019_01:{
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);

							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("diming_current_ch1", 0);
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0019_02:{
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch2", true);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
							
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("diming_current_ch2", 0);
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("channel", 1);
							channel1.addProperty("switch", true);
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("channel", 2);
							channel2.addProperty("switch", true);
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("channel", 3);
							channel3.addProperty("switch", true);
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("channel", 4);
							channel4.addProperty("switch", true);
							jsonArray.add(channel1);
							jsonArray.add(channel2);
							jsonArray.add(channel3);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0020_01: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("channel", 1);
							channel1.addProperty("switch", true);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_02: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("channel", 2);
							channel2.addProperty("switch", true);
							jsonArray.add(channel2);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_03: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("channel", 3);
							channel3.addProperty("switch", true);
							jsonArray.add(channel3);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_04: {
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "read");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("channel", 4);
							channel4.addProperty("switch", true);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0023: {
							// 通过读取是否上报，判断离线
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("model", "report");
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0026: {
							// 请求温湿度
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "read");
							JsonObject jsonObject22 = new JsonObject();
							jsonObject22.addProperty("temperature", 0);
							jsonObject22.addProperty("humidness", 0);
							jsonObject1.add("function", jsonObject22);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						default:
							break;
					}
				}
				break;
			case WRITE:
				if (vendor == Vendor.EASTSOFT) {
					switch (model) {
						case ES_0001:
						case ES_0002:
							break;
						case ES_0022://单火线开关（3个）发送消息同四路开关
						case ES_0003: {
							ESSwitchFourES0003 d = (ESSwitchFourES0003) device;
							boolean switchOne = d.isSwitchOne();
							boolean switchTwo = d.isSwitchTwo();
							boolean switchThree = d.isSwitchThree();
							boolean swtichFour = d.isSwitchFour();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch1", switchOne);
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("switch_ch2", switchTwo);
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("switch_ch3", switchThree);
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("switch_ch4", swtichFour);
							jsonArray.add(channel1);
							jsonArray.add(channel2);
							jsonArray.add(channel3);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0022_01:
						case ES_0003_01: {
							ESSwitchFourES0003_01 d = (ESSwitchFourES0003_01) device;
							boolean status = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch1", status);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0022_02:
						case ES_0003_02: {
							ESSwitchFourES0003_02 d = (ESSwitchFourES0003_02) device;
							boolean status = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch2", status);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0022_03:
						case ES_0003_03: {
							ESSwitchFourES0003_03 d = (ESSwitchFourES0003_03) device;
							boolean status = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch3", status);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0003_04: {
							ESSwitchFourES0003_04 d = (ESSwitchFourES0003_04) device;
							boolean status = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("switch_ch4", status);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0004: {
							ESSwitchES0004 d = (ESSwitchES0004) device;
							boolean feed = d.isFeed();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", feed);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005: {
							ESSwitchTwoES0005 d = (ESSwitchTwoES0005) device;
							boolean switchOne = d.isSwitchOne();
							boolean switchTwo = d.isSwitchTwo();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", switchOne);
							jsonObject2.addProperty("switch_ch2", switchTwo);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005_01: {
							ESSwitchTwoES0005_01 d = (ESSwitchTwoES0005_01) device;
							boolean switchOne = d.isSwitchOne();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", switchOne);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0005_02: {
							ESSwitchTwoES0005_02 d = (ESSwitchTwoES0005_02) device;
							boolean switchTwo = d.isSwitchTwo();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch2", switchTwo);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0006: {
							ESSwitchTouchTwoES0006 d = (ESSwitchTouchTwoES0006) device;
							boolean switchOne = d.isSwitchOne();
							boolean switchTwo = d.isSwitchTwo();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", switchOne);
							jsonObject2.addProperty("switch_ch2", switchTwo);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0006_01: {
							ESSwitchTouchTwoES0006_01 d = (ESSwitchTouchTwoES0006_01) device;
							boolean switchOne = d.isSwitchOne();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", switchOne);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0006_02: {
							ESSwitchTouchTwoES0006_02 d = (ESSwitchTouchTwoES0006_02) device;
							boolean switchOne = d.isSwitchOne();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch2", switchOne);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0007:
							break;
						case ES_0010: {
							ESSwitchES0010 es = (ESSwitchES0010) device;
							boolean feed = es.isFeed();
							JsonObject jsonObject1 = new JsonObject();
							jsonObject1.addProperty("deviceKey", number);
							jsonObject1.addProperty("cmd", "write");
							JsonObject jsonObject3 = new JsonObject();
							jsonObject3.addProperty("switch_ch1", feed);
							jsonObject1.add("function", jsonObject3);
							message.setPayload(jsonObject1.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0011: {
							ESDimmingES0011 es = (ESDimmingES0011) device;
							boolean feed = es.isFeed();
							int luminosity = es.getLuminosity();

							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", feed);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);

							JsonObject jsonObject3 = new JsonObject();
							jsonObject3.addProperty("deviceKey", number);
							jsonObject3.addProperty("cmd", "write");
							JsonObject jsonObject4 = new JsonObject();
							jsonObject4.addProperty("diming", luminosity);
							jsonObject3.add("function", jsonObject4);
							message.setPayload(jsonObject3.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0012: {
							ESToningES0012 es = (ESToningES0012) device;
							boolean feed = es.isFeed();
							int luminosity = es.getLuminosity();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("switch_ch1", feed);
							if (feed) { // 开
								jsonObject2.addProperty("diming", luminosity);
								// 由于int2RGB会报错，所以直接存在设备的sn中
								jsonObject2.addProperty("color", es.getSn());
							}
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0013: {
							ESCurtainES0013 esCurtainES0013 = (ESCurtainES0013) device;
							boolean control = esCurtainES0013.isControl();
							int feed = esCurtainES0013.getFeed();
							int percent = esCurtainES0013.getPercent();
							if (control) {// 状态控制
								String str = "";
								switch (feed) {
									case 0:
										str = "off";
										break;
									case 1:
										str = "on";
										break;
									case 2:
										str = "stop";
										break;
									default:
										break;
								}
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("curtain_operation", str);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							} else {// 百分比控制
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("curtain_percent", percent);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}

						}
							break;
						case ES_0013_01: {
							ESCurtainES0013_01 esCurtainES0013 = (ESCurtainES0013_01) device;
							boolean control = esCurtainES0013.isControl();
							int feed = esCurtainES0013.getFeed();
							int percent = esCurtainES0013.getPercent();
							if (control) {// 状态控制
								String str = "";
								switch (feed) {
								case 0:
									// 开关控制取反
									str = "on";
									break;
								case 1:
									str = "off";
									break;
								case 2:
									str = "stop";
									break;
								default:
									break;
								}
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("curtain_operation", str);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							} else {// 百分比控制
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								// 百分比控制取反
								jsonObject2.addProperty("curtain_percent", 100-percent);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
							
						}
						break;
						case ES_0014: {
							ESAirES0014 es = (ESAirES0014) device;
							int control = es.getControl();
							boolean learn = es.getLearn();
							if (learn) {
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("infrared_learn_code", ESAirES0014.LEARN_CODE);
								jsonObject2.addProperty("infrared_learn_key", control);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							} else {
								JsonObject jsonObject1 = new JsonObject();
								jsonObject1.addProperty("deviceKey", number);
								jsonObject1.addProperty("cmd", "write");
								JsonObject jsonObject22 = new JsonObject();
								jsonObject22.addProperty("infrared_send_code", ESAirES0014.LEARN_CODE);
								jsonObject22.addProperty("infrared_send_key", control);
								jsonObject1.add("function", jsonObject22);
								message.setPayload(jsonObject1.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
						}
							break;
						case ES_0015: {
							ESTvES0015 es = (ESTvES0015) device;
							int instruct = es.getInstruct();
							boolean learn = es.getLearn();
							if (learn) {
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("infrared_learn_code", ESTvES0015.LEARN_CODE);
								jsonObject2.addProperty("infrared_learn_key", instruct);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							} else {
								JsonObject jsonObject1 = new JsonObject();
								jsonObject1.addProperty("deviceKey", number);
								jsonObject1.addProperty("cmd", "write");
								JsonObject jsonObject22 = new JsonObject();
								jsonObject22.addProperty("infrared_send_code", ESTvES0015.LEARN_CODE);
								jsonObject22.addProperty("infrared_send_key", instruct);
								jsonObject1.add("function", jsonObject22);
								message.setPayload(jsonObject1.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
						}
							break;
						case ES_0016: {
							ESSmartLockES0016 d = (ESSmartLockES0016) device;
							boolean feed = d.isFeed();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("lock_addr", sn);
							jsonObject2.addProperty("unlocking", feed);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0019_01: {
							ESDimmingES0019_01 es = (ESDimmingES0019_01) device;
							boolean feed = es.isFeed();
							int luminosity = es.getLuminosity();

							//开开关时才发送写目标亮度
							if (feed == true) {
								JsonObject jsonObject3 = new JsonObject();
								jsonObject3.addProperty("deviceKey", number);
								jsonObject3.addProperty("cmd", "write");
								JsonObject jsonObject4 = new JsonObject();
								jsonObject4.addProperty("diming_target_ch1", luminosity);
								jsonObject3.add("function", jsonObject4);
								message.setPayload(jsonObject3.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}else{
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("switch_ch1", feed);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
						}
						break;
						case ES_0019_02: {
							ESDimmingES0019_02 es = (ESDimmingES0019_02) device;
							boolean feed = es.isFeed();
							int luminosity = es.getLuminosity();

							if (feed==true) {
								JsonObject jsonObject3 = new JsonObject();
								jsonObject3.addProperty("deviceKey", number);
								jsonObject3.addProperty("cmd", "write");
								JsonObject jsonObject4 = new JsonObject();
								jsonObject4.addProperty("diming_target_ch2", luminosity);
								jsonObject3.add("function", jsonObject4);
								message.setPayload(jsonObject3.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}else{
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("switch_ch2", feed);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
						}
							break;
						case ES_0020: {
							ESSwitchFourES0020 d = (ESSwitchFourES0020) device;
							boolean switchOne = d.isSwitchOne();
							boolean switchTwo = d.isSwitchTwo();
							boolean switchThree = d.isSwitchThree();
							boolean swtichFour = d.isSwitchFour();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("channel", 1);
							channel1.addProperty("switch", switchOne);
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("channel", 2);
							channel2.addProperty("switch", switchTwo);
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("channel", 3);
							channel3.addProperty("switch", switchThree);
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("channel", 4);
							channel4.addProperty("switch", swtichFour);
							jsonArray.add(channel1);
							jsonArray.add(channel2);
							jsonArray.add(channel3);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0020_01: {
							ESSwitchFourES0020_01 d = (ESSwitchFourES0020_01) device;
							boolean switchOne = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel1 = new JsonObject();
							channel1.addProperty("channel", 1);
							channel1.addProperty("switch", switchOne);
							jsonArray.add(channel1);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_02: {
							ESSwitchFourES0020_02 d = (ESSwitchFourES0020_02) device;
							boolean switchTwo = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel2 = new JsonObject();
							channel2.addProperty("channel", 2);
							channel2.addProperty("switch", switchTwo);
							jsonArray.add(channel2);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_03: {
							ESSwitchFourES0020_03 d = (ESSwitchFourES0020_03) device;
							boolean switchThree = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							JsonArray jsonArray = new JsonArray();
							JsonObject channel3 = new JsonObject();
							channel3.addProperty("channel", 3);
							channel3.addProperty("switch", switchThree);
							jsonArray.add(channel3);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0020_04: {
							ESSwitchFourES0020_04 d = (ESSwitchFourES0020_04) device;
							boolean swtichFour = d.isStatus();
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();  
							JsonArray jsonArray = new JsonArray();
							JsonObject channel4 = new JsonObject();
							channel4.addProperty("channel", 4);
							channel4.addProperty("switch", swtichFour);
							jsonArray.add(channel4);
							jsonObject2.add("channel_collection", jsonArray);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0024: {
							ESNetworkedThermostatES0024 d = (ESNetworkedThermostatES0024) device;
							boolean powerOn = d.isPowerOn();
							int  temperature=d.getTemperature();
							String  windSpeed=d.getWindSpeedToSend();
							boolean lowTempEnable=d.isLowTempEnable();
							int lowTemp=d.getLowTemp();
							String mode=d.getModeToSend();
							boolean lockPane=d.isLockPane();
							String report=d.getReportToSend();
							
							
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							
							JsonObject function = new JsonObject();
							function.addProperty("power_on", powerOn);
							function.addProperty("temperature", temperature);
							function.addProperty("rev_speed", windSpeed);
							function.addProperty("low_temp_enable", lowTempEnable);
							function.addProperty("low_temp", lowTemp);
							function.addProperty("model", mode);
							function.addProperty("lock_panel", lockPane);
							function.addProperty("report", report);
							
							jsonObject.add("function", function);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
						break;
						case ES_0025: {
							ESWindowPusherES0025 esWindowPusherES0025 = (ESWindowPusherES0025) device;
							boolean control = esWindowPusherES0025.isControl();
							int feed = esWindowPusherES0025.getFeed();
							int percent = esWindowPusherES0025.getPercent();
							String req="";
							if (control) {// 状态控制
									switch (feed) {
										case 0:
											req="5500000302A93D";//关
											break;
										case 1:
											req="5500000301E93C";//开
											break;
										case 2:
											req="550000030368FD";//停
											break;
										default:
											break;
									}//            起始码 设备地址 功能 数据地址 数据内容 CRC16
								} else {// 百分比控制 55 00 00 03 04 1E 7ED6  
									req="5500000304"+fixed(Integer.toHexString(percent),2);
									byte[] bytes = CRC16M.getSendBuf(req);//添加校验码
									req=CRC16M.getBufHexStr(bytes);
								}
							JsonObject jsonObject = new JsonObject();
							jsonObject.addProperty("deviceKey", number);
							jsonObject.addProperty("cmd", "write");
							JsonObject jsonObject2 = new JsonObject();
							jsonObject2.addProperty("messageString", req);
							jsonObject.add("function", jsonObject2);
							message.setPayload(jsonObject.toString().getBytes());
							client.publish(client.getTopic(topic.toString()), message);
						}
							break;
						case ES_0026: {
							ESInfraredTransponderES0026 es = (ESInfraredTransponderES0026) device;
							int instruct = es.getInstruct();
							boolean learn = es.getLearn();
							if (learn) {
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("deviceKey", number);
								jsonObject.addProperty("cmd", "write");
								JsonObject jsonObject2 = new JsonObject();
								jsonObject2.addProperty("infrared_learn_code", ESInfraredTransponderES0026.LEARN_CODE);
								jsonObject2.addProperty("infrared_learn_key", instruct);
								jsonObject.add("function", jsonObject2);
								message.setPayload(jsonObject.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							} else {
								JsonObject jsonObject1 = new JsonObject();
								jsonObject1.addProperty("deviceKey", number);
								jsonObject1.addProperty("cmd", "write");
								JsonObject jsonObject22 = new JsonObject();
								jsonObject22.addProperty("infrared_send_code", ESInfraredTransponderES0026.LEARN_CODE);
								jsonObject22.addProperty("infrared_send_key", instruct);
								jsonObject1.add("function", jsonObject22);
								message.setPayload(jsonObject1.toString().getBytes());
								client.publish(client.getTopic(topic.toString()), message);
							}
						}
							break;
						default:
							break;
					}
				}
				break;

			default:
				break;
		}
	}
	/**
	 * 返回指定长度字符串（前面补len-str.length()个0）
	 * @param str
	 * @param len
	 * @return
	 */
	private String fixed(String str,int len){
		if(null==str || str.isEmpty()){
			str="";
		}
		if(str.length()!=len)
			for(int i=0;i<len-str.length();i++){
				str="0"+str;
		}
		return str;
	}
}

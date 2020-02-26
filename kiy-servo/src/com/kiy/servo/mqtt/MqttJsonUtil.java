package com.kiy.servo.mqtt;

import io.netty.util.internal.ConcurrentSet;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kiy.common.Device;
import com.kiy.common.Notice;
import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.Types.Model;
import com.kiy.common.Types.Origin;
import com.kiy.common.Types.Status;
import com.kiy.common.devices.ESAirES0014;
import com.kiy.common.devices.ESCurtainES0013;
import com.kiy.common.devices.ESCurtainES0013_01;
import com.kiy.common.devices.ESDimmingES0011;
import com.kiy.common.devices.ESDimmingES0019_01;
import com.kiy.common.devices.ESDimmingES0019_02;
import com.kiy.common.devices.ESDryContactES0008;
import com.kiy.common.devices.ESDryContactES0008_01;
import com.kiy.common.devices.ESDryContactES0008_02;
import com.kiy.common.devices.ESDryContactES0008_03;
import com.kiy.common.devices.ESDryContactES0008_04;
import com.kiy.common.devices.ESGateWayES0001;
import com.kiy.common.devices.ESGateWayES0002;
import com.kiy.common.devices.ESHumanInfraredES0023;
import com.kiy.common.devices.ESHumitureSensorES0009;
import com.kiy.common.devices.ESInfraredTransponderES0026;
import com.kiy.common.devices.ESNetworkedThermostatES0024;
import com.kiy.common.devices.ESPLC2RS485FYSerialRadioFrequencyES0027;
import com.kiy.common.devices.ESPLC2RS485HumitureSensorES0007;
import com.kiy.common.devices.ESPLC2RS485IluminaceSensorES0018;
import com.kiy.common.devices.ESPLC2RS485PMES0017;
import com.kiy.common.devices.ESSingleFireES0022_01;
import com.kiy.common.devices.ESSingleFireES0022_02;
import com.kiy.common.devices.ESSingleFireES0022_03;
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
import com.kiy.protocol.Messages.Message;
import com.kiy.protocol.Messages.ReadDeviceStatus.Builder;
import com.kiy.protocol.Messages.WriteDeviceStatus;
import com.kiy.protocol.Units.MDeviceStatus;
import com.kiy.servo.Executor;
import com.kiy.servo.cloud.Cloud;
import com.kiy.servo.data.Data;
import com.kiy.servo.job.Linkager;
import com.kiy.servo.master.Master;
import com.kiy.servo.master.MatcherLogon;

public class MqttJsonUtil {
	public static synchronized final void send(String msg, String gatewayKey)
			throws MqttPersistenceException, MqttException {
		JsonObject jsonObject = new JsonParser().parse(msg).getAsJsonObject();
		String deviceKey; // 设备id,没有则判断该消息没用,遗弃
		String errorCodeString = null; // 错误码，有的消息不会回复错误码
		JsonObject function; // 必有
		JsonElement deviceKeyJsonElement = jsonObject.get("deviceKey");
		JsonElement errorCodeJsonElement = jsonObject.get("errorCode");
		function = jsonObject.getAsJsonObject("function");
		if (deviceKeyJsonElement == null)
			return;
		deviceKey = deviceKeyJsonElement.getAsString();

		if (errorCodeJsonElement != null) {
			errorCodeString = errorCodeJsonElement.getAsString();
		}

		// 通过deviceKey 取得缓存层相应设备
		Servo servo = Data.getServo();
		ConcurrentSet<Device> devices = servo.getDeviceByNumber(deviceKey);
		for (Device device : devices) {
			Model model = device.getModel();
			device.setTickStatus(System.currentTimeMillis());
			System.err.println(function.toString());
			// 将设备消息解析后保存到相应设备缓存中
			switch (model) {
			case ES_0001: {// EastSoft三相网关
				Date date = getMqttDate(function);
				ESGateWayES0001 esGateWayES0001 = (ESGateWayES0001) device;
				esGateWayES0001.setDatetime(date);
			}
				break;
			case ES_0002: {// EastSoft路由器网关
				Date date = getMqttDate(function);
				ESGateWayES0002 esGateWayES0002 = (ESGateWayES0002) device;
				esGateWayES0002.setDatetime(date);
			}
				break;
	
			case ES_0022: {// 单火线(四路开关控制一致)
				String controllerAddr = function.get("controller_addr").getAsString();
				ESSwitchFourES0003 esSwitchFourES0003 = (ESSwitchFourES0003) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSwitchFourES0003.setSwitchOne(function.get(
								"switch_ch1").getAsBoolean());
						esSwitchFourES0003.setSwitchTwo(function.get(
								"switch_ch2").getAsBoolean());
						esSwitchFourES0003.setSwitchThree(function.get(
								"switch_ch3").getAsBoolean());
						esSwitchFourES0003.setSwitchFour(function.get(
								"switch_ch4").getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setSwitchOne(function.get("switch_ch1")
							.getAsBoolean());
					esSwitchFourES0003.setSwitchTwo(function.get("switch_ch2")
							.getAsBoolean());
					esSwitchFourES0003.setSwitchThree(function
							.get("switch_ch3").getAsBoolean());
					esSwitchFourES0003.setSwitchFour(function.get("switch_ch4")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0003: {

				// 四路开关执行器
				String controllerAddr = function.get("controller_addr").getAsString();
				ESSwitchFourES0003 esSwitchFourES0003 = (ESSwitchFourES0003) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSwitchFourES0003.setSwitchOne(function.get(
								"switch_ch1").getAsBoolean());
						esSwitchFourES0003.setSwitchTwo(function.get(
								"switch_ch2").getAsBoolean());
						esSwitchFourES0003.setSwitchThree(function.get(
								"switch_ch3").getAsBoolean());
						esSwitchFourES0003.setSwitchFour(function.get(
								"switch_ch4").getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setSwitchOne(function.get("switch_ch1")
							.getAsBoolean());
					esSwitchFourES0003.setSwitchTwo(function.get("switch_ch2")
							.getAsBoolean());
					esSwitchFourES0003.setSwitchThree(function
							.get("switch_ch3").getAsBoolean());
					esSwitchFourES0003.setSwitchFour(function.get("switch_ch4")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0022_01:
			{
				// 单火线开关1
				String controllerAddr = function.get("controller_addr").getAsString();
				ESSingleFireES0022_01 esSingleFireES0022_01 = (ESSingleFireES0022_01) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSingleFireES0022_01.setStatus(function.get("switch_ch1")
								.getAsBoolean());
						esSingleFireES0022_01.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSingleFireES0022_01.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSingleFireES0022_01.setStatus(function.get("switch_ch1")
							.getAsBoolean());
					esSingleFireES0022_01.setStatus(Status.NONE);
					sendSetStatus(esSingleFireES0022_01, null);
				}
			}
				break;
			case ES_0003_01: {

				// 四路开关执行器开关1
				String controllerAddr = function.get("controller_addr").getAsString();
				ESSwitchFourES0003_01 esSwitchFourES0003 = (ESSwitchFourES0003_01) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSwitchFourES0003.setStatus(function.get("switch_ch1")
								.getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setStatus(function.get("switch_ch1")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0022_02:{

				// 单火线2
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSingleFireES0022_02 esSingleFireES0022_02 = (ESSingleFireES0022_02) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSingleFireES0022_02.setStatus(function.get("switch_ch2")
								.getAsBoolean());
						esSingleFireES0022_02.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSingleFireES0022_02.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSingleFireES0022_02.setStatus(function.get("switch_ch2")
							.getAsBoolean());
					esSingleFireES0022_02.setStatus(Status.NONE);
					sendSetStatus(esSingleFireES0022_02, null);
				}
			}
				break;
			case ES_0003_02: {

				// 四路开关执行器开关2
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0003_02 esSwitchFourES0003 = (ESSwitchFourES0003_02) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						//报错
						esSwitchFourES0003.setStatus(function.get("switch_ch2")
								.getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setStatus(function.get("switch_ch2")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0022_03:{

				// 单火线3
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSingleFireES0022_03 esSingleFireES0022_03 = (ESSingleFireES0022_03) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSingleFireES0022_03.setStatus(function.get("switch_ch3")
								.getAsBoolean());
						esSingleFireES0022_03.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSingleFireES0022_03.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSingleFireES0022_03.setStatus(function.get("switch_ch3")
							.getAsBoolean());
					esSingleFireES0022_03.setStatus(Status.NONE);
					sendSetStatus(esSingleFireES0022_03, null);
				}
			}
				break;
			case ES_0003_03: {

				// 四路开关执行器开关3
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0003_03 esSwitchFourES0003 = (ESSwitchFourES0003_03) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSwitchFourES0003.setStatus(function.get("switch_ch3")
								.getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setStatus(function.get("switch_ch3")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0003_04: {

				// 四路开关执行器开关4
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0003_04 esSwitchFourES0003 = (ESSwitchFourES0003_04) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						//错误
						
						esSwitchFourES0003.setStatus(function.get("switch_ch4")
								.getAsBoolean());
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0003.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchFourES0003.setStatus(function.get("switch_ch4")
							.getAsBoolean());
					esSwitchFourES0003.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0003, null);
				}
			}
				break;
			case ES_0004: { // EastSoft开关控制模块
				ESSwitchES0004 esSwitchES0004 = (ESSwitchES0004) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				if (function.get("controller_addr")!=null) {
					String controller_addr = function.get("controller_addr")
							.getAsString();
					if (gatewayKey.equals(controller_addr)) { // 网关请求
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							esSwitchES0004.setFeed(switch_ch1.getAsBoolean());
							esSwitchES0004.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							esSwitchES0004.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						}
				}
				} else { // 主动上传
					esSwitchES0004.setFeed(switch_ch1.getAsBoolean());
					esSwitchES0004.setStatus(Status.NONE);
					sendSetStatus(esSwitchES0004, null);
				}
			}
				break;
			case ES_0005: { // EastSoft开关控制模块(两路)
				ESSwitchTwoES0005 esSwitchTwoES0005 = (ESSwitchTwoES0005) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				JsonElement switch_ch2 = function.get("switch_ch2");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						esSwitchTwoES0005.setSwitchOne(switch_ch1
								.getAsBoolean());
						esSwitchTwoES0005.setSwitchTwo(switch_ch2
								.getAsBoolean());
						esSwitchTwoES0005.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchTwoES0005.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					esSwitchTwoES0005.setSwitchOne(switch_ch1.getAsBoolean());
					esSwitchTwoES0005.setSwitchTwo(switch_ch2.getAsBoolean());
					esSwitchTwoES0005.setStatus(Status.NONE);
					sendSetStatus(esSwitchTwoES0005, null);
				}
			}
				break;
			case ES_0005_01: { // EastSoft开关控制模块(两路)第一路
				ESSwitchTwoES0005_01 d = (ESSwitchTwoES0005_01) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						d.setSwitchOne(switch_ch1
							.getAsBoolean());
						d.setStatus(Tool
							.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						d.setStatus(Tool
							.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					d.setSwitchOne(switch_ch1.getAsBoolean());
					d.setStatus(Status.NONE);
					sendSetStatus(d, null);
				}
			}
			break;
			case ES_0005_02: { // EastSoft开关控制模块(两路)第二路
				ESSwitchTwoES0005_02 d = (ESSwitchTwoES0005_02) device;
				JsonElement switch_ch2 = function.get("switch_ch2");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						d.setSwitchTwo(switch_ch2
							.getAsBoolean());
						d.setStatus(Tool
							.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						d.setStatus(Tool
							.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					d.setSwitchTwo(switch_ch2.getAsBoolean());
					d.setStatus(Status.NONE);
					sendSetStatus(d, null);
				}
			}
			break;
			case ES_0005_03: { // EastSoft开关控制模块(两路)第三路
				ESSwitchTwoES0005_02 d = (ESSwitchTwoES0005_02) device;
				JsonElement switch_ch3 = function.get("switch_ch3");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						d.setSwitchTwo(switch_ch3
								.getAsBoolean());
						d.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						d.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					d.setSwitchTwo(switch_ch3.getAsBoolean());
					d.setStatus(Status.NONE);
					sendSetStatus(d, null);
				}
			}
			break;
			case ES_0006: { // ES_0006 2按键开关控制模块
				ESSwitchTouchTwoES0006 eSwitchTouchTwoES0006 = (ESSwitchTouchTwoES0006) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				JsonElement switch_ch2 = function.get("switch_ch2");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						eSwitchTouchTwoES0006.setSwitchOne(switch_ch1
								.getAsBoolean());
						eSwitchTouchTwoES0006.setSwitchTwo(switch_ch2
								.getAsBoolean());
						eSwitchTouchTwoES0006.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						eSwitchTouchTwoES0006.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					eSwitchTouchTwoES0006.setSwitchOne(switch_ch1
							.getAsBoolean());
					eSwitchTouchTwoES0006.setSwitchTwo(switch_ch2
							.getAsBoolean());
					eSwitchTouchTwoES0006.setStatus(Status.NONE);
					sendSetStatus(eSwitchTouchTwoES0006, null);
				}
			}
				break;
			case ES_0006_01: { // ES_0006_01 2按键开关控制模块
				ESSwitchTouchTwoES0006_01 eSwitchTouchTwoES0006 = (ESSwitchTouchTwoES0006_01) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				String controller_addr = function.get("controller_addr")
						.getAsString();
					if (gatewayKey.equals(controller_addr)) { // 网关请求
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							eSwitchTouchTwoES0006.setSwitchOne(switch_ch1
									.getAsBoolean());
							eSwitchTouchTwoES0006.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							eSwitchTouchTwoES0006.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						}
					}
				 else { // 主动上传
						eSwitchTouchTwoES0006.setSwitchOne(switch_ch1
								.getAsBoolean());
						eSwitchTouchTwoES0006.setStatus(Status.NONE);
						sendSetStatus(eSwitchTouchTwoES0006, null);
					}
			}
				break;
			case ES_0006_02: { // ES_0006_02 2按键开关控制模块
				ESSwitchTouchTwoES0006_02 eSwitchTouchTwoES0006 = (ESSwitchTouchTwoES0006_02) device;
				JsonElement switch_ch2 = function.get("switch_ch2");
				String controller_addr = function.get("controller_addr")
						.getAsString();
				if (gatewayKey.equals(controller_addr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						eSwitchTouchTwoES0006.setSwitchOne(switch_ch2
								.getAsBoolean());
						eSwitchTouchTwoES0006.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						eSwitchTouchTwoES0006.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					eSwitchTouchTwoES0006.setSwitchOne(switch_ch2
							.getAsBoolean());
					eSwitchTouchTwoES0006.setStatus(Status.NONE);
					sendSetStatus(eSwitchTouchTwoES0006, null);
				}
			}
				break;
			case ES_0007: { // PLC转RS485 温湿度传感器
				ESPLC2RS485HumitureSensorES0007 dEs0007 = (ESPLC2RS485HumitureSensorES0007) device;
				JsonElement messageString = function.get("messageString");
				JsonElement controller_addr = function.get("controller_addr");
				if (controller_addr != null) { // PLC确认报文回复 只保存设备状态数据
					int errorCode = Integer.parseInt(errorCodeString);
					dEs0007.setStatus(Tool.getStatusForErrorCode(errorCode));
				} else { // 设备消息回复
					String message = messageString.getAsString();
					if (message.length() != 18)
						return;
					String str = message.substring(6, 10);
					String str1 = message.substring(10, 14);
					dEs0007.setTemperature(Integer.valueOf(str, 16) / 10);
					dEs0007.setMoisture(Integer.valueOf(str1, 16) / 10);
					sendGetStatus(dEs0007);
				}
			}
				break;
			case ES_0008: { // 干接点
				ESDryContactES0008 dEs0008 = (ESDryContactES0008) device;

				JsonObject alarm = jsonObject.getAsJsonObject("alarm");
				int buttonId = alarm.get("button_id").getAsInt();
				int state = alarm.get("state").getAsInt();

				switch (buttonId) {
				case 1:
					dEs0008.setLineOne(state);
					break;
				case 2:
					dEs0008.setLineTwo(state);
					break;
				case 3:
					dEs0008.setLineThree(state);
					break;
				case 4:
					dEs0008.setLineFour(state);
					break;
				default:
					break;
				}
				sendGetStatus(dEs0008);

				// // 场景模式（暂定）
				// Device adapterDevice = servo.getDeviceByNumber("0000LKHG");
				// ESGateWayES0002 gateWay = (ESGateWayES0002) adapterDevice;
				//
				// Device fourSwitchOne = servo.getDeviceByNumber("0000ANH5");
				// ESSwitchFourES0003 switchOne = (ESSwitchFourES0003)
				// fourSwitchOne;
				//
				// Device fourSwitchTwo = servo.getDeviceByNumber("000045CA");
				// ESSwitchFourES0003 switchTwo = (ESSwitchFourES0003)
				// fourSwitchTwo;
				//
				// Device fourSwitchThree = servo.getDeviceByNumber("0000I4GD");
				// ESSwitchFourES0003 switchThree = (ESSwitchFourES0003)
				// fourSwitchThree;
				//
				// Device fourSwitchFour = servo.getDeviceByNumber("000059GU");
				// ESSwitchFourES0003 switchFour = (ESSwitchFourES0003)
				// fourSwitchFour;
				//
				// Device fourSwitchFive = servo.getDeviceByNumber("0000EF1Z");
				// ESSwitchFourES0003 switchFive = (ESSwitchFourES0003)
				// fourSwitchFive;
				//
				// Device fourSwitchSix = servo.getDeviceByNumber("0000I4WW");
				// ESSwitchFourES0003 switchSix = (ESSwitchFourES0003)
				// fourSwitchSix;
				//
				// Device fourSwitchSeven = servo.getDeviceByNumber("0000JNYT");
				// ESSwitchFourES0003 switchSeven = (ESSwitchFourES0003)
				// fourSwitchSeven;
				//
				// switchOne.setSwitchOne(false);
				// switchOne.setSwitchTwo(false);
				// switchOne.setSwitchThree(false);
				// switchOne.setSwitchFour(false);
				//
				// switchTwo.setSwitchOne(false);
				// switchTwo.setSwitchTwo(false);
				// switchTwo.setSwitchThree(false);
				// switchTwo.setSwitchFour(false);
				//
				// switchThree.setSwitchOne(false);
				// switchThree.setSwitchTwo(false);
				// switchThree.setSwitchThree(false);
				// switchThree.setSwitchFour(false);
				//
				// switchFour.setSwitchOne(false);
				// switchFour.setSwitchTwo(false);
				// switchFour.setSwitchThree(false);
				// switchFour.setSwitchFour(false);
				//
				// switchFive.setSwitchOne(false);
				// switchFive.setSwitchTwo(false);
				// switchFive.setSwitchThree(false);
				// switchFive.setSwitchFour(false);
				//
				// switchSix.setSwitchOne(false);
				// switchSix.setSwitchTwo(false);
				// switchSix.setSwitchThree(false);
				// switchSix.setSwitchFour(false);
				//
				// switchSeven.setSwitchOne(false);
				// switchSeven.setSwitchTwo(false);
				// switchSeven.setSwitchThree(false);
				// switchSeven.setSwitchFour(false);
				//
				// int i1 = Integer.parseInt(dEs0008.getFeature(0).getText());
				// int i2 = Integer.parseInt(dEs0008.getFeature(1).getText());
				// int i3 = Integer.parseInt(dEs0008.getFeature(2).getText());
				// int i4 = Integer.parseInt(dEs0008.getFeature(3).getText());
				// // 判断是不是只有一路报警
				// if ((i1 + i2 + i3 + i4) == 13 && state == 1 &&
				// dEs0008.getName().equals("场景一")) {
				// switch (buttonId) {
				// case 1:// (默认模式)关闭所有灯，衣架伸出，大厅空调关闭，窗帘拉开，所有警报器关闭，排气扇关闭，撤防
				// switchFour.setSwitchFour(true);// 窗帘
				// // 打开电视机
				// break;
				// case 2:// (影音模式)电视机打开，大厅灯关闭，大厅主灯打开，大厅空调打开，窗帘关闭，其他设备不变
				// switchTwo.setSwitchOne(true);// 大厅主灯
				// switchFour.setSwitchTwo(true);// 大厅空调
				// break;
				// case 3://
				// (离家模式)关闭所有灯，关闭空调，关闭排气扇，关闭入户门和车库门，拉上大厅窗帘，所有警报器和传感器进行联动，布防
				// // 关闭电视机
				// break;
				// case 4:// (被入侵模式) 切换到被入侵模式时，推送信息，打开警报器，打开入户警示灯，其他不变
				// switchTwo.setSwitchOne(true);// 大厅主灯
				// switchFour.setSwitchTwo(true);// 大厅空调
				// switchSix.setSwitchOne(true);// 排气扇
				// switchFour.setSwitchFour(true);// 窗帘
				// // 打开电视机
				// break;
				// default:
				// break;
				// }
				// new MQTTDriver(gateWay).setStatus(switchOne);
				// new MQTTDriver(gateWay).setStatus(switchTwo);
				// new MQTTDriver(gateWay).setStatus(switchThree);
				// new MQTTDriver(gateWay).setStatus(switchFour);
				// new MQTTDriver(gateWay).setStatus(switchFive);
				// new MQTTDriver(gateWay).setStatus(switchSix);
				// new MQTTDriver(gateWay).setStatus(switchSeven);
				// } else if ((i1 + i2 + i3 + i4) == 13 && state == 1 &&
				// dEs0008.getName().equals("场景二")) {
				// switch (buttonId) {
				// case 1:// (下雨模式)切换到下雨模式时，回收衣架，其他不变
				// switchSix.setSwitchTwo(true);// 衣架
				// break;
				// case 2:// (晴天模式)切换到晴天模式时，伸出衣架，其他不变
				// // switchSix.setSwitchTwo(false);// 衣架
				// break;
				// case 3:// (火警模式)切换到火警模式时，推送信息，打开消阀灯，打开警报器，其他不变
				// switchFive.setSwitchTwo(true);// 消阀灯
				// switchFive.setSwitchThree(true);// 警报器
				// break;
				// case 4:// (被入侵模式) 切换到被入侵模式时，推送信息，打开警报器，打开入户警示灯，其他不变
				// switchFive.setSwitchThree(true);// 警报器
				// switchSix.setSwitchFour(true);// 入户警示灯
				// // 打开电视机
				// break;
				// default:
				// break;
				// }
				// new MQTTDriver(gateWay).setStatus(switchOne);
				// new MQTTDriver(gateWay).setStatus(switchTwo);
				// new MQTTDriver(gateWay).setStatus(switchThree);
				// new MQTTDriver(gateWay).setStatus(switchFour);
				// new MQTTDriver(gateWay).setStatus(switchFive);
				// new MQTTDriver(gateWay).setStatus(switchSix);
				// new MQTTDriver(gateWay).setStatus(switchSeven);
				// }

			}
				break;

			case ES_0008_01: { // 干接点接口1
				ESDryContactES0008_01 dEs0008 = (ESDryContactES0008_01) device;

				JsonObject alarm = jsonObject.getAsJsonObject("alarm");
				int buttonId = alarm.get("button_id").getAsInt();
				int state = alarm.get("state").getAsInt();
				switch (buttonId) {
				case 1:
					dEs0008.setLineOne(state);
					break;
				default:
					break;
				}
				sendGetStatus(dEs0008);
			}
				break;
			case ES_0008_02: { // 干接点接口2
				ESDryContactES0008_02 dEs0008 = (ESDryContactES0008_02) device;
				//{-----------------------可以删除-----------------------
				//门磁2打开联动厨房所有灯亮（暂时）eec14020-31e1-456c-a110-1c680dd63462
				 Device adapterDevice = servo.getDevice("8e988f81-c557-4926-b6ac-b9be38442ab5");
				 ESGateWayES0002 gateWay = (ESGateWayES0002) adapterDevice;
				
				 //筒灯一
				 Device deviceOne =  servo.getDevice("711f7ac5-ccea-4c64-99ff-d1b27a8e3cbd");
				 ESSwitchTouchTwoES0006_01 switchOne = (ESSwitchTouchTwoES0006_01)deviceOne;
				
				 //筒灯二
				 Device deviceTwo =  servo.getDevice("91e7db92-ecc6-4c77-b85f-61b0d65049c5");
				 ESSwitchTouchTwoES0006_02 switchTwo = (ESSwitchTouchTwoES0006_02)deviceTwo;
				 //窗帘控制器
				 Device deviceThree =  servo.getDevice("f94dd4f0-09b8-4adb-81ce-7306c6d838d2");
				 ESCurtainES0013 curtainThree = (ESCurtainES0013)deviceThree;
				
				//-----------------------可以删除-----------------------}
				JsonObject alarm = jsonObject.getAsJsonObject("alarm");
				int buttonId = alarm.get("button_id").getAsInt();
				int state = alarm.get("state").getAsInt();
				//{-----------------------可以删除-----------------------
				if (state==0) {//打开
					switchOne.setSwitchOne(true);
					switchTwo.setSwitchOne(true);
					curtainThree.setControl(true);
					curtainThree.setFeed(1);
					new MQTTDriver(gateWay).setStatus(switchOne);
					new MQTTDriver(gateWay).setStatus(switchTwo);
					new MQTTDriver(gateWay).setStatus(curtainThree);
				}else if(state==1){//关闭
					switchOne.setSwitchOne(false);
					switchTwo.setSwitchOne(false);
					curtainThree.setControl(true);
					curtainThree.setFeed(0);
					new MQTTDriver(gateWay).setStatus(switchOne);
					new MQTTDriver(gateWay).setStatus(switchTwo);
					new MQTTDriver(gateWay).setStatus(curtainThree);
				}
				//-----------------------可以删除-----------------------}
				switch (buttonId) {
				case 2:
					dEs0008.setLineTwo(state);
					break;
				default:
					break;
				}
				sendGetStatus(dEs0008);
			}
				break;
			case ES_0008_03: { // 干接点接口2
				ESDryContactES0008_03 dEs0008 = (ESDryContactES0008_03) device;

				JsonObject alarm = jsonObject.getAsJsonObject("alarm");
				int buttonId = alarm.get("button_id").getAsInt();
				int state = alarm.get("state").getAsInt();
				System.out.println("ES_0008_03:" + state);
				switch (buttonId) {
				case 3:
					dEs0008.setLineThree(state);
					break;
				default:
					break;
				}
				sendGetStatus(dEs0008);
			}
				break;
			case ES_0008_04: { // 干接点
				ESDryContactES0008_04 dEs0008 = (ESDryContactES0008_04) device;

				JsonObject alarm = jsonObject.getAsJsonObject("alarm");
				int buttonId = alarm.get("button_id").getAsInt();
				int state = alarm.get("state").getAsInt();
				System.out.println("ES_0008_04:" + state);
				switch (buttonId) {
				case 4:
					dEs0008.setLineFour(state);
					break;
				default:
					break;
				}
				sendGetStatus(dEs0008);
			}
				break;
			case ES_0009: { // 温湿度传感器
				ESHumitureSensorES0009 dEs0009 = (ESHumitureSensorES0009) device;
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement temperature = function.get("temperature");
				JsonElement humidness = function.get("humidness");
				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据

					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						if (temperature!=null) {
							dEs0009.setTemperature(temperature.getAsFloat());
						}if (humidness!=null) {
							dEs0009.setMoisture(humidness.getAsFloat());
						}
					}
				} else { // 设备消息回复
					dEs0009.setTemperature(temperature.getAsFloat());
					dEs0009.setMoisture(humidness.getAsFloat());
					sendGetStatus(dEs0009);
				}
			}
				break;
			case ES_0010: { // EastSoft大功率计量开关
				ESSwitchES0010 esSwitchES0010 = (ESSwitchES0010) device;
				JsonElement switch_ch1 = function.get("switch_ch1");
				JsonElement controller_addr = function.get("controller_addr");
				JsonElement current = function.get("current");
				JsonElement electricity = function.get("electricity");
				JsonElement power = function.get("power");
				JsonElement voltage = function.get("voltage");

				if (controller_addr != null) { // PLC确认报文回复 只保存设备状态数据
					if (gatewayKey.equals(controller_addr.getAsString())) { // 网关请求
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							if (switch_ch1 != null) { // 开关状态返回
								esSwitchES0010.setFeed(switch_ch1
										.getAsBoolean());
							} else { // 用电状态返回
								if (current != null)
									esSwitchES0010.setCurrent(current
											.getAsDouble());
								if (electricity != null)
									esSwitchES0010.setElectricity(electricity
											.getAsDouble());
								if (current != null)
									esSwitchES0010.setVoltage(voltage
											.getAsDouble());
								if (power != null)
									esSwitchES0010.setPowers(power
											.getAsDouble());
							}
							esSwitchES0010.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							esSwitchES0010.setStatus(Tool
									.getStatusForErrorCode(errorCode));
							sendGetStatus(esSwitchES0010);
						}
					} else { // 其他设备控制主动上传
						if (switch_ch1 != null) { 
						esSwitchES0010.setFeed(switch_ch1.getAsBoolean());
						esSwitchES0010.setStatus(Status.NONE);
						sendSetStatus(esSwitchES0010, null);
						}
					}
				} else { // 设备主动上传用电信息
					if (current != null)
						esSwitchES0010.setCurrent(current.getAsDouble());
					if (electricity != null)
						esSwitchES0010
								.setElectricity(electricity.getAsDouble());
					if (current != null)
						esSwitchES0010.setVoltage(voltage.getAsDouble());
					if (power != null)
						esSwitchES0010.setPowers(power.getAsDouble());
					sendGetStatus(esSwitchES0010);
				}

			}
				break;
			case ES_0011: { // EastSoft调光控制模块
				ESDimmingES0011 esDimmingES0011 = (ESDimmingES0011) device;
				JsonElement switchCh1 = function.get("switch_ch1");
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement diming = function.get("diming");

				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					if (gatewayKey.equals(controllerAddr.getAsString())) { // 网关请求
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							if (switchCh1 != null) { // 开关状态返回
								esDimmingES0011.setFeed(switchCh1.getAsBoolean());
							} if (diming != null) {
								esDimmingES0011.setLuminosity(diming.getAsInt());
							}
							esDimmingES0011.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							esDimmingES0011.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						}
					} else { // 其他设备控制主动上传
						esDimmingES0011.setFeed(switchCh1.getAsBoolean());
						esDimmingES0011.setStatus(Status.NONE);
						sendSetStatus(esDimmingES0011, null);
					}
				} else { // 设备主动上传用电信息
					if (diming != null)
						esDimmingES0011.setLuminosity(diming.getAsInt());
					sendGetStatus(esDimmingES0011);
				}

			}
				break;
			case ES_0012: { // EastSoft调色控制模块
				ESToningES0012 esToningES0012 = (ESToningES0012) device;
				JsonElement switchCh1 = function.get("switch_ch1");
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement diming = function.get("diming");
				JsonElement color = function.get("color");

				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					if (gatewayKey.equals(controllerAddr.getAsString())) { // 网关请求
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							if (switchCh1 != null) { 
								esToningES0012.setFeed(switchCh1.getAsBoolean());
							}if (diming != null) {
								esToningES0012.setLuminosity(diming.getAsInt());
							}if (color != null) {
								esToningES0012.setColor(Tool.RGB2int(color.getAsString()));
							}
							esToningES0012.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							esToningES0012.setStatus(Tool
									.getStatusForErrorCode(errorCode));
						}
					} else { // 其他设备控制主动上传
						esToningES0012.setFeed(switchCh1.getAsBoolean());
						esToningES0012.setStatus(Status.NONE);
						sendSetStatus(esToningES0012, null);
					}
				} else {
					if (diming != null) {
						esToningES0012.setLuminosity(diming.getAsInt());
						sendGetStatus(esToningES0012);
					} else if (color != null) {
						esToningES0012.setColor(color.getAsInt());
						sendGetStatus(esToningES0012);
					}

				}
			}
				break;
			case ES_0013: {// EastSoft窗帘控制模块
				ESCurtainES0013 esCurtainES0013 = (ESCurtainES0013) device;
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement curtain_percent = function.get("curtain_percent");
				JsonElement current_percent = function.get("current_percent");
				if (controllerAddr != null) {
					if (gatewayKey.equals(controllerAddr.getAsString())) { // 网关请求
						if (errorCodeString != null) {
							int errorCode = Integer.parseInt(errorCodeString);
							if (errorCode == 0) { // 请求成功
								if (current_percent != null) {
									int currentPercent = current_percent
											.getAsInt();
									esCurtainES0013.setPercent(currentPercent);
								} else {
									if (curtain_percent != null) {
										int curtainPercent = curtain_percent
												.getAsInt();
										esCurtainES0013
												.setPercent(curtainPercent);
									}
								}
								esCurtainES0013.setStatus(Tool
										.getStatusForErrorCode(errorCode));
							} else { // 请求失败
								esCurtainES0013.setStatus(Tool
										.getStatusForErrorCode(errorCode));
							}
						} else {
							if (current_percent != null) {
								int currentPercent = current_percent.getAsInt();
								esCurtainES0013.setPercent(currentPercent);
							} else {
								if (curtain_percent != null) {
									int curtainPercent = curtain_percent
											.getAsInt();
									esCurtainES0013.setPercent(curtainPercent);
								}
							}
						}
					} else {
						// 其他设备或人为控制主动上传
						if (current_percent != null) {
							int currentPercent = current_percent.getAsInt();
							esCurtainES0013.setPercent(currentPercent);
						} else {
							if (curtain_percent != null) {
								int curtainPercent = curtain_percent.getAsInt();
								esCurtainES0013.setPercent(curtainPercent);
							}
						}
						esCurtainES0013.setStatus(Status.NONE);
					}

				} else {
					// 暂无处理
				}

			}
				break;
			case ES_0013_01: {// EastSoft窗帘控制模块
				ESCurtainES0013_01 esCurtainES0013 = (ESCurtainES0013_01) device;
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement curtain_percent = function.get("curtain_percent");
				JsonElement current_percent = function.get("current_percent");
				if (controllerAddr != null) {
					if (gatewayKey.equals(controllerAddr.getAsString())) { // 网关请求
						if (errorCodeString != null) {
							int errorCode = Integer.parseInt(errorCodeString);
							if (errorCode == 0) { // 请求成功
								if (current_percent != null) {
									int currentPercent = current_percent
											.getAsInt();
									esCurtainES0013.setPercent(100-currentPercent);
								} else if (curtain_percent != null) {
										int curtainPercent = curtain_percent
												.getAsInt();
										esCurtainES0013
										.setPercent(100-curtainPercent);
									}
								esCurtainES0013.setStatus(Tool
										.getStatusForErrorCode(errorCode));
							} else { // 请求失败
								esCurtainES0013.setStatus(Tool
										.getStatusForErrorCode(errorCode));
							}
						} else {
							if (current_percent != null) {
								int currentPercent = current_percent.getAsInt();
								esCurtainES0013.setPercent(100-currentPercent);
							} else {
								if (curtain_percent != null) {
									int curtainPercent = curtain_percent
											.getAsInt();
									esCurtainES0013.setPercent(100-curtainPercent);
								}
							}
						}
					} else {
						// 其他设备或人为控制主动上传
						if (current_percent != null) {
							int currentPercent = current_percent.getAsInt();
							esCurtainES0013.setPercent(100-currentPercent);
						} else {
							if (curtain_percent != null) {
								int curtainPercent = curtain_percent.getAsInt();
								esCurtainES0013.setPercent(100-curtainPercent);
							}
						}
						esCurtainES0013.setStatus(Status.NONE);
					}
					
				} else {
					// 暂无处理
				}
				
			}
			break;
			case ES_0014: {// EastSoft红外转发模块(空调)
				ESAirES0014 esAirES0014 = (ESAirES0014) device;
				JsonElement temperature = function.get("temperature");
				JsonElement humidness = function.get("humidness");
				if (temperature != null) {
					esAirES0014.setEquipmentTemperature(temperature.getAsFloat());
					if (humidness != null) {
						esAirES0014.setEquipmentHumidness(humidness.getAsFloat());
					}
					sendGetStatus(esAirES0014);
				}
				if (errorCodeString != null) {
					int errorCode = Integer.parseInt(errorCodeString);
					esAirES0014
							.setStatus(Tool.getStatusForErrorCode(errorCode));
					sendSetStatus(esAirES0014, null);
				}
				JsonElement learnResult = function.get("infrared_learn_result");
				if (learnResult != null) {
					if (learnResult.getAsString().equals("success")) {
						sendSetStatus(esAirES0014, "学习成功");
					} else {
						sendSetStatus(esAirES0014, "学习失败,请重试！");
					}
				}
				sendGetStatus(esAirES0014);
			}
				break;
			case ES_0015: {// EastSoft红外转发模块(电视)
				ESTvES0015 es = (ESTvES0015) device;
				JsonElement temperature = function.get("temperature");
				JsonElement humidness = function.get("humidness");
				if (temperature != null) {
					es.setEquipmentTemperature(temperature.getAsFloat());
					if (humidness != null) {
						es.setEquipmentHumidness(humidness.getAsFloat());
					}
					sendGetStatus(es);
				}
				if (errorCodeString != null) {
					int errorCode = Integer.parseInt(errorCodeString);
					es.setStatus(Tool.getStatusForErrorCode(errorCode));
					sendSetStatus(es, null);
				}
				JsonElement learnResult = function.get("infrared_learn_result");
				if (learnResult != null) {
					if (learnResult.getAsString().equals("success")) {
						sendSetStatus(es, "学习成功");
					} else {
						sendSetStatus(es, "学习失败,请重试！");
					}
				}
				sendGetStatus(es);
			}
				break;
			case ES_0016: { // EastSoft智能锁
				ESSmartLockES0016 es = (ESSmartLockES0016) device;
				// 智能锁自身序列号(填在device的sn标识中)
				JsonElement lockAddr = function.get("lock_addr");

				if (lockAddr == null || !lockAddr.getAsString().equals(es.getSn())) {
					return;
				}
				JsonObject alarm = jsonObject.getAsJsonObject("alarm");

				//开门联动
				 Device adapterDevice = servo.getDevice("0faec0ef-24e3-480d-8288-821d6040d933");
				 ESGateWayES0002 gateWay = (ESGateWayES0002) adapterDevice;
				
				 Device deviceOne =  servo.getDevice("85d597dc-eb1e-498d-9422-6a4b769ce0b6");
				 ESSwitchTouchTwoES0006_01 switchOne = (ESSwitchTouchTwoES0006_01)deviceOne;
				
				 switchOne.setSwitchOne(true);
				 
				// 门栓打开（正在开门）
				if (alarm != null) {
					if (alarm.get("doorbell") != null) {
						sendNotice("有人在按门铃", es);
					}
				} else {
					JsonElement controllerAddr = function.get("deviceKey");
					// 正在开锁
					if ((function.get("user_no") != null)) {
						sendNotice("用户：" + function.get("user_no").getAsString() + "开了门", es);
						new MQTTDriver(gateWay).setStatus(switchOne);
					}
					
					if (controllerAddr != null && controllerAddr.getAsString().equals(gatewayKey)) { // 网关请求
						// 开锁
						JsonElement unlocking = function.get("unlocking");
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							es.setFeed(unlocking.getAsBoolean());
							es.setStatus(Tool.getStatusForErrorCode(errorCode));
						} else { // 请求失败
							es.setStatus(Tool.getStatusForErrorCode(errorCode));
						}
					}
				}
			}
				break;
			case ES_0017: { // PLC转RS485 PM2.5传感器
				ESPLC2RS485PMES0017 dEs0017 = (ESPLC2RS485PMES0017) device;
				JsonElement messageString = function.get("messageString");
				JsonElement controllerAddr = function.get("controller_addr");
				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					if (errorCodeString!=null) {
						dEs0017.setStatus(Tool.getStatusForErrorCode(Integer.parseInt(errorCodeString)));
					}
				} else { // 设备消息回复
					String message = messageString.getAsString();
					if (message.length() != 14)
						return;
					String str = message.substring(6, 10);
					dEs0017.setDegree(Integer.valueOf(str, 16));
					sendGetStatus(dEs0017);
				}
			}
				break;
			case ES_0018: { // PLC转RS485 光照传感器
				ESPLC2RS485IluminaceSensorES0018 dEs0018 = (ESPLC2RS485IluminaceSensorES0018) device;
				JsonElement messageString = function.get("messageString");
				JsonElement controllerAddr = function.get("controller_addr");
				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					int errorCode = Integer.parseInt(errorCodeString);
					dEs0018.setStatus(Tool.getStatusForErrorCode(errorCode));
				} else { // 设备消息回复
					String message = messageString.getAsString();
					if (message.length() != 18)
						return;
					String str = message.substring(6, 14);
					dEs0018.setDegree(Integer.valueOf(str, 16) / 10);
					sendGetStatus(dEs0018);
				}
			}
				break;
			case ES_0019_01: { // EastSoft调光控制模块(2路可控硅1)
				ESDimmingES0019_01 es = (ESDimmingES0019_01) device;
				JsonElement switchCh1 = function.get("switch_ch1");
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement dimingCurrentCh1 = function.get("diming_current_ch1");//当前亮度

				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					if (dimingCurrentCh1!=null) {
						es.setLuminosity(dimingCurrentCh1.getAsInt());
					}
					if (jsonObject.get("timestamp") != null) {
						es.setFeed(switchCh1.getAsBoolean());
						es.setStatus(Tool.getStatusForErrorCode(0));//不返回errorCode
					}else {
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0 && switchCh1 != null) { // 请求成功
								es.setFeed(switchCh1.getAsBoolean());
				}
					es.setStatus(Tool.getStatusForErrorCode(errorCode));
			}
					sendGetStatus(es);
				}
			}
			break;
			case ES_0019_02: { // EastSoft调光控制模块(2路可控硅2)
				ESDimmingES0019_02 es = (ESDimmingES0019_02) device;
				JsonElement switchCh2 = function.get("switch_ch2");
				JsonElement controllerAddr = function.get("controller_addr");
				JsonElement dimingCurrentCh2 = function.get("diming_current_ch2");//当前亮度

				if (controllerAddr != null) { // PLC确认报文回复 只保存设备状态数据
					if (dimingCurrentCh2!=null) {
						es.setLuminosity(dimingCurrentCh2.getAsInt());
					}
					if (jsonObject.get("timestamp") != null) {
						es.setFeed(switchCh2.getAsBoolean());
						es.setStatus(Tool.getStatusForErrorCode(0));//不返回errorCode
					}else {
						int errorCode = Integer.parseInt(errorCodeString);
						if (errorCode == 0) { // 请求成功
							if (switchCh2 != null) { // 开关状态返回
								es.setFeed(switchCh2.getAsBoolean());
					}
				}
					es.setStatus(Tool.getStatusForErrorCode(errorCode));
			}
					sendGetStatus(es);
				}
			}
			break;
			case ES_0020: {// 四路开关执行器（带电流检测）
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0020 esSwitchFourES0020 = (ESSwitchFourES0020) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
						esSwitchFourES0020.setSwitchOne(asJsonArray.get(0).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourES0020.setSwitchTwo(asJsonArray.get(1).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourES0020.setSwitchThree(asJsonArray.get(2).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourES0020.setSwitchFour(asJsonArray.get(3).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourES0020.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourES0020.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
					esSwitchFourES0020.setSwitchOne(asJsonArray.get(0).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourES0020.setSwitchTwo(asJsonArray.get(1).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourES0020.setSwitchThree(asJsonArray.get(2).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourES0020.setSwitchFour(asJsonArray.get(3).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourES0020.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourES0020, null);
				}
			}
				break;
			case ES_0020_01: {

				// 四路开关执行器开关1
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0020_01 esSwitchFourOne = (ESSwitchFourES0020_01) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						if (function.get("channel_collection")!=null) {
							JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
							esSwitchFourOne.setStatus(asJsonArray.get(0).getAsJsonObject().get("switch").getAsBoolean());
							esSwitchFourOne.setStatus(Tool.getStatusForErrorCode(errorCode));
						}
					} else { // 请求失败
						esSwitchFourOne.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					if (function.get("channel_collection") != null) {
						JsonArray asJsonArray = function.get(
								"channel_collection").getAsJsonArray();
						if (asJsonArray.get(0).getAsJsonObject().get("switch") != null) {
							esSwitchFourOne.setStatus(asJsonArray.get(0)
									.getAsJsonObject().get("switch")
									.getAsBoolean());
							esSwitchFourOne.setStatus(Status.NONE);
							sendSetStatus(esSwitchFourOne, null);
						}
					}
				}
			}
				break;
			case ES_0020_02: {

				// 四路开关执行器开关2
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0020_02 esSwitchFourTwo = (ESSwitchFourES0020_02) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
						esSwitchFourTwo.setStatus(asJsonArray.get(1).getAsJsonObject().get("switch").getAsBoolean());
					} else { // 请求失败
						esSwitchFourTwo.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
					esSwitchFourTwo.setStatus(asJsonArray.get(1).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourTwo.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourTwo, null);
				}
			}
				break;
			case ES_0020_03: {

				// 四路开关执行器开关3
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0020_03 esSwitchFourThree = (ESSwitchFourES0020_03) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
						esSwitchFourThree.setStatus(asJsonArray.get(2).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourThree.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourThree.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
					esSwitchFourThree.setStatus(asJsonArray.get(2).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourThree.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourThree, null);
				}
			}
				break;
			case ES_0020_04: {

				// 四路开关执行器开关4
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESSwitchFourES0020_04 esSwitchFourFour = (ESSwitchFourES0020_04) device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
						esSwitchFourFour.setStatus(asJsonArray.get(3).getAsJsonObject().get("switch").getAsBoolean());
						esSwitchFourFour.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					} else { // 请求失败
						esSwitchFourFour.setStatus(Tool
								.getStatusForErrorCode(errorCode));
					}
				} else { // 主动上传
					JsonArray asJsonArray = function.get("channel_collection").getAsJsonArray();
					esSwitchFourFour.setStatus(asJsonArray.get(3).getAsJsonObject().get("switch").getAsBoolean());
					esSwitchFourFour.setStatus(Status.NONE);
					sendSetStatus(esSwitchFourFour, null);
				}
			}
				break;
			case ES_0023: {
				// 人体红外传感器上报
				ESHumanInfraredES0023 esHumanInfrared = (ESHumanInfraredES0023)device;
				if (function.get("people") != null) {
					esHumanInfrared.setLight(function.get("light").getAsInt());
					esHumanInfrared.setPeople(function.get("people").getAsBoolean());
					esHumanInfrared.setIllumination(function.get("illumination").getAsInt());
					esHumanInfrared.setStatus(Status.NONE);
					sendSetStatus(esHumanInfrared, null);
					Executor.getEventLoopGroup().execute(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(100); // 检测到有人0.1s后置为无人
								System.err.println("检测到有人0.1s后置为无人");
								esHumanInfrared.setPeople(false);
								sendSetStatus(esHumanInfrared, null);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} 
						}
					});
				}else if(errorCodeString != null){
					esHumanInfrared.setStatus(Tool.getStatusForErrorCode(Integer.parseInt(errorCodeString)));
				}
			}
				break;
			case ES_0024: {
				// 联网型温控器
				String controllerAddr = function.get("controller_addr")
						.getAsString();
				ESNetworkedThermostatES0024 nt = (ESNetworkedThermostatES0024)device;
				if (gatewayKey.equals(controllerAddr)) { // 网关请求
					int errorCode = Integer.parseInt(errorCodeString);
					if (errorCode == 0) { // 请求成功
						nt.setPowerOn(function.get("power_on").getAsBoolean());
						nt.setTemperature(function.get("temperature").getAsInt());
						nt.setWindSpeed(nt.getWindSpeedFromGateway(function.get("rev_speed").getAsString()));
						nt.setLowTempEnable(function.get("low_temp_enable").getAsBoolean());
						nt.setLowTemp(function.get("low_temp").getAsInt());
						nt.setMode(nt.getModeFromGateway(function.get("model").getAsString()));
						nt.setLockPane(function.get("lock_panel").getAsBoolean());
						nt.setReport(nt.getReportFromGateway(function.get("report").getAsString()));
						nt.setStatus(Tool.getStatusForErrorCode(errorCode));
						sendSetStatus(nt, null);
					} else { // 请求失败
						nt.setStatus(Tool.getStatusForErrorCode(errorCode));
					}
				}else{//主动上报
					nt.setPowerOn(function.get("power_on").getAsBoolean());
					nt.setTemperature(function.get("temperature").getAsInt());
					nt.setWindSpeed(nt.getWindSpeedFromGateway(function.get("rev_speed").getAsString()));
					nt.setLowTempEnable(function.get("low_temp_enable").getAsBoolean());
					nt.setLowTemp(function.get("low_temp").getAsInt());
					nt.setMode(nt.getModeFromGateway(function.get("model").getAsString()));
					nt.setLockPane(function.get("lock_panel").getAsBoolean());
					nt.setReport(nt.getReportFromGateway(function.get("report").getAsString()));
					nt.setStatus(Status.NONE);
					sendSetStatus(nt, null);
				}
			}
			break;
			case ES_0026: {// EastSoft红外转发器
				ESInfraredTransponderES0026 es = (ESInfraredTransponderES0026) device;
				JsonElement temperature = function.get("temperature");
				JsonElement humidness = function.get("humidness");
				if (temperature != null) {
					es.setEquipmentTemperature(temperature.getAsFloat());
					if (humidness != null) {
						es.setEquipmentHumidness(humidness.getAsFloat());
					}
					sendGetStatus(es);
				}
				if (errorCodeString != null) {
					int errorCode = Integer.parseInt(errorCodeString);
					es.setStatus(Tool.getStatusForErrorCode(errorCode));
					sendSetStatus(es, null);
				}
				JsonElement learnResult = function.get("infrared_learn_result");
				if (learnResult != null) {
					if (learnResult.getAsString().equals("success")) {
						sendSetStatus(es, "学习成功");
					} else {
						sendSetStatus(es, "学习失败,请重试！");
					}
				}
				sendGetStatus(es);
			}
				break;
				case ES_0027: { // PLC转RS485 射频调试模块
					ESPLC2RS485FYSerialRadioFrequencyES0027 dEs0027 = (ESPLC2RS485FYSerialRadioFrequencyES0027) device;
					JsonElement messageString = function.get("messageString");
					if (messageString != null) {// 设备消息回复
						String message = messageString.getAsString();
						if (message.length() % 12 != 0)
							return;
						if (dEs0027.isLearn()) {
							dEs0027.setSn(message.substring(2, 8));
							sendSetStatus(dEs0027, "学习成功");
							Data.UpdateDevice(dEs0027);
							dEs0027.setLearn(false);
							dEs0027.setFeed(false);
							break;
						} else if (dEs0027.isFeed() || !message.substring(2, 8).equals(dEs0027.getSn()))
							continue;
						dEs0027.setFeed(true);
						sendSetStatus(dEs0027, null);
					}
				}
					break;
			default:
				break;
			}
		}

	}

	/**
	 * 获取时间
	 * 
	 * @param msg
	 *            mqtt返回消息
	 * @return 获取的时间
	 */
	public static final Date getMqttDate(JsonObject fuction) {
		if (Tool.isEmpty(fuction.get("date").getAsString()) ||Tool.isEmpty(fuction.get("time").getAsString())) {
			return null;
		}
		String date = fuction.get("date").getAsString();
		String time = fuction.get("time").getAsString();
		StringBuilder sb = new StringBuilder();
		sb.append("20").append(date).append(" ").append(time);
		return Tool.stringToDate(sb.toString());
	}

	/**
	 * 发送控制设备状态消息
	 */
	private static void sendSetStatus(Device d, String error) {
		// 记录日志
		Data.CreateDeviceStatus(d, Origin.UNKNOWN);

		Message.Builder msg = Message.newBuilder();
		WriteDeviceStatus.Builder wrsb = msg.getWriteDeviceStatusBuilder();
		MDeviceStatus.Builder itemBuilder = wrsb.getItemBuilder();

		itemBuilder.setDeviceId(d.getId());
		itemBuilder.setStatus(d.getStatus().getValue());
		itemBuilder.setCreated(d.getTickStatus());
		for (int index = 0; index < d.getFeatureCount(); index++) { 
			itemBuilder.putItems(index, d.getFeature(index).getValue());
		}
		// 发送
		wrsb.setItem(itemBuilder);

		if (error != null) {
			msg.setError(error);
		}
		Linkager.executeLinkage(d);//设备上报触发联动
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}

	/**
	 * 发送读取设备状态消息
	 */
	private static void sendGetStatus(Device d) {
		// 记录日志
		Data.CreateDeviceStatus(d, Origin.UNKNOWN);

		Message.Builder msg = Message.newBuilder();
		Builder rdsb = msg.getReadDeviceStatusBuilder();
		MDeviceStatus.Builder itemBuilder = rdsb.getItemBuilder();

		itemBuilder.setDeviceId(d.getId());
		itemBuilder.setStatus(d.getStatus().getValue());
		itemBuilder.setCreated(d.getTickStatus());
		for (int index = 0; index < d.getFeatureCount(); index++) {
			itemBuilder.putItems(index, d.getFeature(index).getValue());
		}
		// 发送
		Message m = msg.build();
		Cloud.send(m);
		Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);
	}
	/**
	 * 发送推送通知
	 */
	private static void sendNotice(String notice,Device device) {
	Message.Builder msg = Message.newBuilder();
	com.kiy.protocol.Units.MNotice.Builder noticeBuilder = msg.getNoticeBuilder();
	noticeBuilder.setDeviceId(device.getId());

	noticeBuilder.setContent(notice);
	Message m = msg.build();
	Cloud.send(m);
	Master.getGroup().writeAndFlush(m, MatcherLogon.Logon);

	//通知日志记录
	Notice n = new Notice();
	n.setDeviceId(device.getId());
	n.setContent(notice);
	Data.CreateNotice(n);
	}
}
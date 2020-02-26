package com.kiy.servo.test;

import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.google.gson.JsonObject;
import com.kiy.servo.Config;
import com.kiy.servo.mqtt.MQTTServer;

public class TestMQTT {
	public static void main(String[] args) throws MqttPersistenceException, MqttException {
		Config.load();
		Config.check();
		
		MQTTServer mqttServer = new MQTTServer("0000ADBO");
		mqttServer.start();
		MqttTopic topic = mqttServer.getTopic("kiy/0000ADBO/0000ADBO/command");
		String a=JOptionPane.showInputDialog("请输入设备的A:");
		String p=JOptionPane.showInputDialog("请输入设备的P:");
		String alias=JOptionPane.showInputDialog("请输入设备的别名:");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("deviceKey", "0000ADBO");
		jsonObject.addProperty("cmd", "addDevice");
		jsonObject.addProperty("channel", "plc");
		JsonObject jsonObject2 = new JsonObject();
		jsonObject2.addProperty("aid", a);
		jsonObject2.addProperty("password", p);
		jsonObject2.addProperty("alias", alias);
		jsonObject.add("data", jsonObject2);
		
		MqttMessage message = new MqttMessage();
		message.setQos(1);
	    message.setRetained(true);
	    message.setPayload(jsonObject.toString().getBytes());
		
		mqttServer.publish(topic, message);
//		mqttServer.stop();
	}
}	

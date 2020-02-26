package com.kiy.servo.test;

import com.kiy.common.Weather;
import com.kiy.servo.Config;
import com.kiy.servo.data.Data;
import com.kiy.servo.notice.WeatherManager;

public class TestWeather {
	public static void main(String[] args) {
		
		Config.load();
		Data.initialize();
		
		
		Weather weather = WeatherManager.getWeather();
		
		String aqi = weather.getResult().getWeather_curr();
		System.err.println("天气"+aqi);
		String aqi2 = weather.getResult().getAqi();
		System.err.println("PM"+aqi2);
		String aqiString = weather.getResult().getAQIString();
		System.err.println("AQI text"+aqiString);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		
		
	}
}

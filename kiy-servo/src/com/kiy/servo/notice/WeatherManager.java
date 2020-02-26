package com.kiy.servo.notice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.kiy.common.AQI;
import com.kiy.common.Servo;
import com.kiy.common.Weather;
import com.kiy.servo.Config;
import com.kiy.servo.Log;
import com.kiy.servo.data.Data;

public class WeatherManager {
	private static final String DAY_START = "06:00:00";//白天开始时间
	private static final String DAY_END = "19:00:00";//白天结束时间
	
//	private static final String DAY_WEATHER_ICON_URL = "http://cloud.athenall.com:8080/data/weatherIcon/day/";//白天天气图标路径
//	private static final String NIGHT_WEATHER_ICON_URL = "http://cloud.athenall.com:8080/data/weatherIcon/night/";//晚上天气图标路径
//
//	private static final String IMAGE_TYPE=".png";//图片类型
	private WeatherManager(){}
	
	/**
	 * 获取天气信息
	 * @return
	 */
	public static Weather getWeather(){
		Servo servo = Data.getServo();
		Weather weather = servo.getWeather();
		if (weather == null) {
			/*请求*/
			weather = weatherRequest(servo);
			return weather;
		}else {
			long updated = weather.getUpdated();
			if (System.currentTimeMillis()-updated>=60000) {
				/*缓存超过10分钟则更新*/
				weather = weatherRequest(servo);
				return weather;
			}else {
				return weather;
			}
		}
		
	}
	
	/**
	 * 请求接口获取天气
	 * @return
	 */
	private static Weather weatherRequest(Servo servo){
		InputStream in = null; 
		ByteArrayOutputStream out = null;
		try {
			URL u = new URL("http://api.k780.com/?app=weather.today&weaid="+Config.WEATHER_WEAID+"&appkey=32106&sign=af54d39737870ec1d35de9edb1c4b2b0&format=json");
			in = u.openStream();
			out = new ByteArrayOutputStream();
			
			byte[] b ;
			try {
				byte buf[] = new byte[1024];
				int read = 0;
				while ((read = in.read(buf)) > 0) {
					out.write(buf, 0, read);
				}
				b = out.toByteArray();
			} finally {
					in.close();
					out.flush();
					out.close();
			}
			String string = new String(b, "utf-8");
			System.err.println(string);
			Gson gson = new Gson();
			Weather weather = gson.fromJson(string, Weather.class);
			weather.getResult().setWeather_icon(getWeatherIcon(weather.getResult().getWeather_icon(),weather.getResult().getWeather_icon1()));
//			String dayWeather = weather.getResult().getWeatid();
//			String nightWeather = weather.getResult().getWeatid1();
//			String currentWeather = "";
//			if(isNight()){//设置天气图标
//				if(null != nightWeather && !"".equals(nightWeather)){
//					currentWeather = nightWeather;
//				}else{
//					currentWeather = dayWeather;
//				}
//				weather.getResult().setWeather_icon(getCustomizeWeatherIcon(currentWeather));	
//			}else{
//				if(null != dayWeather && !"".equals(dayWeather)){
//					currentWeather = dayWeather;
//				}else{
//					currentWeather = nightWeather;
//				}
//				weather.getResult().setWeather_icon(getCustomizeWeatherIcon(currentWeather));
//			}
			weather.setUpdated(System.currentTimeMillis());
			String aqiRequest = aqiRequest();
			System.err.println(aqiRequest);
			weather.getResult().setAqi(aqiRequest);
			servo.setWeather(weather);
			return weather;
		} catch (IOException e) {
			Log.error(e);
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			
			if (out!=null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 请求AQI接口
	 */
	private static String aqiRequest(){
		InputStream in = null; 
		ByteArrayOutputStream out = null;
		try {
			URL u = new URL("http://api.k780.com/?app=weather.pm25&weaid="+Config.AQI_WEAID+"&appkey=32106&sign=af54d39737870ec1d35de9edb1c4b2b0&format=json");
			in = u.openStream();
			out = new ByteArrayOutputStream();
			
			byte[] b ;
			byte buf[] = new byte[1024];
			int read = 0;
			while ((read = in.read(buf)) > 0) {
				out.write(buf, 0, read);
			}
			b = out.toByteArray();
					
			String string = new String(b, "utf-8");
			
			Gson gson = new Gson();
			AQI aqi = gson.fromJson(string, AQI.class);
			if (aqi.getResult()!=null) {
				return aqi.getResult().getAqi();
			}else {
				return "0";
			}
			
		} catch (IOException e) {
			Log.error(e);
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.error(e);
				}
			}
			
			if (out!=null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e);
				}
				
			}
		}
		return null;
	}
//	/**
//	 * 自定义天气图标（自动适配白天晚上）
//	 * @param type 天气类型
//	 * @return  图标
//	 */
//	private static String getCustomizeWeatherIcon(String weatid){
//		String weatherIcon=null;
//		if(isNight()){
//			weatherIcon = NIGHT_WEATHER_ICON_URL+weatid+IMAGE_TYPE;
//		}else {
//			weatherIcon = DAY_WEATHER_ICON_URL+weatid+IMAGE_TYPE;
//		}
//		return weatherIcon;
//	}
	/**
	 * 判断当前时间是否为定义的晚上
	 * @return
	 */
	private static boolean isNight(){
		boolean isNight=false;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date=new Date();//获取当前时间
		try {
			date = sdf.parse(sdf.format(date));//指定格式的当前时间
			System.out.println("指定格式的当前时间="+date);
			if(sdf.parse(DAY_START).before(date) && date.before(sdf.parse(DAY_END))){
				isNight=false;
			}else {
				isNight=true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return isNight;
	}
	/**
	 * 获取当前需要显示的天气图标
	 * @return
	 */
	private static String getWeatherIcon(String weatherIconDay,String weatherIconNight){
		String weatherIcon="";
		if(isNight()){
			if(weatherIconNight!=null && !"".equals(weatherIconNight)){
				weatherIcon=getWeatherIconUrl(weatherIconNight,true);
			}else{
				weatherIcon=getWeatherIconUrl(weatherIconDay,false);
			}
		}else{
			if(weatherIconDay!=null && !"".equals(weatherIconDay)){
				weatherIcon=getWeatherIconUrl(weatherIconDay,false);
			}else{
				weatherIcon=getWeatherIconUrl(weatherIconNight,true);
			}
		}
		return weatherIcon;
	}
	/**
	 * 或指定类型的天气图标
	 * @param weatherIcon http://api.k780.com/upload/weather/d/1.gif
	 * @return  		  http://api.k780.com/upload/weather/d1/1.png
	 */
	private static String getWeatherIconUrl(String weatherIcon,boolean isNight){
		if(weatherIcon!=null && !"".equals(weatherIcon)){
			if(isNight){
				weatherIcon=weatherIcon.replace("/n/", "/n1/");
			}else{
				weatherIcon=weatherIcon.replace("/d/", "/d1/");
			}
			weatherIcon=weatherIcon.replace(".gif", ".png");
		}
		return weatherIcon;
	}
	
}

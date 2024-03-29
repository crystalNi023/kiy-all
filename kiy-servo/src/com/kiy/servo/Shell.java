package com.kiy.servo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Shell {
	private static final String FORMAT = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS:%1$tL\t%2$s%n";
	private  static int times=0;//保存调用linux重启测试
	 public static String exec(String command) throws InterruptedException {
		 	Log.info("linuxCommand:"+command);
		 	Thread.sleep(10000);//停止10秒再处理（防止死机）
	        String returnString = "";
	        Process pro = null;
	        Runtime runTime = Runtime.getRuntime();
	        if (runTime == null) {
	            System.err.println("Create runtime false!");
	        }
	        try {
	            pro = runTime.exec(command);
	            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
	            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
	            String line;
	            while ((line = input.readLine()) != null) {
	                returnString = returnString + line + "\n";
	            }
	            input.close();
	            output.close();
	            pro.destroy();
	        } catch (IOException ex) {
	           Log.error(ex.getMessage());
	        }
	        return returnString;
	    }
	 public static String execArray(String [] command) throws InterruptedException {
		 Thread.sleep(10000);//停止10秒再处理（防止死机）
		 String returnString = "";
		 Process pro = null;
		 Runtime runTime = Runtime.getRuntime();
		 if (runTime == null) {
			 System.err.println("Create runtime false!");
		 }
		 try {
			 pro = runTime.exec(command);
			 BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			 PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
			 String line;
			 while ((line = input.readLine()) != null) {
				 returnString = returnString + line + "\n";
			 }
			 input.close();
			 output.close();
			 pro.destroy();
		 } catch (IOException ex) {
			 Log.error(ex.getMessage());
		 }
		 return returnString;
	 }
	 /**
	  * 判断当前时间戳    和指定时间对比（小重启linux系统，大则正常返回）
	  * @param timestamp
	  * @return
	 * @throws InterruptedException 
	  */
	 public static long  checkCurrentTime(long timestamp){
	    	java.util.Date date;
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
				Calendar cal = Calendar.getInstance();
		    	cal.setTime(date);
		    	long itemTimestamp = cal.getTimeInMillis();
		    	if (timestamp < itemTimestamp) {
		    	synchronized (Shell.class) {
					System.err.println("====发现linux系统时间异常====start");
					System.err.println("当前时间：");
					System.err.println(Util.longToDate(timestamp));
					System.err.println("比较时间");
					System.err.println(Util.longToDate(itemTimestamp));
					System.err.println("获取cpu相关信息：");
		    		getCpuUsage();
		    		System.err.println("java 调用linux第"+(times+1)+"次重启。。。");
		    		if(times<3){
		    			System.err.println("调用reboot重启");
		    			times++;//重启次数
		    			String reboot = exec("reboot");
		    			System.err.println("reboot重启返回信息："+reboot);
		    		}else {
		    			System.err.println("调用reboot -f 重启");
		    			times++;//重启次数
		    			String rebootf = exec("reboot -f");
		    			System.err.println("reboot -f重启返回信息："+rebootf);
					}
		    		System.err.println("====发现linux系统时间异常====end");
		    		 }
		    		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("重启linux系统异常"+e.getMessage());
			}
	    	
	    	return timestamp;
	 }
	 public static float getCpuUsage() {//获取cpu相关信息
		 System.err.println("开始收集cpu使用率");
			float cpuUsage = 0;
			Process pro1,pro2;
			Runtime r = Runtime.getRuntime();
			try {
				String command = "cat /proc/stat";
				//第一次采集CPU时间
				long startTime = System.currentTimeMillis();
				pro1 = r.exec(command);
				BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
				String line = null;
				long idleCpuTime1 = 0, totalCpuTime1 = 0;	//分别为系统启动后空闲的CPU时间和总的CPU时间
				while((line=in1.readLine()) != null){	
					if(line.startsWith("cpu")){
						line = line.trim();
						System.err.println(line);
						String[] temp = line.split("\\s+"); 
						idleCpuTime1 = Long.parseLong(temp[4]);
						for(String s : temp){
							if(!s.equals("cpu")){
								totalCpuTime1 += Long.parseLong(s);
							}
						}	
						System.err.println("IdleCpuTime: " + idleCpuTime1 + ", " + "TotalCpuTime" + totalCpuTime1);
						break;
					}						
				}	
				in1.close();
				pro1.destroy();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					System.err.println("CpuUsage休眠时发生InterruptedException. " + e.getMessage());
					System.err.println(sw.toString());
				}
				//第二次采集CPU时间
				long endTime = System.currentTimeMillis();
				pro2 = r.exec(command);
				BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
				long idleCpuTime2 = 0, totalCpuTime2 = 0;	//分别为系统启动后空闲的CPU时间和总的CPU时间
				while((line=in2.readLine()) != null){	
					if(line.startsWith("cpu")){
						line = line.trim();
						System.err.println(line);
						String[] temp = line.split("\\s+"); 
						idleCpuTime2 = Long.parseLong(temp[4]);
						for(String s : temp){
							if(!s.equals("cpu")){
								totalCpuTime2 += Long.parseLong(s);
							}
						}
						System.err.println("IdleCpuTime: " + idleCpuTime2 + ", " + "TotalCpuTime" + totalCpuTime2);
						break;	
					}								
				}
				if(idleCpuTime1 != 0 && totalCpuTime1 !=0 && idleCpuTime2 != 0 && totalCpuTime2 !=0){
					cpuUsage = 1 - (float)(idleCpuTime2 - idleCpuTime1)/(float)(totalCpuTime2 - totalCpuTime1);
					System.err.println("本节点CPU使用率为: " + cpuUsage);
				}				
				in2.close();
				pro2.destroy();
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				System.err.println("CpuUsage发生InstantiationException. " + e.getMessage());
				System.err.println(sw.toString());
			}	
			return cpuUsage;
		}
	public static void main(String[] args) {
		System.out.println(checkCurrentTime(System.currentTimeMillis()));
	}
}

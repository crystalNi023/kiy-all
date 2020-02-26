package com.kiy.servo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
/**
 * 文件操作
 * @author Administrator
 *
 */
public class FileUtil {
	 private static final String BASE_PATH = System.getProperty("user.dir");//工作路径路径
	 private static final String FILE_NAME=File.separator+"console.log";//文件名
	 private static final String ITEM_PATH=File.separator+"logs"+File.separator;//目标路径
	 private static final String INI_NAME=File.separator+"frpc.ini";//文件名
	 private static final String INI_PATH=File.separator+"frp";//目标路径
	 private static String ITEM_NAME="";//目标文件名称
	 private static final int LOG_EXPIRED=-30;//日志有效期（天）
	 private static boolean isChange=false;//ini文件是否改变（默认未改变）
	 private static Map<String, String> remote_ports = new HashMap<String, String>();
	/**
	 * 移动文件
	 * @param srcPathAndName 文件路径+文件名
	 * @param itemPath 目标路径
	 */
	public static void moveToDir(String srcPath,String originalName,String itemName,String itemPath)
	{
		try
		{
			File file=new File(srcPath+originalName); //源文件
			File itemFile = new File(itemPath+itemName);
			if (!itemFile.getParentFile().exists()) {//判断父目录路径是否存在，即test.txt前的I:\a\b\
				itemFile.getParentFile().mkdirs();//不存在则创建父目录
			}
			if (file.renameTo(itemFile)) //源文件移动至目标文件目录
			{
				System.out.println("File is moved successful!");//输出移动成功
				
			}
			else
			{
				System.out.println("File is failed to move !");//输出移动失败
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
    /*
     * 实现文件的拷贝
     * @param srcPathStr
     *          源文件的地址信息
     * @param desPathStr
     *          目标文件的地址信息 
     */
    private static void copyFile(String srcPath,String originalName,String itemName,String itemPath) {
    	File file= new File(itemPath+itemName);
    	if (!file.getParentFile().exists()) {//判断父目录路径是否存在，即test.txt前的I:\a\b\
    		file.getParentFile().mkdirs();//不存在则创建父目录
		}
        try{
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPath+originalName);
            FileOutputStream fos = new FileOutputStream(itemPath+itemName);                

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            fos.flush();
            //3.释放资源
            fis.close();
            fos.close();
            System.out.println("copy  file  success!");
        }catch (Exception e){
        	System.out.println("copy  file  failed!");
            e.printStackTrace();
        }
    }
	/**
	 * 创建文件
	 * @param path 路径
	 * @param fileName  文件名
	 * @return
	 */
	public static boolean createFile(String path,String name){
		boolean bool=false;
		String fileName=path+name;
		File file=new File(fileName);
		try {
			if (!file.getParentFile().exists()) {//判断父目录路径是否存在，即test.txt前的I:\a\b\
		    	file.getParentFile().mkdirs();//不存在则创建父目录
			}
			file.createNewFile();
    		System.out.println("success create file,the file is "+fileName);
		} catch (Exception e) {
			System.out.println("fail create file,msg: "+e.getMessage());
		}
		
		
		return bool;
	}
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名,含路径
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public static boolean deleteDirectory(String sPath) {  
    	boolean flag = false;
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
	           	try {
					if(checkFileIsValid(files[i].getName().substring(0, 10))){//判断文件是否失效（失效删除）
						  flag = deleteFile(files[i].getAbsolutePath());  
			                if (!flag) break;  
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//判断是否为失效文件 files[i].getName()
	              
	            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    /**
     * 验证文件是否失效
     * @param dateStr 指定时间字符串
     * @return
     * @throws ParseException 
     */
    public static boolean checkFileIsValid(String dateStr) throws ParseException{
    	Date dateS = getDate(dateStr);//获取返回时间
    	Date dateN = getTargetDate(new Date(),LOG_EXPIRED);//获取指定日期的
    	return dateS.before(dateN);//传输时间小于指定日期
    }
    /**
     * 清空文件内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
    	if (!file.getParentFile().exists()) {//判断父目录路径是否存在，即test.txt前的I:\a\b\
    		file.getParentFile().mkdirs();//不存在则创建父目录
		}
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
            System.out.println("clear file success!");
        } catch (IOException e) {
        	System.out.println("clear file failed!");
            e.printStackTrace();
        }
    }
    /**
     * 管理日志文件
     */
    public static void manageLogFile(){
    	ITEM_NAME=getFormat("yyyy-MM-dd");
    	copyFile(BASE_PATH,FILE_NAME,ITEM_NAME+"("+UUID.randomUUID()+").log",BASE_PATH+ITEM_PATH);//复制日志到目标路径
    	clearInfoForFile(BASE_PATH+FILE_NAME);//清空文件内容
    	deleteDirectory(BASE_PATH+ITEM_PATH);//删除规定日期外的日志
    }
    /**
     * 获取指定格式的当前日期字符串
     * @param format
     * @return
     */
    public static String getFormat(String format){
    	return new SimpleDateFormat(format).format(new Date());
    } 
    /**
     * 
     * @param dateStr 指定日期字符串
     * @return
     * @throws ParseException 
     */
    public static Date getDate(String dateStr) throws ParseException{
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	 return sdf.parse(dateStr);
    }
    /**
     * 
     * @param dateStr 指定日期字符串
     * @return
     * @throws ParseException 
     */
    public static Date getFormatDate(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	String dateStr = sdf.format(date);
    	Date dateR=date;
    	try {
    		dateR = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return dateR;
    }
    /**
     * 获取指定时间的前后天数时间
     * @param date  指定时间
     * @param i  指定前后多少天   +后几天   -前几天
     * @return
     */
    public static Date getTargetDate(Date date , int i){
    	 Calendar c = Calendar.getInstance();
    	 c.setTime(date);
    	 int day1 = c.get(Calendar.DATE);
         c.set(Calendar.DATE, day1 + i);
         return c.getTime();
    }
    public static void TimedTask () {  
        TimerTask task = new TimerTask() {  
            @Override  
            public void run() {
            	Log.info("执行定时任务开始");
            	manageLogFile(); 
            	Log.info("执行定时任务结束");
            }  
        };  
        Timer timer = new Timer(); 
        Date date=new Date();
        long delay = getTargetDate(getFormatDate(date),1).getTime()-date.getTime();  
        System.out.println("延迟"+delay/1000+"秒执行定时任务");
        long intevalPeriod = 24 * 60 * 60 * 1000;  //每天运行一次
        // schedules the task to be run in an interval  
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
      }
    
    public static void main(String[] args) {
//    	String string="yyyy-MM-dd HH:mm:ss";
//    	System.out.println(getFormat("yyyy-MM-dd"));
//    	System.out.println(getTargetDate(new Date(),10));
//    	System.out.println(string.substring(0, 10));
//		TimedTask();
//    	boolean f1=true;
//    	boolean f2=true;
//    	boolean f3=false;
//    	System.out.println(f1&f2);
//    	System.out.println(f1&f3);
//    	System.out.println(f1&f2&f3);
//    	System.out.println(System.getProperty("user.dir"));
//    
//    	 System.out.println("java.home : "+System.getProperty("java.home"));
//    	 
//         System.out.println("java.class.version : "+System.getProperty("java.class.version"));
//
//         System.out.println("java.class.path : "+System.getProperty("java.class.path"));
//
//         System.out.println("java.library.path : "+System.getProperty("java.library.path"));
//
//         System.out.println("java.io.tmpdir : "+System.getProperty("java.io.tmpdir"));
//
//         System.out.println("java.compiler : "+System.getProperty("java.compiler"));
//
//         System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));
//
//         System.out.println("user.name : "+System.getProperty("user.name"));
//
//         System.out.println("user.home : "+System.getProperty("user.home"));
//
//         System.out.println("user.dir : "+System.getProperty("user.dir"));
    	Config.load();
    	updateIni();
    
	}
    
    ////////////////////////////////////////////////////////////////
    //frp文件修改
    public static void updateIni(){
    	//boolean isChanged=false;
    	//获取数据
    	String master_mark = Config.CLOUD_ID;
    	String file=BASE_PATH+INI_PATH+INI_NAME;
		int remote_port =Config.REMOTE_PORT ;//云端获取映射端口号
		//更改配置文件
		try {
			readAndWriteIni(file, master_mark, remote_port);
			Log.info("远程主机："+master_mark+" 端口号："+remote_port+" 更新成功!");
		} catch (Exception e) {
			Log.error("远程主机："+master_mark+" 端口号更新失败！");
			Log.error("错误信息:"+e.getMessage());
		}
		if(!remote_ports.containsKey("remote_port")){//检测map中是否有remote_port
			remote_ports.put("remote_port", "");
		}
//		//判断是否更改内容
//		String org_port = remote_ports.get("remote_port");
//		if(org_port.equals(remote_port)){
//			isChange=true;
//		}else{
//			isChange=false;
//			remote_ports.put("remote_port", Integer.toString(remote_port));//更新端口
//		}
		//重启frpc
		if(isChange){
			isChange=false;
			String command="nohup /home/athena/frp/frpc -c /home/athena/frp/frpc.ini";//重启frp客户端命令
//			String[] command={"nohup", "/home/athena/frp/frpc","-c","/home/athena/frp/frpc.ini"," >/dev/null 2>&1 &"};//重启frp客户端命令
			new Thread(
				new Runnable() {
					public void run() {
						try {
							Log.info("frpc内容已改变");
							String str=Shell.exec(command);
							Log.info("返回信息："+str);
							Log.info("frpc重启成功");
						} catch (InterruptedException e) {
							Log.error("frpc重启失败，错误信息："+e.getMessage());
						}
					}
				}).start();
			
		}else{
			Log.info("frpc内容未改变！");
		}
    }
	 /**
	  * 修改ini 文件
	  * @param file 文件路径
	  * @param master_mark 伺服器唯一标识
	  * @param remote_port 服务器的端口号
	  * @return String 原始文件内容
	  * @throws Exception
	  */
    public static String readAndWriteIni(String file,String master_mark ,int remote_port ) throws Exception {
    	Log.info("文件未修改！");
    	String currentSection="";//节点名称
    	StringBuffer sbOriginal = new StringBuffer();//存储修改前的文件内容
    	StringBuffer sb = new StringBuffer();//存储修改后的文件内容
        BufferedReader bf = new BufferedReader(new InputStreamReader( new FileInputStream(file), "UTF-8"));//GB2312
        String line = null;
        short i=0;
        while ((line = bf.readLine()) != null) {
            line = line.trim();
            sbOriginal.append(line+"\r\n");
            if ("".equals(line))
                continue;
            if (line.startsWith("[") && line.endsWith("]")) {
            	i++;
            	currentSection = line.substring(1, line.length() - 1);//获取节点名称
            	if(i==2 || "ssh".equalsIgnoreCase(currentSection)){
            		line="["+master_mark+"]";//伺服器标识   替换连接名称[ssh]->[aadqwdacacafa]
            	}
                sb.append(line+"\r\n");
            }else if(line.startsWith(";")){
                String str = "#"+line.substring(1);
                sb.append(str+"\r\n");
            }else if(line.startsWith("#")){
            	 sb.append(line+"\r\n");
            }else {
                int index = line.indexOf("=");
                if (index != -1) {
                    String key = line.substring(0, index);
                    String value="";
                    if("remote_port".equals(key.trim())){
                    	String org_value =line.substring(index + 1, line.length());
                    	value = Integer.toString(remote_port);
                    	if(null != value && !org_value.equals(value)){
                    		isChange=true;
                    		}
                    	Log.info("文件remote_port="+org_value);
                        Log.info("云端获取remote_port="+value);
                    }else {
                    	value = line.substring(index + 1, line.length());
					}
                    sb.append(key+"="+value+"\r\n");
                }
            }
        }
        bf.close();
        //写入文件
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));//写入新文件
		bufferedWriter.write(sb.toString());
		bufferedWriter.flush();
//		Log.info("文件已修改！");
//		Log.info("文件修改后内容为："+"\r\n"+sb.toString());
		bufferedWriter.close();
        return sbOriginal.toString();
    }
}
